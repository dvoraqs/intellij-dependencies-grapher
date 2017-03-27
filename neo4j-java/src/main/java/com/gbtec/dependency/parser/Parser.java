package com.gbtec.dependency.parser;

import java.util.List;
import java.util.stream.Collectors;

import com.gbtec.dependency.graph.Dependency;

public class Parser {

	public List<Dependency> parse(List<String> data) {
		return data.stream().filter(a -> a.contains("->"))
				.map(a -> extractDependency(a)).collect(Collectors.toList());
	}

	private Dependency extractDependency(String a) {
		String[] split = split(a);
		return new Dependency(split[0], split[1]);
	}

	String[] split(String string) {
		return string.replaceAll("\"|;| ", "").split("->");
	}
}
