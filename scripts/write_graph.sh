INPUT_FILE=${1:-"result.txt"}
OUTPUT_FILE=${2:-"~/Documents/Neo4j/dependencies.graphdb"}
JAR_FILE="neo4j-java/target/dependency-graph-0.0.1-SNAPSHOT.jar"

rm -rf $OUTPUT_FILE

java -jar $JAR_FILE $INPUT_FILE $OUTPUT_FILE | grep 'relationships'

cd neo4j-java
mvn clean | grep 'SUCCESS|FAILURE'
cd ..