CREATE EXTENSION IF NOT EXISTS "uuid-ossp" SCHEMA public;

CREATE TABLE IF NOT EXISTS processed_message
(
    id             uuid DEFAULT public.uuid_generate_v4(),
    source_message text,
    message        text,
    processed_at   bigint,
    user_id        int,
    random_id      int,
    group_id       int,
    source_id      varchar(20),
    CONSTRAINT processed_message_pk PRIMARY KEY (id)
);