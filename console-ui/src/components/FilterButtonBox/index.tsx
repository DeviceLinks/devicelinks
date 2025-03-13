import { getApiCommonSearchField } from '@/services/device-links-console-ui/common';
import { CloseOutlined, FilterOutlined } from '@ant-design/icons';
import { Button, Form, FormListFieldData, Input, Popover, Select, Space } from 'antd';
import React from 'react';
export const FilterButtonBox = ({
  initialValues,
  confirm,
}: {
  initialValues: API.SearchField;
  confirm: (values: API.SearchField) => void;
}) => {
  const [form] = Form.useForm<API.SearchField>();
  const searchFields = Form.useWatch<API.SearchFieldItem[]>('searchFields', form);
  const [filterOption, setFilterOption] = React.useState<API.SearchFieldModuleItem[]>([]);
  const getCommonSearchField = async (open: boolean) => {
    if (open) {
      const { data } = await getApiCommonSearchField({ module: initialValues.searchFieldModule });
      setFilterOption(data);
    }
  };
  const resetFormList = () => {
    form.setFieldValue('searchFields', [{}]);
  };
  const changeFieldType = (rowIndex: number, field: string) => {
    const item: API.SearchFieldItem = {
      field: field,
    };
    form.setFieldValue([`searchFields`, rowIndex], item);
  };
  /**
   * 判断最后一个表单项应该显示什么组件
   */
  const getLastFormItem = (formItem: FormListFieldData) => {
    let row = filterOption.find((item) => item.field === searchFields[formItem.name]?.field);
    if (row && row.componentType === 'SELECT') {
      return (
        <Select
          placeholder={'请选择'}
          options={row?.optionStaticData || []}
          style={{ width: '240px' }}
          allowClear
          mode="multiple"
        ></Select>
      );
    } else if (row && row.componentType === 'INPUT') {
      return <Input placeholder={'请输入'} style={{ width: '240px' }}></Input>;
    } else {
      return <Input placeholder={'请输入'} style={{ width: '240px' }}></Input>;
    }
  };
  /**
   * 弹框表单内容
   */
  const content = () => {
    return (
      <Form
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 18 }}
        form={form}
        style={{ maxWidth: 600, zIndex: 9999 }}
        autoComplete="off"
        initialValues={{
          ...initialValues,
          searchFields: [{}],
        }}
        onFinish={confirm}
      >
        <Form.Item name={'searchFieldModule'} hidden={true}></Form.Item>
        <Form.Item name={'searchMatch'} hidden={true}></Form.Item>
        <Form.List name={'searchFields'}>
          {(fields, { add, remove }) => (
            <div style={{ display: 'flex', flexDirection: 'column', rowGap: 16 }}>
              {fields.map((formItem) => {
                return (
                  <Space key={formItem.key}>
                    <Form.Item
                      noStyle
                      name={[formItem.name, 'field']}
                      rules={[{ required: true, message: '请选择字段' }]}
                    >
                      <Select
                        placeholder={'请选择字段'}
                        options={filterOption}
                        style={{ width: '140px' }}
                        fieldNames={{
                          label: 'fieldText',
                          value: 'field',
                        }}
                        onChange={(v) => changeFieldType(formItem.name, v)}
                      ></Select>
                    </Form.Item>
                    <Form.Item
                      noStyle
                      name={[formItem.name, 'operator']}
                      rules={[{ required: true, message: '请选择运算符' }]}
                    >
                      <Select
                        placeholder={'请选择运算符'}
                        options={
                          filterOption.find(
                            (item) => item.field === searchFields[formItem.name]?.field,
                          )?.operators
                        }
                        style={{ width: '140px' }}
                        fieldNames={{
                          label: 'description',
                          value: 'value',
                        }}
                      ></Select>
                    </Form.Item>
                    <Form.Item
                      noStyle
                      name={[formItem.name, 'value']}
                      rules={[{ required: true, message: '请选择/输入' }]}
                    >
                      {getLastFormItem(formItem)}
                    </Form.Item>
                    {formItem && fields.length > 1 && (
                      <CloseOutlined
                        onClick={() => {
                          remove(formItem.name);
                        }}
                      />
                    )}
                  </Space>
                );
              })}
              <div
                style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}
              >
                <Button type={'dashed'} onClick={() => add()}>
                  + 添加筛选条件
                </Button>
                <div>
                  <Button onClick={() => resetFormList()} style={{ marginRight: '10px' }}>
                    重置
                  </Button>
                  <Button type={'primary'} htmlType={'submit'}>
                    搜索
                  </Button>
                </div>
              </div>
            </div>
          )}
        </Form.List>
      </Form>
    );
  };
  return (
    <Popover
      content={content}
      placement="bottom"
      trigger={'click'}
      onOpenChange={(open: boolean) => getCommonSearchField(open)}
    >
      <Button type="text" icon={<FilterOutlined />}></Button>
    </Popover>
  );
};
