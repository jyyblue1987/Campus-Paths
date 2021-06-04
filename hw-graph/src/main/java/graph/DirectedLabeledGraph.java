package graph;

import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * <b>DirectedLabeledGraph</b> represents a mutable directed labeled graph.
 *
 */

public class DirectedLabeledGraph<N,E> {

    // Abstraction Function:
    // A DirectedLabeledGraph g represents a graph that contains the nodes and their
    // corresponding edges. The nodes {n1, n2, ..., nk} are stored in a HashMap as key
    // and each of the node maps to a HashSet that contains their end nodes and the
    // edges label. If a particular node does not contain any outgoing edge, the HashSet
    // mapped from it will be empty.

    // Rep Invariants:
    // g != null && node != null && edge != null

    private final HashMap<N, HashSet<Edge<N,E>>> graph;

    /**
     * @spec.effects Constructs an empty directed labeled graph
     */
    public DirectedLabeledGraph() {
        graph = new HashMap<N, HashSet<Edge<N,E>>>();
        //checkRep();
    }

    /**
     * Add a new node to the graph
     *
     * @param node to be added
     * @spec.modifies this
     * @spec.requires node != null
     * @spec.effects add a new node to the graph
     * @return true if the node does not exist in the graph and is successfully added,
     * false if the node already exist in the graph and cannot be added.
     *
     */
    public boolean addNode(N node) {
        //checkRep();
        if(!containsNode(node)) {
            graph.put(node, new HashSet<Edge<N,E>>());
            //checkRep();
            return true;
        }
        else {
            //checkRep();
            return false;
        }
    }

    /**
     * Add an edge to the graph
     *
     * @param start: start of the edge
     * @param end: end of the edge
     * @param label: label of the edge
     * @spec.modifies this
     * @spec.requires start != null and end != null and label != null and containsNode(start) and containsNode(end)
     * @spec.effects add an edge to the graph
     * @return true if edge does not exist in the graph and is successfully added,
     * false if the edge already exist in the graph and cannot be added
     *
     */
    public boolean addEdge(N start, N end, E label) {
        //checkRep();
        Edge<N,E> edge = new Edge<N,E>(start, end, label);
        if(graph.containsKey(start) && graph.containsKey(end) && !graph.get(end).contains(edge.getLabel())) {
            graph.get(start).add(edge);
            //checkRep();
            return true;
        }
        else {
            //checkRep();
            return false;
        }
    }

    /**
     * Check if the graph contains the node
     *
     * @param node: node to be checked if it exists in the graph
     * @spec.requires node != null
     * @return true if node exists in the graph, false if node does not exist in the graph
     *
     */
    public boolean containsNode(N node) {
        //checkRep();
        if(graph.containsKey(node)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Check if the graph contains the edge
     *
     * @param start: start of the edge
     * @param end: end of the edge
     * @param label: label of the edge
     * @spec.requires start != null and end != null and label != null
     * @return true if edge exists in the graph, false if edge does not exist in the graph
     *
     */
    public boolean containsEdge(N start, N end, E label) {
        //checkRep();
        Edge<N,E> edge = new Edge<N,E>(start, end, label);
        if(containsNode(start) && containsNode(end) && getEdges(start).contains(edge)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Check if the graph is empty
     *
     * @return true if graph is empty
     *
     */
    public boolean isEmpty() {
        //checkRep();
        return graph.isEmpty();
    }

    /**
     * Return the size of the graph
     *
     * @spec.requires graph != null
     * @return the size of the graph
     *
     */
    public int size(){
        //checkRep();
        return graph.size();
    }

    /**
     * Remove a node
     *
     * @param node: node to be removed from the graph
     * @spec.modifies graph
     * @spec.requires node != null
     * @spec.effects removes node from the graph, together with the edges that are bind to it
     * @return true if node exists in the graph and is successfully removed, false if node
     * does not exist in the graph
     *
     */
    public boolean removeNode(N node){
        //checkRep();
        if(containsNode(node)) {
            for(int i = 0; i < graph.size(); i++) {
                HashSet<Edge<N,E>> edges = new HashSet<Edge<N,E>>();
                for(Edge<N,E> e : graph.get(node)) {
                    if(e.getStart().equals(node)) {
                        edges.add(e);
                    }
                }
                for(Edge<N,E> removedEdges : edges) {
                    graph.get(node).remove(removedEdges);
                }
            }
            graph.remove(node);
            //checkRep();
            return true;
        }
        else {
            //checkRep();
            return false;
        }
    }

    /**
     * Remove a node
     *
     * @param start: start of the edge
     * @param end: end of the edge
     * @param label: label of the edge
     * @spec.modifies graph
     * @spec.requires start != null and end != null and label != null and containsNode(start) and containsNode(end)
     * @spec.effects removes edge from the graph
     * @return true if edge exists in the graph and is successfully removed, false if edge
     * does not exist in the graph
     *
     */
    public boolean removeEdge(N start, N end, E label){
        //checkRep();
        Edge<N,E> edge = new Edge<N,E>(start, end, label);
        if(graph.get(start).contains(edge)) {
            graph.get(start).remove(edge);
            //checkRep();
            return true;
        }
        else {
            //checkRep();
            return false;
        }
    }

    /**
     * Get all the nodes in the graph without modifying it
     *
     * @return all the nodes in the graph
     */
    public Set<N> getAllNodes(){
        //checkRep();
        return new HashSet<N>(graph.keySet());
    }

    /**
     * Get all the edges that points out from the specified node
     *
     * @param node: node from which we want to get the edges
     * @spec.requires node != null and containsNode(n)
     * @return all the edges that points out from the node
     */
    public Set<Edge<N,E>> getEdges(N node){
        //checkRep();
        HashSet<Edge<N,E>> edges = new HashSet<>();
        if(containsNode(node)) {
            for(Edge<N,E> e : graph.get(node)) {
                edges.add(e);
            }
        }
        return edges;
    }

    /**
     * Get all the child nodes of the specified node
     *
     * @param node: node from which we want to get the child nodes
     * @spec.requires node != null and containsNode(n)
     * @return all the child nodes of the specified node
     */
    public Set<N> getChildrenNodes(N node){
        //checkRep();
        Set<Edge<N,E>> edges = getEdges(node);
        HashSet<N> children = new HashSet<>();
        for(Edge<N,E> end : edges) {
            children.add(end.getEnd());
        }
        return children;
    }

    @Override
    /**
     * Standard equality operation.
     *
     * @return true if and only if 'obj' is an instance of a DirectedLabeledGraph and 'this' and 'obj' represent
     * the same DirectedLabeledGraph.
     */
    public boolean equals(Object obj){
        if(obj instanceof DirectedLabeledGraph) {
            DirectedLabeledGraph<?,?> g = (DirectedLabeledGraph<?,?>) obj;
            //checkRep();
            return graph.equals(g.graph);
        }
        //checkRep();
        return false;
    }

    @Override
    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also return
     */
    public int hashCode(){
        //checkRep();
        return graph.hashCode();
    }

    /**
     * Returns a spring representation of the graph
     *
     * @return the spring representation of the graph
     */
    public String toString(){
        //checkRep();
        return graph.toString();
    }

    /*
    private void checkRep() {
        for(N node: graph.keySet()) {
            assert(node != null);
            for(Edge<N,E> e:graph.get(node)) {
                assert(e != null);
                assert(graph.containsKey(e.getEnd()));
            }
        }
    }

     */
}
