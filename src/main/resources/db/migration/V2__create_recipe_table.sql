 -- db/migration/V1__create_recipe_table.sql
 CREATE TABLE IF NOT EXISTS rm_recipes (
  recipe_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL,
  ingredients VARCHAR(255) NOT NULL,
  instructions VARCHAR(255) NOT NULL,
  created_date timestamp,
  updated_date timestamp
 );
