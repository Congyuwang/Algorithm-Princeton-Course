#!/bin/bash

BASEDIR=$(dirname "$0")

mkdir ~/Desktop/percolation

cd $BASEDIR/../src

cp Percolation.java PercolationStats.java ~/Desktop/percolation

cd ~/Desktop/percolation

# change the import from princeton.alg.WeightedQuickUnionUF to the one in edu module.
echo "$(tail -n +2 Percolation.java)" > Percolation.java
echo "$(sed '1 a\
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
' Percolation.java)" > Percolation.java

cd ..

zip -r percolation.zip percolation
rm -rf percolation
