# Documentation de l'Application

## Introduction

Cette application est un framework Java modulaire qui implémente un cycle de vie d'application basique avec gestion de
la configuration et support multilingue.

## Architecture

L'application suit un cycle de vie en trois phases principales :

1. **Initialisation** (`init`)
2. **Traitement** (`process`)
3. **Nettoyage** (`dispose`)

## Fonctionnalités principales

### 1. Support multilingue

L'application intègre un support multilingue via `ResourceBundle` :

```java
ResourceBundle messages = ResourceBundle.getBundle("i18n/messages");
```

Les messages sont stockés dans le dossier et peuvent être localisés selon différentes langues. `i18n/`

### 2. Gestion de la configuration

L'application propose deux moyens de configuration :

#### a. Fichier de configuration

- Par défaut : `/config.properties`
- Le chemin peut être modifié via les arguments en ligne de commande
- Chargé au démarrage de l'application

#### b. Arguments en ligne de commande

Format supporté : `-paramètre=valeur`
Paramètres disponibles :

- `-debug` ou `-d` : Niveau de débogage (nombre entier)
- `-configFile` ou `-cf` : Chemin vers le fichier de configuration

### 3. Système de journalisation

Un système de journalisation intégré qui fournit :

- Horodatage (timestamp avec fuseau horaire)
- Nom de la classe source
- Niveau de log (INFO, ERROR)
- Message formaté

Exemple de format de log :

``` 
2024-03-21T10:30:00+01:00;tutorials.core.App;INFO;Message
```

## Utilisation

### Démarrage de l'application

``` bash
java -jar app.jar -debug=1 -configFile=/chemin/vers/config.properties
```

### Cycle de vie

1. **Phase d'initialisation** :
    - Chargement des paramètres de ligne de commande
    - Chargement du fichier de configuration
    - Configuration des paramètres système

2. **Phase de traitement** :
    - Exécution de la logique métier principale
    - Cette phase peut être étendue selon les besoins

3. **Phase de nettoyage** :
    - Libération des ressources
    - Finalisation des opérations

## Extensibilité

L'application est conçue pour être facilement étendue :

- Ajout de nouveaux paramètres de configuration via la méthode `extractConfigValue`
- Personnalisation du processus de traitement dans la méthode `process`
- Extension du système de journalisation selon les besoins

## Bonnes pratiques

- Les messages sont externalisés pour faciliter la maintenance et la traduction
- La configuration est flexible et peut être modifiée sans recompilation
- Le système de logging intégré permet un suivi précis de l'exécution
