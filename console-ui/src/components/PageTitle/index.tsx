import style from './style/index.module.css';

const PageTitle = ({ title = '', context = '', rightSlot = <></> }) => {
  return (
    <div className={style.container}>
      <div>
        <div className={style.titleContent}>{title}</div>
        <div className={style.titleContext}>{context}</div>
      </div>
      <div>
        <>{rightSlot}</>
      </div>
    </div>
  );
};
export default PageTitle;
