create table sys_user
(
    id          int unsigned not null
        primary key auto_increment,
    username    varchar(40)  not null comment '帐号',
    name        varchar(40)  null comment '名称',
    password    varchar(80)  null comment '密码',
    salt        varchar(80)  null comment '加密密码的盐',
    user_type   int          null comment '用户类型(0-系统用户)',
    email       varchar(40)  null comment '电子邮件',
    phone       varchar(40)  null comment '手机号',
    status      int          null comment '状态',
    create_time datetime(3)  null,
    update_time datetime(3)  null,
    create_by   varchar(40)  null,
    update_by   varchar(40)  null,
    is_deleted  bit          null,
    index IDX_sys_user_username (username)
)
    comment '管理员用户信息' charset = utf8mb4;

create table user
(
    id          int unsigned not null
        primary key auto_increment,
    account     varchar(40)  not null comment '账户',
    name        varchar(40)  null comment '名称',
    name_en     varchar(40)  null comment '英文名称',
    password    varchar(80)  not null comment '密码',
    salt        varchar(80)  null comment '加密密码的盐',
    email       varchar(40)  null comment '电子邮件',
    phone       varchar(40)  null comment '手机号',
    status      int          null comment '状态',
    lock_flag   bit          null comment '锁定状态',
    unlock_time datetime(3)  null,
    create_time datetime(3)  null,
    update_time datetime(3)  null,
    create_by   varchar(40)  null,
    update_by   varchar(40)  null,
    is_deleted  bit          null
)
    comment '用户信息' charset = utf8mb4;

create table permission
(
    id          int unsigned not null
        primary key auto_increment,
    code        varchar(40)  not null comment '权限code',
    name        varchar(40)  not null comment '名称',
    parent_id   int          not null comment '父节点ID',
    create_time datetime(3)  null,
    update_time datetime(3)  null,
    create_by   varchar(40)  null,
    update_by   varchar(40)  null,
    is_deleted  bit          null
)
    comment '页面权限' charset = utf8mb4;

create table user_permission_mapping
(
    id            int unsigned not null
        primary key auto_increment,
    user_id       int          not null comment '用户ID',
    permission_id int          not null comment '权限ID',
    sort          int          not null comment '排序字段',
    create_time   datetime(3)  null,
    update_time   datetime(3)  null,
    create_by     varchar(40)  null,
    update_by     varchar(40)  null,
    is_deleted    bit          null
)
    comment '用户页面权限' charset = utf8mb4;

create table project
(
    id          int unsigned  not null
        primary key auto_increment,
    code        varchar(40)   not null comment '项目code',
    name        varchar(255)  not null comment '项目名称',
    name_en     varchar(255)  not null comment '项目英文名称',
    pic_url     varchar(1000) null comment '项目图片地址',
    pic_url2    varchar(1000) null comment '项目图片地址2',
    create_time datetime(3)   null,
    update_time datetime(3)   null,
    create_by   varchar(40)   null,
    update_by   varchar(40)   null,
    is_deleted  bit           null
)
    comment '项目记录' charset = utf8mb4;

create table user_project_mapping
(
    id          int unsigned not null
        primary key auto_increment,
    user_id     int          not null comment '用户ID',
    project_id  int          not null comment '项目ID',
    create_time datetime(3)  null,
    update_time datetime(3)  null,
    create_by   varchar(40)  null,
    update_by   varchar(40)  null,
    is_deleted  bit          null
)
    comment '用户项目权限' charset = utf8mb4;

create table user_project_permission_mapping
(
    id          int unsigned not null
        primary key auto_increment,
    user_id     int          not null comment '用户ID',
    project_id  int          not null comment '项目ID',
    permission  varchar(200) not null comment '权限',
    create_time datetime(3)  null,
    update_time datetime(3)  null,
    create_by   varchar(40)  null,
    update_by   varchar(40)  null,
    is_deleted  bit          null
)
    comment '用户项目权限关联表' charset = utf8mb4;

create table import_sales_date
(
    id           int unsigned   not null
        primary key auto_increment,
    task_id      varchar(40)    not null comment '任务ID',
    project_id   int            not null comment '项目ID',
    seq          int            not null comment '行号',
    create_time  datetime(3)    null,
    year         varchar(40)    null comment 'yearrpt',
    `year_month` datetime(3)    null comment 'ym',
    end_period   datetime(3)    null comment 'EndPeriod',
    product      varchar(100)   null comment 'PL',
    channel      varchar(100)   null comment 'channel',
    units        decimal(15, 4) null comment 'units',
    value        decimal(15, 4) null comment 'value',
    price        decimal(15, 4) null comment 'price',
    type         int            null comment '数据类型'
)
    comment 'SalesDate导入中间表' charset = utf8mb4;

create table import_media
(
    id           int unsigned   not null
        primary key auto_increment,
    task_id      varchar(40)    not null comment '任务ID',
    project_id   int            not null comment '项目ID',
    seq          int            not null comment '行号',
    create_time  datetime(3)    null,
    name         varchar(255)   null comment 'varnm',
    platform     varchar(100)   null comment 'Platform',
    product      varchar(100)   null comment 'media_PL',
    l_group      varchar(100)   null comment 'lgroup',
    var_metrics  varchar(100)   null comment 'var_metrics',
    `year`       varchar(100)   null comment 'yearrpt',
    `year_month` datetime(3)    null comment 'ym',
    end_period   datetime(3)    null comment 'EndPeriod',
    value        decimal(15, 4) null comment 'value'
)
    comment 'Media导入中间表' charset = utf8mb4;

create table import_media_channel
(
    id                   int unsigned   not null
        primary key auto_increment,
    task_id              varchar(40)    not null comment '任务ID',
    project_id           int            not null comment '项目ID',
    seq                  int            not null comment '行号',
    create_time          datetime(3)    null,
    model_name           varchar(100)   null comment 'modelnm',
    platform             varchar(100)   null comment 'Platform',
    product              varchar(100)   null comment 'media_PL',
    l_group              varchar(100)   null comment 'lgroup',
    var_label            varchar(100)   null comment 'varlabel',
    name                 varchar(255)   null comment 'varnm',
    pie_overall          decimal(15, 4) null comment 'pie_overall',
    pie_first_year       decimal(15, 4) null comment 'pie_X2021',
    pie_last_year        decimal(15, 4) null comment 'pie_X2022',
    spend_overall        decimal(15, 4) null comment 'spend_overall',
    spend_first_year     decimal(15, 4) null comment 'spend_X2021',
    spend_last_year      decimal(15, 4) null comment 'spend_X2022',
    value_usd_overall    decimal(15, 4) null comment 'value_usd_overall',
    value_usd_first_year decimal(15, 4) null comment 'value_usd_X2021',
    value_usd_last_year  decimal(15, 4) null comment 'value_usd_X2022',
    driven_overall       decimal(15, 4) null comment 'driven_overall',
    driven_first_year    decimal(15, 4) null comment 'driven_X2021',
    driven_last_year     decimal(15, 4) null comment 'driven_X2022',
    roi_overall          decimal(15, 4) null comment 'roi_overall',
    roi_first_year       decimal(15, 4) null comment 'roi_X2021',
    roi_last_year        decimal(15, 4) null comment 'roi_X2022',
    line                 varchar(100)   null comment 'online or offline'
)
    comment 'Media Channel导入中间表' charset = utf8mb4;

create table import_hl
(
    id          int unsigned   not null
        primary key auto_increment,
    task_id     varchar(40)    not null comment '任务ID',
    project_id  int            not null comment '项目ID',
    seq         int            not null comment '行号',
    create_time datetime(3)    null,
    platform    varchar(100)   null comment 'Platform',
    l_group     varchar(100)   null comment 'lgroup',
    hl1         decimal(15, 4) null comment 'HL1',
    l2_group    varchar(100)   null comment 'l2group',
    hl2         decimal(15, 4) null comment 'HL2'
)
    comment 'hl导入中间表' charset = utf8mb4;

create table import_curve
(
    id          int unsigned   not null
        primary key auto_increment,
    task_id     varchar(40)    not null comment '任务ID',
    project_id  int            not null comment '项目ID',
    seq         int            not null comment '行号',
    create_time datetime(3)    null,
    product     varchar(100)   null comment 'model',
    name        varchar(100)   null comment 'varnm',
    x           decimal(15, 4) null comment 'x',
    y           decimal(15, 4) null comment 'y'
)
    comment 'curve导入中间表' charset = utf8mb4;

create table import_curve_important
(
    id          int unsigned   not null
        primary key auto_increment,
    task_id     varchar(40)    not null comment '任务ID',
    project_id  int            not null comment '项目ID',
    seq         int            not null comment '行号',
    create_time datetime(3)    null,
    product     varchar(100)   null comment 'model',
    name        varchar(100)   null comment 'varnm',
    label       varchar(100)   null comment 'label',
    x           decimal(15, 4) null comment 'x',
    y           decimal(15, 4) null comment 'y'
)
    comment 'curve important导入中间表' charset = utf8mb4;


create table sales_date
(
    id           int unsigned   not null
        primary key auto_increment,
    project_id   int            not null comment '项目ID',
    product      varchar(100)   null comment 'PL',
    year         varchar(40)    null comment 'yearrpt',
    `year_month` datetime(3)    null comment 'ym',
    end_period   datetime(3)    null comment 'EndPeriod',
    channel      varchar(100)   null comment 'channel',
    units        decimal(15, 4) null comment 'units',
    value        decimal(15, 4) null comment 'value',
    price        decimal(15, 4) null comment 'price',
    type         int            null comment '数据类型',
    create_time  datetime(3)    null,
    update_time  datetime(3)    null,
    key idx_project_product (`project_id`, `product`)
)
    comment 'SalesDate数据' charset = utf8mb4;

create table media
(
    id           int unsigned   not null
        primary key auto_increment,
    project_id   int            not null comment '项目ID',
    product      varchar(100)   null comment 'media_PL',
    platform     varchar(100)   null comment 'Platform',
    l_group      varchar(100)   null comment 'lgroup',
    var_metrics  varchar(100)   null comment 'var_metrics',
    name         varchar(255)   null comment 'varnm',
    year         varchar(40)    null comment 'yearrpt',
    `year_month` datetime(3)    null comment 'ym',
    end_period   datetime(3)    null comment 'EndPeriod',
    value        decimal(15, 4) null comment 'value',
    create_time  datetime(3)    null,
    update_time  datetime(3)    null,
    key idx_project_prd_plt_met (`project_id`, `product`, `platform`, `var_metrics`)
)
    comment 'Media数据' charset = utf8mb4;

create table media_channel
(
    id                   int unsigned   not null
        primary key auto_increment,
    project_id           int            not null comment '项目ID',
    platform             varchar(100)   null comment 'Platform',
    l_group              varchar(100)   null comment 'lgroup',
    var_label            varchar(100)   null comment 'varlabel',
    product              varchar(100)   null comment 'media_PL',
    model_name           varchar(100)   null comment 'modelnm',
    name                 varchar(255)   null comment 'varnm',
    line                 varchar(100)   null comment 'online or offline',
    pie_overall          decimal(15, 4) null comment 'pie_overall',
    pie_first_year       decimal(15, 4) null comment 'pie_X2021',
    pie_last_year        decimal(15, 4) null comment 'pie_X2022',
    spend_overall        decimal(15, 4) null comment 'spend_overall',
    spend_first_year     decimal(15, 4) null comment 'spend_X2021',
    spend_last_year      decimal(15, 4) null comment 'spend_X2022',
    value_usd_overall    decimal(15, 4) null comment 'value_usd_overall',
    value_usd_first_year decimal(15, 4) null comment 'value_usd_X2021',
    value_usd_last_year  decimal(15, 4) null comment 'value_usd_X2022',
    driven_overall       decimal(15, 4) null comment 'driven_overall',
    driven_first_year    decimal(15, 4) null comment 'driven_X2021',
    driven_last_year     decimal(15, 4) null comment 'driven_X2022',
    roi_overall          decimal(15, 4) null comment 'roi_overall',
    roi_first_year       decimal(15, 4) null comment 'roi_X2021',
    roi_last_year        decimal(15, 4) null comment 'roi_X2022',
    create_time          datetime(3)    null,
    update_time          datetime(3)    null,
    key idx_project_prd_plt_met (`project_id`, `product`, `platform`)
)
    comment 'Media Channel数据' charset = utf8mb4;

create table hl
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    platform    varchar(100)   null comment 'Platform',
    l_group     varchar(100)   null comment 'lgroup',
    hl1         decimal(15, 4) null comment 'HL1',
    l2_group    varchar(100)   null comment 'l2group',
    hl2         decimal(15, 4) null comment 'HL2',
    create_time datetime(3)    null,
    update_time datetime(3)    null
)
    comment 'HL数据' charset = utf8mb4;

create table group_relation
(
    id          int unsigned not null
        primary key auto_increment,
    project_id  int          not null comment '项目ID',
    platform    varchar(100) null comment 'Platform',
    l_group     varchar(100) null comment 'lgroup',
    l2_group    varchar(100) null comment 'l2group',
    create_time datetime(3)  null,
    update_time datetime(3)  null
)
    comment 'group关联关系' charset = utf8mb4;

create table curve
(
    id              int unsigned   not null
        primary key auto_increment,
    project_id      int            not null comment '项目ID',
    product         varchar(100)   null comment 'model',
    name            varchar(100)   null comment 'lgroup',
    x               decimal(15, 4) null comment 'HL1',
    y               decimal(15, 4) null comment 'HL2',
    is_important    bit            null comment '是否important point',
    important_label varchar(100)   null comment 'import点label',
    create_time     datetime(3)    null,
    update_time     datetime(3)    null
)
    comment 'Curve数据' charset = utf8mb4;