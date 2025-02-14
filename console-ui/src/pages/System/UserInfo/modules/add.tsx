import { PlusOutlined } from '@ant-design/icons';
import {
  ModalForm,
  ProForm,
  ProFormSelect,
  ProFormSwitch,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { Button, Form, message } from 'antd';
import { addUserApi } from '../api';

export default () => {
  const [form] = Form.useForm<{ name: string; username: string; activateMethod: string }>();
  return (
    <ModalForm<{
      name: string;
      username: string;
      activateMethod: string;
    }>
      title="成员入职"
      trigger={
        <Button type="primary">
          <PlusOutlined />
          成员入职
        </Button>
      }
      form={form}
      autoFocusFirstInput
      modalProps={{
        destroyOnClose: true,
        onCancel: () => console.log('run'),
      }}
      submitTimeout={2000}
      onFinish={async (values) => {
        await addUserApi({ data: values });
        message.success('新增成功');
        return true;
      }}
    >
      <ProForm.Group>
        <ProFormText
          width="md"
          name="username"
          label="用户名"
          allowClear
          tooltip="最长为 30 位"
          placeholder="请输入名称"
        />

        <ProFormText width="md" allowClear name="account" label="账号" placeholder="请输入账号" />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormText width="md" allowClear name="phone" label="手机号" placeholder="请输入手机号" />
        <ProFormSelect
          request={async () => [
            {
              value: 'SendUrlToEmail',
              label: '发送激活邮件',
            },
            {
              value: 'ShowUrl',
              label: '显示激活链接',
            },
          ]}
          width="md"
          allowClear
          name="activateMethod"
          label="激活方式"
          placeholder="请选择激活方式"
        />
      </ProForm.Group>
      <ProForm.Group>
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
        />
      </ProForm.Group>
      <ProFormSwitch name="switch" label="强制用户在首次登陆时修改密码" />
      <ProFormTextArea allowClear name="remark" label="备注" placeholder="请输入备注" />
    </ModalForm>
  );
};
