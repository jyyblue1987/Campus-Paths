package graph;


/**
 * <b>Edge</b> represents an immutable edge with a label and the child node its pointing to
 *
 */

public class Edge<N,E> {

    // Abstraction function:
    // Edge e contains the start and end node of an edge, along with its label

    // Rep invariant:
    // end != null && label != null

    private final N start;
    private final N end;
    private final E label;

    /**
     * @param start: start of edge
     * @param end: end of edge
     * @param label: label of edge
     * @spec.requires start != null and end != null and label != null
     * @spec.effects Constructs an Edge with its label along with the parent and child node it points to
     */
    public Edge(N start, N end, E label) {
        this.start = start;
        this.end = end;
        this.label = label;
        checkRep();
    }

    /**
     *
     * @return the label of the edge
     */
    public E getLabel() {
        checkRep();
        return this.label;
    }

    /**
     *
     * @return the end node of the edge
     */
    public N getEnd() {
        checkRep();
        return this.end;
    }

    /**
     *
     * @return the start node of the edge
     */
    public N getStart() {
        return this.start;
    }

    /**
     * Standard equality operation.
     *
     * @return true if and only if 'obj' is an instance of an Edge and 'this' and 'obj' represent
     * the same Edge.
     */
    public boolean equals(Object obj){
        if(obj instanceof Edge<?,?>) {
            Edge<?,?> e = (Edge<?,?>) obj;
            checkRep();
            return start.equals(e.start) && end.equals(e.end) && label.equals(e.label);
        }
        checkRep();
        return false;
    }

    /**
     * Standard hashCode function.
     *
     * @return an int that all objects equal to this will also return
     */
    public int hashCode(){
        checkRep();
        return start.hashCode() + end.hashCode() + label.hashCode();
    }

    /**
     * Returns a string representation of this Edge
     *
     * @return the string representation of this Edge
     */
    public java.lang.String toString(){
        checkRep();
        return start + ", label: " + label + ", " + end;
    }

    private void checkRep() {
        assert(!start.equals(null));
        assert(!end.equals(null));
        assert(!label.equals(null));
    }
}
