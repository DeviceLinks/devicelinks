package cn.devicelinks.console.model.search.module;

import cn.devicelinks.framework.common.*;
import cn.devicelinks.framework.common.web.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 产品检索字段模板定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class ProductSearchFieldModule implements SearchFieldModule {

    SearchFieldOptionData PRODUCT_DEVICE_TYPE_DIRECT = SearchFieldOptionData.of().setLabel("直接连接").setValue(DeviceType.Direct);
    SearchFieldOptionData PRODUCT_DEVICE_TYPE_GATEWAY = SearchFieldOptionData.of().setLabel("网关设备").setValue(DeviceType.Gateway);
    SearchFieldOptionData PRODUCT_DEVICE_TYPE_GATEWAY_SUB = SearchFieldOptionData.of().setLabel("网关子设备").setValue(DeviceType.GatewaySub);

    SearchFieldOptionData PRODUCT_NETWORKING_AWAY_WIFI = SearchFieldOptionData.of().setLabel("WIFI").setValue(DeviceNetworkingAway.WiFi);
    SearchFieldOptionData PRODUCT_NETWORKING_AWAY_CELLULAR_NETWORK = SearchFieldOptionData.of().setLabel("蜂窝网络（2G/3G/4G/5G）").setValue(DeviceNetworkingAway.CellularNetwork);
    SearchFieldOptionData PRODUCT_NETWORKING_AWAY_ETHERNET = SearchFieldOptionData.of().setLabel("以太网").setValue(DeviceNetworkingAway.Ethernet);

    SearchFieldOptionData PRODUCT_ACCESS_GATEWAY_PROTOCOL_MQTT = SearchFieldOptionData.of().setLabel("MQTT").setValue(AccessGatewayProtocol.Mqtt);
    SearchFieldOptionData PRODUCT_ACCESS_GATEWAY_PROTOCOL_MODBUS = SearchFieldOptionData.of().setLabel("Modbus").setValue(AccessGatewayProtocol.Modbus);
    SearchFieldOptionData PRODUCT_ACCESS_GATEWAY_PROTOCOL_REST = SearchFieldOptionData.of().setLabel("REST").setValue(AccessGatewayProtocol.Rest);
    SearchFieldOptionData PRODUCT_ACCESS_GATEWAY_PROTOCOL_SOCKET = SearchFieldOptionData.of().setLabel("Socket").setValue(AccessGatewayProtocol.Socket);
    SearchFieldOptionData PRODUCT_ACCESS_GATEWAY_PROTOCOL_GRPC = SearchFieldOptionData.of().setLabel("gRPC").setValue(AccessGatewayProtocol.Grpc);
    SearchFieldOptionData PRODUCT_ACCESS_GATEWAY_PROTOCOL_BLE = SearchFieldOptionData.of().setLabel("BLE").setValue(AccessGatewayProtocol.Ble);

    SearchFieldOptionData PRODUCT_DATA_FORMAT_JSON = SearchFieldOptionData.of().setLabel("JSON").setValue(DataFormat.Json);
    SearchFieldOptionData PRODUCT_DATA_FORMAT_BYTES = SearchFieldOptionData.of().setLabel("Bytes").setValue(DataFormat.Bytes);
    SearchFieldOptionData PRODUCT_DATA_FORMAT_HEX = SearchFieldOptionData.of().setLabel("Hex").setValue(DataFormat.Hex);

    SearchFieldOptionData PRODUCT_AUTHENTICATION_METHOD_PRODUCT_CREDENTIAL = SearchFieldOptionData.of().setLabel("一型一密").setValue(DeviceAuthenticationMethod.ProductCredential);
    SearchFieldOptionData PRODUCT_AUTHENTICATION_METHOD_DEVICE_CREDENTIAL = SearchFieldOptionData.of().setLabel("一机一密").setValue(DeviceAuthenticationMethod.DeviceCredential);
    SearchFieldOptionData PRODUCT_AUTHENTICATION_METHOD_ACCESS_TOKEN = SearchFieldOptionData.of().setLabel("请求令牌").setValue(DeviceAuthenticationMethod.AccessToken);
    SearchFieldOptionData PRODUCT_AUTHENTICATION_METHOD_MQTT_BASIC = SearchFieldOptionData.of().setLabel("MQTT基础认证").setValue(DeviceAuthenticationMethod.MqttBasic);
    SearchFieldOptionData PRODUCT_AUTHENTICATION_METHOD_X_509 = SearchFieldOptionData.of().setLabel("X.509").setValue(DeviceAuthenticationMethod.X509);

    SearchFieldOptionData PRODUCT_DYNAMIC_REGISTRATION_YES = SearchFieldOptionData.of().setLabel("是").setValue(Boolean.TRUE);
    SearchFieldOptionData PRODUCT_DYNAMIC_REGISTRATION_NO = SearchFieldOptionData.of().setLabel("否").setValue(Boolean.FALSE);

    SearchFieldOptionData PRODUCT_STATUS_DEVELOPMENT = SearchFieldOptionData.of().setLabel("开发中").setValue(ProductStatus.Development);
    SearchFieldOptionData PRODUCT_STATUS_PUBLISHED = SearchFieldOptionData.of().setLabel("已发布").setValue(ProductStatus.Published);


    SearchField PRODUCT_ID = SearchField.of(SearchFieldVariable.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn));

    SearchField PRODUCT_NAME = SearchField.of(SearchFieldVariable.PRODUCT_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField PRODUCT_DEVICE_TYPE = SearchField.of(SearchFieldVariable.PRODUCT_DEVICE_TYPE)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    PRODUCT_DEVICE_TYPE_DIRECT,
                    PRODUCT_DEVICE_TYPE_GATEWAY,
                    PRODUCT_DEVICE_TYPE_GATEWAY_SUB
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField PRODUCT_NETWORKING_AWAY = SearchField.of(SearchFieldVariable.PRODUCT_NETWORKING_AWAY)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    PRODUCT_NETWORKING_AWAY_WIFI,
                    PRODUCT_NETWORKING_AWAY_CELLULAR_NETWORK,
                    PRODUCT_NETWORKING_AWAY_ETHERNET

            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField PRODUCT_ACCESS_GATEWAY_PROTOCOL = SearchField.of(SearchFieldVariable.PRODUCT_ACCESS_GATEWAY_PROTOCOL)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    PRODUCT_ACCESS_GATEWAY_PROTOCOL_MQTT,
                    PRODUCT_ACCESS_GATEWAY_PROTOCOL_MODBUS,
                    PRODUCT_ACCESS_GATEWAY_PROTOCOL_REST,
                    PRODUCT_ACCESS_GATEWAY_PROTOCOL_SOCKET,
                    PRODUCT_ACCESS_GATEWAY_PROTOCOL_GRPC,
                    PRODUCT_ACCESS_GATEWAY_PROTOCOL_BLE

            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField PRODUCT_DATA_FORMAT = SearchField.of(SearchFieldVariable.PRODUCT_DATA_FORMAT)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    PRODUCT_DATA_FORMAT_JSON,
                    PRODUCT_DATA_FORMAT_BYTES,
                    PRODUCT_DATA_FORMAT_HEX
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField PRODUCT_AUTHENTICATION_METHOD = SearchField.of(SearchFieldVariable.PRODUCT_AUTHENTICATION_METHOD)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    PRODUCT_AUTHENTICATION_METHOD_PRODUCT_CREDENTIAL,
                    PRODUCT_AUTHENTICATION_METHOD_DEVICE_CREDENTIAL,
                    PRODUCT_AUTHENTICATION_METHOD_ACCESS_TOKEN,
                    PRODUCT_AUTHENTICATION_METHOD_MQTT_BASIC,
                    PRODUCT_AUTHENTICATION_METHOD_X_509
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField PRODUCT_DYNAMIC_REGISTRATION = SearchField.of(SearchFieldVariable.PRODUCT_DYNAMIC_REGISTRATION)
            .setValueType(SearchFieldValueType.BOOLEAN)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    PRODUCT_DYNAMIC_REGISTRATION_YES,
                    PRODUCT_DYNAMIC_REGISTRATION_NO
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo
            ));

    SearchField PRODUCT_STATUS = SearchField.of(SearchFieldVariable.PRODUCT_STATUS)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setOptionDataSource(SearchFieldOptionDataSource.STATIC)
            .setOptionStaticData(List.of(
                    PRODUCT_STATUS_DEVELOPMENT,
                    PRODUCT_STATUS_PUBLISHED
            ))
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.Product;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(
                PRODUCT_ID,
                PRODUCT_NAME,
                PRODUCT_DEVICE_TYPE,
                PRODUCT_NETWORKING_AWAY,
                PRODUCT_ACCESS_GATEWAY_PROTOCOL,
                PRODUCT_DATA_FORMAT,
                PRODUCT_AUTHENTICATION_METHOD,
                PRODUCT_DYNAMIC_REGISTRATION,
                PRODUCT_STATUS
        );
    }
}
