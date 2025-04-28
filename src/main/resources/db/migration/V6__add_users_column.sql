 -- db/migration/V5__add_users_column_table.sql
ALTER TABLE public.rm_users
  ADD COLUMN IF NOT EXISTS created_by BIGINT NOT NULL,
  ADD COLUMN IF NOT EXISTS updated_by BIGINT NOT NULL;