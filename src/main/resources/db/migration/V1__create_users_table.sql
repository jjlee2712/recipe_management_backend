 -- db/migration/V1__create_users_table.sql
 CREATE TABLE IF NOT EXISTS rm_users (
  user_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  username VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  roles VARCHAR(255) NOT NULL,
  created_date timestamp,
  updated_date timestamp
 );
