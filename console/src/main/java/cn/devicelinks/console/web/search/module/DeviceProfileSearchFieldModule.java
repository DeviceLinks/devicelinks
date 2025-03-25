package cn.devicelinks.console.web.search.module;

import cn.devicelinks.framework.common.ProvisionRegistrationStrategy;
import cn.devicelinks.framework.common.web.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 设备配置文件检索字段模块
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Component
public class DeviceProfileSearchFieldModule implements SearchFieldModule {

    SearchField PROFILE_ID = SearchField.of(SearchFieldVariable.ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField PROFILE_NAME = SearchField.of(SearchFieldVariable.DEVICE_PROFILE_NAME)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField PROFILE_FIRMWARE_ID = SearchField.of(SearchFieldVariable.DEVICE_PROFILE_FIRMWARE_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField PROFILE_SOFTWARE_ID = SearchField.of(SearchFieldVariable.DEVICE_PROFILE_SOFTWARE_ID)
            .setValueType(SearchFieldValueType.STRING)
            .setComponentType(SearchFieldComponentType.INPUT)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.Like,
                    SearchFieldOperator.NotLike
            ));

    SearchField PROFILE_PROVISION_REGISTRATION_STRATEGY = SearchField.of(SearchFieldVariable.DEVICE_PROFILE_PROVISION_REGISTRATION_STRATEGY)
            .setValueType(SearchFieldValueType.ENUM)
            .setComponentType(SearchFieldComponentType.SELECT)
            .setEnumClass(ProvisionRegistrationStrategy.class)
            .setOperators(List.of(
                    SearchFieldOperator.EqualTo,
                    SearchFieldOperator.NotEqualTo,
                    SearchFieldOperator.In,
                    SearchFieldOperator.NotIn
            ));

    @Override
    public SearchFieldModuleIdentifier supportIdentifier() {
        return SearchFieldModuleIdentifier.DeviceProfile;
    }

    @Override
    public List<SearchField> getSearchFields() {
        return List.of(PROFILE_ID, PROFILE_NAME, PROFILE_FIRMWARE_ID, PROFILE_SOFTWARE_ID, PROFILE_PROVISION_REGISTRATION_STRATEGY);
    }
}
