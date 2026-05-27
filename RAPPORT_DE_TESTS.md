# Rapport de tests

Ce document présente les rapports de couverture des tests du projet MDD.

Les données frontend proviennent des fichiers générés dans :

```text
front/coverage
```

Sources utilisées :

- `front/coverage/lcov-report/index.html`
- `front/coverage/coverage-final.json`
- `front/coverage/clover.xml`
- `front/coverage/lcov.info`

Date de génération du rapport frontend : 22 mai 2026 à 10:56.

Les données backend proviennent du rapport généré dans :

```text
back/htmlReport
```

Date de génération du rapport backend : 27 mai 2026 à 10:58.

## Périmètre frontend

Ce rapport concerne uniquement la partie frontend du projet, située dans :

```text
front/src/app
```

Les tests sont écrits avec Jest et Angular Testing Utilities. Ils ciblent les fichiers `*.spec.ts`.

Commande de lancement des tests :

```bash
cd front
npm test
```

Commande de génération du rapport de couverture :

```bash
cd front
npm run test:coverage
```

Le rapport HTML généré est consultable ici :

```text
front/coverage/lcov-report/index.html
```

## Résultat global frontend

Le rapport global indique une bonne couverture du code frontend.

| Indicateur | Couverture | Couvert | Total |
| --- | ---: | ---: | ---: |
| Statements | 94.37% | 285 | 302 |
| Branches | 86.11% | 31 | 36 |
| Functions | 85.18% | 69 | 81 |
| Lines | 94.79% | 255 | 269 |

## Lecture des indicateurs

- `Statements` : pourcentage d'instructions exécutées pendant les tests.
- `Branches` : pourcentage de branches conditionnelles testées, par exemple les `if`, ternaires ou alternatives logiques.
- `Functions` : pourcentage de fonctions ou méthodes appelées par les tests.
- `Lines` : pourcentage de lignes exécutées pendant les tests.

Les résultats sont supérieurs à 85% sur tous les indicateurs globaux, avec une couverture particulièrement forte sur les statements et les lines.

## Seuils de couverture

La configuration Jest du projet définit des seuils globaux minimaux de 70%.

| Indicateur | Seuil minimal | Résultat obtenu | Statut |
| --- | ---: | ---: | --- |
| Statements | 70% | 94.37% | Validé |
| Branches | 70% | 86.11% | Validé |
| Functions | 70% | 85.18% | Validé |
| Lines | 70% | 94.79% | Validé |

Tous les seuils configurés sont donc respectés.

## Couverture par dossier

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

## Fichiers analysés

Le rapport couvre 17 fichiers TypeScript du frontend.

| Fichier | Statements | Branches | Functions | Lines |
| --- | ---: | ---: | ---: | ---: |
| `src/app/app.component.ts` | 100% | 100% | 100% | 100% |
| `src/app/core/service/auth.service.ts` | 100% | 100% | 100% | 100% |
| `src/app/core/service/post.service.ts` | 84.62% | 100% | 71.43% | 90.91% |
| `src/app/core/service/subject.service.ts` | 100% | 100% | 100% | 100% |
| `src/app/core/service/user.service.ts` | 100% | 100% | 100% | 100% |
| `src/app/guards/Auth.guard.ts` | 100% | 100% | 100% | 100% |
| `src/app/interceptors/Auth.interceptor.ts` | 100% | 100% | 100% | 100% |
| `src/app/pages/feed/feed.component.ts` | 93.75% | 50% | 75% | 92.86% |
| `src/app/pages/home/home.component.ts` | 100% | 100% | 100% | 100% |
| `src/app/pages/login/login.component.ts` | 86.36% | 100% | 66.67% | 85% |
| `src/app/pages/new-post/new-post.component.ts` | 95.83% | 50% | 100% | 100% |
| `src/app/pages/not-found/not-found.component.ts` | 100% | 100% | 100% | 100% |
| `src/app/pages/post/post.component.ts` | 95.45% | 100% | 83.33% | 95% |
| `src/app/pages/register/register.component.ts` | 90% | 80% | 71.43% | 88.89% |
| `src/app/pages/shared/header/header.component.ts` | 90% | 83.33% | 78.57% | 91.49% |
| `src/app/pages/subjects/subjects.component.ts` | 100% | 100% | 100% | 100% |
| `src/app/pages/user/user.component.ts` | 97.14% | 100% | 87.5% | 96.88% |

## Éléments bien couverts

Plusieurs zones importantes du frontend sont entièrement couvertes :

- le composant principal de l'application ;
- les services `auth`, `subject` et `user` ;
- le guard d'authentification ;
- l'interceptor HTTP d'authentification ;
- les pages `home`, `not-found` et `subjects`.

Ces éléments sont importants car ils touchent à la navigation, à la sécurité côté frontend et aux appels API.

## Points à améliorer

Le rapport montre quelques zones moins couvertes. Elles ne bloquent pas la validation des seuils, mais peuvent être améliorées pour renforcer la qualité des tests.

| Fichier | Élément à améliorer | Détail |
| --- | --- | --- |
| `src/app/pages/feed/feed.component.ts` | Branches | Une branche conditionnelle autour de la ligne 30 n'est pas couverte. |
| `src/app/pages/new-post/new-post.component.ts` | Branches | Une branche `if` autour de la ligne 43 n'est pas couverte. |
| `src/app/pages/login/login.component.ts` | Functions | Deux fonctions anonymes autour des lignes 47 et 63 ne sont pas couvertes. |
| `src/app/pages/register/register.component.ts` | Branches et functions | Certaines conditions autour des lignes 50 et 63, ainsi que des fonctions autour des lignes 56 et 71, ne sont pas couvertes. |
| `src/app/pages/shared/header/header.component.ts` | Statements, branches et functions | Plusieurs instructions et fonctions autour des lignes 33, 36, 44, 68 et 69 ne sont pas couvertes. |
| `src/app/core/service/post.service.ts` | Functions | Deux fonctions anonymes autour des lignes 25 et 26 ne sont pas couvertes. |
| `src/app/pages/post/post.component.ts` | Functions | Une fonction anonyme autour de la ligne 36 n'est pas couverte. |
| `src/app/pages/user/user.component.ts` | Functions | Une fonction anonyme autour de la ligne 79 n'est pas couverte. |

## Analyse frontend

La couverture globale est satisfaisante pour le projet. Les services, le guard et l'interceptor sont très bien couverts, ce qui sécurise les parties liées aux appels HTTP et à l'authentification.

Les zones les moins couvertes concernent surtout des branches conditionnelles ou des callbacks dans les composants. Pour améliorer encore le rapport, il faudrait ajouter des tests ciblés sur :

- les cas d'erreur ou d'annulation dans les formulaires ;
- les conditions d'affichage dans le fil d'actualité ;
- les redirections ou actions utilisateur dans le header ;
- les callbacks de succès et d'erreur lors des appels aux services.

## Résultat global backend

Le rapport backend est disponible dans :

```text
back/htmlReport/index.html
```

Il couvre les classes Java de l'API Spring Boot.

| Indicateur | Couverture | Couvert | Total |
| --- | ---: | ---: | ---: |
| Classes | 100% | 33 | 33 |
| Méthodes | 81,1% | 77 | 95 |
| Branches | 37,2% | 29 | 78 |
| Lignes | 70,6% | 279 | 395 |

## Couverture backend par package

| Package | Classes | Méthodes | Branches | Lignes |
| --- | ---: | ---: | ---: | ---: |
| `com.openclassrooms.mddapi` | 100% (1/1) | 50% (1/2) | N/A | 50% (1/2) |
| `com.openclassrooms.mddapi.config` | 100% (2/2) | 100% (7/7) | 100% (2/2) | 100% (56/56) |
| `com.openclassrooms.mddapi.controllers` | 100% (5/5) | 91,7% (11/12) | N/A | 92,9% (13/14) |
| `com.openclassrooms.mddapi.exceptions` | 100% (9/9) | 94,4% (17/18) | N/A | 95,8% (23/24) |
| `com.openclassrooms.mddapi.mapper` | 100% (3/3) | 43,5% (10/23) | 17,9% (10/56) | 31,2% (44/141) |
| `com.openclassrooms.mddapi.models` | 100% (3/3) | 100% (3/3) | N/A | 100% (3/3) |
| `com.openclassrooms.mddapi.security` | 100% (3/3) | 87,5% (14/16) | 75% (6/8) | 76,5% (52/68) |
| `com.openclassrooms.mddapi.services` | 100% (7/7) | 100% (14/14) | 91,7% (11/12) | 100% (87/87) |

## Éléments backend bien couverts

Les tests backend couvrent fortement les couches principales de l'API :

- les services métier, avec 100% des lignes couvertes ;
- les contrôleurs REST, avec 92,9% des lignes couvertes ;
- la configuration Spring, avec 100% des lignes couvertes ;
- les exceptions, avec 95,8% des lignes couvertes ;
- les classes du package `models`, avec 100% des lignes couvertes.

Cette couverture est intéressante car elle valide les règles métier, les endpoints REST et une grande partie du comportement attendu côté API.

## Points backend à améliorer

Le principal point faible du rapport backend concerne le package `mapper`.

| Élément | Couverture lignes | Couverture branches | Commentaire |
| --- | ---: | ---: | --- |
| `com.openclassrooms.mddapi.mapper` | 31,2% | 17,9% | Les classes générées par MapStruct contiennent beaucoup de branches défensives, notamment pour les valeurs nulles. |
| `UserMapperImpl` | 24% | 8,3% | Plusieurs chemins de mapping ne sont pas exercés par les tests. |
| `SubjectMapperImpl` | 25,9% | 8,3% | Les cas de mapping incomplets ou null ne sont pas suffisamment testés. |
| `ArticleMapperImpl` | 34,8% | 25% | Le mapping d'articles, commentaires, auteur et sujet pourrait être davantage couvert. |
| `CustomUserDetailsService` | 55,6% | N/A | Certains chemins de chargement utilisateur restent à tester. |

La couverture globale des branches backend est de 37,2%. Ce score est surtout impacté par les mappers générés. Les services, eux, affichent une très bonne couverture : 91,7% des branches et 100% des lignes.

## Analyse backend

La couverture backend est satisfaisante sur les parties les plus importantes du projet :

- les services testent les règles métier ;
- les contrôleurs valident les principaux endpoints ;
- les tests utilisent une base H2 en mémoire, ce qui permet de vérifier le comportement de l'API sans dépendre d'une base MySQL locale ;
- la sécurité est partiellement couverte avec 76,5% des lignes et 75% des branches.

Pour améliorer le score global, il faudrait ajouter des tests ciblés sur :

- les mappers MapStruct avec des objets complets ;
- les mappers MapStruct avec des objets partiellement vides ou `null` ;
- les chemins alternatifs de `CustomUserDetailsService` ;
- les branches de sécurité encore non couvertes.

## Conclusion

Les tests frontend atteignent un niveau de couverture élevé :

- 94.37% de statements ;
- 86.11% de branches ;
- 85.18% de functions ;
- 94.79% de lines.

Les tests backend couvrent également les couches importantes de l'API :

- 100% des classes ;
- 81,1% des méthodes ;
- 70,6% des lignes ;
- 100% des lignes sur les services ;
- 92,9% des lignes sur les contrôleurs.

Les seuils Jest de 70% sont respectés côté frontend. Côté backend, le rapport montre une bonne couverture des services, contrôleurs, exceptions et configurations. Le score global des branches backend reste à améliorer, principalement à cause des mappers générés par MapStruct.

Le projet dispose donc d'une base de tests solide sur le frontend et le backend, avec des pistes d'amélioration ciblées sur les branches conditionnelles frontend, les callbacks de composants et les cas particuliers de mapping côté backend.
