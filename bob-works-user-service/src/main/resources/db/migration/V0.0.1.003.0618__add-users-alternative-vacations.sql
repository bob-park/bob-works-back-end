-- create table

-- users_alternative_vacations
create table users_alternative_vacations
(
    id                 bigserial               not null primary key,
    user_id            bigint                  not null,
    effective_date     date                    not null,
    effective_count    real      default 0     not null,
    use_count          real      default 0     not null,
    is_expired         bool      default false not null,
    effective_reason   varchar(1000)           not null,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100),

    foreign key (user_id) references users (id)
);

-- users_used_alternative_vacations
create table users_used_vacations
(
    id                      bigserial               not null primary key,
    type                    varchar(100)            not null,
    user_id                 bigint                  not null,
    alternative_vacation_id bigint,
    used_date               date                    not null,
    used_count              real                    not null,
    created_date            timestamp default now() not null,
    created_by              varchar(100)            not null,
    last_modified_date      timestamp,
    last_modified_by        varchar(100),

    foreign key (user_id) references users (id),
    foreign key (alternative_vacation_id) references users_alternative_vacations (id)
);