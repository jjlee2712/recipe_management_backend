 -- db/migration/V5__add_ratings_column_table.sql
ALTER TABLE public.rm_ratings
  ADD COLUMN remarks TEXT;