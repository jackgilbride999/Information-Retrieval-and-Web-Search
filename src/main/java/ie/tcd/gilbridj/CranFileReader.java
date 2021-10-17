package ie.tcd.gilbridj;

import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CranFileReader {

    static String readFile(String path){

        try{
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
    
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line + " ") ;
            }
        
            String fileContent = sb.toString();
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
    
    public static List<CranfieldDocument> getDocumentList(String path){

        String cranAllFile = readFile(path);
        String[] separatedFile = cranAllFile.split(Constants.ID);

        List<CranfieldDocument> documentList = new ArrayList<CranfieldDocument>();
        // skip first item in array as it is empty
        for(int i = 1; i < separatedFile.length; i++) {
            documentList.add(createCranfieldDocumentObject(separatedFile[i]));
        }

        return documentList;
    }

    private static CranfieldDocument createCranfieldDocumentObject(String documentString) {

        String[] delimiters = {
            Constants.TITLE,
            Constants.AUTHOR,
            Constants.BIBLIOGRAPHY, 
            Constants.WORDS
        };
		ArrayList<String> docContents = new ArrayList<String>();

        for(int i = 0; i < delimiters.length; i++){

            if(!delimiters[i].equals(Constants.WORDS)) {
                // this is the base case
                // split on the delimiter, add the left hand side to the list
                //   and continue operating on the right hand side
                docContents.add(documentString.split(delimiters[i])[0]);
                documentString = documentString.split(delimiters[i])[1];
            }
            else {
                // we have reached the ".W" delimiter and there is a bibliography
                // and words to add, so add them
                docContents.add(documentString.split(delimiters[i])[0]);
                docContents.add(documentString.split(delimiters[i])[1]);
            }
        }

        return new CranfieldDocument(docContents);
    }

    public static List<CranfieldQuery> getQueryList(String path){
        
        String cranQryFile = readFile(path);
        String[] separatedFile = cranQryFile.split(Constants.ID);

        List<CranfieldQuery> queryList = new ArrayList<CranfieldQuery>();
        // skip first item in array as it is empty
        for(int i = 1; i < separatedFile.length; i++) {
            String[] splitQueryString = separatedFile[i].split(Constants.WORDS);
            queryList.add(new CranfieldQuery(Integer.parseInt(splitQueryString[0].trim()), splitQueryString[1]));
        }
        return queryList;
    }
}
