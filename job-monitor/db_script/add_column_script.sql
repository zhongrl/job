-- add table schedule_job
alter table schedule_job add column params varchar(264) default '{}'
comment '参数';