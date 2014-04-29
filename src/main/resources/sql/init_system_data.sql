INSERT INTO `sys_role` VALUES (1, 'ROLE_ADMIN', '管理员');
INSERT INTO `sys_role` VALUES (2, 'ROLE_USER', '系统用户');

INSERT INTO `sys_resource` VALUES (1, 'access', '权限管理', 0, '');
INSERT INTO `sys_resource` VALUES (2, 'role', '角色管理', 1, '/role/index.do');
INSERT INTO `sys_resource` VALUES (3, 'user', '用户管理', 1, '/user/index.do');
INSERT INTO `sys_resource` VALUES (4, '/resource', '资源管理', 1, '/resource/index.do');

INSERT INTO `sys_resource_role` VALUES (1, 1);
INSERT INTO `sys_resource_role` VALUES (2, 1);
INSERT INTO `sys_resource_role` VALUES (3, 1);
INSERT INTO `sys_resource_role` VALUES (4, 1);