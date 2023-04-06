-- create table

-- authorization_scopes
create table authorization_scopes
(
    id                 bigserial               not null primary key,
    scope              varchar(100)            not null,
    description        varchar(2000),
    created_date       timestamp default now() not null,
    last_modified_date timestamp
);


-- authorization_clients
create table authorization_clients
(
    id                             bigserial               not null primary key,
    client_id                      varchar(100)            not null,
    client_secret                  varchar(1000)           not null,
    client_name                    varchar(100)            not null,
    client_issue_at                timestamp               not null,
    client_secret_expires_at       timestamp,
    required_authorization_consent bool      default false not null,
    access_token_time_to_live      bigint                  not null,
    created_date                   timestamp default now() not null,
    last_modified_date             timestamp
);

-- authorization_client_redirect_url
create table authorization_clients_redirects
(
    id                 serial                  not null primary key,
    client_id          bigint                  not null,
    redirect_uri       varchar(1000)           not null,
    created_date       timestamp default now() not null,
    last_modified_date timestamp,

    foreign key (client_id) references authorization_clients (id)
);

-- authorization_clients_scopes
create table authorization_clients_scopes
(
    id        bigserial not null primary key,
    client_id bigint    not null,
    scope_id  bigint    not null,

    foreign key (client_id) references authorization_clients (id),
    foreign key (scope_id) references authorization_scopes (id)
);

