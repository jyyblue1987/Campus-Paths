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

package campuspaths;

import campuspaths.utils.CORSFilter;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import com.google.gson.Gson;
import spark.Spark;

import java.util.Collection;
import java.util.Map;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().
        
        CampusMap graph = new CampusMap();

        Gson gson = new Gson();

        // Get the shortest path from a starting building to an ending building
        Spark.get("/findPath", (request, response) -> {
            String start = request.queryParams("start");
            String end = request.queryParams("end");
            Path<Point> shortestPath = graph.findShortestPath(start, end);
            return gson.toJson(shortestPath);
        });

        // Get a list of all the short names of the buildings
        Spark.get("/getBuildingNames", (request, response) -> {
            Map<String, String> buildings = graph.buildingNames();
            Collection<String> shortNames = buildings.keySet();
            return gson.toJson(shortNames);
        });
    }

}
