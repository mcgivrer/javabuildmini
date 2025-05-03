# README

Having trouble building your Java classes with javac and java commands?

I'm offering a mini script to save time on VERY simple JDK-based projects.

> **WARNING** The script proposed here doesn't handle external libraries and their dependencies, only the JDK is
> considered during the build. A more advanced version of the build script will be proposed later.

Your project containing the classes to compile must follow this file structure:

```
text [MYPROJECT]
|_ src
| |_ main
| | |_ java
| | | |_ [MY_CLASS].java
| | |_ resources
| | |_ myConfig.properties
| |_ target
| |_ build
| |_ [MY_PROJECT]-[MY_VERSION].jar
|_ build.sh
|_ REAMDE.md
|_ LICENSE
|_ .sdkmanrc
|_ .gitignore
``` 

As you can see, the structure is heavily inspired by a standard MAVEN project, where only the SRC/MAIN code part is
used.

The `build.sh` script I propose here, built around this structure, allows any Java developer to quickly get started. It
is presented below:

```bash 
#!/bin/bash 
project_name=step0 
project_version=1.0.0 
main_class=App 
echo "build project {project_name} version{project_version}..." 
echo --- echo "clean previous build..." 
rm -vrf target/ 
mkdir -vp target/{build,classes} 
echo "done." 
echo --- 
echo "sources files:" find src/main/java src/main/resources -name "*.java" 
echo --- echo "compile..." 
javac -d target/classes (find src/main/java src/main/resources -name "*.java") 
cp -vr src/main/resources/* target/classes/ 
echo "done." 
echo --- 
echo "build jar..." 
for app in{main_class} 
do 
  echo ">> for {project_name}.app..." 
  jar cvfe target/build/{project_name}-{app}-{project_version}.jarapp -C target/classes . 
  echo "done." 
done
``` 

Its usage is as simple as it gets. At the root of your project, just type the command line below:

```
bash build.sh
``` 

With the script example above, you'll get a JAR in target/build with a name composed of the `program_name` and
`program_version` variables defined at the beginning of the script, in the following format:

```
bash target/build/step0-App-1.0.0.jar
``` 

The main entry point class of the jar must be defined in the `main_class` variable.

> **NOTE 1** Remember to change the project version and name (`project_name`, `project_version`) and the main class
`main_class` in the script before compilation.

> **TIPS** You can also generate multiple JARs by specifying a space-separated list of classes in the `main_class`
> variable, like "MyClass1 MyClass2 MyClass3". This will create 3 JAR files, each having one of the listed classes as its
> entry point.

Happy Coding!

McG.

