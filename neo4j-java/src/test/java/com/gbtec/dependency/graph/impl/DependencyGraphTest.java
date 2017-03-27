package com.gbtec.dependency.graph.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;
import org.neo4j.helpers.collection.Iterables;
import org.neo4j.test.TestGraphDatabaseFactory;
import org.neo4j.tooling.GlobalGraphOperations;

import com.gbtec.dependency.graph.Dependency;
import com.gbtec.dependency.graph.GraphWriter;

public class DependencyGraphTest {

	static GraphDatabaseService graphDb;
	
	Transaction tx;
	GraphWriter w;

	List<Dependency> dependencies;

	@BeforeClass
	public static void prepareTestDatabase() {
		graphDb = new TestGraphDatabaseFactory().newImpermanentDatabase();
	}

	@AfterClass
	public static void destroyTestDatabase() {
		graphDb.shutdown();
	}

	@Before
	public void setUp() throws Exception {
		w = new Neo4JWriter(graphDb);
		tx = graphDb.beginTx();
		dependencies = new ArrayList<Dependency>();
	}

	@After
	public void tearDown() throws Exception {
		tx.close();
	}

	private int countNodes() {
		Iterable<Node> allNodes = GlobalGraphOperations.at(graphDb)
				.getAllNodes();
		return (int) Iterables.count(allNodes);
	}

	private Relationship findFirstRelationshipOfNode(String key) {
		ResourceIterable<Node> nodes = graphDb.findNodesByLabelAndProperty(
				Neo4JWriter.Label, "name", key);
		Node from = nodes.iterator().next();
		Relationship relationship = from.getRelationships().iterator().next();
		return relationship;
	}

	@Test
	public void oneDependencyMustBeAddedAsTwoNodes() throws Exception {
		dependencies.add(new Dependency("1", "2"));

		w.createRelatedNodes(dependencies);

		assertThat(countNodes(), equalTo(2));
	}

	@Test
	public void twoDependenciesHavingSameParentMustBeAddedAsThreeNodes()
			throws Exception {
		dependencies.add(new Dependency("1", "2"));
		dependencies.add(new Dependency("1", "3"));

		w.createRelatedNodes(dependencies);

		assertThat(countNodes(), equalTo(3));
	}

	@Test
	public void nodesMustHaveReleationship() throws Exception {
		dependencies.add(new Dependency("from", "to"));

		w.createRelatedNodes(dependencies);

		Relationship relationship = findFirstRelationshipOfNode("from");

		assertEquals(relationship.getStartNode().getProperty("name"), "from");
		assertEquals(relationship.getEndNode().getProperty("name"), "to");
	}

}
