# ReprisesExamens

Application de gestion des requêtes de reprises d'examens (du département d'informatique)

## Environnement nécessaire

- [Java jdk 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven version 3.8.5](https://maven.apache.org/download.cgi?Preferred=ftp://ftp.osuosl.org/pub/apache/)
- [Node.js version 16.15.0](https://nodejs.org/en/download/)

## Installer les dépendances

```sh
./mvnw clean install
```

## Lancer l'application

```sh
./mvnw spring-boot:run
```

Cela sert l'application à l'adresse `http://localhost:8080`. Une API REST est exposée à l'adresse `http://localhost:8080/api`.

## Lancer le front-end en mode développement

Cette méthode sert le front-end et le backend sur le même port. Pour une expérience
de développement front-end plus conviviale avec "hot reload", vous pouvez également lancer le
front-end avec `npm`. Le back-end doit être déjà lancé comme montré plus haut.

À partir du répertoire `/frontend`:

```
npm start
```

Cela sert l'application frontend React à l'adresse `http://localhost:3000`. L'API REST sera toujours exposée à l'adresse `http://localhost:8080/api`.

## Rouler les tests

Pour lancer les test JUnit (backend) et Jest (frontend):

```sh
./mvnw verify
```
