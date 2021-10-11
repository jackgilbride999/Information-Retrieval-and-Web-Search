package ie.tcd.gilbridj;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;
import java.util.List;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import org.apache.lucene.document.Document;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

//import org.apache.lucene.index.Term;
import org.apache.lucene.index.DirectoryReader;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
//import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;

public class QueryIndex
{

	// the location of the search index
	private static String INDEX_DIRECTORY = "./index";
	
	// Limit the number of search results we get
	private static int MAX_RESULTS = 10;
	
	public static void main(String[] args) throws IOException, ParseException
	{
		// Analyzer used by the query parser. Must be the same as the one used when creating the index
		Analyzer analyzer = new StandardAnalyzer();
		Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));
		
		// create objects to read and search across the index
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		
		// Create the query parser. The default search field is "content", but
		// we can use this to search across any field
		QueryParser parser = new QueryParser("contents", analyzer);


		FileWriter fw = new FileWriter("./results.txt", false);
		BufferedWriter bw = new BufferedWriter(fw);

		List<CranfieldQuery> queryList = CranFileReader.getQueryList(args[1]);
		
		String queryString = "";
		int queryId;

		for(int i=0; i < queryList.size(); i++)
		{
			queryId = queryList.get(i).getQueryId();
			queryString = queryList.get(i).getText().replace('?', ' ').trim();

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
					String result = (queryId + " Q0 " + hitDoc.get("id") + " " + (i + 1) + " " + hits[j].score + " STANDARD");
					System.out.println(result);
					bw.write(result);
					bw.newLine();
				}
			}
			
			System.out.print(">>> ");
		}
		
		bw.close();
		fw.close();
		ireader.close();
		directory.close();
	}
}