CREATE TABLE RecipeCategory (
    `categoryID`        INTEGER PRIMARY KEY,
    `name`              VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE Recipe (
    `recipeID`          INTEGER PRIMARY KEY,
    `name`              VARCHAR(255) NULL DEFAULT NULL,
    `time`              INT(4) NOT NULL DEFAULT 30,
    `difficulty`        INT(1) NOT NULL DEFAULT 0,
    `description`       TEXT NULL DEFAULT NULL,
    `defaultQuantity`   INT(2) NOT NULL DEFAULT 4,
    `category`          INT NOT NULL REFERENCES `RecipeCategory`(`categoryID`)
);

CREATE TABLE IngredientCategory (
    `categoryID`        INTEGER PRIMARY KEY,
    `name`              VARCHAR(255) NULL DEFAULT NULL
);

CREATE TABLE Ingredient (
    `ingredientID`      INTEGER PRIMARY KEY,
    `name`              VARCHAR(255) UNIQUE NOT NULL,
    `isVegan`           BOOLEAN NOT NULL DEFAULT FALSE,
    `isVegetarian`      BOOLEAN NOT NULL DEFAULT FALSE,
    `category`          INT NOT NULL REFERENCES `IngredientCategory`(`categoryID`)
);

CREATE TABLE RecipeIngredient (
    `relationID`        INTEGER PRIMARY KEY,
    `recipeID`          INTEGER NOT NULL REFERENCES `Recipe`(`recipeID`) ON DELETE CASCADE,
    `ingredientID`      INTEGER NOT NULL REFERENCES `Ingredient`(`ingredientID`),
    `unitID`            INTEGER NOT NULL REFERENCES `IngredientUnits`(`unitID`),
    `quantity`          FLOAT NOT NULL

);

CREATE TABLE IngredientUnits (
    `unitID`            INTEGER PRIMARY KEY,
    `unit`              VARCHAR(50) UNIQUE NOT NULL,
    `name`              VARCHAR(50) UNIQUE NOT NULL
);