# DominoTracker
Ce projet n'a rien à voir avec les dominos.

## Qu'est ce que Domino tracker ?
Domino Tracker a pour but de remplacer les post-it que moi et ma famille utilisons tous les jours pour noter l'activité de notre chat Domino, sorti à telle heure, mangé, dormi, etc...
On y peut se connecter, voir les activités du chat, changer la date et rentrer un évenement parmis une liste de types d'évenement.

## Organisation du projet

Le projet respecte une architecture MVC, et se connecte directement à une base de donnée MySQL hébergée localement.
Certaines actions simples sont directement implémentées dans la base de données sous forme de procédures et fonctions. Les autres sont gérées dans les classes faisant partie du dossier "model".

## Diagramme de cas d'utilisation

<img src="https://user-images.githubusercontent.com/95452323/228650373-86b13f0b-5b57-4750-b238-cf6e9c1db7a6.png">

## Diagramme de classes
