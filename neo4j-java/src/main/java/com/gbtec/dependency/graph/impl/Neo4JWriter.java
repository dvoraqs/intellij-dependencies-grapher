package com.gbtec.dependency.graph.impl;

import java.util.List;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.DynamicRelationshipType;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.Iterables;
import org.neo4j.tooling.GlobalGraphOperations;

import com.gbtec.dependency.graph.Dependency;
import com.gbtec.dependency.graph.GraphWriter;

public class Neo4JWriter implements GraphWriter {

    private GraphDatabaseService graphDb = null;

    static Label Label = DynamicLabel.label("maven-artifact");

    public Neo4JWriter(GraphDatabaseService graphDb) {
        this.graphDb = graphDb;
    }

    /*
     * (non-Javadoc)
     * @see com.gbtec.dependency.graph.GraphWriter#write(java.util.List)
     */
    @Override
    public void createRelatedNodes(List<Dependency> dependencies) {
    	
    	Transaction tx = graphDb.beginTx();

        for (Dependency d : dependencies) {
            Node from = findOrCreateNode(d.from());
            Node to = findOrCreateNode(d.to());

            createRelationShip(from, to);
            System.out.println("write: " + from + " " + to);
        }
        
        
        Iterable<Node> allNodes = GlobalGraphOperations.at(graphDb)
				.getAllNodes();

		Iterable<Relationship> allRelationships = GlobalGraphOperations.at(
				graphDb).getAllRelationships();

		System.out.println(String.format(
				"'%s' Items related by '%s' relationships.",
				Iterables.count(allNodes), Iterables.count(allRelationships)));

        System.out.println("Shutting down...");
		
		tx.success();
		tx.close();
    }

    /**
     * 
     * Creates a node if it does not yet exist. If a node with the given name
     * already exists in database, no new node will created.
     * 
     * @param nodeName
     *            The name of the node to create
     * @return A node object with the given name.
     */
    private Node findOrCreateNode(String nodeName) {
        ResourceIterable<Node> nodes = findNodeByName(nodeName);
        Node node;
        if (nodes.iterator().hasNext()) {
            node = nodes.iterator().next();
            nodes.iterator().close();
        } else {
            node = createNode(nodeName);
        }
        return node;
    }

    /**
     * Finds all nodes with given name.
     * 
     * @param name
     *            The name of a node to lookup
     * @return A {@link ResourceIterable} containing all matched nodes
     */
    private ResourceIterable<Node> findNodeByName(String name) {
        return graphDb.findNodesByLabelAndProperty(Label, "name", name);
    }

    /**
     * Creates a new node with name.
     * 
     * @param name
     *            The name of the node
     * @return The newly created node
     */
    private Node createNode(String name) {
        Node node = graphDb.createNode(Label);
        node.setProperty("name", name);
        return node;
    }

    /**
     * Creates a repository named "dependsOn"
     * 
     * @param from
     *            The node object where the association starts
     * @param to
     *            The node object where the association ends
     * @return The relationship object
     */
    private Relationship createRelationShip(Node from, Node to) {
        return from.createRelationshipTo(to,
                DynamicRelationshipType.withName("dependsOn"));
    }
}
