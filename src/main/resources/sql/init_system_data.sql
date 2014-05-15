INSERT INTO `sys_role` VALUES (1, 'ROLE_ADMIN', '管理员');
INSERT INTO `sys_role` VALUES (2, 'ROLE_USER', '系统用户');

INSERT INTO `sys_resource` VALUES (1, 'access', '权限管理', 0, '', NULL, 'MENU');
INSERT INTO `sys_resource` VALUES (2, 'role', '角色管理', 1, '/role/index.do', NULL, 'MENU');
INSERT INTO `sys_resource` VALUES (3, 'user', '用户管理', 1, '/user/index.do', NULL, 'MENU');
INSERT INTO `sys_resource` VALUES (4, '/resource', '资源管理', 1, '/resource/index.do', NULL, 'MENU');
INSERT INTO `sys_resource` VALUES (5, '/resource', '获得菜单', 0, '/resource/getMenus.do', NULL, 'REQUEST');

INSERT INTO `sys_resource_role` VALUES (1, 1);
INSERT INTO `sys_resource_role` VALUES (2, 1);
INSERT INTO `sys_resource_role` VALUES (3, 1);
INSERT INTO `sys_resource_role` VALUES (4, 1);