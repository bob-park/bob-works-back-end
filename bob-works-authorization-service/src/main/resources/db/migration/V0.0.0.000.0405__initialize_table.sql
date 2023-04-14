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

/*
    insert data
*/
-- authorization_clients
insert into authorization_clients (id, client_id, client_secret, client_name, client_issue_at, required_authorization_consent, access_token_time_to_live) values (1, '82606268-9d8f-47a6-b2ea-5e4fb396516d', '{noop}SMHnl3kU494VujaZDOqp', 'oauth2-client-app1', '2023-04-07 11:06:06.061715', true, 1800);
insert into authorization_clients (id, client_id, client_secret, client_name, client_issue_at, required_authorization_consent, access_token_time_to_live) values (2, '69a59cc4-3954-4f3f-9efd-b7a293b4bc18', '{noop}a3nZ3vsmdWS0ewu52rKG', 'Bob Works 문서 서비스', '2023-04-11 16:44:19.806866', true, 1800);

-- authorization_clients_redirects
insert into authorization_clients_redirects(client_id, redirect_uri) values (1, 'https://oauth.pstmn.io/v1/callback');
insert into authorization_clients_redirects(client_id, redirect_uri) values (1, 'http://127.0.0.1:8080/');
insert into authorization_clients_redirects(client_id, redirect_uri) values (2, 'https://oauth.pstmn.io/v1/callback');
insert into authorization_clients_redirects(client_id, redirect_uri) values (2, 'http://127.0.0.1:8081/');

-- authorization_scopes
insert into authorization_scopes (id, scope, description) values (1, 'profile', '사용자 프로필에 접근합니다.');
insert into authorization_scopes (id, scope, description) values (2, 'document', '사용자가 작성한 문서에 접근합니다.');
insert into authorization_scopes (id, scope, description) values (3, 'email', '사용자의 email 에 접근합니다.');
insert into authorization_scopes (id, scope, description) values (4, 'phone', '사용자의 휴대전화 정보에 접근합니다.');

-- authorization_clients_scopes
insert into authorization_clients_scopes(client_id, scope_id) values (1, 1);
insert into authorization_clients_scopes(client_id, scope_id) values (1, 2);
insert into authorization_clients_scopes(client_id, scope_id) values (1, 3);
insert into authorization_clients_scopes(client_id, scope_id) values (1, 4);
insert into authorization_clients_scopes(client_id, scope_id) values (2, 1);
insert into authorization_clients_scopes(client_id, scope_id) values (2, 2);
insert into authorization_clients_scopes(client_id, scope_id) values (2, 3);
insert into authorization_clients_scopes(client_id, scope_id) values (2, 4);


-- users
insert into users(id, user_id, password, name, email, created_by) values (11, 'admin', '{bcrypt}$2a$12$t8WLpJ8FFm7eUYrQatHcCODdtaGKUWm2c6Fkftrg2q5VEQ.q1alCq', 'admin', 'admin@admin.com', 'admin');
insert into users(id, user_id, password, name, email, created_by) values (12, 'manager', '{bcrypt}$2a$12$t8WLpJ8FFm7eUYrQatHcCODdtaGKUWm2c6Fkftrg2q5VEQ.q1alCq', 'manager', 'manager@manager.com', 'admin');
insert into users(id, user_id, password, name, email, created_by) values (13, 'user', '{bcrypt}$2a$12$t8WLpJ8FFm7eUYrQatHcCODdtaGKUWm2c6Fkftrg2q5VEQ.q1alCq', 'user', 'user@user.com', 'admin');

-- roles
insert into roles (id, p_id, role_name, description) values (11, null, 'ROLE_ADMIN', '관리자');
insert into roles (id, p_id, role_name, description) values (12, 11, 'ROLE_MANAGER', '매니저');
insert into roles (id, p_id, role_name, description) values (13, 12, 'ROLE_USER', '사용자');

-- users_roles
insert into users_roles(user_id, role_id) values (11, 11);
insert into users_roles(user_id, role_id) values (12, 12);
insert into users_roles(user_id, role_id) values (13, 13);