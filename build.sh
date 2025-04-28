#!/bin/bash
project_name=step0
main_class=tutorials.App
echo "build project..."
echo ---
echo "clean previous build..."
rm -vrf target/
mkdir -vp target/{build,classes}
echo "done."
echo ---
echo "sources files:"
find src/main/java src/main/resources -name "*.java"
echo ---
echo "compile..."
javac -d target/classes $(find src/main/java src/main/resources -name "*.java")
cp -vr src/main/resources/* target/classes/
echo "done."
echo ---
echo "build jar..."
for app in ${main_class}
do
  echo ">> for ${project_name}.$app..."
  jar cvfe target/build/${project_name}-1.0.0.jar $app -C target/classes .
  echo "done."
done
