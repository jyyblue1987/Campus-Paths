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

package pathfinder;

import graph.DirectedLabeledGraph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CampusMap implements ModelAPI {

    private DirectedLabeledGraph<Point,Double> graph;
    private Map<String,String> shortToLong;
    private Map<String,Point> shortToPoint;

    public CampusMap() {
        List<CampusBuilding> buildings = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        shortToLong = new HashMap<>();
        shortToPoint = new HashMap<>();
        for(CampusBuilding b : buildings) {
            if(b != null) {
                shortToLong.put(b.getShortName(), b.getLongName());
                shortToPoint.put(b.getShortName(), new Point(b.getX(),b.getY()));
            }
        }

        List<CampusPath> paths = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        graph = new DirectedLabeledGraph<>();
        for(CampusPath p : paths) {
            Point start = new Point(p.getX1(),p.getY1());
            Point end = new Point(p.getX2(),p.getY2());
            graph.addNode(start);
            graph.addNode(end);
            if(graph.containsNode(start) && graph.containsNode(end)) {
                graph.addEdge(start, end, p.getDistance());
                graph.addEdge(end, start, p.getDistance());
            }
        }
    }

    @Override
    public boolean shortNameExists(String shortName) {
        return buildingNames().containsKey(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        return buildingNames().get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        return Collections.unmodifiableMap(shortToLong);
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        if(startShortName == null || endShortName == null || !buildingNames().containsKey(startShortName) || !buildingNames().containsKey(endShortName)) {
            throw new IllegalArgumentException();
        }

        return CampusPath.minPath(graph, shortToPoint.get(startShortName),shortToPoint.get(endShortName));
    }

}
