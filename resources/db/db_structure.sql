CREATE TABLE RecipeCategory (
    `categoryID`    INT(10) NOT NULL PRIMARY KEY,
    `name`          VARCHAR(255) NULL DEFAULT NULL COLLATE utf8_unicode_ci
);

CREATE TABLE Recipe (
    `recipeID`      INT(10) NOT NULL PRIMARY KEY,
    `name`          VARCHAR(255) NULL DEFAULT NULL COLLATE utf8_unicode_ci,
    `time`          INT(4) NOT NULL DEFAULT 30,
    `difficulty`    INT(1) NOT NULL DEFAULT 0,
    `desciprtion`   VARCHAR
    `defaultQuantity`   INT(2) NOT NULL DEFAULT 4,


    `taste`         INT NULL DEFAULT NULL,

);