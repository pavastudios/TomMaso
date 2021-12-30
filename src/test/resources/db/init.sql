CREATE TABLE IF NOT EXISTS `Utente`
(
    `id_utente`       INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `password`        VARCHAR(255) NOT NULL,
    `data_iscrizione` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `permessi`        INT          NOT NULL DEFAULT 8,
    `username`        VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS `Blog`
(
    `id_blog`      INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `proprietario` INT          NOT NULL,
    `nome`         VARCHAR(255) NOT NULL UNIQUE,
    `visite`       INT          NOT NULL DEFAULT 0,
    FOREIGN KEY (`proprietario`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `Commento`
(
    `id_commento` INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `mittente`    INT,
    `testo`       TEXT         NOT NULL,
    `url_pagina`  VARCHAR(255) NOT NULL,
    `data_invio`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`mittente`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS `Chat`
(
    `id_chat` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `utente1` INT NOT NULL,
    `utente2` INT NOT NULL,
    FOREIGN KEY (`utente1`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`utente2`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (`utente1`, `utente2`)
);

CREATE TABLE IF NOT EXISTS `Messaggio`
(
    `id_messaggio` INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `id_chat`      INT             NOT NULL,
    `mittente`     INT             NOT NULL,
    `testo`        TEXT            NOT NULL,
    `data_invio`   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`id_chat`) REFERENCES `Chat` (`id_chat`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`mittente`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS `Report`
(
    `id_report`     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `tipo`          TINYINT         NOT NULL,
    `url`           VARCHAR(255)    NOT NULL,
    `motivo`        VARCHAR(255)    NOT NULL,
    `reporter`      INT             NOT NULL,
    `data_report`   TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `status`        INT             NOT NULL DEFAULT 0,
    `target`        INT             ,
    FOREIGN KEY (`reporter`) REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (`target`)   REFERENCES `Utente` (`id_utente`) ON UPDATE CASCADE ON DELETE CASCADE,
    UNIQUE (`url`, `reporter`)
);
