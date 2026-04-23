# Erreurs a eviter (retour d'experience)

Date: 2026-04-16

## 1) Bean Spring introuvable (`NoSuchBeanDefinitionException`)

Erreur observee:
- `No qualifying bean of type 'com.itu.visamanagement.services.HelloService' available`
- Contexte: echec de chargement du contexte Spring pendant `VisamanagementApplicationTests`.

Cause:
- `HelloService` n'etait pas declare comme bean Spring (`@Service`).
- Le controleur injectait `HelloService`, mais Spring ne pouvait pas l'instancier.

Correction appliquee:
- Ajout de `@Service` dans `HelloService`.
- Simplification du service pour ne plus dependre d'un objet non gere par Spring.
- Le controleur utilise maintenant `helloService.getHelloMessage()`.

Comment l'eviter:
- Toujours annoter les classes metier injectees avec `@Service` (ou `@Component`).
- Verifier que les classes a injecter sont dans un package scanne par `@SpringBootApplication`.
- Favoriser l'injection par constructeur avec champs `final`.
- Lancer `./mvnw clean test` apres ajout/modification de dependances entre composants Spring.

## 2) Avertissements vus dans les logs (non bloquants)

- Hibernate: `PostgreSQLDialect does not need to be specified explicitly`.
  - Action conseillee: retirer la propriete `hibernate.dialect` si elle est definie explicitement.
- Thymeleaf: `Cannot find template location: classpath:/templates/`.
  - Action conseillee: desactiver Thymeleaf si non utilise ou ajouter des templates si necessaire.
- `spring.jpa.open-in-view` active par defaut.
  - Action conseillee: definir explicitement `spring.jpa.open-in-view=false` si non souhaite.

## 3) Template introuvable avec Thymeleaf (`TemplateInputException`)

Erreur observee:
- `Error resolving template [Hello], template might not exist or might not be accessible by any of the configured Template Resolvers`

Cause:
- Le projet utilisait Thymeleaf (`spring-boot-starter-thymeleaf`) mais la vue etait uniquement en JSP (`src/webapp/WEB-INF/views/Hello.jsp`).
- Thymeleaf cherche par defaut des vues dans `src/main/resources/templates`.

Correction appliquee:
- Ajout du template `src/main/resources/templates/Hello.html`.
- Suppression des proprietes JSP `spring.mvc.view.prefix` et `spring.mvc.view.suffix` pour eviter la confusion de moteur de vue.

Comment l'eviter:
- Choisir un seul moteur de vue principal (Thymeleaf ou JSP) pour une application.
- Si Thymeleaf est actif, placer les templates dans `src/main/resources/templates`.
- Garder une convention simple entre le nom retourne par le controleur et le nom du template.
