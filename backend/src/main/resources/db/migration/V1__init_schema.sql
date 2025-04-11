-- V1__init_schema.sql

-- Users table
CREATE TABLE users
(
    id       UUID PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL
);

-- Units table
CREATE TABLE units
(
    id      UUID PRIMARY KEY,
    name    VARCHAR(100) NOT NULL,
    symbol  VARCHAR(10)  NOT NULL,
    user_id UUID,
    CONSTRAINT fk_unit_user FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Ingredients table
CREATE TABLE ingredients
(
    id               UUID PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    shopping_unit_id UUID         NOT NULL,
    user_id          UUID,
    deleted          BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_ingredient_unit FOREIGN KEY (shopping_unit_id) REFERENCES units (id),
    CONSTRAINT fk_ingredient_user FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Recipes table
CREATE TABLE recipes
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    user_id     UUID         NOT NULL,
    deleted     BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_recipe_user FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Recipe_Ingredients table
CREATE TABLE recipe_ingredients
(
    recipe_id      UUID           NOT NULL,
    ingredient_id  UUID           NOT NULL,
    amount         DECIMAL(10, 2) NOT NULL,
    recipe_unit_id UUID           NOT NULL,
    PRIMARY KEY (recipe_id, ingredient_id),
    CONSTRAINT fk_ri_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE,
    CONSTRAINT fk_ri_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients (id),
    CONSTRAINT fk_ri_unit FOREIGN KEY (recipe_unit_id) REFERENCES units (id)
);

-- Unit Conversions table
CREATE TABLE unit_conversions
(
    ingredient_id     UUID           NOT NULL,
    from_unit_id      UUID           NOT NULL,
    to_unit_id        UUID           NOT NULL,
    conversion_factor DECIMAL(10, 4) NOT NULL,
    user_id           UUID,
    PRIMARY KEY (ingredient_id, from_unit_id, to_unit_id),
    CONSTRAINT fk_uc_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients (id),
    CONSTRAINT fk_uc_from_unit FOREIGN KEY (from_unit_id) REFERENCES units (id),
    CONSTRAINT fk_uc_to_unit FOREIGN KEY (to_unit_id) REFERENCES units (id),
    CONSTRAINT fk_uc_user FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Shopping Lists table
CREATE TABLE shopping_lists
(
    id      UUID PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    user_id UUID         NOT NULL,
    deleted BOOLEAN      NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_shopping_user FOREIGN KEY (user_id) REFERENCES users (id)
);

-- Shopping List Recipes table
CREATE TABLE shopping_list_recipes
(
    shopping_list_id UUID NOT NULL,
    recipe_id        UUID NOT NULL,
    PRIMARY KEY (shopping_list_id, recipe_id),
    CONSTRAINT fk_slr_list FOREIGN KEY (shopping_list_id) REFERENCES shopping_lists (id) ON DELETE CASCADE,
    CONSTRAINT fk_slr_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (id)
);

-- Shopping List Ingredients table
CREATE TABLE shopping_list_ingredients
(
    shopping_list_id UUID           NOT NULL,
    ingredient_id    UUID           NOT NULL,
    amount           DECIMAL(10, 2) NOT NULL,
    unit_id          UUID           NOT NULL,
    PRIMARY KEY (shopping_list_id, ingredient_id),
    CONSTRAINT fk_sli_list FOREIGN KEY (shopping_list_id) REFERENCES shopping_lists (id) ON DELETE CASCADE,
    CONSTRAINT fk_sli_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients (id),
    CONSTRAINT fk_sli_unit FOREIGN KEY (unit_id) REFERENCES units (id)
);