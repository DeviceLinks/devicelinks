import { Button, message } from 'antd';
import { ModalForm } from '@ant-design/pro-components';
import { useRequest } from '@umijs/max';
import { postApiAttribute } from '@/services/device-links-console-ui/attribute';
import React from 'react';
import { PlusOutlined } from '@ant-design/icons';

type CreateAttributeFormProps = {
  reload?: () => void;
  initialValues?: {
    productId?: string;
    moduleId?: string;
  };
};
const CreateAttributeForm: React.FC<CreateAttributeFormProps> = ({ reload, initialValues }) => {
  const [messageApi, contextHolder] = message.useMessage();
  const { run, loading } = useRequest(postApiAttribute, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('新增属性成功');
      reload?.();
    },
  });
  return (
    <>
      {contextHolder}
      <ModalForm<API.AddAttributeRequest>
        title={'新增属性'}
        width="520px"
        modalProps={{ okButtonProps: { loading }, destroyOnClose: true }}
        initialValues={initialValues}
        trigger={
          <Button type="primary" icon={<PlusOutlined />}>
            新增属性
          </Button>
        }
        onFinish={async (values) => {
          await run(values);
        }}
      ></ModalForm>
    </>
  );
};
export default CreateAttributeForm;
