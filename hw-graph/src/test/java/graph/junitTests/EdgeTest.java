package graph.junitTests;

import graph.Edge;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class EdgeTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Test
    public void testGetLabel() {
        Edge<String, String> edge = new Edge<String, String>("s","a", "b");
        assertEquals("b", edge.getLabel());
    }

    @Test
    public void testGetEnd() {
        Edge<String, String> edge = new Edge<String, String>("s","a", "b");
        assertEquals("a", edge.getEnd());
    }

    @Test
    public void testGetStart() {
        Edge<String, String> edge = new Edge<String, String>("s", "a", "b");
        assertEquals("s", edge.getStart());
    }

    @Test
    public void testEqual() {
        Edge<String, String> edge = new Edge<String, String>("s", "a", "b");
        Edge<String, String> edge1 = new Edge<String, String>("s","a", "b");
        assertTrue(edge.equals(edge1));
    }

    @Test
    public void testNotEqual() {
        Edge<String, String> edge = new Edge<String, String>("s","a", "b");
        Edge<String, String> edge1 = new Edge<String, String>("s","b", "c");
        assertFalse(edge.equals(edge1));
    }
}
