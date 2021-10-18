echo "Compiling..."
mvn -q package
echo ""

echo "Removing existing index..."
rm -r index/
echo ""

echo "Standard Analyzer, VSM Scoring..."
java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry standard vsm 100
trec_eval/trec_eval -m map -m P.5 ./cran/QRelsCorrectedforTRECeval ./results.txt 
echo ""

echo "Standard Analyzer, BM25 Scoring..."
java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry standard bm25 100
trec_eval/trec_eval -m map -m P.5 ./cran/QRelsCorrectedforTRECeval ./results.txt 
echo ""

echo "English Analyzer, VSM Scoring..."
java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry english vsm 100
trec_eval/trec_eval -m map -m P.5 ./cran/QRelsCorrectedforTRECeval ./results.txt 
echo ""

echo "English Analyzer, BM25 Scoring..."
java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry english bm25 100
trec_eval/trec_eval -m map -m P.5 ./cran/QRelsCorrectedforTRECeval ./results.txt 
echo ""

echo "Custom Analyzer, VSM Scoring"
java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry custom vsm 100
trec_eval/trec_eval -m map -m P.5 ./cran/QRelsCorrectedforTRECeval ./results.txt 
echo ""

echo "Custom Analyzer, BM25 Scoring"
java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry custom bm25 100
trec_eval/trec_eval -m map -m P.5 ./cran/QRelsCorrectedforTRECeval ./results.txt 
echo ""