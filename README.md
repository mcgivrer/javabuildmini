# README

Vous avez quelques classes java à builder et vous galérez avec la commande javac et java ?

Je vous propose un mini script permettant de gagner du temps sur des projets TRES simple à base JDK.

> **ATTENTION** Le script proposé ici ne prend pas en charge des librairies externes et leurs dépendances, seul le JDK
> est considéré lors du build. Une version plus évoluée d’un script de build sera proposé ultérieurement.

Votre projet comportant vos classes à compiler doit respecter la structure de fichier suivante :

```text
[MONPROJET]
|_ src
|  |_ main
|  |  |_ java
|  |  |  |_ [MA_CLASSE].java
|  |  |_ resources
|  |     |_ maConfig.properties
|  |_ target
|     |_ build
|        |_ [MON_PROJET]-[MA_VERSION].jar
|_ build.sh
|_ REAMDE.md
|_ LICENSE
|_ .sdkmanrc
|_ .gitignore
```

Vous pouvez constater que la structure est fortement inspirée d’un porjet MAVEN standard, où seule la partie code
SRC/MAIN est utilisée.

Le script `build.sh` que je propose ici, construit autour de cette structure, permet à tout développeur java de
rapidement le prendre en main. Il est présenté ci-dessous:

```bash
#!/bin/bash
project_name=step0
project_version=1.0.0
main_class=App
echo "build project ${project_name} version ${project_version}..."
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
  jar cvfe target/build/${project_name}-${app}-${project_version}.jar $app -C target/classes .
  echo "done."
done
```

Son utilisation est on ne peut plus simple, à la racine de votre projet, tapez simplement la ligne de commande
ci-dessous :

```bash
build.sh
```

Vous obtiendrez, avec l'exemple de script ci-dessus, un JAR dans target/build ayant comme nom un composé des variables
`program_name`et `program_version` en début de script, avec la forme suivante :

```bash
target/build/step0-App-1.0.0.jar
```

La classe principale point d’entrée du jar doit être définie dans la sera alors variable `main_class`.

> **NOTE 1** Pensez à changer la version et le nom du projet (`project_name`, `project_version`) et la classe principale
`main_class` dans le script avant compilation.

> **TIPS** Vous pouvez également générer plusieurs JAR en spécifiant une liste de classes séparée par des espaces dans
> la variable `main_class`, "MaClasse1 MaClasse2 Maclasse3", ainsi 3 fichiers JAR, ayant chacun pour point d'entrée une
> des classes listées, seront créés.

Bon Code !

McG.
