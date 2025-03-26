import { Button, message } from 'antd';
import { ModalForm, ProFormText } from '@ant-design/pro-components';
import { useRequest } from '@@/exports';
import {
  getApiOpenApiFunctionModuleModuleId,
  postApiOpenApiFunctionModuleModuleId,
} from '@/services/device-links-console-ui/functionModule';
import { EditOutlined } from '@ant-design/icons';
import React from 'react';
type UpdateFunctionModuleFormProps = {
  reload?: () => void;
  moduleId: string;
};
const UpdateFunctionModuleForm: React.FC<UpdateFunctionModuleFormProps> = ({
  reload,
  moduleId,
}) => {
  const [messageApi, contextHolder] = message.useMessage();
  const { run, loading } = useRequest(postApiOpenApiFunctionModuleModuleId, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('编辑功能模块成功');
      reload?.();
    },
  });
  const fetchData = async () => {
    const { data } = await getApiOpenApiFunctionModuleModuleId({
      moduleId,
    });
    return data!;
  };
  return (
    <>
      {contextHolder}
      <ModalForm<API.FunctionModule>
        title={'编辑模块'}
        request={fetchData}
        modalProps={{ okButtonProps: { loading }, destroyOnClose: true }}
        width="520px"
        trigger={<Button icon={<EditOutlined />} key="edit" type={'text'}></Button>}
        onFinish={async (values) => {
          await run(
            {
              moduleId,
            },
            values,
          );
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
          disabled
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
export default UpdateFunctionModuleForm;
