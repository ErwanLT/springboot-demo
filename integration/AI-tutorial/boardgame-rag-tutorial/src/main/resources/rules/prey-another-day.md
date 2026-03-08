# Prey Another Day (FR)

Source officielle : https://media.play-in.com/pdf/rules_games/prey_another_day_fr.pdf

> Note : texte restructuré en Markdown pour l'indexation RAG.

## But du jeu

La première personne à cumuler **5 jetons Nourriture** remporte la partie.

## Mise en place

- Poser une carte **Appel** devant chaque joueur.
- Prendre une série de cartes **Animal** :
  - Ours
  - Loup
  - Lynx
  - Hibou
  - Souris
- Placer tous les jetons Nourriture au centre de la table pour constituer la réserve.

## Aperçu d'une manche

Une partie se déroule en plusieurs manches.

Au cours d'une manche :
- minimum : **1 chasse**
- maximum : **3 chasses**

Chaque chasse se déroule en 3 étapes :
1. Choix d'un animal
2. Appel
3. Fin de la chasse

## Déroulement d'une manche

### 1) Choix d'un animal

Chaque joueur choisit **1 carte Animal** de sa main et la pose face cachée.

Si un joueur est dévoré pendant la manche (voir étape Appel), il est éliminé pour le reste de la manche et ne joue plus les chasses suivantes.

### 2) Appel

Un joueur lit sa carte Appel et appelle les animaux dans l'ordre croissant :
- Ours (1)
- Loup (2)
- Lynx (3)
- Hibou (4)
- Souris (5)

Les joueurs ayant l'animal appelé révèlent leur carte.

La personne qui effectue l'appel révèle aussi sa propre carte lorsque son animal est appelé.

#### Cas A : aucun animal révélé

- Rien ne se passe.

#### Cas B : exactement 1 animal révélé

- Le joueur révélé est le seul prédateur de cette chasse.
- Il peut chasser **un animal de valeur strictement supérieure**.
- Les joueurs possédant l'animal chassé le révèlent et sont **dévorés** (éliminés pour la manche).
- Le prédateur conserve sa carte révélée devant lui.

Exemples :
- Ours peut chasser Loup, Lynx, Hibou ou Souris.
- Lynx peut chasser Hibou ou Souris.

#### Cas C : 2 animaux ou plus révélés

- Les prédateurs se neutralisent.
- Aucun joueur n'est dévoré.
- Les cartes révélées restent visibles.

L'appel continue ensuite avec l'animal suivant, jusqu'à révéler toutes les cartes jouées pour cette chasse.

### 3) Fin de la chasse

Après la chasse, vérifier la condition de fin de manche.

#### La manche continue si :

- plusieurs joueurs ont survécu
- et que ce n'était pas la 3e chasse

Dans ce cas, une nouvelle chasse démarre entre survivants avec les cartes restantes en main.

#### La manche se termine immédiatement si :

- il ne reste qu'un seul survivant
- ou si la 3e chasse vient de se terminer

Dans les deux cas, distribuer les jetons Nourriture.

## Fin d'une manche : distribution des jetons Nourriture

Les jetons sont attribués uniquement à la fin d'une manche.

### Si une seule personne a survécu

- cette personne reçoit **2 jetons Nourriture**
- les autres ne reçoivent rien

### Si 2 personnes ou plus ont survécu

Chaque survivant additionne la valeur des 3 animaux qu'il a joués durant la manche.

Attribution :
- **1 jeton** pour chaque survivant
- **+1 jeton supplémentaire** au plus haut score
- en cas d'égalité au plus haut score, chaque joueur concerné reçoit ce jeton bonus
- les joueurs éliminés ne reçoivent rien

## Fin de partie

Dès qu'un joueur atteint **5 jetons Nourriture ou plus**, la partie prend fin.

Si plusieurs joueurs atteignent ce seuil au même tour avec le même total,
la partie continue jusqu'à ce qu'un joueur ait strictement le plus grand nombre de jetons.
