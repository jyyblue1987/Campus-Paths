package pathfinder.junitTests;

import graph.DirectedLabeledGraph;
import org.junit.Test;
import pathfinder.parser.CampusPath;

public class dijkstraTest {

    @Test(expected = AssertionError.class)
    public void dijkstraTestInvalidEdge() {
        DirectedLabeledGraph<String,Double> graph = new DirectedLabeledGraph<>();
        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("A","B",-50.0);
        CampusPath.minPath(graph,"A","B");
    }

    @Test(expected = AssertionError.class)
    public void dijkstraTestNullNode() {
        DirectedLabeledGraph<String,Double> graph = new DirectedLabeledGraph<>();
        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("A","B",50.0);
        CampusPath.minPath(graph,null,"B");
    }

}
