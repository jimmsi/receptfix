```plaintext
users
-----
- id (PK)
- email
- password
- created_at

|
| 1 --- N
↓

recipes
--------
- id (PK)
- name
- description
- created_at
- user_id (FK → users.id)

|
| 1 --- N
↓

recipe_ingredients
------------------
- recipe_id (FK → recipes.id)
- ingredient_id (FK → ingredients.id)
- amount
- recipe_unit_id (FK → units.id)
(PK = recipe_id + ingredient_id)

ingredients
-----------
- id (PK)
- name
- shopping_unit_id (FK → units.id)
- created_by_user_id (FK → users.id, nullable)
- created_at

|
| 1 --- N
↓

unit_conversions
----------------
- ingredient_id (FK → ingredients.id)
- from_unit_id (FK → units.id)
- to_unit_id (FK → units.id)
- conversion_factor
- source (e.g. 'system', 'user')
(PK = ingredient_id + from_unit_id + to_unit_id)

units
-----
- id (PK)
- name (e.g. gram, dl)
- symbol (e.g. g, dl)

users
|
| 1 --- N
↓

shopping_lists
--------------
- id (PK)
- name
- created_at
- user_id (FK → users.id)

|
| 1 --- N
↓

shopping_list_recipes
---------------------
- shopping_list_id (FK → shopping_lists.id)
- recipe_id (FK → recipes.id)
(PK = shopping_list_id + recipe_id)

|
| 1 --- N
↓

shopping_list_ingredients
-------------------------
- shopping_list_id (FK → shopping_lists.id)
- ingredient_id (FK → ingredients.id)
- amount
- unit_id (FK → units.id)
(PK = shopping_list_id + ingredient_id)
