import cn.devicelinks.common.AttributeDataType;
import cn.devicelinks.common.SignatureAlgorithm;

import java.util.List;

/**
 * 属性数据类型验证单元测试
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class AttributeDataTypeValidationTest {
    public static void main(String[] args) {
        System.out.println(AttributeDataType.INTEGER.validate("1"));
        System.out.println(AttributeDataType.INTEGER.validate("aaaa"));
        System.out.println(AttributeDataType.DOUBLE.validate("1.1"));
        System.out.println(AttributeDataType.DOUBLE.validate("aa"));
        System.out.println(AttributeDataType.ENUM.validate(SignatureAlgorithm.SHA256withRSA));
        System.out.println(AttributeDataType.BOOLEAN.validate(true));
        System.out.println(AttributeDataType.BOOLEAN.validate("false"));
        System.out.println(AttributeDataType.DATE.validate("2025-06-10"));
        System.out.println(AttributeDataType.DATE.validate("2025-6-10"));
        System.out.println(AttributeDataType.DATE.validate("15:41:00"));
        System.out.println(AttributeDataType.TIME.validate("15:41:00"));
        System.out.println(AttributeDataType.TIME.validate("2025-6-10 15:41:00"));
        System.out.println(AttributeDataType.TIMESTAMP.validate("2025-6-10 15:41:00"));
        System.out.println(AttributeDataType.TIMESTAMP.validate(System.currentTimeMillis()));
        System.out.println(AttributeDataType.JSON.validate(""));
        System.out.println(AttributeDataType.JSON.validate("{}"));
        System.out.println(AttributeDataType.ARRAY.validate(new String[]{}));
        System.out.println(AttributeDataType.ARRAY.validate(List.of()));
    }
}
