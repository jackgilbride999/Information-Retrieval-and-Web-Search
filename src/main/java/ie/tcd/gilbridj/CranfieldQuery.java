package ie.tcd.gilbridj;

public class CranfieldQuery {
   
    private int queryId;
    private String text;

    public CranfieldQuery(int queryId, String text){
        this.queryId = queryId;
        this.text = text;
      //  System.out.println("Created query " + queryId + ": " + text);
    }

    public int getQueryId(){
        return queryId;
    }

    public void setQueryId(int queryId){
        this.queryId = queryId;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }

}
