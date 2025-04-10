import React, { useState } from 'react';
import RcResizeObserver from 'rc-resize-observer';
import { PageContainer, ProSkeleton } from '@ant-design/pro-components';
import { useModel, useParams, useRequest } from '@umijs/max';
import {
  deleteApiProductProductId,
  getApiProductProductId,
  postApiProductProductId,
  postApiProductProductIdPublish,
} from '@/services/device-links-console-ui/product';
import { DeleteOutlined, DownOutlined, UploadOutlined } from '@ant-design/icons';
import { Button, Dropdown, MenuProps, message, Modal, Space, Tag } from 'antd';
import { Typography } from 'antd';
import ProductBasicInfoForm from '@/pages/device/product/modules/ProductBasicInfoForm';
import ProductFunctionModuleInfo from '@/pages/device/product/modules/ProductFunctionModuleInfo';
import ProductDeviceInfo from '@/pages/device/product/modules/ProductDeviceInfo';
const { Paragraph } = Typography;
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
  const [responsive, setResponsive] = useState(false);
  if (loading) {
    return <ProSkeleton type="descriptions" />;
  }
  return (
    <RcResizeObserver
      key="resize-observer"
      onResize={(offset) => {
        setResponsive(offset.width < 596);
      }}
    >
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
            children: (
              <ProductFunctionModuleInfo
                responsive={responsive}
                productId={productId}
              ></ProductFunctionModuleInfo>
            ),
          },
          {
            key: 'device',
            tab: '设备列表',
            children: <ProductDeviceInfo productId={productId}></ProductDeviceInfo>,
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
    </RcResizeObserver>
  );
};
export default ProductProfile;
