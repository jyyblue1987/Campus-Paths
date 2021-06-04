package marvel;

import graph.DirectedLabeledGraph;
import graph.Edge;

import java.io.IOException;
import java.util.*;

public class MarvelPaths {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        DirectedLabeledGraph<String,String> g = newGraph("marvel.csv");
        String answer = "yes";
        while(answer.equals("yes")) {
            System.out.println("Which marvel character would you like to start with?");
            System.out.println("Please type in full capital letters and use dash (-) instead of space in between names.");
            System.out.println("If they have different names, type them in alphabetical order and start with numbers if their name starts with a number.");
            System.out.println("Separate the names with a slash (/) and no space in between. Example: a24-HOUR-MAN/EMMANUEL");
            System.out.print("Type here: ");
            String start = in.nextLine();
            System.out.println();
            System.out.println("Which marvel character would you like to end with?");
            System.out.println("Please type in full capital letters and use dash (-) instead of space in between names: ");
            System.out.println("If they have different names, type them in alphabetical order and start with numbers if their name starts with a number.");
            System.out.println("Separate the names with a slash (/) and no space in between. Example: a24-HOUR-MAN/EMMANUEL");
            System.out.print("Type here: ");
            String end = in.nextLine();
            System.out.println();

            if((!g.containsNode(start)) && (!g.containsNode(end))) {
                System.out.println("The marvel characters you typed in does not exist.");
            }
            else if(!g.containsNode(start)) {
                System.out.println("The first marvel character you typed in does not exist.");
            }
            else if(!g.containsNode(end)) {
                System.out.println("The second marvel character you typed in does not exist.");
            }
            else {
                String temp = start;
                String result = "path from " + start + " to " + end + ":";
                ArrayList<Edge<String,String>> path = findShortestPath(start, end, g);
                if(start.equals(end)){
                    result+="";
                }else if (path.isEmpty()) {
                    result += "\n" + "no path found";
                } else {
                    for (Edge<String,String> edge : path) {
                        result += "\n" + temp + " to " + edge.getEnd() +
                                " via " + edge.getLabel();
                        temp = edge.getEnd();
                    }
                }
                System.out.println(result);
            }

            System.out.println("Would you like to find the shortest path between two Marvel characters again?");
            System.out.println("Type 'yes' or 'no' here: ");
            answer = in.nextLine();
        }
        System.out.println("Thank you for using this service.");
    }

    public static DirectedLabeledGraph<String,String> newGraph(String filename) throws IOException {
        DirectedLabeledGraph<String,String> marvelGraph = new DirectedLabeledGraph<String,String>();
        ArrayList<String> characterList = new ArrayList<>();
        HashMap<String, ArrayList<String>> marvelList = MarvelParser.parseData(filename, characterList);
        for(String character : characterList) {
            marvelGraph.addNode(character);
        }
        for(String book : marvelList.keySet()) {
            for(String startNode : marvelList.get(book)) {
                for(String endNode : marvelList.get(book)) {
                    if(!startNode.equals(endNode)) {
                        marvelGraph.addEdge(startNode, endNode, book);
                    }
                }
            }
        }
        return marvelGraph;
    }

    public static ArrayList<Edge<String,String>> findShortestPath(String start, String end, DirectedLabeledGraph<String,String> graph) {
        Queue<String> toVisit = new LinkedList<>();
        HashMap<String, ArrayList<Edge<String,String>>> paths = new HashMap<>();
        toVisit.add(start);
        paths.put(start, new ArrayList<>());
        while(!toVisit.isEmpty()) {
            String node = toVisit.remove();
            if(node.equals(end)) {
                return paths.get(end);
            }
            ArrayList<Edge<String,String>> edges = new ArrayList<>(graph.getEdges(node));
            Collections.sort(edges,Comparator.comparing(edge -> edge.getEnd()+edge.getLabel()));
            for(Edge<String,String> e : edges) {
                String endNode = e.getEnd();
                if(!paths.containsKey(endNode)) {
                    ArrayList<Edge<String,String>> path = new ArrayList<>(paths.get(node));
                    path.add(e);
                    paths.put(endNode, path);
                    toVisit.add(endNode);
                }
            }
        }
        return null;
    }

}
