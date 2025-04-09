import React from 'react';
import { ProForm, ProFormSelect, ProFormText, ProFormTextArea } from '@ant-design/pro-components';
import { Typography } from 'antd';
const { Paragraph } = Typography;
type ProductBaseInfoProps = {
  productInfo: API.Product | undefined;
  submit: (values: API.Product) => Promise<void>;
  enums: API.Enum;
};
/**
 * 产品基本信息
 * @param productInfo
 * @param submit
 * @param enums
 * @constructor
 */
const ProductBasicInfoForm: React.FC<ProductBaseInfoProps> = ({ productInfo, submit, enums }) => {
  const { DeviceType, ProductStatus, DeviceNetworkingAway, DeviceAuthenticationMethod } = enums;
  return (
    <ProForm<API.Product>
      initialValues={productInfo}
      onFinish={async (values) => {
        await submit(values);
      }}
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
        label="产品ID"
        disabled
        fieldProps={{
          suffix: (
            <Paragraph
              style={{
                margin: 0,
              }}
              copyable={{
                text: productInfo?.id,
              }}
            ></Paragraph>
          ),
        }}
      ></ProFormText>
      <ProFormText name="name" label="产品名称" required></ProFormText>
      <ProFormText
        name="productKey"
        label="产品Key"
        disabled
        fieldProps={{
          suffix: (
            <Paragraph
              style={{
                margin: 0,
              }}
              copyable={{
                text: productInfo?.productKey,
              }}
            ></Paragraph>
          ),
        }}
      ></ProFormText>
      <ProFormText
        name="productSecret"
        label="产品Secret"
        disabled
        fieldProps={{
          suffix: (
            <Paragraph
              style={{
                margin: 0,
              }}
              copyable={{
                text: productInfo?.productSecret,
              }}
            ></Paragraph>
          ),
        }}
      ></ProFormText>
      <ProFormSelect
        name="deviceType"
        label="设备类型"
        options={DeviceType}
        disabled={productInfo?.status === 'Published'}
      ></ProFormSelect>
      <ProFormSelect
        name="networkingAway"
        label="联网方式"
        options={DeviceNetworkingAway}
        disabled={productInfo?.status === 'Published'}
      ></ProFormSelect>
      {productInfo?.authenticationMethod === 'ProductCredential' && (
        <ProFormSelect
          name="authenticationMethod"
          label="鉴权方式"
          options={DeviceAuthenticationMethod}
        ></ProFormSelect>
      )}
      <ProFormSelect name="status" label="状态" options={ProductStatus} disabled></ProFormSelect>
      <ProFormText name="createTime" label="创建时间" disabled></ProFormText>
      <ProFormTextArea
        name="description"
        colProps={{
          span: 24,
        }}
        label="描述"
      ></ProFormTextArea>
    </ProForm>
  );
};
export default ProductBasicInfoForm;
