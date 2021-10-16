package ie.tcd.gilbridj;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;

public class Utils {
    
    public static Analyzer getAnalyzerFromType(String analyzerType){
        Analyzer analyzer;
        switch(analyzerType){
			case Constants.STANDARD:
				analyzer = new StandardAnalyzer();
				break;
			case Constants.ENGLISH:
				analyzer = new EnglishAnalyzer();
				break;
			case Constants.CUSTOM:
				analyzer = createCustomAnalyzer();
				break;
			default:
				analyzer = null;
				System.out.println("Invalid analyzer type. Valid analyzer types are standard, english and custom.");
				System.exit(1);
		}
        return analyzer;
    }

    public static void setIndexSearcherSimilarity(IndexSearcher isearcher, String similarity){
        switch(similarity){
			case Constants.CLASSIC:
			case Constants.VSM:
				isearcher.setSimilarity(new ClassicSimilarity());
				break;
			case Constants.BM25:
				isearcher.setSimilarity(new BM25Similarity());
				break;
			default:
				System.out.println("Invalid similarity type. Valid similarity types are vsm and bm25.");
				System.exit(1);			
		}
    }

    public static void setIndexWriterConfigSimilarity(IndexWriterConfig config, String similarity){
        switch(similarity){
			case Constants.CLASSIC:
			case Constants.VSM:
				config.setSimilarity(new ClassicSimilarity());
				break;
			case Constants.BM25:
				config.setSimilarity(new BM25Similarity());
				break;
			default:
				System.out.println("Invalid similarity type. Valid similarity types are vsm and bm25.");
				System.exit(1);			
		}
    }

    public static CustomAnalyzer createCustomAnalyzer(){
		return CustomAnalyzer.builder().build();
	}
}
