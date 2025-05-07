import {
  postApiDepartment,
  postApiDepartmentDepartmentId,
  postApiDepartmentTreeFilter,
} from '@/services/device-links-console-ui/department';
import {
  ProForm,
  ProFormDigit,
  ProFormInstance,
  ProFormText,
  ProFormTextArea,
  ProFormTreeSelect,
} from '@ant-design/pro-components';
import { Form, message, Modal } from 'antd';
import React from 'react';

// 1. 定义独立弹窗组件props类型
interface ModalProps {
  open: boolean;
  onCancel: () => void;
  departmentInfo: API.DepartmentTree | undefined;
  refresh: () => void;
}

const CreateDepartmentModal: React.FC<ModalProps> = ({
  open,
  onCancel,
  departmentInfo,
  refresh,
}) => {
  const [formInfo, setFormInfo] = React.useState<API.DepartmentTree>();
  const [form] = Form.useForm<API.DepartmentTree>();
  const formRef = React.useRef<ProFormInstance>();
  React.useEffect(() => {
    if (open && formRef.current) {
      console.log('xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx', departmentInfo);
      setFormInfo(JSON.parse(JSON.stringify(departmentInfo)));
      formRef.current?.setFieldsValue(formInfo);
    }
  }, [open, departmentInfo]);
  return (
    <Modal
      title={formInfo?.id ? '编辑部门' : '新增部门'}
      open={open}
      onCancel={onCancel}
      footer={false}
    >
      <ProForm<API.DepartmentTree>
        form={form}
        formRef={formRef}
        key={formInfo?.id || ''}
        autoFocusFirstInput
        onFinish={async (values: any) => {
          console.log('formInfo', formInfo);
          if (!formInfo?.id) {
            console.log('+++++++++++++++');
            await postApiDepartment(values);
            message.success('新增成功');
            refresh();
            onCancel();
            formRef.current?.resetFields();
            return true;
          } else {
            console.log('--------------------');
            await postApiDepartmentDepartmentId(
              { departmentId: formInfo?.id ?? '' },
              { ...values },
            );
            message.success('更新成功');
            refresh();
            onCancel();
            formRef.current?.resetFields();
            return true;
          }
        }}
      >
        <ProFormTreeSelect
          allowClear
          tooltip={'如果不选，则默认为一级部门'}
          name="pid"
          label="上级部门"
          placeholder="请选择上级部门"
          fieldProps={{
            fieldNames: {
              label: 'name',
              value: 'id',
            },
          }}
          request={async () => {
            const { data } = await postApiDepartmentTreeFilter({
              searchFieldModule: 'Department',
              searchMatch: 'ALL',
              searchFields: [],
            });
            return data;
          }}
        />
        <ProFormText
          name="name"
          label="部门名称"
          allowClear
          tooltip={'部门名称最长为30个字符'}
          placeholder="请输入部门名称"
          rules={[
            {
              required: true,
              pattern: /^[\u4e00-\u9fa5a-zA-Z0-9][\u4e00-\u9fa5a-zA-Z0-9_-]*$/,
              message: '首字符需为中文/英文/数字，后续可包含下划线或短横线',
            },
          ]}
        />
        <ProFormText
          name="identifier"
          label="部门标识符"
          allowClear
          placeholder="请输入部门名称"
          rules={[
            {
              required: true,
              pattern: /^[a-zA-Z][a-zA-Z0-9_]*$/,
              message: '必须以字母开头，仅允许包含字母、数字和下划线',
            },
          ]}
        />

        <ProFormDigit
          name="sort"
          label="排序"
          fieldProps={{
            precision: 0, // 关键配置：禁止小数
            step: 1, // 步进值设为整数
            formatter: (v) => `${v}`.replace(/[^\d]/g, ''), // 过滤非数字字符
          }}
          rules={[{ pattern: /^\d+$/, message: '必须为整数' }]} // 二次验证
        />
        <ProFormDigit
          name="level"
          label="等级"
          fieldProps={{
            precision: 0, // 关键配置：禁止小数
            step: 1, // 步进值设为整数
            formatter: (v) => `${v}`.replace(/[^\d]/g, ''), // 过滤非数字字符
          }}
          rules={[{ pattern: /^\d+$/, message: '必须为整数' }]} // 二次验证
        />
        <ProFormTextArea name="description" label="描述" />
      </ProForm>
    </Modal>
  );
};

export default CreateDepartmentModal;
