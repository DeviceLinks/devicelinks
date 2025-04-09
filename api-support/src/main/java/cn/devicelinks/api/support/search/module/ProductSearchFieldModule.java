package cn.devicelinks.api.support.search.module;

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

    SearchFieldOptionData PRODUCT_DYNAMIC_REGISTRATION_YES = SearchFieldOptionData.of().setLabel("是").setValue(Boolean.TRUE);
    SearchFieldOptionData PRODUCT_DYNAMIC_REGISTRATION_NO = SearchFieldOptionData.of().setLabel("否").setValue(Boolean.FALSE);


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
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(DeviceType.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField PRODUCT_NETWORKING_AWAY = SearchField.of(SearchFieldVariable.PRODUCT_NETWORKING_AWAY)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(DeviceNetworkingAway.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField PRODUCT_ACCESS_GATEWAY_PROTOCOL = SearchField.of(SearchFieldVariable.PRODUCT_ACCESS_GATEWAY_PROTOCOL)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(AccessGatewayProtocol.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField PRODUCT_DATA_FORMAT = SearchField.of(SearchFieldVariable.PRODUCT_DATA_FORMAT)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(DataFormat.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    SearchField PRODUCT_AUTHENTICATION_METHOD = SearchField.of(SearchFieldVariable.PRODUCT_AUTHENTICATION_METHOD)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(DeviceAuthenticationMethod.class)
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
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(ProductStatus.class)
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
