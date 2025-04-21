package cn.devicelinks.api.support.feign;

/**
 * Feign常量定义
 *
 * @author 恒宇少年
 * @since 1.0
 */
public interface FeignConstants {

    String API_KEY_HEADER_NAME = "X-API-KEY";

    String API_TIMESTAMP_HEADER_NAME = "X-API-TIMESTAMP";

    String API_SIGN_HEADER_NAME = "X-API-SIGN";

    String JSON_CONTENT_TYPE_HEADER = "Content-Type: application/json; charset=UTF-8";
}
