package com.sustech.cs307.project2.shenzhenmetro;

import com.sustech.cs307.project2.shenzhenmetro.object.LineDetail;
import com.sustech.cs307.project2.shenzhenmetro.object.Station;
import com.sustech.cs307.project2.shenzhenmetro.repository.LineDetailRepository;
import com.sustech.cs307.project2.shenzhenmetro.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Temp implements CommandLineRunner {
    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private LineDetailRepository lineDetailRepository;

    private class LineDetailDirectedGraph {
        private Node[] nodes;
        private ArrayList<ArrayList<Edge>> adjacencyList;

        private LineDetailDirectedGraph() {
            initGraph();
        }

        private void initGraph() {
           List<Station> stations = stationRepository.findAll();
           nodes = new Node[stations.size()];
           for (int i = 0; i < nodes.length; i++) {
               nodes[i] = new Node(stations.get(i).getEnglishName());
           }

           List<LineDetail> lineDetails = lineDetailRepository.findAll();
           adjacencyList = new ArrayList<>(nodes.length);
           for (LineDetail lineDetail : lineDetails) {
               Edge edge = new Edge(lineDetail.getLineName());

           }
        }

        private class Node {
            private String station;

            private Node(String station) {
                this.station = station;
            }

            @Override
            public String toString() {
                return String.format("%s", station);
            }
        }

        private class Edge {
            private String line;

            public Edge(String line) {
                this.line = line;
            }

            @Override
            public String toString() {
                return String.format("%s", line);
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(Temp.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        findPaths("Luohu", "Linhai");
    }

    public void findPaths(String stationName1, String stationName2) {
        LineDetailDirectedGraph graph = new LineDetailDirectedGraph();
//        List<LineDetail> lineDetails = lineDetailRepository.findAllOrderByLineNumberAndStationOrder();
//        for (LineDetail lineDetail : lineDetails) {
//            System.out.println(lineDetail.getStationName());
//        }
    }
}
