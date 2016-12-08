package com.goforer.mychallenge.model.data.sort;

import com.goforer.mychallenge.model.data.Repos;

import java.util.Comparator;

/**
 * Created by USER on 2016-12-08.
 */

public class ReposComparator implements Comparator<Repos> {
    public ReposComparator() {
    }

    @Override
    public int compare(Repos repos1, Repos repos2) {
        return repos2.getStarCount() - repos1.getStarCount();
    }
}
