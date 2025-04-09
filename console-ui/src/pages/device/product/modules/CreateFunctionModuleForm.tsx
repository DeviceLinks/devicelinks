import { Button, message } from 'antd';
import { ModalForm, ProFormText } from '@ant-design/pro-components';
import { useRequest } from '@@/exports';
import { postApiFunctionModule } from '@/services/device-links-console-ui/functionModule';
import { PlusOutlined } from '@ant-design/icons';
import React from 'react';
type CreateFunctionModuleFormProps = {
  reload?: () => void;
  productId?: string;
};
const CreateFunctionModuleForm: React.FC<CreateFunctionModuleFormProps> = ({
  reload,
  productId,
}) => {
  const [messageApi, contextHolder] = message.useMessage();
  const { run, loading } = useRequest(postApiFunctionModule, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('新增功能模块成功');
      reload?.();
    },
  });
  return (
    <>
      {contextHolder}
      <ModalForm<API.FunctionModule>
        title={'新增模块'}
        modalProps={{ okButtonProps: { loading }, destroyOnClose: true }}
        width="520px"
        initialValues={{
          productId,
        }}
        trigger={
          <Button type="primary" icon={<PlusOutlined />}>
            新增模块
          </Button>
        }
        onFinish={async (values) => {
          await run(values);
          // 重置表单
          return true;
        }}
      >
        <ProFormText name="productId" hidden />
        <ProFormText
          name="name"
          label="模块名称"
          placeholder="请输入模块名称"
          fieldProps={{
            maxLength: 30,
          }}
          rules={[
            {
              required: true,
              message: '请输入模块名称',
            },
            {
              pattern: /^[\u4e00-\u9fa5a-zA-Z0-9][\u4e00-\u9fa5a-zA-Z0-9_-]*$/,
              message: '必须以汉字、字母或数字开头，后续可包含汉字、字母、数字、_ 或 -',
            },
          ]}
        />
        <ProFormText
          name="identifier"
          label="标识符"
          placeholder="请输入标识符"
          rules={[
            {
              required: true,
              message: '请输入标识符',
            },
            {
              pattern: /^[a-zA-Z][a-zA-Z0-9_]*$/,
              message: '必须以字母开头，后续可包含字母、数字或 _',
            },
          ]}
        ></ProFormText>
      </ModalForm>
    </>
  );
};
export default CreateFunctionModuleForm;
