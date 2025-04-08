# Receptfix - Database Design Overview

This document outlines the core database structure for the Receptfix app. The design supports managing recipes, ingredients, and shopping lists with proper unit handling and conversion.

---

## Users (`users`)
Stores registered users.

| Column       | Description               |
|--------------|---------------------------|
| `id`         | Unique identifier (UUID)  |
| `email`      | Login email               |
| `password`   | Hashed password           |
| `created_at` | When the account was created |

- A user can own recipes, shopping lists, and create ingredients.

---

## Recipes (`recipes`)
Represents a recipe with a name and description.

| Column       | Description               |
|--------------|---------------------------|
| `id`         | Recipe ID (UUID)          |
| `name`       | Recipe name               |
| `description`| Optional description      |
| `created_at` | Timestamp                 |
| `user_id`    | FK → users.id             |

- A recipe has many ingredients (via `recipe_ingredients`).

---

## Ingredients (`ingredients`)
List of all available ingredients, both system-defined and user-defined.

| Column               | Description                            |
|----------------------|----------------------------------------|
| `id`                 | Unique ID                              |
| `name`               | Ingredient name                        |
| `shopping_unit_id`   | FK → units.id (used for shopping lists)|
| `created_by_user_id` | FK → users.id (nullable)               |
| `created_at`         | Timestamp                              |

- An ingredient can be used in many recipes.
- Each ingredient has **one fixed unit** for shopping lists.

---

## Units (`units`)
List of measurement units.

| Column  | Example       |
|---------|----------------|
| `name`  | gram, liter     |
| `symbol`| g, l, st        |

Used in both recipes and shopping lists.

---

## Recipe Ingredients (`recipe_ingredients`)
Links a recipe to an ingredient and defines how much of it is used and in which unit.

| Column           | Description                      |
|------------------|----------------------------------|
| `recipe_id`      | FK → recipes.id                  |
| `ingredient_id`  | FK → ingredients.id              |
| `amount`         | Decimal                          |
| `recipe_unit_id` | FK → units.id (unit used in recipe) |

- Allows same ingredient to be used in different units in different recipes.

---

## Unit Conversions (`unit_conversions`)
Stores how to convert between units for a specific ingredient.

| Column            | Description                             |
|-------------------|-----------------------------------------|
| `ingredient_id`   | FK → ingredients.id                     |
| `from_unit_id`    | FK → units.id                           |
| `to_unit_id`      | FK → units.id                           |
| `conversion_factor` | e.g. 1 dl flour = 60 g → factor = 60 |
| `source`          | "system" or "user"                      |

Used to convert recipe units to shopping list units.

---

## Shopping Lists (`shopping_lists`)
Lists created by users (e.g. "Week 14 shopping").

| Column       | Description               |
|--------------|---------------------------|
| `id`         | List ID (UUID)            |
| `name`       | List name                 |
| `created_at` | Timestamp                 |
| `user_id`    | FK → users.id             |

---

## Shopping List Recipes (`shopping_list_recipes`)
Links a shopping list to recipes it includes.

| Column             | Description          |
|--------------------|----------------------|
| `shopping_list_id` | FK → shopping_lists.id |
| `recipe_id`        | FK → recipes.id      |

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

recipes ---< recipe_ingredients >--- ingredients
ingredients ---< unit_conversions

shopping_lists ---< shopping_list_recipes >--- recipes
shopping_lists ---< shopping_list_ingredients >--- ingredients
```