/*
    create table
 */
-- documents
create table documents
(
    id                 bigserial                     not null primary key,
    type               varchar(20)                   not null,
    type_id            bigint                        not null,
    writer_id          bigint                        not null,
    status             varchar(20) default 'WAITING' not null,
    created_date       timestamp   default now()     not null,
    created_by         varchar(100)                  not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100),

    foreign key (type_id) references document_types (id),
    foreign key (writer_id) references users (id)
);

-- vacation_documents
create table vacation_documents
(
    id                 bigint       not null primary key,
    vacation_type      varchar(20)  not null,
    vacation_sub_type  varchar(20),
    vacation_date_from date         not null,
    vacation_date_to   date         not null,
    days_count         real         not null,
    reason             varchar(255) not null,

    foreign key (id) references documents (id)
);

-- documents_approvals
create table documents_approvals
(

    id                 bigserial                     not null primary key,
    document_id        bigint                        not null,
    line_id            bigint                        not null,
    status             varchar(20) default 'WAITING' not null,
    approved_date_time timestamp,
    reason             text,
    created_date       timestamp   default now()     not null,
    last_modified_date timestamp,

    foreign key (document_id) references documents (id),
    foreign key (line_id) references document_types_approval_lines (id)
);
