# Information-Retrieval-and-Web-Search

To compile, run and evaluate the search engine, simply enter:
```
bash run.sh
```
The script should package the project together with Maven, run the Java project with a number of different arguments and evaluate them with trec_eval.

If you wish to run the project manually, enter the following:
```
mvn -q package
rm -r index/
java -jar target/assignment1-1.0.jar cran/cran.all.1400 cran/cran.qry ANALYZER SIMILARITY MAX_RESULTS
trec_eval/trec_eval -m map -m P.5 ./cran/QRelsCorrectedforTRECeval ./results.txt 
```

Where:
- `ANALYZER` is a choice of `standard`, `english` or `custom`
- `SIMILARITY` is a choice of `vsm`, `classic` or  `bm25`
- `MAX_RESULTS` is the maximum number of results you want a query to return, e.g. `10`, `100` or `1000`. A higher number results in a higher accuracy but a longer runtime.

## Prerequisites
- Maven and Java are installed on your system
- trec_eval is contained in a folder directly within this repository and has been built. trec_eval can be found [here](https://github.com/usnistgov/trec_eval).