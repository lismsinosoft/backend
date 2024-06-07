INSERT INTO `sys_user` (`username`, `name`, `password`,
                        `salt`, `user_type`, `email`, `phone`, `status`,
                        `create_time`, `update_time`, `create_by`, `update_by`, `is_deleted`)
VALUES ('admin', 'admin',
        '953c2fb5556ef6ee9cd7b4bccdb30569dce38bf88b3797923997bb6d763dbb59', '1618975235838', 0,
        NULL, NULL, 1, now(), now(), 'wzl', 'wzl', false);



INSERT INTO gfk.permission (code, name, parent_id, create_time, update_time, create_by, update_by, is_deleted)
VALUES ('dashboard', 'dashboard', 0, now(), now(), 'admin', 'admin', false),
       ('simulator', 'simulator', 0, now(), now(), 'admin', 'admin', false),
       ('optimizer', 'optimizer', 0, now(), now(), 'admin', 'admin', false);

INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (1, '项目1', '项目1', '项目1En', '2023-03-11 20:54:58.369', '2023-03-11 20:54:58.369', 'admin', 'admin', true);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (2, '项目1', '项目1', '项目1En', '2023-03-11 20:55:01.733', '2023-03-11 20:55:01.733', 'admin', 'admin', true);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (3, '项目1', '项目1', '项目1En', '2023-03-11 20:56:12.430', '2023-03-11 20:56:12.430', 'admin', 'admin', true);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (4, 'project1', '2年数据项目', '2-Year-Project', '2023-03-11 20:56:30.322', '2023-07-18 20:59:26.094', 'admin',
        'admin', false);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (5, '1', '2', '3', '2023-06-13 16:42:31.791', '2023-06-13 16:42:31.791', 'admin', 'admin', true);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (6, '12', '2', '3', '2023-06-13 16:46:09.586', '2023-06-13 16:46:09.586', 'admin', 'admin', true);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (7, '3', '3', '3', '2023-06-13 16:46:52.416', '2023-06-13 16:46:52.416', 'admin', 'admin', true);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (8, 'ceshi', '4', '4', '2023-06-13 16:47:30.923', '2023-06-13 17:11:43.951', 'admin', 'admin', false);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (9, '5', '5', '5', '2023-06-13 16:47:57.257', '2023-06-13 16:47:57.257', 'admin', 'admin', true);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (10, '464q1', '44', '64', '2023-06-13 16:49:51.270', '2023-06-13 17:11:14.308', 'admin', 'admin', false);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (11, '测试三的23', 'dsadsa', '的撒打算', '2023-06-13 17:15:38.233', '2023-06-13 17:16:35.907', 'admin', 'admin',
        false);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (12, 'test', 'test', 'test', '2023-06-13 17:16:31.130', '2023-06-13 17:16:31.130', 'admin', 'admin', false);
INSERT INTO gfk.project (id, code, name, name_en, create_time, update_time, create_by, update_by, is_deleted)
VALUES (13, 'project2', '3年数据项目', '3-Year-Project', '2023-07-18 21:00:40.078', '2023-07-18 21:00:40.078', 'admin',
        'admin', false);

INSERT INTO gfk.user (id, account, name, name_en, password, salt, email, phone, status, lock_flag, unlock_time,
                      create_time, update_time, create_by, update_by, is_deleted)
VALUES (1, 'aaaaa', 'sa', 'saw', '5b7336f08b30666853ae9ee922572818087e16deb7b90711ab8d870e780d106b', '1677420823690',
        'dasd', '1111', null, false, null, '2023-02-26 22:13:43.693', '2023-02-28 21:52:45.720', 'admin', 'admin',
        false);
INSERT INTO gfk.user (id, account, name, name_en, password, salt, email, phone, status, lock_flag, unlock_time,
                      create_time, update_time, create_by, update_by, is_deleted)
VALUES (2, 'aaa2aa', 'wzl', 'wzl', 'edffbf3a4ef25cf3d794a7c677c864cde372b4dccb2dd56a8470f251f1a73d92', '1677592319425',
        'wzl@email.com', '18888888888', null, false, null, '2023-02-28 21:51:59.431', '2023-02-28 21:51:59.436',
        'admin', 'admin', false);
INSERT INTO gfk.user (id, account, name, name_en, password, salt, email, phone, status, lock_flag, unlock_time,
                      create_time, update_time, create_by, update_by, is_deleted)
VALUES (3, 'test', 'test1', 'test1', '8a530d40b64c86d66e50b6ecc01886e349601c1f2bb7f69e3811ffe629164b1a',
        '1686648158426', 'test1', 'test1', null, false, null, '2023-06-13 17:22:38.426', '2023-06-13 17:24:27.800',
        'admin', 'admin', false);
INSERT INTO gfk.user (id, account, name, name_en, password, salt, email, phone, status, lock_flag, unlock_time,
                      create_time, update_time, create_by, update_by, is_deleted)
VALUES (4, '3', '333', '33', 'ea377c66ceea403199fb022c933fc345a0c02941e24e527a2fbebd35378b4ede', '1686648409623', '33',
        '333', null, false, null, '2023-06-13 17:26:49.623', '2023-06-13 17:26:49.623', 'admin', 'admin', false);

INSERT INTO gfk.user_permission_mapping (id, user_id, permission_id, sort, create_time, update_time, create_by,
                                         update_by, is_deleted)
VALUES (1, 2, 1, 0, '2023-02-28 23:36:59.778', '2023-02-28 23:36:59.778', 'admin', 'admin', false);

INSERT INTO gfk.user_project_mapping (id, user_id, project_id, create_time, update_time, create_by, update_by,
                                      is_deleted)
VALUES (1, 2, 4, '2023-03-11 21:14:08.932', '2023-03-11 21:14:08.932', 'admin', 'admin', true);
INSERT INTO gfk.user_project_mapping (id, user_id, project_id, create_time, update_time, create_by, update_by,
                                      is_deleted)
VALUES (2, 2, 13, null, null, null, null, false);
