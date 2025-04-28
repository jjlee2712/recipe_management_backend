 -- db/migration/V7__add_users_column_table.sql
CREATE TABLE rm_recipe_attachments (
    attachment_id SERIAL PRIMARY KEY,
    filename VARCHAR(255),
    content_type VARCHAR(255),
    data BYTEA,
    recipe_id BIGINT NOT NULL,
    created_date timestamp,
    updated_date timestamp,
    created_by integer NOT NULL,
    updated_by integer NOT NULL
);