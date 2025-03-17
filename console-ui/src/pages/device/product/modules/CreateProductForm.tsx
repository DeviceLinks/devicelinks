import { FC } from 'react';
import { ActionType, ModalForm } from '@ant-design/pro-components';
import { PlusOutlined } from '@ant-design/icons';
import { Button, message } from 'antd';
import { useRequest } from '@umijs/max';
import { postApiProduct } from '@/services/device-links-console-ui/product';
interface CreateFormProps {
  reload?: ActionType['reload'];
}
const CreateProductForm: FC<CreateFormProps> = (props) => {
  const { reload } = props;
  const [messageApi, contextHolder] = message.useMessage();
  const { run, loading } = useRequest(postApiProduct, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('新增产品成功');
      reload?.();
    },
    onError: () => {
      messageApi?.error('新增产品失败,请重试');
    },
  });
  return (
    <>
      {contextHolder}
      <ModalForm
        modalProps={{ okButtonProps: { loading } }}
        title={'新增产品'}
        width="400px"
        trigger={
          <Button type="primary" icon={<PlusOutlined />}>
            新增产品
          </Button>
        }
        onFinish={async (value) => {
          await run(value as API.Product);
          return true;
        }}
      ></ModalForm>
    </>
  );
};

export default CreateProductForm;
