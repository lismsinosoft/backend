create table project_years
(
    id          int unsigned not null
        primary key auto_increment,
    project_id  int          not null comment '项目ID',
    table_no    int          null comment '图表号',
    year        int          null comment '年份',
    create_time datetime(3)  null,
    update_time datetime(3)  null,
    key idx_project_year_table (`project_id`, `table_no`)
)
    comment '图表年份表' charset = utf8mb4;

create table project_series
(
    id          int unsigned not null
        primary key auto_increment,
    project_id  int          not null comment '项目ID',
    name        varchar(512) null comment 'series name',
    create_time datetime(3)  null,
    update_time datetime(3)  null,
    unique key idx_project_series (`project_id`, `name`)
)
    comment '图表产品表' charset = utf8mb4;

create table t1p1
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    type1       varchar(100)   null comment 'type1',
    year        int            null comment '年份',
    value       decimal(15, 4) null comment 'value',
    growth      decimal(15, 4) null comment '增长率',
    percent     decimal(15, 4) null comment '百分比',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t1p1_project_id (`project_id`)
)
    comment 't1p1数据' charset = utf8mb4;

create table t1p2
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    year        varchar(100)   null comment '年份',
    `period`    datetime(3)    null comment 'ym',
    units       decimal(15, 4) null comment '单价',
    price       decimal(15, 4) null comment '价格',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t1p2_project_id (`project_id`)
)
    comment 't1p2数据' charset = utf8mb4;

create table t1p3
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    overall     varchar(100)   null comment 'type1',
    year        int            null comment '年份',
    value       decimal(15, 4) null comment 'value',
    growth      decimal(15, 4) null comment '增长率',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t1p3_project_id (`project_id`)
)
    comment 't1p3数据' charset = utf8mb4;

create table t2p1
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    year        varchar(100)   null comment '年份',
    label       varchar(100)   null comment 'label',
    value       decimal(15, 4) null comment '百分比',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t2p1_project_id (`project_id`)
)
    comment 't2p1数据' charset = utf8mb4;

create table t2p2
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    type1       varchar(100)   null comment 'type1',
    type2       varchar(100)   null comment 'type2',
    year        int            null comment '年份',
    value       decimal(15, 4) null comment 'value',
    growth      decimal(15, 4) null comment '增长率',
    percent     decimal(15, 4) null comment '百分比',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t2p2_project_id (`project_id`)
)
    comment 't2p2数据' charset = utf8mb4;

create table t3p1
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    platform    varchar(100)   null comment 'platform',
    year        int            null comment '年份',
    driven      decimal(15, 4) null comment 'driven',
    spending    decimal(15, 4) null comment 'spending',
    roi         decimal(15, 4) null comment 'roi',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t3p1_project_id (`project_id`)
)
    comment 't3p1数据' charset = utf8mb4;

create table t4p1
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    type2       varchar(100)   null comment 'type2',
    year        int            null comment '年份',
    value       decimal(15, 4) null comment 'value',
    growth      decimal(15, 4) null comment '增长率',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t4p1_project_id (`project_id`)
)
    comment 't4p1数据' charset = utf8mb4;

create table t4p2
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    type1       varchar(100)   null comment 'type1',
    type2       varchar(100)   null comment 'type2',
    act_on      varchar(100)   null comment 'actOn',
    year        int            null comment '年份',
    roi         decimal(15, 4) null comment 'roi',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t4p2_project_id (`project_id`)
)
    comment 't4p2数据' charset = utf8mb4;

create table t4p3
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    type2       varchar(100)   null comment 'type2',
    type3       varchar(100)   null comment 'type3',
    year        int            null comment '年份',
    value       decimal(15, 4) null comment 'value',
    growth      decimal(15, 4) null comment '增长率',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t4p3_project_id (`project_id`)
)
    comment 't4p3数据' charset = utf8mb4;

create table t4p4
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    type2       varchar(100)   null comment 'type2',
    type3       varchar(100)   null comment 'type3',
    act_on      varchar(100)   null comment 'actOn',
    year        int            null comment '年份',
    roi         decimal(15, 4) null comment 'roi',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t4p4_project_id (`project_id`)
)
    comment 't4p4数据' charset = utf8mb4;


create table t5p1
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    type1       varchar(100)   null comment 'type1',
    type2       varchar(100)   null comment 'type2',
    act_on      varchar(100)   null comment 'actOn',
    name        varchar(100)   null comment 'variable',
    year        int            null comment '年份',
    value       decimal(15, 4) null comment 'value',
    growth      decimal(15, 4) null comment '增长率',
    sort        int            null comment 'r_variable',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t5p1_project_id (`project_id`)
)
    comment 't5p1数据' charset = utf8mb4;

create table t5p2
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    type1       varchar(100)   null comment 'type1',
    type2       varchar(100)   null comment 'type2',
    act_on      varchar(100)   null comment 'actOn',
    year        int            null comment '年份',
    value       decimal(15, 4) null comment 'value',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t5p2_project_id (`project_id`)
)
    comment 't5p2数据' charset = utf8mb4;

create table t5p3
(
    id           int unsigned   not null
        primary key auto_increment,
    project_id   int            not null comment '项目ID',
    series       varchar(100)   null comment 'series',
    type1        varchar(100)   null comment 'type1',
    type2        varchar(100)   null comment 'type2',
    act_on       varchar(100)   null comment 'actOn',
    x            decimal(15, 4) null comment 'x',
    y            decimal(15, 4) null comment 'y',
    is_important bit            null comment '是否关键点',
    label        varchar(100)   null comment 'label',
    create_time  datetime(3)    null,
    update_time  datetime(3)    null,
    key idx_t5p3_project_id (`project_id`),
    key idx_t5p3_filter (`project_id`, `series`, `type1`, `type2`, `act_on`)
)
    comment 't5p3数据' charset = utf8mb4;

create table t5p4
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    varnm       varchar(100)   null comment 'varnm',
    series      varchar(100)   null comment 'series',
    type1       varchar(100)   null comment 'type1',
    type2       varchar(100)   null comment 'type2',
    act_on      varchar(100)   null comment 'actOn',
    metrics     varchar(100)   null comment 'metrics',
    `period`    datetime(3)    null comment 'period',
    value       decimal(15, 4) null comment 'value',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_t5p4_project_id (`project_id`),
    key idx_t5p4_filter (`project_id`, `series`, `type1`, `type2`, `act_on`)
)
    comment 't5p4数据' charset = utf8mb4;

create table timeframe
(
    id          int unsigned not null
        primary key auto_increment,
    project_id  int          not null comment '项目ID',
    frame1      varchar(100) null comment 'timeframe1',
    frame2      varchar(100) null comment 'timeframe2',
    create_time datetime(3)  null,
    update_time datetime(3)  null,
    key idx_timeframe_project_id (`project_id`)
)
    comment 'timeframe数据' charset = utf8mb4;

create table sim_t1p1
(
    id           int unsigned   not null
        primary key auto_increment,
    project_id   int            not null comment '项目ID',
    series       varchar(100)   null comment 'series',
    frame1       varchar(100)   null comment 'timeframe1',
    frame2       varchar(100)   null comment 'timeframe2',
    type1        varchar(100)   null comment 'type1',
    aspect       varchar(100)   null comment 'aspect',
    spending     decimal(15, 4) null comment 'spending (x)',
    revenue      decimal(15, 4) null comment 'revenue (y)',
    is_important bit            null comment '是否关键点',
    label        varchar(100)   null comment 'label',
    create_time  datetime(3)    null,
    update_time  datetime(3)    null,
    key idx_sim_t1p1_project_id (`project_id`),
    key idx_sim_t1p1_filter (`project_id`, `series`, `frame1`, `frame2`, `type1`)
)
    comment 'Simulator t1p1数据' charset = utf8mb4;


create table sim_t1p2
(
    id          int unsigned   not null
        primary key auto_increment,
    project_id  int            not null comment '项目ID',
    series      varchar(100)   null comment 'series',
    frame1      varchar(100)   null comment 'timeframe1',
    frame2      varchar(100)   null comment 'timeframe2',
    type1       varchar(100)   null comment 'type1',
    label       varchar(100)   null comment 'label',
    spend       decimal(15, 4) null comment 'spend',
    revenue     decimal(15, 4) null comment 'revenue',
    roi         decimal(15, 4) null comment 'roi',
    create_time datetime(3)    null,
    update_time datetime(3)    null,
    key idx_sim_t1p2_project_id (`project_id`),
    key idx_sim_t1p2_filter (`project_id`, `series`, `frame1`, `frame2`, `type1`)
)
    comment 'Simulator t1p2数据' charset = utf8mb4;

create table sim_t1p3
(
    id              int unsigned   not null
        primary key auto_increment,
    project_id      int            not null comment '项目ID',
    series          varchar(100)   null comment 'series',
    frame1          varchar(100)   null comment 'timeframe1',
    frame2          varchar(100)   null comment 'timeframe2',
    type1           varchar(100)   null comment 'type1',
    spend_current   decimal(15, 4) null comment 'Spend Current',
    spend_minimal   decimal(15, 4) null comment 'Spend Minimal',
    spend_optimal   decimal(15, 4) null comment 'Spend Optimal',
    revenue_current decimal(15, 4) null comment 'Revenue Current',
    revenue_minimal decimal(15, 4) null comment 'Revenue Minimal',
    revenue_optimal decimal(15, 4) null comment 'Revenue Optimal',
    roi_current     decimal(15, 4) null comment 'Roi Current',
    roi_minimal     decimal(15, 4) null comment 'Roi Minimal',
    roi_optimal     decimal(15, 4) null comment 'Roi Optimal',
    status          varchar(100)   null comment 'status',
    action          varchar(100)   null comment 'action',
    spend_gap_num   decimal(15, 4) null comment 'Spend Gap Num',
    spend_gap_pct   decimal(15, 4) null comment 'Spend Gap Pct',
    roi_gap_num     decimal(15, 4) null comment 'Roi Gap Num',
    roi_gap_pct     decimal(15, 4) null comment 'Roi Gap Pct',
    revenue_gap_num decimal(15, 4) null comment 'Revenue Gap Num',
    revenue_gap_pct decimal(15, 4) null comment 'Revenue Gap Pct',
    create_time     datetime(3)    null,
    update_time     datetime(3)    null,
    key idx_sim_t1p3_project_id (`project_id`),
    key idx_sim_t1p3_filter (`project_id`, `series`, `frame1`, `frame2`, `type1`)
)
    comment 'Simulator t1p3数据' charset = utf8mb4;

create table language_text
(
    id          int unsigned  not null
        primary key auto_increment,
    project_id  int           not null comment '项目ID',
    text_key    varchar(100)  null comment '多语言key',
    text_value  varchar(2000) null comment '多语言value',
    language    varchar(100)  null comment '所属语言',
    create_time datetime(3)   null,
    update_time datetime(3)   null,
    key idx_sim_t1p3_project_id (`project_id`),
    key idx_sim_t1p3_project_language (`project_id`, `language`)
)
    comment '多语言文本表' charset = utf8mb4;

create table file
(
    id          int unsigned  not null
        primary key auto_increment,
    project_id  int           null comment '项目ID',
    filename    varchar(200)  not null comment '文件名',
    file_path   varchar(2000) not null comment '文件路径',
    file_md5    varchar(100)  null comment '文件MD5',
    file_size   int           not null comment '文件大小',
    create_time datetime(3)   null,
    update_time datetime(3)   null,
    key idx_file_project_id (`project_id`),
    key idx_file_filename (`filename`)
)
    comment '文件上传表' charset = utf8mb4;
