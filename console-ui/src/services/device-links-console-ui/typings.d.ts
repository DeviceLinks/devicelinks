declare namespace API {
  type deleteApiDepartmentDepartmentIdParams = {
    departmentId: string;
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

  type getApiCommonSearchFieldParams = {
    /** 功能模块，取值：Device、User、Log、Notification */
    module?: string;
  };

  type getApiDepartmentDepartmentIdParams = {
    /** 部门ID */
    departmentId: string;
  };

  type getApiUserParams = {
    /** 每页条数 */
    pageSize?: string;
    /** 当前页码 */
    page?: string;
    /** 排序属性，接口返回值"result"中的属性都可以作为排序字段 */
    sortProperty?: string;
    /** 排序顺序，ASC：正序；DESC：倒序 */
    sortDirection?: string;
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

  type PageResult = {
    /** 当前页码 */
    page: number;
    /** 每页条数 */
    pageSize: number;
    /** 总页数 */
    totalPages: number;
    /** 总条数 */
    totalRows: number;
    /** 用户列表 */
    result: string[];
  };

  type postApiDepartmentDepartmentIdParams = {
    /** 部门ID */
    departmentId: string;
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

  type SearchField = {
    /** 检索字段模块，User/Device/Log/Notification */
    searchFieldModule: string;
    /** 检索字段之间的匹配方式，ALL：所有；ANY：任意 */
    searchMatch: string;
    /** 检索字段列表 */
    searchFields?: { field?: string; operator?: string; value?: (string | number)[] }[];
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
    identity: string;
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
}
