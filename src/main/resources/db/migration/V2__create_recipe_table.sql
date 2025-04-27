 -- db/migration/V2__create_recipes_table.sql
 CREATE TABLE IF NOT EXISTS rm_recipes (
  recipe_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  instructions VARCHAR(255) NOT NULL,
  prepare_time_minutes integer NOT NULL,
  cook_time_minutes integer NOT NULL,
  servings integer,
  difficulty VARCHAR(255) NOT NULL,
  status VARCHAR (100),
  created_date timestamp,
  updated_date timestamp,
  created_by integer NOT NULL,
  updated_by integer NOT NULL,
  active_flag char(1) NOT NULL,
  CONSTRAINT "rm_recipes_created_by_fkey" FOREIGN KEY ("created_by") REFERENCES "public"."rm_users"("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "rm_recipes_updated_by_fkey" FOREIGN KEY ("updated_by") REFERENCES "public"."rm_users"("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

 -- db/migration/V2__create_category_table.sql
CREATE TABLE IF NOT EXISTS rm_category(
  category_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  category_name VARCHAR(255) NOT NULL,
  created_date timestamp,
  updated_date timestamp,
  created_by integer NOT NULL,
  updated_by integer NOT NULL,
  active_flag char(1) NOT NULL,
  CONSTRAINT "rm_category_created_by_fkey" FOREIGN KEY ("created_by") REFERENCES "public"."rm_users"("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "rm_category_updated_by_fkey" FOREIGN KEY ("updated_by") REFERENCES "public"."rm_users"("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

 -- db/migration/V2__create_recipes_category_table.sql
CREATE TABLE IF NOT EXISTS rm_recipes_category (
  recipe_category_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  recipe_id BIGINT NOT NULL,
  category_id BIGINT NOT NULL,
  created_date timestamp,
  updated_date timestamp,
  created_by integer NOT NULL,
  updated_by integer NOT NULL,
  active_flag char(1) NOT NULL,
  CONSTRAINT "rm_recipes_category_recipe_id_fkey" FOREIGN KEY ("recipe_id") REFERENCES "public"."rm_recipes"("recipe_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "rm_recipes_category_category_id_fkey" FOREIGN KEY ("category_id") REFERENCES "public"."rm_category"("category_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "rm_category_created_by_fkey" FOREIGN KEY ("created_by") REFERENCES "public"."rm_users"("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "rm_category_updated_by_fkey" FOREIGN KEY ("updated_by") REFERENCES "public"."rm_users"("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION
);





 -- db/migration/V2__create_ingredients_table.sql
 CREATE TABLE IF NOT EXISTS rm_ingredients(
  ingredient_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  ingredient_name  VARCHAR(255) NOT NULL,
  quantity integer NOT NULL,
  unit VARCHAR(255) NOT NULL,
  created_date timestamp,
  updated_date timestamp,
  created_by integer NOT NULL,
  updated_by integer NOT NULL,
  recipe_id BIGINT NOT NULL,
  active_flag char(1) NOT NULL,
  CONSTRAINT "rm_ingredients_created_by_fkey" FOREIGN KEY ("created_by") REFERENCES "public"."rm_users"("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "rm_ingredients_updated_by_fkey" FOREIGN KEY ("updated_by") REFERENCES "public"."rm_users"("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT "rm_ingredients_recipe_id_fkey" FOREIGN KEY ("recipe_id") REFERENCES "public"."rm_recipes"("recipe_id") ON UPDATE NO ACTION ON DELETE NO ACTION
 );


  -- db/migration/V2__create_ratings_table.sql
  CREATE TABLE IF NOT EXISTS rm_ratings(
    ratings_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    recipe_id BIGINT NOT NULL,
    rating integer NOT NULL,
    created_date timestamp,
    updated_date timestamp,
    created_by integer NOT NULL,
    updated_by integer NOT NULL,
    active_flag char(1) NOT NULL,
    CONSTRAINT "rm_ratings_created_by_fkey" FOREIGN KEY ("created_by") REFERENCES "public"."rm_users"("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "rm_ratings_updated_by_fkey" FOREIGN KEY ("updated_by") REFERENCES "public"."rm_users"("user_id") ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT "rm_ratings_recipe_id_fkey" FOREIGN KEY ("recipe_id") REFERENCES "public"."rm_recipes"("recipe_id") ON UPDATE NO ACTION ON DELETE NO ACTION
  );