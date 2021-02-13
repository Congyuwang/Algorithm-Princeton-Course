#!/bin/bash

BASEDIR=$(dirname "$0")

mkdir ~/Desktop/hello

cd $BASEDIR/../src/assignments/helloWorld || exit

cp HelloWorld.java HelloGoodbye.java RandomWord.java ~/Desktop/hello

cd ~/Desktop/hello || exit

echo "$(tail -n +3 HelloWorld.java)" > HelloWorld.java
echo "$(tail -n +3 HelloGoodbye.java)" > HelloGoodbye.java
echo "$(tail -n +3 RandomWord.java)" > RandomWord.java

cd ..

zip -r hello.zip hello
rm -rf hello
