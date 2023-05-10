-- users_avatars
create table users_avatars
(
    id               bigserial    not null primary key,
    user_id          bigint       not null,
    avatar_path      varchar(1000),
    created_date     timestamp    not null default now(),
    created_by       varchar(100) not null,
    last_modify_date timestamp,
    last_modify_by   varchar(100),

    foreign key (user_id) references users (id)
);