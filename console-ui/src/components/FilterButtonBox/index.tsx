import { getApiCommonSearchField } from '@/services/device-links-console-ui/common';
import { CloseOutlined, FilterOutlined } from '@ant-design/icons';
import { Button, Form, FormListFieldData, Input, Popover, Select, Space } from 'antd';
import React from 'react';
import style from './styles/index.module.css';
export const FilterButtonBox = ({ module, confirm }) => {
  const [form] = Form.useForm();
  const [filterOption, setfilterOption] = React.useState([]);
  const [selectedValues, setSelectedValues] = React.useState({});
  const getCommonSearchField = async (open: boolean) => {
    if (open) {
      const { data } = await getApiCommonSearchField({ module: module });
      setfilterOption(data);
    }
  };
  const resetFormList = () => {
    form.resetFields();
  };
  const changeFileldType = (rowIndex: any, value: any) => {
    // 清空运算符和值
    form.setFieldsValue({
      items: {
        [rowIndex]: {
          value: undefined,
          operator: undefined,
        },
      },
    });
    setSelectedValues((prev) => ({ ...prev, [rowIndex]: value }));
  };
  /**
   * 判断最后一个表单项应该显示什么组件
   */
  const getLastFormItem = (formItem: FormListFieldData) => {
    let row = filterOption.find((item) => item.field === selectedValues[formItem.name]);
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
        name={'formList'}
        style={{ maxWidth: 600, zIndex: 9999 }}
        autoComplete="off"
        initialValues={{ items: [{}] }}
        onFinish={confirm}
      >
        <Form.List name={'items'}>
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
                        onChange={(v) => changeFileldType(formItem.name, v)}
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
                          filterOption.find((item) => item.field === selectedValues[formItem.name])
                            ?.operators || []
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
      <div className={style.FilterButtonBoxContainer}>
        <Button
          className={style.ghostBtn}
          type="link"
          icon={<FilterOutlined />}
          size={'small'}
          variant={'filled'}
        ></Button>
      </div>
    </Popover>
  );
};
