declare namespace API {
  type AccessGatewayProtocol = Enum[];

  type AlarmType = Enum[];

  type Attribute = {
    /** 属性ID */
    id: string;
    /** 产品ID */
    productId: string;
    /** 功能模块ID */
    moduleId: string;
    /** 上级ID */
    pid: string;
    /** 属性名称 */
    name: string;
    /** 属性标识符 */
    identifier: string;
    /** 数据类型 */
    dataType: string;
    /** 附加信息 */
    addition: {
      unitId?: string;
      step?: number;
      dataLength?: number;
      valueRange: { min?: number; max?: number };
      valueMap?: Record<string, any>;
    };
    /** 是否启用 */
    enabled: boolean;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人ID */
    createBy: any;
    /** 创建时间 */
    createTime: any;
    /** 描述 */
    description: any;
    /** 子属性列表 */
    childAttributes: Attribute[];
  };

  type AttributeDataType = Enum[];

  type CurrentUser = {
    user: User;
    department: Department;
  };

  type DataFormat = Enum[];

  type deleteApi_openAPI_functionModuleModuleIdParams = {
    /** 功能模块ID */
    moduleId: string;
  };

  type deleteApiAttributeAttributeIdParams = {
    /** 属性ID */
    attributeId: string;
  };

  type deleteApiDepartmentDepartmentIdParams = {
    departmentId: string;
  };

  type deleteApiDeviceDeviceIdParams = {
    /** 设备ID */
    deviceId: string;
  };

  type deleteApiProductProductIdParams = {
    /** 产品ID */
    productId: string;
  };

  type deleteApiUserUserIdParams = {
    /** 用户ID */
    userId: string;
  };

  type Department = {
    /** ID */
    id: string;
    /** 部门名称 */
    name: string;
    /** 标识符 */
    identifier: string;
    /** 上级ID */
    pid: any;
    /** 排序 */
    sort: number;
    /** 等级 */
    level: number;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人ID */
    createBy: any;
    /** 创建时间 */
    createTime: string;
    /** 描述 */
    description: any;
  };

  type Device = {
    /** 设备ID */
    id: string;
    /** 设备所属部门ID */
    departmentId: string;
    /** 产品ID */
    productId: string;
    /** 设备号 */
    deviceCode: string;
    /** 设备类型 */
    deviceType: string;
    /** 设备名称 */
    name: string;
    /** 设备状态 */
    status: string;
    /** 设备标签 */
    tags: any;
    /** 激活时间 */
    activationTime: any;
    /** 最后上线时间 */
    lastOnlineTime: any;
    /** 最后上报时间 */
    lastReportTime: any;
    /** 是否启用 */
    enabled: boolean;
    /** 是否删除 */
    deleted: boolean;
    /** 附加信息 */
    addition: any;
    /** 创建人ID */
    createBy: string;
    /** 创建时间 */
    createTime: string;
    /** 备注 */
    mark: any;
  };

  type DeviceAuth = {
    /** 凭证ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 认证方式 */
    authenticationMethod: string;
    /** 附加信息 */
    addition: {
      accessToken?: string;
      x509Pem?: string;
      mqttBasic: { clientId?: string; username?: string; password?: string };
      deviceCredential: { deviceKey?: string; deviceSecret?: string };
    };
    /** 过期时间 */
    expirationTime: any;
    /** 是否删除 */
    deleted: boolean;
    /** 创建时间 */
    createTime: string;
  };

  type DeviceAuthenticationMethod = Enum[];

  type DeviceNetworkingAway = Enum[];

  type DeviceType = Enum[];

  type EntityAction = Enum[];

  type Enum = {
    /** 枚举描述 */
    label: string;
    /** 枚举值 */
    value: string;
  };

  type FunctionModule = {
    /** 功能模块ID */
    id: string;
    /** 产品ID */
    productId: string;
    /** 功能模块名称 */
    name: string;
    /** 功能模块标识符 */
    identifier: string;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人ID */
    createBy: string;
    /** 创建时间 */
    createTime: string;
  };

  type getApi_openAPI_functionModuleModuleIdParams = {
    moduleId: string;
  };

  type getApiAttributeAttributeIdParams = {
    /** 属性ID */
    attributeId: string;
  };

  type getApiCommonSearchFieldParams = {
    /** 功能模块，取值：Device、User、Log、Notification、FunctionModule、Attribute、Product、Telemetry */
    module?: string;
  };

  type getApiDepartmentDepartmentIdParams = {
    /** 部门ID */
    departmentId: string;
  };

  type getApiDeviceDeviceIdAuthorizationParams = {
    /** 设备ID */
    deviceId: string;
  };

  type getApiProductProductIdParams = {
    /** 产品ID */
    productId: string;
  };

  type GlobalParam = {
    /** ID */
    id: string;
    /** 参数名称 */
    name: string;
    /** 标识符 */
    flag: string;
    /** 参数值 */
    value: string;
    /** 参数类型 */
    dataType: string;
    /** 是否为多值 */
    multivalued: boolean;
    /** 是否允许自己设置 */
    allowSelfSet: boolean;
    /** 是否启用 */
    enabled: boolean;
    /** 创建时间 */
    createTime: string;
    /** 备注 */
    mark: string;
  };

  type GlobalSettingDataType = Enum[];

  type Log = {
    /** 日志ID */
    id: string;
    /** 用户ID */
    userId: string;
    /** 会话ID */
    sessionId: string;
    /** 操作动作 */
    action: string;
    /** 操作对象类型 */
    objectType: string;
    /** 操作对象ID */
    objectId: string;
    /** 日志内容 */
    msg: any;
    /** 是否操作成功 */
    success: boolean;
    /** 附加信息 */
    addition: {
      ipAddress?: any;
      browser?: any;
      os?: any;
      location?: string;
      failureReason?: string;
      failureStackTrace?: string;
      objectFields?: {
        field?: string;
        fieldName?: string;
        beforeValue?: any;
        afterValue?: any;
        different?: boolean;
      }[];
    };
    /** 活动数据 */
    activityData: string;
    /** 操作时间 */
    createTime: string;
  };

  type LogAction = Enum[];

  type LoginResult = {
    /** 过期时间 */
    expires_in?: number;
    /** 消息 */
    message?: string;
    /** 是否成功 */
    success?: boolean;
    /** 认证token */
    token?: string;
  };

  type LogObjectType = Enum[];

  type NotificationMatchUserAway = Enum[];

  type NotificationPushAway = Enum[];

  type NotificationSeverity = Enum[];

  type NotificationSeverity1 = Enum[];

  type NotificationStatus = Enum[];

  type NotificationTypeIdentifier = Enum[];

  type OTAFileSource = Enum[];

  type OtaPackageDownloadProtocol = Enum[];

  type OtaPackageType = Enum[];

  type OtaUpgradeBatchMethod = Enum[];

  type OtaUpgradeBatchScope = Enum[];

  type OtaUpgradeBatchState = Enum[];

  type OtaUpgradeBatchType = Enum[];

  type OtaUpgradeProgressState = Enum[];

  type OtaUpgradeStrategyRetryInterval = Enum[];

  type PageResult = {
    /** 当前页码 */
    page: number;
    /** 每页条数 */
    pageSize: number;
    /** 总页数 */
    totalPages: number;
    /** 总条数 */
    totalRows: number;
    /** 分页数据列表 */
    result: any[];
  };

  type PlatformType = Enum[];

  type postApi_openAPI_functionModuleFilterParams = {
    /** 每页条数 */
    pageSize?: number;
    /** 当前页码 */
    page?: number;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
  };

  type postApi_openAPI_functionModuleModuleIdParams = {
    moduleId: string;
  };

  type postApiAttributeAttributeIdParams = {
    /** 属性ID */
    attributeId: string;
  };

  type postApiAttributeFilterParams = {
    /** 每页条数 */
    pageSize?: number;
    /** 当前页码 */
    page?: number;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
  };

  type postApiDepartmentDepartmentIdParams = {
    /** 部门ID */
    departmentId: string;
  };

  type postApiDeviceDeviceIdAuthorizationParams = {
    /** 设备ID */
    deviceId: string;
  };

  type postApiDeviceDeviceIdEnabledParams = {
    /** 设备ID */
    deviceId: string;
    /** 启用/禁用 */
    enabled?: boolean;
  };

  type postApiDeviceDeviceIdParams = {
    deviceId: string;
  };

  type postApiDeviceFilterParams = {
    /** 每页条数 */
    pageSize?: number;
    /** 当前页码 */
    page?: number;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
  };

  type postApiLogFilterParams = {
    /** 每页条数 */
    pageSize?: number;
    /** 当前页码 */
    page?: number;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
  };

  type postApiProductFilterParams = {
    /** 每页条数 */
    pageSize?: number;
    /** 当前页码 */
    page?: number;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
  };

  type postApiProductProductIdParams = {
    /** 产品ID */
    productId: string;
  };

  type postApiProductProductIdPublishParams = {
    /** 产品ID */
    productId: string;
  };

  type postApiProductProductIdRegenerateKeySecretParams = {
    /** 产品ID */
    productId: string;
  };

  type postApiTelemetryFilterParams = {
    /** 每页条数 */
    pageSize?: number;
    /** 当前页码 */
    page?: number;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
  };

  type postApiUserFilterParams = {
    /** 每页条数 */
    pageSize?: number;
    /** 当前页码 */
    page?: number;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
  };

  type postApiUserStatusUserIdParams = {
    /** 用户ID */
    userId: string;
    /** 启用true，禁用：false */
    enabled?: string;
  };

  type postApiUserUserIdParams = {
    /** 用户ID */
    userId: string;
  };

  type postAuthLoginParams = {
    /** 用户名 */
    username: string;
    /** 密码，16位小写md5加密后的值 */
    password: string;
  };

  type Product = {
    /** ID */
    id: string;
    /** 产品名称 */
    name: string;
    /** 产品Key */
    productKey: string;
    /** 产品Secret */
    productSecret: string;
    /** 设备类型 */
    deviceType: string;
    /** 联网方式 */
    networkingAway: string;
    /** 接入网关协议 */
    accessGatewayProtocol: any;
    /** 数据格式 */
    dataFormat: string;
    /** 鉴权方式 */
    authenticationMethod: string;
    /** 是否开启动态注册（仅适用一型一密） */
    dynamicRegistration: boolean;
    /** 状态 */
    status: string;
    /** 是否删除 */
    deleted: boolean;
    /** 描述 */
    description: any;
    /** 创建人 */
    createBy: string;
    /** 创建时间 */
    createTime: string;
  };

  type ProductStatus = Enum[];

  type ResponseResult = {
    /** 状态码 */
    code: string;
    /** 消息 */
    message: string;
    /** 响应数据 */
    data: any;
    additional: Record<string, any>;
  };

  type SearchField = {
    /** 检索字段模块 */
    searchFieldModule: string;
    /** 检索字段之间的匹配方式 */
    searchMatch: string;
    /** 检索字段列表 */
    searchFields?: { field?: string; operator?: string; value?: (string | number)[] }[];
  };

  type SearchFieldComponentType = Enum[];

  type SearchFieldMatch = Enum[];

  type SearchFieldModuleIdentifier = Enum[];

  type SearchFieldOperator = Enum[];

  type SearchFieldOptionDataSource = Enum[];

  type SearchFieldValueType = Enum[];

  type SessionStatus = Enum[];

  type SignAlgorithm = Enum[];

  type SignatureAlgorithm = Enum[];

  type Telemetry = {
    /** ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 遥测数据类型 */
    metricType: string;
    /** 遥测数据Key */
    metricKey: string;
    /** 遥测数据值，JSON字符串 */
    metricValue: string;
    /** 附加信息 */
    addition: { metadata: { dataType?: string } };
    /** 最后更新时间 */
    lastUpdateTimestamp: number;
    /** 创建时间 */
    createTime: string;
  };

  type User = {
    /** ID */
    id: string;
    /** 名称 */
    name: string;
    /** 账号 */
    account: string;
    /** 邮箱地址 */
    email: string;
    /** 手机号 */
    phone: string;
    /** 密码 */
    pwd?: any;
    /** 激活方式，SendUrlToEmail：向邮箱发送激活链接；ShowUrl：显示激活链接 */
    activateMethod: any;
    /** 记录令牌 */
    activateToken: any;
    /** 所属部门ID */
    departmentId: any;
    /** 身份，User：普通用户；SystemAdmin：系统管理员；TenantAdmin：租户管理员 */
    identity: string;
    /** 最后登录时间 */
    lastLoginTime: any;
    /** 最后修改密码时间 */
    lastChangePwdTime: any;
    /** 是否启用 */
    enabled: boolean;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人 */
    createBy: any;
    /** 创建时间 */
    createTime: string;
    /** 备注 */
    mark: any;
  };

  type UserActivateMethod = Enum[];

  type UserIdentity = Enum[];
}
