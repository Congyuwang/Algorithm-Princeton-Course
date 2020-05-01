#!/bin/bash

BASEDIR=$(dirname "$0")

mkdir ~/Desktop/collinear

cd $BASEDIR/../src

cp BruteCollinearPoints.java FastCollinearPoints.java Point.java ~/Desktop/collinear

cd ~/Desktop/collinear

cd ..

zip -r collinear.zip collinear
rm -rf collinear
