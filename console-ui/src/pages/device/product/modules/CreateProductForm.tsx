import { FC } from 'react';
import {
  ActionType,
  ModalForm,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-components';
import { PlusOutlined } from '@ant-design/icons';
import { Button, message } from 'antd';
import { useModel, useRequest } from '@umijs/max';
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
  });
  const { enums } = useModel('enumModel');
  const {
    DeviceType,
    DeviceNetworkingAway,
    AccessGatewayProtocol,
    DataFormat,
    DeviceAuthenticationMethod,
  } = enums!;
  return (
    <>
      {contextHolder}
      <ModalForm
        modalProps={{ okButtonProps: { loading }, destroyOnClose: true }}
        title={'新增产品'}
        width="520px"
        trigger={
          <Button type="primary" icon={<PlusOutlined />}>
            新增产品
          </Button>
        }
        onFinish={async (value) => {
          await run(value as API.Product);
          // 重置表单
          return true;
        }}
      >
        <ProFormText
          label={'产品名称'}
          key={'name'}
          rules={[{ required: true, message: '请输入产品名称' }]}
          name={'name'}
        ></ProFormText>
        <ProFormSelect
          label={'设备类型'}
          name={'deviceType'}
          key={'deviceType'}
          rules={[{ required: true, message: '请选择设备类型' }]}
          options={DeviceType}
        ></ProFormSelect>
        <ProFormSelect
          label={'联网方式'}
          name={'networkingAway'}
          key={'networkingAway'}
          rules={[{ required: true, message: '请选择联网方式' }]}
          options={DeviceNetworkingAway}
        ></ProFormSelect>
        <ProFormSelect
          label={'接入网关协议'}
          name={'accessGatewayProtocol'}
          key={'accessGatewayProtocol'}
          rules={[{ required: true, message: '请选择接入网关协议' }]}
          options={AccessGatewayProtocol}
        ></ProFormSelect>
        <ProFormSelect
          label={'数据格式'}
          name={'dataFormat'}
          key={'dataFormat'}
          rules={[{ required: true, message: '请选择数据格式' }]}
          options={DataFormat}
        ></ProFormSelect>
        <ProFormSelect
          label={'鉴权方式'}
          name={'authenticationMethod'}
          key={'authenticationMethod'}
          rules={[{ required: true, message: '请选择鉴权方式' }]}
          options={DeviceAuthenticationMethod}
        ></ProFormSelect>
        <ProFormTextArea label={'描述'} name={'description'}></ProFormTextArea>
      </ModalForm>
    </>
  );
};

export default CreateProductForm;
