package model.graph;

import com.github.davidmoten.rtree.RTree;
import com.github.davidmoten.rtree.geometry.Geometries;
import com.github.davidmoten.rtree.geometry.Point;

import java.util.NoSuchElementException;

/**
 * Adapts com.github.davidmoten.rtree.RTree for certain needs - finding nearest point
 */
public final class RTreeAdapter {
    private final static double MAX_DISTANCE = 0.002;//200 meters
    private RTree<Long, Point> tree;

    private RTreeAdapter() {
        //as said in documentation, it is the most optimized way to create a tree for >10 000 elements
        tree = RTree.star().maxChildren(4).create();
    }

    public static RTreeAdapter create() {
        return new RTreeAdapter();
    }

    public void add(long id, double lat, double lon) {
        tree = tree.add(id, Geometries.pointGeographic(lat, lon));
    }

    public void remove(long id, double lat, double lon) {
        tree = tree.delete(id, Geometries.pointGeographic(lat, lon));
    }

    public int size() {
        return tree.size();
    }

    /**
     * @return nearest vertex name or null, if vertex if more than 200 meters away
     */
    public Long getNearestVertexId(double lat, double lon) {
        try {
            return tree
                    .nearest(Geometries.pointGeographic(lat, lon), MAX_DISTANCE, 1)
                    .toBlocking()
                    .single()
                    .value();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * Saves picture of tree
     *
     * @param path where to save
     */
    public void visualize(String path) {
        tree.visualize(1920, 1080).save(path);
    }
}
