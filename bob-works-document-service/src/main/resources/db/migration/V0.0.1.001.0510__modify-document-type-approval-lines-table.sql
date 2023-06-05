
-- alter table
alter table document_types_approval_lines add team_id bigint;
alter table document_types_approval_lines add constraint approval_lines_team_fk foreign key (team_id) references teams(id);


-- update data
update document_types_approval_lines set team_id = 1 where id = 1;
