/**
  create table
 */
create table maintenance_customer_chat_rooms
(
    id                 varchar(41)             not null primary key,
    customer_id        bigint                  not null,
    manager_id         bigint,
    title              varchar(100)            not null,
    description        text,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100),

    foreign key (customer_id) references users (id),
    foreign key (manager_id) references users (id)
);

create table maintenance_customer_chats
(
    id                 varchar(41)             not null primary key,
    room_id            varchar(41)             not null,
    writer_id          bigint                  not null,
    contents           text                    not null,
    is_read            bool      default false not null,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100),

    foreign key (writer_id) references users (id)
);