# MDD - Monde de Dev

MDD est une application fullstack développée avec Angular et Spring Boot.
Elle permet à des développeurs de s'inscrire, se connecter, consulter des thèmes, s'abonner à leurs sujets favoris, publier des articles et commenter les publications.

Le projet est séparé en deux applications :

- `front` : application web Angular
- `back` : API REST Spring Boot

## Fonctionnalités

- Inscription et connexion utilisateur
- Authentification par JWT
- Consultation du fil d'actualité
- Tri des articles par date
- Création d'articles
- Consultation du détail d'un article
- Ajout de commentaires
- Consultation des thèmes disponibles
- Abonnement et désabonnement à un thème
- Consultation et mise à jour du profil utilisateur

## Stack technique

### Frontend

- Angular 21
- Angular Material
- TypeScript
- RxJS
- Jest

### Backend

- Java 21
- Spring Boot 3.5
- Spring Web
- Spring Security
- Spring Data JPA
- Hibernate
- MySQL
- H2 pour les tests
- JWT
- Lombok
- MapStruct
- Springdoc OpenAPI / Swagger
- Maven

## Prérequis

Avant de lancer le projet, installer :

- Node.js et npm
- Java 21
- MySQL
- Maven, ou utiliser le wrapper Maven fourni dans `back`

## Installation

Cloner le dépôt, puis installer les dépendances du frontend :

```bash
cd front
npm install
```

Le backend utilise Maven. Les dépendances seront téléchargées au premier lancement ou à la première commande de test.

## Configuration de la base de données

Créer une base de données MySQL :

```sql
CREATE DATABASE mdd_database;
```

La configuration principale se trouve dans :

```text
back/src/main/resources/application.properties
```

Configuration actuelle :

```properties
server.port=9090
spring.datasource.url=jdbc:mysql://localhost:3306/mdd_database
spring.jpa.hibernate.ddl-auto=update
```

Si votre installation MySQL nécessite un utilisateur et un mot de passe, ajoutez-les dans `application.properties` :

```properties
spring.datasource.username=votre_utilisateur
spring.datasource.password=votre_mot_de_passe
```

Au premier démarrage, un jeu de données de démonstration est inséré automatiquement si la table des utilisateurs est vide.

Comptes créés :

| Identifiant | Email | Mot de passe |
| --- | --- | --- |
| `user1` | `user1@test.com` | `Password1!` |
| `user2` | `user2@test.com` | `Password2!` |

## Lancer le backend

Depuis le dossier `back` :

```bash
cd back
./mvnw spring-boot:run
```

Sous Windows PowerShell :

```bash
cd back
.\mvnw.cmd spring-boot:run
```

L'API est disponible sur :

```text
http://localhost:9090
```

La documentation Swagger est disponible sur :

```text
http://localhost:9090/swagger-ui/index.html
```

## Lancer le frontend

Depuis le dossier `front` :

```bash
cd front
npm start
```

L'application est disponible sur :

```text
http://localhost:4200
```

Le frontend appelle l'API sur :

```text
http://localhost:9090
```

Le CORS backend autorise actuellement l'origine :

```text
http://localhost:4200
```

## Tests

### Tests backend

Depuis le dossier `back` :

```bash
./mvnw test
```

Sous Windows PowerShell :

```bash
.\mvnw.cmd test
```

Les tests backend utilisent une base H2 en mémoire configurée dans :

```text
back/src/test/resources/application-test.properties
```

### Tests frontend

Depuis le dossier `front` :

```bash
npm test
```

Cette commande lance les tests unitaires Angular avec Jest. Les fichiers de test sont les fichiers `*.spec.ts` présents dans `front/src`.

Pour générer un rapport de couverture :

```bash
npm run test:coverage
```

Cette commande génère un rapport de couverture détaillé. Il permet de vérifier quelles parties du code frontend sont couvertes par les tests unitaires : composants, services, guards et interceptors.

Après génération, le rapport HTML peut être consulté depuis :

```text
front/coverage/lcov-report/index.html
```

Le dernier rapport de couverture frontend donne les résultats globaux suivants :

| Indicateur | Couverture | Éléments couverts |
| --- | ---: | ---: |
| Statements | 94.37% | 285 / 302 |
| Branches | 86.11% | 31 / 36 |
| Functions | 85.18% | 69 / 81 |
| Lines | 94.79% | 255 / 269 |

Ces résultats dépassent les seuils globaux configurés dans Jest :

| Indicateur | Seuil minimal |
| --- | ---: |
| Statements | 70% |
| Branches | 70% |
| Functions | 70% |
| Lines | 70% |

Le rapport montre également une couverture détaillée par dossier :

| Dossier | Statements | Branches | Functions | Lines |
| --- | ---: | ---: | ---: | ---: |
| `app` | 100% | 100% | 100% | 100% |
| `app/core/service` | 95.83% | 100% | 89.47% | 97.61% |
| `app/guards` | 100% | 100% | 100% | 100% |
| `app/interceptors` | 100% | 100% | 100% | 100% |
| `app/pages/feed` | 93.75% | 50% | 75% | 92.85% |
| `app/pages/home` | 100% | 100% | 100% | 100% |
| `app/pages/login` | 86.36% | 100% | 66.66% | 85% |
| `app/pages/new-post` | 95.83% | 50% | 100% | 100% |
| `app/pages/not-found` | 100% | 100% | 100% | 100% |
| `app/pages/post` | 95.45% | 100% | 83.33% | 95% |
| `app/pages/register` | 90% | 80% | 71.42% | 88.88% |
| `app/pages/shared/header` | 90% | 83.33% | 78.57% | 91.48% |
| `app/pages/subjects` | 100% | 100% | 100% | 100% |
| `app/pages/user` | 97.14% | 100% | 87.5% | 96.87% |

Les tests frontend couvrent notamment :

- les services Angular qui appellent l'API REST ;
- le guard d'authentification protégeant les routes privées ;
- l'interceptor HTTP qui ajoute le token JWT aux requêtes ;
- les composants de pages principales : accueil, connexion, inscription, fil d'actualité, création d'article, détail d'article, thèmes, profil utilisateur et page 404.

Les zones avec une couverture plus faible concernent principalement certaines branches conditionnelles dans les pages `feed` et `new-post`, ainsi que quelques fonctions dans les pages `login`, `post`, `register`, `header` et `user`. Elles restent néanmoins au-dessus des seuils globaux définis pour le projet.

Un rapport de tests plus détaillé est disponible dans :

```text
RAPPORT_DE_TESTS.md
```

## Endpoints principaux

### Authentification

| Méthode | Endpoint | Description | Authentification |
| --- | --- | --- | --- |
| `POST` | `/api/auth/register` | Inscription | Non |
| `POST` | `/api/auth/login` | Connexion | Non |
| `GET` | `/api/auth/me` | Profil utilisateur connecté | Oui |
| `POST` | `/api/auth/logout` | Déconnexion côté client | Oui |

### Utilisateur

| Méthode | Endpoint | Description | Authentification |
| --- | --- | --- | --- |
| `PUT` | `/api/users/me` | Mise à jour du profil | Oui |

### Thèmes

| Méthode | Endpoint | Description | Authentification |
| --- | --- | --- | --- |
| `GET` | `/api/subjects` | Liste des thèmes | Oui |
| `POST` | `/api/subjects/{id}/subscribe` | S'abonner à un thème | Oui |
| `DELETE` | `/api/subjects/{id}/subscribe` | Se désabonner d'un thème | Oui |

### Articles

| Méthode | Endpoint | Description | Authentification |
| --- | --- | --- | --- |
| `GET` | `/api/articles` | Liste des articles | Oui |
| `GET` | `/api/articles?sort=asc` | Liste des articles triés par date croissante | Oui |
| `GET` | `/api/articles?sort=desc` | Liste des articles triés par date décroissante | Oui |
| `GET` | `/api/articles/{id}` | Détail d'un article | Oui |
| `POST` | `/api/articles` | Création d'un article | Oui |
| `POST` | `/api/articles/{id}/comments` | Ajout d'un commentaire | Oui |

## Collection Postman

Une collection Postman est disponible dans :

```text
back/postman/MDD-API.postman_collection.json
```

Un environnement local est également fourni :

```text
back/postman/MDD-Local.postman_environment.json
```

## Structure du projet

```text
.
|-- back
|   |-- postman
|   |-- src/main/java/com/openclassrooms/mddapi
|   |   |-- config
|   |   |-- controllers
|   |   |-- dto
|   |   |-- exceptions
|   |   |-- mapper
|   |   |-- models
|   |   |-- repository
|   |   |-- security
|   |   `-- services
|   `-- src/test
|-- front
|   `-- src/app
|       |-- core
|       |-- guards
|       |-- interceptors
|       `-- pages
`-- README.md
```

## Notes de développement

- Le token JWT est stocké côté frontend après la connexion.
- Les routes protégées du frontend utilisent un guard d'authentification.
- Les appels authentifiés passent par un interceptor Angular qui ajoute le token aux requêtes HTTP.
- La configuration JWT locale est définie dans `application.properties`.
- Pour un environnement de production, déplacer les secrets hors du dépôt et utiliser des variables d'environnement.
