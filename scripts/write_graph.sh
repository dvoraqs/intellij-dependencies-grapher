INPUT_FILE=${1:-"result.txt"}
OUTPUT_FILE=${2:-"/Users/dvoraqs/Documents/Neo4j/shared.graphdb"}

rm -rf $OUTPUT_FILE

java -jar neo4j-java/target/dependency-graph-0.0.1-SNAPSHOT.jar $INPUT_FILE $OUTPUT_FILE | grep 'relationships'

cd neo4j-java
mvn clean | grep 'SUCCESS|FAILURE'
cd ..