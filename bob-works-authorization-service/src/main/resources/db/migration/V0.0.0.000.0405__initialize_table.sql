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


-- authorization_client_sessions
create table authorization_client_sessions
(
    id                          bigserial               not null primary key,
    client_id                   bigint                  not null,
    principal_name              varchar(100)            not null,
    authorization_grant_type    varchar(100)            not null,
    authorized_scopes           varchar(500)            not null,
    attributes                  json,
    state                       varchar(100),
    authorization_code_value    varchar(100)            not null,
    authorization_code_issued   timestamp default now() not null,
    authorization_code_expire   timestamp               not null,
    authorization_code_metadata varchar(500),
    access_token_value          varchar(1000)           not null,
    access_token_issued_at      timestamp default now() not null,
    access_token_expires_at     timestamp               not null,
    access_token_metadata       varchar(1000),
    access_token_type           varchar(100)            not null,
    access_token_scopes         varchar(1000)           not null,
    oidc_id_token_value         varchar(1000),
    oidc_id_token_issued_at     timestamp default now(),
    oidc_id_token_expires_at    timestamp,
    oidc_id_token_metadata      varchar(1000),
    refresh_token_value         varchar(1000),
    refresh_token_issued_at     timestamp default now(),
    refresh_token_expires_at    timestamp,
    refresh_token_metadata      varchar(1000),

    foreign key (client_id) references authorization_clients (id)
);