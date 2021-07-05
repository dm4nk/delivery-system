package com.company.Mains;

import com.company.Exceptions.WrongGraphFormatException;
import com.company.model.graph.Graph;
import com.company.model.schedules.Order;
import com.github.davidmoten.rtree.Entry;
import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

import rx.Observable;

import java.util.List;

public class MainTemp {
    public static void main(String[] args) throws WrongGraphFormatException {

        RTree<String, Point> tree = RTree.star().create();
        //tree = tree.add("1", Geometries.pointGeographic(53.243112, 50.210236));
        tree = tree.add("2", Geometries.pointGeographic(53.260800, 50.215452));
        tree = tree.add("3", Geometries.pointGeographic(53.250889, 50.222729));

        List<Entry<String, Point>> list = tree.search(Geometries.pointGeographic(53.243112, 50.210236), 0.01).toList().toBlocking().single();

        for(Entry<String, Point> t: list){
            System.out.println(t.value() + ": " + t.geometry().x() + ", " + t.geometry().y());
        }

        Graph gr = Graph.getInstance();
        gr.addVertex("1", -1, -1);
        gr.addVertex("2", 1, 1);
        gr.addVertex("3", -0.7, -0.5);

        Order or = new Order(null, -0.8, -0.3);
        System.out.println(or.calculateNearestVertex(gr).getName());
    }
}