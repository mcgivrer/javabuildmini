# Moteur 2D physique

Le moteur décrit ici permet de gérer les movements en respectant les lois de Newton, 
de detecter les collisions et en gérer les réponses entre les objets de type 
Entity évoluant dans une zone définie par un ou plieurs objets World.
le propriété mecanique et physique des objects sont portées par la classe Material.

## les objets

Chacune des classes suivantes possèdent un identifiant unique (id:long), un nom (name:String) défini à l'instantiation (mais 
avec la possibilité d'en voir un généré par defaut).


### Entity 

La classe Entity défini une entité gérée par le modteur. Elle précise les attributs suivants:

- position : Vector2D (m), 
- taille : Vector2D (m),
- velocité: Vector2D (m/s),
- acceleration:Vector2D (m/s²),
- forces: List<Vector2D> est une liste des forces appliquées à l'entité. la liste est effacée à chaque cycle,
- masse: double (kg), 
- material:Material  défini la matière de l'entité,
- active : booléen précisant si l'objet est actif ou non et si il doit pêtre considéré dans les calculs.
- parent: Entity
- child: List<Entity>

Sont présents également quelques attributes graphiques:

- shape:Shape defini la forme de dessin de l'entité qui peut être un point, un rectangle, une ellipse, ou une ligne,
- edgeColor: Color la couleur de dessin des contours,
- fillColor: Color la couleur de remplissage


### Material 

La classe Materiaeprmet de donner les caracteristique physique et mécanique pour une matière qui peut être 
attachée à une ou plusieurs entités. Elle définit les attributs suivants:

- densité, 
- friction de contact,
- friction dans l'air
- élasticité


### World

Définit la taille du monde dans lequel les entités évoluent, défini la matière par défaut dans laqelle 
ces entités évoluent, ainsi que la gravité de l'environnemnt (exprimée en m/s²).

- playrea: Rectangle2D, 
- gravity: Vector2D, 
- material: Material, 
- parent: World 
- areas: List<World> une liste de zones,


Un objet World peut-être est composé de plusieurs World imbriqués via des relations 
parent/enfant, permettant ainsi définir un monde dans lequel les caractéristique 
peuvent changer d'une zone à l'autre (material, garvity, etc...).

## Traitement


les entités (Entity) évoluant dans le World sont soumises aux caractéristiques 
définies par le ou les objets World. Afin d'optimiser la detection des collisions, 
les entités seront dispatchées à travers un QuadTree à la taille du WOrld, ce qui 
servira à réduire considérablement le nombre de comparaison entre les entités.

Il est impoerant de noter que ce moteur 2D doit être capable de gérer un très grand 
nombre d'entiés, et doit dont être optimisé. lutilisation des fonctionnalités 
offertes par le JDK24 (lambdas, etc...) est nécessaire.
