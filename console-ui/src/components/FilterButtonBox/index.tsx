import { FilterOutlined } from '@ant-design/icons';
import { Button, Form, Popover } from 'antd';
import style from './styles/index.module.css';
export const FilterButtonBox = () => {
  // 弹框内容
  const content = () => {
    return <Form></Form>;
  };
  return (
    <Popover content={content} placement="bottom" trigger={'click'}>
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
