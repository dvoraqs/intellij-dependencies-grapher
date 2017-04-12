rm -rf neo4j-java
git clone git@github.com:dvoraqs/dependency-graph.git neo4j-java 

cd neo4j-java
git checkout print
mvn clean install | grep 'FAILURE'
cd ..