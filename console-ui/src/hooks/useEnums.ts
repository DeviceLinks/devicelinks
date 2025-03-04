import React from 'react';

const useEnums: {
  [enumTypeList: string]: React.MutableRefObject<API.Enum>[];
} = (enumTypeList: API.Enum[]) => {
  if (!enumTypeList?.length) {
    return {};
  }
};
export default useEnums;
