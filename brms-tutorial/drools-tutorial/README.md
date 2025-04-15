# Drools tutorial

Dans le développement d'applications modernes, la gestion des règles métier complexes peut devenir un défi, surtout lorsqu'elles doivent être fréquemment mises à jour sans modifier le code source.
Drools, un moteur de règles open-source, offre une solution pratique pour externaliser la logique métier dans des fichiers de règles facilement modifiables.
Lorsqu'il est intégré à Spring Boot, Drools permet de combiner la puissance d'un framework web robuste avec une gestion dynamique des règles.
Cet article explore comment intégrer Drools dans une application Spring Boot, en s'appuyant sur un cas pratique de gestion bancaire, et détaille les étapes, les avantages, et les pièges à éviter.

## Présentation de Drools
Drools est un système de gestion de règles métier (BRMS) développé par Red Hat. Il permet de définir des règles dans un langage déclaratif (souvent en fichiers .drl) qui sont ensuite évaluées par un moteur d'inférence basé sur l'algorithme Rete. Drools s'intègre parfaitement avec Java, offrant une API pour manipuler les règles et les faits (objets métier).
Dans un contexte Spring Boot, Drools peut être utilisé pour appliquer des règles complexes, comme la validation de transactions bancaires ou la gestion de politiques de tarification, tout en restant découplé de la logique applicative.
Fonctionnalités clés :
- **Langage déclaratif** : Les règles sont écrites dans un format lisible, facilitant leur maintenance.
- **Moteur d'inférence** : Évalue automatiquement les règles en fonction des faits insérés.
- **Intégration Java** : Compatible avec les POJO (Plain Old Java Objects).
- **Flexibilité** : Les règles peuvent être modifiées sans recompiler l'application.

### ⚖️ Avantages et inconvénients
#### ➕ Avantages
- **Modularité** : Les règles sont externalisées, permettant aux analystes métier de les modifier sans toucher au code Java.
- **Flexibilité** : Drools s'adapte à des cas d'usage variés, comme la validation, les calculs dynamiques, ou la détection de fraudes.
- **Intégration Sprin**g : Avec Spring Boot, Drools peut être configuré comme un bean, utilisé avec AOP pour appliquer des règles de manière transparente.
- **Performance** : Le moteur Rete optimise l'évaluation des règles, même pour des ensembles complexes.

#### ➖ Inconvénients
- **Complexité initiale** : La configuration de Drools et l'écriture de règles nécessitent une courbe d'apprentissage, surtout pour les développeurs non familiers avec les BRMS.
- **Performance à grande échelle** : La création répétée de sessions Drools peut être coûteuse si elle n'est pas optimisée (par exemple, via un pool).
- **Maintenance des règles** : Sans tests rigoureux, les modifications des règles peuvent introduire des erreurs difficiles à détecter.
- **Dépendance au classpath** : Les fichiers de règles doivent être correctement inclus dans le build, ce qui peut poser des problèmes si mal configuré.

### Installation du plugin
Pour intégrer Drools dans une application Spring Boot, ajoutez les dépendances nécessaires dans votre fichier pom.xml (pour Maven) ou build.gradle (pour Gradle). Voici un exemple pour Maven :
```xml
<dependencies>
    <dependency>
        <groupId>org.drools</groupId>
        <artifactId>drools-core</artifactId>
        <version>${drools.version}</version>
    </dependency>
    <dependency>
        <groupId>org.drools</groupId>
        <artifactId>drools-compiler</artifactId>
        <version>${drools.version}</version>
    </dependency>
    <dependency>
        <groupId>org.drools</groupId>
        <artifactId>drools-xml-support</artifactId>
        <version>${drools.version}</version>
    </dependency>
    <dependency>
        <groupId>org.drools</groupId>
        <artifactId>drools-mvel</artifactId>
        <version>${drools.version}</version>
    </dependency>
</dependencies>
```

## Cas pratique : Gestion bancaire avec Drools
Pour illustrer l'intégration, nous allons examiner une application Spring Boot qui utilise Drools pour gérer des transactions bancaires (dépôts, retraits) en appliquant des règles comme la vérification du solde ou la détection de transactions suspectes. Voici les composants clés et leur explication.
### Modèles métier
Les classes Account et Transaction représentent les faits utilisés par Drools. Elles implémentent une interface DroolsFact pour faciliter leur identification.
```java
public interface DroolsFact {
// Marqueur pour les objets utilisés dans Drools
}

public class Account implements DroolsFact {
@NotBlank(message = "Le numéro de compte ne peut pas être vide")
    private String accountNumber;
    private double balance;
    // Getters et setters
}

public class Transaction implements DroolsFact {
    @NotBlank(message = "Le numéro de compte ne peut pas être vide")
    private String accountNumber;
    @NotNull(message = "Le type de transaction est requis")
    private String type;
    @Positive(message = "Le montant doit être positif")
    private double amount;
    private boolean approved;
    private String message;
    // Getters et setters
}
```
**Explication** :
- Les annotations de validation (@NotBlank, @Positive) garantissent que les données sont correctes avant d'être traitées.
- L'interface DroolsFact permet à l'aspect Drools d'identifier les objets à insérer comme faits.

### Annotation personnalisée
Une annotation @DroolsRule marque les méthodes où les règles Drools doivent s'appliquer.
```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DroolsRule {
}
```
**Explication** :
Cette annotation est utilisée avec Spring AOP pour intercepter les méthodes et exécuter les règles Drools de manière transparente.

### Configuration Drools
La classe DroolsConfig configure le moteur Drools, charge les fichiers de règles, et crée un KieContainer.

```java
@Configuration
@EnableAspectJAutoProxy
public class DroolsConfig {

    private static final Logger logger = LoggerFactory.getLogger(DroolsConfig.class);
    private static final KieServices kieServices = KieServices.Factory.get();
    private static final String RULES_PATH = "rules/*.drl";

    @Bean
    public KieContainer kieContainer() {
        try {
            logger.info("Initialisation de la configuration Drools");

            // Créer un KieFileSystem
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            logger.info("KieFileSystem créé");

            // Charger tous les fichiers .drl du dossier rules
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] ruleResources = resolver.getResources("classpath:" + RULES_PATH);
            if (ruleResources.length == 0) {
                logger.error("Aucun fichier de règles trouvé dans {}", RULES_PATH);
                throw new IllegalStateException("Aucun fichier de règles trouvé dans " + RULES_PATH);
            }

            // Convertir les ressources Spring en ressources Drools
            for (Resource springResource : ruleResources) {
                // Utiliser le chemin relatif complet (rules/nom_du_fichier)
                String resourcePath = "rules/" + springResource.getFilename();
                logger.info("Tentative de chargement de la ressource : {}", resourcePath);

                // Créer une ressource Drools à partir du chemin relatif
                org.kie.api.io.Resource droolsResource = kieServices.getResources()
                        .newClassPathResource(resourcePath, "UTF-8");
                kieFileSystem.write(droolsResource);
                logger.info("Fichier de règles chargé : {}", resourcePath);
            }

            // Créer un kmodule.xml programmatique
            KieModuleModel kieModuleModel = kieServices.newKieModuleModel();
            KieBaseModel kieBaseModel = kieModuleModel.newKieBaseModel("bankRules");
            kieBaseModel.addPackage("rules");
            kieBaseModel.setDefault(true);
            KieSessionModel kieSessionModel = kieBaseModel.newKieSessionModel("bankSession");
            kieSessionModel.setDefault(true);
            logger.info("Configuration de kmodule : kbase=bankRules, ksession=bankSession");

            // Écrire kmodule.xml dans le KieFileSystem
            String kmoduleXml = kieModuleModel.toXML();
            kieFileSystem.writeKModuleXML(kmoduleXml);
            logger.info("kmodule.xml généré : {}", kmoduleXml);

            // Construire le KieModule
            logger.info("Démarrage de la compilation des règles");
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
            kieBuilder.buildAll();
            logger.info("Compilation terminée");

            // Vérifier les erreurs de compilation
            if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
                logger.error("Erreurs lors de la compilation des règles :");
                for (Message message : kieBuilder.getResults().getMessages(Message.Level.ERROR)) {
                    logger.error("Erreur : {}", message.getText());
                }
                throw new IllegalStateException("Échec de la compilation des règles Drools : " + kieBuilder.getResults().getMessages());
            }
            if (kieBuilder.getResults().hasMessages(Message.Level.WARNING)) {
                logger.warn("Avertissements lors de la compilation des règles :");
                for (Message message : kieBuilder.getResults().getMessages(Message.Level.WARNING)) {
                    logger.warn("Avertissement : {}", message.getText());
                }
            }
            logger.info("Compilation des règles réussie");

            // Créer le KieContainer
            KieModule kieModule = kieBuilder.getKieModule();
            KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
            logger.info("KieContainer créé avec succès, ReleaseId : {}", kieModule.getReleaseId());

            return kieContainer;
        } catch (IOException e) {
            logger.error("Erreur lors du chargement des fichiers de règles", e);
            throw new IllegalStateException("Impossible de charger les fichiers de règles", e);
        } catch (Exception e) {
            logger.error("Erreur lors de la création du KieContainer", e);
            throw new IllegalStateException("Impossible de créer le KieContainer", e);
        }
    }
}
```
**Explication** :
- KieModule : Lors de la compilation avec KieBuilder, le KieModule est créé pour encapsuler les règles compilées (issues des fichiers .drl) et la configuration (kmodule.xml). Il agit comme une unité logique regroupant toutes les ressources Drools nécessaires pour une application.
- KieContainer : Créé à partir du KieModule, le KieContainer est un conteneur global qui permet d'accéder aux bases de connaissances (KieBase) et de créer des sessions (KieSession) pour exécuter les règles. Il est configuré comme un bean Spring pour être injecté dans d'autres composants, comme l'aspect.
- Charge dynamiquement tous les fichiers .drl dans rules/ pour une flexibilité maximale.
- Crée un kmodule.xml programmatique pour définir une base de connaissances (bankRules) et une session (bankSession).
- Vérifie les erreurs de compilation pour garantir la validité des règles.

### Aspect Drools
L'aspect DroolsAspect applique les règles aux méthodes annotées avec @DroolsRule.
```java
@Aspect
@Component
public class DroolsAspect {
private static final Logger logger = LoggerFactory.getLogger(DroolsAspect.class);
private final KieContainer kieContainer;

    public DroolsAspect(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    @Around("@annotation(fr.eletutour.annotation.DroolsRule)")
    public Object applyDroolsRules(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Interception de la méthode avec @DroolsRule: {}, arguments: {}",
                joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));

        // Récupérer les faits Drools parmi les arguments
        List<DroolsFact> facts = new ArrayList<>();
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof DroolsFact) {
                facts.add((DroolsFact) arg);
            }
        }

        // Vérifier qu'il y a des faits
        if (facts.isEmpty()) {
            logger.error("Aucun fait Drools trouvé dans les arguments");
            throw new IllegalArgumentException("Au moins un fait Drools est requis pour appliquer les règles");
        }
        logger.info("Faits trouvés: {}", facts);

        // Créer une session Drools
        logger.info("Création de la KieSession 'bankSession'");
        KieSession kieSession = kieContainer.newKieSession("bankSession");
        if (kieSession == null) {
            logger.error("Échec de la création de la KieSession 'bankSession'");
            throw new IllegalStateException("KieSession 'bankSession' non trouvée");
        }

        // Configurer le logger comme global
        Logger rulesLogger = LoggerFactory.getLogger("RulesLogger");
        kieSession.setGlobal("logger", rulesLogger);

        try {
            // Insérer les faits
            for (DroolsFact fact : facts) {
                kieSession.insert(fact);
            }
            logger.info("Faits insérés: {}", facts);

            // Exécuter les règles
            logger.info("Exécution des règles Drools");
            int rulesFired = kieSession.fireAllRules();
            logger.info("Nombre de règles exécutées: {}", rulesFired);

            // Continuer l'exécution de la méthode originale
            logger.info("Poursuite de l'exécution de la méthode originale");
            return joinPoint.proceed();
        } finally {
            // Libérer la session
            logger.info("Libération de la KieSession");
            kieSession.dispose();
        }
    }
}
```
**Explication** :
- KieSession : La KieSession est créée à partir du KieContainer pour chaque exécution de règles. Elle représente un environnement d'exécution où les faits (comme Account et Transaction) sont insérés, et les règles sont évaluées. Elle est libérée après usage (dispose()) pour éviter les fuites de mémoire.
- Utilise Spring AOP pour intercepter les méthodes annotées.
- Insère dynamiquement tous les arguments implémentant DroolsFact dans la session Drools.
- Exécute les règles et poursuit l'exécution de la méthode originale.

Règles Drools
```drl
package rules

import fr.eletutour.model.Account
import fr.eletutour.model.Transaction

dialect "mvel"

global org.slf4j.Logger logger

// Règle 0 : Valider les données de la transaction
rule "Validate Transaction"
salience 100
when
$t: Transaction(
accountNumber == null || accountNumber == "",
approved == false
||
amount <= 0,
approved == false
||
type == null || type == "",
approved == false
)
then
$t.setApproved(false);
$t.setMessage("Transaction invalide : numéro de compte, montant ou type manquant ou incorrect.");
logger.error("Règle 'Validate Transaction' déclenchée pour une transaction invalide");
end

// Règle 1 : Vérifier si le retrait est possible
rule "Check Sufficient Balance for Withdrawal"
salience 50
when
$t: Transaction(type in ("WITHDRAW", "WITHDRAWAL"), $amount: amount, approved == false)
$a: Account(accountNumber == $t.accountNumber, balance >= $amount)
then
$t.setApproved(true);
$a.setBalance($a.getBalance() - $amount);
$t.setMessage("Retrait de " + $amount + " approuvé.");
logger.info("Règle 'Check Sufficient Balance' déclenchée pour " + $t.getAccountNumber());
end

// Règle 2 : Refuser le retrait si solde insuffisant
rule "Deny Withdrawal Insufficient Balance"
salience 50
when
$t: Transaction(type in ("WITHDRAW", "WITHDRAWAL"), $amount: amount, approved == false)
$a: Account(accountNumber == $t.accountNumber, balance < $amount)
then
$t.setApproved(false);
$t.setMessage("Retrait de " + $amount + " refusé : solde insuffisant.");
logger.info("Règle 'Deny Withdrawal' déclenchée pour " + $t.getAccountNumber() + ", solde=" + $a.getBalance());
end

// Règle 3 : Appliquer des frais de découvert
rule "Apply Overdraft Fee"
salience 30
when
$a: Account(balance < 0)
then
double fee = 10.0;
$a.setBalance($a.getBalance() - fee);
logger.info("Frais de découvert de " + fee + " appliqués au compte " + $a.getAccountNumber());
end

// Règle 4 : Détecter une transaction suspecte
rule "Flag Suspicious Transaction"
salience 20
when
$t: Transaction(type in ("WITHDRAW", "WITHDRAWAL"), amount > 1000, approved == true)
then
$t.setMessage($t.getMessage() + " [ATTENTION : Transaction suspecte détectée !]");
logger.info("Transaction suspecte : " + $t);
end

// Règle 5 : Gérer les dépôts
rule "Process Deposit"
salience 50
when
$t: Transaction(type == "DEPOSIT", $amount: amount, approved == false)
$a: Account(accountNumber == $t.accountNumber)
then
$t.setApproved(true);
$a.setBalance($a.getBalance() + $amount);
$t.setMessage("Dépôt de " + $amount + " approuvé.");
logger.info("Règle 'Process Deposit' déclenchée pour " + $t.getAccountNumber());
end
```
**Explication** :
- La règle Validate Transaction vérifie les données de base (priorité élevée avec salience 100).
- Les règles de retrait et de dépôt ajustent le solde et approuvent/refusent la transaction.
- Le logger global trace l'exécution pour faciliter le débogage.

### Service et contrôleur
Le TransactionProcessor applique les règles via @DroolsRule, et le BankController expose une API REST.
```java
@Component
public class TransactionProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TransactionProcessor.class);
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public TransactionProcessor(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    /**
     * Exécute une transaction en appliquant les règles Drools.
     *
     * @param transaction La transaction à traiter.
     * @param account     Le compte associé.
     * @return La transaction mise à jour.
     * @throws TransactionFailedException Si la transaction est refusée ou invalide.
     */
    @Transactional
    @DroolsRule
    public Transaction doTransaction(Transaction transaction, Account account) {
        logger.info("Exécution de la transaction: type={}, accountNumber={}, amount={}",
                transaction.getType(), transaction.getAccountNumber(), transaction.getAmount());

        // La logique Drools est appliquée via l'aspect
        logger.info("Après Drools - Transaction: approved={}, message={}",
                transaction.isApproved(), transaction.getMessage());

        // Valider l'état après Drools
        if (transaction.getMessage() == null || transaction.getMessage().isEmpty()) {
            logger.error("La transaction n'a pas de message après l'exécution des règles");
            throw new TransactionFailedException("Échec de la transaction : aucun message fourni par les règles");
        }

        // Vérifier si la transaction est approuvée
        if (!transaction.isApproved()) {
            logger.warn("Transaction non approuvée: {}", transaction.getMessage());
            transactionRepository.save(transaction);
            throw new TransactionFailedException(transaction.getMessage());
        }

        // Sauvegarder les modifications
        logger.info("Sauvegarde de Account: balance={}", account.getBalance());
        accountRepository.save(account);
        transactionRepository.save(transaction);

        return transaction;
    }
}
```

```java
@Service
public class BankService {
    private static final Logger logger = LoggerFactory.getLogger(BankService.class);
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionProcessor transactionProcessor;

    public BankService(AccountRepository accountRepository, TransactionRepository transactionRepository,
                       TransactionProcessor transactionProcessor) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transactionProcessor = transactionProcessor;
    }

    /**
     * Traite une transaction en récupérant le compte associé.
     *
     * @param transaction La transaction à traiter.
     * @return La transaction mise à jour.
     * @throws AccountNotFoundException Si le compte n'existe pas.
     */
    @Transactional
    public Transaction getAccountAndDoTransaction(Transaction transaction) {
        logger.info("Récupération du compte pour la transaction: {}", transaction.getAccountNumber());
        Account account = accountRepository.findById(transaction.getAccountNumber())
                .orElseThrow(() -> {
                    logger.error("Compte non trouvé : {}", transaction.getAccountNumber());
                    return new AccountNotFoundException("Compte non trouvé : " + transaction.getAccountNumber());
                });
        return transactionProcessor.doTransaction(transaction, account);
    }

    /**
     * Crée un nouveau compte.
     *
     * @param account Le compte à créer.
     * @return Le compte créé.
     */
    @Transactional
    public Account createAccount(Account account) {
        logger.info("Création du compte: {}", account.getAccountNumber());
        return accountRepository.save(account);
    }

    /**
     * Récupère un compte par son numéro.
     *
     * @param accountNumber Le numéro du compte.
     * @return Le compte trouvé.
     * @throws AccountNotFoundException Si le compte n'existe pas.
     */
    public Account getAccount(String accountNumber) {
        return accountRepository.findById(accountNumber)
                .orElseThrow(() -> {
                    logger.error("Compte non trouvé : {}", accountNumber);
                    return new AccountNotFoundException("Compte non trouvé : " + accountNumber);
                });
    }
}
```
```java
@RestController
@RequestMapping("/api/bank")
public class BankController {
private static final Logger logger = LoggerFactory.getLogger(BankController.class);
private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @Operation(summary = "Créer un nouveau compte", description = "Crée un compte bancaire avec les informations fournies.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compte créé avec succès"),
            @ApiResponse(responseCode = "400", description = "Données invalides")
    })
    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account) {
        logger.info("Requête pour créer le compte: {}", account.getAccountNumber());
        return ResponseEntity.ok(bankService.createAccount(account));
    }

    @Operation(summary = "Récupérer un compte", description = "Récupère les détails d'un compte par son numéro.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Compte trouvé"),
            @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    @GetMapping("/accounts/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        logger.info("Requête pour récupérer le compte: {}", accountNumber);
        return ResponseEntity.ok(bankService.getAccount(accountNumber));
    }

    @Operation(summary = "Traiter une transaction", description = "Traite une transaction (dépôt ou retrait) pour un compte.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transaction traitée avec succès"),
            @ApiResponse(responseCode = "400", description = "Transaction invalide ou non approuvée"),
            @ApiResponse(responseCode = "404", description = "Compte non trouvé")
    })
    @PostMapping("/transactions")
    public ResponseEntity<Transaction> processTransaction(@Valid @RequestBody Transaction transaction) {
        logger.info("Requête pour traiter la transaction: type={}, accountNumber={}, amount={}",
                transaction.getType(), transaction.getAccountNumber(), transaction.getAmount());
        Transaction result = bankService.getAccountAndDoTransaction(transaction);
        return ResponseEntity.ok(result);
    }
}
```

## Conclusion
L'intégration de Drools dans Spring Boot offre une approche puissante pour gérer des règles métier complexes tout en maintenant une architecture modulaire.

Notre cas pratique montre comment utiliser Drools pour valider et traiter des transactions bancaires, avec une configuration flexible et une séparation claire des responsabilités.

Bien que la configuration initiale puisse sembler complexe, les avantages en termes de flexibilité et de maintenabilité sont significatifs, surtout pour les applications nécessitant des mises à jour fréquentes des règles.