package com.msushanth.tablesapp.PresentationLayer.FormClasses.Search;

import java.util.Comparator;

/**
 * Created by allenqian on 11/25/17.
 */

public class pair<X, Y> {
    public final X dist;
    public final Y index;

    public pair(X input1, Y input2) {
        this.dist = input1;
        this.index = input2;
    }
}

class SortbyDist implements Comparator<pair>
{
    // Used for sorting in descending order of
    // Euclidean Distance
    public int compare(pair a, pair b)
    {
        double sub = (double)b.dist - (double)a.dist;

        if(sub == 0.0) return 0;
        else if(sub > 0.0) return -1;
        else return 1;
    }
}
