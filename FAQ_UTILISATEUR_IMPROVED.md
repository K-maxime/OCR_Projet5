# ❓ FAQ Utilisateur — MDD

> **Guide complet pour bien utiliser l'application MDD**  
> Trouvez des réponses à vos questions, résolvez les problèmes courants et explorez les fonctionnalités.

---

## 📋 Table des matières

1. [Guide rapide par profil](#-guide-rapide-par-profil)
2. [Presentation générale](#-présentation-générale)
3. [Gestion de compte](#-gestion-de-compte)
4. [Navigation](#-navigation)
5. [Thèmes et abonnements](#-thèmes-et-abonnements)
6. [Articles et fil d'actualité](#-articles-et-fil-dactalité)
7. [Commentaires](#-commentaires)
8. [Profil utilisateur](#-profil-utilisateur)
9. [Erreurs courantes](#-erreurs-courantes)
10. [Dépannage](#-dépannage-technique)
11. [Bonnes pratiques](#-bonnes-pratiques-de-sécurité)

---

## 🎯 Guide rapide par profil

### Je suis nouveau développeur — Je découvre l'app

**En 10 minutes, je fais :**

1. ✅ [Créer un compte](#comment-créer-un-compte-)
2. ✅ [M'abonner à 3 thèmes](#comment-sabonner-à-un-thème-)
3. ✅ [Consulter le fil d'actualité](#comment-consulter-les-articles-)

**Temps** : ~10 min | **Prérequis** : Email valide

---

### Je veux partager mon expertise — Je publie des articles

**Ma checklist :**

1. ✅ [Me connecter](#comment-se-connecter-)
2. ✅ [Créer un article](#comment-créer-un-article-)
3. ✅ [Répondre aux commentaires](#comment-commenter-un-article-)

**Temps** : ~15 min par article | **Prérequis** : Compte créé

---

### J'ai une erreur — Je dépanne vite

**Diagnostic rapide :**

| Symptôme | Section |
| --- | --- |
| Page blanche, rien ne charge | [Dépannage technique](#dépannage-technique) |
| Impossible de se connecter | [Gestion de compte](#-gestion-de-compte) |
| Message d'erreur bizarre | [Erreurs courantes](#-erreurs-courantes) |

---

## 💡 Présentation générale

### À quoi sert MDD ?

**MDD** (*Monde de Dev*) est une **plateforme communautaire pour développeurs**.

Elle permet de :
- 📚 Consulter des **thèmes techniques** (JavaScript, Python, DevOps, etc.)
- 📰 Lire des **articles** publiés par la communauté
- ✍️ **Publier vos propres articles** et partager votre expertise
- 💬 **Commenter** et discuter avec d'autres devs
- 🔔 **Personnaliser votre expérience** en vous abonnant aux sujets qui vous intéressent

**En résumé** : C'est LinkedIn pour les devs, mais open et décentralisé. 🚀

---

### Qui peut utiliser MDD ?

#### ✅ Connecté

Vous avez accès à **toutes les fonctionnalités** :

```
├─ Consulter le fil d'actualité
├─ Consulter les thèmes
├─ S'abonner/désabonner aux thèmes
├─ Créer un article
├─ Consulter un article en détail
├─ Commenter un article
└─ Modifier votre profil
```

#### ❌ Non connecté

Vous ne pouvez accéder qu'à :
- Page d'accueil
- Page de connexion
- Page d'inscription

**Astuce** : Si une page vous redirige vers la connexion, c'est qu'elle est protégée.

---

## 🔐 Gestion de compte

### Comment créer un compte ?

**Étape 1 : Aller sur la page d'inscription**

Depuis la page d'accueil, cliquez sur `S'inscrire` (bouton en haut à droite).

**Étape 2 : Remplir le formulaire**

Vous devez entrer :

| Champ | Règle | Exemple |
| --- | --- | --- |
| **Email** | Format valide | alice@example.com |
| **Nom d'utilisateur** | Unique, pas d'espaces | alice_dev ou aliceDev |
| **Mot de passe** | Min 8 caractères + majuscule + minuscule + chiffre + spécial | MyPass@123 |

**Exigences du mot de passe** :

```
❌ "password"           → Pas assez sécurisé
❌ "Password1"        → Pas de caractère spécial
❌ "PASSWORD@123"     → Pas de minuscule
✅ "MyPass@2025"      → Valide !
```

**Étape 3 : Valider**

Cliquez sur `S'enregistrer`. Vous êtes redirigé vers le fil d'actualité et connecté automatiquement. 🎉

---

### Pourquoi mon inscription échoue ?

| Problème | Cause | Solution |
| --- | --- | --- |
| **Email refusé** | Format invalide | Utiliser format complet : nom@domaine.ext |
| **Email déjà utilisé** | Compte existant | Utiliser une autre adresse ou [se connecter](#comment-se-connecter-) |
| **Mot de passe refusé** | Ne respecte pas les règles | Ajouter majuscule + minuscule + chiffre + spécial |
| **Champ vide** | Un champ obligatoire manque | Compléter tous les champs en rouge |
| **Erreur serveur** | Backend ne répond pas | Vérifier que le backend tourne sur http://localhost:9090 |

---

### Comment se connecter ?

**Étape 1 : Aller sur la page de connexion**

Depuis la page d'accueil, cliquez sur `Se connecter`.

**Étape 2 : Entrer vos identifiants**

```
┌──────────────────────────┐
│ Email ou nom d'utilisateur │
│ [________________]        │
│                          │
│ Mot de passe             │
│ [________________]        │
│                          │
│ [Se connecter]           │
└──────────────────────────┘
```

**Vous pouvez utiliser :**
- Votre email : `alice@example.com`
- OU votre nom d'utilisateur : `alice_dev`

**Étape 3 : Accéder à l'appli**

Vous êtes redirigé vers le fil d'actualité. ✅

**Données stockées** : Votre token JWT est conservé dans la session du navigateur. Vous restez connecté jusqu'à :
- Vous cliquez sur "Déconnexion"
- Vous fermez le navigateur
- 24h (expiration du token)

---

### Pourquoi je n'arrive pas à me connecter ?

| Problème | Cause | Solution |
| --- | --- | --- |
| **Erreur "identifiants invalides"** | Email/username ou password incorrect | Vérifier la casse du password et l'email/username exact |
| **Bouton désactivé** | Formulaire incomplet | Remplir les 2 champs (email/username + password) |
| **Redirection vers connexion** | Session expirée | Se reconnecter |
| **Erreur 401** | Token invalide ou expiré | Se reconnecter |
| **Erreur 500** | Backend en erreur | Vérifier que le backend tourne sur http://localhost:9090 |
| **Page blanche** | API ne répond pas | Attendre quelques secondes et réessayer |

**Je suis bloqué** :
1. Fermer l'onglet du navigateur complètement
2. Rouvrir et aller sur http://localhost:4200
3. Réessayer la connexion

---

### Comment me déconnecter ?

**Option 1 : Bouton Déconnexion**

Cliquez sur votre nom d'utilisateur en haut à droite → `Déconnexion`.

Vous êtes redirigé vers la page d'accueil.

**Option 2 : Fermer l'app**

Fermer le navigateur supprime aussi le token.

**Qu'arrive-t-il après** :
- ✅ Votre token JWT est supprimé
- ✅ Les pages protégées ne sont plus accessibles
- ✅ Vous êtes redirigé vers la page de connexion en cas d'accès

---

## 🧭 Navigation

### Pourquoi certaines pages ne sont pas accessibles ?

**Pages protégées** (nécessitent une connexion) :

```
✅ Connecté   → Accès à tout
├─ Fil d'actualité
├─ Articles détail
├─ Création d'article
├─ Thèmes & Abonnements
└─ Profil utilisateur

❌ Non connecté → Redirection vers connexion
```

**Si vous tentez d'accéder à une page protégée** :

```
Vous êtes redirigé automatiquement vers :
/login → Vous vous connectez → Vous pouvez accéder à la page
```

**Astuce** : Gardez l'URL en tête. Après connexion, vous serez redirigé vers la page que vous aviez demandée.

---

### Que faire si une page n'existe pas (404) ?

**Vous voyez "Not Found"** ou "Page non trouvée"

**Cause** : L'URL ne correspond à aucune route de l'app

**Solution** :
1. Cliquez sur le logo pour revenir à l'accueil
2. Utilisez la navigation (menu en haut)
3. Vérifiez l'URL dans la barre d'adresse

**Exemple** : 
```
❌ http://localhost:4200/articles/999999  → Article inexistant
✅ http://localhost:4200/                  → Retour à l'accueil
```

---

## 📚 Thèmes et abonnements

### À quoi servent les thèmes ?

**Les thèmes groupent les articles par sujet technique.**

Exemples de thèmes :
- JavaScript
- Python
- DevOps
- Cloud
- Base de données
- Sécurité
- etc.

**Pourquoi s'abonner** :

```
SANS abonnement          AVEC abonnement
────────────────────────────────────────
Voir tous les articles   Voir articles filtrés
Pas de perso             Fil personnalisé
```

Vous n'êtes **jamais forcé** de vous abonner. Vous pouvez consulter tous les articles.

---

### Comment s'abonner à un thème ?

**Étape 1 : Aller à la page Thèmes**

Menu → `Thèmes`

**Étape 2 : Sélectionner un thème**

Vous voyez la liste de tous les thèmes disponibles.

**Étape 3 : Cliquer sur le bouton**

```
┌──────────────────────────┐
│ JavaScript               │
│ [S'abonner]              │ ← Cliquez ici
└──────────────────────────┘
```

**Étape 4 : Confirmation**

Le bouton devient `Désabonner`. Vous êtes abonné. ✅

**Effet immédiat** :
- Articles de ce thème apparaissent dans votre fil
- Vous recevez les nouveaux articles du thème

---

### Pourquoi je ne peux pas m'abonner à un thème ?

| Problème | Cause | Solution |
| --- | --- | --- |
| **Bouton grisé** | Vous êtes déjà abonné | Regardez le bouton, il dit "Désabonner" |
| **Erreur 404** | Le thème n'existe pas | Recharger la page des thèmes |
| **Erreur 401** | Session expirée | Se reconnecter |
| **Erreur 500** | Backend en erreur | Attendre et réessayer |
| **Rien ne se passe** | Mauvaise connexion | Vérifier que l'API répond (Swagger) |

**Test rapide** :
1. Recharger la page (`F5`)
2. Vérifier l'état du thème
3. Réessayer

---

### Comment me désabonner d'un thème ?

**Méthode 1 : Depuis la page Thèmes**

Thèmes → Trouver le thème → Cliquer `Désabonner`

**Méthode 2 : Depuis le profil**

Profil → Section "Vos abonnements" → Cliquer `Désabonner`

**Après désabonnement** :
- Articles du thème disparaissent de votre fil
- Le thème redevient "S'abonner"

---

## 📰 Articles et fil d'actualité

### Comment consulter les articles ?

**Étape 1 : Aller au fil d'actualité**

Menu → `Fil d'actualité` (après connexion)

**Étape 2 : Voir les articles**

```
┌──────────────────────────────────────────┐
│ [Article 1]                              │
│ Auteur: Alice | Thème: JavaScript        │
│ "Introduction à React"                   │
│ [Lire l'article]                         │
├──────────────────────────────────────────┤
│ [Article 2]                              │
│ Auteur: Bob | Thème: DevOps              │
│ "Docker pour débutants"                  │
│ [Lire l'article]                         │
└──────────────────────────────────────────┘
```

**Étape 3 : Cliquer pour voir le détail**

Cliquez sur un article pour lire le contenu complet et commenter.

**Par défaut** : Articles triés du plus récent au plus ancien.

---

### Comment trier les articles ?

**Tri disponible** :

| Ordre | Description | Commande |
| --- | --- | --- |
| **Décroissant** (défaut) | Plus récent → Plus ancien | `?sort=desc` |
| **Croissant** | Plus ancien → Plus récent | `?sort=asc` |

**Exécuter le tri** :

Utiliser le selector de tri dans l'interface, ou ajouter à l'URL :

```
http://localhost:4200/articles?sort=asc    → Articles du plus ancien
http://localhost:4200/articles?sort=desc   → Articles du plus récent
http://localhost:4200/articles             → Défaut (desc)
```

**Erreur si** :
- Valeur autre que `asc` ou `desc` → API renvoie erreur 400
- Solution : Utiliser uniquement `asc` ou `desc`

---

### Comment créer un article ?

**Étape 1 : Aller à "Créer un article"**

Menu → `Créer un article`

**Étape 2 : Remplir le formulaire**

```
┌─────────────────────────────────────┐
│ Thème *                             │
│ [Sélectionner un thème ▼]           │
│                                     │
│ Titre *                             │
│ [Introduction à TypeScript.......]  │
│                                     │
│ Contenu *                           │
│ [TypeScript est un sur-ensemble.... │
│  ............................... ]   │
│                                     │
│ [Publier]                           │
└─────────────────────────────────────┘
```

**Champs obligatoires** :
- **Thème** : Sélectionner dans la liste
- **Titre** : Max 200 caractères
- **Contenu** : Votre article (min 10 caractères)

**Étape 3 : Publier**

Cliquez `Publier`. Votre article apparaît dans le fil. 🎉

---

### Pourquoi la création échoue ?

| Problème | Cause | Solution |
| --- | --- | --- |
| **Thème manquant** | Aucun thème sélectionné | Ouvrir le dropdown et sélectionner |
| **Titre vide** | Champ non rempli | Entrer un titre |
| **Contenu vide** | Champ non rempli | Entrer le contenu de l'article |
| **Thème inexistant** | ID invalide | Recharger et sélectionner un thème valide |
| **Session expirée** | Token invalide | Se reconnecter |
| **Erreur 500** | Erreur serveur | Vérifier les logs et réessayer |

---

### Pourquoi un article est introuvable ?

| Cas | Cause | Solution |
| --- | --- | --- |
| URL modifiée manuellement | ID d'article invalide | Retourner au fil et ouvrir depuis la liste |
| Article supprimé | Modérateur a supprimé | Voir la section "Gestion de contenu" |
| Mauvais lien | Lien cassé | Copier l'URL correcte depuis le fil |

---

## 💬 Commentaires

### Comment commenter un article ?

**Étape 1 : Ouvrir un article**

Fil d'actualité → Cliquer sur un article

**Étape 2 : Aller à la section commentaires**

Scroller jusqu'en bas → Section "Commentaires"

**Étape 3 : Écrire votre commentaire**

```
┌─────────────────────────────────────┐
│ Votre commentaire                   │
│ [.................................] │
│ [.................................] │
│                                     │
│ [Envoyer]                           │
└─────────────────────────────────────┘
```

**Étape 4 : Envoyer**

Cliquez `Envoyer`. Votre commentaire apparaît immédiatement. ✅

---

### Pourquoi mon commentaire n'est pas envoyé ?

| Problème | Cause | Solution |
| --- | --- | --- |
| **Champ vide** | Contenu absent | Écrire un commentaire avant d'envoyer |
| **Article inexistant** | URL incorrecte | Revenir au fil et ouvrir depuis la liste |
| **Bouton désactivé** | Formulaire invalide | Remplir le champ |
| **Session expirée** | Token expiré | Se reconnecter |
| **Erreur 500** | Serveur en erreur | Attendre et réessayer |

---

## 👤 Profil utilisateur

### Que puis-je modifier dans mon profil ?

**Sections modifiables** :

```
┌─────────────────────────────────────┐
│ Email                               │
│ [alice@example.com..................] │
│                                     │
│ Nom d'utilisateur                   │
│ [alice_dev.................] │
│                                     │
│ Mot de passe                        │
│ [••••••••••..................] │
│                                     │
│ [Enregistrer les modifications]     │
└─────────────────────────────────────┘
```

**Vos abonnements** (lecture seule) :
- Liste des thèmes auxquels vous êtes abonné
- Bouton "Désabonner" pour chaque thème

---

### Pourquoi la mise à jour du profil échoue ?

| Problème | Cause | Solution |
| --- | --- | --- |
| **Email invalide** | Format incorrect | Utiliser format complet : nom@domaine.ext |
| **Email utilisé** | Déjà associé à un compte | Choisir une autre adresse |
| **Username vide** | Champ obligatoire | Entrer un nom d'utilisateur |
| **Username utilisé** | Déjà pris | Choisir un autre username |
| **Mot de passe faible** | Ne respecte pas les règles | Ajouter majuscule + minuscule + chiffre + spécial |
| **Mot de passe incorrect** | Ancien password faux | Vérifier le password actuel |
| **Session expirée** | Token expiré | Se reconnecter |

---

## ❌ Erreurs courantes

### Erreur 400 Bad Request

**Signification** : Les données que vous envoyez sont invalides.

**Exemples** :
- Email au mauvais format
- Mot de passe trop faible
- Titre d'article vide
- Tri avec valeur autre que `asc` / `desc`

**Solution** : Vérifier les champs du formulaire ou l'URL.

---

### Erreur 401 Unauthorized

**Signification** : Vous n'êtes pas authentifié ou votre token est expiré.

**Cas** :
- Vous êtes non connecté
- Votre session a expiré (24h)
- Vous avez fermé le navigateur

**Solution** :
1. Se reconnecter
2. Vérifier que le navigateur accepte les cookies/sessionStorage
3. Fermer et rouvrir l'app

---

### Erreur 404 Not Found

**Signification** : La ressource demandée n'existe pas.

**Exemples** :
- Article ID 999 n'existe pas
- Thème supprimé
- URL mal écrite

**Solution** : Revenir en arrière et accéder depuis la navigation.

---

### Erreur 409 Conflict

**Signification** : Action en conflit avec une ressource existante.

**Exemples** :
- Email déjà utilisé
- Nom d'utilisateur déjà pris
- Abonnement déjà existant

**Solution** : Utiliser des valeurs différentes ou vérifier l'état actuel.

---

### Erreur 500 Internal Server Error

**Signification** : Erreur côté serveur (backend).

**Cas** :
- Base de données indisponible
- Backend en erreur
- Ressource corrompue

**Solution** :
1. Attendre quelques secondes
2. Recharger la page
3. Vérifier que le backend tourne sur http://localhost:9090
4. Regarder les logs du backend

---

## 🔧 Dépannage technique

### Le frontend ne charge pas

**Symptôme** : Page blanche, rien n'apparaît.

**Checklist** :

```
1. ✅ Frontend lancé ?
   → http://localhost:4200 dans la barre d'adresse
   → Doit voir quelque chose

2. ✅ Backend lancé ?
   → http://localhost:9090/swagger-ui/index.html doit répondre
   
3. ✅ Console navigateur
   → Ouvrir F12 → Onglet Console
   → Chercher erreurs en rouge

4. ✅ Vider le cache
   → Ctrl+Maj+Suppr (Windows) ou Cmd+Maj+Suppr (Mac)
   → Cocher "Cache"
   → Rafraîchir
```

---

### Le backend ne démarre pas

**Checklist** :

```
1. ✅ Java 21 installé ?
   → Terminal : java -version
   → Doit afficher Java 21.x

2. ✅ MySQL lancé ?
   → Terminal : mysql -u root -p
   → Doit se connecter

3. ✅ Base mdd_database existe ?
   → MySQL : SHOW DATABASES;
   → Doit contenir "mdd_database"

4. ✅ application.properties correct ?
   → Vérifier back/src/main/resources/application.properties
   → URL : jdbc:mysql://localhost:3306/mdd_database

5. ✅ Logs du backend
   → Terminal : cd back && ./mvnw spring-boot:run
   → Chercher erreurs en rouge
```

---

### Données cellulaires / Hors ligne

**Consommation** :
- Par session : ~2 MB
- Images : compressées automatiquement

**Mode hors ligne** :
- Certaines pages se chargent en cache si déjà visitées
- Édition impossible (besoin de connexion)

---

## 🔒 Bonnes pratiques de sécurité

### Protéger votre compte

```
✅ DO
├─ Utiliser un mot de passe robuste (12+ caractères)
├─ Ne pas partager vos identifiants
├─ Se déconnecter après utilisation
├─ Utiliser une connexion sécurisée (HTTPS en prod)
└─ Changer le password régulièrement

❌ DON'T
├─ Utiliser "password123"
├─ Partager votre token
├─ Laisser l'app ouverte sur un ordi partagé
├─ Modifier manuellement les URLs
└─ Éditer le localStorage (navigateur)
```

### HTTPS en production

**Avant de déployer**, utiliser HTTPS :

```
🔒 https://mdd.example.com  ← Sécurisé
🔓 http://localhost:4200    ← Développement local
```

---

## 📞 Besoin d'aide ?

### Je n'ai pas trouvé ma réponse

1. Chercher dans cette FAQ (Ctrl+F)
2. Vérifier les logs du navigateur (F12)
3. Vérifier l'état du backend (Swagger)
4. Contacter l'équipe dev

### Signaler un bug

1. Reproduire le problème
2. Noter les étapes exactes
3. Captures d'écran du problème
4. Contacter : dev@mdd.local

---

**Dernière mise à jour** : 27 mai 2026  
**Version FAQ** : 1.0.0

