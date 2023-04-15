/*
    create table
 */
-- users_vacations
create table users_vacations
(
    id                      bigserial               not null primary key,
    user_id                 bigint                  not null,
    year                    int                     not null,
    general_total_count     real       default 0     not null,
    general_used_count      real       default 0     not null,
    alternative_total_count real       default 0     not null,
    alternative_used_count  real       default 0     not null,
    created_date            timestamp default now() not null,
    created_by              varchar(100)            not null,
    last_modified_date      timestamp,
    last_modified_by        varchar(100),

    foreign key (user_id) references users (id)
);

-- insert data
insert into users_vacations (id, user_id, year, general_total_count, general_used_count, alternative_total_count, alternative_used_count, created_by) values (1, 11, 2023, 15, 0, 0, 0, 'admin');
insert into users_vacations (id, user_id, year, general_total_count, general_used_count, alternative_total_count, alternative_used_count, created_by) values (2, 12, 2023, 15, 0, 0, 0, 'admin');
insert into users_vacations (id, user_id, year, general_total_count, general_used_count, alternative_total_count, alternative_used_count, created_by) values (3, 13, 2023, 15, 0, 0, 0, 'admin');
