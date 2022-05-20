CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE "user"
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    username   TEXT      NOT NULL,
    password   TEXT      NOT NULL,
    email      TEXT      NOT NULL,
    bio        TEXT,
    image      TEXT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    CONSTRAINT user_email_unique UNIQUE (email),
    CONSTRAINT user_username_unique UNIQUE (username)
);

CREATE TABLE follower
(
    user_id     UUID      NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    followee_id UUID      NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    inserted_at TIMESTAMP NOT NULL,
    CONSTRAINT user_followee_follower_unq UNIQUE (user_id, followee_id)
);

CREATE TABLE article
(
    id          UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    slug        TEXT      NOT NULL,
    title       TEXT      NOT NULL,
    description TEXT      NOT NULL,
    body        TEXT      NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP NOT NULL,
    author_id   UUID      NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    CONSTRAINT articles_slug_unique UNIQUE (slug)
);

CREATE TABLE tag
(
    id   UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name TEXT NOT NULL,
    CONSTRAINT tag_name_unique UNIQUE (name)
);

CREATE TABLE article_tag
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    article_id UUID NOT NULL REFERENCES article (id) ON DELETE CASCADE,
    tag_id     UUID NOT NULL REFERENCES tag (id) ON DELETE CASCADE,
    CONSTRAINT article_tag_unique UNIQUE (article_id, tag_id)
);

CREATE TABLE comment
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    body       TEXT      NOT NULL,
    article_id UUID      NOT NULL REFERENCES article (id) ON DELETE CASCADE,
    author_id  UUID      NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE favorite
(
    id         UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id    UUID NOT NULL REFERENCES "user" (id) ON DELETE CASCADE,
    article_id UUID NOT NULL REFERENCES article (id) ON DELETE CASCADE,
    CONSTRAINT article_user_unique UNIQUE (article_id, user_id)
)