package ie.tcd.gilbridj;

import java.util.ArrayList;

public class CranfieldDocument {
    
    private int documentId;
    private String title;
    private String authors;
    private String bibliograpy;
    private String words;

    public CranfieldDocument(){
        this.title = "";
        this.authors = "";
        this.bibliograpy = "";
        this.words = "";
    }

    public CranfieldDocument(int documentId, String title, String authors, String bibliography, String words){
        this.documentId = documentId;
        this.title = title;
        this.authors = authors;
        this.bibliograpy = bibliography;
        this.words = words;
    }

    public CranfieldDocument(ArrayList<String> docContents) {
        this(
            Integer.parseInt(docContents.get(0).trim()), 
            docContents.get(1), 
            docContents.get(2), 
            docContents.get(3), 
            docContents.get(4)
        );
    }

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getBibliography() {
        return this.bibliograpy;
    }

    public void setBibliography(String bibliography) {
        this.bibliograpy = bibliography;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }
}
