package com.gbtec.dependency.graph;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import com.gbtec.dependency.graph.impl.Neo4JWriter;
import com.gbtec.dependency.parser.Parser;

public class DependencyGraph {

	public static void main(String[] args) {

		String DB_PATH = args[1];

		
		deleteFileOrDirectory(new File(DB_PATH));

		GraphDatabaseService graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabase(DB_PATH);
		
		registerShutdownHook(graphDb);

		try {
			Parser parser = new Parser();

			List<String> lines = FileUtils.readLines(new File(
					args[0]));
					//"/Users/dvoraqs/Desktop/Experiments/shared_basic.txt"));
					//"src/main/resources/tree.dot.txt"));

			GraphWriter writer = new Neo4JWriter(graphDb);
			writer.createRelatedNodes(parser.parse(lines));

		} catch (IOException e) {
			System.out.println(e);
		}

		graphDb.shutdown();
		System.out.println("Shut down finished");
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		// Registers a shutdown hook for the Neo4j instance so that it
		// shuts down nicely when the VM exits (even if you "Ctrl-C" the
		// running application).
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	private static void deleteFileOrDirectory(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				for (File child : file.listFiles()) {
					deleteFileOrDirectory(child);
				}
			}
			file.delete();
		}
	}
}
