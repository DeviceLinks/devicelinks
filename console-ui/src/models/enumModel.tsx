/*
 *   Copyright (C) 2024-2025  DeviceLinks
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
import { getApiCommonEnums } from '@/services/device-links-console-ui/common';
import { history } from '@@/core/history';
import { useEffect, useState } from 'react';
import { ProSchemaValueEnumMap, ProSchemaValueEnumType } from '@ant-design/pro-components';
import { Tag } from 'antd';

const loginPath = '/user/login';
export default function Page(): {
  fetchEnums: () => Promise<API.Enum | undefined>;
  enums: API.Enum;
  setData: (data: API.Enum) => void;
  getProSchemaValueEnumObjByEnum: (
    list: API.EnumItem[],
    showStatus?: boolean,
  ) => ProSchemaValueEnumMap;
  getEnumByValue: (
    list: API.EnumItem[],
    value: string | number | boolean,
  ) => API.EnumItem | undefined;
} {
  const initEnums = {
    AttributeDataType: [],
    OtaUpgradeStrategyRetryInterval: [],
    SearchFieldComponentType: [],
    SessionStatus: [],
    NotificationPushAway: [],
    NotificationType: [],
    AlarmType: [],
    SearchFieldOperator: [],
    OtaUpgradeBatchMethod: [],
    UserActivateMethod: [],
    DeviceType: [],
    OtaUpgradeBatchScope: [],
    NotificationSeverity: [],
    ProductStatus: [],
    DeviceNetworkingAway: [],
    SearchFieldValueType: [],
    PlatformType: [],
    AccessGatewayProtocol: [],
    DeviceStatus: [],
    OtaUpgradeStrategyType: [],
    SearchFieldOptionDataSource: [],
    SearchFieldModuleIdentifier: [],
    OtaUpgradeBatchType: [],
    GlobalSettingDataType: [],
    SignatureAlgorithm: [],
    NotificationMatchUserAway: [],
    LogAction: [],
    DeviceAuthenticationMethod: [],
    NotificationTypeIdentifier: [],
    SignAlgorithm: [],
    DataFormat: [],
    OtaPackageType: [],
    OtaUpgradeProgressState: [],
    OtaPackageDownloadProtocol: [],
    EntityAction: [],
    NotificationStatus: [],
    SearchFieldMatch: [],
    OTAFileSource: [],
    LogObjectType: [],
    OtaUpgradeBatchState: [],
    UserIdentity: [],
  } as API.Enum;
  const [enums, setEnums] = useState<API.Enum>(initEnums);
  const fetchEnums = async () => {
    try {
      const { data } = await getApiCommonEnums({
        skipErrorHandler: true,
      });
      return data;
    } catch (error) {
      return initEnums;
    }
  };
  // 如果不是登录页面，执行
  const { location } = history;
  useEffect(() => {
    if (location.pathname !== loginPath) {
      fetchEnums().then((data) => {
        setEnums(data);
      });
    }
  }, []);
  //设置数据
  const setData = (data: API.Enum) => {
    setEnums(data);
  };
  /**
   * 获取AntD Pro 类型的枚举
   * @param list
   */
  const getProSchemaValueEnumObjByEnum = (list: API.EnumItem[]): ProSchemaValueEnumMap => {
    const map = new Map<string | number | boolean, ProSchemaValueEnumType>();
    list.forEach((item: API.EnumItem) => {
      map.set(item.value, {
        text: (
          <Tag bordered={false} color={item.showStyle.toLowerCase()}>
            {item.label}
          </Tag>
        ),
      });
    });
    return map;
  };
  /**
   *  根据值获取枚举
   * @param list
   * @param value
   */
  const getEnumByValue = (list: API.EnumItem[], value: string | number | boolean) => {
    return list.find((item) => item.value === value);
  };

  return {
    fetchEnums,
    enums,
    setData,
    getProSchemaValueEnumObjByEnum,
    getEnumByValue,
  };
}
