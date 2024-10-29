-- work_times_logs
create table holiday_work_times_logs
(
    id                 bigserial               not null primary key,
    time_id            bigint                  not null,
    calculation_log    text                    not null,
    created_date       timestamp default now() not null,
    created_by         varchar(50)             not null,
    last_modified_date timestamp,
    last_modified_by   varchar(50),

    foreign key (time_id) references holiday_work_times (id)
);