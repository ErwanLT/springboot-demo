# Carcassonne

## Placement des tuiles

Chaque tour, nous piochons une tuile et nous devons la placer de manière à ce que les bords correspondent aux tuiles adjacentes.
Une route doit continuer une route, une ville doit continuer une ville, et un champ peut continuer un champ.
Si aucune position légale n'existe, la tuile est retirée de la partie.

## Placement des partisans

Après avoir placé une tuile, nous pouvons placer un partisan sur cette tuile uniquement si la zone ciblée n'est pas déjà occupée
par un autre partisan relié par continuité.

## Score des routes

Une route complète rapporte 1 point par tuile constituant la route.
Une route incomplète en fin de partie rapporte aussi 1 point par tuile.
