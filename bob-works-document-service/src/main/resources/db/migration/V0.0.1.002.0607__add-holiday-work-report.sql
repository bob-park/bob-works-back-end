/**
  create table
 */

-- holiday_work_reports
create table holiday_work_reports
(
    id           bigint       not null primary key,
    work_purpose varchar(500) not null,

    foreign key (id) references documents (id)
);

-- holiday_work_users
create table holiday_work_users
(
    id                 bigserial               not null primary key,
    holiday_work_id    bigint                  not null,
    is_manual_input    bool      default false not null,
    work_user_id       bigint,
    work_user_name     varchar(100)            not null,
    work_date          date                    not null,
    total_work_time    real      default 0     not null,
    is_vacation        bool      default false not null,
    payment_time       real      default 0     not null,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100),

    foreign key (holiday_work_id) references holiday_work_reports (id),
    foreign key (work_user_id) references users (id)
);

-- holiday_work_times
create table holiday_work_times
(
    id                 bigserial               not null primary key,
    work_users_id      bigint                  not null,
    start_time         time                    not null,
    end_time           time                    not null,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100),

    foreign key (work_users_id) references holiday_work_users (id)
);
