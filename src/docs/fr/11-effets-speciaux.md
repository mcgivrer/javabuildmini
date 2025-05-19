# Effets Spéciaux

## Simulation du cycle Jour / Nuit

Afin de réaliser la simulation du cycle d'lternace du jour et de la nuit, nous 
allons creaer 3 nouveaux composants. Ils hériteront tous les 3 de la classe Entity, 
afin qu'ils soient dessinés via le `Renderer` existant.

L'idée et d'obtenir le rendu à l'écran comme présenté ci-dessous:

![la nuit étoilée](../illustrations/SFK_StarSky.png)

![le levé du jour](../illustrations/SFK_SunBurst.png)

![la mi journée](../illustrations/SFK_MidDaySun.png)

![La tombé de la nuit](../illustrations/SFK_End_Of_Day.png)


Les 3 composants sont:

1. `Sun` qui simulera le parcours et le dessin du soleil, 
2. `Sky` qui créera l'animation des couleurs du ciel en fonction du moment de la journée,
3. `StarSky` qui fera apparaitre et animera un ensemble d'étoiles, gravitant autour 
d'un point dans le ciel.

Afin de faciliter l'implémentation de ces nouveaux composants, nous allons faire 
évoluer la classe Entity et Renderer pour en simplifier la phase de dessin 
(`Renderer#draw()`) et déleguer au composant lui-même ses spécificités de rendu.

La class Entity comportera donc 2 nouvelles méthodes :

- `Entity#update(double elapsed)` permettant de gérer les animations des composants,
- `Entity#draw(Graphics2D g)` qui donne la main au composant pour son dessin à l'écran.

La classe `Renderer` sera simplifée par l'appel de la méthode Entity#draw() pour 
toutes les entités, quelques soient leur type.
