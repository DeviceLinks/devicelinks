import PageTitle from '@/components/PageTitle';
import { Button } from 'antd';
import style from './styles/index.module.css';

const UserInfo: React.FC = () => {
  const titleRightButton = <Button type="primary">成员入职</Button>;
  return (
    <div className={style.container}>
      <PageTitle
        title={'用户管理'}
        context={'当前用户池的所有用户，在这里你可以对用户进行统一管理'}
        rightSlot={titleRightButton}
      />
    </div>
  );
};

export default UserInfo;
