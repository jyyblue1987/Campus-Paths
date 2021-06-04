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

package pathfinder.scriptTestRunner;

import graph.DirectedLabeledGraph;
import graph.Edge;
import marvel.MarvelPaths;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusPath;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    private final Map<String, DirectedLabeledGraph<String, Double>> graphs = new HashMap<String, DirectedLabeledGraph<String, Double>>();
    private final PrintWriter output;
    private final BufferedReader input;

    // Leave this constructor public
    public PathfinderTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException {
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if(st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while(st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        DirectedLabeledGraph<String,Double> g = new DirectedLabeledGraph<String,Double>();
        graphs.put(graphName, g);
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        DirectedLabeledGraph<String,Double> g = graphs.get(graphName);
        if (g != null) {
            g.addNode(nodeName);
        }
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String weight = arguments.get(3);
        double edgeLabel = Double.parseDouble(weight);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         double edgeLabel) {
        DirectedLabeledGraph<String,Double> g = graphs.get(graphName);
        g.addEdge(parentName, childName, edgeLabel);
        output.println("added edge " + String.format("%.3f",edgeLabel) + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        DirectedLabeledGraph<String,Double> g = graphs.get(graphName);
        List<String> sort = new ArrayList<>(g.getAllNodes());
        Collections.sort(sort);
        String result = graphName + " contains:";
        for(String node : sort) {
            result += " " + node;
        }
        output.println(result);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        DirectedLabeledGraph<String,Double> g = graphs.get(graphName);
        List<Edge<String, Double>> sort = new ArrayList<>();
        sort.addAll(g.getEdges(parentName));
        Collections.sort(sort, Comparator.comparing(edge -> edge.getEnd()+edge.getLabel()));
        String result = "the children of " + parentName + " in " + graphName + " are:";
        for(Edge<String, Double> e : sort) {
            result += " " + e.getEnd() + "(" + String.format("%.3f",e.getLabel()) + ")";
        }
        output.println(result);
    }

    private void findPath(List<String> arguments) {
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to findPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String start = arguments.get(1);
        String end = arguments.get(2);
        findPath(graphName, start, end);
    }

    private void findPath(String graphName, String start, String end) {
        DirectedLabeledGraph<String,Double> g = graphs.get(graphName);

        if ((!g.containsNode(start)) && (!g.containsNode(end))) {
            output.println("unknown: " + start);
            output.println("unknown: " + end);
        } else if ((!g.containsNode(start))) {
            output.println("unknown: " + start);
        } else if (!(g.containsNode(end))) {
            output.println("unknown: " + end);
        } else {
            String temp = start;
            String result = "path from " + start + " to " + end + ":";
            Path<String> path = CampusPath.minPath(g, start, end);
            if(start.equals(end)){
                result+="";
            }else if (path == null) {
                result += "\n" + "no path found";
            } else {
                double totalCost = 0.0;
                for (Path<String>.Segment edge : path) {
                    result += "\n" + temp + " to " + edge.getEnd() +
                            " with weight " + String.format("%.3f",(edge.getCost()));
                    totalCost += edge.getCost();
                    temp = edge.getEnd();
                }
                result += "\n" + "total cost: " + String.format("%.3f", totalCost);
            }

            output.println(result);
        }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
