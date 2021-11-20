-- Creazione tabelle
DROP TABLE IF EXISTS `Report`;
DROP TABLE IF EXISTS `Messaggio`;
DROP TABLE IF EXISTS `Chat`;
DROP TABLE IF EXISTS `Commento`;
DROP TABLE IF EXISTS `Blog`;
DROP TABLE IF EXISTS `PasswordReset`;
DROP TABLE IF EXISTS `Utente`;

CREATE TABLE `Utente`
(
    `id_utente`       INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `password`        VARCHAR(255) NOT NULL,
    `data_iscrizione` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `permessi`        INT          NOT NULL DEFAULT 0,
    `username`        VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE `Blog`
(
    `id_blog`      INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `proprietario` INT          NOT NULL,
    `nome`         VARCHAR(255) NOT NULL UNIQUE,
    `visite`       INT          NOT NULL DEFAULT 0,
    FOREIGN KEY (`proprietario`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `Commento`
(
    `id_commento` INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `mittente`    INT,
    `testo`       TEXT         NOT NULL,
    `url_pagina`  VARCHAR(255) NOT NULL,
    `data_invio`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`mittente`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE `Chat`
(
    `id_chat` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `utente1` INT NOT NULL,
    `utente2` INT NOT NULL,
    FOREIGN KEY (`utente1`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`utente2`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (`utente1`, `utente2`)
);

CREATE TABLE `Messaggio`
(
    `id_messaggio` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `id_chat`      INT             NOT NULL,
    `mittente`     INT             NOT NULL,
    `testo`        TEXT            NOT NULL,
    `data_invio`   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`id_chat`) REFERENCES `Chat` (`id_chat`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`mittente`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE `PasswordReset`
(
    `codice`    BINARY(32) PRIMARY KEY, -- UNHEX(SHA2(UUID(),256))
    `id_utente` INT       NOT NULL,
    `scadenza`  TIMESTAMP NOT NULL,     -- 1 ora
    FOREIGN KEY (`id_utente`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE `Report`
(
    `id_report`     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `tipo`          TINYINT         NOT NULL,
    `url`           VARCHAR(255)    NOT NULL,
    `motivo`        VARCHAR(255)    NOT NULL,
    `reporter`      INT             NOT NULL,
    `data_report`   TIMESTAMP       NOT NULL,
    `status`        INT             NOT NULL DEFAULT 0,
    `target`        INT             ,
    FOREIGN KEY (`reporter`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`target`)   REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (`url`, `reporter`)
);

SET GLOBAL time_zone = '+2:00'; -- Orario giusto, almeno per ora
