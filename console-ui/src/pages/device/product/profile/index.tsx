import React from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { useParams, useRequest } from '@umijs/max';
import { getApiProductProductId } from '@/services/device-links-console-ui/product';
import { DeleteOutlined, DownOutlined, UploadOutlined } from '@ant-design/icons';
import { Button, Dropdown, MenuProps, Space } from 'antd';
import { Typography } from 'antd';
const { Paragraph } = Typography;
const ProductProfile: React.FC = () => {
  const { productId } = useParams();
  const { data: productInfo, loading } = useRequest(
    () => getApiProductProductId({ productId } as API.getApiProductProductIdParams),
    {},
  );
  const menuItems: MenuProps['items'] = [
    ...(productInfo?.status !== 'Published'
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
  return (
    <PageContainer
      tabList={[
        {
          key: 'profile',
          tab: '产品信息',
          children: <div>产品信息</div>,
        },
        {
          key: 'function',
          tab: '功能模块',
        },
        {
          key: 'device',
          tab: '设备列表',
        },
      ]}
      loading={loading}
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
        <Dropdown menu={{ items: menuItems }}>
          <Button type={'primary'}>
            <Space>
              更多
              <DownOutlined />
            </Space>
          </Button>
        </Dropdown>
      }
    ></PageContainer>
  );
};
export default ProductProfile;
