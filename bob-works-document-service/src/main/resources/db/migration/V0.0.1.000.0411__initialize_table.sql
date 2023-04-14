/*
    create table
 */
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
create table document_types_approval_lines
(
    id               bigserial not null primary key,
    p_id             bigint,
    document_type_id bigint    not null,
    user_id      bigint    not null,

    foreign key (p_id) references document_types_approval_lines (id),
    foreign key (document_type_id) references document_types (id),
    foreign key (user_id) references users (id)
);

/*
    insert data
 */
-- document_types
insert into document_types (id, type, name, created_by) values (1, 'VACATION', '휴가계', 'admin');

-- document_types_approve_lines
insert into document_types_approval_lines (id, p_id, document_type_id, user_id) values (1, null, 1, 12);
insert into document_types_approval_lines (id, p_id, document_type_id, user_id) values (2, 1, 1, 11);
