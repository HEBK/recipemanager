CREATE TABLE RecipeCategory (
    `categoryID`    INT(10) NOT NULL PRIMARY KEY,
    `name`          VARCHAR(255) NULL DEFAULT NULL COLLATE utf8_unicode_ci
);

CREATE TABLE Recipe (
    `recipeID`          INT(10) NOT NULL PRIMARY KEY,
    `name`              VARCHAR(255) NULL DEFAULT NULL COLLATE utf8_unicode_ci,
    `time`              INT(4) NOT NULL DEFAULT 30,
    `difficulty`        INT(1) NOT NULL DEFAULT 0,
    `description`       TEXT NULL DEFAULT NULL COLLATE utf8_unicode_ci,
    `defaultQuantity`   INT(2) NOT NULL DEFAULT 4,
    `taste`             INT NULL DEFAULT NULL
);

CREATE TABLE Ingredient (
    `ingredientID`      INT(10) NOT NULL PRIMARY KEY,
    `name`              VARCHAR(255) NULL DEFAULT NULL COLLATE utf8_unicode_ci,
    `isVegan`           BOOLEAN NOT NULL DEFAULT 0,
    `isVegetarian`      BOOLEAN NOT NULL DEFAULT 0
);

CREATE TABLE IngredientCategory (
    `categoryID`        INT(10) NOT NULL PRIMARY KEY,
    `name`              VARCHAR(255) NULL DEFAULT NULL COLLATE utf8_unicode_ci
);

CREATE TABLE RecipeIngredients (
    `relationID`        INT(10) NOT NULL PRIMARY KEY,
    `recipeID`          INT(10) NOT NULL,
    `ingredientID`      INT(10) NOT NULL,
    `quantity`          FLOAT(10000) NOT NULL,
    `unit`              VARCHAR(255) NOT NULL DEFAULT 'g'
);

ALTER TABLE RecipeIngredients ADD FOREIGN KEY (`recipeID`) REFERENCES `Recipe`(`recipeID`);
ALTER TABLE RecipeIngredients ADD FOREIGN KEY (`ingredientID`) REFERENCES `Ingredient`(`ingredientID`);