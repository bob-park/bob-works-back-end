-- create table

-- positions
create table positions
(
    id                 bigserial               not null primary key,
    name               varchar(100)            not null,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100)
);

-- users_positions
create table users_positions
(
    id          bigserial not null primary key,
    user_id     bigint    not null,
    position_id bigint    not null,

    foreign key (user_id) references users (id),
    foreign key (position_id) references positions (id)
);

-- document_types
create table document_types
(
    id                 bigserial               not null primary key,
    type               varchar(100)            not null,
    name               varchar(100)            not null,
    description        text,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100)
);

-- document_types_approve_lines
create table document_types_approve_lines
(
    id               bigserial not null primary key,
    p_id             bigint,
    document_type_id bigint    not null,
    position_id      bigint    not null,

    foreign key (p_id) references document_types_approve_lines (id),
    foreign key (document_type_id) references document_types (id),
    foreign key (position_id) references positions (id)
);