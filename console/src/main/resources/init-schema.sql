create table attribute
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    product_id  varchar(32)                        not null comment '产品ID，关联product#id',
    module_id   varchar(32)                        not null comment '功能模块ID，关联function_module#id',
    pid         varchar(32)                        null comment '上级ID，关联data_model#id',
    name        varchar(30)                        not null comment '数据模型名称',
    identifier  varchar(50)                        not null comment '标识符',
    data_type   varchar(10)                        not null comment '数据类型，INT32：整数型；LONG64：长整数型；FLOAT：单精度浮点型；DOUBLE：双精度浮点型；ENUM：枚举型；BOOLEAN：布尔型；STRING：字符串；DATE：日期；DATETIME：日期时间；TIME：时间；TIMESTAMP：时间戳；JSON：json对象类型；ARRAY：数组',
    addition    json                               null comment '附加信息，不同数据类型附加信息不同',
    enabled     bit      default b'1'              not null comment '是否启用',
    deleted     bit      default b'0'              not null comment '是否删除',
    create_by   varchar(32)                        null comment '创建人ID，关联sys_user#id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    description varchar(100)                       null comment '描述'
)
    comment '属性';

create table attribute_unit
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    name        varchar(20)                        not null comment '名称',
    symbol      varchar(20)                        not null comment '单位',
    enabled     bit      default b'1'              not null comment '是否启用',
    deleted     bit      default b'0'              not null comment '是否删除',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '属性标准单位';

create table device
(
    id               varchar(32)                        not null comment 'ID'
        primary key,
    department_id    varchar(32)                        not null comment '部门ID，关联sys_department#id',
    product_id       varchar(32)                        not null comment '设备产品ID，关联device_product#id',
    device_code      varchar(50)                        not null comment '设备号',
    device_type      varchar(20)                        not null comment '设备类型，Direct：直连设备；Gateway：网关设备；GatewaySub：网关子设备',
    name             varchar(30)                        null comment '备注名称',
    status           varchar(15)                        not null comment '设备状态，未激活：NotActivate；在线：Online；离线：Offline',
    tags             varchar(200)                       null comment '设备标签，多个使用","隔开',
    activation_time  datetime                           null comment '激活时间',
    last_online_time datetime                           null comment '最后上线时间',
    last_report_time datetime                           null comment '最后上报时间',
    enabled          bit      default b'1'              not null comment '是否启用',
    deleted          bit      default b'0'              not null comment '是否删除',
    addition         json                               null comment '扩展信息',
    create_by        varchar(32)                        not null comment '创建人ID',
    create_time      datetime default CURRENT_TIMESTAMP not null comment '添加时间',
    mark             varchar(100)                       null comment '备注'
)
    comment '设备基本信息表';

create table device_authentication
(
    id                    varchar(32)                        not null comment 'ID'
        primary key,
    device_id             varchar(32)                        not null comment '设备ID，关联device#id',
    authentication_method varchar(20)                        null comment '鉴权方式，ProductCredential：一型一密；DeviceCredential：一机一密；AccessToken：访问令牌；MqttBasic：mqtt基础认证；X509：X.509',
    addition              json                               not null comment '附加信息，根据“鉴权方式”配置参数',
    expiration_time       datetime                           null comment '过期时间，为空时表示永不过期',
    deleted               bit      default b'0'              not null comment '是否删除',
    create_time           datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '设备鉴权信息表';

create table device_ota
(
    id                 varchar(32)                        not null comment 'ID'
        primary key,
    device_id          varchar(32)                        not null comment '设备ID，关联device#id',
    module_id          varchar(30)                        not null comment '功能模块ID，关联function_module#id',
    latest_version     varchar(30)                        not null comment '最新OTA版本',
    latest_report_time datetime default CURRENT_TIMESTAMP not null comment '最后上报时间'
)
    comment '设备固件信息';

create table device_shadow
(
    id                    varchar(32)            not null comment 'ID'
        primary key,
    device_id             varchar(32)            not null comment '设备ID，关联device#id',
    reported_state        json                   not null comment '设备上报属性状态',
    desired_state         json                   not null comment '期望属性状态',
    reported_version      bigint default 1       not null comment '上报版本号',
    desired_version       bigint default 1       not null comment '期望版本号',
    status                varchar(20)            not null comment '状态，Normal：正常，InSync：同步中；Conflict：冲突；Error：异常',
    last_update_timestamp bigint default (now()) null comment '最后更新时间',
    last_sync_timestamp   bigint                 null comment '最后同步时间',
    create_time           datetime               not null comment '创建时间',
    constraint device_shadow_device_id_uindex
        unique (device_id)
)
    comment '设备影子';

create table device_shadow_history
(
    id                  varchar(32)                   not null comment 'ID'
        primary key,
    device_id           varchar(32)                   not null comment '设备ID，关联device#id',
    shadow_name         varchar(30) default 'default' not null comment '影子名称',
    operation_type      varchar(30)                   not null comment '操作类型，Update：更新；ConflictResolve：解决冲突；RollBack：回滚',
    previous_version    bigint                        not null comment '上一个版本号',
    current_version     bigint                        not null comment '当前版本号',
    shadow_data         json                          not null comment '完整影子文档（上报+期望）',
    delta               json                          null comment '增量变更内容',
    operation_timestamp bigint                        null comment '操作时间戳',
    operation_source    varchar(20)                   not null comment '操作来源，DeviceReport：设备上报；CloudIssue：云端下发；SystemAuto：系统自动处理',
    create_time         datetime                      not null comment '创建时间'
)
    comment '设备影子历史';

create table function_module
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    product_id  varchar(32)                        not null comment '商品ID，关联product#id',
    name        varchar(30)                        not null comment '功能名称',
    identifier  varchar(50)                        not null comment '标识符',
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

create table notification
(
    id            varchar(32)                        not null comment 'ID'
        primary key,
    department_id varchar(32)                        not null comment '部门ID，关联sys_department#id',
    type_id       varchar(32)                        not null comment '通知类型ID，关联notification_type#id',
    subject       varchar(50)                        null comment '主题',
    message       varchar(100)                       null comment '信息',
    status        varchar(20)                        not null comment '状态，激活：Activation；已清除：Cleared；已确认：Confirmed；未确认：Unconfirmed',
    severity      varchar(20)                        not null comment '严重性，危险：Danger；重要：Important；次要：Secondary；警告：Warn；不确定：Uncertain',
    addition      json                               null comment '附加信息',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '通知';

create table notification_receiver
(
    id              varchar(32)                        not null comment 'ID'
        primary key,
    name            varchar(30)                        not null comment '名称',
    type            varchar(20)                        not null comment '通知类型，Platform：平台',
    match_user_away varchar(20)                        not null comment '匹配用户方式，All：全部用户；AllAdmin：全部管理员；SpecifyUser：指定用户',
    description     varchar(100)                       null comment '描述',
    create_by       varchar(32)                        not null comment '创建人ID',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '通知接收人';

create table notification_rule
(
    id              varchar(32)                        not null comment 'ID'
        primary key,
    name            varchar(30)                        not null comment '规则名称',
    trigger_type_id varchar(30)                        not null comment '触发通知的类型ID，关联notification_type#id',
    template_id     varchar(32)                        not null comment '通知模版ID，关联notification_template#id',
    receiver_ids    text                               not null comment '通知收件人ID列表，多个使用","隔开，关联notification_receiver#id',
    addition        json                               null comment '附加信息',
    enabled         bit      default b'1'              not null comment '是否启用',
    deleted         bit      default b'0'              not null comment '是否删除',
    create_by       varchar(32)                        not null comment '创建人',
    create_time     datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '通知规则';

create table notification_template
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    name        varchar(30)                        not null comment '模版名称',
    type_id     varchar(20)                        not null comment '通知类型ID，关联notification_type#id',
    push_away   varchar(20)                        not null comment '推送方式，Web；Email；Weixin',
    addition    json                               null comment '附加信息',
    deleted     bit      default b'0'              not null comment '是否删除',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    create_by   varchar(32)                        not null comment '创建人'
)
    comment '通知模版';

create table notification_type
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    name        varchar(20)                        not null comment '通知类型名称',
    identifier  varchar(30)                        not null comment '标识符',
    enabled     bit      default b'1'              not null comment '是否启用',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '通知类型';

create table ota
(
    id             varchar(32)                        not null comment 'ID'
        primary key,
    product_id     varchar(32)                        not null comment '产品ID，关联product#id',
    module_id      varchar(32)                        not null comment '设备模块ID，关联fucntion_module#id',
    type           varchar(10)                        not null comment '升级包类型，Firmware：固件；Software：软件',
    version        varchar(64)                        not null comment '版本号，格式如：1.1.1',
    sign_algorithm varchar(10)                        not null comment '签名算法，MD5；SHA256；SHA512',
    sign_with_key  bit      default b'0'              not null comment '签名是否需要密钥key参与，如果参与则由私钥加密文件byte[]内容后生成签名',
    upgrade_item   text                               not null comment '升级项，多个使用,隔开',
    addition       json                               null comment '附加信息',
    enabled        bit      default b'1'              not null comment '是否启用',
    deleted        bit      default b'0'              not null comment '是否删除',
    create_by      varchar(32)                        not null comment '创建人ID，关联sys_user#id',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    mark           varchar(200)                       null comment '备注信息'
)
    comment '设备固件信息';

create table ota_file
(
    id            varchar(32)                        not null comment 'ID'
        primary key,
    ota_id        varchar(32)                        not null comment '固件ID，关联device_ota#id',
    file_source   varchar(10)                        not null comment '包文件来源，Binary：二进制文件；Url：外部URL',
    file_checksum varchar(50)                        not null comment '包文件校验和',
    addition      json                               not null comment '附加信息',
    deleted       bit      default b'0'              not null comment '是否删除',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '设备固件文件';

create table ota_upgrade_batch
(
    id             varchar(32)                not null comment 'ID'
        primary key,
    ota_id         varchar(32)                not null comment '固件ID，关联ota#id',
    name           varchar(50)                not null comment '批次名称',
    type           varchar(20)                not null comment '批次类型，验证升级包：VerifyPackage；批量升级：BatchUpgrade',
    state          varchar(20) default 'wait' not null comment '状态，待升级：Wait；升级中：Upgrading；已完成：Completed；已取消：Cancelled；未完成：Unfinished',
    upgrade_method varchar(10)                not null comment '升级方式，静态升级：Static；动态升级：Dynamic',
    upgrade_scope  varchar(10)                not null comment '升级范围，全部设备：All；定向升级：Direction；区域升级：Area；灰度升级：Grayscale；分组升级：Group',
    addition       json                       null comment '附加信息',
    create_by      varchar(32)                not null comment '创建人ID',
    create_time    datetime                   not null comment '创建时间',
    mark           varchar(100)               null comment '备注信息'
)
    comment 'OTA升级批次';

create table ota_upgrade_progress
(
    id             varchar(32)   not null comment 'ID'
        primary key,
    device_id      varchar(32)   not null comment '设备ID，关联device#id',
    ota_id         varchar(32)   not null comment '设备固件ID，关联device_ota#id',
    ota_batch_id   varchar(32)   not null comment 'OTA批次ID，关联ota_upgrade_batch#id',
    progress       int default 0 not null comment '升级进度，0~100整数，-1:升级失败，-2:下载失败，-3：校验失败，-4：写入失败',
    state          varchar(10)   not null comment '升级状态，WaitPush：等待推送，Wait：等待升级，Success：升级成功，Failure：升级失败，Cancel：取消升级',
    state_desc     varchar(30)   null comment '升级状态描述，如：设备离线',
    start_time     datetime      not null comment '开始升级时间',
    complete_time  datetime      null comment '升级完成时间，升级成功或者失败结束的时间',
    failure_reason varchar(50)   null comment '升级失败的描述'
)
    comment '设备固件进度';

create table ota_upgrade_strategy
(
    id                      varchar(32)                        not null comment 'ID'
        primary key,
    ota_batch_id            varchar(32)                        not null comment 'OTA批次ID，关联ota_upgrade_batch#id',
    type                    varchar(15)                        not null comment '策略类型，立即升级：Immediately；定时升级：Schedule',
    active_push             bit      default b'1'              not null comment '是否主动推送',
    confirm_upgrade         bit      default b'0'              not null comment '是否需要设备确认升级，确认后才可以收到OTA',
    retry_interval          varchar(20)                        not null comment '重试间隔，Not：不重试；Immediately：立即重试；Minute10：10分钟后重试；Minute30：30分钟后重试；Hour1：1小时后重试；Hour24：24小时后重试',
    download_protocol       varchar(5)                         not null comment '升级包下载协议，Http；Mqtt',
    multiple_module_upgrade bit      default b'0'              not null comment '是否支持多模块同时升级',
    cover_before_upgrade    bit      default b'0'              not null comment '是否覆盖之前的升级',
    tags                    json                               null comment '自定义标签列表，升级时将标签下发给设备',
    create_by               varchar(32)                        not null comment '创建人ID',
    create_time             datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment 'OTA升级策略';

create table product
(
    id                      varchar(32)                           not null comment 'ID'
        primary key,
    name                    varchar(30)                           not null comment '产品名称',
    product_key             varchar(30)                           not null comment '产品Key',
    product_secret          varchar(50)                           not null comment '产品密钥',
    device_type             varchar(20)                           not null comment '设备类型，Direct：直连设备；Gateway：网关设备；Gateway-Sub：网关子设备',
    networking_away         varchar(20)                           null comment '联网方式，WiFi：Wi-Fi，CellularNetwork：蜂窝网络（2G/3G/4G/5G），Ethernet：以太网',
    access_gateway_protocol varchar(20)                           null comment '接入网关协议，Mqtt；Modbus；Rest；Socket；Grpc；Ble',
    data_format             varchar(20)                           not null comment '数据格式，Json：json；Bytes：字节；Hex：十六进制',
    authentication_method   varchar(20)                           not null comment '鉴权方式，ProductCredential：一型一密；DeviceCredential：一机一密；AccessToken：访问令牌；MqttBasic：mqtt基础认证；X509：X.509',
    dynamic_registration    bit         default b'0'              not null comment '是否开启动态注册（仅适用一型一密）',
    status                  varchar(20) default 'development'     not null comment '状态，Development：开发中，Published：已发布',
    deleted                 bit         default b'0'              not null comment '是否删除',
    description             varchar(100)                          null comment '描述',
    create_by               varchar(32)                           not null comment '创建人ID',
    create_time             datetime    default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '产品';

create table sys_department
(
    id          varchar(32)                        not null comment 'ID'
        primary key,
    name        varchar(30)                        not null comment '部门名称',
    identifier  varchar(50)                        not null comment '部门标识符',
    pid         varchar(32)                        null comment '上级部门ID，关联sys_department#id',
    sort        int      default 0                 not null comment '排序，值越大越靠后',
    level       int      default 1                 not null comment '部门层级',
    deleted     bit      default b'0'              not null comment '是否删除',
    create_by   varchar(32)                        null comment '创建人，关联sys_user#id',
    create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    description varchar(200)                       null comment '描述'
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
    checksum       text                               not null comment '校验和',
    deleted        bit      default b'0'              not null comment '是否删除',
    create_by      varchar(32)                        not null comment '创建人ID',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '文件基本信息表';

create table sys_global_setting
(
    id             varchar(32)                        not null comment 'ID'
        primary key,
    name           varchar(30)                        null comment '参数名称',
    flag           varchar(50)                        not null comment '参数标识码',
    value          text                               null comment '默认值',
    data_type      varchar(20)                        not null comment '参数数据类型，DateTime：年月日时分秒，Date：年月日，Time：时分秒，Bool：true/false，String：字符串，Number：整数，Decimal：浮点类型',
    multivalued    bit      default b'0'              null comment '是否为多值，多值之前使用英文半角","隔开',
    allow_self_set bit      default b'1'              not null comment '是否允许自己修改',
    enabled        bit      default b'1'              not null comment '是否启用',
    create_time    datetime default CURRENT_TIMESTAMP not null comment '添加时间',
    mark           varchar(200)                       null comment '备注',
    constraint sys_global_setting_flag_uindex
        unique (flag)
)
    comment '全局参数' row_format = DYNAMIC;

create table sys_log
(
    id            varchar(32)                        not null comment 'ID'
        primary key,
    user_id       varchar(32)                        not null comment '操作用户ID，关联sys_user#id',
    session_id    varchar(32)                        not null comment '会话ID',
    action        varchar(20)                        not null comment '操作动作',
    object_type   varchar(20)                        not null comment '操作目标类型',
    object_id     varchar(32)                        not null comment '对象ID',
    msg           varchar(200)                       null comment '操作描述',
    success       bit      default b'1'              not null comment '是否成功',
    addition      json                               null comment '附加信息',
    activity_data json                               null comment '活动数据',
    create_time   datetime default CURRENT_TIMESTAMP not null comment '创建时间'
)
    comment '用户操作日志';

create table sys_user
(
    id                   varchar(32)                           not null comment 'ID'
        primary key,
    name                 varchar(30)                           not null comment '名称',
    account              varchar(30)                           not null comment '账号',
    email                varchar(50)                           not null comment '邮箱地址',
    phone                varchar(11)                           null comment '手机号',
    pwd                  varchar(100)                          null comment '加密后的登录密码',
    activate_method      varchar(20)                           null comment '账号激活方式，SendUrlToEmail：向邮箱发送激活邮件；ShowUrl：显示激活链接',
    activate_token       varchar(50)                           null comment '激活码',
    department_id        varchar(32)                           null comment '所属部门ID，关联sys_department#id',
    identity             varchar(30) default 'user'            not null comment '用户身份；管理员：Aministrator（租户ID不为空时表示为租户管理员）；普通用户：User；',
    last_login_time      datetime                              null comment '最后登录时间',
    last_change_pwd_time datetime                              null comment '最后修改密码的时间',
    enabled              bit         default b'1'              not null comment '是否启用',
    deleted              bit         default b'0'              not null comment '是否删除',
    create_by            varchar(32)                           null comment '创建人ID，关联sys_user#id',
    create_time          datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    mark                 varchar(100)                          null comment '备注'
)
    comment '用户基本信息表';

create table sys_user_session
(
    id               varchar(32)                  not null comment 'ID'
        primary key,
    user_id          varchar(32)                  not null comment '用户ID，关联sys_user#id',
    username         varchar(50)                  not null comment '授权给的用户',
    token_value      text                         not null comment '令牌值',
    platform_type    varchar(10)                  not null comment '平台类型，Pc：电脑端、App：移动端',
    status           varchar(20) default 'normal' not null comment '会话状态，Normal：正常，LoggedOut：已登出，Kickoff：被踢除',
    issued_time      datetime                     not null comment '令牌发行时间',
    expires_time     datetime                     not null comment '过期时间',
    logout_time      datetime                     null comment '主动退出登录时间',
    last_active_time datetime                     null comment '最后活跃时间'
)
    comment '用户会话';

create table telemetry
(
    id                    varchar(32)                        not null comment 'ID'
        primary key,
    device_id             varchar(32)                        not null comment '设备ID，关联device#id',
    metric_type           varchar(30)                        not null comment '指标类型，DeviceMetadata：设备元数据；DeviceState：设备状态；ProtocolMetadata：协议元数据；TriggerEvent：触发事件；BusinessData：业务数据；Log：日志',
    metric_key            varchar(50)                        not null comment '遥测数据指标Key',
    metric_value          json                               not null comment '遥测指标数据值',
    addition              json                               null comment '附加信息',
    last_update_timestamp bigint                             not null comment '遥测数据最新上报时间戳',
    create_time           datetime default CURRENT_TIMESTAMP not null comment '创建时间，首次上报时间'
)
    comment '设备最新遥测数据';

create index telemetry_device_id_index
    on telemetry (device_id);

create index telemetry_metric_type_metric_key_index
    on telemetry (metric_type, metric_key);