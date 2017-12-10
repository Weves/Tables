package com.msushanth.tablesapp.ViewLayer.Search;

import java.util.Comparator;

/**
 * Created by Chris Weaver on 12/6/2017.
 */



public class SortByDist implements Comparator<Pair>
{
    // Used for sorting in ascending order of
    // Euclidean Distance
    public int compare(Pair a, Pair b)
    {
        double sub = (double)b.dist - (double)a.dist;

        if(sub == 0.0) return 0;
        else if(sub > 0.0) return -1;
        else return 1;
    }
}