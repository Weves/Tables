package com.msushanth.tablesapp.ViewLayer.Search;

/**
 * Created by allenqian on 11/25/17.
 */

public class Pair<X, Y> {
    public final X dist;
    public final Y index;

    public Pair(X input1, Y input2) {
        this.dist = input1;
        this.index = input2;
    }
}
