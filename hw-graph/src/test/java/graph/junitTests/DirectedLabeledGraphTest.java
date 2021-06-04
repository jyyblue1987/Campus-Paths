package graph.junitTests;

import graph.DirectedLabeledGraph;
import org.junit.Test;
import java.util.HashSet;
import org.junit.Rule;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class DirectedLabeledGraphTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Test
    public void testConstructor() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        assertTrue(graph != null);
    }

    @Test
    public void testConstructorIsEmpty() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        assertTrue(graph.isEmpty());
    }

    @Test
    public void testGraphConstructorSize() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        assertEquals(0, graph.size());
    }

    @Test
    public void testSizeAfterAddingOneNode() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        assertEquals(1, graph.size());
    }

    @Test
    public void testSizeAfterAddingTwoNodes() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        assertEquals(2, graph.size());
    }

    @Test
    public void testGetNodesAfterAddingTwoNodes() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        HashSet<String> nodes = new HashSet<String>();
        nodes.add("a");
        nodes.add("b");
        assertEquals(nodes, graph.getAllNodes());
    }

    @Test
    public void testContainsNode() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        assertTrue(graph.containsNode("a"));
    }

    @Test
    public void testContainsEdgeTrue() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addEdge("a", "b", "hello");
        assertTrue(graph.containsEdge("a", "b", "hello"));
    }

    @Test
    public void testContainsEdgeWrongStart() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addEdge("x", "b", "hello");
        assertFalse(graph.containsEdge("x", "b", "hello"));
    }

    @Test
    public void testContainsEdgeWrongEnd() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addEdge("a", "z", "hello");
        assertFalse(graph.containsEdge("a", "z", "hello"));
    }

    @Test
    public void testRemoveOneNode() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.removeNode("a");
        assertFalse(graph.containsNode("a"));
    }

    @Test
    public void testRemoveTwoNode() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.removeNode("a");
        graph.removeNode("b");
        assertFalse(graph.containsNode("a"));
        assertFalse(graph.containsNode("b"));
    }

    @Test
    public void testRemoveEdge() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addEdge("a", "b", "hello");
        graph.addEdge("a", "b", "bye");
        graph.removeEdge("a", "b", "bye");
        assertFalse(graph.containsEdge("a", "b", "bye"));
        assertTrue(graph.containsEdge("a", "b", "hello"));
    }

    @Test
    public void testGetEdgesPointedOutFromNode() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        graph.addNode("d");
        graph.addEdge("a", "b", "hello");
        graph.addEdge("a", "c", "bye");
        assertEquals(2, graph.getEdges("a").size());
    }

    @Test
    public void testGetEdgesWhenEmpty() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("hello");
        assertEquals(0, graph.getEdges("a").size());
    }

    @Test
    public void testGetChildrenNodes() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        HashSet<String> children = new HashSet<String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addNode("c");
        graph.addEdge("a", "b", "hello");
        graph.addEdge("a", "a", "bye");
        graph.addEdge("a", "c", "okay");
        children.add("b");
        children.add("a");
        children.add("c");
        assertEquals(children, graph.getChildrenNodes("a"));
    }

    @Test
    public void testGetChildrenAfterRemovingNode() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.addEdge("a", "b", "hello");
        graph.removeNode("a");
        assertTrue(graph.getEdges("a").isEmpty());
    }

    @Test
    public void testIsEmptyAfterAdd() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        assertFalse(graph.isEmpty());
    }

    @Test
    public void testIsEmptyAfterNotFullRemove() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        graph.removeNode("a");
        assertFalse(graph.isEmpty());
    }

    @Test
    public void testNotContainNodeAfterAdd() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        assertFalse(graph.containsNode("b"));
    }

    @Test
    public void testChildrenNodeWithNoEdge() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        assertTrue(graph.getChildrenNodes("a").isEmpty());
    }

    @Test
    public void testGetEdgesWithNoEdge() {
        DirectedLabeledGraph<String, String> graph = new DirectedLabeledGraph<String, String>();
        graph.addNode("a");
        graph.addNode("b");
        assertTrue(graph.getEdges("a").isEmpty());
    }
}
