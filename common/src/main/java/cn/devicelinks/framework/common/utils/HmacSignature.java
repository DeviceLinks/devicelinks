package cn.devicelinks.framework.common.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * HMAC签名
 * <p>
 * 支持的签名算法查看 {@link HmacSignatureAlgorithm}
 *
 * @author 恒宇少年
 * @since 1.0
 */
public final class HmacSignature {
    private final HmacSignatureAlgorithm algorithm;
    private final String secret;
    private Mac mac;

    private HmacSignature(HmacSignatureAlgorithm algorithm, String secret) {
        this.algorithm = algorithm;
        this.secret = secret;
        this.initMac();
    }

    private void initMac() {
        try {
            Mac mac = Mac.getInstance(algorithm.toString());
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), algorithm.toString());
            mac.init(secretKeySpec);
            this.mac = mac;
        } catch (Exception e) {
            throw new RuntimeException("HMAC signing error", e);
        }
    }

    public byte[] toBytes(String data) {
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    public String toHex(String data) {
        return ByteUtils.bytesToHex(toBytes(data));
    }

    public static HmacSignature hmacSha1(String secret) {
        return new HmacSignature(HmacSignatureAlgorithm.HmacSHA1, secret);
    }

    public static HmacSignature hmacSha256(String secret) {
        return new HmacSignature(HmacSignatureAlgorithm.HmacSHA256, secret);
    }

    public static HmacSignature hmacSha384(String secret) {
        return new HmacSignature(HmacSignatureAlgorithm.HmacSHA384, secret);
    }

    public static HmacSignature hmacSha512(String secret) {
        return new HmacSignature(HmacSignatureAlgorithm.HmacSHA512, secret);
    }

    public static HmacSignature hmacMd5(String secret) {
        return new HmacSignature(HmacSignatureAlgorithm.HmacMD5, secret);
    }
}
