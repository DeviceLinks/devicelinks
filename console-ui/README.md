# 控制台前端界面

## 安装 `node_modules`:

```bash
  npm install
```

or (更推荐)

```bash
  pnpm install
```

## 运行

开发环境

```bash
  npm run dev
```

## OpenAPI 替换

1. 将 `config/openapi.json` 替换为你的 OpenAPI 配置文件
2. 运行

```bash
  npm run openapi
```

## 目录结构

```
├── config
│   ├── config.ts                 # 全部配置
│   ├── defaultSettings.ts        # 默认配置
│   ├── openapi.json              # OpenAPI 配置
│   ├── proxy.json                # 代理配置
│   └── routes.ts                 # 路由配置
├── public
│   ├── icons                     # 图标目录
│   ├── scripts                   # 脚本目录
│   ├── favicon.ico               # 图标
│   └── logo.svg                  # logo
├── src
│   ├── components                # 业务通用组件
│   ├── pages                     # 业务页面入口和常用模板
│   │    └── example              # 业务页面模板
│   │         ├── list
│   │         │   └──index.tsx    # 列表业务
│   │         ├── profile
│   │         │   └──index.tsx    # 详情业务
│   │         └── styles          # 样式
│   │         └── modules         # 业务通用模块（组件）
|   |             └── CreateProductForm.tsx     # 添加业务组件
│   ├── services                  # 后台接口服务
│   ├── utils                     # 工具库
│   ├── access.ts                 # 权限配置
│   ├── global.less               # 全局样式
│   └── global.ts                 # 全局js
├── tsconfig.json
|
└── package.json
```

## 使用枚举

示例： proTable 中使用

```ts
import { useModel } from '@umijs/max';

const Demo: React.FC = () => {
  const { enums, getProSchemaValueEnumObjByEnum } = useModel('enumModel');
  const { Status, Type } = enums;
  const columns: ProColumns<API.Demo>[] = [
    {
      title: '类型',
      dataIndex: 'type',
      valueType: 'select',
      fieldProps: {
        options: Type,
      },
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: getProSchemaValueEnumObjByEnum(Status),
    },
  ];
  return <ProTable columns={columns}></ProTable>;
};
```
