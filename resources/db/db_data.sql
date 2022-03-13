INSERT INTO `IngredientCategory` (name) VALUES
    ('Getreideprodukte'),           -- 1
    ('Milchprodukte'),              -- 2
    ('Fleisch, Fisch & Eier'),      -- 3
    ('Öle, Fette & Nüsse'),         -- 4
    ('Süßes & Salziges'),           -- 5
    ('Gemüse & Früchte'),           -- 6
    ('Getränke'),                   -- 7
    ('Sonstiges'),                  -- 8
    ('Gewürze');                    -- 9

INSERT INTO `Ingredient` (category, name, isVegan, isVegetarian) VALUES
    (1, 'Mehl', true, true),
    (2, 'Milch', false, true),
    (5, 'Zucker', true, true),
    (9, 'Pfeffer', true, true),
    (7, 'Wasser', true, true),
    (3, 'T-Bone Steak', false, false),
    (3, 'Gemischtes Hackfleisch', false, false),
    (9, 'Oregano', true, true),
    (9, 'Basilikum', true, true),
    (3, 'Hähnchenbrustfilet', false, false),
    (2, 'Joghurt mild', false, true),
    (2, 'Quark', false, true),
    (2, 'Sojamilch', true, true),
    (2, 'Eier', false, true);