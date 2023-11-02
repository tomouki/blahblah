CREATE TABLE IF NOT EXISTS user_info(
                         user_id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
                         user_name              VARCHAR(128) NOT NULL,
                         user_icon              VARCHAR(128),
                         email                  VARCHAR(128) NOT NULL,
                         create_time             TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS workspace_info (
    workspace_id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
    user_id                VARCHAR(60)  NULL,
    workspace_name         VARCHAR(128) NOT NULL,
    create_time             TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS workspace_user_map (
    workspace_id           VARCHAR(60)  NOT NULL,
    user_id                VARCHAR(60)  NOT NULL
);

CREATE TABLE IF NOT EXISTS channel_info (
    channel_id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
    channel_name           VARCHAR(128) NOT NULL,
    channel_type           VARCHAR(128) NOT NULL,
    workspace_id           VARCHAR(60)  NOT NULL,
    create_time             TIMESTAMP    NOT NULL
);

CREATE TABLE IF NOT EXISTS post_info (
    post_id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
    channel_id             VARCHAR(60) NOT NULL,
    user_id                VARCHAR(60) NOT NULL,
    submitted_time         TIMESTAMP    NOT NULL,
    title                  VARCHAR(128) NOT NULL,
    text                   VARCHAR(500) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat_info (
    chat_id                     VARCHAR(60)  DEFAULT RANDOM_UUID() PRIMARY KEY,
    channel_id             VARCHAR(60) NOT NULL,
    user_id                VARCHAR(60) NOT NULL,
    submitted_time         TIMESTAMP    NOT NULL,
    text                   VARCHAR(500) NOT NULL
);