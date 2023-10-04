USE TTMS;

ALTER TABLE _user
ADD agent_id INT NULL,
FOREIGN KEY (agent_id) REFERENCES _user(id);

ALTER TABLE _user
ALTER COLUMN password VARCHAR(255) NULL;

ALTER TABLE _user
DROP CONSTRAINT UQ___user__6E2DBEDED2C86917;

SELECT 
    constraint_name, 
    constraint_type 
FROM 
    information_schema.table_constraints
WHERE 
    table_name = '_user';

SELECT * FROM _user;