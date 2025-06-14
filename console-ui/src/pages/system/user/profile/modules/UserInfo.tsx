import { postApiDepartmentTreeFilter } from '@/services/device-links-console-ui/department';
import {
  ProForm,
  ProFormSelect,
  ProFormSwitch,
  ProFormText,
  ProFormTextArea,
  ProFormTreeSelect,
} from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import { Typography } from 'antd';

const { Paragraph } = Typography;
export default ({
  initialValues,
  onSubmit,
}: {
  initialValues: API.User | undefined;
  onSubmit: (values: API.UpdateUserRequest) => void;
}) => {
  // 转换初始值（过滤不可修改字段）

  const { enums } = useModel('enumModel');
  const { UserActivateMethod } = enums!;

  return (
    <ProForm<API.UpdateUserRequest>
      onFinish={(values) => {
        onSubmit(values);
      }}
      initialValues={initialValues}
      grid={true}
      rowProps={{
        gutter: 16,
      }}
      colProps={{
        xs: 24,
        sm: 24,
        md: 12,
        xl: 8,
        lg: 8,
      }}
    >
      <ProFormText
        name="id"
        label="用户ID"
        allowClear
        disabled
        fieldProps={{
          suffix: (
            <Paragraph
              style={{
                margin: 0,
              }}
              copyable={{
                text: initialValues?.id,
              }}
            ></Paragraph>
          ),
        }}
      />
      <ProFormText
        name="name"
        label="用户名"
        allowClear
        tooltip="最长为 30 位"
        placeholder="请输入名称"
        rules={[
          {
            required: true,
            pattern: /^[\u4e00-\u9fa5a-zA-Z0-9][\u4e00-\u9fa5a-zA-Z0-9_-]*$/,
            message: '首字符需为中文/英文/数字，后续可包含下划线或短横线',
          },
        ]}
      />

      <ProFormText allowClear name="account" label="账号" placeholder="请输入账号" disabled />
      <ProFormText allowClear disabled name="id" label="用户ID" placeholder="" />
      <ProFormText
        allowClear
        name="phone"
        label="手机号"
        placeholder="请输入手机号"
        rules={[
          {
            pattern: /^1[3-9]\d{9}$|^$/,
            message: '手机号格式不正确',
          },
        ]}
      />
      <ProFormSelect
        options={UserActivateMethod}
        allowClear
        name="activateMethod"
        label="激活方式"
        placeholder="请选择激活方式"
        rules={[{ required: true, message: '请选择激活方式' }]}
      />

      <ProFormText allowClear name="email" label="邮箱地址" placeholder="请输入邮箱地址" />

      <ProFormTreeSelect
        allowClear
        name="departmentId"
        label="所属部门"
        rules={[{ required: true, message: '请选择部门' }]}
        placeholder="请选择所属部门"
        fieldProps={{
          fieldNames: {
            label: 'name',
            value: 'id',
          },
        }}
        request={async () => {
          const { data } = await postApiDepartmentTreeFilter({
            searchFieldModule: 'Department',
            searchMatch: 'ALL',
            searchFields: [
              {
                field: 'deleted',
                operator: 'EqualTo',
                value: false,
              },
            ],
          });
          return data;
        }}
      />
      <ProFormSwitch name="switch" label="强制用户在首次登陆时修改密码" />
      <ProFormText
        allowClear
        disabled
        name="lastLoginTime"
        label="最后登录时间"
        placeholder="尚未登录"
      />
      <ProFormText
        allowClear
        disabled
        name="lastChangePwdTime"
        label="最后修改密码时间"
        placeholder="暂未修改密码"
      />
      <ProFormText allowClear disabled name="createTime" label="新增时间" placeholder="新增时间" />

      <ProFormTextArea
        colProps={{
          span: 24,
        }}
        allowClear
        name="remark"
        label="备注"
        placeholder="请输入备注"
      />
    </ProForm>
  );
};
