package com.gbtec.dependency.graph;

import java.util.List;

public interface GraphWriter {
    /**
     * Write the given list of {@link Dependency}s to graph.
     * 
     * @param dependencies
     *            {@link List} of {@link Dependency}s to write.
     */
    void createRelatedNodes(List<Dependency> dependencies);
}
