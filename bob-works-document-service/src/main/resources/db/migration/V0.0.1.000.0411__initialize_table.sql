/*
    create table
 */
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
    user_id      bigint    not null,

    foreign key (p_id) references document_types_approve_lines (id),
    foreign key (document_type_id) references document_types (id),
    foreign key (user_id) references users (id)
);

/*
    insert data
 */
-- positions
insert into positions (id, name, created_by) values (11, '사원', 'admin');
insert into positions (id, name, created_by) values (12, '대리', 'admin');
insert into positions (id, name, created_by) values (13, '과장', 'admin');
insert into positions (id, name, created_by) values (14, '차장', 'admin');
insert into positions (id, name, created_by) values (15, '부장', 'admin');
insert into positions (id, name, created_by) values (16, '팀장', 'admin');
insert into positions (id, name, created_by) values (17, '이사', 'admin');

-- users_position
insert into users_positions(user_id, position_id) values (11, 17);
insert into users_positions(user_id, position_id) values (12, 16);
insert into users_positions(user_id, position_id) values (13, 11);
