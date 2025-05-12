import { postApiDepartmentTreeFilter } from '@/services/device-links-console-ui/department';
import { postApiUser } from '@/services/device-links-console-ui/user';
import { PlusOutlined } from '@ant-design/icons';
import {
  ModalForm,
  ProForm,
  ProFormSelect,
  ProFormSwitch,
  ProFormText,
  ProFormTextArea,
  ProFormTreeSelect,
} from '@ant-design/pro-components';
import { useModel } from '@umijs/max';
import { Button, Form, message } from 'antd';
import { ButtonType } from 'antd/es/button';

interface Props {
  refresh: () => void;
  btnType?: ButtonType;
}

export default (prop: Props) => {
  const { refresh } = prop;
  const [form] = Form.useForm<API.User>();
  const { enums } = useModel('enumModel');
  const { UserActivateMethod } = enums!;
  return (
    <ModalForm<API.User>
      title="成员入职"
      trigger={
        <Button type={prop.btnType}>
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
      onFinish={async (values: any) => {
        await postApiUser(values);
        message.success('新增成功');
        refresh();
        return true;
      }}
    >
      <ProForm.Group>
        <ProFormText
          width="md"
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

        <ProFormText
          width="md"
          allowClear
          name="account"
          label="账号"
          placeholder="请输入账号"
          rules={[{ required: true, message: '请输入账号' }]}
        />
      </ProForm.Group>
      <ProForm.Group>
        <ProFormText
          width="md"
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
        />
        {/*<ProFormText*/}
        {/*  allowClear*/}
        {/*  width="md"*/}
        {/*  name="departmentId"*/}
        {/*  label="所属部门"*/}
        {/*  placeholder="请选择所属部门"*/}
        {/*/>*/}
        <ProFormTreeSelect
          allowClear
          name="departmentId"
          label="所属部门"
          width="md"
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
      </ProForm.Group>
      <ProFormSwitch name="switch" label="强制用户在首次登陆时修改密码" />
      <ProFormTextArea allowClear name="remark" label="备注" placeholder="请输入备注" />
    </ModalForm>
  );
};
