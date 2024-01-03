CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_role` (
  `role_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `privilege` (
  `privilege_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`privilege_id`),
  UNIQUE KEY `UK_h7iwbdg4ev8mgvmij76881tx8` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles_privileges` (
  `privilege_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`privilege_id`,`role_id`),
  KEY `FK9h2vewsqh8luhfq71xokh4who` (`role_id`),
  CONSTRAINT `FK5yjwxw2gvfyu76j3rgqwo685u` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`privilege_id`),
  CONSTRAINT `FK9h2vewsqh8luhfq71xokh4who` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `endpoint` (
  `endpoint_id` bigint NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `endpoint_uri` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`endpoint_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `privileges_endpoints` (
  `endpoint_id` bigint NOT NULL,
  `privilege_id` bigint NOT NULL,
  PRIMARY KEY (`endpoint_id`,`privilege_id`),
  KEY `FK2klwrtart5fqwm3w4sihbmeba` (`privilege_id`),
  CONSTRAINT `FK2klwrtart5fqwm3w4sihbmeba` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`privilege_id`),
  CONSTRAINT `FK8s9gy7xkg046v9l3a51w3qf6h` FOREIGN KEY (`endpoint_id`) REFERENCES `endpoint` (`endpoint_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `application` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `application_name` varchar(255) DEFAULT NULL,
  `application_number` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tenant` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_by` varchar(255) DEFAULT NULL,
  `created_on` datetime(6) DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_on` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `tenant_code` varchar(255) DEFAULT NULL,
  `tenant_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `application_tenant` (
  `application_id` bigint,
  `tenant_id` bigint,
  KEY `FKltkjer5kc0dtiyd7s0frq7l6n` (`application_id`),
  CONSTRAINT `FKltkjer5kc0dtiyd7s0frq7l6n` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `FKoy6kqgax9tp4795rw8jl3iv39` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `config` (
  `config_id` bigint NOT NULL AUTO_INCREMENT,
  `data` varchar(255) DEFAULT NULL,
  `key_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`config_id`),
  UNIQUE KEY `UK_8c6dl7eofcfmo2y3p4gd93o3t` (`key_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_tenant` (
  `user_id` bigint NOT NULL,
  `tenant_id` bigint NOT NULL,
  PRIMARY KEY (`user_id`,`tenant_id`),
  KEY `FK5d635q2p1gie2n1y9alk5kgbh` (`tenant_id`),
  CONSTRAINT `FK1e97sb7tts0u74wntv5f6keoy` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK5d635q2p1gie2n1y9alk5kgbh` FOREIGN KEY (`tenant_id`) REFERENCES `tenant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `user` (`user_id`, `email`, `name`, `password`) VALUES (1, "admin@verinite.com", "admin", "$2a$12$TiQjVYIJqZvBtd3d06lVbOq3JrdmclWhaKb8lgR//TO5XzNbKk.se");

INSERT INTO `role` (`role_id`, `name`) VALUES (1, "ADMIN");

INSERT INTO `user_role` (`role_id`, `user_id`) VALUES (1, 1);

INSERT INTO `privilege` (`privilege_id`, `name`) VALUES (1, "SIGNUP");
INSERT INTO `privilege` (`privilege_id`, `name`) VALUES (2, "SIGNIN");
INSERT INTO `privilege` (`privilege_id`, `name`) VALUES (3, "CONFIG");
INSERT INTO `privilege` (`privilege_id`, `name`) VALUES (4, "APPLICATION");

INSERT INTO `roles_privileges` (`privilege_id`, `role_id`) VALUES (1, 1);
INSERT INTO `roles_privileges` (`privilege_id`, `role_id`) VALUES (2, 1);
INSERT INTO `roles_privileges` (`privilege_id`, `role_id`) VALUES (3, 1);
INSERT INTO `roles_privileges` (`privilege_id`, `role_id`) VALUES (4, 1);

INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (1, "SIGNUP_POST", "/api/ums/v1/signup", "SIGNUP_POST", "POST");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (2, "SIGNIN_POST", "/api/ums/v1/signin", "SIGNIN_POST", "POST");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (3, "ALL_CONFIG_POST", "/api/ums/v1/config/**", "ALL_CONFIG_POST", "POST");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (4, "ALL_CONFIG_GET", "/api/ums/v1/config/**", "ALL_CONFIG_GET", "GET");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (5, "ALL_CONFIG_PATCH", "/api/ums/v1/config/**", "ALL_CONFIG_PATCH", "PATCH");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (6, "ALL_APPLICATION_POST", "/api/ums/v1/application/**", "ALL_APPLICATION_POST", "POST");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (7, "ALL_APPLICATION_GET", "/api/ums/v1/application/**", "ALL_APPLICATION_GET", "GET");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (8, "ALL_APPLICATION_PATCH", "/api/ums/v1/application/**", "ALL_APPLICATION_PATCH", "PATCH");

INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (1, 1);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (2, 2);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (3, 3);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (4, 3);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (5, 3);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (6, 4);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (7, 4);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (8, 4);



