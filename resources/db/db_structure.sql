CREATE TABLE RecipeCategory (
    `categoryID`        INTEGER PRIMARY KEY,
    `name`              VARCHAR(255) NULL DEFAULT NULL
);

CREATE TABLE Recipe (
    `recipeID`          INTEGER PRIMARY KEY,
    `name`              VARCHAR(255) NULL DEFAULT NULL,
    `time`              INT(4) NOT NULL DEFAULT 30,
    `difficulty`        INT(1) NOT NULL DEFAULT 0,
    `description`       TEXT NULL DEFAULT NULL,
    `defaultQuantity`   INT(2) NOT NULL DEFAULT 4,
    `taste`             INT NULL DEFAULT NULL,
    `category`          INT NOT NULL REFERENCES `RecipeCategory`(`categoryID`)
);

CREATE TABLE IngredientCategory (
    `categoryID`        INTEGER PRIMARY KEY,
    `name`              VARCHAR(255) NULL DEFAULT NULL
);

CREATE TABLE Ingredient (
    `ingredientID`      INTEGER PRIMARY KEY,
    `name`              VARCHAR(255) NULL DEFAULT NULL,
    `isVegan`           BOOLEAN NOT NULL DEFAULT FALSE,
    `isVegetarian`      BOOLEAN NOT NULL DEFAULT FALSE,
    `category`          INT NOT NULL REFERENCES `IngredientCategory`(`categoryID`)
);

CREATE TABLE RecipeIngredient (
    `relationID`        INTEGER PRIMARY KEY,
    `recipeID`          INTEGER NOT NULL REFERENCES `Recipe`(`recipeID`),
    `ingredientID`      INTEGER NOT NULL REFERENCES `Ingredient`(`ingredientID`),
    `quantity`          FLOAT NOT NULL,
    `unit`              VARCHAR(255) NOT NULL DEFAULT 'g'
);