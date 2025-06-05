# Receptfix - Database Design Overview

This document outlines the core database structure for the Receptfix app. The design supports managing recipes, ingredients, and shopping lists with proper unit handling and conversion. Resources can either be system-defined (user_id = NULL) or user-owned (user_id = UUID).

---

## Users (`users`)
Stores registered users.

| Column      | Description               |
|-------------|---------------------------|
| `id`        | Unique identifier (UUID)  |
| `email`     | Login email               |
| `username`  | Username                  |
| `password`  | Hashed password           |
| `role`      | USER or ADMIN             |

- A user can own recipes, ingredients, units, shopping lists, and unit conversions.

---

## Recipes (`recipes`)
Represents a recipe with a name and description.

| Column       | Description               |
|--------------|---------------------------|
| `id`         | Recipe ID (UUID)          |
| `name`       | Recipe name               |
| `description`| Optional description      |
| `user_id`    | FK → users.id             |
| `deleted`    | Soft delete flag          |

- A recipe has many ingredients (via `recipe_ingredients`).
- Recipes can be system-defined (`user_id IS NULL`) or user-owned.

---

## Ingredients (`ingredients`)
List of all available ingredients, both system-defined and user-defined.

| Column             | Description                            |
|--------------------|----------------------------------------|
| `id`               | Unique ID                              |
| `name`             | Ingredient name                        |
| `shopping_unit_id` | FK → units.id (used for shopping lists)|
| `user_id`          | FK → users.id (nullable)               |
| `deleted`          | Soft delete flag                       |

- Each ingredient has one fixed unit for shopping lists.

---

## Units (`units`)
List of measurement units.

| Column   | Description                   |
|----------|-------------------------------|
| `id`     | Unique ID                     |
| `name`   | Unit name (e.g. gram)         |
| `symbol` | Symbol (e.g. g, dl, st)       |
| `user_id`| FK → users.id (nullable)     |

- Units can be system-wide or user-defined.

---

## Recipe Ingredients (`recipe_ingredients`)
Links a recipe to an ingredient and defines how much of it is used and in which unit.

| Column           | Description                      |
|------------------|----------------------------------|
| `recipe_id`      | FK → recipes.id                  |
| `ingredient_id`  | FK → ingredients.id              |
| `amount`         | Decimal                          |
| `recipe_unit_id` | FK → units.id (unit used in recipe) |

---

## Unit Conversions (`unit_conversions`)
Stores how to convert between units for a specific ingredient.

| Column            | Description                             |
|-------------------|-----------------------------------------|
| `ingredient_id`   | FK → ingredients.id                     |
| `from_unit_id`    | FK → units.id                           |
| `to_unit_id`      | FK → units.id                           |
| `conversion_factor` | e.g. 1 dl flour = 60 g → factor = 60 |
| `user_id`         | FK → users.id (nullable)               |

- Used to convert recipe units to shopping list units.
- Can be defined per user or globally (user_id IS NULL).

---

## Shopping Lists (`shopping_lists`)
Lists created by users (e.g. "Week 14 shopping").

| Column     | Description               |
|------------|---------------------------|
| `id`       | List ID (UUID)            |
| `name`     | List name                 |
| `user_id`  | FK → users.id             |
| `deleted`  | Soft delete flag          |

---

## Shopping List Recipes (`shopping_list_recipes`)
Links a shopping list to recipes it includes.

| Column             | Description               |
|--------------------|---------------------------|
| `shopping_list_id` | FK → shopping_lists.id    |
| `recipe_id`        | FK → recipes.id           |

---

## Shopping List Ingredients (`shopping_list_ingredients`)
Summed-up ingredients (in unified shopping units) for the list.

| Column             | Description                         |
|--------------------|-------------------------------------|
| `shopping_list_id` | FK → shopping_lists.id              |
| `ingredient_id`    | FK → ingredients.id                 |
| `amount`           | Total amount (converted if needed)  |
| `unit_id`          | FK → units.id (shopping unit)       |

---

## ER Summary (Text)

```
users ---< recipes
users ---< shopping_lists
users ---< ingredients
users ---< units
users ---< unit_conversions

recipes ---< recipe_ingredients >--- ingredients
ingredients ---< unit_conversions

shopping_lists ---< shopping_list_recipes >--- recipes
shopping_lists ---< shopping_list_ingredients >--- ingredients
```