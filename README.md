# Information-Retrieval-and-Web-Search

To compile, run and evaluate the search engine, simply type:
```
bash run.sh
```
The script should package the project together with Maven, run the Java project with a number of different arguments and evaluate them with trec_eval.

```
mvn -q package
rm -r index/
java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry ANALYZER SIMILARITY MAX_RESULTS
trec_eval/trec_eval -m map -m P.5 ./cran/QRelsCorrectedforTRECeval ./results.txt 
```

Where:
- `ANALYZER` is a choice of `standard`, `english` or `custom`
- `SIMILARITY` is a choice of `vsm`, `classic` or  `bm25`
- `MAX_RESULTS` is the maximum number of results you want a query to return, e.g. `10`, `100` or `1000`