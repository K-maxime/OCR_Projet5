# FAQ utilisateur

Cette FAQ explique les principaux cas d'usage de l'application MDD, les erreurs courantes rencontrées par les utilisateurs et les solutions possibles.

## Guide rapide par profil utilisateur

### Je suis un nouveau développeur
1. [Créer un compte](#comment-créer-un-compte-)
2. [M'abonner à des thèmes](#comment-sabonner-à-un-thème-)
3. [Consulter le fil d'actualité](#comment-consulter-les-articles-)

### Je veux publier mon expertise
1. [Me connecter](#comment-se-connecter-)
2. [Créer un article](#comment-créer-un-article-)
3. [Interagir via les commentaires](#comment-commenter-un-article-)

### J'ai un problème technique
→ Voir [Dépannage local](#dépannage-local)

## Présentation générale

### À quoi sert l'application MDD ?

MDD, pour Monde de Dev, est une application communautaire destinée aux développeurs. Elle permet de consulter des thèmes techniques, de s'abonner aux sujets qui vous intéressent, de lire des articles, de publier du contenu et d'échanger grâce aux commentaires.

### Qui peut utiliser l'application ?

Tout utilisateur disposant d'un compte peut accéder aux fonctionnalités principales :

- consulter le fil d'actualité ;
- consulter les thèmes ;
- s'abonner ou se désabonner d'un thème ;
- créer un article ;
- commenter un article ;
- modifier son profil.

Les visiteurs non connectés peuvent uniquement accéder aux pages d'accueil, de connexion et d'inscription.

## Compte utilisateur

### Comment créer un compte ?

Depuis la page d'accueil, cliquez sur `S'inscrire`, puis renseignez :

- une adresse email valide ;
- un nom d'utilisateur ;
- un mot de passe.

Le mot de passe doit contenir au minimum :

- 8 caractères ;
- une majuscule ;
- une minuscule ;
- un chiffre ;
- un caractère spécial.

### Pourquoi mon inscription échoue ?

Les causes les plus fréquentes sont :

| Problème | Cause possible | Solution |
| --- | --- | --- |
| Email refusé | L'adresse email n'est pas au bon format. | Vérifier le format, par exemple `nom@email.com`. |
| Mot de passe refusé | Le mot de passe ne respecte pas les règles de sécurité. | Ajouter une majuscule, une minuscule, un chiffre et un caractère spécial. |
| Compte déjà existant | L'email ou le nom d'utilisateur est déjà utilisé. | Utiliser une autre adresse email ou un autre nom d'utilisateur. |
| Champ obligatoire vide | Un champ requis n'a pas été rempli. | Compléter tous les champs du formulaire. |

### Comment se connecter ?

Depuis la page d'accueil, cliquez sur `Se connecter`, puis renseignez :

- votre email ou votre nom d'utilisateur ;
- votre mot de passe.

Après connexion, l'application enregistre un token d'authentification dans la session du navigateur. Ce token permet d'accéder aux pages protégées.

### Pourquoi je n'arrive pas à me connecter ?

| Problème | Cause possible | Solution |
| --- | --- | --- |
| Message d'erreur à la connexion | Identifiant ou mot de passe incorrect. | Vérifier l'email, le nom d'utilisateur et le mot de passe. |
| Bouton désactivé | Le formulaire est invalide ou incomplet. | Remplir tous les champs obligatoires. |
| Retour à la page de connexion | La session est absente ou expirée. | Se reconnecter. |
| Erreur serveur | L'API backend n'est peut-être pas lancée. | Vérifier que le backend est disponible sur `http://localhost:9090`. |

### Comment se déconnecter ?

Utilisez le bouton de déconnexion dans l'interface. La déconnexion supprime le token de session côté frontend. Après déconnexion, les pages protégées ne sont plus accessibles.

## Navigation

### Pourquoi certaines pages ne sont pas accessibles ?

Les pages suivantes nécessitent une connexion :

- fil d'actualité ;
- création d'article ;
- détail d'un article ;
- thèmes ;
- profil utilisateur.

Si vous tentez d'accéder à une page protégée sans être connecté, l'application vous redirige vers le parcours d'authentification.

### Que faire si une page n'existe pas ?

Si l'URL ne correspond à aucune route connue, l'application affiche une page `Not Found`. Dans ce cas, revenez à l'accueil ou utilisez la navigation de l'application.

## Thèmes et abonnements

### À quoi servent les thèmes ?

Les thèmes permettent de regrouper les articles par sujet technique. Un utilisateur peut s'abonner aux thèmes qui l'intéressent afin de personnaliser son expérience.

### Comment s'abonner à un thème ?

Depuis la page des thèmes, cliquez sur le bouton d'abonnement du thème souhaité.

Si l'abonnement réussit, l'état du thème est mis à jour et le thème apparaît comme déjà suivi.

### Pourquoi je ne peux pas m'abonner à un thème ?

| Problème | Cause possible | Solution |
| --- | --- | --- |
| Vous êtes déjà abonné | L'abonnement existe déjà. | Aucune action nécessaire. |
| Thème introuvable | Le thème demandé n'existe pas ou son identifiant est invalide. | Recharger la page des thèmes. |
| Erreur d'authentification | La session utilisateur n'est plus valide. | Se reconnecter. |
| Erreur réseau | Le backend n'est pas disponible. | Vérifier que l'API est lancée. |

### Comment se désabonner d'un thème ?

Depuis la page profil, utilisez le bouton de désabonnement associé au thème. Après désabonnement, la liste des thèmes suivis est mise à jour.

### Pourquoi je ne peux pas me désabonner ?

Les causes possibles sont :

- l'abonnement n'existe plus ;
- le thème n'existe pas ;
- la session utilisateur a expiré ;
- l'API backend ne répond pas.

La solution la plus simple consiste à recharger la page, puis à se reconnecter si nécessaire.

## Articles

### Comment consulter les articles ?

Après connexion, accédez au fil d'actualité. Les articles disponibles y sont affichés avec leurs informations principales.

### Comment trier les articles ?

Le backend accepte deux valeurs de tri :

- `desc` pour afficher les articles du plus récent au plus ancien ;
- `asc` pour afficher les articles du plus ancien au plus récent.

Si aucun tri n'est précisé, le tri par défaut est décroissant.

### Pourquoi le tri ne fonctionne pas ?

Si une valeur autre que `asc` ou `desc` est envoyée, l'API renvoie une erreur de type `400 Bad Request`.

Solution : utiliser uniquement `asc` ou `desc`.

### Comment créer un article ?

Depuis la page de création d'article, renseignez :

- un thème ;
- un titre ;
- un contenu.

Tous les champs sont obligatoires. Une fois le formulaire validé, l'article est créé et associé à l'utilisateur connecté.

### Pourquoi la création d'article échoue ?

| Problème | Cause possible | Solution |
| --- | --- | --- |
| Thème manquant | Aucun thème n'a été sélectionné. | Sélectionner un thème. |
| Titre vide | Le titre est obligatoire. | Renseigner un titre. |
| Contenu vide | Le contenu est obligatoire. | Renseigner le contenu de l'article. |
| Thème introuvable | Le thème sélectionné n'existe plus. | Recharger la liste des thèmes. |
| Session expirée | Le token d'authentification n'est plus valide. | Se reconnecter. |

### Pourquoi un article est introuvable ?

Un article peut être introuvable si :

- l'identifiant dans l'URL est incorrect ;
- l'article n'existe plus ;
- l'URL a été modifiée manuellement.

Dans ce cas, retournez au fil d'actualité et ouvrez l'article depuis la liste.

## Commentaires

### Comment commenter un article ?

Ouvrez le détail d'un article, écrivez votre commentaire dans le champ prévu, puis validez l'envoi.

### Pourquoi mon commentaire n'est pas envoyé ?

| Problème | Cause possible | Solution |
| --- | --- | --- |
| Commentaire vide | Le contenu est obligatoire. | Écrire un commentaire avant l'envoi. |
| Article introuvable | L'article n'existe pas ou l'URL est incorrecte. | Revenir au fil d'actualité et rouvrir l'article. |
| Session expirée | Le token utilisateur est absent ou invalide. | Se reconnecter. |
| Erreur réseau | Le backend ne répond pas. | Vérifier que l'API est lancée. |

## Profil utilisateur

### Que puis-je modifier dans mon profil ?

La page profil permet de modifier :

- l'adresse email ;
- le nom d'utilisateur ;
- le mot de passe.

Les règles de validation sont les mêmes que lors de l'inscription.

### Pourquoi la mise à jour du profil échoue ?

| Problème | Cause possible | Solution |
| --- | --- | --- |
| Email invalide | L'adresse email n'est pas au bon format. | Corriger l'email. |
| Nom d'utilisateur vide | Le champ username est obligatoire. | Renseigner un nom d'utilisateur. |
| Mot de passe invalide | Le mot de passe ne respecte pas les règles de sécurité. | Utiliser un mot de passe plus robuste. |
| Email ou username déjà utilisé | Les informations sont déjà associées à un autre compte. | Choisir d'autres informations. |
| Erreur d'authentification | La session n'est plus valide. | Se reconnecter. |

## Scénarios complets

### Scénario 1 : Je m'inscris et découvre des articles

1. Cliquez sur `S'inscrire` depuis la page d'accueil
2. Remplissez le formulaire (email, username, mot de passe robuste)
3. Cliquez sur `S'enregistrer`
4. Vous êtes redirigé vers le fil d'actualité
5. Consultez les articles publiés par d'autres développeurs

**Durée** : 2 minutes
**Prérequis** : Email valide, mot de passe robuste

### Scénario 2 : Je publie un article technique

1. Allez dans `Créer un article`
2. Sélectionnez un thème (ex. "JavaScript")
3. Entrez un titre : "Comment optimiser les boucles"
4. Entrez votre contenu technique
5. Cliquez `Publier`
6. L'article apparaît immédiatement dans le fil des autres utilisateurs

**Durée** : 5 minutes
**Prérequis** : Être connecté

## Erreurs courantes

### Erreur 400 Bad Request

Cette erreur indique généralement que les données envoyées ne sont pas valides.

Exemples :

- email au mauvais format ;
- mot de passe trop faible ;
- titre d'article vide ;
- commentaire vide ;
- tri différent de `asc` ou `desc`.

Solution : vérifier les champs du formulaire ou la valeur envoyée à l'API.

### Erreur 401 Unauthorized

Cette erreur indique que l'utilisateur n'est pas authentifié ou que son token est invalide.

Solutions :

- se reconnecter ;
- vérifier que le navigateur accepte le stockage de session ;
- fermer puis rouvrir l'application ;
- vérifier que le backend est bien lancé.

### Erreur 404 Not Found

Cette erreur indique que la ressource demandée n'existe pas.

Exemples :

- article introuvable ;
- thème introuvable ;
- abonnement introuvable ;
- URL incorrecte.

Solution : revenir à la page précédente, recharger les données, puis réessayer depuis l'interface.

### Erreur 409 Conflict

Cette erreur indique qu'une action entre en conflit avec une donnée existante.

Exemples :

- email ou nom d'utilisateur déjà utilisé ;
- abonnement déjà existant.

Solution : utiliser une autre valeur ou vérifier l'état actuel de l'action.

## Dépannage local

### Le frontend ne charge pas les données

Vérifiez que le backend est lancé sur :

```text
http://localhost:9090
```

Vérifiez aussi que le frontend est lancé sur :

```text
http://localhost:4200
```

Le backend autorise actuellement les requêtes CORS provenant de `http://localhost:4200`.

### L'application affiche des erreurs réseau

Les causes possibles sont :

- le backend n'est pas lancé ;
- le port `9090` est déjà utilisé ;
- la base de données MySQL n'est pas disponible ;
- l'URL de l'API appelée par le frontend n'est pas correcte.

Solution : lancer le backend, vérifier la configuration MySQL, puis recharger l'application.

### Le backend ne démarre pas

Vérifiez les points suivants :

- Java 21 est installé ;
- MySQL est lancé ;
- la base `mdd_database` existe ;
- les identifiants MySQL sont correctement renseignés si votre installation les exige ;
- le port `9090` est disponible.

### La connexion à la base de données échoue

Créer la base si elle n'existe pas :

```sql
CREATE DATABASE mdd_database;
```

Puis vérifier la configuration dans :

```text
back/src/main/resources/application.properties
```

Si MySQL demande un utilisateur et un mot de passe, ajouter :

```properties
spring.datasource.username=votre_utilisateur
spring.datasource.password=votre_mot_de_passe
```

### Le frontend ne démarre pas

Depuis le dossier `front`, vérifier que les dépendances sont installées :

```bash
npm install
```

Puis lancer l'application :

```bash
npm start
```

Si une erreur apparaît, vérifier la version de Node.js et supprimer puis réinstaller les dépendances si nécessaire.

## Bonnes pratiques utilisateur

- Utiliser un mot de passe robuste et unique.
- Se déconnecter après utilisation sur un ordinateur partagé.
- Ne pas modifier manuellement les URL d'articles ou de thèmes.
- Recharger la page si une donnée semble obsolète après un abonnement ou un désabonnement.
- Vérifier que le backend est lancé avant de tester les fonctionnalités connectées.
