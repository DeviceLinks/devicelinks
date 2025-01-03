/*
 *   Copyright (C) 2024  DeviceLinks
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

package cn.devicelinks.framework.common.utils;

import cn.devicelinks.framework.common.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import java.util.HexFormat;
import java.util.function.Function;

/**
 * 签名工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
@Slf4j
public class SignatureUtils {
    /**
     * 生成Base64格式签名
     *
     * @param bytes      字节数组
     * @param privateKey RSA密钥
     * @return 签名字符串，Base64格式编码
     */
    public static String generateBase64Signature(byte[] bytes, PrivateKey privateKey, SignatureAlgorithm algorithm) {
        return generateSignature(bytes, privateKey, algorithm, Base64.getEncoder()::encodeToString);
    }

    /**
     * 验证Base64格式签名
     *
     * @param bytes     字节数组
     * @param signature 签名字符串，Base64格式编码
     * @param publicKey RSA公钥
     * @return 验证结果，返回true时表示验证通过
     */
    public static boolean verifyBase64Signature(byte[] bytes, String signature, PublicKey publicKey, SignatureAlgorithm algorithm) {
        return verifySignature(bytes, signature, publicKey, algorithm, Base64.getDecoder()::decode);
    }

    /**
     * 生成Hex格式签名
     *
     * @param bytes      字节数组
     * @param privateKey RSA私钥
     * @param algorithm  签名算法
     * @return 签名字符串，Hex格式编码
     */
    public static String generateHexSignature(byte[] bytes, PrivateKey privateKey, SignatureAlgorithm algorithm) {
        return generateSignature(bytes, privateKey, algorithm, HexFormat.of()::formatHex);
    }

    /**
     * 验证Hex格式签名
     *
     * @param bytes     字节数组
     * @param signature 签名字符串，Hex格式编码
     * @param publicKey RSA公钥
     * @param algorithm 签名算法
     * @return 验证结果，返回true时表示验证通过
     */
    public static boolean verifyHexSignature(byte[] bytes, String signature, PublicKey publicKey, SignatureAlgorithm algorithm) {
        return verifySignature(bytes, signature, publicKey, algorithm, HexFormat.of()::parseHex);
    }

    /**
     * 生成签名
     *
     * @param bytes      字节数组
     * @param privateKey 私钥
     * @param algorithm  签名算法
     * @param encoder    编码器
     * @return 签名字符串
     */
    private static String generateSignature(byte[] bytes, PrivateKey privateKey, SignatureAlgorithm algorithm, Function<byte[], String> encoder) {
        try {
            Signature signature = Signature.getInstance(algorithm.toString());
            signature.initSign(privateKey);
            signature.update(bytes);
            return encoder.apply(signature.sign());
        } catch (Exception e) {
            log.error("生成签名失败", e);
        }
        return null;
    }

    /**
     * 验证签名
     *
     * @param bytes     字节数组
     * @param signature 签名
     * @param publicKey 公钥
     * @param algorithm 签名算法
     * @param decoder   解码器
     * @return 验证结果
     */
    private static boolean verifySignature(byte[] bytes, String signature, PublicKey publicKey, SignatureAlgorithm algorithm, Function<String, byte[]> decoder) {
        try {
            Signature sign = Signature.getInstance(algorithm.toString());
            sign.initVerify(publicKey);
            sign.update(bytes);
            return sign.verify(decoder.apply(signature));
        } catch (Exception e) {
            log.error("验证签名失败", e);
        }
        return false;
    }
}
