 -- db/migration/V4__update_ingredients_table.sql
ALTER TABLE public.rm_ingredients
  ALTER COLUMN quantity TYPE numeric USING quantity::numeric;