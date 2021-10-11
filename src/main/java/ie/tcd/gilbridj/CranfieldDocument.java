package ie.tcd.gilbridj;

public class CranfieldDocument {
    
    private int documentId;
    private String title;
    private String authors;
    private String bibliograpy;
    private String words;

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
