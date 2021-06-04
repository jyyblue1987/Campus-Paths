/*
 * Copyright (C) 2021 Kevin Zatloukal.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Spring Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.parser;

import graph.DirectedLabeledGraph;
import graph.Edge;
import pathfinder.datastructures.Path;

import java.util.*;

/**
 * This represents one immutable entry of the data in campus_paths.csv,
 */
public class CampusPath {

    /**
     * The pixel-x coordinate of the first point in this path segment.
     */
    private final double x1;

    /**
     * The pixel-y coordinate of the first point in this path segment.
     */
    private final double y1;

    /**
     * The pixel-x coordinate of the second point in this path segment.
     */
    private final double x2;

    /**
     * The pixel-y coordinate of the second point in this path segment.
     */
    private final double y2;

    /**
     * The distance between the points as described in the dataset.
     */
    private final double distance;

    /**
     * Creates a new immutable CampusPath entry containing the provided data.
     *
     * @param x1       The pixel-x coordinate of the first point
     * @param y1       The pixel-y coordinate of the first point
     * @param x2       The pixel-x coordinate of the second point
     * @param y2       The pixel-y coordinate of the second point
     * @param distance The distance between the points as described in the dataset
     */
    public CampusPath(double x1, double y1, double x2, double y2, double distance) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.distance = distance;
    }

    /**
     * @return The pixel-x coordinate of the first point in this path segment.
     */
    public double getX1() {
        return x1;
    }

    /**
     * @return The pixel-y coordinate of the first point in this path segment.
     */
    public double getY1() {
        return y1;
    }

    /**
     * @return The pixel-x coordinate of the second point in this path segment.
     */
    public double getX2() {
        return x2;
    }

    /**
     * @return The pixel-y coordinate of the second point in this path segment.
     */
    public double getY2() {
        return y2;
    }

    /**
     * @return The distance between the points as described in the dataset.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Dijkstra's Algorithm to find the minimum cost path
     *
     * @spec.requires Edge weights must be non-negative
     * @param <N> generics
     * @param graph the graph to be analyzed
     * @param start the starting node of the path we want to find
     * @param end the ending node of the path we want to find
     * @return The minimum cost path from the start to end node
     */
    public static <N> Path<N> minPath(DirectedLabeledGraph<N,Double> graph, N start, N end) {
        Queue<Path<N>> active = new PriorityQueue<Path<N>>(15, new Comparator<Path<N>>() {
            @Override
            public int compare(Path<N> o1, Path<N> o2) {
                double w1 = 0;
                double w2 = 0;
                int result = Double.compare(w1, w2);
                if (result == 0) {
                    result = Double.compare(o1.getCost(), o2.getCost());
                }
                return result;
            }
        });
        // set of nodes for which we know the min-cost path from start
        Set<N> finished = new HashSet<>();

        // Initially we only know of the path from start to itself, which has
        // a cost of zero because it contains no edges.
        Path<N> startStart = new Path<>(start);
        active.add(startStart);

        while(!active.isEmpty()) {
            // minPath is the lowest-cost path in active and,
            // if minDest isn't already 'finished,' is the
            // minimum-cost path to the node minDest
            Path<N> minPath = active.remove();
            N minDest = minPath.getEnd();

            if(minDest.equals(end)) {
                return minPath;
            }

            if(finished.contains(minDest)) {
                continue;
            }

            for(Edge<N,Double> edge:graph.getEdges(minDest)) {
                // for all children of minDest, if we don't know the min-cost path from start to child,
                // examine the path we've just found
                if(!finished.contains(edge.getEnd())) {
                    Path<N> newPath = minPath.extend(edge.getEnd(), edge.getLabel().doubleValue());
                    active.add(newPath);
                }
            }

            finished.add(minDest);
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("[Path (%.3f, %.3f) -> (%.3f, %.3f); Distance: %.3f]",
                             x1, y1, x2, y2, distance);
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) {
            return true;
        }
        if(!(other instanceof CampusPath)) {
            return false;
        }
        if(this.hashCode() != other.hashCode()) {
            // Equal objects must have equal hashCodes, according
            // to the hashCode spec, so if their hashCodes aren't equal,
            // the object's can't be equal.
            return false;
        }
        CampusPath that = (CampusPath) other;
        return (Double.compare(this.x1, that.x1) == 0)
               && (Double.compare(this.y1, that.y1) == 0)
               && (Double.compare(this.x2, that.x2) == 0)
               && (Double.compare(this.y2, that.y2) == 0)
               && (Double.compare(this.distance, that.distance) == 0);
    }

    @Override
    public int hashCode() {
        int result = Double.hashCode(this.distance);
        result = (31 * result) + Double.hashCode(this.x1);
        result = (31 * result) + Double.hashCode(this.y1);
        result = (31 * result) + Double.hashCode(this.x2);
        result = (31 * result) + Double.hashCode(this.y2);
        return result;
    }
}
