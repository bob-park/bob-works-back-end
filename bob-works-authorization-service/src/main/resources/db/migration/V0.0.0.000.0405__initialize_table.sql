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
create table authorization_clients_sessions
(
    id                          varchar(100) not null primary key,
    client_id                   bigint       not null,
    principal_name              varchar(100) not null,
    authorization_grant_type    varchar(100) not null,
    authorized_scopes           varchar(500) not null,
    attributes                  bytea,
    state                       varchar(100),
    authorization_code_value    text,
    authorization_code_issued   timestamp default now(),
    authorization_code_expire   timestamp,
    authorization_code_metadata bytea,
    access_token_value          text,
    access_token_issued_at      timestamp default now(),
    access_token_expires_at     timestamp,
    access_token_metadata       bytea,
    access_token_type           varchar(100),
    access_token_scopes         varchar(1000),
    oidc_id_token_value         text,
    oidc_id_token_issued_at     timestamp default now(),
    oidc_id_token_expires_at    timestamp,
    oidc_id_token_metadata      bytea,
    refresh_token_value         text,
    refresh_token_issued_at     timestamp default now(),
    refresh_token_expires_at    timestamp,
    refresh_token_metadata      bytea,

    foreign key (client_id) references authorization_clients (id)
);

-- authorization_consents
create table authorization_consents
(
    id             bigserial    not null primary key,
    client_id      bigint       not null,
    principal_name varchar(100) not null,
    authorities    text         not null,

    foreign key (client_id) references authorization_clients (id)
);

-- teams
create table teams
(
    id                 bigserial               not null primary key,
    name               varchar(100)            not null,
    description        text,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100)
);

-- users
create table users
(
    id                 bigserial               not null primary key,
    user_id            varchar(100)            not null,
    password           varchar(200),
    name               varchar(100)            not null,
    email              varchar(100)            not null,
    phone              varchar(100),
    description        text,
    created_date       timestamp default now() not null,
    created_by         varchar(100)            not null,
    last_modified_date timestamp,
    last_modified_by   varchar(100)
);

-- teams_users
create table teams_users
(
    id      bigserial not null primary key,
    team_id bigint    not null,
    user_id bigint    not null,

    foreign key (team_id) references teams (id),
    foreign key (user_id) references users (id)
);

-- roles
create table roles
(
    id                 bigserial               not null primary key,
    p_id               bigint,
    role_name          varchar(100)            not null,
    description        text,
    created_date       timestamp default now() not null,
    last_modified_date timestamp,

    foreign key (p_id) references roles (id)
);

-- users_roles
create table users_roles
(
    id      bigserial not null primary key,
    user_id bigint    not null,
    role_id bigint    not null,


    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);
