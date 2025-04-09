## A quoi sert le package Services

Le package va garder toutes les fonctionnalites de notre application et de nos services.

Nous aurons donc une classe pour chaque service. Tous les services qui s'adaptent a plus d'un objet, doivent implementer les interfaces.
pour le respect des normes SOLID et le bon fonctionnement de notre application. 

On aura le package userService pour la gestion des utilisateurs et le package equipementService pour la gestion des equipements. 
dans le package du user on aura les services de CRUD et de connection. Dans le package du equipement on aura les services de CRUD.

En plus du CRUD on aura pour les equipements, le services de recherches, de changements d'etat...

En plus on devra implementer l'envoi des mails, la declaration de vol ...



