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

<img src="https://cdn.discordapp.com/attachments/924421151504097321/1090730324381675661/image.png">

## Points d'amélioration

Ayant réalisé ce projet seul, il comporte de nombreux points d'amélioration sur lesquels j'aimerais travailler plus tard:
<ul>
  <li>Pouvoir ajouter des éléments aux évenements: commentaires, évenements..</li>
  <li>Ajouter des statistisques</li>
  <li>Possibilité d'avoir plusieurs chats, à prendre en compte dans l'affichage, pouvoir sélectionner un chat en particulier ou tous</li>
  <li>Meilleure interface graphique: Elle est actuellement très basique, je n'ai pas eu le temps de découvrir en détails comment me servir des outils</li>
  <li>Plusieurs foyers auxquels seraient attribués chaques chats et personnes</li>
 </ul>
