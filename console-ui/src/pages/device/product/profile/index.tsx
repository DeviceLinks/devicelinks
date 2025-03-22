import React from 'react';
import {
  PageContainer,
  ProForm,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
  ProSkeleton,
  ProCard,
  ProFormRadio,
  ProList,
} from '@ant-design/pro-components';
import { useModel, useParams, useRequest } from '@umijs/max';
import {
  deleteApiProductProductId,
  getApiProductProductId,
  postApiProductProductId,
  postApiProductProductIdPublish,
} from '@/services/device-links-console-ui/product';
import { DeleteOutlined, DownOutlined, UploadOutlined } from '@ant-design/icons';
import { Button, Dropdown, MenuProps, message, Modal, Progress, Space, Tag } from 'antd';
import { Typography } from 'antd';
import { postApiOpenApiFunctionModuleFilter } from '@/services/device-links-console-ui/functionModule';
const { Paragraph } = Typography;
type ProductBaseInfoProps = {
  productInfo: API.Product | undefined;
  submit: (values: API.Product) => Promise<void>;
  enums: API.Enum;
};
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
type ProductFunctionModuleInfoProps = {
  productId: string | undefined;
};
const ProductFunctionModuleInfo: React.FC<ProductFunctionModuleInfoProps> = ({ productId }) => {
  const { data: functionModuleList } = useRequest(() =>
    postApiOpenApiFunctionModuleFilter({
      searchFieldModule: 'FunctionModule',
      searchMatch: 'ALL',
      searchFields: [
        {
          field: 'productId',
          operator: 'EqualTo',
          value: productId,
        },
      ],
    } as API.SearchField),
  );
  const data = [
    '语雀的天空',
    'Ant Design',
    '蚂蚁金服体验科技',
    'TechUI',
    'TechUI 2.0',
    'Bigfish',
    'Umi',
    'Ant Design Pro',
  ].map((item) => ({
    title: item,
    subTitle: <Tag color="#5BD8A6">语雀专栏</Tag>,
    actions: [<a key="run">邀请</a>, <a key="delete">删除</a>],
    avatar: 'https://gw.alipayobjects.com/zos/antfincdn/UCSiy1j6jx/xingzhuang.svg',
    content: (
      <div
        style={{
          flex: 1,
        }}
      >
        <div
          style={{
            width: 200,
          }}
        >
          <div>发布中</div>
          <Progress percent={80} />
        </div>
      </div>
    ),
  }));
  return (
    <ProCard split="vertical">
      <ProCard
        colSpan={{
          xs: 24,
          sm: 24,
          md: 6,
          xl: 5,
          lg: 5,
        }}
        ghost
      >
        <ProList<API.FunctionModule>
          rowKey={'id'}
          itemCardProps={{
            size: 'small',
          }}
          rowSelection={{
            type: 'radio',
            selections: false,
          }}
          dataSource={functionModuleList}
          metas={{
            title: {
              dataIndex: 'name',
            },
          }}
          toolbar={{
            search: {
              placeholder: '请输入功能模块名称',
              onSearch: (value) => {
                console.log(value);
              },
            },
            actions: [
              <Button type="primary" key="primary">
                新增模块
              </Button>,
            ],
          }}
        ></ProList>
      </ProCard>
      <ProCard></ProCard>
    </ProCard>
  );
};
const ProductProfile: React.FC = () => {
  const { productId } = useParams();
  const [messageApi, contextHolder] = message.useMessage();
  const { enums, getEnumByValue } = useModel('enumModel');
  /**
   *  获取产品详情
   */
  const {
    data: productInfo,
    loading,
    refresh: productRefresh,
  } = useRequest(() => getApiProductProductId({ productId } as API.getApiProductProductIdParams), {
    refreshDeps: [productId],
  });
  /**
   * 当前页面需要的枚举
   */
  const { ProductStatus } = enums;
  /**
   *  更新产品
   */
  const { run: updateProduct } = useRequest(postApiProductProductId, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('更新产品成功');
      productRefresh();
    },
  });
  /**
   * 删除产品
   */
  const { run: deleteProduct } = useRequest(deleteApiProductProductId, {
    manual: true,
    onSuccess: () => {
      messageApi
        ?.success({
          content: '删除产品成功',
          duration: 1,
        })
        .then(() => {
          history.back();
        });
    },
  });
  /**
   * 发布产品
   */
  const { run: publishProduct } = useRequest(postApiProductProductIdPublish, {
    manual: true,
    onSuccess: () => {
      messageApi?.success('发布产品成功');
      productRefresh();
    },
  });
  const handleMenuClick: MenuProps['onClick'] = (e) => {
    if (e.key === 'delete') {
      Modal.confirm({
        title: '提示',
        content: '确定要删除该产品吗？',
        okText: '确定',
        cancelText: '取消',
        onOk: async () => {
          await deleteProduct({
            productId: productInfo?.id,
          } as API.deleteApiProductProductIdParams);
        },
      });
    } else if (e.key === 'publish') {
      Modal.confirm({
        title: '提示',
        content: '确定要发布该产品吗？',
        okText: '确定',
        cancelText: '取消',
        onOk: async () => {
          await publishProduct({
            productId: productInfo?.id,
          } as API.postApiProductProductIdPublishParams);
        },
      });
    }
  };
  const menuItems: MenuProps['items'] = [
    ...(productInfo && productInfo?.status !== 'Published'
      ? [
          {
            key: 'publish',
            icon: <UploadOutlined />,
            label: '发布',
          },
        ]
      : []),
    {
      key: 'delete',
      icon: <DeleteOutlined />,
      label: '删除',
      danger: true,
    },
  ];

  if (loading) {
    return <ProSkeleton type="descriptions" />;
  }
  return (
    <PageContainer
      tags={
        productInfo && (
          <Tag color={getEnumByValue(ProductStatus, productInfo.status)?.showStyle.toLowerCase()}>
            {getEnumByValue(ProductStatus, productInfo.status)?.label}
          </Tag>
        )
      }
      tabList={[
        {
          key: 'profile',
          tab: '产品信息',
          children: (
            <ProductBasicInfoForm
              productInfo={productInfo}
              enums={enums}
              submit={async (values) => {
                await updateProduct(
                  {
                    productId: productInfo?.id,
                  } as API.postApiProductProductIdParams,
                  {
                    ...productInfo,
                    ...values,
                  },
                );
              }}
            ></ProductBasicInfoForm>
          ),
        },
        {
          key: 'function',
          tab: '功能模块',
          children: <ProductFunctionModuleInfo productId={productId}></ProductFunctionModuleInfo>,
        },
        {
          key: 'device',
          tab: '设备列表',
        },
      ]}
      title={`产品名称：${productInfo?.name || '-'}`}
      content={
        <Paragraph
          copyable={{
            text: `${productInfo?.id}`,
          }}
        >
          ID：{productInfo?.id || '-'}
        </Paragraph>
      }
      extra={
        <Dropdown menu={{ items: menuItems, onClick: handleMenuClick }}>
          <Button type={'primary'}>
            <Space>
              更多
              <DownOutlined />
            </Space>
          </Button>
        </Dropdown>
      }
    >
      {contextHolder}
    </PageContainer>
  );
};
export default ProductProfile;
