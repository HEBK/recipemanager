INSERT INTO `IngredientCategory` (name) VALUES
    ('Getreideprodukte'),
    ('Milchprodukte'),
    ('Fleisch, Fisch & Eier'),
    ('Öle, Fette & Nüsse'),
    ('Süßes & Salziges'),
    ('Gemüse & Früchte'),
    ('Getränke'),
    ('Sonstiges');

INSERT INTO `Ingredient` (category, name, isVegan, isVegetarian) VALUES
    (1, 'Mehl', true, true),
    (2, 'Milch', false, true),
    (5, 'Zucker', true, true),
    (7, 'Wasser', true, true);