declare namespace API {
  
        type AccessGatewayProtocol = [object Object];
  
        type AlarmType = [object Object];
  
        type Attribute =
          {
              /** 属性ID */
                'id': string;
              /** 产品ID */
                'productId': string;
              /** 功能模块ID */
                'moduleId': string;
              /** 上级ID */
                'pid': string;
              /** 属性名称 */
                'name': string;
              /** 属性标识符 */
                'identifier': string;
              /** 数据类型 */
                'dataType': string;
              /** 附加信息 */
                'addition': { 'unitId'?: string; 'step'?: number; 'dataLength'?: number; 'valueRange': { 'min'?: number; 'max'?: number; }; 'valueMap'?: Record<string, any>; };
              /** 是否启用 */
                'enabled': boolean;
              /** 是否删除 */
                'deleted': boolean;
              /** 创建人ID */
                'createBy': string;
              /** 创建时间 */
                'createTime': string;
              /** 描述 */
                'description': string;
              /** 子属性列表 */
                'childAttributes': Attribute[];
          }
        
  
        type AttributeDataType = [object Object];
  
        type CurrentUser =
          {
                'user': User;
                'department': Department;
          }
        
  
        type DataFormat = [object Object];
  
        type deleteApi_openAPI_functionModuleModuleIdParams =
          {
              /** 功能模块ID */
                'moduleId': string;
          }
        
  
        type deleteApiAttributeAttributeIdParams =
          {
              /** 属性ID */
                'attributeId': string;
          }
        
  
        type deleteApiDepartmentDepartmentIdParams =
          {
                'departmentId': string;
          }
        
  
        type deleteApiDeviceDeviceIdParams =
          {
              /** 设备ID */
                'deviceId': string;
          }
        
  
        type deleteApiProductProductIdParams =
          {
              /** 产品ID */
                'productId': string;
          }
        
  
        type deleteApiUserUserIdParams =
          {
              /** 用户ID */
                'userId': string;
          }
        
  
        type Department =
          {
              /** ID */
                'id': string;
              /** 部门名称 */
                'name': string;
              /** 标识符 */
                'identifier': string;
              /** 上级ID */
                'pid': string;
              /** 排序 */
                'sort': number;
              /** 等级 */
                'level': number;
              /** 是否删除 */
                'deleted': boolean;
              /** 创建人ID */
                'createBy': string;
              /** 创建时间 */
                'createTime': string;
              /** 描述 */
                'description': string;
          }
        
  
        type Device =
          {
              /** 设备ID */
                'id': string;
              /** 设备所属部门ID */
                'departmentId': string;
              /** 产品ID */
                'productId': string;
              /** 设备号 */
                'deviceCode': string;
              /** 设备类型 */
                'deviceType': string;
              /** 设备名称 */
                'name': string;
              /** 设备状态 */
                'status': string;
              /** 设备标签 */
                'tags': any;
              /** 激活时间 */
                'activationTime': any;
              /** 最后上线时间 */
                'lastOnlineTime': any;
              /** 最后上报时间 */
                'lastReportTime': any;
              /** 是否启用 */
                'enabled': boolean;
              /** 是否删除 */
                'deleted': boolean;
              /** 附加信息 */
                'addition': any;
              /** 创建人ID */
                'createBy': string;
              /** 创建时间 */
                'createTime': string;
              /** 备注 */
                'mark': any;
          }
        
  
        type DeviceAuth =
          {
              /** 凭证ID */
                'id': string;
              /** 设备ID */
                'deviceId': string;
              /** 认证方式 */
                'authenticationMethod': string;
              /** 附加信息 */
                'addition': { 'accessToken'?: string; 'x509Pem'?: string; 'mqttBasic': { 'clientId'?: string; 'username'?: string; 'password'?: string; }; 'deviceCredential': { 'deviceKey'?: string; 'deviceSecret'?: string; }; };
              /** 过期时间 */
                'expirationTime': any;
              /** 是否删除 */
                'deleted': boolean;
              /** 创建时间 */
                'createTime': string;
          }
        
  
        type DeviceAuthenticationMethod = [object Object];
  
        type DeviceNetworkingAway = [object Object];
  
        type DeviceType = [object Object];
  
        type EntityAction = [object Object];
  
        type Enum = Record<string, any>;
  
        type FunctionModule =
          {
              /** 功能模块ID */
                'id': string;
              /** 产品ID */
                'productId': string;
              /** 功能模块名称 */
                'name': string;
              /** 功能模块标识符 */
                'identifier': string;
              /** 是否删除 */
                'deleted': boolean;
              /** 创建人ID */
                'createBy': string;
              /** 创建时间 */
                'createTime': string;
          }
        
  
        type getApi_openAPI_functionModuleModuleIdParams =
          {
                'moduleId': string;
          }
        
  
        type getApiAttributeAttributeIdParams =
          {
              /** 属性ID */
                'attributeId': string;
          }
        
  
        type getApiCommonSearchFieldParams =
          {
              /** 功能模块，取值：Device、User、Log、Notification、FunctionModule、Attribute、Product、Telemetry */
                'module'?: string;
          }
        
  
        type getApiDepartmentDepartmentIdParams =
          {
              /** 部门ID */
                'departmentId': string;
          }
        
  
        type getApiDeviceDeviceIdAuthorizationParams =
          {
              /** 设备ID */
                'deviceId': string;
          }
        
  
        type getApiProductProductIdParams =
          {
              /** 产品ID */
                'productId': string;
          }
        
  
        type GlobalParam =
          {
              /** ID */
                'id': string;
              /** 参数名称 */
                'name': string;
              /** 标识符 */
                'flag': string;
              /** 参数值 */
                'value': string;
              /** 参数类型 */
                'dataType': string;
              /** 是否为多值 */
                'multivalued': boolean;
              /** 是否允许自己设置 */
                'allowSelfSet': boolean;
              /** 是否启用 */
                'enabled': boolean;
              /** 创建时间 */
                'createTime': string;
              /** 备注 */
                'mark': string;
          }
        
  
        type GlobalSettingDataType = [object Object];
  
        type Log =
          {
              /** 日志ID */
                'id': string;
              /** 用户ID */
                'userId': string;
              /** 会话ID */
                'sessionId': string;
              /** 操作动作 */
                'action': string;
              /** 操作对象类型 */
                'objectType': string;
              /** 操作对象ID */
                'objectId': string;
              /** 日志内容 */
                'msg': string;
              /** 是否操作成功 */
                'success': boolean;
              /** 附加信息 */
                'addition': { 'ipAddress'?: string; 'browser'?: string; 'os'?: string; 'location'?: string; 'failureReason'?: string; 'failureStackTrace'?: string; 'objectFields'?: { 'field'?: string; 'fieldName'?: string; 'beforeValue'?: string; 'afterValue'?: string; 'different'?: boolean; }[]; };
              /** 活动数据 */
                'activityData': string;
              /** 操作时间 */
                'createTime': string;
          }
        
  
        type LogAction = [object Object];
  
        type LoginResult =
          {
              /** 过期时间 */
                'expires_in'?: number;
              /** 消息 */
                'message'?: string;
              /** 是否成功 */
                'success'?: boolean;
              /** 认证token */
                'token'?: string;
          }
        
  
        type LogObjectType = [object Object];
  
        type NotificationMatchUserAway = [object Object];
  
        type NotificationPushAway = [object Object];
  
        type NotificationSeverity = [object Object];
  
        type NotificationSeverity1 = [object Object];
  
        type NotificationStatus = [object Object];
  
        type NotificationTypeIdentifier = [object Object];
  
        type OTAFileSource = [object Object];
  
        type OtaPackageDownloadProtocol = [object Object];
  
        type OtaPackageType = [object Object];
  
        type OtaUpgradeBatchMethod = [object Object];
  
        type OtaUpgradeBatchScope = [object Object];
  
        type OtaUpgradeBatchState = [object Object];
  
        type OtaUpgradeBatchType = [object Object];
  
        type OtaUpgradeProgressState = [object Object];
  
        type OtaUpgradeStrategyRetryInterval = [object Object];
  
        type PageResult =
          {
              /** 当前页码 */
                'page': number;
              /** 每页条数 */
                'pageSize': number;
              /** 总页数 */
                'totalPages': number;
              /** 总条数 */
                'totalRows': number;
              /** 分页数据列表 */
                'result': string[];
          }
        
  
        type PlatformType = [object Object];
  
        type postApi_openAPI_functionModuleFilterParams =
          {
              /** 每页条数 */
                'pageSize'?: number;
              /** 当前页码 */
                'page'?: number;
              /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
                'sortProperty'?: string;
              /** 排序顺序，ASC：正序；DESC：倒序 */
                'sortDirection'?: string;
          }
        
  
        type postApi_openAPI_functionModuleModuleIdParams =
          {
                'moduleId': string;
          }
        
  
        type postApiAttributeAttributeIdParams =
          {
              /** 属性ID */
                'attributeId': string;
          }
        
  
        type postApiAttributeFilterParams =
          {
              /** 每页条数 */
                'pageSize'?: number;
              /** 当前页码 */
                'page'?: number;
              /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
                'sortProperty'?: string;
              /** 排序顺序，ASC：正序；DESC：倒序 */
                'sortDirection'?: string;
          }
        
  
        type postApiDepartmentDepartmentIdParams =
          {
              /** 部门ID */
                'departmentId': string;
          }
        
  
        type postApiDeviceDeviceIdAuthorizationParams =
          {
              /** 设备ID */
                'deviceId': string;
          }
        
  
        type postApiDeviceDeviceIdEnabledParams =
          {
              /** 设备ID */
                'deviceId': string;
              /** 启用/禁用 */
                'enabled'?: boolean;
          }
        
  
        type postApiDeviceDeviceIdParams =
          {
                'deviceId': string;
          }
        
  
        type postApiDeviceFilterParams =
          {
              /** 每页条数 */
                'pageSize'?: number;
              /** 当前页码 */
                'page'?: number;
              /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
                'sortProperty'?: string;
              /** 排序顺序，ASC：正序；DESC：倒序 */
                'sortDirection'?: string;
          }
        
  
        type postApiLogFilterParams =
          {
              /** 每页条数 */
                'pageSize'?: number;
              /** 当前页码 */
                'page'?: number;
              /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
                'sortProperty'?: string;
              /** 排序顺序，ASC：正序；DESC：倒序 */
                'sortDirection'?: string;
          }
        
  
        type postApiProductFilterParams =
          {
              /** 每页条数 */
                'pageSize'?: number;
              /** 当前页码 */
                'page'?: number;
              /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
                'sortProperty'?: string;
              /** 排序顺序，ASC：正序；DESC：倒序 */
                'sortDirection'?: string;
          }
        
  
        type postApiProductProductIdParams =
          {
              /** 产品ID */
                'productId': string;
          }
        
  
        type postApiProductProductIdPublishParams =
          {
              /** 产品ID */
                'productId': string;
          }
        
  
        type postApiProductProductIdRegenerateKeySecretParams =
          {
              /** 产品ID */
                'productId': string;
          }
        
  
        type postApiTelemetryFilterParams =
          {
              /** 每页条数 */
                'pageSize'?: number;
              /** 当前页码 */
                'page'?: number;
              /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
                'sortProperty'?: string;
              /** 排序顺序，ASC：正序；DESC：倒序 */
                'sortDirection'?: string;
          }
        
  
        type postApiUserFilterParams =
          {
              /** 每页条数 */
                'pageSize'?: number;
              /** 当前页码 */
                'page'?: number;
              /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
                'sortProperty'?: string;
              /** 排序顺序，ASC：正序；DESC：倒序 */
                'sortDirection'?: string;
          }
        
  
        type postApiUserStatusUserIdParams =
          {
              /** 用户ID */
                'userId': string;
              /** 启用true，禁用：false */
                'enabled'?: string;
          }
        
  
        type postApiUserUserIdParams =
          {
              /** 用户ID */
                'userId': string;
          }
        
  
        type postAuthLoginParams =
          {
              /** 用户名 */
                'username': string;
              /** 密码，16位小写md5加密后的值 */
                'password': string;
          }
        
  
        type Product =
          {
              /** ID */
                'id': string;
              /** 产品名称 */
                'name': string;
              /** 产品Key */
                'productKey': string;
              /** 产品Secret */
                'productSecret': string;
              /** 设备类型 */
                'deviceType': string;
              /** 联网方式 */
                'networkingAway': string;
              /** 接入网关协议 */
                'accessGatewayProtocol': string;
              /** 数据格式 */
                'dataFormat': string;
              /** 鉴权方式 */
                'authenticationMethod': string;
              /** 是否开启动态注册（仅适用一型一密） */
                'dynamicRegistration': boolean;
              /** 状态 */
                'status': string;
              /** 是否删除 */
                'deleted': boolean;
              /** 描述 */
                'description': string;
              /** 创建人 */
                'createBy': string;
              /** 创建时间 */
                'createTime': string;
          }
        
  
        type ProductStatus = [object Object];
  
        type ResponseResult =
          {
              /** 状态码 */
                'code': string;
              /** 消息 */
                'message': string;
                'data': string;
                'additional': Record<string, any>;
          }
        
  
        type SearchField =
          {
              /** 检索字段模块 */
                'searchFieldModule': string;
              /** 检索字段之间的匹配方式 */
                'searchMatch': string;
              /** 检索字段列表 */
                'searchFields'?: ({ 'field'?: string; 'operator'?: string; 'value'?: (string | number)[]; })[];
          }
        
  
        type SearchFieldComponentType = [object Object];
  
        type SearchFieldMatch = [object Object];
  
        type SearchFieldModuleIdentifier = [object Object];
  
        type SearchFieldOperator = [object Object];
  
        type SearchFieldOptionDataSource = [object Object];
  
        type SearchFieldValueType = [object Object];
  
        type SessionStatus = [object Object];
  
        type SignAlgorithm = [object Object];
  
        type SignatureAlgorithm = [object Object];
  
        type Telemetry =
          {
              /** ID */
                'id': string;
              /** 设备ID */
                'deviceId': string;
              /** 遥测数据类型 */
                'metricType': string;
              /** 遥测数据Key */
                'metricKey': string;
              /** 遥测数据值，JSON字符串 */
                'metricValue': string;
              /** 附加信息 */
                'addition': { 'metadata': { 'dataType'?: string; }; };
              /** 最后更新时间 */
                'lastUpdateTimestamp': number;
              /** 创建时间 */
                'createTime': string;
          }
        
  
        type User =
          {
              /** ID */
                'id': string;
              /** 名称 */
                'name': string;
              /** 账号 */
                'account': string;
              /** 邮箱地址 */
                'email': string;
              /** 手机号 */
                'phone': string;
              /** 密码 */
                'pwd'?: string;
              /** 激活方式，SendUrlToEmail：向邮箱发送激活链接；ShowUrl：显示激活链接 */
                'activateMethod': string;
              /** 记录令牌 */
                'activateToken': string;
              /** 所属部门ID */
                'departmentId': string;
              /** 身份，User：普通用户；SystemAdmin：系统管理员；TenantAdmin：租户管理员 */
                'identity': string;
                'lastLoginTime': string;
                'lastChangePwdTime': string;
              /** 是否启用 */
                'enabled': boolean;
              /** 是否删除 */
                'deleted': boolean;
              /** 创建人 */
                'createBy': string;
              /** 创建时间 */
                'createTime': string;
              /** 备注 */
                'mark': string;
          }
        
  
        type UserActivateMethod = [object Object];
  
        type UserIdentity = [object Object];
  
}
