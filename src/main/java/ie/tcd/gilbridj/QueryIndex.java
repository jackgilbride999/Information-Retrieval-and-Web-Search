package ie.tcd.gilbridj;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.index.DirectoryReader;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;

public class QueryIndex
{		
	public static void main(String[] args) throws IOException, ParseException
	{
		String analyzerType = args[2].toLowerCase();
		Analyzer analyzer = Utils.getAnalyzerFromType(analyzerType);

		// create objects to read and search across the index
		Directory directory = FSDirectory.open(Paths.get(Constants.INDEX_DIRECTORY));
		DirectoryReader ireader = DirectoryReader.open(directory);
		IndexSearcher isearcher = new IndexSearcher(ireader);
		String similarity = args[3].toLowerCase();

		Utils.setIndexSearcherSimilarity(isearcher, similarity);

		FileWriter fw = new FileWriter(Constants.RESULTS_PATH, false);
		BufferedWriter bw = new BufferedWriter(fw);

		List<CranfieldQuery> queryList = CranFileReader.getQueryList(args[1]);
		String[] fields = {Constants.TITLE, Constants.WORDS};
		HashMap<String, Float> weightMap = new HashMap<>();
		weightMap.put(Constants.TITLE, (float)0.33);
		weightMap.put(Constants.WORDS, (float)0.67);
		MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, analyzer, weightMap);

		int maxResults = 0;
		try {
			maxResults = Integer.parseInt(args[4]);
		} catch (Exception e) {
			System.out.println("Invalid argument. Max Results should be an integer");
			System.exit(1);
		}

		for(int i=0; i < queryList.size(); i++)
		{
			String queryString = QueryParser.escape(queryList.get(i).getText());

			if (queryString.length() > 0)
			{
				Query query = parser.parse(queryString);
				ScoreDoc[] hits = isearcher.search(query, maxResults).scoreDocs;
				
				for (int j = 0; j < hits.length; j++)
				{
					Document hitDoc = isearcher.doc(hits[j].doc);
					String result = ((i+1) + " Q0 " + hitDoc.get(Constants.ID) + " " + (j + 1) + " " + hits[j].score + " STANDARD");
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