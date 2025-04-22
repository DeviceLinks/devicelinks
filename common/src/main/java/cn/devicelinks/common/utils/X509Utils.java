package cn.devicelinks.common.utils;

import cn.devicelinks.common.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

/**
 * X.509 证书工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class X509Utils {
    private static final String CERTIFICATE_TYPE = "X.509";
    private static final String X509_PEM_HEADER = "-----BEGIN CERTIFICATE-----";
    private static final String X509_PEM_FOOTER = "-----END CERTIFICATE-----";

    /**
     * 验证字符串是否为有效的 X.509 PEM 格式
     *
     * @param pemString PEM 格式的字符串
     * @return 如果是有效的 X.509 PEM 格式则返回 true，否则返回 false
     */
    public static boolean isValidX509Pem(String pemString) {
        try {
            // Remove PEM headers and footers, and decode Base64
            String cleanPem = pemString
                    .replace(X509_PEM_HEADER, Constants.EMPTY_STRING)
                    .replace(X509_PEM_FOOTER, Constants.EMPTY_STRING)
                    .replaceAll("\\s", Constants.EMPTY_STRING);
            byte[] decoded = Base64.getDecoder().decode(cleanPem);

            // Try to generate a certificate from the decoded bytes
            CertificateFactory cf = CertificateFactory.getInstance(CERTIFICATE_TYPE);
            X509Certificate cert = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(decoded));

            // If we get here without exception, it's a valid certificate
            return true;
        } catch (Exception e) {
            // If any exception occurs during parsing, it's not a valid certificate
            log.error("Invalid X.509 PEM: ", e);
            return false;
        }
    }
}