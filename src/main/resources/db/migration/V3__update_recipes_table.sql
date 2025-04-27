 -- db/migration/V3__update_recipes_table.sql
 ALTER TABLE public.rm_recipes 
  ALTER COLUMN status TYPE Char(1),
  ALTER COLUMN status SET NOT NULL,
  ALTER COLUMN instructions TYPE text,
  ALTER COLUMN difficulty TYPE VARCHAR(20),
  ALTER COLUMN difficulty  SET NOT NULL,
  ADD COLUMN inactive_recipe_reason VARCHAR(255);
