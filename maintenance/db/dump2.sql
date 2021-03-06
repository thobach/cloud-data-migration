-- MySQL dump 10.13  Distrib 5.5.16, for osx10.5 (i386)
--
-- Host: 127.0.0.1    Database: clouddatamigration
-- ------------------------------------------------------
-- Server version	5.1.54

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CDMCriterionPossibleValue`
--

DROP TABLE IF EXISTS `CDMCriterionPossibleValue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CDMCriterionPossibleValue` (
  `id` char(32) NOT NULL,
  `key` varchar(256) NOT NULL,
  `name` varchar(256) NOT NULL,
  `orderNumber` smallint(6) DEFAULT NULL,
  `CDMCriterion_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_CDMCriterionValue_CDMCriterion1` (`CDMCriterion_id`),
  CONSTRAINT `fk_CDMCriterionValue_CDMCriterion1` FOREIGN KEY (`CDMCriterion_id`) REFERENCES `cdmcriterion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CDMCriterionPossibleValue`
--

LOCK TABLES `CDMCriterionPossibleValue` WRITE;
/*!40000 ALTER TABLE `CDMCriterionPossibleValue` DISABLE KEYS */;
INSERT INTO `CDMCriterionPossibleValue` VALUES ('15acb5d2f921483eaa6a25b810d31989','COMPLETED','Complete',1,'d9be7c52341b454ab3874e1cb729ecf6'),('198d2d9759734d188e543763683a5081','CONSTANT','constant',3,'ad45e65b8729440caa251b4b8ec8fe88'),('202b522f9843476694bf8daf11b1e16c','SOURCE_NOSQL','NoSQL',2,'9912094f31b248a69209e59933e842f0'),('24e5225f92d34acfb7e9b5042bd4e43a','PARTIAL','Partial',2,'d9be7c52341b454ab3874e1cb729ecf6'),('25940cadc9fa4d60bfb9a8a8bfa17c03','TWO_WAY_SYNC','Two-way synchronization',4,'62ae70ec83c445ba8d410e7eb734c898'),('29b087f9c3604e13ae7bc78147e787f8','FAST','Fast',1,'129ffc4df5644e3abda5f4ced83759f8'),('3c96630555af4b6b852024fe6630d204','CLOUD_TO_CLOUD','Between clouds',3,'c9237484d62d4094b91cbe60ef6a7c47'),('49a29abc80cc4b68a31f104c363849b2','COPIED','Copied',2,'62ae70ec83c445ba8d410e7eb734c898'),('4c37de5ceb8441e48f38c7208dce235a','DIFFERENT_VERSION','Same product, different version',2,'3ac49847897941e2ad6b36a0b224dcd2'),('5c9cd0a45ddf457da608a3d9a3e092a3','STEPWISE','Stepwise',2,'309dae3ab4f54c2b8e43aea37c1f7f7a'),('62e55fa92c5649e099ef4aba5f82703d','DIFFERENT_PRODUCT','Different products',3,'3ac49847897941e2ad6b36a0b224dcd2'),('640d86fdca964d599fc87d1d2447829e','LIVE','Live (without downtime)',1,'274abf12614949b3a956dd4dc4e3fb72'),('767e87c5cd1d40edbccbdc80a01c0eea','ACYCLIC','acyclic variable',2,'ad45e65b8729440caa251b4b8ec8fe88'),('76a3b263d24b4f9380f6adbf0b2537f1','MOVED','Moved',1,'62ae70ec83c445ba8d410e7eb734c898'),('7cfc0a3904b14d5383f9054dfd0d38e0','SLOW','Slow',2,'129ffc4df5644e3abda5f4ced83759f8'),('84850ca70da14cfda39297dfc995ffc1','TARGET_BLOB','Blob Store',3,'77cd349a88054696a362def189496dcf'),('8da427ce2805420a956bb1a120c0356f','CUT_OFF','Cut-off date',1,'309dae3ab4f54c2b8e43aea37c1f7f7a'),('905c60897ba64faf803cce442aa57838','SOURCE_BLOB','Blob Store',3,'9912094f31b248a69209e59933e842f0'),('9a536e2867c34f4caf8e55194d26a71e','NON_LIVE','Non-Live (with downtime)',2,'274abf12614949b3a956dd4dc4e3fb72'),('9da3be86828c4446b64efde1a3c2a5cc','CLOUD_TO_LOCAL','From cloud to local datacenter',2,'c9237484d62d4094b91cbe60ef6a7c47'),('9fc489edbb7143b4a8e0e34ec28de696','SOURCE_RDBMS','RDBMS',1,'9912094f31b248a69209e59933e842f0'),('a4156461fc454e86bb935b8a9cd1b336','TARGET_RDBMS','RDBMS',1,'77cd349a88054696a362def189496dcf'),('a9b3a7d340a34e3182a38f4ac70d01d7','PERMANENT','Permanent',1,'f011c857ae2f426fa2d2ee6b9afd1f66'),('b0453d5f797f4bcea87d820ac9af042b','TARGET_NOSQL','NoSQL',2,'77cd349a88054696a362def189496dcf'),('b49a5386fd654055a50be10b027bbfd3','ONE_WAY_SYNC','One-way synchronization',3,'62ae70ec83c445ba8d410e7eb734c898'),('cac2c6db609e4fba8eccec053ea06b8b','LOCAL_TO_CLOUD','From local datacenter to cloud',1,'c9237484d62d4094b91cbe60ef6a7c47'),('d28f0eac11f04e5f883413f0ab736282','TEMPORARY','Temporary',2,'f011c857ae2f426fa2d2ee6b9afd1f66'),('f22582de90cb424c948a71815d1944cd','SAME_VERSION','Same product, same version',1,'3ac49847897941e2ad6b36a0b224dcd2'),('f27df0d8cb7c41999d71658fb4bb536d','CYCLIC','cyclic variable',1,'ad45e65b8729440caa251b4b8ec8fe88');
/*!40000 ALTER TABLE `CDMCriterionPossibleValue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CDMScenario_has_CDMCriterionPossibleValue`
--

DROP TABLE IF EXISTS `CDMScenario_has_CDMCriterionPossibleValue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CDMScenario_has_CDMCriterionPossibleValue` (
  `CDMScenario_id` char(32) NOT NULL,
  `CDMCriterionPossibleValue_id` char(32) NOT NULL,
  PRIMARY KEY (`CDMScenario_id`,`CDMCriterionPossibleValue_id`),
  KEY `fk_CDMScenario_has_CDMCriterionPossibleValue_CDMCriterionPoss1` (`CDMCriterionPossibleValue_id`),
  KEY `fk_CDMScenario_has_CDMCriterionPossibleValue_CDMScenario1` (`CDMScenario_id`),
  CONSTRAINT `fk_CDMScenario_has_CDMCriterionPossibleValue_CDMScenario1` FOREIGN KEY (`CDMScenario_id`) REFERENCES `cdmscenario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CDMScenario_has_CDMCriterionPossibleValue_CDMCriterionPoss1` FOREIGN KEY (`CDMCriterionPossibleValue_id`) REFERENCES `cdmcriterionpossiblevalue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CDMScenario_has_CDMCriterionPossibleValue`
--

LOCK TABLES `CDMScenario_has_CDMCriterionPossibleValue` WRITE;
/*!40000 ALTER TABLE `CDMScenario_has_CDMCriterionPossibleValue` DISABLE KEYS */;
INSERT INTO `CDMScenario_has_CDMCriterionPossibleValue` VALUES ('38793cd441e2416ba6981e815979036a','15acb5d2f921483eaa6a25b810d31989'),('38793cd441e2416ba6981e815979036a','198d2d9759734d188e543763683a5081'),('38793cd441e2416ba6981e815979036a','202b522f9843476694bf8daf11b1e16c'),('38793cd441e2416ba6981e815979036a','24e5225f92d34acfb7e9b5042bd4e43a'),('38793cd441e2416ba6981e815979036a','29b087f9c3604e13ae7bc78147e787f8'),('38793cd441e2416ba6981e815979036a','3c96630555af4b6b852024fe6630d204'),('38793cd441e2416ba6981e815979036a','4c37de5ceb8441e48f38c7208dce235a'),('38793cd441e2416ba6981e815979036a','5c9cd0a45ddf457da608a3d9a3e092a3'),('38793cd441e2416ba6981e815979036a','62e55fa92c5649e099ef4aba5f82703d'),('38793cd441e2416ba6981e815979036a','640d86fdca964d599fc87d1d2447829e'),('38793cd441e2416ba6981e815979036a','767e87c5cd1d40edbccbdc80a01c0eea'),('38793cd441e2416ba6981e815979036a','76a3b263d24b4f9380f6adbf0b2537f1'),('38793cd441e2416ba6981e815979036a','7cfc0a3904b14d5383f9054dfd0d38e0'),('38793cd441e2416ba6981e815979036a','84850ca70da14cfda39297dfc995ffc1'),('38793cd441e2416ba6981e815979036a','8da427ce2805420a956bb1a120c0356f'),('38793cd441e2416ba6981e815979036a','905c60897ba64faf803cce442aa57838'),('38793cd441e2416ba6981e815979036a','9a536e2867c34f4caf8e55194d26a71e'),('38793cd441e2416ba6981e815979036a','9da3be86828c4446b64efde1a3c2a5cc'),('38793cd441e2416ba6981e815979036a','9fc489edbb7143b4a8e0e34ec28de696'),('38793cd441e2416ba6981e815979036a','a4156461fc454e86bb935b8a9cd1b336'),('38793cd441e2416ba6981e815979036a','a9b3a7d340a34e3182a38f4ac70d01d7'),('38793cd441e2416ba6981e815979036a','b0453d5f797f4bcea87d820ac9af042b'),('38793cd441e2416ba6981e815979036a','cac2c6db609e4fba8eccec053ea06b8b'),('38793cd441e2416ba6981e815979036a','f22582de90cb424c948a71815d1944cd'),('38793cd441e2416ba6981e815979036a','f27df0d8cb7c41999d71658fb4bb536d');
/*!40000 ALTER TABLE `CDMScenario_has_CDMCriterionPossibleValue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CloudDataHostingSolution`
--

DROP TABLE IF EXISTS `CloudDataHostingSolution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CloudDataHostingSolution` (
  `id` char(32) NOT NULL,
  `Project_id` char(32) NOT NULL,
  `CDHSCriterionPossibleValue_id` char(32) NOT NULL,
  `value` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_CloudDataHostingSolution_Project1` (`Project_id`),
  KEY `fk_CloudDataHostingSolution_CDSCriterionPossibleValue1` (`CDHSCriterionPossibleValue_id`),
  CONSTRAINT `fk_CloudDataHostingSolution_Project1` FOREIGN KEY (`Project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CloudDataHostingSolution_CDSCriterionPossibleValue1` FOREIGN KEY (`CDHSCriterionPossibleValue_id`) REFERENCES `cdhscriterionpossiblevalue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CloudDataHostingSolution`
--

LOCK TABLES `CloudDataHostingSolution` WRITE;
/*!40000 ALTER TABLE `CloudDataHostingSolution` DISABLE KEYS */;
INSERT INTO `CloudDataHostingSolution` VALUES ('0605ae12bdce408aa7cbc551b10da9dc','2ec339c01d7e4bd0a4b7bc21192a29e0','de75983ce6224edaa7e0b20247f52068',NULL),('0aa02835daae4b5ab74021a2bd3ff189','2ec339c01d7e4bd0a4b7bc21192a29e0','b3b4bbed35bf4b85b1569706478eaf3f',NULL),('2374bb907de644cda35c5f9144869987','2ec339c01d7e4bd0a4b7bc21192a29e0','435ee48d0b6445bda914af6e63b34bb4',NULL),('2e65f5662a5845dbba42a56a38780f03','2ec339c01d7e4bd0a4b7bc21192a29e0','abbbc17a25834e3ebb4ede30494acfea',NULL),('32da376b04b9483abc3719acd32cf5ee','2ec339c01d7e4bd0a4b7bc21192a29e0','e3915e23017849339a3851f16beefdfc',NULL),('4ad94b2b404148a6b89342c2a47a1773','2ec339c01d7e4bd0a4b7bc21192a29e0','722f8b818ac3481b9da077d27d375e78',NULL),('558110c990df487087b20413ae3123be','2ec339c01d7e4bd0a4b7bc21192a29e0','f7e002e63f5e4d49b2e23d3679c2c956',NULL),('5ebf49b4cd694cdcb75e21a4abb8ff64','2ec339c01d7e4bd0a4b7bc21192a29e0','2ea0e1d3799647a2a3316afed19cebfd','99.9'),('67387fd99cb640c2876ea61ebbff855f','2ec339c01d7e4bd0a4b7bc21192a29e0','d301729fe611455da1d3ad3e9425d3af',NULL),('692322d445fd4ddea214d53235395a09','2ec339c01d7e4bd0a4b7bc21192a29e0','541caa1e01d34c949e559fe2e89b6405',NULL),('76c53cd8215a41b6b47c73dc4c140f98','2ec339c01d7e4bd0a4b7bc21192a29e0','9f9b59a8666247fca71bf3960f2648d9',NULL),('78bad780746c44569dcdd744841ded86','2ec339c01d7e4bd0a4b7bc21192a29e0','9b54951ec1ed44839bbf0588c9bfe565',NULL),('8a06b109afb046fab1317b30da84e4e0','2ec339c01d7e4bd0a4b7bc21192a29e0','f439f47621c34cb3a1a5d2e2cb5b0a71',NULL),('9ffeb64e81874569b65996cfc7f55a84','2ec339c01d7e4bd0a4b7bc21192a29e0','a74444a41fe54cd2907a67f99a0c4478',NULL),('bf7ca4b535bd427994c4b6dff8a76087','2ec339c01d7e4bd0a4b7bc21192a29e0','a32439175ecc43728f44be4101086747',NULL),('c0b0cbcd92c7470d9e5e429f13a959f0','2ec339c01d7e4bd0a4b7bc21192a29e0','93de8f9577e140c2991360ccf957aaf9',NULL),('cc91740429da4ba5bc49affd4a98c087','2ec339c01d7e4bd0a4b7bc21192a29e0','e43a478d7bbd4b74b5f9d7890d9138c0',NULL),('cf52ca3869274d91b93bf29d273e7fda','2ec339c01d7e4bd0a4b7bc21192a29e0','4d43b303c39c4e6dbabd9d6a9b282013',NULL),('e9addb166ff84114bf4cc51f51b3af9f','2ec339c01d7e4bd0a4b7bc21192a29e0','d584925b823c4e36b9303ec1b7cde19d','1440');
/*!40000 ALTER TABLE `CloudDataHostingSolution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LocalDBLCriterion`
--

DROP TABLE IF EXISTS `LocalDBLCriterion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LocalDBLCriterion` (
  `id` char(32) NOT NULL,
  `name` varchar(256) NOT NULL,
  `orderNumber` smallint(6) DEFAULT NULL,
  `LocalDBLCategory_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_LocalDBLCriterion_LocalDBLCategory1` (`LocalDBLCategory_id`),
  CONSTRAINT `fk_LocalDBLCriterion_LocalDBLCategory1` FOREIGN KEY (`LocalDBLCategory_id`) REFERENCES `localdblcategory` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LocalDBLCriterion`
--

LOCK TABLES `LocalDBLCriterion` WRITE;
/*!40000 ALTER TABLE `LocalDBLCriterion` DISABLE KEYS */;
INSERT INTO `LocalDBLCriterion` VALUES ('03761c19bd91439e87e2ac061590e24c','Cloud computing deployment model of the source system',2,'dfd070c33c8449f7b4da4b1b0d4e25f4'),('35ff997a738b4434b7d9fe82b100a18f','Cloud computing service model of the source system',3,'dfd070c33c8449f7b4da4b1b0d4e25f4'),('3952b94249334f8ba4279d4391f71401','Location of the source system',1,'dfd070c33c8449f7b4da4b1b0d4e25f4'),('504369991bce47acb29b2c7aaa794b6c','Does the source system use proprietory extensions to the SQL 1999 standard and are these extensions used by the application?',1,'8460e2f690b9458f96ba0c7832cc9847'),('58f8bf5796ae4d2dbfdca614fc1e5a89','Does the source system support transactions and are these used by the application?',3,'8460e2f690b9458f96ba0c7832cc9847'),('5f07ef358d46490abc9a99d6df295ec9','Does the source system support Joins and are there used by the application?',4,'8460e2f690b9458f96ba0c7832cc9847'),('672be1638ad840ab84d13ef229a81c3c','What is the data store type of the source system?',6,'dfd070c33c8449f7b4da4b1b0d4e25f4'),('6e9bfc67a0754a629e190acb5a82e27c','Is the source system addressed on the network level via an URL that can be resolved by a DNS?',7,'dfd070c33c8449f7b4da4b1b0d4e25f4'),('6fc99aba40f54222be80653994cbfcf4','Does the source system support stored procedures or trigger and are these used by the application?',2,'8460e2f690b9458f96ba0c7832cc9847'),('c383b49a50a5445da5f083b8f9199c4a','Does the source system have a synchronisation function?',5,'dfd070c33c8449f7b4da4b1b0d4e25f4'),('e0cc9cdeb1194d0e9255274eedd30ae7','Does the source system run on a distributed infrastructure?',4,'dfd070c33c8449f7b4da4b1b0d4e25f4');
/*!40000 ALTER TABLE `LocalDBLCriterion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Solution`
--

DROP TABLE IF EXISTS `Solution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Solution` (
  `id` char(32) NOT NULL,
  `name` varchar(256) NOT NULL,
  `description` text NOT NULL,
  `LocalDBLCriterionPossibleValue_id` char(32) DEFAULT NULL,
  `CDHSCriterionPossibleValue_id` char(32) DEFAULT NULL,
  `CDMCriterionPossibleValue_id` char(32) DEFAULT NULL,
  `CDMCriterionPossibleValue_id1` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Solution_LocalDBLCriterionPossibleValue1` (`LocalDBLCriterionPossibleValue_id`),
  KEY `fk_Solution_CDSCriterionPossibleValue1` (`CDHSCriterionPossibleValue_id`),
  KEY `fk_Solution_CDMCriterionPossibleValue1` (`CDMCriterionPossibleValue_id`),
  KEY `fk_Solution_CDMCriterionPossibleValue2` (`CDMCriterionPossibleValue_id1`),
  CONSTRAINT `fk_Solution_LocalDBLCriterionPossibleValue1` FOREIGN KEY (`LocalDBLCriterionPossibleValue_id`) REFERENCES `localdblcriterionpossiblevalue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Solution_CDSCriterionPossibleValue1` FOREIGN KEY (`CDHSCriterionPossibleValue_id`) REFERENCES `cdhscriterionpossiblevalue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Solution_CDMCriterionPossibleValue1` FOREIGN KEY (`CDMCriterionPossibleValue_id`) REFERENCES `cdmcriterionpossiblevalue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Solution_CDMCriterionPossibleValue2` FOREIGN KEY (`CDMCriterionPossibleValue_id1`) REFERENCES `cdmcriterionpossiblevalue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Solution`
--

LOCK TABLES `Solution` WRITE;
/*!40000 ALTER TABLE `Solution` DISABLE KEYS */;
INSERT INTO `Solution` VALUES ('00f27b2a8a734c0aa45a73789855618a','Adaption of application logic','If your migration scenario requires scalability of your target system, but it isn\'t, you can adapt your application logic to switch between read and write replicas.',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','f27df0d8cb7c41999d71658fb4bb536d',NULL),('0285762c773245229b5bd3e9cae6662d','Adaption of application logic','If your migration scenario requires scalability of your target system, but it isn\'t, you can adapt your application logic to switch between read and write replicas.',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','767e87c5cd1d40edbccbdc80a01c0eea',NULL),('0c1c842f1b2f4d92b6a3ab9b922c7c8c','Adaption of the data access layer','If your target system does not ensure confidentiality, you can adapt your data access layer to prohibit storing sensitive data there.',NULL,'f60ea4bed33b42a09cfdee851269c3e6',NULL,NULL),('149205d31b8c4ddba980ed61b1a2cffb','Pseudonymizer of Critical Data','If your target system is hosted in a public cloud, you can use a cloud data pattern called \'Pseudonymizer of Critical Data\' to ensure your critical data is not exposed.',NULL,'b3b4bbed35bf4b85b1569706478eaf3f',NULL,NULL),('1672b769a86f42c3a2a1cd859a80c429','Adaption of application logic','If your current system is a RDBMS and uses stored procedures or triggers and your target system is a NoSQL data store that does not support stored procedures or triggers, you can adapt your application logic to process the stored procedure and trigger logic.',NULL,NULL,'9fc489edbb7143b4a8e0e34ec28de696','b0453d5f797f4bcea87d820ac9af042b'),('17289460327a424897238266d26ea0ee','Anonymizer of Critical Data','If your target system is hosted in a public cloud, you can use a cloud data pattern called \'Anonymizer of Critical Data\' to ensure your critical data is not exposed.',NULL,'b3b4bbed35bf4b85b1569706478eaf3f',NULL,NULL),('1f917c6f3b204f0cbe230c78732e3360','Adaption of application logic','If your target system is hosted in a public cloud, you can adapt your application logic to prohibit storing sensitive data there.',NULL,'b3b4bbed35bf4b85b1569706478eaf3f',NULL,NULL),('2421d19c06a1467eb7e69b110dc84a5c','Local Sharding-Based Router','If your migration scenario requires sharding functionality in your target system, but it isn\'t supported, you can use a cloud data pattern called \"Local Sharding-Based Router\".',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','f27df0d8cb7c41999d71658fb4bb536d',NULL),('246d45aea1124700bca7d8a91f924c09','Pseudonymizer of Critical Data','If your target system does not ensure confidentiality, you can use a cloud data pattern called \'Pseudonymizer of Critical Data\'.',NULL,'f60ea4bed33b42a09cfdee851269c3e6',NULL,NULL),('309ae0a508d24ae1a2d96789162102c1','Adaption of application logic','If your source system is a RDBMS and target system a NoSQL data store and you use complex SQL queries which are not supported in the NoSQL store, you can adapt your application logic to use simpler request and applying the query logic on the client side.','695d65b893ce4490882d330509abd08d','d595c54a28e442ecb26e8146534c7e91',NULL,NULL),('31c90ea65b1a4ea78a3fb802eb004813','Adaption of the data access layer','If your migration scenario requires sharding functionality in your target system, but it isn\'t supported, you can adapt your data access layer to select the right shard for every query.',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','f27df0d8cb7c41999d71658fb4bb536d',NULL),('323d2dbedeed462888521bebc7c8e4cd','Adaption of application logic','If your migration scenario requires sharding functionality in your target system, but it isn\'t supported, you can adapt your application logic to select the right shard for every query.',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','f27df0d8cb7c41999d71658fb4bb536d',NULL),('361d7151a88f4481841e0410763a3118','Data Store Functionality Extension','If your current system is different (product) than your target system, then a cloud data pattern called \"Data Store Functionality Extension\" can simulate features and semantics that the target system does not understand or would handle differently.',NULL,NULL,'62e55fa92c5649e099ef4aba5f82703d',NULL),('3b4b17b9b57248a7b81f910b71bcfb96','Filter of Critical Data','If your target system is hosted in a public cloud, you can use a cloud data pattern called \'Filter of Critical Data\' to ensure your critical data is not exposed.',NULL,'b3b4bbed35bf4b85b1569706478eaf3f',NULL,NULL),('488a1826c7cf495cb7868ab17b79007c','Adaption of the data access layer','If your migration scenario requires scalability of your target system, but it isn\'t, you can adapt your data access layer to switch between read and write replicas.',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','f27df0d8cb7c41999d71658fb4bb536d',NULL),('4e296da0a04542528e81c31a6af0fc4e','Confidentiality Level Data Aggregator','If your target system is hosted in a public cloud, you can use a cloud data pattern called \'Confidentiality Level Data Aggregator\' to ensure your critical data is not exposed.',NULL,'b3b4bbed35bf4b85b1569706478eaf3f',NULL,NULL),('4e4af00cfc3f4dcdaa5d2470de818b4a','Anonymizer of Critical Data','If your target system does not ensure confidentiality, you can use a cloud data pattern called \'Anonymizer of Critical Data\'.',NULL,'f60ea4bed33b42a09cfdee851269c3e6',NULL,NULL),('556b9a3c338f4975ba1636ce86b127ce','Adaption of the data access layer','If your target system is hosted in a public cloud, you can adapt your data access layer to prohibit storing sensitive data there.',NULL,'b3b4bbed35bf4b85b1569706478eaf3f',NULL,NULL),('587ddff3bf93454ca1b7e28b55fc0931','Change connection strings','If your source and target system is addressed via a DNS resolvable URL and the source and target data store are compatible you can adapt the connection strings in the application settings and configure your firewall to allow access to the target data store.','0161517218744bcc80d12ee76c69024e',NULL,NULL,NULL),('5a093609d9a24916a7f50bc7bf651ff5','Adaption of application logic','If your target system does not ensure confidentiality, you can adapt your application layer to prohibit storing sensitive data there.',NULL,'f60ea4bed33b42a09cfdee851269c3e6',NULL,NULL),('5a9fbcdcbc494e6698c52310e8b85446','Local Database Proxy','If your migration scenario requires scalability of your target system, but it isn\'t, you can use a cloud data pattern called \"Local Database Proyx\".',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','f27df0d8cb7c41999d71658fb4bb536d',NULL),('5c618103cecc4394ae0e3782f8cbafdb','Local Sharding-Based Router','If your migration scenario requires sharding functionality in your target system, but it isn\'t supported, you can use a cloud data pattern called \"Local Sharding-Based Router\".',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','767e87c5cd1d40edbccbdc80a01c0eea',NULL),('5dc0c743a8404b09b27113be0c0a9b24','Adaption of application logic','If your current system is different (version) than your target system, then you can adapt your application logic to not use features that are not supported and use different semantics as required by the target system.',NULL,NULL,'4c37de5ceb8441e48f38c7208dce235a',NULL),('667ed10a38e4420599326fdab0afcdba','Confidentiality Level Data Aggregator','If your target system does not ensure confidentiality, you can use a cloud data pattern called \'Confidentiality Level Data Aggregator\'.',NULL,'f60ea4bed33b42a09cfdee851269c3e6',NULL,NULL),('6e61d311f8d14e89b75d14a6165fa62f','Local Database Proxy','If your migration scenario requires scalability of your target system, but it isn\'t, you can use a cloud data pattern called \"Local Database Proyx\".',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','767e87c5cd1d40edbccbdc80a01c0eea',NULL),('7b5b7aee3dee47feadcc10baedb18071','Instanciate and synchronize multiple instances','If your target system does not support high availability and high scalability of read requests then you can instanciate multiple instances and setup synchronization between them.',NULL,'c2fe8be8df264d4cbbbc562b2b904e00',NULL,NULL),('84ba6836cc194066b271ee194c6109a0','Adaption of application logic','If your migration scenario requires one-way syncing but the source or target system does not support it, you can adapt your application logic to sync the data manually.',NULL,'24566eb326d9462b8214ff8340a4fb1a','b49a5386fd654055a50be10b027bbfd3',NULL),('86d59c96fab14e848302c6bf9b6d7da7','Change CNAME, DNS Cache TTL and Firewall','If your source and target system is addressed via a DNS resolvable URL and the source and target data store are compatible you can just change the CNAME entry to point to the new target system, check that the DNS Cache TTL is low and configure your firewall to allow access to the target data store.','0161517218744bcc80d12ee76c69024e',NULL,NULL,NULL),('954a73d9fec34fc9813e48eba3bfe0c2','Adaption of the data access layer','If your current system is a RDBMS and uses stored procedures or triggers and your target system is a NoSQL data store that does not support stored procedures or triggers, you can adapt your data access layer to process the stored procedure and trigger logic.',NULL,NULL,'9fc489edbb7143b4a8e0e34ec28de696','b0453d5f797f4bcea87d820ac9af042b'),('97af422d6ebd40d29983f47e21f36d78','Adaption of the data access layer','If your current system is different (version) than your target system, then you can adapt your data access layer to simulate the missing features and translate the different semantics.',NULL,NULL,'4c37de5ceb8441e48f38c7208dce235a',NULL),('a9baeec7451b473b9fc353bd39f00361','Change connection strings','If your source and target system is addressed via IP and the source and target data store are compatible you can adapt the connection strings in the application settings and configure your firewall to allow access to the target data store.','e5dd7851d8a649c48a5d459411b80bac',NULL,NULL,NULL),('b000879d77e54995bf979ca41cce44a1','Adaption of application logic','If your migration scenario requires sharding functionality in your target system, but it isn\'t supported, you can adapt your application logic to select the right shard for every query.',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','767e87c5cd1d40edbccbdc80a01c0eea',NULL),('b965c6726ed54388aa482818816cbc12','Filter of Critical Data','If your target system does not ensure confidentiality, you can use a cloud data pattern called \'Filter of Critical Data\'.',NULL,'f60ea4bed33b42a09cfdee851269c3e6',NULL,NULL),('bb9b63ffaf3a44e787978d3ff65ed614','Adaption of the data access layer','If your migration scenario requires scalability of your target system, but it isn\'t, you can adapt your data access layer to switch between read and write replicas.',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','767e87c5cd1d40edbccbdc80a01c0eea',NULL),('bdbf81a077364f1a96fc7ef29737f20e','Adaption of the data access layer','If your migration scenario requires two-way syncing but the source or target system does not support it, you can adapt your data access layer to sync the data manually.',NULL,'6f0c63597b094dbe9303bf01fb7055a3','25940cadc9fa4d60bfb9a8a8bfa17c03',NULL),('c2298a15c3da447f9ccc3278a84c093f','Data Store Functionality Extension','If your current system is different (version) than your target system, then a cloud data pattern called \"Data Store Functionality Extension\" can simulate features and semantics that the target system does not understand or would handle differently.',NULL,NULL,'4c37de5ceb8441e48f38c7208dce235a',NULL),('c3c1c0bb3b2c4863a967745a8f4e7a26','Adaption of the data access layer','If your current system is different (product) than your target system, then you can adapt your data access layer to simulate the missing features and translate the different semantics.',NULL,NULL,'62e55fa92c5649e099ef4aba5f82703d',NULL),('c8946905dddc4c6bb9af27260b596761','ETL Tool or other third party data migration tool','If your migration scenario requires two-way syncing but the source or target system does not support it, you can use ETL or third party migration tools to im- and export the data manually.',NULL,'6f0c63597b094dbe9303bf01fb7055a3','25940cadc9fa4d60bfb9a8a8bfa17c03',NULL),('c8cbc609df364a69aa1d14dcfc223ae6','ETL Tool or other third party data migration tool','If your migration scenario requires one-way syncing but the source or target system does not support it, you can use ETL or third party migration tools to im- and export the data manually.',NULL,'24566eb326d9462b8214ff8340a4fb1a','b49a5386fd654055a50be10b027bbfd3',NULL),('d605ffd4a295434fae4b147493ae5ff7','Emulator of Stored Procedures','If your current system is a RDBMS and uses stored procedures or triggers and your target system is a NoSQL data store that does not support stored procedures or triggers, you can use a cloud data pattern called \"Emulator of Stored Procedures\" that interprets stored procedures and triggers.',NULL,NULL,'9fc489edbb7143b4a8e0e34ec28de696','b0453d5f797f4bcea87d820ac9af042b'),('deb94eb67bb84747af2f0d9d096c1809','External services for complex queries','If your source system is a RDBMS and target system a NoSQL data store and you use complex SQL queries which are not supported in the NoSQL store, you can use external services that allow complex queries on NoSQL databases like AWS Elastic MapReduce.','695d65b893ce4490882d330509abd08d','d595c54a28e442ecb26e8146534c7e91',NULL,NULL),('ea2dd53602a54e76a643b2b90f84fc1b','Adaption of the data access layer','If your migration scenario requires one-way syncing but the source or target system does not support it, you can adapt your data access layer to sync the data manually.',NULL,'24566eb326d9462b8214ff8340a4fb1a','b49a5386fd654055a50be10b027bbfd3',NULL),('ede870191a614344b0d41e789dde7eda','Pool of reserved instances','If your resource utilization can grow faster than it takes to launch a new instance, you can use a pool of spare resource to have constant response time.',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','29b087f9c3604e13ae7bc78147e787f8',NULL),('f46ef2f92eff4e79ab1550c640e83d37','Adaption of the data access layer','If your source system is a RDBMS and target system a NoSQL data store and you use complex SQL queries which are not supported in the NoSQL store, you can adapt your data access layer to simulate those complex queries by splitting them into simpler request and applying the query logic on the client side.','695d65b893ce4490882d330509abd08d','d595c54a28e442ecb26e8146534c7e91',NULL,NULL),('f61a96f236384ec5bfcda54a7121bc78','Adaption of the data access layer','If your migration scenario requires sharding functionality in your target system, but it isn\'t supported, you can adapt your data access layer to select the right shard for every query.',NULL,'a27b90b03c1b4fde98363393cfaf3dbe','767e87c5cd1d40edbccbdc80a01c0eea',NULL),('f8f888b939e94370bbafbcd76a61f647','Adaption of application logic','If your migration scenario requires two-way syncing but the source or target system does not support it, you can adapt your application logic to sync the data manually.',NULL,'6f0c63597b094dbe9303bf01fb7055a3','25940cadc9fa4d60bfb9a8a8bfa17c03',NULL),('fadc967730934c6ab6406b35a2037b8f','Adaption of application logic','If your current system is different (product) than your target system, then you can adapt your application logic to not use features that are not supported and use different semantics as required by the target system.',NULL,NULL,'62e55fa92c5649e099ef4aba5f82703d',NULL);
/*!40000 ALTER TABLE `Solution` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project`
--

DROP TABLE IF EXISTS `Project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project` (
  `id` char(32) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` text,
  `department` varchar(45) DEFAULT NULL COMMENT '	',
  `url` varchar(256) DEFAULT NULL,
  `created` datetime NOT NULL,
  `User_id` char(32) NOT NULL,
  `CloudDataStore_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Project_User` (`User_id`),
  KEY `fk_Project_CloudDataStore1` (`CloudDataStore_id`),
  CONSTRAINT `fk_Project_User` FOREIGN KEY (`User_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_CloudDataStore1` FOREIGN KEY (`CloudDataStore_id`) REFERENCES `clouddatastore` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project`
--

LOCK TABLES `Project` WRITE;
/*!40000 ALTER TABLE `Project` DISABLE KEYS */;
INSERT INTO `Project` VALUES ('2ec339c01d7e4bd0a4b7bc21192a29e0','Cloud Data Migration Tool','The Cloud Data Migration Tool (CDM) allows for migrating the data layer of an application to a cloud data store. The application itself is a Java Web application which uses a local MySQL database as data layer. The migration of the whole application to the Cloud should allow to use a compatible data store in the cloud.','IAAS','http://www.iaas.uni-stuttgart.de/','2012-07-10 21:36:24','ff2c28ae2f6147f88b4471d102129077','25f8a91dcdac4f0fa7f703e3bf787254');
/*!40000 ALTER TABLE `Project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CloudDataStoreProperty`
--

DROP TABLE IF EXISTS `CloudDataStoreProperty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CloudDataStoreProperty` (
  `id` char(32) NOT NULL,
  `CloudDataStore_id` char(32) NOT NULL,
  `CDHSCriterionPossibleValue_id` char(32) NOT NULL,
  `inputValue` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_CloudDataStore_has_CDHSCriterionPossibleValue_CDHSCriterio1` (`CDHSCriterionPossibleValue_id`),
  KEY `fk_CloudDataStore_has_CDHSCriterionPossibleValue_CloudDataSto1` (`CloudDataStore_id`),
  CONSTRAINT `fk_CloudDataStore_has_CDHSCriterionPossibleValue_CloudDataSto1` FOREIGN KEY (`CloudDataStore_id`) REFERENCES `clouddatastore` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CloudDataStore_has_CDHSCriterionPossibleValue_CDHSCriterio1` FOREIGN KEY (`CDHSCriterionPossibleValue_id`) REFERENCES `cdhscriterionpossiblevalue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CloudDataStoreProperty`
--

LOCK TABLES `CloudDataStoreProperty` WRITE;
/*!40000 ALTER TABLE `CloudDataStoreProperty` DISABLE KEYS */;
INSERT INTO `CloudDataStoreProperty` VALUES ('08ef895c36ed4a44a4a86466885f7bff','25f8a91dcdac4f0fa7f703e3bf787254','01cc9967aa1b4c0aa7f2cea1cadc71f6',NULL),('0ba42deaf6014d5da438378387cdc3a2','25f8a91dcdac4f0fa7f703e3bf787254','d414020320544badad6825eba8011cab',NULL),('17dc434a6f2446a18521db7d0c86cd82','25f8a91dcdac4f0fa7f703e3bf787254','b4aa43c4297d46d3874666f541de9e7c',NULL),('19b90a882d194bf6be6083ead83b4751','25f8a91dcdac4f0fa7f703e3bf787254','f7e002e63f5e4d49b2e23d3679c2c956',NULL),('1cac4582d4b04d2491bcab4996182db5','25f8a91dcdac4f0fa7f703e3bf787254','69f8e644b00649d59c3c8f0eae06b8ff',NULL),('1d8a9d06f94441efba5119fc8edc429b','25f8a91dcdac4f0fa7f703e3bf787254','9b54951ec1ed44839bbf0588c9bfe565',NULL),('2346ad554c6549ffbb14ddf30f0236bf','25f8a91dcdac4f0fa7f703e3bf787254','435ee48d0b6445bda914af6e63b34bb4',NULL),('2a426a42e22d428ab42cb2dfda7e0c72','25f8a91dcdac4f0fa7f703e3bf787254','9f9b59a8666247fca71bf3960f2648d9',NULL),('2d59cc5f3ba94cb7a2a59c2b7e3ee1b9','25f8a91dcdac4f0fa7f703e3bf787254','3964513c41fc4825b0a96ff8aa6a115b',NULL),('3238fcfaac96410cbb46d457c73c660e','25f8a91dcdac4f0fa7f703e3bf787254','722f8b818ac3481b9da077d27d375e78',NULL),('3a1349f823f4461c8bf69819a9da24ec','25f8a91dcdac4f0fa7f703e3bf787254','5a328b1082f9426bacb0a5511f99ba22',NULL),('402832a538ba04c30138baa41eda0075','402832a538ba04c30138ba04c3ea0000','9e8e656e554d48a896c0e469091dbcb9',NULL),('402832a538ba04c30138baa41ee30076','402832a538ba04c30138ba04c3ea0000','3964513c41fc4825b0a96ff8aa6a115b',NULL),('402832a538ba04c30138baa41eec0077','402832a538ba04c30138ba04c3ea0000','1f9f5a13e5af4e168a50a97424654e6c',NULL),('402832a538ba04c30138baa41ef40078','402832a538ba04c30138ba04c3ea0000','b3db76b4a862410f97ec966f1d452198',NULL),('402832a538ba04c30138baa41eff0079','402832a538ba04c30138ba04c3ea0000','a27b90b03c1b4fde98363393cfaf3dbe','limited number of read replica'),('402832a538ba04c30138baa41f08007a','402832a538ba04c30138ba04c3ea0000','2b4be7bd74dc49bc9f7296ccb4a82123','15'),('402832a538ba04c30138baa41f11007b','402832a538ba04c30138ba04c3ea0000','74fdd897c45343a0999b9506339565eb',NULL),('402832a538ba04c30138baa41f1d007c','402832a538ba04c30138ba04c3ea0000','9f9b59a8666247fca71bf3960f2648d9',NULL),('402832a538ba04c30138baa41f26007d','402832a538ba04c30138ba04c3ea0000','912fdc5fa00847c6afd214f6f79c0347',NULL),('402832a538ba04c30138baa41f2e007e','402832a538ba04c30138ba04c3ea0000','82588cc2cfb04f5ea6d695ea08208552',NULL),('402832a538ba04c30138baa41f37007f','402832a538ba04c30138ba04c3ea0000','4dff53a210284f3aa3144b99b4d9ea87',NULL),('402832a538ba04c30138baa41f3f0080','402832a538ba04c30138ba04c3ea0000','e3915e23017849339a3851f16beefdfc',NULL),('402832a538ba04c30138baa41f470081','402832a538ba04c30138ba04c3ea0000','8318ff1ebf0b4c4686a2767a13d07d6a',NULL),('402832a538ba04c30138baa41f4f0082','402832a538ba04c30138ba04c3ea0000','5d3bf5d3340b4bc186f07644577733dc',NULL),('402832a538ba04c30138baa41f570083','402832a538ba04c30138ba04c3ea0000','3a2479d6a7c745fda0aefb0031aa12e7',NULL),('402832a538ba04c30138baa41f5f0084','402832a538ba04c30138ba04c3ea0000','4d43b303c39c4e6dbabd9d6a9b282013',NULL),('402832a538ba04c30138baa41f660085','402832a538ba04c30138ba04c3ea0000','f60ea4bed33b42a09cfdee851269c3e6',NULL),('402832a538ba04c30138baa41f700086','402832a538ba04c30138ba04c3ea0000','798ef223cc4847efa69d360634d30852',NULL),('402832a538ba04c30138baa41f780087','402832a538ba04c30138ba04c3ea0000','1513f48247434f38a9f60c0a59917478',NULL),('402832a538ba04c30138baa41f830088','402832a538ba04c30138ba04c3ea0000','1aef5391ffc84f34a0c5c7eb9b8889b8','US, Europe, Asia, South America'),('402832a538ba04c30138baa41f900089','402832a538ba04c30138ba04c3ea0000','435ee48d0b6445bda914af6e63b34bb4',NULL),('402832a538ba04c30138baa41fa1008a','402832a538ba04c30138ba04c3ea0000','80340f71206d4fabb8a4c4a0b55f7349','10'),('402832a538ba04c30138baa41fa9008b','402832a538ba04c30138ba04c3ea0000','66ed93e64142462c86b4249a371ffa68',NULL),('402832a538ba04c30138baa41fb1008c','402832a538ba04c30138ba04c3ea0000','66c52278fdc94ca1a9fa4ad0bf0ff167',NULL),('402832a538ba04c30138baa41fbb008d','402832a538ba04c30138ba04c3ea0000','f6aa974d76dd4c87994739ce145c9bda',NULL),('402832a538ba04c30138baa41fc3008e','402832a538ba04c30138ba04c3ea0000','f7e002e63f5e4d49b2e23d3679c2c956',NULL),('402832a538ba04c30138baa41fcc008f','402832a538ba04c30138ba04c3ea0000','24566eb326d9462b8214ff8340a4fb1a',NULL),('402832a538ba04c30138baa41fd40090','402832a538ba04c30138ba04c3ea0000','04e630296aff4d70af54a1c7167fec6e',NULL),('402832a538ba04c30138baa41fdc0091','402832a538ba04c30138ba04c3ea0000','a32439175ecc43728f44be4101086747',NULL),('402832a538ba04c30138baa41fe50092','402832a538ba04c30138ba04c3ea0000','f03e6987ca9441b8b181dd672d992f4d',NULL),('402832a538ba04c30138baa41ff30093','402832a538ba04c30138ba04c3ea0000','9b54951ec1ed44839bbf0588c9bfe565',NULL),('402832a538ba04c30138baa41ffb0094','402832a538ba04c30138ba04c3ea0000','2b78ae1efdaa44929aa5bda792b4e5fe',NULL),('402832a538ba04c30138baa420050095','402832a538ba04c30138ba04c3ea0000','522a2f79bcdd4f9a80b0e90f6b5856a0',NULL),('402832a538ba04c30138baa4200c0096','402832a538ba04c30138ba04c3ea0000','541caa1e01d34c949e559fe2e89b6405',NULL),('402832a538ba04c30138baa420160097','402832a538ba04c30138ba04c3ea0000','80d75fad8f6d49f5b7474ec662d31dab',NULL),('402832a538ba04c30138baa4201e0098','402832a538ba04c30138ba04c3ea0000','e43a478d7bbd4b74b5f9d7890d9138c0',NULL),('402832a538ba04c30138baa420270099','402832a538ba04c30138ba04c3ea0000','49f152d32f3244008cb3c51d19799ea0','5.1 to 5.5'),('402832a538ba04c30138baa4202e009a','402832a538ba04c30138ba04c3ea0000','722f8b818ac3481b9da077d27d375e78',NULL),('402832a538ba04c30138baa42038009b','402832a538ba04c30138ba04c3ea0000','b82597306d2b487e91fafae439b27184',NULL),('402832a538ba04c30138baa4203f009c','402832a538ba04c30138ba04c3ea0000','d414020320544badad6825eba8011cab',NULL),('402832a538ba04c30138baa42048009d','402832a538ba04c30138ba04c3ea0000','de75983ce6224edaa7e0b20247f52068',NULL),('402832a538ba04c30138baa42050009e','402832a538ba04c30138ba04c3ea0000','abbbc17a25834e3ebb4ede30494acfea',NULL),('402832a538ba04c30138baa42059009f','402832a538ba04c30138ba04c3ea0000','a74444a41fe54cd2907a67f99a0c4478',NULL),('402832a538ba04c30138baa4206100a0','402832a538ba04c30138ba04c3ea0000','b4aa43c4297d46d3874666f541de9e7c',NULL),('402832a538ba04c30138baa4206900a1','402832a538ba04c30138ba04c3ea0000','d301729fe611455da1d3ad3e9425d3af',NULL),('402832a538ba04c30138baa4207100a2','402832a538ba04c30138ba04c3ea0000','b3b4bbed35bf4b85b1569706478eaf3f',NULL),('402832a538ba04c30138baa4207900a3','402832a538ba04c30138ba04c3ea0000','8d6b19a57d53412c956561ae9e59bbc7',NULL),('402832a538ba04c30138baa4208100a4','402832a538ba04c30138ba04c3ea0000','9b9d81de22ea4b8dac42632c88336689',NULL),('402832a538ba04c30138baa4208a00a5','402832a538ba04c30138ba04c3ea0000','01cc9967aa1b4c0aa7f2cea1cadc71f6',NULL),('402832a538ba04c30138baa4209000a6','402832a538ba04c30138ba04c3ea0000','23552cf3038241bebd00bc6d17f23dd2',NULL),('402832a538ba04c30138baa4209900a7','402832a538ba04c30138ba04c3ea0000','a39fe2d5a57b4ee59bc8a6a4ed0474f2',NULL),('402832a538ba04c30138baa420a300a8','402832a538ba04c30138ba04c3ea0000','d584925b823c4e36b9303ec1b7cde19d','5'),('402832a538ba04c30138baa420ad00a9','402832a538ba04c30138ba04c3ea0000','d0f5c6c18ee64f73962f29c2ac70a659',NULL),('402832a538ba04c30138baa420b600aa','402832a538ba04c30138ba04c3ea0000','7f7bb318b7434e5bb2d95a150756b993',NULL),('402832a538ba04c30138baa420be00ab','402832a538ba04c30138ba04c3ea0000','cc99d68d3d5c4ca5983f2156c8b54a3d','35'),('402832a538ba04c30138baa420c500ac','402832a538ba04c30138ba04c3ea0000','1ccbedf4ea71462b9fcd7bc765fd3583',NULL),('402832a538ba04c30138baa420cd00ad','402832a538ba04c30138ba04c3ea0000','a621e09ae0cb40579334c8487b6a8ee0',NULL),('402832a538ba04c30138baa420de00ae','402832a538ba04c30138ba04c3ea0000','7f43c1179af048fc9daf66e0ffce2a25',NULL),('4dc9d1ad2b6842de83af07751078956d','25f8a91dcdac4f0fa7f703e3bf787254','e3915e23017849339a3851f16beefdfc',NULL),('5f8fc88d9ba3477a8fe80a17a8d6b58a','25f8a91dcdac4f0fa7f703e3bf787254','49f152d32f3244008cb3c51d19799ea0','5.5.x'),('666a8fe99ab74a288a963d0829e1e5cf','25f8a91dcdac4f0fa7f703e3bf787254','de75983ce6224edaa7e0b20247f52068',NULL),('7f73c4f35a0e425ca98e7618f9086868','25f8a91dcdac4f0fa7f703e3bf787254','7f43c1179af048fc9daf66e0ffce2a25',NULL),('829695124a444d2fb46519ebb230aac0','25f8a91dcdac4f0fa7f703e3bf787254','b82597306d2b487e91fafae439b27184',NULL),('850cfeec35e54775862f227d624e94de','25f8a91dcdac4f0fa7f703e3bf787254','93de8f9577e140c2991360ccf957aaf9',NULL),('9a8358e1ba9f4cb0bc6b6d7b6a788b2a','25f8a91dcdac4f0fa7f703e3bf787254','d584925b823c4e36b9303ec1b7cde19d','1440'),('a0fdc0c5493d45e6a138e674c6e30d07','25f8a91dcdac4f0fa7f703e3bf787254','d301729fe611455da1d3ad3e9425d3af',NULL),('a66293a692a94d0a8d3f11b3f8d63acf','25f8a91dcdac4f0fa7f703e3bf787254','a32439175ecc43728f44be4101086747',NULL),('a75bdb603b744312990f4de016e14a71','25f8a91dcdac4f0fa7f703e3bf787254','a27b90b03c1b4fde98363393cfaf3dbe','up to 4 GB RAM, up to 10 GB storage'),('a76e402d3a6c4f6d9c12419e652495f2','25f8a91dcdac4f0fa7f703e3bf787254','e43a478d7bbd4b74b5f9d7890d9138c0',NULL),('b4eac791376a4d33b3f0b37203e039b3','25f8a91dcdac4f0fa7f703e3bf787254','2b100dde84d249ba8528f00831a699d2',NULL),('b8de2de44965484c8ace0e43e0a8d177','25f8a91dcdac4f0fa7f703e3bf787254','4d43b303c39c4e6dbabd9d6a9b282013',NULL),('b94f5922c91e421f9d4e869c68fcc969','25f8a91dcdac4f0fa7f703e3bf787254','2ea0e1d3799647a2a3316afed19cebfd','99.95'),('bed55344de8d49eabf6fff855501cc3f','25f8a91dcdac4f0fa7f703e3bf787254','74fdd897c45343a0999b9506339565eb',NULL),('c3121f2cfba349a1bd087e30a7518f9a','25f8a91dcdac4f0fa7f703e3bf787254','b3b4bbed35bf4b85b1569706478eaf3f',NULL),('c60fcbb88ce64c879a7fd413fff4e6dc','25f8a91dcdac4f0fa7f703e3bf787254','80340f71206d4fabb8a4c4a0b55f7349','10'),('c99e459086994d32a425656e49fbb560','25f8a91dcdac4f0fa7f703e3bf787254','82588cc2cfb04f5ea6d695ea08208552',NULL),('cb9936f555e64404b8d0966b9243c55b','25f8a91dcdac4f0fa7f703e3bf787254','541caa1e01d34c949e559fe2e89b6405',NULL),('ce4c0457f62e4606b72ca0f140d6c29a','25f8a91dcdac4f0fa7f703e3bf787254','efa28ce9de504ec3840c60d6ee63f3c2',NULL),('d426d0d99267441bbcdec31ba0d92128','25f8a91dcdac4f0fa7f703e3bf787254','f03e6987ca9441b8b181dd672d992f4d',NULL),('d52c6bf66324480c8de69455c889e45e','25f8a91dcdac4f0fa7f703e3bf787254','f439f47621c34cb3a1a5d2e2cb5b0a71',NULL),('d572e7e3308e41f29e994672b7e168b4','25f8a91dcdac4f0fa7f703e3bf787254','1aef5391ffc84f34a0c5c7eb9b8889b8','currently North America, but can be stored anywhere as well'),('e005f600a258404692b7ea0dfb5f3afb','25f8a91dcdac4f0fa7f703e3bf787254','04e630296aff4d70af54a1c7167fec6e',NULL),('e86f46f0e61142e8ace843a64264f511','25f8a91dcdac4f0fa7f703e3bf787254','9e8e656e554d48a896c0e469091dbcb9',NULL),('ecec77dd10204682af6dd7ea9ec51d74','25f8a91dcdac4f0fa7f703e3bf787254','522a2f79bcdd4f9a80b0e90f6b5856a0',NULL),('fb5e76799f9542e980ae77b13d39c66c','25f8a91dcdac4f0fa7f703e3bf787254','abbbc17a25834e3ebb4ede30494acfea',NULL),('fedfd78b25e4412dbe201cc05ff0b0f2','25f8a91dcdac4f0fa7f703e3bf787254','a74444a41fe54cd2907a67f99a0c4478',NULL);
/*!40000 ALTER TABLE `CloudDataStoreProperty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CloudDataStore`
--

DROP TABLE IF EXISTS `CloudDataStore`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CloudDataStore` (
  `id` char(32) NOT NULL,
  `name` varchar(256) NOT NULL,
  `provider` varchar(256) DEFAULT NULL,
  `website` varchar(256) DEFAULT NULL,
  `description` text,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CloudDataStore`
--

LOCK TABLES `CloudDataStore` WRITE;
/*!40000 ALTER TABLE `CloudDataStore` DISABLE KEYS */;
INSERT INTO `CloudDataStore` VALUES ('25f8a91dcdac4f0fa7f703e3bf787254','Google Cloud SQL','Google','https://developers.google.com/cloud-sql/','Google Cloud SQL is a web service that allows you to create, configure, and use relational databases that live in Google\'s cloud. It is a fully-managed service that maintains, manages, and administers your databases, allowing you to focus on your applications and services.\n\nBy offering the capabilities of a familiar MySQL database, the service enables you to easily move your data, applications, and services in and out of the cloud. This enables high data portability and helps you achieve faster time-to-market because you can quickly leverage your existing database.','2012-07-10 21:37:33'),('402832a538ba04c30138ba04c3ea0000','Amazon Relational Database Service (RDS)','Amazon Web Services (AWS)','http://aws.amazon.com/rds/','Amazon Relational Database Service (Amazon RDS) is a web service that makes it easy to set up, operate, and scale a relational database in the cloud. It provides cost-efficient and resizable capacity while managing time-consuming database administration tasks, freeing you up to focus on your applications and business. Amazon RDS gives you access to the capabilities of a familiar MySQL, Oracle or Microsoft SQL Server database engine. This means that the code, applications, and tools you already use today with your existing databases can be used with Amazon RDS. Amazon RDS automatically patches the database software and backs up your database, storing the backups for a user-defined retention period and enabling point-in-time recovery. You benefit from the flexibility of being able to scale the compute resources or storage capacity associated with your relational database instance via a single API call. In addition, Amazon RDS makes it easy to use replication to enhance availability and reliability for production databases. Amazon RDS for MySQL also enables you to scale out beyond the capacity of a single database deployment for read-heavy database workloads.','2012-07-24 19:24:30');
/*!40000 ALTER TABLE `CloudDataStore` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CDMCriterion`
--

DROP TABLE IF EXISTS `CDMCriterion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CDMCriterion` (
  `id` char(32) NOT NULL,
  `name` varchar(256) NOT NULL,
  `key` varchar(256) NOT NULL,
  `orderNumber` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CDMCriterion`
--

LOCK TABLES `CDMCriterion` WRITE;
/*!40000 ALTER TABLE `CDMCriterion` DISABLE KEYS */;
INSERT INTO `CDMCriterion` VALUES ('129ffc4df5644e3abda5f4ced83759f8','Utilization Increase','UTILIZATION_INCREASE',11),('274abf12614949b3a956dd4dc4e3fb72','Migration Strategy','MIGRATION_STRATEGY',1),('309dae3ab4f54c2b8e43aea37c1f7f7a','Migration Duration','MIGRATION_DURATION',9),('3ac49847897941e2ad6b36a0b224dcd2','Product/Version Change','PRODUCT_OR_VERSION_CHANGE',6),('62ae70ec83c445ba8d410e7eb734c898','Local DBL','LOCAL_DBL',2),('77cd349a88054696a362def189496dcf','Target Data Store Type','TARGET_DATA_STORE_TYPE',5),('9912094f31b248a69209e59933e842f0','Source Data Store Type','SOURCE_DATA_STORE_TYPE',4),('ad45e65b8729440caa251b4b8ec8fe88','Resource Utilization','RESOURCE_UTILIZATION',10),('c9237484d62d4094b91cbe60ef6a7c47','Direction of Data Movement','DIRECTION_OF_DATA_MOVEMENT',7),('d9be7c52341b454ab3874e1cb729ecf6','Migration Degree','MIGRATION_DEGREE',3),('f011c857ae2f426fa2d2ee6b9afd1f66','Migration Durability','MIGRATION_DURABILITY',8);
/*!40000 ALTER TABLE `CDMCriterion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Project_has_CDMScenario`
--

DROP TABLE IF EXISTS `Project_has_CDMScenario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Project_has_CDMScenario` (
  `Project_id` char(32) NOT NULL,
  `CDMScenario_id` char(32) NOT NULL,
  PRIMARY KEY (`Project_id`,`CDMScenario_id`),
  KEY `fk_Project_has_CDMScenario_CDMScenario1` (`CDMScenario_id`),
  KEY `fk_Project_has_CDMScenario_Project1` (`Project_id`),
  CONSTRAINT `fk_Project_has_CDMScenario_Project1` FOREIGN KEY (`Project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_CDMScenario_CDMScenario1` FOREIGN KEY (`CDMScenario_id`) REFERENCES `cdmscenario` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Project_has_CDMScenario`
--

LOCK TABLES `Project_has_CDMScenario` WRITE;
/*!40000 ALTER TABLE `Project_has_CDMScenario` DISABLE KEYS */;
INSERT INTO `Project_has_CDMScenario` VALUES ('2ec339c01d7e4bd0a4b7bc21192a29e0','38793cd441e2416ba6981e815979036a');
/*!40000 ALTER TABLE `Project_has_CDMScenario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CDHSCriterion`
--

DROP TABLE IF EXISTS `CDHSCriterion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CDHSCriterion` (
  `id` char(32) NOT NULL,
  `name` varchar(256) NOT NULL,
  `key` varchar(256) NOT NULL,
  `orderNumber` smallint(6) DEFAULT NULL,
  `CDHSCategory_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_CDSProperty_Category1` (`CDHSCategory_id`),
  CONSTRAINT `fk_CDSProperty_Category1` FOREIGN KEY (`CDHSCategory_id`) REFERENCES `cdhscategory` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CDHSCriterion`
--

LOCK TABLES `CDHSCriterion` WRITE;
/*!40000 ALTER TABLE `CDHSCriterion` DISABLE KEYS */;
INSERT INTO `CDHSCriterion` VALUES ('05d7669ba496488ba7f0e6dbb6266be2','Storage Access','INTEROPERABILITY_STORAGE_ACCESS',3,'7d04199ea39041c0a3538864fcd92e15'),('0629f4ed0aee464db0118c38263ca293','Backup Method','BACKUP_METHOD',4,'f77f2bd877da4c1a9351fad06257b129'),('0be224e6c57e4d3ea947cfd0b8767c4c','Accessability','STORAGE_ACCESSABILITY',1,'2a6fd7a6d71e41ff8d8e54f418e0ce5a'),('0d3da195c7ff495cb63071b10f5999ef','Choice','LOCATION_CHOICE',1,'888f26469277424b87bf29fe2b4e0a16'),('132db49a4cd747109b038bef98f2b811','ORM','INTEROPERABILITY_ORM',4,'7d04199ea39041c0a3538864fcd92e15'),('15767383a8574a69806be60640e20d9a','Max Domain (NoSQL) / Table (RDBMS) / Bucket (BLOB) Size','DATA_CONSTRAINTS_DOMAIN_SIZE',2,'cf265ea08a794fbeb8ac9dd9d7f8b960'),('16cd19f4ee004ef28779284ccc833668','Schema Customizable','FLEXABILITY_SCHEMA_CUSTOMIZABLE',1,'c906192a2dc948738c2c661026fff3ec'),('19e171736518456c87199c1b6b8cb7a1','Transfer Encryption','SECURITY_TRANSFER_ENCRYPTION',2,'1b38031d4be543e6b9f1c3c7064fd86e'),('1e3655a562f143af8a5a805146a4d998','Storage Encryption','SECURITY_STORAGE_ENCRYPTION',1,'1b38031d4be543e6b9f1c3c7064fd86e'),('1fa1ede903434557b0238d32a45d7d83','Service Model','CLOUD_COMPUTING_SERVICE_MODEL',1,'bd789672517448adac99bdfb46ff8d6a'),('203b9d84c80142ffa194dd8bfbb356dc','Replication','AVAILABILITY_REPLICATION',1,'d8df252de1a7418796036f435cff0f5d'),('25aa3a4a92504794831d316f1617d509','Availability in case of Partitioning','CAP_AVAILABILITY_IN_CASE_OF_PARTITIONING',2,'d0a4c6c1b2e64a83bd27909cf25f655a'),('2b0d0cb04c304147b0231b681844395d','Predictability Read/Write/Response','PERFORMANCE_PREDICTABILITY',1,'fb6c52d9628a499c923c4a957a9d1a26'),('31d1151cdf3a497e9fd4af3220926992','Number of Backups Kept','BACKUP_NUMBERS_KEPT',3,'f77f2bd877da4c1a9351fad06257b129'),('34e33e2aa1ac48e0a986f865345ee7a7','Geographic location','LOCATION_GEOGRAPHY',2,'888f26469277424b87bf29fe2b4e0a16'),('433fca4556b44380802c92a49bd84080','Data Portability','INTEROPERABILITY_DATA_PORTABILITY',1,'7d04199ea39041c0a3538864fcd92e15'),('4c3cbaff88be493f83f2bad49a4a0186','Consistency Model','CAP_CONSISTENCY_MODEL',1,'d0a4c6c1b2e64a83bd27909cf25f655a'),('4e6c61393c644fab950aab2427a78ed3','Time to Launch new Instance','SCALABILITY_TIME_TO_LAUNCH',4,'97d3376a010c4e35a6c41f4e11f288b0'),('524dc181b2394434b16c02a93608290c','Automated Scalability Criterion','SCALABILITY_CRITERION',5,'97d3376a010c4e35a6c41f4e11f288b0'),('58fc6100d2664d82b469668f333bbd62','Replication Location','AVAILABILITY_REPLICATION_LOCATION',4,'d8df252de1a7418796036f435cff0f5d'),('5a249ab25af9472191a814de5b6e70cb','Product including Version','COMPATABILITY_PRODUCT_AND_VERSION',1,'6e1b84598995410b950bede10fdb0729'),('6380c010834a46418089a971aa83a668','Instance Size Predefined','DATA_CONSTRAINTS_INSTANTE_SIZE_PREDEFINED',5,'cf265ea08a794fbeb8ac9dd9d7f8b960'),('750a033f224246d194bd64137aa850c6','Interrupt of Access','BACKUP_INTERRUPT_OF_ACCESS',2,'f77f2bd877da4c1a9351fad06257b129'),('753c9b7a32bd43d9ba176f0ea8438902','Bandwidth Limit','PERFORMANCE_BANDWIDTH_LIMIT',2,'fb6c52d9628a499c923c4a957a9d1a26'),('76cc72b7d4b54c209e7a5a5f3deacc19','Supported Integrated Developement Environment (IDE)','INTEROPERABILITY_IDE',6,'7d04199ea39041c0a3538864fcd92e15'),('831e07703f51453b905007f0b11213b3','Authentication','SECURITY_AUTHENTICATION',4,'1b38031d4be543e6b9f1c3c7064fd86e'),('840c6b9e81b54c22b9b5ed9d59923fd8','Migration & Deployment Support','INTEROPERABILITY_MIGRATION_AND_DEPLOYMENT_SUPPORT',5,'7d04199ea39041c0a3538864fcd92e15'),('8e0dbb23311840c68d033665ed57aec4','Developer SDKs','INTEROPERABILITY_SDK',7,'7d04199ea39041c0a3538864fcd92e15'),('8efb3276cd8149bd8c5c0ef43460d15e','Replication Method','AVAILABILITY_REPLICATION_METHOD',3,'d8df252de1a7418796036f435cff0f5d'),('8fc3c4f5ff70448bbf3277ae12cff8f9','Multi-tenancy Capability','MULTI_TENANCY_SUPPORT',1,'244e16c145294c348c1e480e3d78f9f4'),('90e99c1e1a4d47b3bd45abc1b993219d','Firewall','SECURITY_FIREWALL',3,'1b38031d4be543e6b9f1c3c7064fd86e'),('93cd078658d946faa3cd24eb9808044c','Storage Type','STORAGE_TYPE',2,'2a6fd7a6d71e41ff8d8e54f418e0ce5a'),('9893bfd6377e4a5ab5c1cba1f9bfe050','Data Exchange Format','INTEROPERABILITY_DATA_EXCHANGE_FORMAT',2,'7d04199ea39041c0a3538864fcd92e15'),('9c14b41c82cc4f74bbbd54e49e675948','Transaction Support','STORAGE_TRANSACTION_SUPPORT',3,'2a6fd7a6d71e41ff8d8e54f418e0ce5a'),('b2cf6ee6d7434babb9aeaec0025b6599','Degree of Automation','MANAGEMENT_AND_MAINTENANCE_EFFORT_DEGREE_OF_AUTOMATION',1,'4e57e820d7724e15bda701e589a48a2f'),('bf916be1d568479f8c6bd0ae0704cb61','Throughput','PERFORMANCE_THROUGHPUT_LIMIT',3,'fb6c52d9628a499c923c4a957a9d1a26'),('c150e13fa2aa41828d9fb7ee66790106','Max Item (NoSQL) / Row (RDBMS) / File (BLOB) Size','DATA_CONSTRAINTS_ITEM_SIZE',1,'cf265ea08a794fbeb8ac9dd9d7f8b960'),('c45f80d7574945eeb744ec9a0fce83c5','Max Item/Row/File Number Per Instance','DATA_CONSTRAINTS_ITEM_NUMBER',3,'cf265ea08a794fbeb8ac9dd9d7f8b960'),('c9d8bd1c1d7d4a38b1382fd39b43ad31','Degree of Automation','SCALABILITY_DEGREE_OF_AUTOMATION',1,'97d3376a010c4e35a6c41f4e11f288b0'),('cc4392bf333347a3ae9243645241c66e','Max Size Per Instance (All Domains, All Tables, All Buckets)','DATA_CONSTRAINTS_INSTANCE_SIZE',4,'cf265ea08a794fbeb8ac9dd9d7f8b960'),('d03cac7743ff44a598c4a0c5772f3ac8','Confidentiality','SECURITY_CONFIDENTIALITY',5,'1b38031d4be543e6b9f1c3c7064fd86e'),('d1264caee2fd482589f1ee5c33481e36','Deployment Model','CLOUD_COMPUTING_DEPLOYMENT_MODEL',2,'bd789672517448adac99bdfb46ff8d6a'),('d3b1d7a36e13434188017e63ad4d3a90','Cloud Location','LOCATION_CLOUD',3,'888f26469277424b87bf29fe2b4e0a16'),('d6466d6dddbf499dad2492353964385a','Schema','FLEXABILITY_SCHEMA_SUPPORT',1,'c906192a2dc948738c2c661026fff3ec'),('d66f09daaa004fa6b37514575973fdb8','Degree','AVAILABILITY_DEGREE',6,'d8df252de1a7418796036f435cff0f5d'),('d6a0185c6cd44aeebc686a7ce29585dc','Degree','SCALABILITY_DEGREE',3,'97d3376a010c4e35a6c41f4e11f288b0'),('dba6f34e727a4a2292a5518b5de3d8e9','Automatic Failover','AVAILABILITY_AUTOMATIC_FAILOVER',5,'d8df252de1a7418796036f435cff0f5d'),('e1784b79bf634c82b594a86ee780d02e','Multi-tenancy Type','MULTI_TENANCY_TYPE',2,'244e16c145294c348c1e480e3d78f9f4'),('eb93b4e3ff7d4c3097cdbb64a95e3def','Supported KPIs','MONITORING_KPIS',1,'1e8610f35455463397d8bd79e225be18'),('ef5ab46bd349408d85972f68ff560110','Replication Type','AVAILABILITY_REPLICATION_TYPE',2,'d8df252de1a7418796036f435cff0f5d'),('f093b7b9fcdd4fac841758746a653b13','Latency','PERFORMANCE_LATENCY',4,'fb6c52d9628a499c923c4a957a9d1a26'),('f53a4ff1c0ad47ccaaf129a9d010ece3','Type','SCALABILITY_TYPE',2,'97d3376a010c4e35a6c41f4e11f288b0'),('fa3663bf6c944e9db8bb2d50dfe67e4a','Integrity','SECURITY_INTEGRITY',6,'1b38031d4be543e6b9f1c3c7064fd86e'),('fdca854074a64d99930eb1fc0d78bd80','Backup Interval','BACKUP_INTERVAL',1,'f77f2bd877da4c1a9351fad06257b129');
/*!40000 ALTER TABLE `CDHSCriterion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LocalDBLCriterionPossibleValue`
--

DROP TABLE IF EXISTS `LocalDBLCriterionPossibleValue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LocalDBLCriterionPossibleValue` (
  `id` char(32) NOT NULL,
  `key` varchar(45) NOT NULL,
  `name` varchar(256) NOT NULL,
  `orderNumber` smallint(6) DEFAULT NULL,
  `LocalDBLCriterion_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_LocalDBLCriterionPossibleValue_LocalDBLCriterion1` (`LocalDBLCriterion_id`),
  CONSTRAINT `fk_LocalDBLCriterionPossibleValue_LocalDBLCriterion1` FOREIGN KEY (`LocalDBLCriterion_id`) REFERENCES `localdblcriterion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LocalDBLCriterionPossibleValue`
--

LOCK TABLES `LocalDBLCriterionPossibleValue` WRITE;
/*!40000 ALTER TABLE `LocalDBLCriterionPossibleValue` DISABLE KEYS */;
INSERT INTO `LocalDBLCriterionPossibleValue` VALUES ('0161517218744bcc80d12ee76c69024e','DNS','Yes',1,'6e9bfc67a0754a629e190acb5a82e27c'),('0984bcfd72c54119bb9ce42dc28ce82c','NO_SYNC','No',1,'c383b49a50a5445da5f083b8f9199c4a'),('09f90f79ae394c338532ca20c6c52baa','IAAS','IaaS',4,'35ff997a738b4434b7d9fe82b100a18f'),('18a0956590824ad59bec61ce15ffa48f','NO_CLOUD_DEPLOYMENT','None (not hosted in the cloud)',1,'03761c19bd91439e87e2ac061590e24c'),('1ebd8c1fe37645c8b77b0c090e0457e4','NO_USE_OF_TRANSACTION','No',2,'58f8bf5796ae4d2dbfdca614fc1e5a89'),('21d1943636844f208a47d308ec7658c9','TWO_WAY_SYNC','Yes, two-way synchronization',3,'c383b49a50a5445da5f083b8f9199c4a'),('3dbad3571b084f8a89c381a3f44b5b66','NOT_DISTRIBUTED','No',2,'e0cc9cdeb1194d0e9255274eedd30ae7'),('4031d7ad2529419c958fbd7f88d13b61','HYBRID_CLOUD','Hybrid Cloud',5,'03761c19bd91439e87e2ac061590e24c'),('4223f7adc6fd4a9aaa6d5acb88d245cf','USE_OF_NON_STANDARD_SQL','Yes',1,'504369991bce47acb29b2c7aaa794b6c'),('4a09cf6d30964f88955a1388398c9d42','ONE_WAY_SYNC','Yes, one-way synchronization',2,'c383b49a50a5445da5f083b8f9199c4a'),('4a5720ae86884c37a23c3aa071b98ad7','OFF_PREMISE','Off-premise',2,'3952b94249334f8ba4279d4391f71401'),('5503601301404df6a936e150007ed4d6','NO_USE_OF_TRIGGER_OR_STPR','No',2,'6fc99aba40f54222be80653994cbfcf4'),('5bac7838361c4c61966b281b86bc4bae','NO_CLOUD_SERVICE','None (not hosted in the cloud)',1,'35ff997a738b4434b7d9fe82b100a18f'),('5e433851784b4c78b09bbc95a6e154ed','SAAS','SaaS',2,'35ff997a738b4434b7d9fe82b100a18f'),('5ebb33171d9b4499b06cedcbea6229f8','USE_OF_TRANSACTION','Yes',1,'58f8bf5796ae4d2dbfdca614fc1e5a89'),('695d65b893ce4490882d330509abd08d','RDBMS','RDBMS',1,'672be1638ad840ab84d13ef229a81c3c'),('6c138ad5cb1841759caf137bb2153b63','PRIVATE_CLOUD','Private Cloud',2,'03761c19bd91439e87e2ac061590e24c'),('899af51ef2494ebdbde68ddaa36e38b3','PUBLIC_CLOUD','Public Cloud',3,'03761c19bd91439e87e2ac061590e24c'),('9994e49c11294eb1b2552c9c216038c0','NOSQL','NoSQL',2,'672be1638ad840ab84d13ef229a81c3c'),('9ca003187a9140d2874448a6e5f2ef61','ON_PREMISE','On-premise',1,'3952b94249334f8ba4279d4391f71401'),('9d9e093b666d438eb1816474bff8516d','DISTRIBUTED','Yes',1,'e0cc9cdeb1194d0e9255274eedd30ae7'),('a703e2809fd245a5b76b6ea9222e77ef','NO_USE_OF_JOINS','No',2,'5f07ef358d46490abc9a99d6df295ec9'),('a8611be8fc86451fb25b0814c1b9a52d','NO_USE_OF_NON_STANDARD_SQL','No',2,'504369991bce47acb29b2c7aaa794b6c'),('acbf009e9b614910ae2f7f16601004a2','BLOB','Blob Store',3,'672be1638ad840ab84d13ef229a81c3c'),('af2bf520480840e896137b1333d041d5','COMMUNITY_CLOUD','Community Cloud',4,'03761c19bd91439e87e2ac061590e24c'),('de761080b73b4e0faac4f31a784db878','USE_OF_TRIGGER_OR_STPR','Yes',1,'6fc99aba40f54222be80653994cbfcf4'),('e5dd7851d8a649c48a5d459411b80bac','NO_DNS','No',2,'6e9bfc67a0754a629e190acb5a82e27c'),('f7ae4407b4074879919930d369821f49','USE_OF_JOINS','Yes',1,'5f07ef358d46490abc9a99d6df295ec9'),('faf184ed9f844e7e8dff0e22b8f9f6d3','PAAS','PaaS',3,'35ff997a738b4434b7d9fe82b100a18f');
/*!40000 ALTER TABLE `LocalDBLCriterionPossibleValue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CDHSCategory`
--

DROP TABLE IF EXISTS `CDHSCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CDHSCategory` (
  `id` char(32) NOT NULL,
  `name` varchar(256) NOT NULL,
  `orderNumber` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CDHSCategory`
--

LOCK TABLES `CDHSCategory` WRITE;
/*!40000 ALTER TABLE `CDHSCategory` DISABLE KEYS */;
INSERT INTO `CDHSCategory` VALUES ('1b38031d4be543e6b9f1c3c7064fd86e','Security',4),('1e8610f35455463397d8bd79e225be18','Monitoring',15),('244e16c145294c348c1e480e3d78f9f4','Multi-tenancy',17),('2a6fd7a6d71e41ff8d8e54f418e0ce5a','Storage',9),('4e57e820d7724e15bda701e589a48a2f','Management and Maintenance effort',14),('6e1b84598995410b950bede10fdb0729','Compatibility',8),('7d04199ea39041c0a3538864fcd92e15','Interoperability',7),('888f26469277424b87bf29fe2b4e0a16','Location',5),('97d3376a010c4e35a6c41f4e11f288b0','Scalability',1),('bd789672517448adac99bdfb46ff8d6a','Cloud Computing',13),('c906192a2dc948738c2c661026fff3ec','Flexibility',12),('cf265ea08a794fbeb8ac9dd9d7f8b960','Data Constraints',6),('d0a4c6c1b2e64a83bd27909cf25f655a','CAP (consistency, availibility, partition tolerance)',11),('d8df252de1a7418796036f435cff0f5d','Availability',2),('f77f2bd877da4c1a9351fad06257b129','Backup',16),('fb6c52d9628a499c923c4a957a9d1a26','Performance',10);
/*!40000 ALTER TABLE `CDHSCategory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LocalDBLProperty`
--

DROP TABLE IF EXISTS `LocalDBLProperty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LocalDBLProperty` (
  `id` char(32) NOT NULL,
  `Project_id` char(32) NOT NULL,
  `LocalDBLCriterionPossibleValue_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_Project_has_LocalDBLCriterion_Project1` (`Project_id`),
  KEY `fk_Project_has_LocalDBLCriterion_LocalDBLCriterionPossibleVal1` (`LocalDBLCriterionPossibleValue_id`),
  CONSTRAINT `fk_Project_has_LocalDBLCriterion_Project1` FOREIGN KEY (`Project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_Project_has_LocalDBLCriterion_LocalDBLCriterionPossibleVal1` FOREIGN KEY (`LocalDBLCriterionPossibleValue_id`) REFERENCES `localdblcriterionpossiblevalue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LocalDBLProperty`
--

LOCK TABLES `LocalDBLProperty` WRITE;
/*!40000 ALTER TABLE `LocalDBLProperty` DISABLE KEYS */;
INSERT INTO `LocalDBLProperty` VALUES ('2419dd81c040400d9971b62c7d01dfdc','2ec339c01d7e4bd0a4b7bc21192a29e0','18a0956590824ad59bec61ce15ffa48f'),('35257087cfaa4dcbaaa98f65dcd3d3de','2ec339c01d7e4bd0a4b7bc21192a29e0','5ebb33171d9b4499b06cedcbea6229f8'),('59fca5c8384048b2a721c3bac1023f4b','2ec339c01d7e4bd0a4b7bc21192a29e0','3dbad3571b084f8a89c381a3f44b5b66'),('5e9689bc2f1b4acea3714cb714c11f69','2ec339c01d7e4bd0a4b7bc21192a29e0','695d65b893ce4490882d330509abd08d'),('756877a61eda489e909da31f38db5ee6','2ec339c01d7e4bd0a4b7bc21192a29e0','5503601301404df6a936e150007ed4d6'),('7ad7a5716e1e449eac00a0155f5d7be0','2ec339c01d7e4bd0a4b7bc21192a29e0','a8611be8fc86451fb25b0814c1b9a52d'),('8b4c480dd1a648af8e064e61e08ee176','2ec339c01d7e4bd0a4b7bc21192a29e0','21d1943636844f208a47d308ec7658c9'),('9edcc9cb138c4345b413aeb3cd4a73ac','2ec339c01d7e4bd0a4b7bc21192a29e0','9ca003187a9140d2874448a6e5f2ef61'),('ba09b31b11314afd869ff9e35b31bbf5','2ec339c01d7e4bd0a4b7bc21192a29e0','5bac7838361c4c61966b281b86bc4bae'),('e3c0c6e10bec4d49801585677da60304','2ec339c01d7e4bd0a4b7bc21192a29e0','f7ae4407b4074879919930d369821f49'),('f9909bec32964d599c81fd1adbb505f2','2ec339c01d7e4bd0a4b7bc21192a29e0','0161517218744bcc80d12ee76c69024e');
/*!40000 ALTER TABLE `LocalDBLProperty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CDHSCriterionPossibleValue`
--

DROP TABLE IF EXISTS `CDHSCriterionPossibleValue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CDHSCriterionPossibleValue` (
  `id` char(32) NOT NULL,
  `key` varchar(256) NOT NULL,
  `name` varchar(256) NOT NULL,
  `type` enum('SELECT','INPUT') NOT NULL DEFAULT 'SELECT',
  `orderNumber` smallint(6) DEFAULT NULL,
  `scale` enum('NOT_COMPARABLE','UPPER_IS_BETTER','LOWER_IS_BETTER') DEFAULT NULL,
  `CDHSCriterion_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_CDSCriterionPossibleValue_CDSProperty1` (`CDHSCriterion_id`),
  CONSTRAINT `fk_CDSCriterionPossibleValue_CDSProperty1` FOREIGN KEY (`CDHSCriterion_id`) REFERENCES `cdhscriterion` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CDHSCriterionPossibleValue`
--

LOCK TABLES `CDHSCriterionPossibleValue` WRITE;
/*!40000 ALTER TABLE `CDHSCriterionPossibleValue` DISABLE KEYS */;
INSERT INTO `CDHSCriterionPossibleValue` VALUES ('01cc9967aa1b4c0aa7f2cea1cadc71f6','MONITORING_NETWORK','Data Transfer - Network (I/O)','SELECT',2,NULL,'eb93b4e3ff7d4c3097cdbb64a95e3def'),('04e630296aff4d70af54a1c7167fec6e','EXCHANGE_FORMAT_PROPRIETORY','Proprietory','SELECT',3,NULL,'9893bfd6377e4a5ab5c1cba1f9bfe050'),('0b9e59546f0143f9909d39748d5090b6','NO_BACKUP','None','SELECT',1,NULL,'fdca854074a64d99930eb1fc0d78bd80'),('0ee100bc2f8e4664b254ceee46f7f942','BACKUP_AS_EXPORT_TO_FILE','File-backup (export)','SELECT',2,NULL,'0629f4ed0aee464db0118c38263ca293'),('10af3aa4201a4a749c132dfd7fd52bf5','LATENCY','Milliseconds','INPUT',1,NULL,'f093b7b9fcdd4fac841758746a653b13'),('1513f48247434f38a9f60c0a59917478','LOCATION_CHOICE','Yes','SELECT',1,NULL,'0d3da195c7ff495cb63071b10f5999ef'),('195c7c2347644e55910fb26b3f4ead2c','STORAGE_ACCESS_VIA_REST','REST-API','SELECT',2,NULL,'05d7669ba496488ba7f0e6dbb6266be2'),('1aef5391ffc84f34a0c5c7eb9b8889b8','REGION','Region/Country/Continent','INPUT',1,'NOT_COMPARABLE','34e33e2aa1ac48e0a986f865345ee7a7'),('1b9edce94f594385837dae2ccabc9a35','SCALE_ON_SYSTEM_LOAD','System Load','SELECT',2,NULL,'524dc181b2394434b16c02a93608290c'),('1bee9b085d694a54afa2733966fa3865','CONFIDENTIALITY','Yes','SELECT',1,NULL,'d03cac7743ff44a598c4a0c5772f3ac8'),('1ccbedf4ea71462b9fcd7bc765fd3583','BACKUP_AS_FILE_SYSTEM_SNAPSHOT','Snapshot (file system)','SELECT',1,NULL,'0629f4ed0aee464db0118c38263ca293'),('1f9f5a13e5af4e168a50a97424654e6c','HORIZONTAL_SCALABILITY','Horizontal (scale out)','SELECT',2,NULL,'f53a4ff1c0ad47ccaaf129a9d010ece3'),('22031dbde30845d0a96e9c5035960fb8','HYBRID_CLOUD','Hybrid Cloud','SELECT',4,NULL,'d1264caee2fd482589f1ee5c33481e36'),('23552cf3038241bebd00bc6d17f23dd2','MONITORING_DISK','Data Transfer - Disk (I/O)','SELECT',3,NULL,'eb93b4e3ff7d4c3097cdbb64a95e3def'),('24566eb326d9462b8214ff8340a4fb1a','NO_SYNC','No Sync','SELECT',7,NULL,'433fca4556b44380802c92a49bd84080'),('2ae7e9a6a53244478d8c9b2de425bcd2','MULTI_TENANCY_USING_MULTIPLE_INSTANCES','Multiple Instances','SELECT',1,NULL,'e1784b79bf634c82b594a86ee780d02e'),('2b100dde84d249ba8528f00831a699d2','NO_INSTANCE_SIZE_PREDEFINED','No','SELECT',1,NULL,'6380c010834a46418089a971aa83a668'),('2b4be7bd74dc49bc9f7296ccb4a82123','MINUTES_TO_LAUNCH_NEW_INSTANCE','Minutes','INPUT',2,'LOWER_IS_BETTER','4e6c61393c644fab950aab2427a78ed3'),('2b78ae1efdaa44929aa5bda792b4e5fe','MIGRATION_AND_DEPLOYMENT_SUPPORT','Yes','SELECT',1,NULL,'840c6b9e81b54c22b9b5ed9d59923fd8'),('2b7a20fc399e4a63a0cb088524a0b186','BLOB_STORE','Blob Store','SELECT',3,NULL,'93cd078658d946faa3cd24eb9808044c'),('2cae18ba41714f1582d4b3965b6d6225','MAX_ITEM_NUMBER','Number','INPUT',1,'UPPER_IS_BETTER','c45f80d7574945eeb744ec9a0fce83c5'),('2ea0e1d3799647a2a3316afed19cebfd','AVAILABILITY_DEGREE','Percentage','INPUT',1,'UPPER_IS_BETTER','d66f09daaa004fa6b37514575973fdb8'),('31fc895c1ac246f68b6bd9de0b8ed425','NO_BACKUPS_KEPT','None','SELECT',1,NULL,'31d1151cdf3a497e9fd4af3220926992'),('34075599f45648459f75c50b12867667','ON_PREMISE','On-Premise','SELECT',1,NULL,'d3b1d7a36e13434188017e63ad4d3a90'),('3626ddebb43149b9946b6a4b8dc070f5','NOT_AVAILABLE_IN_CASE_OF_PARTITIONING','Not available','SELECT',2,NULL,'25aa3a4a92504794831d316f1617d509'),('3964513c41fc4825b0a96ff8aa6a115b','VERTICAL_SCALABILITY','Vertical (scale up)','SELECT',1,NULL,'f53a4ff1c0ad47ccaaf129a9d010ece3'),('3a2479d6a7c745fda0aefb0031aa12e7','FIREWALL','Yes','SELECT',1,NULL,'90e99c1e1a4d47b3bd45abc1b993219d'),('3e22e137b5a04dee94d2c49415026e4c','BACKUP_DOES_NOT_INTERRUPT_PRODUCTION','No','SELECT',2,NULL,'750a033f224246d194bd64137aa850c6'),('3f9e6ca0db8c4bb79d6a959ba025d4e9','NO_AUTOMATIC_FAILOVER','No','SELECT',2,NULL,'dba6f34e727a4a2292a5518b5de3d8e9'),('41dac1d4ed454eecab48c561d4f2b971','DATA_STORE_ENGINE_DB2','DB2','SELECT',4,NULL,'5a249ab25af9472191a814de5b6e70cb'),('435ee48d0b6445bda914af6e63b34bb4','OFF_PREMISE','Off-Premise','SELECT',2,NULL,'d3b1d7a36e13434188017e63ad4d3a90'),('48b4bab9d5044e9e9c793a422dfccdbf','TRANSFER_ENCRYPTION','Yes','SELECT',1,NULL,'19e171736518456c87199c1b6b8cb7a1'),('49f152d32f3244008cb3c51d19799ea0','DATA_STORE_ENGINE_VERSION','Version','INPUT',5,'NOT_COMPARABLE','5a249ab25af9472191a814de5b6e70cb'),('4ace3a3a36e443b6a0ddaeaf3621d126','NO_REPLICATION','None','SELECT',1,NULL,'ef5ab46bd349408d85972f68ff560110'),('4d43b303c39c4e6dbabd9d6a9b282013','AUTHENTICATION','Yes','SELECT',1,NULL,'831e07703f51453b905007f0b11213b3'),('4dd9f75148744006ac1da651cf76fb1b','DOTNET_SDK','.Net','SELECT',2,NULL,'8e0dbb23311840c68d033665ed57aec4'),('4dff53a210284f3aa3144b99b4d9ea87','REPLICATION_IN_DIFFERENT_DATA_CENTER','Different Data Center in Same Region','SELECT',3,NULL,'58fc6100d2664d82b469668f333bbd62'),('4e34c0f06d224380952d26c36c3f9f62','INTELLIJ_SUPPORT','IntelliJ IDEA','SELECT',4,NULL,'76cc72b7d4b54c209e7a5a5f3deacc19'),('50d636f6015d4ae89aa00b4369b654a1','NUMBER_OF_BACKUPS_KEPT','Number of backups','INPUT',2,'UPPER_IS_BETTER','31d1151cdf3a497e9fd4af3220926992'),('51ebc0a32ae04da5875465603a47a3f0','DATA_STORE_ENGINE_POSTGRE_SQL','PostgreSQL','SELECT',2,NULL,'5a249ab25af9472191a814de5b6e70cb'),('522a2f79bcdd4f9a80b0e90f6b5856a0','ECLIPSE_SUPPORT','Eclipse','SELECT',1,NULL,'76cc72b7d4b54c209e7a5a5f3deacc19'),('541caa1e01d34c949e559fe2e89b6405','JAVA_SDK','Java','SELECT',1,NULL,'8e0dbb23311840c68d033665ed57aec4'),('556ee409bc494431b5c01795c940edb4','MAX_ITEM_SIZE','Byte','INPUT',1,'UPPER_IS_BETTER','c150e13fa2aa41828d9fb7ee66790106'),('5868fe21c388486f969d406e1394c7ed','STORAGE_ACCESS_VIA_PROPRIETORY','Proprietory','SELECT',4,NULL,'05d7669ba496488ba7f0e6dbb6266be2'),('592c252c3e4542bb918949ffcdcb1655','WEAK_CONSISTENCY','Weak','SELECT',2,NULL,'4c3cbaff88be493f83f2bad49a4a0186'),('5a328b1082f9426bacb0a5511f99ba22','AUTOMATED_UPGRADE','Managed/Automated','SELECT',2,NULL,'b2cf6ee6d7434babb9aeaec0025b6599'),('5c3fbbe204824d07bf5a2a8f9970cb84','REPLICATION_IN_SAME_DATA_CENTER','Same Data Center (AZ)','SELECT',2,NULL,'58fc6100d2664d82b469668f333bbd62'),('5d3bf5d3340b4bc186f07644577733dc','NO_TRANSFER_ENCRYPTION','No','SELECT',2,NULL,'19e171736518456c87199c1b6b8cb7a1'),('5efd0f8854124193995a2c41d6834d4f','MULTI_TENANCY_SUPPORT','Yes','SELECT',1,NULL,'8fc3c4f5ff70448bbf3277ae12cff8f9'),('625df3e5bb3a47af9a718379c93cf9d8','DATA_STORE_ENGINE_MS_SQL','MS SQL (SQL Server)','SELECT',3,NULL,'5a249ab25af9472191a814de5b6e70cb'),('63a16fd7431d44409c32e96de8c316ee','PHP_SDK','PHP','SELECT',3,NULL,'8e0dbb23311840c68d033665ed57aec4'),('66c52278fdc94ca1a9fa4ad0bf0ff167','IMPORT','Import','SELECT',2,NULL,'433fca4556b44380802c92a49bd84080'),('66ed93e64142462c86b4249a371ffa68','INSTANCE_SIZE_PREDEFINED','Yes','SELECT',1,NULL,'6380c010834a46418089a971aa83a668'),('69f8e644b00649d59c3c8f0eae06b8ff','NO_LOCATION_CHOICE_BUT_AUTOMATIC_SELECTED','No - Automatically Selected','SELECT',3,NULL,'0d3da195c7ff495cb63071b10f5999ef'),('6b45017711f442aab5f42cc1479511cc','RUBY_SDK','Ruby','SELECT',4,NULL,'8e0dbb23311840c68d033665ed57aec4'),('6e5ab0c6eb054bd28dffd705f036221a','MULTI_TENANCY_NATIVE','Native Multi-Tenancy','SELECT',2,NULL,'e1784b79bf634c82b594a86ee780d02e'),('6f0c63597b094dbe9303bf01fb7055a3','ONE_WAY_SYNC','One-Way-Synchronisation','SELECT',5,NULL,'433fca4556b44380802c92a49bd84080'),('722f8b818ac3481b9da077d27d375e78','RDBMS','RDBMS','SELECT',1,NULL,'93cd078658d946faa3cd24eb9808044c'),('73ff61cdbe0c4c62938739a9389392bb','STORAGE_ENCRYPTION','Yes','SELECT',1,NULL,'1e3655a562f143af8a5a805146a4d998'),('74fdd897c45343a0999b9506339565eb','NO_AUTOMATED_SCALABILITY_CRITERION','None','SELECT',1,NULL,'524dc181b2394434b16c02a93608290c'),('798ef223cc4847efa69d360634d30852','NO_INTEGRITY','No','SELECT',2,NULL,'fa3663bf6c944e9db8bb2d50dfe67e4a'),('7e9975ca7cb74be680c410ec39581cc7','PRIVATE_CLOUD','Private Cloud','SELECT',1,NULL,'d1264caee2fd482589f1ee5c33481e36'),('7f1f0721c9c94254a52f4bea77261c49','STORAGE_ACCESS_VIA_SOA','SOA','SELECT',1,NULL,'05d7669ba496488ba7f0e6dbb6266be2'),('7f43c1179af048fc9daf66e0ffce2a25','NO_MULTI_TENANCY_SUPPORT','No','SELECT',2,NULL,'8fc3c4f5ff70448bbf3277ae12cff8f9'),('7f7bb318b7434e5bb2d95a150756b993','BACKUP_INTERRUPTS_PRODUCTION','Yes','SELECT',1,NULL,'750a033f224246d194bd64137aa850c6'),('7fee0f9579c84aba9f9ae6da631cb5d0','PARTIAL_AUTOMATED_SCALABILITY','Partial Automation (Mix of Aut. and Manual)','SELECT',3,NULL,'c9d8bd1c1d7d4a38b1382fd39b43ad31'),('80340f71206d4fabb8a4c4a0b55f7349','MAX_INSTANCE_SIZE','GB','INPUT',1,'UPPER_IS_BETTER','cc4392bf333347a3ae9243645241c66e'),('80b56e350c8b4cc7990f3c6fc56b2fbe','NO_SDK','None','SELECT',6,NULL,'8e0dbb23311840c68d033665ed57aec4'),('80d75fad8f6d49f5b7474ec662d31dab','NO_SDK_NEEDED_SINCE_STANDARDS_USED','None Needed (Standard Support)','SELECT',7,NULL,'8e0dbb23311840c68d033665ed57aec4'),('81c8bb2c8e1e42fca6285b616fd26516','ORM_LINQ','LINQ','SELECT',3,NULL,'132db49a4cd747109b038bef98f2b811'),('82588cc2cfb04f5ea6d695ea08208552','SYNCHRONOUS_REPLICATION','Synchronous','SELECT',2,NULL,'8efb3276cd8149bd8c5c0ef43460d15e'),('8318ff1ebf0b4c4686a2767a13d07d6a','NO_STORAGE_ENCRYPTION','No','SELECT',2,NULL,'1e3655a562f143af8a5a805146a4d998'),('85cc9596be494dab8db354f7b38bb13b','VISUAL_STUDIO_SUPPORT','Visual Studio','SELECT',3,NULL,'76cc72b7d4b54c209e7a5a5f3deacc19'),('8d6b19a57d53412c956561ae9e59bbc7','SELF_SERVICE_UPGRADE','Self-Service','SELECT',1,NULL,'b2cf6ee6d7434babb9aeaec0025b6599'),('912fdc5fa00847c6afd214f6f79c0347','MASTER_SLAVE_REPLICATION','Master-Slave Replication','SELECT',2,NULL,'ef5ab46bd349408d85972f68ff560110'),('9150ee10a9a34c268f3b26d7ce7d4a07','CDN','CDN','SELECT',4,NULL,'93cd078658d946faa3cd24eb9808044c'),('93de8f9577e140c2991360ccf957aaf9','INTEGRITY','Yes','SELECT',1,NULL,'fa3663bf6c944e9db8bb2d50dfe67e4a'),('9671b5a329724ab593a383caca701ee5','PERFORMANCE_PREDICTABLE','Predictable, x milliseconds','INPUT',2,NULL,'2b0d0cb04c304147b0231b681844395d'),('96ea23a358e447938feb74467fb61f8b','COMMUNITY_CLOUD','Community Cloud','SELECT',3,NULL,'d1264caee2fd482589f1ee5c33481e36'),('9b54951ec1ed44839bbf0588c9bfe565','ORM_JDO','JDO','SELECT',2,NULL,'132db49a4cd747109b038bef98f2b811'),('9b9ba824c3d14495aa9a61aac59ec9f5','ACCESS_VIA_VPN','VPN','SELECT',1,NULL,'0be224e6c57e4d3ea947cfd0b8767c4c'),('9b9d81de22ea4b8dac42632c88336689','MONITORING_CPU','Processing Load (CPU)','SELECT',1,NULL,'eb93b4e3ff7d4c3097cdbb64a95e3def'),('9e8e656e554d48a896c0e469091dbcb9','MANUAL_SCALABILITY','Manual','SELECT',1,NULL,'c9d8bd1c1d7d4a38b1382fd39b43ad31'),('9f9b59a8666247fca71bf3960f2648d9','DISTRIBUTED','Yes (distributed infrastructure)','SELECT',1,NULL,'203b9d84c80142ffa194dd8bfbb356dc'),('a1ed96799af04ce19761675a5d0bf6a8','EXCHANGE_FORMAT_JSON','JSON','SELECT',2,NULL,'9893bfd6377e4a5ab5c1cba1f9bfe050'),('a27b90b03c1b4fde98363393cfaf3dbe','LIMITED_SCALABILITY','Limited','INPUT',2,'NOT_COMPARABLE','d6a0185c6cd44aeebc686a7ce29585dc'),('a32439175ecc43728f44be4101086747','STORAGE_ACCESS_VIA_SQL','SQL','SELECT',3,NULL,'05d7669ba496488ba7f0e6dbb6266be2'),('a39fe2d5a57b4ee59bc8a6a4ed0474f2','MONITORING_RAM','Memory Load (RAM)','SELECT',4,NULL,'eb93b4e3ff7d4c3097cdbb64a95e3def'),('a47083d1d52e496082c305c145dbbbcf','SCHEMA_NOT_CUSTOMIZABLE','No','SELECT',2,NULL,'16cd19f4ee004ef28779284ccc833668'),('a621e09ae0cb40579334c8487b6a8ee0','INCREMENTAL_BACKUP','Incremental Backup','SELECT',3,NULL,'0629f4ed0aee464db0118c38263ca293'),('a74444a41fe54cd2907a67f99a0c4478','SCHEMA_SUPPORT','Yes','SELECT',1,NULL,'d6466d6dddbf499dad2492353964385a'),('a8030df5d5604425858586fc27286b3d','ASYNCHRONOUS_REPLICATION','Asynchronous','SELECT',3,NULL,'8efb3276cd8149bd8c5c0ef43460d15e'),('aa1f4559ca2b446ca17ac142c9612a45','NO_DATA_PORTABILITY','None','SELECT',1,NULL,'433fca4556b44380802c92a49bd84080'),('abbbc17a25834e3ebb4ede30494acfea','AVAILABLE_IN_CASE_OF_PARTITIONING','Available','SELECT',1,NULL,'25aa3a4a92504794831d316f1617d509'),('b103c831148a41c6bf87e45fd769d7c7','NO_SCHEMA_SUPPORT','No','SELECT',2,NULL,'d6466d6dddbf499dad2492353964385a'),('b3b4bbed35bf4b85b1569706478eaf3f','PUBLIC_CLOUD','Public Cloud','SELECT',2,NULL,'d1264caee2fd482589f1ee5c33481e36'),('b3db76b4a862410f97ec966f1d452198','HORIZONTAL_AND_VERTICAL_SCALABILITY','Vertical (scale up) and Horizontal (scale out)','SELECT',3,NULL,'f53a4ff1c0ad47ccaaf129a9d010ece3'),('b4aa43c4297d46d3874666f541de9e7c','SCHEMA_CUSTOMIZABLE','Yes','SELECT',1,NULL,'16cd19f4ee004ef28779284ccc833668'),('b6e450eb2a8e48fdabb8d3e7eaa72dd5','AUTOMATED_SCALABILITY','Automated','SELECT',2,NULL,'c9d8bd1c1d7d4a38b1382fd39b43ad31'),('b82597306d2b487e91fafae439b27184','TRANSACTION_SUPPORTED','ACID','SELECT',1,NULL,'9c14b41c82cc4f74bbbd54e49e675948'),('c28f70c5b6794f1b8dd3d0d8a1b4e8f0','TWO_WAY_SYNC','Two-Way-Synchronisation','SELECT',6,NULL,'433fca4556b44380802c92a49bd84080'),('c2fe8be8df264d4cbbbc562b2b904e00','CENTRALIZED','No (centralized infrastructure)','SELECT',2,NULL,'203b9d84c80142ffa194dd8bfbb356dc'),('c9e3a3954442477ba44c776dc624db29','SAAS','SaaS','SELECT',3,NULL,'1fa1ede903434557b0238d32a45d7d83'),('cc99d68d3d5c4ca5983f2156c8b54a3d','NUMBER_OF_BACKUPS_FOR_X_DAYS_KEPT','Backups for number of days','INPUT',3,'UPPER_IS_BETTER','31d1151cdf3a497e9fd4af3220926992'),('cd73de4b4f254c2ebe4be43c88892363','NO_MIGRATION_AND_DEPLOYMENT_SUPPORT','No','SELECT',2,NULL,'840c6b9e81b54c22b9b5ed9d59923fd8'),('d0f5c6c18ee64f73962f29c2ac70a659','ON_DEMAND_BACKUP','On Demand','SELECT',3,NULL,'fdca854074a64d99930eb1fc0d78bd80'),('d1eee5a8df9b4bd09e55e5a8a94a32c8','EXCHANGE_FORMAT_XML','XML','SELECT',1,NULL,'9893bfd6377e4a5ab5c1cba1f9bfe050'),('d301729fe611455da1d3ad3e9425d3af','PAAS','PaaS','SELECT',2,NULL,'1fa1ede903434557b0238d32a45d7d83'),('d414020320544badad6825eba8011cab','PERFORMANCE_NON_PREDICTABLE','Non-Predictable','SELECT',1,NULL,'2b0d0cb04c304147b0231b681844395d'),('d584925b823c4e36b9303ec1b7cde19d','PERIODIC_BACKUP','Periodic, every x minutes','INPUT',2,'LOWER_IS_BETTER','fdca854074a64d99930eb1fc0d78bd80'),('d595c54a28e442ecb26e8146534c7e91','NOSQL','NoSQL','SELECT',2,NULL,'93cd078658d946faa3cd24eb9808044c'),('d6f922852efa45fea55e7d647b84b667','MAX_DOMAIN_SIZE','GB','INPUT',1,'UPPER_IS_BETTER','15767383a8574a69806be60640e20d9a'),('d876bba677a94092891fcd7b060e04bd','THROUGHPUT_LIMIT','Bytes/Second','INPUT',1,NULL,'bf916be1d568479f8c6bd0ae0704cb61'),('d9536d7539ea46b085e2fb10702275a7','NO_LOCATION_CHOICE_BUT_SINGLE_LOCATION','No - Single Location','SELECT',2,NULL,'0d3da195c7ff495cb63071b10f5999ef'),('db8860a4d2024f7d8708d5a6e6ccc8f1','NETBEANS_SUPPORT','NetBeans','SELECT',2,NULL,'76cc72b7d4b54c209e7a5a5f3deacc19'),('dd337e996176427b8ec2825f925c3934','NO_AUTHENTICATION','No','SELECT',2,NULL,'831e07703f51453b905007f0b11213b3'),('de75983ce6224edaa7e0b20247f52068','STRONG_CONSISTENCY','Strong','SELECT',1,NULL,'4c3cbaff88be493f83f2bad49a4a0186'),('e3915e23017849339a3851f16beefdfc','AUTOMATIC_FAILOVER','Yes','SELECT',1,NULL,'dba6f34e727a4a2292a5518b5de3d8e9'),('e43a478d7bbd4b74b5f9d7890d9138c0','DATA_STORE_ENGINE_MYSQL','MySQL','SELECT',1,NULL,'5a249ab25af9472191a814de5b6e70cb'),('e597eac649464e98a1ddc8ed505a9dab','NO_FIREWALL','No','SELECT',2,NULL,'90e99c1e1a4d47b3bd45abc1b993219d'),('e98973c6a371463c9e31e01592d36f1d','NO_NEW_INSTANCE_LAUNCHABLE','None','SELECT',1,NULL,'4e6c61393c644fab950aab2427a78ed3'),('ea93b660ac824a279ffdbd7bdb95d9f4','BANDWIDTH_LIMIT','Bytes','INPUT',1,NULL,'753c9b7a32bd43d9ba176f0ea8438902'),('eb9db535cc6c44d8a6a565723bdf32a7','UNLIMITED_SCALABILITY','Virtually Unlimited','SELECT',1,NULL,'d6a0185c6cd44aeebc686a7ce29585dc'),('efa28ce9de504ec3840c60d6ee63f3c2','PYTHON_SDK','Python','SELECT',5,NULL,'8e0dbb23311840c68d033665ed57aec4'),('f03e6987ca9441b8b181dd672d992f4d','ORM_JPA','JPA','SELECT',1,NULL,'132db49a4cd747109b038bef98f2b811'),('f247569234f04515872050c51f9740cc','EVENTUAL_CONSISTENCY','Eventual Consistency','SELECT',3,NULL,'4c3cbaff88be493f83f2bad49a4a0186'),('f439f47621c34cb3a1a5d2e2cb5b0a71','REPLICATION_IN_DIFFERENT_REGION','Different Data Center in Different Region','SELECT',4,NULL,'58fc6100d2664d82b469668f333bbd62'),('f60ea4bed33b42a09cfdee851269c3e6','NO_CONFIDENTIALITY','No','SELECT',2,NULL,'d03cac7743ff44a598c4a0c5772f3ac8'),('f6aa974d76dd4c87994739ce145c9bda','EXPORT','Export','SELECT',3,NULL,'433fca4556b44380802c92a49bd84080'),('f7e002e63f5e4d49b2e23d3679c2c956','IMPORT_AND_EXPORT','Import and Export','SELECT',4,NULL,'433fca4556b44380802c92a49bd84080'),('f883683014eb4297b1260cb68744e82f','SCALE_ON_LATENCY','Latency','SELECT',3,NULL,'524dc181b2394434b16c02a93608290c'),('faaf8c2b0c65494f8cc4253dfbd35562','MASTER_MASTER_REPLICATION','Master-Master Replication','SELECT',3,NULL,'ef5ab46bd349408d85972f68ff560110'),('fb61cc17e16148a48c1adf01dc4ca14b','IAAS','IaaS','SELECT',1,NULL,'1fa1ede903434557b0238d32a45d7d83');
/*!40000 ALTER TABLE `CDHSCriterionPossibleValue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `User` (
  `id` char(32) NOT NULL,
  `username` varchar(45) NOT NULL,
  `passwordHash` varchar(256) DEFAULT NULL,
  `email` varchar(256) NOT NULL,
  `verified` tinyint(1) NOT NULL DEFAULT '0',
  `created` datetime NOT NULL,
  `sessionExpiryDate` datetime DEFAULT NULL,
  `sessionToken` char(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `User`
--

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;
INSERT INTO `User` VALUES ('ff2c28ae2f6147f88b4471d102129077','thobach','05f63f323ae6a9cdc81a375b4ead509c69361fb2d197c44364687fd60c5a8ca1','info@thobach.de',1,'2012-07-10 21:35:36',NULL,NULL);
/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CDMStrategy`
--

DROP TABLE IF EXISTS `CDMStrategy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CDMStrategy` (
  `id` char(32) NOT NULL,
  `Project_id` char(32) NOT NULL,
  `CDMCriterionPossibleValue_id` char(32) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_CDMCriterionActualValue_Project1` (`Project_id`),
  KEY `fk_CDMCriterionActualValue_CDMCriterionPossibleValue1` (`CDMCriterionPossibleValue_id`),
  CONSTRAINT `fk_CDMCriterionActualValue_Project1` FOREIGN KEY (`Project_id`) REFERENCES `project` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_CDMCriterionActualValue_CDMCriterionPossibleValue1` FOREIGN KEY (`CDMCriterionPossibleValue_id`) REFERENCES `cdmcriterionpossiblevalue` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CDMStrategy`
--

LOCK TABLES `CDMStrategy` WRITE;
/*!40000 ALTER TABLE `CDMStrategy` DISABLE KEYS */;
INSERT INTO `CDMStrategy` VALUES ('04781166582e49529fbbd4047935424f','2ec339c01d7e4bd0a4b7bc21192a29e0','15acb5d2f921483eaa6a25b810d31989'),('0e7d6020b3094aa49cea8906e3025822','2ec339c01d7e4bd0a4b7bc21192a29e0','767e87c5cd1d40edbccbdc80a01c0eea'),('2016d0085e144612942deef5dd450ea1','2ec339c01d7e4bd0a4b7bc21192a29e0','4c37de5ceb8441e48f38c7208dce235a'),('3c7741319d6041319a07eacbf5f5d111','2ec339c01d7e4bd0a4b7bc21192a29e0','76a3b263d24b4f9380f6adbf0b2537f1'),('3dd548eba61042e89a8115fe5dad75d4','2ec339c01d7e4bd0a4b7bc21192a29e0','a4156461fc454e86bb935b8a9cd1b336'),('53bc6f9b96f54462a1460567cef849eb','2ec339c01d7e4bd0a4b7bc21192a29e0','8da427ce2805420a956bb1a120c0356f'),('6fd65bf1fd6d45bc876b8f666bc02664','2ec339c01d7e4bd0a4b7bc21192a29e0','9a536e2867c34f4caf8e55194d26a71e'),('87fedbba5a6140d9b8166b3ebed83a49','2ec339c01d7e4bd0a4b7bc21192a29e0','a9b3a7d340a34e3182a38f4ac70d01d7'),('8859501ab727482ab549a38a111eb2f6','2ec339c01d7e4bd0a4b7bc21192a29e0','7cfc0a3904b14d5383f9054dfd0d38e0'),('8afe64aead7a4eba9a001896bd8a7c9e','2ec339c01d7e4bd0a4b7bc21192a29e0','cac2c6db609e4fba8eccec053ea06b8b'),('f50333ac27904c1aaacac0bbd3ed14b5','2ec339c01d7e4bd0a4b7bc21192a29e0','9fc489edbb7143b4a8e0e34ec28de696');
/*!40000 ALTER TABLE `CDMStrategy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `CDMScenario`
--

DROP TABLE IF EXISTS `CDMScenario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CDMScenario` (
  `id` char(32) NOT NULL,
  `name` varchar(256) NOT NULL,
  `description` text NOT NULL,
  `orderNumber` smallint(6) DEFAULT NULL,
  `created` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `CDMScenario`
--

LOCK TABLES `CDMScenario` WRITE;
/*!40000 ALTER TABLE `CDMScenario` DISABLE KEYS */;
INSERT INTO `CDMScenario` VALUES ('08e7fbf313614786a60dd3cc34987e33','Off-Loading of Peak Loads (Cloud Burst)','tbd.',5,'2012-07-10 21:39:36'),('245af91045854d2c8537d5e88626b700','Archive','tbd.',9,'2012-07-10 21:39:36'),('2c3481bc8ee64ef3b9bf8b570799b633','Backup','tbd.',8,'2012-07-10 21:39:36'),('38793cd441e2416ba6981e815979036a','Plain Outsourcing','Plain outsourcing of the DBL means migrating the local DBL to the Cloud without changing the type of the datastore (RDBMS, NoSQL or Blob Store).',1,'2012-07-10 21:39:36'),('8ea8f958c3a048858f79c16f1d0a1d43','Data Synchronization','tbd.',7,'2012-07-10 21:39:36'),('9001e75fd713447d8f92ed940d106c8b','Usage of highly-scalable data stores (NoSQL, Blob Store)','Migrate from a RDBMS which is not highly-scalable to a highly scalabe Cloud data store.',2,'2012-07-10 21:39:36'),('9e159e1c56604216a8c5bf21267b9f8e','Data Distribution (Sharding)','tbd.',4,'2012-07-10 21:39:36'),('a6e474fa91974f449aa2ddf90894be77','Daten Usage from the Cloud','tbd.',11,'2012-07-10 21:39:36'),('a6e82bb652b14f0389632b6d2114dec7','Work on Copy of Data (Data Analysis and Monitoring)','tbd.',6,'2012-07-10 21:39:36'),('c88cc1cdb6dd471d9d2cf0a8e6b9287f','Data Import from the Cloud','tbd.',10,'2012-07-10 21:39:36'),('f71f49480f27414081e370e723f9eab5','Geographic Replication','tbd.',3,'2012-07-10 21:39:36');
/*!40000 ALTER TABLE `CDMScenario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `LocalDBLCategory`
--

DROP TABLE IF EXISTS `LocalDBLCategory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `LocalDBLCategory` (
  `id` char(32) NOT NULL,
  `name` varchar(256) NOT NULL,
  `orderNumber` smallint(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `LocalDBLCategory`
--

LOCK TABLES `LocalDBLCategory` WRITE;
/*!40000 ALTER TABLE `LocalDBLCategory` DISABLE KEYS */;
INSERT INTO `LocalDBLCategory` VALUES ('8460e2f690b9458f96ba0c7832cc9847','RDBMS',2),('dfd070c33c8449f7b4da4b1b0d4e25f4','Characterization',1);
/*!40000 ALTER TABLE `LocalDBLCategory` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-07-25 19:40:07
