package com.company.someDijkstra.dijkstraForMatrix;

import com.company.Exceptions.wrongTaskFormatException;
import com.company.schedules.Task;

import java.util.*;

public abstract class basicDijkstraForMatrixGraph {

        //нахождение пути, нагло украденное с интернета
        private static double[] find(double[][] m, int start, Vector<Integer> pars){
            double[] ds = new double[m.length];
            Arrays.fill(ds, Double.MAX_VALUE);

            boolean[] visit = new boolean[m.length];
            pars.setSize(m.length);

            ds[start] = 0;
            int sel;
            for(int i = 0; i < m.length; ++i){
                sel = -1;
                for(int j = 0; j < m[i].length; ++j){
                    if(!visit[j] && (sel == -1 || ds[j] < ds[sel]))
                        sel = j;
                }

                if(ds[sel] == Double.MAX_VALUE)
                    break;
                visit[sel] = true;

                for(int j = 0; j < m[sel].length; ++j){
                    if(m[sel][j] == Double.MAX_VALUE)
                        continue;
                    if((ds[sel] + m[sel][j]) < ds[j]){
                        ds[j] = ds[sel] + m[sel][j];
                        pars.set(j, sel);
                    }
                }
            }
            return ds;
        }

        //если путь есть - сначала путь в обратом порядке - затем вес пути
        //если нет такого пути вообще - return null
        //если путь есть, но не хватает времени - сначала путь в обратом порядке - затем отрицательное число - сколько времени не хватает
        public static ArrayList<Integer> showPath(double[][] graph, Task task) throws wrongTaskFormatException {

            int start = task.getFromIndex();
            int end =  task.getToIndex();
            if(start == -1 || end == -1) throw new wrongTaskFormatException("points must be integers");

            if(start >= graph.length || end >= graph.length) throw new wrongTaskFormatException("points out of range");

            Vector<Integer> pars = new Vector();
            double[] ds = find(graph, start, pars);
            if(end >= ds.length || ds[end] == Double.MAX_VALUE)
                return null;

            ArrayList<Integer> ps = new ArrayList();
            if(task.timeRequired() - ds[end] < 0)
                ps.add((int) (task.timeRequired() - ds[end]));
            else
                ps.add((int) ds[end]);
            for(int i = end; i != start; i = pars.get(i)) // выделяем путь
                ps.add(i);
            ps.add(start);

            ds = null;
            pars = null;

            return ps;
        }
    public static void printArrayListAsPath(ArrayList<Integer> arrayList){
        if(arrayList == null) {
            System.out.println("no such path: no routes to destination point");
            return;
        }
        System.out.print("path: ");
        for (int i = arrayList.size(); --i > 1;) {
            System.out.print(arrayList.get(i) + " -> ");
        }
        System.out.println(arrayList.get(1));

        if(arrayList.get(0) < 0)
            System.out.println("NOT enough time! Time required in addition: " + -1*arrayList.get(0) + " minutes");
        else
            System.out.println("time required: " + arrayList.get(0) + " minutes") ;
    }
}