package ie.tcd.gilbridj;

import java.io.IOException;

import java.util.List;

import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
 
public class CreateIndex
{
	// Directory where the search index will be saved
	private static String INDEX_DIRECTORY = "./index";

	public static void main(String[] args) throws IOException
	{
		// Make sure we were given something to index
		if (args.length <= 3)
		{
            System.out.println("Invalid number of arguments");
            System.exit(1);            
        }

		String analyzerType = args[2].toLowerCase();
		Analyzer analyzer = Utils.getAnalyzerFromType(analyzerType);

		// Open the directory that contains the search index
		Directory directory = FSDirectory.open(Paths.get(INDEX_DIRECTORY));

		// Set up an index writer to add process and save documents to the index
		IndexWriterConfig config = new IndexWriterConfig(analyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		String similarity = args[3].toLowerCase();
		Utils.setIndexWriterConfigSimilarity(config, similarity);
		IndexWriter iwriter = new IndexWriter(directory, config);

		
		List<CranfieldDocument> documentList = CranFileReader.getDocumentList(args[0]);
		for(int i = 0; i < documentList.size(); i++)
		{
			CranfieldDocument document = documentList.get(i);
			Document luceneDocument = new Document();

			luceneDocument.add(new StringField(Constants.ID, String.valueOf(document.getDocumentId()), Field.Store.YES));
			luceneDocument.add(new TextField(Constants.TITLE, document.getTitle(), Field.Store.YES));
			luceneDocument.add(new TextField(Constants.AUTHOR, document.getAuthors(), Field.Store.YES));
			luceneDocument.add(new TextField(Constants.BIBLIOGRAPHY, document.getBibliography(), Field.Store.YES));
			luceneDocument.add(new TextField(Constants.WORDS, document.getWords(), Field.Store.YES));

			if (iwriter.getConfig().getOpenMode() == OpenMode.CREATE) {
				iwriter.addDocument(luceneDocument);
			}
		}

		// Commit everything and close
		iwriter.close();
		directory.close();

		try {
			QueryIndex.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}