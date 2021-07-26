package com.company.model.graph;

import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;
import java.util.NoSuchElementException;

public final class RTreeAdapter {
    private RTree<String, Point> tree;
    private final static double MAX_DISTANCE = 0.002;//200 meters

    private RTreeAdapter(){
        //as said in documentation, it is the most optimized way to create a tree for >10 000 elements
        tree = RTree.star().maxChildren(4).create();
    }

    public static RTreeAdapter create(){
        return new RTreeAdapter();
    }

    public void add(String name, double lon, double lat){
        tree = tree.add(name, Geometries.pointGeographic(lon, lat));
    }

    public void remove(String name, double lon, double lat){
        tree = tree.delete(name, Geometries.pointGeographic(lon, lat));
    }

    /**
     * @return nearest vertex name or null, if vertex if more than 200 meters away
     */
    public String getNearestVertexName(double lon, double lat){
        try {
            return tree
                    .nearest(Geometries.pointGeographic(lon, lat), MAX_DISTANCE, 1)
                    .toBlocking()
                    .single()
                    .value();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Saves picture of tree
     * @param path - where to save
     */
    public void visualize(String path){
        tree.visualize(1920, 1080).save(path);
    }

    public int size(){
        return tree.size();
    }
}
