# FlamePixels
Dessine et anime une flamme

La flamme consiste a un ensemble de cercles qui apparaissent a la position de la flamme. 
On applique un algorithme similaire aux marching squares pour calculer des valeurs de proximités par
rapport aux cercles. Tous les calculs sont fait localement dans une zone qui délimite la flamme. 
Plus la valeur est forte, plus la flamme est vive.
Les cercles sont naturellement poussés vers le haut et leur taille diminue progressivement.

![Flame](https://github.com/Avengiron/HostReadMeImages/blob/main/FlamePixels/Flame.gif)
