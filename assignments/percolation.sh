#!/bin/bash

BASEDIR=$(dirname "$0")

mkdir ~/Desktop/percolation

cd $BASEDIR/../princeton/src

cp Percolation.java PercolationStats.java ~/Desktop/percolation

cd ~/Desktop/percolation

echo "$(tail -n +2 Percolation.java)" > Percolation.java
echo "$(sed '1 a\
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
' Percolation.java)" > Percolation.java

cd ..

zip -r percolation.zip percolation
rm -rf percolation
