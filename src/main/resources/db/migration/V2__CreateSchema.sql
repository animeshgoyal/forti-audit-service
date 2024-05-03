--drop table  audit_info;
CREATE TABLE audit_info (
id varchar(100) NOT NULL,
source_obj_id varchar(50),
source_obj_name varchar(50),
user_id varchar(50),
action_type varchar(50),
time_stamp DATE,
service varchar(50),
instance_id varchar(50),
CONSTRAINT PK300 PRIMARY KEY (id));
