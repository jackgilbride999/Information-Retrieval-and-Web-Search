mvn package

java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry standard vsm
trec_eval/trec_eval -m map ./cran/QRelsCorrectedforTRECeval ./results.txt 

java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry standard bm25
trec_eval/trec_eval -m map ./cran/QRelsCorrectedforTRECeval ./results.txt 

java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry english vsm
trec_eval/trec_eval -m map ./cran/QRelsCorrectedforTRECeval ./results.txt 

java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry english bm25
trec_eval/trec_eval -m map ./cran/QRelsCorrectedforTRECeval ./results.txt 