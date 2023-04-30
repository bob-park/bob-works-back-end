-- add teams
insert into teams (id, name, description, created_by) values (1, '응용 S/W 개발팀', '개발팀', 'admin');
insert into teams (id, name, description, created_by) values (2, '인프라사업팀', '인프라사업팀', 'admin');

-- add teams_users
insert into teams_users(team_id, user_id) values (1, 13);
insert into teams_users(team_id, user_id) values (1, 12);