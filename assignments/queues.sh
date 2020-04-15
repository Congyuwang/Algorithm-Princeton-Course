#!/bin/bash

BASEDIR=$(dirname "$0")

mkdir ~/Desktop/queues

cd $BASEDIR/../princeton/src

cp princeton/algo/queue/RandomizedQueue.java princeton/algo/deque/Deque.java Permutation.java StreamChooseK.java ~/Desktop/queues

cd ~/Desktop/queues

echo "$(tail -n +3 Deque.java)" > Deque.java
echo "$(tail -n +3 RandomizedQueue.java)" > RandomizedQueue.java
echo "$(tail -n +2 StreamChooseK.java)" > StreamChooseK.java
echo "$(sed '1 r StreamChooseK.java' Permutation.java)" > Permutation.java
echo "$(sed 's/implements Queue/implements Iterable/g' RandomizedQueue.java)" > RandomizedQueue.java
echo "$(sed 's/@Override//' RandomizedQueue.java)" > RandomizedQueue.java
echo "$(sed 's/@SuppressWarnings(\"unchecked\")//' RandomizedQueue.java)" > RandomizedQueue.java
rm StreamChooseK.java
cd ~/Desktop
zip -r queues.zip queues
rm -rf queues
