set names utf8mb4;
set
foreign_key_checks = 0;

-- ----------------------------
-- table structure for t_dept
-- ----------------------------
drop table if exists `t_dept`;
create table `t_dept`
(
    `dept_id`     bigint(20) not null auto_increment comment '部门id',
    `parent_id`   bigint(20) not null comment '上级部门id',
    `dept_name`   varchar(100) character set utf8 collate utf8_general_ci not null comment '部门名称',
    `order_num`   bigint(20) null default null comment '排序',
    `create_time` datetime(0) null default null comment '创建时间',
    `modify_time` datetime(0) null default null comment '修改时间',
    primary key (`dept_id`) using btree,
    key           `t_dept_parent_id` (`parent_id`),
    key           `t_dept_dept_name` (`dept_name`)
) engine = innodb
  auto_increment = 11
  character set = utf8
  collate = utf8_general_ci comment = '部门表'
  row_format = dynamic;

-- ----------------------------
-- records of t_dept
-- ----------------------------
insert into `t_dept`
values (1, 0, '开发部', 1, '2019-06-14 20:56:41', null);
insert into `t_dept`
values (2, 1, '开发一部', 1, '2019-06-14 20:58:46', null);
insert into `t_dept`
values (3, 1, '开发二部', 2, '2019-06-14 20:58:56', null);
insert into `t_dept`
values (4, 0, '采购部', 2, '2019-06-14 20:59:56', null);
insert into `t_dept`
values (5, 0, '财务部', 3, '2019-06-14 21:00:08', null);
insert into `t_dept`
values (6, 0, '销售部', 4, '2019-06-14 21:00:15', null);
insert into `t_dept`
values (7, 0, '工程部', 5, '2019-06-14 21:00:42', null);
insert into `t_dept`
values (8, 0, '行政部', 6, '2019-06-14 21:00:49', null);
insert into `t_dept`
values (9, 0, '人力资源部', 8, '2019-06-14 21:01:14', '2019-06-14 21:01:34');
insert into `t_dept`
values (10, 0, '系统部', 7, '2019-06-14 21:01:31', null);

-- ----------------------------
-- table structure for t_log
-- ----------------------------
drop table if exists `t_log`;
create table `t_log`
(
    `id`          bigint(20) not null auto_increment comment '日志id',
    `username`    varchar(50) character set utf8 collate utf8_general_ci null default null comment '操作用户',
    `operation`   text character set utf8 collate utf8_general_ci null comment '操作内容',
    `time`        decimal(11, 0) null default null comment '耗时',
    `method`      text character set utf8 collate utf8_general_ci null comment '操作方法',
    `params`      text character set utf8 collate utf8_general_ci null comment '方法参数',
    `ip`          varchar(64) character set utf8 collate utf8_general_ci null default null comment '操作者ip',
    `create_time` datetime(0) null default null comment '创建时间',
    `location`    varchar(50) character set utf8 collate utf8_general_ci null default null comment '操作地点',
    primary key (`id`) using btree,
    key           `t_log_create_time` (`create_time`)
) engine = innodb auto_increment = 1011 character set = utf8 collate = utf8_general_ci comment = '操作日志表' row_format = dynamic;

-- ----------------------------
-- table structure for t_login_log
-- ----------------------------
drop table if exists `t_login_log`;
create table `t_login_log`
(
    `id`         bigint(11) not null auto_increment comment 'id',
    `username`   varchar(50) character set utf8 collate utf8_general_ci not null comment '用户名',
    `login_time` datetime(0) not null comment '登录时间',
    `location`   varchar(50) character set utf8 collate utf8_general_ci null default null comment '登录地点',
    `ip`         varchar(50) character set utf8 collate utf8_general_ci null default null comment 'ip地址',
    `system`     varchar(50) character set utf8 collate utf8_general_ci null default null comment '操作系统',
    `browser`    varchar(50) character set utf8 collate utf8_general_ci null default null comment '浏览器',
    primary key (`id`) using btree,
    key          `t_login_log_login_time` (`login_time`)
) engine = innodb
  auto_increment = 70
  character set = utf8
  collate = utf8_general_ci comment = '登录日志表'
  row_format = dynamic;

-- ----------------------------
-- table structure for t_menu
-- ----------------------------
drop table if exists `t_menu`;
create table `t_menu`
(
    `menu_id`     bigint(20) not null auto_increment comment '菜单/按钮id',
    `parent_id`   bigint(20) not null comment '上级菜单id',
    `menu_name`   varchar(50) not null comment '菜单/按钮名称',
    `url`         varchar(50) default null comment '菜单url',
    `perms`       text comment '权限标识',
    `icon`        varchar(50) default null comment '图标',
    `type`        char(2)     not null comment '类型 0菜单 1按钮',
    `order_num`   bigint(20) default null comment '排序',
    `create_time` datetime    not null comment '创建时间',
    `modify_time` datetime    default null comment '修改时间',
    primary key (`menu_id`) using btree,
    key           `t_menu_parent_id` (`parent_id`),
    key           `t_menu_menu_id` (`menu_id`)
) engine = innodb
  auto_increment = 179
  default charset = utf8
  row_format = dynamic comment ='菜单表';

-- ----------------------------
-- records of t_menu
-- ----------------------------
begin;
insert into `t_menu`
values (1, 0, '系统管理', null, null, 'layui-icon-setting', '0', 1, '2017-12-27 16:39:07', null);
insert into `t_menu`
values (2, 0, '系统监控', '', '', 'layui-icon-alert', '0', 2, '2017-12-27 16:45:51', '2019-06-13 11:20:40');
insert into `t_menu`
values (3, 1, '用户管理', '/system/user', 'user:view', '', '0', 1, '2017-12-27 16:47:13', '2019-12-04 16:46:50');
insert into `t_menu`
values (4, 1, '角色管理', '/system/role', 'role:view', '', '0', 2, '2017-12-27 16:48:09', '2019-06-13 08:57:19');
insert into `t_menu`
values (5, 1, '菜单管理', '/system/menu', 'menu:view', '', '0', 3, '2017-12-27 16:48:57', '2019-06-13 08:57:34');
insert into `t_menu`
values (6, 1, '部门管理', '/system/dept', 'dept:view', '', '0', 4, '2017-12-27 16:57:33', '2019-06-14 19:56:00');
insert into `t_menu`
values (8, 2, '在线用户', '/monitor/online', 'online:view', '', '0', 1, '2017-12-27 16:59:33', '2019-06-13 14:30:31');
insert into `t_menu`
values (10, 2, '系统日志', '/monitor/log', 'log:view', '', '0', 2, '2017-12-27 17:00:50', '2019-06-13 14:30:37');
insert into `t_menu`
values (11, 3, '新增用户', null, 'user:add', null, '1', null, '2017-12-27 17:02:58', null);
insert into `t_menu`
values (12, 3, '修改用户', null, 'user:update', null, '1', null, '2017-12-27 17:04:07', null);
insert into `t_menu`
values (13, 3, '删除用户', null, 'user:delete', null, '1', null, '2017-12-27 17:04:58', null);
insert into `t_menu`
values (14, 4, '新增角色', null, 'role:add', null, '1', null, '2017-12-27 17:06:38', null);
insert into `t_menu`
values (15, 4, '修改角色', null, 'role:update', null, '1', null, '2017-12-27 17:06:38', null);
insert into `t_menu`
values (16, 4, '删除角色', null, 'role:delete', null, '1', null, '2017-12-27 17:06:38', null);
insert into `t_menu`
values (17, 5, '新增菜单', null, 'menu:add', null, '1', null, '2017-12-27 17:08:02', null);
insert into `t_menu`
values (18, 5, '修改菜单', null, 'menu:update', null, '1', null, '2017-12-27 17:08:02', null);
insert into `t_menu`
values (19, 5, '删除菜单', null, 'menu:delete', null, '1', null, '2017-12-27 17:08:02', null);
insert into `t_menu`
values (20, 6, '新增部门', null, 'dept:add', null, '1', null, '2017-12-27 17:09:24', null);
insert into `t_menu`
values (21, 6, '修改部门', null, 'dept:update', null, '1', null, '2017-12-27 17:09:24', null);
insert into `t_menu`
values (22, 6, '删除部门', null, 'dept:delete', null, '1', null, '2017-12-27 17:09:24', null);
insert into `t_menu`
values (23, 8, '踢出用户', null, 'user:kickout', null, '1', null, '2017-12-27 17:11:13', null);
insert into `t_menu`
values (24, 10, '删除日志', null, 'log:delete', null, '1', null, '2017-12-27 17:11:45', '2019-06-06 05:56:40');
insert into `t_menu`
values (101, 0, '任务调度', null, null, 'layui-icon-time-circle', '0', 3, '2018-02-24 15:52:57', null);
insert into `t_menu`
values (102, 101, '定时任务', '/job/job', 'job:view', '', '0', 1, '2018-02-24 15:53:53', '2018-04-25 09:05:12');
insert into `t_menu`
values (103, 102, '新增任务', null, 'job:add', null, '1', null, '2018-02-24 15:55:10', null);
insert into `t_menu`
values (104, 102, '修改任务', null, 'job:update', null, '1', null, '2018-02-24 15:55:53', null);
insert into `t_menu`
values (105, 102, '删除任务', null, 'job:delete', null, '1', null, '2018-02-24 15:56:18', null);
insert into `t_menu`
values (106, 102, '暂停任务', null, 'job:pause', null, '1', null, '2018-02-24 15:57:08', null);
insert into `t_menu`
values (107, 102, '恢复任务', null, 'job:resume', null, '1', null, '2018-02-24 15:58:21', null);
insert into `t_menu`
values (108, 102, '立即执行任务', null, 'job:run', null, '1', null, '2018-02-24 15:59:45', null);
insert into `t_menu`
values (109, 101, '调度日志', '/job/log', 'job:log:view', '', '0', 2, '2018-02-24 16:00:45', '2019-06-09 02:48:19');
insert into `t_menu`
values (110, 109, '删除日志', null, 'job:log:delete', null, '1', null, '2018-02-24 16:01:21', null);
insert into `t_menu`
values (115, 0, '其他模块', null, null, 'layui-icon-gift', '0', 5, '2019-05-27 10:18:07', null);
insert into `t_menu`
values (116, 115, 'apex图表', '', '', null, '0', 2, '2019-05-27 10:21:35', null);
insert into `t_menu`
values (117, 116, '线性图表', '/others/apex/line', 'apex:line:view', null, '0', 1, '2019-05-27 10:24:49', null);
insert into `t_menu`
values (118, 115, '高德地图', '/others/map', 'map:view', '', '0', 3, '2019-05-27 17:13:12', '2019-06-12 15:33:00');
insert into `t_menu`
values (119, 116, '面积图表', '/others/apex/area', 'apex:area:view', null, '0', 2, '2019-05-27 18:49:22', null);
insert into `t_menu`
values (120, 116, '柱形图表', '/others/apex/column', 'apex:column:view', null, '0', 3, '2019-05-27 18:51:33', null);
insert into `t_menu`
values (121, 116, '雷达图表', '/others/apex/radar', 'apex:radar:view', null, '0', 4, '2019-05-27 18:56:05', null);
insert into `t_menu`
values (122, 116, '条形图表', '/others/apex/bar', 'apex:bar:view', null, '0', 5, '2019-05-27 18:57:02', null);
insert into `t_menu`
values (123, 116, '混合图表', '/others/apex/mix', 'apex:mix:view', '', '0', 6, '2019-05-27 18:58:04',
        '2019-06-06 02:55:23');
insert into `t_menu`
values (125, 115, '导入导出', '/others/eximport', 'others:eximport:view', '', '0', 4, '2019-05-27 19:01:57',
        '2019-06-13 01:20:14');
insert into `t_menu`
values (126, 132, '系统图标', '/others/febs/icon', 'febs:icons:view', '', '0', 4, '2019-05-27 19:03:18',
        '2019-06-06 03:05:26');
insert into `t_menu`
values (127, 2, '请求追踪', '/monitor/httptrace', 'httptrace:view', '', '0', 6, '2019-05-27 19:06:38',
        '2019-06-13 14:36:43');
insert into `t_menu`
values (128, 2, '系统信息', null, null, null, '0', 7, '2019-05-27 19:08:23', null);
insert into `t_menu`
values (129, 128, 'jvm信息', '/monitor/jvm', 'jvm:view', '', '0', 1, '2019-05-27 19:08:50', '2019-06-13 14:36:51');
insert into `t_menu`
values (131, 128, '服务器信息', '/monitor/server', 'server:view', '', '0', 3, '2019-05-27 19:10:07', '2019-06-13 14:37:04');
insert into `t_menu`
values (132, 115, 'febs组件', '', '', null, '0', 1, '2019-05-27 19:13:54', null);
insert into `t_menu`
values (133, 132, '表单组件', '/others/febs/form', 'febs:form:view', null, '0', 1, '2019-05-27 19:14:45', null);
insert into `t_menu`
values (134, 132, 'febs工具', '/others/febs/tools', 'febs:tools:view', '', '0', 3, '2019-05-29 10:11:22',
        '2019-06-12 13:21:27');
insert into `t_menu`
values (135, 132, '表单组合', '/others/febs/form/group', 'febs:formgroup:view', null, '0', 2, '2019-05-29 10:16:19', null);
insert into `t_menu`
values (136, 2, '登录日志', '/monitor/loginlog', 'loginlog:view', '', '0', 3, '2019-05-29 15:56:15', '2019-06-13 14:35:56');
insert into `t_menu`
values (137, 0, '代码生成', '', null, 'layui-icon-verticalright', '0', 4, '2019-06-03 15:35:58', null);
insert into `t_menu`
values (138, 137, '生成配置', '/generator/configure', 'generator:configure:view', null, '0', 1, '2019-06-03 15:38:36',
        null);
insert into `t_menu`
values (139, 137, '代码生成', '/generator/generator', 'generator:view', '', '0', 2, '2019-06-03 15:39:15',
        '2019-06-13 14:31:38');
insert into `t_menu`
values (159, 132, '其他组件', '/others/febs/others', 'others:febs:others', '', '0', 5, '2019-06-12 07:51:08',
        '2019-06-12 07:51:40');
insert into `t_menu`
values (160, 3, '密码重置', null, 'user:password:reset', null, '1', null, '2019-06-13 08:40:13', null);
insert into `t_menu`
values (161, 3, '导出excel', null, 'user:export', null, '1', null, '2019-06-13 08:40:34', null);
insert into `t_menu`
values (162, 4, '导出excel', null, 'role:export', null, '1', null, '2019-06-13 14:29:00', '2019-06-13 14:29:11');
insert into `t_menu`
values (163, 5, '导出excel', null, 'menu:export', null, '1', null, '2019-06-13 14:29:32', null);
insert into `t_menu`
values (164, 6, '导出excel', null, 'dept:export', null, '1', null, '2019-06-13 14:29:59', null);
insert into `t_menu`
values (165, 138, '修改配置', null, 'generator:configure:update', null, '1', null, '2019-06-13 14:32:09',
        '2019-06-13 14:32:20');
insert into `t_menu`
values (166, 139, '生成代码', null, 'generator:generate', null, '1', null, '2019-06-13 14:32:51', null);
insert into `t_menu`
values (167, 125, '模板下载', null, 'eximport:template', null, '1', null, '2019-06-13 14:33:37', null);
insert into `t_menu`
values (168, 125, '导出excel', null, 'eximport:export', null, '1', null, '2019-06-13 14:33:57', null);
insert into `t_menu`
values (169, 125, '导入excel', null, 'eximport:import', null, '1', null, '2019-06-13 14:34:19', null);
insert into `t_menu`
values (170, 10, '导出excel', null, 'log:export', null, '1', null, '2019-06-13 14:34:55', null);
insert into `t_menu`
values (171, 136, '删除日志', null, 'loginlog:delete', null, '1', null, '2019-06-13 14:35:27', '2019-06-13 14:36:08');
insert into `t_menu`
values (172, 136, '导出excel', null, 'loginlog:export', null, '1', null, '2019-06-13 14:36:26', null);
insert into `t_menu`
values (173, 102, '导出excel', null, 'job:export', null, '1', null, '2019-06-13 14:37:25', null);
insert into `t_menu`
values (174, 109, '导出excel', null, 'job:log:export', null, '1', null, '2019-06-13 14:37:46', '2019-06-13 14:38:02');
insert into `t_menu`
values (175, 2, 'swagger文档', '/monitor/swagger', 'swagger:view', '', '0', 8, '2019-08-18 17:25:36',
        '2019-08-18 17:25:59');
insert into `t_menu`
values (178, 115, '数据权限', '/others/datapermission', 'others:datapermission', '', '0', 5, '2020-04-29 09:34:25', null);
commit;

-- ----------------------------
-- table structure for t_role
-- ----------------------------
drop table if exists `t_role`;
create table `t_role`
(
    `role_id`     bigint(20) not null auto_increment comment '角色id',
    `role_name`   varchar(100) character set utf8 collate utf8_general_ci not null comment '角色名称',
    `remark`      varchar(100) character set utf8 collate utf8_general_ci null default null comment '角色描述',
    `create_time` datetime(0) not null comment '创建时间',
    `modify_time` datetime(0) null default null comment '修改时间',
    primary key (`role_id`) using btree
) engine = innodb
  auto_increment = 81
  character set = utf8
  collate = utf8_general_ci comment = '角色表'
  row_format = dynamic;

-- ----------------------------
-- records of t_role
-- ----------------------------
insert into `t_role`
values (1, '系统管理员', '系统管理员，拥有所有操作权限 ^_^', '2019-06-14 16:23:11', '2019-08-18 17:26:19');
insert into `t_role`
values (2, '注册账户', '注册账户，拥有查看，新增权限（新增用户除外）和导出excel权限', '2019-06-14 16:00:15', '2019-08-18 17:36:02');
insert into `t_role`
values (77, 'redis监控员', '负责redis模块', '2019-06-14 20:49:22', null);
insert into `t_role`
values (78, '系统监控员', '负责整个系统监控模块', '2019-06-14 20:50:07', null);
insert into `t_role`
values (79, '跑批人员', '负责任务调度跑批模块', '2019-06-14 20:51:02', null);
insert into `t_role`
values (80, '开发人员', '拥有代码生成模块的权限', '2019-06-14 20:51:26', null);

-- ----------------------------
-- table structure for t_role_menu
-- ----------------------------
drop table if exists `t_role_menu`;
create table `t_role_menu`
(
    `id`      bigint(20) not null auto_increment comment 'id',
    `role_id` bigint(20) not null comment '角色id',
    `menu_id` bigint(20) not null comment '菜单/按钮id',
    primary key (`id`) using btree,
    key       `t_role_menu_menu_id` (`menu_id`),
    key       `t_role_menu_role_id` (`role_id`)
) engine = innodb
  default charset = utf8
  row_format = dynamic comment ='角色菜单关联表';

-- ----------------------------
-- records of t_role_menu
-- ----------------------------
begin;
insert into `t_role_menu`(role_id, menu_id)
values (77, 2);
insert into `t_role_menu`(role_id, menu_id)
values (78, 2);
insert into `t_role_menu`(role_id, menu_id)
values (78, 8);
insert into `t_role_menu`(role_id, menu_id)
values (78, 23);
insert into `t_role_menu`(role_id, menu_id)
values (78, 10);
insert into `t_role_menu`(role_id, menu_id)
values (78, 24);
insert into `t_role_menu`(role_id, menu_id)
values (78, 170);
insert into `t_role_menu`(role_id, menu_id)
values (78, 136);
insert into `t_role_menu`(role_id, menu_id)
values (78, 171);
insert into `t_role_menu`(role_id, menu_id)
values (78, 172);
insert into `t_role_menu`(role_id, menu_id)
values (78, 127);
insert into `t_role_menu`(role_id, menu_id)
values (78, 128);
insert into `t_role_menu`(role_id, menu_id)
values (78, 129);
insert into `t_role_menu`(role_id, menu_id)
values (78, 131);
insert into `t_role_menu`(role_id, menu_id)
values (79, 101);
insert into `t_role_menu`(role_id, menu_id)
values (79, 102);
insert into `t_role_menu`(role_id, menu_id)
values (79, 103);
insert into `t_role_menu`(role_id, menu_id)
values (79, 104);
insert into `t_role_menu`(role_id, menu_id)
values (79, 105);
insert into `t_role_menu`(role_id, menu_id)
values (79, 106);
insert into `t_role_menu`(role_id, menu_id)
values (79, 107);
insert into `t_role_menu`(role_id, menu_id)
values (79, 108);
insert into `t_role_menu`(role_id, menu_id)
values (79, 173);
insert into `t_role_menu`(role_id, menu_id)
values (79, 109);
insert into `t_role_menu`(role_id, menu_id)
values (79, 110);
insert into `t_role_menu`(role_id, menu_id)
values (79, 174);
insert into `t_role_menu`(role_id, menu_id)
values (80, 137);
insert into `t_role_menu`(role_id, menu_id)
values (80, 138);
insert into `t_role_menu`(role_id, menu_id)
values (80, 165);
insert into `t_role_menu`(role_id, menu_id)
values (80, 139);
insert into `t_role_menu`(role_id, menu_id)
values (80, 166);
insert into `t_role_menu`(role_id, menu_id)
values (1, 1);
insert into `t_role_menu`(role_id, menu_id)
values (1, 3);
insert into `t_role_menu`(role_id, menu_id)
values (1, 11);
insert into `t_role_menu`(role_id, menu_id)
values (1, 12);
insert into `t_role_menu`(role_id, menu_id)
values (1, 13);
insert into `t_role_menu`(role_id, menu_id)
values (1, 160);
insert into `t_role_menu`(role_id, menu_id)
values (1, 161);
insert into `t_role_menu`(role_id, menu_id)
values (1, 4);
insert into `t_role_menu`(role_id, menu_id)
values (1, 14);
insert into `t_role_menu`(role_id, menu_id)
values (1, 15);
insert into `t_role_menu`(role_id, menu_id)
values (1, 16);
insert into `t_role_menu`(role_id, menu_id)
values (1, 162);
insert into `t_role_menu`(role_id, menu_id)
values (1, 5);
insert into `t_role_menu`(role_id, menu_id)
values (1, 17);
insert into `t_role_menu`(role_id, menu_id)
values (1, 18);
insert into `t_role_menu`(role_id, menu_id)
values (1, 19);
insert into `t_role_menu`(role_id, menu_id)
values (1, 163);
insert into `t_role_menu`(role_id, menu_id)
values (1, 6);
insert into `t_role_menu`(role_id, menu_id)
values (1, 20);
insert into `t_role_menu`(role_id, menu_id)
values (1, 21);
insert into `t_role_menu`(role_id, menu_id)
values (1, 22);
insert into `t_role_menu`(role_id, menu_id)
values (1, 164);
insert into `t_role_menu`(role_id, menu_id)
values (1, 2);
insert into `t_role_menu`(role_id, menu_id)
values (1, 8);
insert into `t_role_menu`(role_id, menu_id)
values (1, 23);
insert into `t_role_menu`(role_id, menu_id)
values (1, 10);
insert into `t_role_menu`(role_id, menu_id)
values (1, 24);
insert into `t_role_menu`(role_id, menu_id)
values (1, 170);
insert into `t_role_menu`(role_id, menu_id)
values (1, 136);
insert into `t_role_menu`(role_id, menu_id)
values (1, 171);
insert into `t_role_menu`(role_id, menu_id)
values (1, 172);
insert into `t_role_menu`(role_id, menu_id)
values (1, 127);
insert into `t_role_menu`(role_id, menu_id)
values (1, 128);
insert into `t_role_menu`(role_id, menu_id)
values (1, 129);
insert into `t_role_menu`(role_id, menu_id)
values (1, 131);
insert into `t_role_menu`(role_id, menu_id)
values (1, 175);
insert into `t_role_menu`(role_id, menu_id)
values (1, 101);
insert into `t_role_menu`(role_id, menu_id)
values (1, 102);
insert into `t_role_menu`(role_id, menu_id)
values (1, 103);
insert into `t_role_menu`(role_id, menu_id)
values (1, 104);
insert into `t_role_menu`(role_id, menu_id)
values (1, 105);
insert into `t_role_menu`(role_id, menu_id)
values (1, 106);
insert into `t_role_menu`(role_id, menu_id)
values (1, 107);
insert into `t_role_menu`(role_id, menu_id)
values (1, 108);
insert into `t_role_menu`(role_id, menu_id)
values (1, 173);
insert into `t_role_menu`(role_id, menu_id)
values (1, 109);
insert into `t_role_menu`(role_id, menu_id)
values (1, 110);
insert into `t_role_menu`(role_id, menu_id)
values (1, 174);
insert into `t_role_menu`(role_id, menu_id)
values (1, 137);
insert into `t_role_menu`(role_id, menu_id)
values (1, 138);
insert into `t_role_menu`(role_id, menu_id)
values (1, 165);
insert into `t_role_menu`(role_id, menu_id)
values (1, 139);
insert into `t_role_menu`(role_id, menu_id)
values (1, 166);
insert into `t_role_menu`(role_id, menu_id)
values (1, 115);
insert into `t_role_menu`(role_id, menu_id)
values (1, 132);
insert into `t_role_menu`(role_id, menu_id)
values (1, 133);
insert into `t_role_menu`(role_id, menu_id)
values (1, 135);
insert into `t_role_menu`(role_id, menu_id)
values (1, 134);
insert into `t_role_menu`(role_id, menu_id)
values (1, 126);
insert into `t_role_menu`(role_id, menu_id)
values (1, 159);
insert into `t_role_menu`(role_id, menu_id)
values (1, 116);
insert into `t_role_menu`(role_id, menu_id)
values (1, 117);
insert into `t_role_menu`(role_id, menu_id)
values (1, 119);
insert into `t_role_menu`(role_id, menu_id)
values (1, 120);
insert into `t_role_menu`(role_id, menu_id)
values (1, 121);
insert into `t_role_menu`(role_id, menu_id)
values (1, 122);
insert into `t_role_menu`(role_id, menu_id)
values (1, 123);
insert into `t_role_menu`(role_id, menu_id)
values (1, 118);
insert into `t_role_menu`(role_id, menu_id)
values (1, 125);
insert into `t_role_menu`(role_id, menu_id)
values (1, 167);
insert into `t_role_menu`(role_id, menu_id)
values (1, 168);
insert into `t_role_menu`(role_id, menu_id)
values (1, 169);
insert into `t_role_menu`(role_id, menu_id)
values (1, 178);
insert into `t_role_menu`(role_id, menu_id)
values (2, 1);
insert into `t_role_menu`(role_id, menu_id)
values (2, 3);
insert into `t_role_menu`(role_id, menu_id)
values (2, 161);
insert into `t_role_menu`(role_id, menu_id)
values (2, 4);
insert into `t_role_menu`(role_id, menu_id)
values (2, 14);
insert into `t_role_menu`(role_id, menu_id)
values (2, 162);
insert into `t_role_menu`(role_id, menu_id)
values (2, 5);
insert into `t_role_menu`(role_id, menu_id)
values (2, 17);
insert into `t_role_menu`(role_id, menu_id)
values (2, 163);
insert into `t_role_menu`(role_id, menu_id)
values (2, 6);
insert into `t_role_menu`(role_id, menu_id)
values (2, 20);
insert into `t_role_menu`(role_id, menu_id)
values (2, 164);
insert into `t_role_menu`(role_id, menu_id)
values (2, 2);
insert into `t_role_menu`(role_id, menu_id)
values (2, 8);
insert into `t_role_menu`(role_id, menu_id)
values (2, 10);
insert into `t_role_menu`(role_id, menu_id)
values (2, 170);
insert into `t_role_menu`(role_id, menu_id)
values (2, 136);
insert into `t_role_menu`(role_id, menu_id)
values (2, 172);
insert into `t_role_menu`(role_id, menu_id)
values (2, 127);
insert into `t_role_menu`(role_id, menu_id)
values (2, 128);
insert into `t_role_menu`(role_id, menu_id)
values (2, 129);
insert into `t_role_menu`(role_id, menu_id)
values (2, 131);
insert into `t_role_menu`(role_id, menu_id)
values (2, 175);
insert into `t_role_menu`(role_id, menu_id)
values (2, 101);
insert into `t_role_menu`(role_id, menu_id)
values (2, 102);
insert into `t_role_menu`(role_id, menu_id)
values (2, 173);
insert into `t_role_menu`(role_id, menu_id)
values (2, 109);
insert into `t_role_menu`(role_id, menu_id)
values (2, 174);
insert into `t_role_menu`(role_id, menu_id)
values (2, 137);
insert into `t_role_menu`(role_id, menu_id)
values (2, 138);
insert into `t_role_menu`(role_id, menu_id)
values (2, 139);
insert into `t_role_menu`(role_id, menu_id)
values (2, 115);
insert into `t_role_menu`(role_id, menu_id)
values (2, 132);
insert into `t_role_menu`(role_id, menu_id)
values (2, 133);
insert into `t_role_menu`(role_id, menu_id)
values (2, 135);
insert into `t_role_menu`(role_id, menu_id)
values (2, 134);
insert into `t_role_menu`(role_id, menu_id)
values (2, 126);
insert into `t_role_menu`(role_id, menu_id)
values (2, 159);
insert into `t_role_menu`(role_id, menu_id)
values (2, 116);
insert into `t_role_menu`(role_id, menu_id)
values (2, 117);
insert into `t_role_menu`(role_id, menu_id)
values (2, 119);
insert into `t_role_menu`(role_id, menu_id)
values (2, 120);
insert into `t_role_menu`(role_id, menu_id)
values (2, 121);
insert into `t_role_menu`(role_id, menu_id)
values (2, 122);
insert into `t_role_menu`(role_id, menu_id)
values (2, 123);
insert into `t_role_menu`(role_id, menu_id)
values (2, 118);
insert into `t_role_menu`(role_id, menu_id)
values (2, 125);
insert into `t_role_menu`(role_id, menu_id)
values (2, 167);
insert into `t_role_menu`(role_id, menu_id)
values (2, 168);
insert into `t_role_menu`(role_id, menu_id)
values (2, 169);
insert into `t_role_menu`(role_id, menu_id)
values (2, 178);
commit;
-- ----------------------------
-- table structure for t_user
-- ----------------------------
drop table if exists `t_user`;
create table `t_user`
(
    `user_id`         bigint(20) not null auto_increment comment '用户id',
    `username`        varchar(50) character set utf8 collate utf8_general_ci  not null comment '用户名',
    `password`        varchar(128) character set utf8 collate utf8_general_ci not null comment '密码',
    `dept_id`         bigint(20) null default null comment '部门id',
    `email`           varchar(128) character set utf8 collate utf8_general_ci null default null comment '邮箱',
    `mobile`          varchar(20) character set utf8 collate utf8_general_ci null default null comment '联系电话',
    `status`          char(1) character set utf8 collate utf8_general_ci      not null comment '状态 0锁定 1有效',
    `create_time`     datetime(0) not null comment '创建时间',
    `modify_time`     datetime(0) null default null comment '修改时间',
    `last_login_time` datetime(0) null default null comment '最近访问时间',
    `sex`             char(1) character set utf8 collate utf8_general_ci null default null comment '性别 0男 1女 2保密',
    `is_tab`          char(1) character set utf8 collate utf8_general_ci null default null comment '是否开启tab，0关闭 1开启',
    `theme`           varchar(10) character set utf8 collate utf8_general_ci null default null comment '主题',
    `avatar`          varchar(100) character set utf8 collate utf8_general_ci null default null comment '头像',
    `description`     varchar(100) character set utf8 collate utf8_general_ci null default null comment '描述',
    primary key (`user_id`) using btree,
    key               `t_user_username` (`username`),
    key               `t_user_mobile` (`mobile`)
) engine = innodb
  auto_increment = 8
  character set = utf8
  collate = utf8_general_ci comment = '用户表'
  row_format = dynamic;

-- ----------------------------
-- records of t_user
-- ----------------------------
insert into `t_user`
values (1, 'mrbird', 'cb62ad1497597283961545d608f80241', 1, 'mrbird@qq.com', '17788888888', '1', '2019-06-14 20:39:22',
        '2019-12-04 16:47:11', '2019-12-04 16:48:10', '0', '1', 'black', 'cnrhvkzwxjpwaacfpbdc.png', '我是帅比作者。');
insert into `t_user`
values (2, 'scott', '1d685729d113cfd03872f154939bee1c', 10, 'scott@gmail.com', '17722222222', '1',
        '2019-06-14 20:55:53', '2019-06-14 21:05:43', '2019-08-18 17:36:18', '0', '1', 'black',
        'gaongjwsryravauxxcmb.png', '我是scott。');
insert into `t_user`
values (3, 'reina', '1461afff857c02afbfb768aa3771503d', 4, 'reina@hotmail.com', '17711111111', '0',
        '2019-06-14 21:07:38', '2019-06-14 21:09:06', '2019-06-14 21:08:26', '1', '1', 'black',
        '5997fedcc7bd4cffbd350b40d1b5b987.jpg', '由于公款私用，已被封禁。');
insert into `t_user`
values (4, 'micaela', '9f2daa2c7bed6870fcbb5b9a51d6300e', 10, 'micaela@163.com', '17733333333', '1',
        '2019-06-14 21:10:13', '2019-06-14 21:11:26', '2019-06-14 21:10:37', '0', '0', 'white', '20180414165909.jpg',
        '我叫米克拉');
insert into `t_user`
values (5, 'jana', '176679b77b3c3e352bd3b30ddc81083e', 8, 'jana@126.com', '17744444444', '1', '2019-06-14 21:12:16',
        '2019-06-14 21:12:52', '2019-06-14 21:12:32', '1', '1', 'white', '20180414165821.jpg', '大家好，我叫简娜');
insert into `t_user`
values (6, 'georgie', 'dffc683378cdaa015a0ee9554c532225', 3, 'georgie@qq.com', '17766666666', '0',
        '2019-06-14 21:15:09', '2019-06-14 21:16:25', '2019-06-14 21:16:11', '2', '0', 'black',
        'biazfanxmamnroxxvxka.png', '生产执行rm -rf *，账号封禁t t');
insert into `t_user`
values (7, 'margot', '31379841b9f4bfde22b8b40471e9a6ce', 9, 'margot@qq.com', '13444444444', '1', '2019-06-14 21:17:53',
        '2019-06-14 21:18:59', '2019-06-14 21:18:07', '1', '1', 'white', '20180414165834.jpg', '大家好我叫玛戈');

-- ----------------------------
-- table structure for t_user_role
-- ----------------------------
drop table if exists `t_user_role`;
create table `t_user_role`
(
    `id`      bigint(20) not null auto_increment comment 'id',
    `user_id` bigint(20) not null comment '用户id',
    `role_id` bigint(20) not null comment '角色id',
    key       `t_user_role_user_id` (`user_id`),
    key       `t_user_role_role_id` (`role_id`),
    primary key (`id`) using btree
) engine = innodb
  character set = utf8
  collate = utf8_general_ci comment = '用户角色关联表'
  row_format = dynamic;

-- ----------------------------
-- records of t_user_role
-- ----------------------------
insert into `t_user_role`(user_id, role_id)
values (1, 1);
insert into `t_user_role`(user_id, role_id)
values (2, 2);
insert into `t_user_role`(user_id, role_id)
values (3, 77);
insert into `t_user_role`(user_id, role_id)
values (4, 78);
insert into `t_user_role`(user_id, role_id)
values (5, 79);
insert into `t_user_role`(user_id, role_id)
values (6, 80);
insert into `t_user_role`(user_id, role_id)
values (7, 78);
insert into `t_user_role`(user_id, role_id)
values (7, 79);
insert into `t_user_role`(user_id, role_id)
values (7, 80);

-- ----------------------------
-- table structure for t_user_data_permission
-- ----------------------------
drop table if exists `t_user_data_permission`;
create table `t_user_data_permission`
(
    `user_id` bigint(20) not null,
    `dept_id` bigint(20) not null,
    primary key (`user_id`, `dept_id`)
) engine = innodb
  default charset = utf8 comment ='用户数据权限关联表';

-- ----------------------------
-- records of t_user_data_permission
-- ----------------------------
begin;
insert into `t_user_data_permission`
values (1, 1);
insert into `t_user_data_permission`
values (1, 2);
insert into `t_user_data_permission`
values (1, 3);
insert into `t_user_data_permission`
values (1, 4);
insert into `t_user_data_permission`
values (1, 5);
insert into `t_user_data_permission`
values (1, 6);
insert into `t_user_data_permission`
values (2, 1);
insert into `t_user_data_permission`
values (2, 2);
insert into `t_user_data_permission`
values (3, 4);
insert into `t_user_data_permission`
values (4, 5);
commit;

set
foreign_key_checks = 1;

