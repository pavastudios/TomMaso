DROP TABLE IF EXISTS `RememberMe`;
DROP TABLE IF EXISTS `Messaggio`;
DROP TABLE IF EXISTS `Chat`;
DROP TABLE IF EXISTS `Commento`;
DROP TABLE IF EXISTS `Pagina`;
DROP TABLE IF EXISTS `Blog`;
DROP TABLE IF EXISTS `Utente`;

CREATE TABLE `Utente`(
    `id_utente` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `email` VARCHAR(255) NOT NULL,
    `password` BINARY(64) NOT NULL,
    `salt` VARCHAR(20) NOT NULL,
    `data_iscrizione` TIMESTAMP NOT NULL,
    `propic_url` VARCHAR(255),
    `is_admin` BOOLEAN,
    `username` VARCHAR(255) UNIQUE
);

CREATE TABLE `Blog`(
    `id_blog` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `proprietario` INT NOT NULL,
    `nome` VARCHAR(255) UNIQUE,
    FOREIGN KEY (`proprietario`) REFERENCES `Utente`(`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `Pagina`(
    `id_pagina` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `blog` INT NOT NULL,
    `url` VARCHAR(255),
    FOREIGN KEY (`blog`) REFERENCES `Blog`(`id_blog`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `Commento`(
    `id_commento` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `mittente` INT,
    `testo` TEXT NOT NULL,
    `pagina` INT NOT NULL,
    FOREIGN KEY (`mittente`) REFERENCES `Utente`(`id_utente`) ON UPDATE CASCADE ON DELETE SET NULL,
    FOREIGN KEY (`pagina`) REFERENCES `Pagina`(`id_pagina`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `Chat`(
    `id_chat` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `utente1` INT NOT NULL,
    `utente2` INT NOT NULL,
    FOREIGN KEY (`utente1`) REFERENCES `Utente`(`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`utente2`) REFERENCES `Utente`(`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `Messaggio`(
    `id_messaggio` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `id_chat` INT NOT NULL,
    `mittente` INT NOT NULL,
    `testo` TEXT NOT NULL,
    FOREIGN KEY (`id_chat`) REFERENCES `Chat`(`id_chat`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`mittente`) REFERENCES `Utente`(`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `RememberMe`(
    `cookie` BINARY(32), -- UNHEX(SHA2(UUID(),256))
    `id_utente` INT NOT NULL,
    `scadenza` TIMESTAMP, -- 1 anno
    FOREIGN KEY (`id_utente`) REFERENCES `Utente`(`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE
);

