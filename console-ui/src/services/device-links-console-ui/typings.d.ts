declare namespace API {
  type AccessGatewayProtocol = EnumItem[];

  type AddAttributeRequest = {
    /** 产品ID */
    productId: string;
    /** 功能模块ID */
    moduleId: string;
    info: AttributeInfoRequest;
    childAttributes: AttributeInfoRequest[];
  };

  type AddDataChartRequest = {
    /** 数据图表名称 */
    chartName: string;
    /** 数据图表类型 */
    chartType: string;
    /** 目标ID */
    targetId: string;
    /** 目标位置 */
    targetLocation: string;
    /** 备注 */
    mark?: string;
    /** 数据图表字段列表 */
    fields: AddDataChartRequestChartField[];
  };

  type AddDataChartRequestChartField = {
    /** 字段ID */
    fieldId: string;
    /** 字段类型 */
    fieldType: string;
    /** 字段描述 */
    fieldLabel: string;
  };

  type AddDepartmentRequest = {
    /** 部门名称 */
    name: string;
    /** 部门标识符 */
    identifier: string;
    /** 上级部门ID */
    pid?: number;
    /** 部门排序值 */
    sort?: number;
    /** 部门等级 */
    level?: number;
    /** 描述 */
    description?: string;
  };

  type AddDeviceDesiredAttributeRequest = {
    /** 属性知晓类型 */
    knowType: string;
    /** 属性ID，对于已经在功能模块下定义的属性需要传递属性ID，已知属性类型必填 */
    attributeId?: string;
    /** 属性标识符，未知属性必填 */
    identifier?: string;
    /** 数据类型，未知属性必填 */
    dataType?: string;
    /** 属性期望值，根据数据类型传递，ARRAY类型直接传递"[xx,xxx,xx]"即可 */
    desiredValue: string;
  };

  type AddDeviceProfileRequest = {
    /** 配置文件名称 */
    name: string;
    /** 产品ID */
    productId?: string;
    /** 固件ID */
    firmwareId?: string;
    /** 软件ID */
    softwareId?: string;
    /** 日志附加配置，选择产品后可以为产品下定义的功能模块定义日志等级以及上报间隔，如果不选择产品只允许设置"default"功能模块的日志等级以及上报间隔 */
    logAddition?: DeviceProfileLogAddition | null;
    /** 预配置附加信息 */
    provisionAddition: DeviceProfileProvisionAddition;
    /** 自定义扩展Json配置 */
    extension?: string;
    /** 描述 */
    description?: string;
  };

  type AddDeviceRequest = {
    /** 产品ID */
    productId: string;
    /** 部门ID */
    departmentId: string;
    /** 设备类型 */
    deviceType: string;
    /** 设备唯一名称 */
    deviceName: string;
    /** 备注名称 */
    noteName?: string;
    /** 标签列表 */
    tags?: string[];
    /** 备注 */
    mark?: string;
    /** 鉴权方式 */
    authenticationMethod: string;
    /** 鉴权附加信息 */
    authenticationAddition: DeviceAuthenticationAddition;
  };

  type AddDeviceTagRequest = {
    /** 设备标签名称 */
    name: string;
  };

  type AddDeviceTelemetryRequest = {
    /** 遥测数据标识符 */
    identifier: string;
    /** 遥测数据值 */
    value: string;
    /** 数据类型 */
    dataType?: string;
  };

  type AddFunctionModuleRequest = {
    /** 功能模块产品ID */
    productId: string;
    /** 功能模块名称 */
    name: string;
    /** 功能模块标识符 */
    identifier: string;
  };

  type AddProductRequest = {
    /** 产品名称 */
    name: string;
    /** 设备类型 */
    deviceType: string;
    /** 网络方式 */
    networkingAway?: string;
    /** 接入网关协议 */
    accessGatewayProtocol?: string;
    /** 数据格式 */
    dataFormat: string;
    /** 认证方式 */
    authenticationMethod: string;
    /** 描述 */
    description?: string;
  };

  type AddUserRequest = {
    /** 用户名称 */
    username: string;
    /** 用户账号 */
    account: string;
    /** 激活方式 */
    activateMethod: string;
    /** 邮箱地址 */
    email: string;
    /** 手机号 */
    phone: string;
    /** 部门ID */
    departmentId: string;
    /** 描述 */
    mark: string;
  };

  type AlarmType = EnumItem[];

  type ApiResponse = {
    /** 状态码 */
    code: string;
    /** 消息 */
    message: string;
    data: string;
    additional: Record<string, any>;
    /** 是否成功，前端使用 */
    success?: boolean;
  };

  type Attribute = {
    /** 属性ID */
    id: string;
    /** 产品ID */
    productId: string;
    /** 功能模块ID */
    moduleId: string;
    /** 上级ID */
    pid?: string;
    /** 属性名称 */
    name: string;
    /** 属性标识符 */
    identifier: string;
    /** 数据类型 */
    dataType: string;
    /** 附加信息 */
    addition?: AttributeAddition;
    /** 是否可写，可写的属性可用于期望属性设置 */
    writable: boolean;
    /** 是否系统内置 */
    system: boolean;
    /** 属性范围 */
    scope: string;
    /** 是否启用 */
    enabled: boolean;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人ID */
    createBy: string;
    /** 创建时间 */
    createTime: string;
    /** 描述 */
    description?: string;
  };

  type AttributeAddition = {
    /** 数据单位ID */
    unitId?: string;
    /** 数据步长 */
    step?: number;
    /** 数据长度 */
    dataLength?: number;
    /** 数据范围 */
    valueRange?: { min?: number; max?: number };
    /** 数据值映射集合 */
    valueMap?: string;
    /** 数组、集合元素的数量 */
    elementCount?: number;
    /** 单个元素的数据类型 */
    elementDataType?: string;
  };

  type AttributeDataType = EnumItem[];

  type AttributeDTO = {
    /** 属性ID */
    id: string;
    /** 产品ID */
    productId: string;
    /** 功能模块ID */
    moduleId: string;
    /** 上级ID */
    pid?: string;
    /** 属性名称 */
    name: string;
    /** 属性标识符 */
    identifier: string;
    /** 数据类型 */
    dataType: string;
    /** 附加信息 */
    addition?: AttributeAddition;
    /** 是否可写，可写的属性可用于期望属性设置 */
    writable: boolean;
    /** 是否系统内置 */
    system: boolean;
    /** 属性范围 */
    scope: string;
    /** 是否启用 */
    enabled: boolean;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人ID */
    createBy: string;
    /** 创建时间 */
    createTime: string;
    /** 描述 */
    description?: string;
    childAttributes: Attribute;
  };

  type AttributeInfoRequest = {
    /** 属性名称 */
    name: string;
    /** 属性标识符 */
    identifier: string;
    /** 数据类型 */
    dataType: string;
    /** 附加信息 */
    addition: AttributeAddition;
    /** 是否可写（可写的属性可用于设置期望值） */
    writable?: string;
    /** 属性描述 */
    description?: string;
  };

  type AttributeUnit = {
    /** 单位ID */
    id: string;
    /** 属性单位名称 */
    name: string;
    /** 属性单位 */
    symbol: string;
    /** 是否启用 */
    enabled: boolean;
    /** 是否删除 */
    deleted: boolean;
    /** 创建时间 */
    createTime: string;
  };

  type BatchSetDeviceProfileRequest = {
    /** 批量设置方式 */
    batchSetAway: string;
    /** 设备ID列表，仅适用SpecifyDevice方式 */
    deviceIds?: string[];
    /** 设备标签列表，仅适用于DeviceTag方式 */
    deviceTags?: string[];
    /** 设备类型，仅适用于DeviceType方式 */
    deviceType?: string;
  };

  type CurrentLoginUser = {
    user: User;
    department: Department;
  };

  type DataChart = {
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
    mark?: string;
  };

  type DataChartDTO = {
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
    mark?: string;
    fields: DataChartField[];
  };

  type DataChartField = {
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

  type DataFormat = EnumItem[];

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

  type deleteApiDeviceProfileProfileIdParams = {
    /** 设备配置文件ID */
    profileId: string;
  };

  type deleteApiDeviceTagTagIdParams = {
    /** 设备标签ID */
    tagId: string;
  };

  type deleteApiFunctionModuleModuleIdParams = {
    /** 功能模块ID */
    moduleId: string;
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
    pid?: string;
    /** 排序 */
    sort: number;
    /** 等级 */
    level: number;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人ID */
    createBy?: string;
    /** 创建时间 */
    createTime: string;
    /** 描述 */
    description?: string;
  };

  type Device = {
    /** 设备ID */
    id: string;
    /** 设备所属部门ID */
    departmentId: string;
    /** 产品ID */
    productId: string;
    /** 设备唯一名称 */
    deviceName: string;
    /** 设备类型 */
    deviceType: string;
    /** 设备名称 */
    name?: string;
    /** 设备状态 */
    status: string;
    /** 设备标签 */
    tags?: string;
    /** IP地址 */
    ipAddress?: string;
    /** 激活时间 */
    activationTime?: string;
    /** 最后上线时间 */
    lastOnlineTime?: string;
    /** 最后上报时间 */
    lastReportTime?: string;
    /** 是否启用 */
    enabled: boolean;
    /** 是否删除 */
    deleted: boolean;
    /** 附加信息 */
    addition?: string;
    /** 创建人ID */
    createBy: string;
    /** 创建时间 */
    createTime: string;
    /** 备注 */
    mark?: string;
  };

  type DeviceAttribute = {
    /** 上报属性ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 模块ID */
    moduleId: string;
    /** 定义属性ID */
    attributeId?: string;
    /** 属性标识符 */
    identifier: string;
    /** 属性值，动态值，格式不固定 */
    value: Record<string, any>;
    /** 属性值来源 */
    value_source: string;
    /** 属性版本号 */
    version: number;
    /** 最后更新时间 */
    lastUpdateTime: string;
    /** 首次上报时间 */
    createTime: string;
  };

  type DeviceAttributeDesired = {
    /** 期望属性ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 功能模块ID */
    moduleId: string;
    /** 定义属性ID */
    attributeId?: string;
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
    expiredTime?: string;
    /** 是否删除 */
    deleted: boolean;
  };

  type DeviceAttributeDesiredDTO = {
    /** 期望属性ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 功能模块ID */
    moduleId: string;
    /** 定义属性ID */
    attributeId?: string;
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
    expiredTime?: string;
    /** 是否删除 */
    deleted: boolean;
    /** 属性名称 */
    attributeName: string;
    /** 属性数据类型 */
    attributeDataType: string;
    /** 附加信息 */
    attributeAddition: AttributeAddition;
    /** 属性描述 */
    attributeDescription?: string;
  };

  type DeviceAttributeDTO = {
    /** 上报属性ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 模块ID */
    moduleId: string;
    /** 定义属性ID */
    attributeId?: string;
    /** 属性标识符 */
    identifier: string;
    /** 属性值，动态值，格式不固定 */
    value: Record<string, any>;
    /** 属性值来源 */
    value_source: string;
    /** 属性版本号 */
    version: number;
    /** 最后更新时间 */
    lastUpdateTime: string;
    /** 首次上报时间 */
    createTime: string;
    /** 属性名称 */
    attributeName: string;
    /** 属性数据类型 */
    attributeDataType: string;
    /** 附加信息 */
    attributeAddition: AttributeAddition;
    /** 属性描述 */
    attributeDescription?: string;
  };

  type DeviceAuthentication = {
    /** 凭证ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 认证方式 */
    authenticationMethod: string;
    /** 鉴权附加信息 */
    addition: DeviceAuthenticationAddition;
    /** 过期时间 */
    expirationTime?: string;
    /** 是否删除 */
    deleted: boolean;
    /** 创建时间 */
    createTime: string;
  };

  type DeviceAuthenticationAddition = {
    /** 请求令牌 */
    staticToken: string;
    /** X.509 pem证书 */
    x509Pem: string;
    /** mqtt基础认证 */
    mqttBasic: { clientId: string; username: string; password: string };
    /** 设备凭证 */
    dynamicToken: { deviceSecret: string; secretGenerateTime: string };
  };

  type DeviceAuthenticationMethod = EnumItem[];

  type DeviceDTO = {
    /** 设备ID */
    id: string;
    /** 设备所属部门ID */
    departmentId: string;
    /** 产品ID */
    productId: string;
    /** 设备唯一名称 */
    deviceName: string;
    /** 设备类型 */
    deviceType: string;
    /** 设备名称 */
    name?: string;
    /** 设备状态 */
    status: string;
    /** 设备标签 */
    tags?: string;
    /** IP地址 */
    ipAddress?: string;
    /** 激活时间 */
    activationTime?: string;
    /** 最后上线时间 */
    lastOnlineTime?: string;
    /** 最后上报时间 */
    lastReportTime?: string;
    /** 是否启用 */
    enabled: boolean;
    /** 是否删除 */
    deleted: boolean;
    /** 附加信息 */
    addition?: string;
    /** 创建人ID */
    createBy: string;
    /** 创建时间 */
    createTime: string;
    /** 备注 */
    mark?: string;
    /** 认证方式 */
    authenticationMethod: string;
    /** 各个功能模块的版本 */
    moduleVersion: Record<string, any>;
  };

  type DeviceNetworkingAway = EnumItem[];

  type DeviceProfile = {
    /** 设备配置ID */
    id: string;
    /** 名称 */
    name: string;
    /** 是否为默认配置文件 */
    defaultProfile: boolean;
    /** 固件ID */
    firmwareId?: string;
    /** 软件ID */
    softwareId?: string;
    /** 日志附加信息 */
    logAddition?: DeviceProfileLogAddition | null;
    /** 预配置附加信息 */
    provisionAddition: DeviceProfileProvisionAddition;
    /** 报警附加信息 */
    alarmAddition?: string;
    /** 自定义扩展附加JSON配置 */
    extension?: string;
    /** 创建人 */
    createBy?: string;
    /** 创建时间 */
    createTime: string;
    /** 是否删除 */
    deleted: boolean;
    /** 描述 */
    description?: string;
  };

  type DeviceProfileLogAddition = {
    /** 日志等级配置列表 */
    logLevels: {
      default: { level: string; reportIntervalSeconds: number };
      xxx: { level: string; reportIntervalSeconds: number };
    };
  };

  type DeviceProfileProvisionAddition = {
    /** 预配置设备Key */
    provisionDeviceKey: string;
    /** 预配置设备Secret */
    provisionDeviceSecret: string;
    /** 动态令牌有效期时长，单位：秒 */
    dynamicTokenValidSeconds: number;
  };

  type DeviceShadow = {
    /** 影子ID */
    id: string;
    /** 设备ID */
    deviceId: string;
    /** 设备影子JSON数据 */
    shadowData: {
      module: string;
      reported: { state: Record<string, any>; metadata: Record<string, any> };
      desired: { state: Record<string, any>; metadata: Record<string, any> };
    }[];
    /** 设备影子状态 */
    status: string;
    /** 最后更新时间 */
    lastUpdateTimestamp?: string;
    /** 最后同步时间 */
    lastSyncTimestamp?: string;
    /** 影子创建时间 */
    createTime: string;
  };

  type DeviceStatus = EnumItem[];

  type DeviceTag = {
    /** 标签ID */
    id: string;
    /** 标签名称 */
    name: string;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人 */
    createBy: string;
    /** 创建时间 */
    createTime: string;
  };

  type DeviceTelemetry = {
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
    addition?: TelemetryAddition;
    /** 最后更新时间 */
    lastUpdateTimestamp: number;
    /** 是否删除 */
    deleted: boolean;
    /** 创建时间 */
    createTime: string;
  };

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
    SearchFieldOperator: SearchFieldOperator2;
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

  type ExtractUnknownDesiredAttributeRequest = {
    /** 属性名称 */
    attributeName: string;
    /** 附加信息 */
    addition: AttributeAddition;
    /** 描述 */
    description?: string;
  };

  type ExtractUnknownDeviceAttributeRequest = {
    /** 属性名称 */
    attributeName: string;
    /** 附加信息 */
    addition: AttributeAddition;
    /** 数据类型 */
    dataType: string;
    /** 是否可写 */
    writable?: boolean;
    /** 描述 */
    description?: string;
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

  type getApiDeviceProfileProfileIdParams = {
    /** 设备配置文件ID */
    profileId: string;
  };

  type getApiFunctionModuleModuleIdParams = {
    moduleId: string;
  };

  type getApiProductProductIdParams = {
    /** 产品ID */
    productId: string;
  };

  type getApiUserUserIdParams = {
    /** 用户ID */
    userId: string;
  };

  type GlobalSetting = {
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
    msg?: string;
    /** 是否操作成功 */
    success: boolean;
    /** 附加信息 */
    addition?: LogAddition;
    /** 活动数据 */
    activityData?: string;
    /** 操作时间 */
    createTime: string;
  };

  type LogAction = EnumItem[];

  type LogAddition = {
    /** 操作IP地址 */
    ipAddress?: string;
    /** 操作浏览器 */
    browser?: string;
    /** 操作系统 */
    os?: string;
    /** 操作地 */
    location?: string;
    /** 错误原因 */
    failureReason?: string;
    /** 错误堆栈 */
    failureStackTrace?: string;
    /** 对象操作之前值的JSON字符串 */
    beforeObject: string;
    /** 对象操作之后值的JSON字符串 */
    afterObject: string;
  };

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

  type PaginationQuery = {
    /** 每页条数 */
    pageSize: number;
    /** 当前页码 */
    page: number;
    /** 排序字段 */
    sortProperty: string;
    /** 排序方式 */
    sortDirection: string;
  };

  type PlatformType = EnumItem[];

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

  type postApiDeviceProfileFilterParams = {
    /** 每页条数 */
    pageSize?: number;
    /** 当前页码 */
    page?: number;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
  };

  type postApiDeviceProfileProfileIdBatchSetParams = {
    /** 设备配置文件ID */
    profileId: string;
  };

  type postApiDeviceProfileProfileIdParams = {
    /** 设备配置文件ID */
    profileId: string;
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

  type postApiFunctionModuleModuleIdParams = {
    moduleId: string;
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
    networkingAway?: string;
    /** 接入网关协议 */
    accessGatewayProtocol?: string;
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
    description?: string;
    /** 创建人 */
    createBy: string;
    /** 创建时间 */
    createTime: string;
  };

  type ProductStatus = EnumItem[];

  type putApiDeviceProfileProfileIdBasicInfoParams = {
    /** 设备配置文件ID */
    profileId: string;
  };

  type putApiDeviceProfileProfileIdExtensionParams = {
    /** 设备配置文件ID */
    profileId: string;
  };

  type putApiDeviceProfileProfileIdLogAdditionParams = {
    /** 日志配置文件ID */
    profileId: string;
  };

  type putApiDeviceProfileProfileIdProvisionAdditionParams = {
    /** 设备配置文件ID */
    profileId: string;
  };

  type RegenerateKeySecretResponse = {
    /** 产品ID */
    productId: string;
    /** 产品名称 */
    productName: string;
    /** 产品Key */
    productKey: string;
    /** 产品Secret */
    productSecret: string;
  };

  type SearchField = {
    /** 字段标识符 */
    field: string;
    /** 字段描述 */
    fieldText: string;
    /** 值类型 */
    valueType: 'STRING' | 'INTEGER' | 'FLOAT' | 'BOOLEAN' | 'DATE' | 'DATE_TIME' | 'TIME' | 'ENUM';
    /** 前端组件类型 */
    componentType: 'INPUT' | 'SELECT' | 'DATE' | 'DATE_TIME';
    /** 选项数据源 */
    optionDataSource: string;
    optionApiDataCode?: string;
    /** 选项静态数据列表 */
    optionStaticData?: SearchFieldOptionData[];
    enumClass?: string;
    /** 运算符列表 */
    operators: SearchFieldOperator[];
    /** 是否必须传递 */
    required?: boolean;
  };

  type SearchFieldComponentType = EnumItem[];

  type SearchFieldFilter = {
    /** 检索字段 */
    field: string;
    /** 运算符 */
    operator?:
      | 'EqualTo'
      | 'NotEqualTo'
      | 'GreaterThan'
      | 'GreaterThanOrEqualTo'
      | 'LessThan'
      | 'LessThanOrEqualTo'
      | ' In'
      | 'NotIn'
      | 'Like'
      | 'NotLike';
    /** 字段值 */
    value?: string | number | boolean | (string | number | boolean)[];
  };

  type SearchFieldMatch = EnumItem[];

  type SearchFieldModuleIdentifier = EnumItem[];

  type SearchFieldOperator = {
    /** 描述 */
    description: string;
    /** 运算符标识符ß */
    value:
      | 'EqualTo'
      | 'NotEqualTo'
      | 'GreaterThan'
      | 'GreaterThanOrEqualTo'
      | 'LessThan'
      | 'LessThanOrEqualTo'
      | ' In'
      | 'NotIn'
      | 'Like'
      | 'NotLike';
  };

  type SearchFieldOperator2 = EnumItem[];

  type SearchFieldOptionData = {
    /** 字段选项描述 */
    label: string;
    /** 字段描述值 */
    value: string;
  };

  type SearchFieldOptionDataSource = EnumItem[];

  type SearchFieldQuery = {
    /** 检索字段所属模块，详见：SearchFieldModuleIdentifier枚举定义 */
    searchFieldModule: string;
    /** 运算符匹配方式 */
    searchMatch: 'ANY' | 'ALL';
    /** 检索字段列表 */
    searchFields: SearchFieldFilter[];
  };

  type SearchFieldValueType = EnumItem[];

  type SessionStatus = EnumItem[];

  type SignAlgorithm = EnumItem[];

  type SignatureAlgorithm = EnumItem[];

  type TelemetryAddition = {
    metadata: { dataType: string };
  };

  type UpdateAttributeRequest = {
    info: AttributeInfoRequest;
    childAttributes: AttributeInfoRequest[];
  };

  type UpdateDepartmentRequest = {
    /** 部门名称 */
    name: string;
    /** 上级部门ID */
    pid?: number;
    /** 排序 */
    sort?: number;
    /** 描述 */
    description?: string;
  };

  type UpdateDeviceAuthorizationRequest = {
    /** 设备鉴权方式 */
    authenticationMethod: string;
    /** 鉴权附加信息 */
    authenticationAddition: DeviceAuthenticationAddition;
  };

  type UpdateDeviceDesiredAttributeRequest = {
    /** 属性数据类型，未知属性可传递 */
    dataType?: string;
    /** 期望属性值 */
    desiredValue: string;
  };

  type UpdateDeviceProfileBasicInfoRequest = {
    /** 配置文件名称 */
    name: string;
    /** 产品ID */
    productId?: string;
    /** 固件OTA ID */
    firmwareId?: string;
    /** 软件OTA ID */
    softwareId?: string;
    /** 描述 */
    description?: string;
  };

  type UpdateDeviceProfileExtensionRequest = {
    /** 扩展配置，必须传递JSON格式字符串，不可以为空，可以传递"{}" */
    extension: string;
  };

  type UpdateDeviceProfileLogAdditionRequest = {
    /** 日志附加信息 */
    logAddition: DeviceProfileLogAddition;
  };

  type UpdateDeviceProfileProvisionAdditionRequest = {
    /** 预配置附加信息 */
    provisionAddition: DeviceProfileProvisionAddition;
  };

  type UpdateDeviceProfileRequest = {
    /** 配置文件名称 */
    name: string;
    /** 产品ID */
    productId?: string;
    /** 固件ID */
    firmwareId?: string;
    /** 软件ID */
    softwareId?: string;
    /** 日志附加配置，选择产品后可以为产品下定义的功能模块定义日志等级以及上报间隔，如果不选择产品只允许设置"default"功能模块的日志等级以及上报间隔 */
    logAddition?: DeviceProfileLogAddition | null;
    /** 预配置附加信息 */
    provisionAddition: DeviceProfileProvisionAddition;
    /** 自定义扩展Json配置 */
    extension?: string;
    /** 描述 */
    description?: string;
  };

  type UpdateDeviceRequest = {
    /** 设备备注名称 */
    noteName?: string;
    /** 设备标签列表 */
    tags?: string[];
    /** 备注信息 */
    mark?: string;
  };

  type UpdateFunctionModuleRequest = {
    /** 功能模块名称 */
    name: string;
    /** 功能模块标识符 */
    identifier: string;
  };

  type UpdateProductRequest = {
    /** 产品名称 */
    name: string;
    /** 设备类型 */
    deviceType: string;
    /** 网络方式 */
    networkingAway?: string;
    /** 接入网关协议 */
    accessGatewayProtocol?: string;
    /** 数据格式 */
    dataFormat: string;
    /** 认证方式 */
    authenticationMethod: string;
    /** 描述 */
    description?: string;
  };

  type UpdateUserRequest = {
    /** 用户名称 */
    username: string;
    /** 邮箱地址 */
    email?: string;
    /** 部门ID */
    departmentId: string;
    /** 手机号 */
    phone?: string;
    /** 备注 */
    mark?: string;
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
    phone?: string;
    /** 密码 */
    pwd?: string;
    /** 激活方式，SendUrlToEmail：向邮箱发送激活链接；ShowUrl：显示激活链接 */
    activateMethod: string;
    /** 记录令牌 */
    activateToken?: string;
    /** 所属部门ID */
    departmentId?: string;
    /** 身份，User：普通用户；SystemAdmin：系统管理员；TenantAdmin：租户管理员 */
    identity: 'User' | 'SystemAdmin' | 'TenantAdmin';
    /** 最后登录时间 */
    lastLoginTime?: string;
    /** 最后修改密码时间 */
    lastChangePwdTime?: string;
    /** 是否启用 */
    enabled: boolean;
    /** 是否删除 */
    deleted: boolean;
    /** 创建人 */
    createBy?: string;
    /** 创建时间 */
    createTime: string;
    /** 备注 */
    mark?: string;
  };

  type UserActivateMethod = EnumItem[];

  type UserIdentity = EnumItem[];
}
