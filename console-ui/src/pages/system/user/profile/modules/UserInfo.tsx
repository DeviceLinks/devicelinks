import { postApiUserUserId } from '@/services/device-links-console-ui/user';
import {
  ProForm,
  ProFormSelect,
  ProFormSwitch,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import { Form, message } from 'antd';

interface Props {
  userInfo: API.User;
}

export default (props: Props) => {
  const { enums } = useModel('enumModel');
  const { UserActivateMethod } = enums!;
  const { userInfo } = props;
  const [form] = Form.useForm<API.User>();

  const handleFinish = async (value: API.User) => {
    await postApiUserUserId({ userId: value.id }, value);
    message.success('保存成员信息成功');
  };
  return (
    <ProForm
      form={form}
      onFinish={async (values: API.User) => handleFinish(values)}
      initialValues={userInfo}
    >
      <ProForm.Group>
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
          rules={[{ required: true, message: '请输入账号' }]}
          disabled
        />
        <ProFormText width="md" allowClear disabled name="id" label="用户ID" placeholder="" />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormText
          width="md"
          allowClear
          name="phone"
          label="手机号"
          placeholder="请输入手机号"
          rules={[{ required: true, message: '请输入手机号' }]}
        />
        <ProFormSelect
          options={UserActivateMethod}
          width="md"
          allowClear
          name="activateMethod"
          label="激活方式"
          placeholder="请选择激活方式"
          rules={[{ required: true, message: '请选择激活方式' }]}
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormText
          allowClear
          width="md"
          name="email"
          label="邮箱地址"
          placeholder="请输入邮箱地址"
          rules={[{ required: true, message: '请输入邮箱地址' }]}
        />
        <ProFormText
          allowClear
          width="md"
          name="departmentId"
          label="所属部门"
          placeholder="请选择所属部门"
          rules={[{ required: true, message: '' }]}
        />
      </ProForm.Group>
      <ProFormSwitch name="switch" label="强制用户在首次登陆时修改密码" />
      <ProFormTextArea
        allowClear
        name="remark"
        label="备注"
        placeholder="请输入备注"
        rules={[{ required: true, message: '请输入备注' }]}
      />
    </ProForm>
  );
};
