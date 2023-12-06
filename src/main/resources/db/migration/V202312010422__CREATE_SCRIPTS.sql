CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `role` (
  `role_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `UK_8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user_role` (
  `role_id` int NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`),
  CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `privilege` (
  `privilege_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`privilege_id`),
  UNIQUE KEY `UK_h7iwbdg4ev8mgvmij76881tx8` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `roles_privileges` (
  `privilege_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`privilege_id`,`role_id`),
  KEY `FK9h2vewsqh8luhfq71xokh4who` (`role_id`),
  CONSTRAINT `FK5yjwxw2gvfyu76j3rgqwo685u` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`privilege_id`),
  CONSTRAINT `FK9h2vewsqh8luhfq71xokh4who` FOREIGN KEY (`role_id`) REFERENCES `role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `endpoint` (
  `endpoint_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `endpoint_uri` varchar(255) DEFAULT NULL,
  `method` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`endpoint_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `privileges_endpoints` (
  `endpoint_id` int NOT NULL,
  `privilege_id` int NOT NULL,
  PRIMARY KEY (`endpoint_id`,`privilege_id`),
  KEY `FK2klwrtart5fqwm3w4sihbmeba` (`privilege_id`),
  CONSTRAINT `FK2klwrtart5fqwm3w4sihbmeba` FOREIGN KEY (`privilege_id`) REFERENCES `privilege` (`privilege_id`),
  CONSTRAINT `FK8s9gy7xkg046v9l3a51w3qf6h` FOREIGN KEY (`endpoint_id`) REFERENCES `endpoint` (`endpoint_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `user` (`user_id`, `email`, `name`, `password`) VALUES (1, "admin@verinite.com", "admin", "$2a$12$TiQjVYIJqZvBtd3d06lVbOq3JrdmclWhaKb8lgR//TO5XzNbKk.se");

INSERT INTO `role` (`role_id`, `name`) VALUES (1, "ADMIN");
INSERT INTO `role` (`role_id`, `name`) VALUES (2, "USER");

INSERT INTO `user_role` (`role_id`, `user_id`) VALUES (1, 1);

INSERT INTO `privilege` (`privilege_id`, `name`) VALUES (1, "SIGNUP");
INSERT INTO `privilege` (`privilege_id`, `name`) VALUES (2, "SIGNIN");
INSERT INTO `privilege` (`privilege_id`, `name`) VALUES (3, "CONFIG");

INSERT INTO `roles_privileges` (`privilege_id`, `role_id`) VALUES (1, 1);
INSERT INTO `roles_privileges` (`privilege_id`, `role_id`) VALUES (2, 1);
INSERT INTO `roles_privileges` (`privilege_id`, `role_id`) VALUES (3, 1);

INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (1, "SIGN-UP", "/api/v1/auth/signup", "SIGN_UP", "POST");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (2, "SIGN-IN", "/api/v1/auth/signin", "SIGN_IN", "POST");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (3, "CONFIG", "/api/v1/config/**", "CONFIG", "POST");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (4, "CONFIG", "/api/v1/config/**", "CONFIG", "GET");
INSERT INTO `endpoint` (`endpoint_id`, `description`, `endpoint_uri`, `name`, `method`) VALUES (5, "CONFIG", "/api/v1/config/**", "CONFIG", "PATCH");

INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (1, 1);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (2, 2);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (3, 3);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (4, 3);
INSERT INTO `privileges_endpoints` (`endpoint_id`, `privilege_id`) VALUES (5, 3);




