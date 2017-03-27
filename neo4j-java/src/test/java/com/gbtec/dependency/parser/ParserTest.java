package com.gbtec.dependency.parser;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.gbtec.dependency.graph.Dependency;

public class ParserTest {

	List<String> data = Arrays.<String> asList("digraph \"anything\" {",
			"\"anything\" -> \"something1\" ;",
			"\"anything\" -> \"something2\" ;", "}");

	@Test
	public void filterFirstAndLastElement() {
		Parser parser = new Parser();
		List<Dependency> actual = parser.parse(data);
		assertThat(actual.size(), equalTo(data.size() - 2));
	}

	@Test
	public void testName() throws Exception {
		Parser parser = new Parser();
		String[] actual = parser.split("\"anything\" -> \"something\"");
		assertThat(Arrays.asList(actual), contains("anything", "something"));
	}
	
	@Test
	public void depenenciesMustBeExtracted() {
		Parser parser = new Parser();
		List<Dependency> actual = parser.parse(data);

		assertThat(
				actual,
				contains(new Dependency("anything", "something1"),
						new Dependency("anything", "something2")));
	}

}
