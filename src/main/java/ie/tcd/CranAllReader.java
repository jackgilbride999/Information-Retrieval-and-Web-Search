package ie.tcd;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import ie.tcd.gilbridj.CranfieldDocument;

public class CranAllReader {

    String readFile(String path){

        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
    
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append(" ");
            }
        
            String fileContent = br.toString();
            fr.close();
            br.close();
            return fileContent;
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
        }
    
    public List<CranfieldDocument> getDocumentList(String path){

        String cranAllFile = readFile(path);
        String[] separatedFile = cranAllFile.split(".I");

        List<CranfieldDocument> documentList = new ArrayList<CranfieldDocument>();
        // skip first item in array as it is empty
        for(int i = 1; i < separatedFile.length; i++) {
            documentList.add(createCranfieldDocumentObject(separatedFile[i]));
        }

        return documentList;
    }

    private CranfieldDocument createCranfieldDocumentObject(String documentString) {

        String[] delimiters = {".T", ".A", ".B", ".W"};
		ArrayList<String> docContents = new ArrayList<String>();

        for(int i = 0; i < delimiters.length; i++){

            if(!documentString.equals(".W") && !delimiters[i].equals(".W")) {
                // this is the base case
                // split on the delimiter, add the left hand side to the list
                //   and continue operating on the right hand side
                docContents.add(documentString.split(delimiters[i])[0]);
                documentString = documentString.split(delimiters[i])[1];
            }
            else if(documentString.equals(".W")){
                // this is the case where the entire remaining string equals ".W"
                // i.e. there is no words or bibliography
                // add blank arguments
                docContents.add("");
                docContents.add("");
            } else if (delimiters[i].equals(".W")) {
                // we have reached the ".W" delimiter and there is a bibliography
                // and words to add, so add them
                docContents.add(documentString.split(delimiters[i])[0]);
                docContents.add(documentString.split(delimiters[i])[1]);
            }
        }

        return new CranfieldDocument(docContents);
    }

}
