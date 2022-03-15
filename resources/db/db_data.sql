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
    (9, 'Oregano', true, true),
    (9, 'Basilikum', true, true),
    (3, 'Hähnchenbrustfilet', false, false),
    (2, 'Joghurt mild', false, true),
    (2, 'Quark', false, true),
    (2, 'Sojamilch', true, true),
    (6, 'Zwiebel(n)', true, true),
    (1, 'Brötchen', true, true),
    (3, 'Ei(er)', false, true),
    (3, 'Hackfleisch, gemischt', false, false),
    (5, 'Salz', true, true),
    (9, 'Senf', true, true),
    (9, 'Majoran, getrocknet', true, true),
    (9, 'Paprikapulver', true, true),
    (9, 'Petersilie, getrocknet', true, true),
    (9, 'Knoblauch', true, true),
    (5, 'Maggi', true, true),
    (4, 'Margarine', true, true),
    (4, 'Rapsöl', true, true);

INSERT INTO `IngredientUnits` (unit, name) VALUES
    ('ml', 'Milliliter'),
    ('cl', 'Zentiliter'),
    ('l', 'Liter'),
    ('g', 'Gramm'),
    ('kg', 'Kilogramm'),
    ('Becher', 'Becher'),
    ('TL', 'Teelöffel'),
    ('EL', 'Esslöffel'),
    ('Pkg.', 'Packung/en'),
    ('n. B.', 'nach Belieben'),
    ('Stk.', 'Stück');

INSERT INTO `RecipeCategory` (name) VALUES
    ('Pasta'),
    ('Kuchen'),
    ('Torten'),
    ('Eis'),
    ('Soßen'),
    ('Eintopf'),
    ('Suppen'),
    ('Salat'),
    ('Getränke'),
    ('Traditionell');