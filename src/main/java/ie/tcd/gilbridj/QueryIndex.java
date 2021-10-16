package ie.tcd.gilbridj;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.index.DirectoryReader;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;

public class QueryIndex
{

	// the location of the search index
	private static String INDEX_DIRECTORY = "./index";
	
	// Limit the number of search results we get
	private static int MAX_RESULTS = 1000;
	
	public static void main(String[] args) throws IOException, ParseException
	{
		// Analyzer used by the query parser. Must be the same as the one used when creating the index
		String analyzerType = args[2].toLowerCase();
		Analyzer analyzer;
		if(analyzerType.equals("standard")){
			analyzer = new EnglishAnalyzer();
		} else if (analyzerType.equals("english")){
			analyzer = new StandardAnalyzer();
		} else {
			analyzer = null;
			System.out.println("Invalid analyzer type.");
			System.exit(1);
		}

		// create objects to read and search across the index
		Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		String similarity = args[3].toLowerCase();
		if(similarity.equals("classic") || similarity.equals("vsm")) {
			isearcher.setSimilarity(new ClassicSimilarity());
		} else if(similarity.equals("bm25")) {
			isearcher.setSimilarity(new BM25Similarity());			
		} else {
			System.out.println("Invalid similarity type.");
			System.exit(1);		
		}

		// Create the query parser. The default search field is "content", but
		// we can use this to search across any field
		//QueryParser parser = new QueryParser("contents", analyzer);

		QueryParser parser = new QueryParser("contents", analyzer);


		FileWriter fw = new FileWriter("./results.txt", false);
		BufferedWriter bw = new BufferedWriter(fw);

		List<CranfieldQuery> queryList = CranFileReader.getQueryList(args[1]);
		
		for(int i=0; i < queryList.size(); i++)
		{
			String queryString = QueryParser.escape(queryList.get(i).getText());

			if (queryString.length() > 0)
			{
				// parse the query with the parser
				Query query = parser.parse(queryString);

				// Get the set of results
				ScoreDoc[] hits = isearcher.search(query, MAX_RESULTS).scoreDocs;
				
				// Print the results
				for (int j = 0; j < hits.length; j++)
				{
					Document hitDoc = isearcher.doc(hits[j].doc);
					String result = ((i+1) + " Q0 " + hitDoc.get("id") + " " + (j + 1) + " " + hits[j].score + " STANDARD");
					bw.write(result);
					bw.newLine();
				}
			}
		}
		
		bw.close();
		fw.close();
		ireader.close();
		directory.close();
	}
}