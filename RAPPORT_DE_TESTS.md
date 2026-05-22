# Rapport de tests

Ce document présente le rapport de couverture des tests unitaires du frontend Angular.

Les données proviennent des fichiers générés dans :

```text
front/coverage
```

Sources utilisées :

- `front/coverage/lcov-report/index.html`
- `front/coverage/coverage-final.json`
- `front/coverage/clover.xml`
- `front/coverage/lcov.info`

Date de génération du rapport : 22 mai 2026 à 10:56.

## Périmètre

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

## Résultat global

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

## Analyse

La couverture globale est satisfaisante pour le projet. Les services, le guard et l'interceptor sont très bien couverts, ce qui sécurise les parties liées aux appels HTTP et à l'authentification.

Les zones les moins couvertes concernent surtout des branches conditionnelles ou des callbacks dans les composants. Pour améliorer encore le rapport, il faudrait ajouter des tests ciblés sur :

- les cas d'erreur ou d'annulation dans les formulaires ;
- les conditions d'affichage dans le fil d'actualité ;
- les redirections ou actions utilisateur dans le header ;
- les callbacks de succès et d'erreur lors des appels aux services.

## Conclusion

Les tests frontend atteignent un niveau de couverture élevé :

- 94.37% de statements ;
- 86.11% de branches ;
- 85.18% de functions ;
- 94.79% de lines.

Les seuils Jest de 70% sont respectés pour tous les indicateurs. Le frontend dispose donc d'une base de tests solide, avec quelques pistes d'amélioration ciblées sur les branches conditionnelles et certains callbacks de composants.
