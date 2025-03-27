import {
  ProForm,
  ProFormSelect,
  ProFormSwitch,
  ProFormText,
  ProFormTextArea,
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
        width="md"
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
        width="md"
        name="username"
        label="用户名"
        allowClear
        tooltip="最长为 30 位"
        placeholder="请输入名称"
        rules={[{ required: true, message: '请输入用户名' }]}
      />

      <ProFormText
        width="md"
        allowClear
        name="account"
        label="账号"
        placeholder="请输入账号"
        disabled
      />
      <ProFormText width="md" allowClear disabled name="id" label="用户ID" placeholder="" />
      <ProFormText width="md" allowClear name="phone" label="手机号" placeholder="请输入手机号" />
      <ProFormSelect
        options={UserActivateMethod}
        width="md"
        allowClear
        name="activateMethod"
        label="激活方式"
        placeholder="请选择激活方式"
      />

      <ProFormText
        allowClear
        width="md"
        name="email"
        label="邮箱地址"
        placeholder="请输入邮箱地址"
      />
      <ProFormText
        allowClear
        width="md"
        name="departmentId"
        label="所属部门"
        placeholder="请选择所属部门"
        rules={[{ required: true, message: '请选择部门' }]}
      />
      <ProFormSwitch name="switch" label="强制用户在首次登陆时修改密码" />
      <ProFormText
        allowClear
        width="md"
        disabled
        name="lastLoginTime"
        label="最后登录时间"
        placeholder="尚未登录"
      />
      <ProFormText
        allowClear
        width="md"
        disabled
        name="lastChangePwdTime"
        label="最后修改密码时间"
        placeholder="暂未修改密码"
      />
      <ProFormText
        allowClear
        width="md"
        disabled
        name="createTime"
        label="新增时间"
        placeholder="新增时间"
      />

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
