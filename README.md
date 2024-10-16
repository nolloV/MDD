# MDD API
![MDD](./front/src/assets/logo_p6.png)

## Description
ORION souhaite créer le prochain réseau social dédié aux développeurs : MDD (Monde de Dév). Le but du réseau social MDD est d’aider les développeurs qui cherchent un travail, grâce à la mise en relation, en encourageant les liens et la collaboration entre pairs qui ont des intérêts communs. MDD pourrait devenir un vivier pour le recrutement des profils manquants des entreprises.

Avant de lancer MDD auprès d’un large public, l’entreprise veut le tester avec une version minimale déployée en interne (aussi nommé MVP : Minimum Viable Product).

Le MVP permettra aux utilisateurs de s’abonner à des sujets liés à la programmation (comme JavaScript, Java, Python, Web3, etc.). Son fil d’actualité affichera chronologiquement les articles correspondants. L’utilisateur pourra également écrire des articles et poster des commentaires.

## Prérequis

- Java 21 ou supérieur
- Maven
- Node.js et npm
- MySQL
- Angular 14

## Installation des Prérequis

### Java 21

1. Téléchargez et installez Java 21 depuis le site officiel d'Oracle :
    - [Oracle JDK 21](https://www.oracle.com/java/technologies/javase-jdk21-downloads.html)

2. Vérifiez l'installation en exécutant la commande suivante dans votre terminal :
    ```bash
    java -version
    ```

### Maven

1. Téléchargez et installez Maven depuis le site officiel :
    - [Apache Maven](https://maven.apache.org/download.cgi)

2. Ajoutez Maven à votre `PATH` et vérifiez l'installation en exécutant la commande suivante dans votre terminal :
    ```bash
    mvn -version
    ```

### Node.js et npm

1. Téléchargez et installez Node.js (qui inclut npm) depuis le site officiel :
    - [Node.js](https://nodejs.org/)

2. Vérifiez l'installation en exécutant les commandes suivantes dans votre terminal :
    ```bash
    node -v
    npm -v
    ```

### MySQL

1. Téléchargez et installez MySQL depuis le site officiel :
    - [MySQL Community Server](https://dev.mysql.com/downloads/mysql/)

2. Démarrez le serveur MySQL et connectez-vous en utilisant le client de votre choix (par exemple, MySQL Workbench ou la ligne de commande).

3. Créez une base de données pour le projet :
    ```sql
    CREATE DATABASE mdd_db;
    ```

## Installation du Projet

### Backend

1. Clonez le dépôt :
    ```bash
    git clone <https://github.com/nolloV/MDD.git>
    cd back
    ```

2. Configurez la base de données dans `back/src/main/resources/application.properties` :
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/mdd_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=secret
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    ```

3. Installez les dépendances et compilez le projet :
    ```bash
    mvn clean install
    ```

4. Démarrez l'application :
    ```bash
    mvn spring-boot:run
    ```

### Frontend

1. Allez dans le répertoire `front` :
    ```bash
    cd ../front
    ```

2. Installez les dépendances :
    ```bash
    npm install
    ```

3. Démarrez le serveur de développement :
    ```bash
    ng serve
    ```

4. Accédez à l'application à l'adresse `http://localhost:4200/`.

## Utilisation

### Endpoints de l'API

- **Utilisateurs**
  - `GET /users` : Récupérer tous les utilisateurs
  - `GET /users/{id}` : Récupérer un utilisateur par ID
  - `POST /users` : Créer un nouvel utilisateur
  - `PUT /users/{id}` : Mettre à jour un utilisateur
  - `DELETE /users/{id}` : Supprimer un utilisateur
  - `PUT /users/username/{username}` : Mettre à jour un utilisateur par nom d'utilisateur
  - `POST /users/subscribe/{userId}/{themeId}` : Abonner un utilisateur à un thème
  - `POST /users/unsubscribe/{userId}/{themeId}` : Désabonner un utilisateur d'un thème

- **Articles**
  - `GET /articles` : Récupérer tous les articles
  - `GET /articles/{id}` : Récupérer un article par ID
  - `POST /articles` : Créer un nouvel article
  - `PUT /articles/{id}` : Mettre à jour un article
  - `DELETE /articles/{id}` : Supprimer un article

- **Commentaires**
  - `GET /comments` : Récupérer tous les commentaires
  - `GET /comments/{id}` : Récupérer un commentaire par ID
  - `POST /comments` : Créer un nouveau commentaire
  - `PUT /comments/{id}` : Mettre à jour un commentaire
  - `DELETE /comments/{id}` : Supprimer un commentaire
  - `GET /comments/article/{articleId}` : Récupérer les commentaires par ID d'article

- **Thèmes**
  - `GET /themes` : Récupérer tous les thèmes
  - `GET /themes/{id}` : Récupérer un thème par ID
  - `POST /themes` : Créer un nouveau thème
  - `PUT /themes/{id}` : Mettre à jour un thème
  - `DELETE /themes/{id}` : Supprimer un thème
  - `GET /themes/title/{title}` : Récupérer un thème par titre

### Authentification

L'API utilise JWT pour l'authentification. Les endpoints protégés nécessitent un token JWT valide dans l'en-tête `Authorization`.

## Configuration

Les propriétés de configuration sont définies dans le fichier `application.properties` pour le backend et dans les fichiers de configuration Angular pour le frontend.

## Déploiement

Pour déployer l'application, vous pouvez utiliser des outils comme Docker, Kubernetes, ou des services cloud comme AWS, Azure, ou Google Cloud.

### Contribuer
Les contributions sont les bienvenues ! Veuillez soumettre une pull request ou ouvrir une issue pour discuter des changements que vous souhaitez apporter.

### Licence
Ce projet est sous licence MIT. Voir le fichier LICENSE pour plus de détails.