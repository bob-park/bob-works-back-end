/*
    create table
 */
-- notices
create table notices
(
    id                 bigserial               not null primary key,
    title              varchar(500)            not null,
    description        text,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100)
);
