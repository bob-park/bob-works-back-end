-- users_notification
create table users_notification_servers
(
    id                 varchar(41)             not null primary key,
    user_id            bigint                  not null,
    type               varchar(50)             not null,
    hook_url           varchar(1000),
    options            json,
    created_date       timestamp default now() not null,
    created_by         varchar(50)             not null,
    last_modified_date timestamp,
    last_modified_by   varchar(50),

    foreign key (user_id) references users (id)
);