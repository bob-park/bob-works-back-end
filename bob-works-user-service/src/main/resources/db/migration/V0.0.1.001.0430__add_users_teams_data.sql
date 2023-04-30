-- add teams
insert into teams (id, name, description, created_by) values (1, '디지털 미디어 사업부 (응용 S/W 개발팀)', '개발팀', 'admin');
insert into teams (id, name, description, created_by) values (2, '디지털 미디어 사업부 (인프라사업팀)', '인프라사업팀', 'admin');

-- add teams_users
insert into teams_users(team_id, user_id) values (1, 13);
insert into teams_users(team_id, user_id) values (1, 12);