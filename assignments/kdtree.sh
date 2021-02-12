#!/bin/bash

BASEDIR=$(dirname "$0")

mkdir ~/Desktop/kdtree

cd $BASEDIR/../src/assignments/kdtree || exit

cp KdTree.java PointSET.java ~/Desktop/kdtree

cd ~/Desktop/kdtree || exit

# change the import from princeton.alg.WeightedQuickUnionUF to the one in edu module.
echo "$(tail -n +3 KdTree.java)" > KdTree.java
echo "$(tail -n +3 PointSET.java)" > PointSET.java

cd ..

zip -r kdtree.zip kdtree
rm -rf kdtree
