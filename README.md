# 🚀 MDD — Monde de Dev

> **Une plateforme communautaire pour les développeurs**  
> Partagez vos articles, découvrez des sujets techniques et connectez-vous avec d'autres devs.

MDD est une application **fullstack** développée avec **Angular 19** et **Spring Boot 3**, permettant aux développeurs de :
- ✅ S'inscrire et se connecter de manière sécurisée
- ✅ Consulter un fil d'actualité personnalisé
- ✅ Publier des articles techniques
- ✅ Commenter et interagir avec la communauté
- ✅ S'abonner à des thèmes qui les intéressent

---

## 📚 Table des matières

1. [Fonctionnalités](#-fonctionnalités)
2. [Stack technique](#-stack-technique)
3. [Architecture](#-architecture-générale)
4. [Installation](#-installation-et-configuration)
5. [Démarrage rapide](#-démarrage-rapide)
6. [Tests](#-tests)
7. [Documentation](#-documentation)
8. [Endpoints API](#-endpoints-principaux)
9. [Dépannage](#-dépannage)

---

## ✨ Fonctionnalités

| Fonctionnalité | Description | Statut |
| --- | --- | --- |
| **Authentification sécurisée** | Inscription, connexion, JWT | ✅ Implémenté |
| **Fil d'actualité** | Consultation des articles avec tri par date | ✅ Implémenté |
| **Publication d'articles** | Création et publication avec sélection de thème | ✅ Implémenté |
| **Commentaires** | Interaction sur les articles | ✅ Implémenté |
| **Gestion des thèmes** | Abonnement/désabonnement aux sujets | ✅ Implémenté |
| **Profil utilisateur** | Consultation et mise à jour des infos | ✅ Implémenté |

---

## 🏗️ Stack technique

### Frontend
```
Angular 19 • TypeScript 5.8 • Angular Material 19
RxJS • Jest • SCSS
```

### Backend
```
Java 21 • Spring Boot 3.5 • Spring Security
Spring Data JPA • Hibernate • MySQL 8.0+
JWT (jjwt 0.12.3) • MapStruct • Springdoc OpenAPI / Swagger
Maven • H2 (tests)
```

### Versions minimales testées

| Technologie | Version | Statut |
| --- | --- | --- |
| Node.js | 18.x LTS | ✅ Testé |
| npm | 10.x | ✅ Testé |
| Java | 21 | ✅ Requis |
| MySQL | 8.0+ | ✅ Requis |
| Maven | 3.9+ | ✅ Inclus (wrapper) |

**Note** : Le wrapper Maven (`mvnw`) est fourni. Vous n'avez besoin que de **Java 21**.

---

## 🎯 Architecture générale

```
┌──────────────────────────────────────────┐
│       FRONTEND (Angular 19)              │
│    http://localhost:4200                 │
│  - Composants réactifs                   │
│  - Guard d'authentification              │
│  - Interceptor JWT                       │
└────────────────┬─────────────────────────┘
                 │ HTTPS + JWT Token
                 │
┌────────────────▼─────────────────────────┐
│     BACKEND API (Spring Boot 3)          │
│    http://localhost:9090                 │
│  - REST Controllers                      │
│  - Services métier                       │
│  - Spring Security + JWT                 │
│  - Swagger: /swagger-ui/index.html       │
└────────────────┬─────────────────────────┘
                 │ JDBC
                 │
┌────────────────▼─────────────────────────┐
│    DATA (MySQL / H2 tests)               │
│      mdd_database                        │
│  - Users, Articles, Comments             │
│  - Subjects, Subscriptions               │
└──────────────────────────────────────────┘
```

---

## 📦 Installation et configuration

### Prérequis

Avant de commencer, assurez-vous d'avoir installé :
- **Java 21** 
- **Node.js 18+ LTS**
- **MySQL 8.0+**

### Étape 1 : Cloner le dépôt

```bash
git clone <url-du-depot>
cd mdd
```

### Étape 2 : Installer les dépendances frontend

```bash
cd front
npm install
```

### Étape 3 : Configurer la base de données

Créer la base de données MySQL :

```sql
CREATE DATABASE mdd_database;
```


Configuration actuelle :

```properties
server.port=9090
spring.datasource.url=jdbc:mysql://localhost:3306/mdd_database
spring.jpa.hibernate.ddl-auto=update
```

Modifier la configuration dans `application.properties` :

```properties
spring.datasource.username=votre_utilisateur
spring.datasource.password=votre_mot_de_passe
```

**Comptes de démonstration créés automatiquement** :

| Identifiant | Email | Mot de passe |
| --- | --- | --- |
| `user1` | `user1@test.com` | `Password1!` |
| `user2` | `user2@test.com` | `Password2!` |

Le backend crée ces comptes au premier démarrage si la table des utilisateurs est vide.

---

## 🚀 Démarrage rapide

### Option 1 : Lancer tout en parallèle (recommandé)

**Terminal 1 – Backend** :
```bash
cd back
./mvnw spring-boot:run
```

**Terminal 2 – Frontend** :
```bash
cd front
npm start
```

**Accès** :
- Application : http://localhost:4200
- API Swagger : http://localhost:9090/swagger-ui/index.html
- Identifiants : `user1@test.com` / `Password1!`

### Option 2 : Sur Windows PowerShell

**Terminal 1 – Backend** :
```bash
cd back
.\mvnw.cmd spring-boot:run
```

**Terminal 2 – Frontend** :
```bash
cd front
npm start
```

---

## ⚡ Problèmes courants au démarrage

| Problème | Cause probable | Solution |
| --- | --- | --- |
| ❌ Port 3306 indisponible | MySQL n'est pas lancé ou autre processus occupe le port | `sudo lsof -i :3306` pour vérifier ; redémarrer MySQL |
| ❌ Port 4200/9090 indisponible | Autre processus occupe le port | Changer le port ou terminer le processus existant |
| ❌ `Could not find or load main class` | Java n'est pas sur le PATH | Vérifier : `java -version` et la variable `$JAVA_HOME` |
| ❌ Erreur de connexion BD | Identifiants MySQL incorrects | Vérifier le username/password dans `application.properties` |
| ❌ `node_modules` corrompu | Installation npm incomplète | `rm -rf node_modules package-lock.json && npm install` |
| ❌ Frontend ne charge pas les données | Backend n'est pas lancé | Vérifier que le backend tourne sur http://localhost:9090 |

---

## 🧪 Tests

### Tests backend

```bash
cd back

# Lancer tous les tests
./mvnw test

# Sur Windows
.\mvnw.cmd test
```

**Résultats de couverture** (dernière exécution : 27 mai 2026) :

| Indicateur | Couverture | Éléments |
| --- | --- | --- |
| Classes | 100% | 33/33 |
| Méthodes | 81.1% | 77/95 |
| Lignes | 70.6% | 279/395 |
| Services | **100%** | **87/87 lignes** |
| Contrôleurs | **92.9%** | **13/14 lignes** |

📊 Rapport HTML complet : `back/htmlReport/index.html`

**Configuration** : Les tests utilisent une base **H2 en mémoire** (cf. `application-test.properties`)

---

### Tests frontend

```bash
cd front

# Lancer les tests unitaires
npm test

# Générer un rapport de couverture
npm run test:coverage
```

**Résultats de couverture** (dernière exécution : 22 mai 2026) :

| Indicateur | Couverture | Éléments |
| --- | --- | --- |
| Statements | 94.37% | 285/302 |
| Branches | 86.11% | 31/36 |
| Functions | 85.18% | 69/81 |
| Lines | 94.79% | 255/269 |

📊 Rapport HTML complet : `front/coverage/lcov-report/index.html`

**Points forts** :
- ✅ Services d'authentification, utilisateurs et thèmes : **100% couverture**
- ✅ Guard d'authentification : **100% couverture**
- ✅ Interceptor JWT : **100% couverture**

**Seuils configurés** : Minimum 70% sur tous les indicateurs (✅ tous dépassés)

---

## 📖 Documentation

### Rapport de tests détaillé

Voir `RAPPORT_DE_TESTS.md` pour :
- Analyse par fichier et dossier
- Zones bien couvertes et points à améliorer
- Recommandations priorisées

### FAQ utilisateur

Voir `FAQ_UTILISATEUR.md` pour :
- Guide rapide par profil utilisateur
- Scénarios complets d'utilisation
- Dépannage et erreurs courantes
- Bonnes pratiques

---

## 🔌 Endpoints principaux

### 🔐 Authentification

| Méthode | Endpoint | Description | Auth |
| --- | --- | --- | --- |
| `POST` | `/api/auth/register` | Créer un compte | ❌ |
| `POST` | `/api/auth/login` | Se connecter | ❌ |
| `GET` | `/api/auth/me` | Profil utilisateur actuel | ✅ |
| `POST` | `/api/auth/logout` | Déconnexion (côté client) | ✅ |

### 👤 Utilisateur

| Méthode | Endpoint | Description | Auth |
| --- | --- | --- | --- |
| `PUT` | `/api/users/me` | Mettre à jour le profil | ✅ |

### 📚 Thèmes

| Méthode | Endpoint | Description | Auth |
| --- | --- | --- | --- |
| `GET` | `/api/subjects` | Liste de tous les thèmes | ✅ |
| `POST` | `/api/subjects/{id}/subscribe` | S'abonner à un thème | ✅ |
| `DELETE` | `/api/subjects/{id}/subscribe` | Se désabonner d'un thème | ✅ |

### 📝 Articles

| Méthode | Endpoint | Description | Auth |
| --- | --- | --- | --- |
| `GET` | `/api/articles` | Liste des articles (tri par défaut : desc) | ✅ |
| `GET` | `/api/articles?sort=asc` | Articles triés du plus ancien au plus récent | ✅ |
| `GET` | `/api/articles?sort=desc` | Articles triés du plus récent au plus ancien | ✅ |
| `GET` | `/api/articles/{id}` | Détail d'un article | ✅ |
| `POST` | `/api/articles` | Créer un article | ✅ |
| `POST` | `/api/articles/{id}/comments` | Ajouter un commentaire | ✅ |

📚 **Documentation Swagger complète** : http://localhost:9090/swagger-ui/index.html

---

## 🛠️ Outils et ressources

### Collection Postman

Importer la collection dans Postman pour tester l'API facilement :

```
back/postman/MDD-API.postman_collection.json
```

Environnement local pré-configuré :

```
back/postman/MDD-Local.postman_environment.json
```

### Structure du projet

```
mdd/
├── front/                          # Application Angular
│   ├── src/app/
│   │   ├── core/service/           # Services (Auth, User, Post, Subject)
│   │   ├── guards/                 # Auth.guard.ts
│   │   ├── interceptors/           # Auth.interceptor.ts
│   │   └── pages/                  # Composants de pages
│   ├── coverage/                   # Rapports de couverture Jest
│   └── package.json
│
├── back/                           # API Spring Boot
│   ├── src/main/java/.../
│   │   ├── config/                 # SecurityConfig, AppConfig
│   │   ├── controllers/            # AuthController, ArticleController, etc.
│   │   ├── dto/                    # Objets de transfert de données
│   │   ├── exceptions/             # GlobalExceptionHandler, custom exceptions
│   │   ├── mapper/                 # MapStruct mappers
│   │   ├── models/                 # Entités JPA (User, Article, etc.)
│   │   ├── repository/             # Interfaces JPA
│   │   ├── security/               # JwtTokenProvider, CustomUserDetailsService
│   │   └── services/               # Services métier
│   ├── src/test/
│   ├── postman/                    # Collections et environnements Postman
│   ├── htmlReport/                 # Rapports de couverture JaCoCo
│   └── pom.xml
│
├── README.md                       # Ce fichier
├── RAPPORT_DE_TESTS.md            # Analyse détaillée des tests
└── FAQ_UTILISATEUR.md             # Guide utilisateur et dépannage
```

---

## 🔒 Notes de sécurité

### Authentification et JWT

- **Token JWT** : Stocké côté frontend après la connexion
- **Routes protégées** : Guard d'authentification vérifie le token avant d'accéder à la page
- **Interceptor** : Ajoute automatiquement le token à chaque requête HTTP
- **Passwords** : Encodés en BCrypt côté backend

### Configuration pour la production

**Avant de déployer**, déplacer les secrets hors du dépôt :

```bash
# Variables d'environnement
export JWT_SECRET=votre_secret_très_long
export DB_PASSWORD=votre_mot_de_passe_sécurisé
export CORS_ORIGIN=https://votre-domaine.com
```

Puis adapter `application.properties` :

```properties
spring.datasource.password=${DB_PASSWORD}
app.jwt.secret=${JWT_SECRET}
```

**CORS** : Actuellement configuré pour `http://localhost:4200` uniquement.

---

## 📌 Points importants

| Point | Détail |
| --- | --- |
| **Base de données** | Les schémas se créent automatiquement avec Hibernate (ddl-auto=update) |
| **Données de démo** | 2 utilisateurs de test créés automatiquement au premier démarrage |
| **Tests** | H2 en mémoire pour les tests (pas de dépendance MySQL) |
| **CORS** | Actuellement autorisé uniquement sur `http://localhost:4200` |
| **JWT Subject** | Utilise le username (pas l'userId) pour cohérence avec `loadUserByUsername()` |

---

## 📞 Support et ressources

- 📖 **FAQ utilisateur** : `FAQ_UTILISATEUR.md`
- 📊 **Rapport de tests** : `RAPPORT_DE_TESTS.md`
- 🔗 **Swagger API** : http://localhost:9090/swagger-ui/index.html
- 📮 **Postman** : `back/postman/MDD-API.postman_collection.json`

---


