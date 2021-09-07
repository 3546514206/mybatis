-- role
insert into role (id, rolename,alias) values(1,'ROLE_ADMIN','超级管理员');
insert into role (id, rolename,alias) values(2,'ROLE_MANAGER','普通管理员');
insert into role (id, rolename,alias) values(3,'ROLE_USER','普通用户');
insert into role (id, rolename,alias) values(4,'ROLE_ANONYMOUS','未登录用户');

-- user
insert into user(id, username,password) values(1,'zhenqing.wang','123');
insert into user(id, username,password) values(2,'admin','admin');
insert into user(id, username,password) values(3,'anonymous','ano');

--user_role
insert into user_role(id, user_id, role_id) values(1, 1, 1);
insert into user_role(id, user_id, role_id) values(2, 2, 2);
insert into user_role(id, user_id, role_id) values(3, 3, 3);
insert into user_role(id, user_id, role_id) values(4, 3, 4);