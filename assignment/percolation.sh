#!/bin/bash

BASEDIR=$(dirname "$0")

mkdir ~/Desktop/percolation

cd $BASEDIR/../princeton/src

cp Percolation.java PercolationStats.java ~/Desktop/percolation

cd ~/Desktop/percolation

cd ..

zip -r percolation.zip percolation
rm -rf percolation
