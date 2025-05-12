# README

Vous avez quelques classes java à builder et vous galérez avec la commande javac et java ?

Je vous propose un mini script permettant de gagner du temps sur des projets TRES simple à base JDK.

> **ATTENTION** Le script proposé ici ne prend pas en charge des librairies externes et leurs dépendances, seul le JDK
> est considéré lors du build. Une version plus évoluée d’un script de build sera proposé ultérieurement.

Votre projet comportant vos classes à compiler doit respecter la structure de fichier suivante:

```text
[MONPROJET]
|_ libs
|_ src
|  |_ docs
|  |  |_ en
|  |  |_ fr
|  |_ main
|  |  |_ java
|  |  |  |_ [MA_CLASSE].java
|  |  |_ resources
|  |     |_ maConfig.properties
|  |_ target
|     |_ build
|        |_ [MON_PROJET]-[MA_VERSION].jar
|_ build
|_ REAMDE.md
|_ LICENSE
|_ .sdkmanrc
|_ .gitignore
```

Vous pouvez constater que la structure est fortement inspirée d’un projet MAVEN standard, où seule la partie code
`src/main/{java,resources}` est utilisée.

Vous pouvez ajouter des fichiers de configuration spécifiques à votre projet dans le répertoire `src/main/resources`.
Ces fichiers peuvent inclure des propriétés de configuration, des fichiers XML, ou tout autre type de ressource
nécessaire à votre application.
Dans l'exemple de code présent sont fournis un fichier de configuration ainsi que plusieurs fichier permettant
la traduction de votre application.

Vous noterez également que un nouveau répertoire contenant le documentation `src/docs` apparait.
Il comporte des sous répertoires par langue ad-hoc, si nécessaire.

Le script `build` que je propose ici, construit autour de cette structure, permet à tout développeur java de
rapidement le prendre en main.

Il est présenté ci-dessous:

```bash
#!/bin/bash
project_name=javabuildmini
project_version=1.0.0
main_class=App
JARS=
#
#--- DO NOT CHANGE THE FOLLOWING LINES ---
#
echo "build project ' ${project_name}' version ${project_version}..."
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
# shellcheck disable=SC2046
javac -d target/classes -cp ${JARS// //;} $(find src/main/java src/main/resources -name "*.java")
cp -vr src/main/resources/* target/classes/
echo "done."
echo ---
echo Create MANIFEST...
echo """Manifest-Version: ${project_name}
Main-Class: ${main_class}
Class-Path: ${JARS}
Implementation-Title: ${project_name}
Implementation-Version: ${project_version}
""" >>target/MANIFEST.MF

echo ---
echo "build jar..."
for app in ${main_class}
do
  echo ">> for ${project_name}.$app..."
  jar cvfm target/build/${project_name}-$app-${project_version}.jar target/MANIFEST.MF -C target/classes .
  mkdir -p target/build/libs
  cp -vr ./libs/*.jar target/build/libs
  echo "done."
done
```

Son utilisation est on ne peut plus simple, à la racine de votre projet, tapez simplement la ligne de commande
ci-dessous:

```bash
./build
```

Vous obtiendrez, avec l'exemple de script ci-dessus, un JAR dans target/build ayant comme nom un composé des variables
`program_name`et `program_version` en début de script, avec la forme suivante:

```bash
target/build/javabuildmini-App-1.0.0.jar
```

> [!NOTE]
> Pensez à changer la version et le nom du projet (`project_name`, `project_version`) et la classe principale
`main_class` dans le script avant compilation.

> [!NOTE]
> La (les) classe(s) principale(s), point d’entrée du jar, doi(ven)t être définie(s) dans la variable
`main_class`.
>
> [!TIP]
> Vous pouvez également générer plusieurs JAR en spécifiant une liste de classes séparée par des espaces dans
> la variable `main_class`, "MaClasse1 MaClasse2 Maclasse3", ainsi, trois fichiers archives java (JAR), ayant chacun
> pour point d'entrée une des classes listées, seront crés.

> [!NOTE]
> Vous pouvez maintenant ajouter des dépendances externes en ajoutant leurs chemins dans le script via la
> variable `JARS` qui doit lister les archives
> java (JAR) à
> inclure dans le build ainsi que dans le fichier `MANIFEST.MF` des jars créés.

Bon Code !

McG.
