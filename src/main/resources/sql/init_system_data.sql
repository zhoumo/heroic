INSERT INTO sys_role (id, role_key, role_name) VALUES (1, 'ROLE_ADMIN', '管理员');
INSERT INTO sys_role (id, role_key, role_name) VALUES (2, 'ROLE_USER', '系统用户');

INSERT INTO sys_resource (id, resource_key, resource_name, parent_id, resource_type, resource_url) VALUES (1, 'access', '权限管理', 0, 'MENU', '');
INSERT INTO sys_resource (id, resource_key, resource_name, parent_id, resource_type, resource_url) VALUES (2, 'role', '角色管理', 1, 'MENU', '/role/index.do');
INSERT INTO sys_resource (id, resource_key, resource_name, parent_id, resource_type, resource_url) VALUES (3, 'user', '用户管理', 1, 'MENU', '/user/index.do');
INSERT INTO sys_resource (id, resource_key, resource_name, parent_id, resource_type, resource_url) VALUES (4, 'resource', '资源管理', 1, 'MENU', '/resource/index.do');
INSERT INTO sys_resource (id, resource_key, resource_name, parent_id, resource_type, resource_url) VALUES (5, 'getMenus', '获得菜单', 0, 'REQUEST', '/resource/getMenus.do');

INSERT INTO sys_resource_role (resource_id, role_id) VALUES (1, 1);
INSERT INTO sys_resource_role (resource_id, role_id) VALUES (2, 1);
INSERT INTO sys_resource_role (resource_id, role_id) VALUES (3, 1);
INSERT INTO sys_resource_role (resource_id, role_id) VALUES (4, 1);