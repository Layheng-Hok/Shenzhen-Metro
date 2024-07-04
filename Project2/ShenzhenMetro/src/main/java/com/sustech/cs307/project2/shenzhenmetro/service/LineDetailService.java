package com.sustech.cs307.project2.shenzhenmetro.service;

import com.sustech.cs307.project2.shenzhenmetro.object.LineDetail;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LineDetailService {
    private static class Graph {
        private Map<String, Map<String, Set<String>>> adjList;

        private Graph() {
            adjList = new HashMap<>();
        }

        private void addEdge(String station1, String station2, String lineName) {
            adjList.putIfAbsent(station1, new HashMap<>());
            adjList.get(station1).putIfAbsent(station2, new HashSet<>());
            adjList.get(station1).get(station2).add(lineName);
        }

        private List<List<String>> findAllPaths(String start, String end) {
            int maxDepth = 1;
            List<List<String>> paths = new ArrayList<>();
            do {
                List<String> path = new ArrayList<>();
                List<String> lines = new ArrayList<>();
                Set<String> visited = new HashSet<>();
                path.add(start);
                dfs(start, end, visited, path, lines, paths, maxDepth);
                maxDepth++;
            } while (paths.isEmpty() && maxDepth < 5);
            return paths;
        }

        private void dfs(String current, String end, Set<String> visited, List<String> path, List<String> lines, List<List<String>> paths, int maxDepth) {
            if (current.equals(end)) {
                List<String> pathWithLines = new ArrayList<>();
                for (int i = 0; i < path.size(); i++) {
                    if (i < lines.size()) {
                        pathWithLines.add(lines.get(i) + ": " + path.get(i) + " -> " + path.get(i + 1));
                        if (i < lines.size() - 1 && !lines.get(i).equals(lines.get(i + 1))) {
                            pathWithLines.add("Transit from " + lines.get(i) + " to " + lines.get(i + 1) + " at " + path.get(i + 1) + " station");
                        }
                    }
                }
                paths.add(pathWithLines);
                return;
            }

            if (path.size() > maxDepth) {
                return;
            }

            visited.add(current);
            for (Map.Entry<String, Set<String>> neighborEntry : adjList.getOrDefault(current, new HashMap<>()).entrySet()) {
                String neighbor = neighborEntry.getKey();
                for (String lineName : neighborEntry.getValue()) {
                    if (!visited.contains(neighbor)) {
                        path.add(neighbor);
                        lines.add(lineName);
                        dfs(neighbor, end, visited, path, lines, paths, maxDepth);
                        path.remove(path.size() - 1);
                        lines.remove(lines.size() - 1);
                    }
                }
            }
            visited.remove(current);
        }
    }

    public List<List<String>> findAllPaths(List<LineDetail> lineDetails, String start, String end) {
        Graph graph = new Graph();
        for (LineDetail lineDetail1 : lineDetails) {
            for (LineDetail lineDetail2 : lineDetails) {
                if (lineDetail1.getLineName().equals(lineDetail2.getLineName()) && !lineDetail1.getStationName().equals(lineDetail2.getStationName())) {
                    graph.addEdge(lineDetail1.getStationName(), lineDetail2.getStationName(), lineDetail1.getLineName());
                }
            }
        }
        return graph.findAllPaths(start, end);
    }
}
