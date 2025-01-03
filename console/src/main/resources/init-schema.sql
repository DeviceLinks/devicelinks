create table command
(
    id            varchar(32)                        not null comment 'ID'
        primary key,
    device_id     varchar(32)                        not null comment '设备ID，关联device#id',
    content       varchar(100)                       not null comment '指令内容',
    params        json                               not null comment '参数',
    status        varchar(20)                        null comment '指令状态；已发送：sent，待发送：wait，发送成功：success',
    send_time     datetime                           null comment '指令发送时间',
    response_time datetime                           null comment '响应时间',
    create_by     varchar(32)                        not null comment '创建人ID',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '指令';

create table data_properties
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    module_id   varchar(32)                        not null comment '功能模块ID，关联function_module#id',
    pid         varchar(32)                        null comment '上级ID，关联data_model#id',
    type        varchar(10)                        not null comment '数据模型类型，attribute：属性',
    name        varchar(30)                        not null comment '数据模型名称',
    identifier  varchar(50)                        not null comment '标识符',
    data_type   varchar(10)                        not null comment '数据类型，int32：整数型；long64：长整数型；float：单精度浮点型；double：双精度浮点型；enum：枚举型；boolean：布尔型；string：字符串；date：日期；datetime：日期时间；time：时间；timestamp：时间戳；object：对象类型；array：数组',
    rw_type     varchar(10)                        not null comment '读写类型，readwrite：读写，readonly：只读',
    description varchar(100)                       null comment '描述',
    addition    json                               null comment '附加信息，不同数据类型附加信息不同',
    enabled     bit      default b'1'              not null comment '是否启用',
    deleted     bit      default b'0'              not null comment '是否删除',
    create_by   varchar(32)                        null comment '创建人ID，关联sys_user#id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '数据模型';

create table data_properties_unit
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    name        varchar(20)                        not null comment '名称',
    symbol      varchar(20)                        not null comment '单位',
    enabled     bit      default b'1'              not null comment '是否启用',
    deleted     bit      default b'0'              not null comment '是否删除',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '数据模型标准单位';

create table device
(
    id                 varchar(32)                        not null comment 'ID'
        primary key,
    device_number      varchar(50)                        not null comment '设备号',
    product_id         varchar(32)                        not null comment '设备产品ID，关联device_product#id',
    name               varchar(30)                        null comment '设备名称',
    status             varchar(15)                        not null comment '设备状态，未激活：NOT_ACTIVATED；在线：ONLINE；离线：OFFLINE；',
    tags               varchar(200)                       null comment '设备标签，多个使用","隔开',
    certification_code varchar(50)                        null comment '认证码',
    activation_time    datetime                           null comment '激活时间',
    last_online_time   datetime                           null comment '最后上线时间',
    last_report_time   datetime                           null comment '最后上报时间',
    enabled            bit      default b'1'              not null comment '是否启用',
    deleted            bit      default b'0'              not null comment '是否删除',
    extended_info      json                               null comment '扩展信息',
    create_time        datetime default CURRENT_TIMESTAMP not null comment '添加时间',
    create_by          varchar(32)                        not null comment '创建人ID',
    mark               varchar(100)                       null comment '备注'
)
    comment '设备基本信息表';

create table device_attribute_desired
(
    id               varchar(32)                        not null comment 'ID'
        primary key,
    device_id        varchar(32)                        not null comment '设备ID',
    module_id        varchar(32)                        not null comment '功能模块ID，关联function_module#id',
    properties_id    varchar(32)                        not null comment '属性ID，关联data_properties#id',
    properties_value text                               not null comment '属性期望值',
    version          int                                not null comment '期望值版本',
    create_by        varchar(32)                        not null comment '创建人ID',
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '设备属性期望值';

create table device_attribute_latest
(
    id               varchar(32)                        not null comment 'ID'
        primary key,
    device_id        varchar(32)                        not null comment '设备ID，关联device#id',
    module_id        varchar(32)                        not null comment '功能模块ID，关联function_module#id',
    properties_id    varchar(32)                        not null comment '属性ID，关联data_properties#id',
    properties_value text                               not null comment '属性值',
    latest_time      datetime                           not null comment '设备产生属性值的时间',
    create_time      datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '设备属性最新值';

create table device_authentication
(
    id              varchar(32)                        not null comment 'ID'
        primary key,
    device_id       varchar(32)                        not null comment '设备ID，关联device#id',
    code            varchar(100)                       not null comment '鉴权码',
    expiration_time datetime                           null comment '过期时间，为空时表示永不过期',
    deleted         bit      default b'0'              not null comment '是否删除',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '设备鉴权信息表';

create table device_ota_latest
(
    id               varchar(32)                        not null comment 'ID'
        primary key,
    device_id        varchar(32)                        not null comment '设备ID，关联device#id',
    module           varchar(30)                        not null comment '功能模块',
    version          varchar(30)                        not null comment '固件版本',
    last_report_time datetime default CURRENT_TIMESTAMP not null comment '最后上报时间'
)
    comment '设备固件信息';

create table function_module
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    product_id  varchar(32)                        not null comment '商品ID，关联product#id',
    name        varchar(30)                        not null comment '功能名称',
    deleted     bit      default b'0'              not null comment '是否删除',
    create_by   varchar(32)                        not null comment '创建人',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '功能模块';

create table function_module_template
(
    id          varchar(32)              not null comment 'ID'
        primary key,
    module_id   varchar(32)              not null comment '功能模块ID，关联function_module#id',
    name        varchar(30)              not null comment '模版名称',
    deleted     bit      default b'0'    not null comment '是否删除',
    create_by   varchar(32)              not null comment '创建人ID',
    create_time datetime default (now()) not null comment '创建时间'
)
    comment '功能模块模版';

create table mqtt_broker_topic
(
    id                varchar(32)                        not null comment 'ID'
        primary key,
    name              varchar(30)                        not null comment '类别名称',
    topic             varchar(50)                        not null comment '主题',
    category          varchar(30)                        not null comment '类别，基础通信：basic；数据通信：data；自定义：customize',
    sub_category      varchar(20)                        not null comment '子类别',
    device_permission varchar(10)                        not null comment '设备端权限；publish：发布，subscribe：订阅',
    enabled           bit      default b'1'              not null comment '是否启用',
    mark              varchar(50)                        null comment '备注',
    create_by         varchar(32)                        null comment '创建人，关联sys_user#id',
    create_time       datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment 'mqtt_broker定义的topic';

create table mqtt_broker_user
(
    id            mediumint auto_increment
        primary key,
    username      varchar(100) not null,
    password_hash varchar(200) not null,
    is_admin      tinyint(1)   not null
);

create table mqtt_broker_acl
(
    id      mediumint auto_increment
        primary key,
    user_id mediumint    not null,
    topic   varchar(200) not null,
    rw      int          not null comment '1:readonly, 2:writeonly ,3:readwrite ,4:subscribe ',
    constraint mqtt_broker_acl_ibfk_1
        foreign key (user_id) references mqtt_broker_user (id)
            on update cascade on delete cascade
);

create index mosquitto_user_id
    on mqtt_broker_acl (user_id);

create table ota
(
    id             varchar(32)                          not null comment 'ID'
        primary key,
    product_id     varchar(32)                          not null comment '产品ID，关联product#id',
    module_id      varchar(32)                          not null comment '设备模块ID，关联fucntion_module#id',
    type           varchar(5) default 'full'            not null comment '升级包类型，full：完整，diff：差分',
    version_before varchar(64)                          null comment '待升级版本号，仅差分类型该列存在值，格式如：1.1.0',
    version        varchar(64)                          not null comment '版本号，格式如：1.1.1',
    sign_algorithm varchar(10)                          not null comment '签名算法，MD5；SHA256',
    sign_with_key  bit        default b'0'              not null comment '签名是否需要密钥key参与，如果参与则由私钥加密文件byte[]内容后生成签名',
    verify         bit        default b'0'              not null comment '是否需要验证升级包，验证通过后才可以批量下发',
    upgrade_item   text                                 not null comment '升级项，多个使用;隔开',
    addition       json                                 null comment '附加信息',
    enabled        bit        default b'1'              not null comment '是否启用',
    deleted        bit        default b'0'              not null comment '是否删除',
    create_time    datetime   default CURRENT_TIMESTAMP not null comment '创建时间',
    create_by      varchar(32)                          not null comment '创建人ID，关联sys_user#id',
    mark           varchar(200)                         null comment '备注信息'
)
    comment '设备固件信息';

create table ota_file
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    ota_id      varchar(32)                        not null comment '固件ID，关联device_ota#id',
    file_id     varchar(32)                        not null comment '文件ID，关联sys_file#id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '设备固件文件';

create table ota_upgrade_batch
(
    id             varchar(32)                not null comment 'ID'
        primary key,
    ota_id         varchar(32)                not null comment '固件ID，关联ota#id',
    name           varchar(50)                not null comment '批次名称',
    type           varchar(20)                not null comment '批次类型，验证升级包：verify_package；批量升级：batch_upgrade',
    state          varchar(20) default 'wait' not null comment '状态，待升级：wait；升级中：upgrading；已完成：completed；已取消：cabcelled；未完成：unfinished',
    upgrade_method varchar(10)                not null comment '升级方式，静态升级：static；动态升级：dynamic',
    upgrade_scope  varchar(10)                not null comment '升级范围，全部设备：all；定向升级：direction；区域升级：area；灰度升级：grayscale；分组升级：group',
    addition       json                       null comment '附加信息',
    create_by      varchar(32)                not null comment '创建人ID',
    create_time    datetime                   not null comment '创建时间',
    mark           varchar(100)               null comment '备注信息'
)
    comment 'OTA升级批次';

create table ota_upgrade_progress
(
    id            varchar(32)   not null comment 'ID'
        primary key,
    device_id     varchar(32)   not null comment '设备ID，关联device#id',
    ota_id        varchar(32)   not null comment '设备固件ID，关联device_ota#id',
    ota_batch_id  varchar(32)   not null comment 'OTA批次ID，关联ota_upgrade_batch#id',
    progress      int default 0 not null comment '升级进度，0~100整数，-1:升级失败，-2:下载失败，-3：校验失败，-4：写入失败',
    state         varchar(10)   not null comment '升级状态，WAIT_PUSH：等待推送，WAIT：等待升级，SUCCESS：升级成功，FAILURE：升级失败，CANCEL：取消升级',
    state_desc    varchar(30)   null comment '升级状态描述，如：设备离线',
    start_time    datetime      not null comment '开始升级时间',
    complete_time datetime      null comment '升级完成时间，升级成功或者失败结束的时间',
    failure_desc  varchar(50)   null comment '升级失败的描述'
)
    comment '设备固件进度';

create table ota_upgrade_strategy
(
    id                      varchar(32)                        not null comment 'ID'
        primary key,
    ota_batch_id            varchar(32)                        not null comment 'OTA批次ID，关联ota_upgrade_batch#id',
    type                    varchar(15)                        not null comment '策略类型，立即升级：immediately；定时升级：schedule',
    active_push             bit      default b'1'              not null comment '是否主动推送',
    confirm_upgrade         bit      default b'0'              not null comment '是否需要设备确认升级，确认后才可以收到OTA',
    retry_interval          varchar(20)                        not null comment '重试间隔，不重试；立即重试；10分钟后重试；30分钟后重试；1小时后重试；24小时后重试',
    download_protocol       varchar(5)                         not null comment '升级包下载协议，http；mqtt',
    multiple_module_upgrade bit      default b'0'              not null comment '是否支持多模块同时升级',
    cover_before_upgrade    bit      default b'0'              not null comment '是否覆盖之前的升级',
    tags                    json                               null comment '自定义标签列表，升级时将标签下发给设备',
    create_by               varchar(32)                        not null comment '创建人ID',
    create_time             datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment 'OTA升级策略';

create table product
(
    id                    varchar(32)                           not null comment 'ID'
        primary key,
    name                  varchar(20)                           not null comment '产品名称',
    product_key           varchar(30)                           not null comment '产品Key',
    product_secret        varchar(50)                           not null comment '产品密钥',
    networking_away       varchar(20)                           not null comment '联网方式，wifi：Wi-Fi，cellular_network：蜂窝网络（2G/3G/4G/5G），ethernet：以太网',
    protocol_id           varchar(32)                           not null comment '协议ID，关联device_protocol#id',
    data_format           varchar(20)                           not null comment '数据格式，JSON：json；BINARY：二进制；DECIMAL：十进制；HEX：十六进制；STRING：字符串',
    authentication_method varchar(20)                           not null comment '鉴权方式，DeviceCredential：一机一密，Signature：固定签名，TemporaryToken：临时令牌',
    signature_code        varchar(50)                           null comment '固定签名码',
    status                varchar(20) default 'development'     not null comment '状态，development：开发中，published：已发布',
    deleted               bit         default b'0'              not null comment '是否删除',
    description           varchar(100)                          null comment '描述',
    create_time           datetime    default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '产品';

create table product_authorization_mqtt_topic
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    product_id  varchar(32)                        not null comment '产品ID，关联product#id',
    topic_id    varchar(32)                        not null comment '主题ID，关联mqtt_broker_topic#id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '产品MQTT主题关系';

create table sys_department
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    name        varchar(30)                        not null comment '部门名称',
    code        varchar(50)                        null comment '部门编码',
    pid         varchar(32)                        null comment '上级部门ID，关联sys_department#id',
    sort        int      default 0                 not null comment '排序，值越大越靠后',
    level       int      default 1                 not null comment '部门层级',
    create_by   varchar(32)                        null comment '创建人，关联sys_user#id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    deleted     bit      default b'0'              not null comment '是否删除',
    mark        varchar(200)                       null comment '备注'
)
    comment '部门基本信息表';

create table sys_department_level
(
    id                   varchar(32)                        not null comment 'ID'
        primary key,
    department_id        varchar(32)                        not null comment '部门ID，关联sys_department#id',
    parent_department_id varchar(32)                        not null comment '上级部门ID，关联sys_department#id',
    create_time          datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '部门等级关系';

create index sys_department_level_department_id_index
    on sys_department_level (department_id);

create index sys_department_level_parent_department_id_index
    on sys_department_level (parent_department_id);

create table sys_file
(
    id             varchar(32)                        not null comment 'ID'
        primary key,
    name           varchar(50)                        not null comment '文件名称',
    path           varchar(100)                       not null comment '文件位置',
    size           int      default 0                 not null comment '文件字节大小',
    sign_algorithm varchar(36)                        not null comment '签名算法，MD5；SHA256',
    sign_with_key  bit      default b'0'              not null comment '签名是否需要密钥key参与，如果参与则由私钥加密文件byte[]内容后生成签名',
    sign           text                               not null comment '文件签名',
    deleted        bit      default b'0'              not null comment '是否删除',
    create_by      varchar(32)                        not null comment '创建人ID',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '文件基本信息表';

create table sys_global_setting
(
    id             varchar(20)                        not null comment 'ID'
        primary key,
    name           varchar(100)                       null comment '参数名称',
    flag           varchar(100)                       not null comment '参数标识码',
    default_value  text                               null comment '默认值',
    data_type      varchar(20)                        not null comment '参数数据类型，datetime：年月日时分秒，date：年月日，time：时分秒，boolean：true/false，string：字符串，number：整数，decimal：浮点类型',
    multivalued    bit      default b'0'              null comment '是否为多值，多值之前使用英文半角","隔开',
    allow_self_set bit      default b'1'              not null comment '是否允许自己修改',
    enabled        bit      default b'1'              not null comment '是否启用',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '添加时间',
    mark           varchar(200)                       null comment '备注',
    constraint sys_global_setting_flag_uindex
        unique (flag)
)
    comment '全局参数' row_format = DYNAMIC;

create table sys_group
(
    id            varchar(32)                        not null comment 'ID'
        primary key,
    department_id varchar(32)                        not null comment '部门ID，关联sys_department#id',
    name          varchar(30)                        not null comment '分组名称',
    type          varchar(10)                        not null comment '分组类型；user：用户组，permission：权限组',
    enabled       bit      default b'1'              not null comment '是否启用',
    deleted       bit      default b'0'              not null comment '是否删除',
    create_by     varchar(32)                        not null comment '创建人ID，关联sys_user#id',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '分组信息表';

create table sys_group_user
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    group_id    varchar(32)                        not null comment '分组ID，关联sys_group#id',
    user_id     varchar(32)                        not null comment '用户ID，关联sys_user#id',
    create_by   varchar(32)                        not null comment '创建人ID，关联sys_user#id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户与用户组关系';

create table sys_position
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    name        varchar(30)                        not null comment '职位名称',
    deleted     bit      default b'0'              not null comment '是否删除',
    enabled     bit      default b'1'              not null comment '是否启用',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    create_by   varchar(32)                        not null comment '创建人',
    mark        int                                null comment '备注'
)
    comment '职位基本信息';

create table sys_user
(
    id                   varchar(32)                           not null comment 'ID'
        primary key,
    name                 varchar(30)                           not null comment '名称',
    account              varchar(30)                           not null comment '账号',
    pwd                  varchar(100)                          not null comment '加密后的登录密码',
    department_id        varchar(32)                           null comment '所属部门ID，关联sys_department#id',
    position_id          varchar(32)                           null comment '岗位ID，关联sys_position#id',
    identity             varchar(30) default 'user'            not null comment '用户身份；管理员：administrator（租户ID不为空时表示为租户管理员）；普通用户：user；',
    enabled              bit         default b'1'              not null comment '是否启用',
    deleted              bit         default b'0'              not null comment '是否删除',
    last_login_time      datetime                              null comment '最后登录时间',
    last_change_pwd_time datetime                              null comment '最后修改密码的时间',
    create_by            varchar(32)                           null comment '创建人ID，关联sys_user#id',
    create_time          datetime    default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户基本信息表';

create table sys_user_login_log
(
    id              varchar(32) not null comment 'ID'
        primary key,
    user_id         varchar(32) not null comment '用户ID，关联sys_user#id',
    user_session_id varchar(32) not null comment '用户会话ID，关联sys_user_session#id',
    request_id      varchar(32) null comment '请求ID',
    ip_address      varchar(30) not null comment '登录IP地址',
    source_address  varchar(20) null comment '登录地',
    platform_type   varchar(5)  null comment '平台类型，pc：电脑端、app：移动端',
    addition        json        null comment '附加信息',
    login_time      datetime    not null comment '登录时间'
)
    comment '用户登录日志';

create index sys_user_login_log_user_id_index
    on sys_user_login_log (user_id);

create table sys_user_operate_log
(
    id             varchar(32)      not null comment 'ID'
        primary key,
    user_id        varchar(32)      not null comment '操作用户ID，关联sys_user#id',
    request_id     varchar(32)      not null comment '请求ID',
    resource_code  varchar(30)      not null comment '资源Code，关联sys_resource#code',
    action         varchar(20)      not null comment '操作动作',
    object_type    varchar(20)      not null comment '操作目标类型',
    object         varchar(50)      not null comment '操作对象',
    object_fields  text             null comment '操作对象字段列表',
    msg            varchar(200)     null comment '操作描述',
    success        bit default b'1' not null comment '是否成功',
    failure_reason varchar(200)     null comment '操作失败时的原因',
    ip_address     varchar(30)      not null comment '操作人登录IP地址',
    addition       json             null comment '附加信息',
    operate_time   datetime         null comment '操作时间'
)
    comment '用户操作日志';

create table sys_user_session
(
    id               varchar(32)                  not null comment 'ID'
        primary key,
    user_id          varchar(32)                  not null comment '用户ID，关联sys_user#id',
    username         varchar(50)                  not null comment '授权给的用户',
    token_value      text                         not null comment '令牌值',
    ip_address       varchar(30)                  not null comment '登录地IP',
    platform_type    varchar(10)                  not null comment '平台类型，pc：电脑端、app：移动端',
    status           varchar(20) default 'normal' not null comment '会话状态，normal：正常，loggedOut：已登出，kickoff：被剔除',
    issued_time      datetime                     not null comment '令牌发行时间',
    expires_time     datetime                     not null comment '过期时间',
    logout_time      datetime                     null comment '主动退出登录时间',
    last_active_time datetime                     null comment '最后活跃时间'
)
    comment '用户会话';

