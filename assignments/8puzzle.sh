#!/bin/bash

BASEDIR=$(dirname "$0")

mkdir ~/Desktop/8puzzle

cd $BASEDIR/../src/assignments/eightPuzzle

cp Solver.java Board.java ~/Desktop/8puzzle

cd ~/Desktop/8puzzle

# change the import from princeton.alg.WeightedQuickUnionUF to the one in edu module.
echo "$(tail -n +3 Board.java)" > Board.java
echo "$(tail -n +3 Solver.java)" > Solver.java

cd ..

zip -r 8puzzle.zip 8puzzle
rm -rf 8puzzle
