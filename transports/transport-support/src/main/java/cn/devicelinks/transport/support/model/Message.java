package cn.devicelinks.transport.support.model;

import cn.devicelinks.common.utils.HmacSignatureAlgorithm;
import cn.devicelinks.component.web.validator.EnumValid;
import cn.devicelinks.component.web.validator.TimestampValid;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 设备消息通用数据格式实体定义
 * <p>
 * 可继承该类自定义封装具体业务的消息实体，也可以直接通过泛型的方式直接定义使用
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Data
public class Message<T> {
    /**
     * 消息唯一ID，设备端生成，用于响应匹配和去重
     */
    @NotBlank(message = "消息ID不可以为空.")
    @Length(min = 20, max = 50, message = "消息ID长度请控制在20 ~ 50之间.")
    private String messageId;
    /**
     * 请求协议版本号，默认为"1.0"
     */
    @NotBlank(message = "协议版本号不可以为空.")
    private String version = "1.0";
    /**
     * 发送请求时的时间戳（单位：毫秒）
     */
    @TimestampValid(message = "请求时间戳值无效.")
    private long timestamp;
    /**
     * 签名算法，如 HmacSHA256、HmacMD5 等
     *
     * @see HmacSignatureAlgorithm
     */
    @EnumValid(target = HmacSignatureAlgorithm.class, message = "签名算法值无效.")
    private String signAlgorithm;
    /**
     * 签名值（对除sign外的字段签名）
     */
    @NotBlank(message = "签名不可以为空.")
    private String sign;
    /**
     * 业务参数对象实例，类型不固定
     */
    @Valid
    @NotNull
    private T param;
}
