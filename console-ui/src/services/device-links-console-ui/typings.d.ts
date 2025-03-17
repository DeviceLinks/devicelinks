declare namespace API {
  type AccessGatewayProtocol = EnumItem[];

  type AlarmType = EnumItem[];

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
    createBy: string;
    /** 创建时间 */
    createTime: string;
    /** 描述 */
    description: string;
    /** 子属性列表 */
    childAttributes: Attribute[];
  };

  type AttributeDataType = EnumItem[];

  type CurrentUser = {
    user: User;
    department: Department;
  };

  type DataFormat = EnumItem[];

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

  type deleteApiDeviceAttributeDesiredAttributeIdDesiredParams = {
    /** 期望属性ID */
    desiredAttributeId: string;
  };

  type deleteApiDeviceDeviceIdParams = {
    /** 设备ID */
    deviceId: string;
  };

  type deleteApiDeviceDeviceIdTelemetryTelemetryIdParams = {
    /** 设备ID */
    deviceId: string;
    /** 遥测数据ID */
    telemetryId: string;
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
    pid: string;
    /** 排序 */
    sort: number;
    /** 等级 */
    level: number;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人ID */
    createBy: string;
    /** 创建时间 */
    createTime: string;
    /** 描述 */
    description: string;
  };

  type DesiredAttribute = {
    /** 期望属性ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 功能模块ID */
    moduleId: string;
    /** 定义属性ID */
    attributeId: any;
    /** 属性标识符 */
    identifier: string;
    /** 属性数据类型 */
    dataType: string;
    /** 期望版本 */
    version: number;
    /** 期望属性值，动态数据，格式不固定 */
    desiredValue: Record<string, any>;
    /** 状态 */
    status: string;
    /** 最后更新时间 */
    lastUpdateTime: string;
    /** 创建时间 */
    createTime: string;
    /** 过期时间 */
    expiredTime: any;
    /** 定义属性名称 */
    attributeName: any;
    /** 定义属性数据类型 */
    attributeDataType: any;
    /** 定义属性附加信息 */
    attributeAddition: any;
    /** 定义属性描述 */
    attributeDescription: any;
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

  type DeviceAttribute = {
    /** 上报属性ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 模块ID */
    moduleId: string;
    /** 定义属性ID */
    attributeId: any;
    /** 属性标识符 */
    identifier: string;
    /** 属性值，动态值，格式不固定 */
    value: Record<string, any>;
    /** 属性值来源 */
    value_source: string;
    /** 属性版本号 */
    version: number;
    /** 是否在设备状态页面展示 */
    displayOnStatusPage: boolean;
    /** 最后更新时间 */
    lastUpdateTime: string;
    /** 首次上报时间 */
    createTime: string;
    /** 定义属性名称 */
    attributeName: any;
    /** 定义属性数据类型 */
    attributeDataType: any;
    /** 定义属性附加信息 */
    attributeAddition: any;
    /** 定义属性描述 */
    attributeDescription: any;
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

  type DeviceAuthenticationMethod = EnumItem[];

  type DeviceDetailInfo = {
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
    /** 设备认证方式 */
    authenticationMethod: string;
    /** 功能模块OTA版本 */
    moduleVersion: string;
  };

  type DeviceNetworkingAway = EnumItem[];

  type DeviceShadow = {
    /** 影子ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 设备影子JSON数据 */
    shadowData: {
      module?: string;
      reported: { state?: Record<string, any>; metadata?: Record<string, any> };
      desired: { state?: Record<string, any>; metadata?: Record<string, any> };
    }[];
    /** 设备影子状态 */
    status: string;
    /** 最后更新时间 */
    lastUpdateTimestamp: string;
    /** 最后同步时间 */
    lastSyncTimestamp?: any;
    /** 影子创建时间 */
    createTime: string;
  };

  type DeviceStatus = EnumItem[];

  type DeviceType = EnumItem[];

  type EntityAction = EnumItem[];

  type Enum = {
    AttributeDataType: AttributeDataType;
    OtaUpgradeStrategyRetryInterval: OtaUpgradeStrategyRetryInterval;
    SearchFieldComponentType: SearchFieldComponentType;
    SessionStatus: SessionStatus;
    NotificationPushAway: NotificationPushAway;
    NotificationType: NotificationType;
    AlarmType: AlarmType;
    SearchFieldOperator: SearchFieldOperator;
    OtaUpgradeBatchMethod: OtaUpgradeBatchMethod;
    UserActivateMethod: UserActivateMethod;
    DeviceType: DeviceType;
    OtaUpgradeBatchScope: OtaUpgradeBatchScope;
    NotificationSeverity: NotificationSeverity1;
    ProductStatus: ProductStatus;
    DeviceNetworkingAway: DeviceNetworkingAway;
    SearchFieldValueType: SearchFieldValueType;
    PlatformType: PlatformType;
    AccessGatewayProtocol: AccessGatewayProtocol;
    DeviceStatus: DeviceStatus;
    OtaUpgradeStrategyType: OtaUpgradeStrategyType;
    SearchFieldOptionDataSource: SearchFieldOptionDataSource;
    SearchFieldModuleIdentifier: SearchFieldModuleIdentifier;
    OtaUpgradeBatchType: OtaUpgradeBatchType;
    GlobalSettingDataType: GlobalSettingDataType;
    SignatureAlgorithm: SignatureAlgorithm;
    NotificationMatchUserAway: NotificationMatchUserAway;
    LogAction: LogAction;
    DeviceAuthenticationMethod: DeviceAuthenticationMethod;
    NotificationTypeIdentifier: NotificationTypeIdentifier;
    SignAlgorithm: SignAlgorithm;
    DataFormat: DataFormat;
    OtaPackageType: OtaPackageType;
    OtaUpgradeProgressState: OtaUpgradeProgressState;
    OtaPackageDownloadProtocol: OtaPackageDownloadProtocol;
    EntityAction: EntityAction;
    NotificationStatus: NotificationStatus;
    SearchFieldMatch: SearchFieldMatch;
    OTAFileSource: OTAFileSource;
    LogObjectType: LogObjectType;
    OtaUpgradeBatchState: OtaUpgradeBatchState;
    UserIdentity: UserIdentity;
  };

  type EnumItem = {
    /** 枚举描述 */
    label: string;
    /** 枚举值 */
    value: string;
    /** 回显样式 */
    showStyle: string;
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
    /** 功能模块，取值：Device、User、Log、Notification、FunctionModule、Attribute、Product、Telemetry、DataChart */
    module?: string;
  };

  type getApiDepartmentDepartmentIdParams = {
    /** 部门ID */
    departmentId: string;
  };

  type getApiDeviceDeviceId_openAPI_functionModuleParams = {
    /** 设备ID */
    deviceId: string;
  };

  type getApiDeviceDeviceIdAuthorizationParams = {
    /** 设备ID */
    deviceId: string;
  };

  type getApiDeviceDeviceIdModuleModuleIdAttributeLatestParams = {
    /** 设备ID */
    deviceId: string;
    /** 功能模块ID */
    moduleId: string;
    /** 属性名称 */
    attributeName?: string;
    /** 属性标识符 */
    attributeIdentifier?: string;
  };

  type getApiDeviceDeviceIdParams = {
    /** 设备ID */
    deviceId: string;
  };

  type getApiDeviceDeviceIdShadowParams = {
    /** 设备ID */
    deviceId: string;
  };

  type getApiProductProductIdParams = {
    /** 产品ID */
    productId: string;
  };

  type getApiUserUserIdParams = {
    /** 用户ID */
    userId: string;
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

  type GlobalSettingDataType = EnumItem[];

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
    msg: string;
    /** 是否操作成功 */
    success: boolean;
    /** 附加信息 */
    addition: {
      ipAddress?: string;
      browser?: string;
      os?: string;
      location?: string;
      failureReason?: string;
      failureStackTrace?: string;
      objectFields?: {
        field?: string;
        fieldName?: string;
        beforeValue?: string;
        afterValue?: string;
        different?: boolean;
      }[];
    };
    /** 活动数据 */
    activityData: string;
    /** 操作时间 */
    createTime: string;
  };

  type LogAction = EnumItem[];

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

  type LogObjectType = EnumItem[];

  type NotificationMatchUserAway = EnumItem[];

  type NotificationPushAway = EnumItem[];

  type NotificationSeverity = EnumItem[];

  type NotificationSeverity1 = EnumItem[];

  type NotificationStatus = EnumItem[];

  type NotificationType = EnumItem[];

  type NotificationTypeIdentifier = EnumItem[];

  type OTAFileSource = EnumItem[];

  type OtaPackageDownloadProtocol = EnumItem[];

  type OtaPackageType = EnumItem[];

  type OtaUpgradeBatchMethod = EnumItem[];

  type OtaUpgradeBatchScope = EnumItem[];

  type OtaUpgradeBatchState = EnumItem[];

  type OtaUpgradeBatchType = EnumItem[];

  type OtaUpgradeProgressState = EnumItem[];

  type OtaUpgradeStrategyRetryInterval = EnumItem[];

  type OtaUpgradeStrategyType = EnumItem[];

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
    result: string[];
  };

  type PlatformType = EnumItem[];

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

  type postApiDeviceAttributeDesiredAttributeIdDesiredExtractParams = {
    /** 期望属性ID */
    desiredAttributeId: string;
  };

  type postApiDeviceAttributeDesiredAttributeIdDesiredParams = {
    /** 期望属性ID */
    desiredAttributeId: string;
  };

  type postApiDeviceAttributeDesiredFilterParams = {
    /** 每页条数 */
    pageSize?: number;
    /** 当前页码 */
    page?: number;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
  };

  type postApiDeviceAttributeDeviceAttributeIdExtractParams = {
    /** 设备属性ID */
    deviceAttributeId: string;
  };

  type postApiDeviceAttributeFilterParams = {
    /** 每页条数 */
    pageSize?: number;
    /** 当前页码 */
    page?: number;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
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

  type postApiDeviceDeviceIdModuleModuleIdAttributeDesiredParams = {
    /** 设备ID */
    deviceId: string;
    /** 设备所属产品下的功能模块ID */
    moduleId: string;
  };

  type postApiDeviceDeviceIdParams = {
    deviceId: string;
  };

  type postApiDeviceDeviceIdTelemetryParams = {
    /** 设备ID */
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

  type postApiDeviceTelemetryFilterParams = {
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
    accessGatewayProtocol: string;
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
    description: string;
    /** 创建人 */
    createBy: string;
    /** 创建时间 */
    createTime: string;
  };

  type ProductStatus = EnumItem[];

  type ResponseResult = {
    /** 状态码 */
    code: string;
    /** 消息 */
    message: string;
    data: any;
    additional: Record<string, any>;
    success: boolean;
  };

  type SearchField = {
    /** 检索字段模块 */
    searchFieldModule: string;
    /** 检索字段之间的匹配方式 */
    searchMatch: 'ANY' | 'ALL';
    /** 检索字段列表 */
    searchFields?: SearchFieldItem[];
  };

  type SearchFieldComponentType = EnumItem[];

  type SearchFieldItem = {
    /** 检索字段 */
    field: string;
    /** 检索运算符 */
    operator?:
      | 'EqualTo'
      | 'NotEqualTo'
      | 'GreaterThan'
      | 'GreaterThanOrEqualTo'
      | 'LessThan'
      | 'LessThanOrEqualTo'
      | 'In'
      | 'NotIn'
      | 'Like'
      | 'NotLike';
    /** 匹配检索值列表 */
    value?: string | (string | number)[] | number | boolean | any;
  };

  type SearchFieldMatch = EnumItem[];

  type SearchFieldModuleIdentifier = EnumItem[];

  type SearchFieldModuleItem = {
    /** 字段 */
    field: string;
    /** 字段中文描述 */
    fieldText: string;
    /** 字段值类型 */
    valueType:
      | 'STRING'
      | ' INTEGER'
      | ' FLOAT'
      | 'BOOLEAN'
      | 'DATE'
      | 'DATE_TIME'
      | 'TIME'
      | 'ENUM';
    /** 前端组件类型 */
    componentType: 'INPUT' | 'SELECT' | 'DATE' | 'DATE_TIME';
    /** 下拉框选项数据源，STATIC：静态数据；API：接口查询数据 */
    optionDataSource: string;
    /** 数据源为API时查询数据的编码 */
    optionApiDataCode: string;
    /** 数据源为STATIC时的静态数据 */
    optionStaticData: { label?: string; value?: string | boolean }[];
    /** 支持的运算法 */
    operators: {
      description?: string;
      value?:
        | 'EqualTo'
        | 'NotEqualTo'
        | 'GreaterThan'
        | 'GreaterThanOrEqualTo'
        | 'LessThan'
        | 'LessThanOrEqualTo'
        | 'In'
        | 'NotIn'
        | 'Like'
        | 'NotLike';
    }[];
  };

  type SearchFieldOperator = EnumItem[];

  type SearchFieldOptionDataSource = EnumItem[];

  type SearchFieldValueType = EnumItem[];

  type SessionStatus = EnumItem[];

  type shujutubiaomoxing = {
    /** 数据图表ID */
    id: string;
    /** 图表名称 */
    name: string;
    /** 图表位置 */
    targetLocation: string;
    /** 图表目标ID */
    targetId: string;
    /** 图表类型 */
    chartType: string;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人 */
    createBy: string;
    /** 创建时间 */
    createTime: string;
    /** 备注 */
    mark: any;
    fields: E695B0E68DAEE59BBEE8A1A8E5AD97E6AEB5E6A8A1E59E8B[];
  };

  type shujutubiaoziduanmoxing = {
    /** 数据图表字段ID */
    id: string;
    /** 图表ID */
    chartId: string;
    /** 业务字段类型 */
    fieldType: string;
    /** 业务字段ID */
    fieldId: string;
    /** 业务字段标识符 */
    fieldIdentifier: string;
    /** 业务字段描述 */
    fieldLabel: string;
    /** 创建时间 */
    createTime: string;
  };

  type SignAlgorithm = EnumItem[];

  type SignatureAlgorithm = EnumItem[];

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
    /** 是否在设备状态展示 */
    displayOnStatusPage: boolean;
    /** 最后更新时间 */
    lastUpdateTimestamp: number;
    /** 是否删除 */
    deleted: boolean;
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
    pwd?: string;
    /** 激活方式，SendUrlToEmail：向邮箱发送激活链接；ShowUrl：显示激活链接 */
    activateMethod: string;
    /** 记录令牌 */
    activateToken: string;
    /** 所属部门ID */
    departmentId: string;
    /** 身份，User：普通用户；SystemAdmin：系统管理员；TenantAdmin：租户管理员 */
    identity: 'User' | 'SystemAdmin' | 'TenantAdmin';
    lastLoginTime: string;
    lastChangePwdTime: string;
    /** 是否启用 */
    enabled: boolean;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人 */
    createBy: string;
    /** 创建时间 */
    createTime: string;
    /** 备注 */
    mark: string;
  };

  type UserActivateMethod = EnumItem[];

  type UserIdentity = EnumItem[];
}
