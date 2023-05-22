-- create table

-- user document signature
create table users_document_signatures
(
    id                 bigserial               not null primary key,
    user_id            bigint                  not null,
    signature          bytea                   not null,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100),

    foreign key (user_id) references users (id)
);