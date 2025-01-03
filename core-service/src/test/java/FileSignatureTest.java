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

import cn.devicelinks.framework.common.SignatureAlgorithm;
import cn.devicelinks.framework.common.utils.DigestUtils;
import cn.devicelinks.framework.common.utils.RSAKeyUtils;
import cn.devicelinks.framework.common.utils.SignatureUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * 文件签名测试类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class FileSignatureTest {
    private static final String USER_DIR = System.getProperty("user.home");
    private static final String KEY_PAIR_BASE_DIR = USER_DIR + File.separator + "devicelinks" + File.separator + "key";
    public static final String RSA_PRIVATE_PEM_FILE_PATH = KEY_PAIR_BASE_DIR + File.separator + "rsa_private.pem";
    public static final String RSA_PUBLIC_PEM_FILE_PATH = KEY_PAIR_BASE_DIR + File.separator + "rsa_public.pem";

    public static void main(String[] args) throws Exception {
        // 读取文件内容
        FileInputStream fis = new FileInputStream("/Users/yuqiyu/Downloads/Cybertruck.jpeg");
        byte[] fileData = fis.readAllBytes();
        fis.close();

        // MD5
        System.out.println("MD5：" + DigestUtils.getHexDigest(DigestUtils.MD5, fileData));

        // SHA256
        System.out.println("SHA256：" + DigestUtils.getHexDigest(DigestUtils.SHA256, fileData));

        // 签名
        PrivateKey privateKey = RSAKeyUtils.readPemPrivateKey(RSA_PRIVATE_PEM_FILE_PATH);
        String signature = SignatureUtils.generateHexSignature(fileData, privateKey, SignatureAlgorithm.SHA256withRSA);
        System.out.println("签名：" + signature);

        // 验签
        PublicKey publicKey = RSAKeyUtils.readPemPublicKey(RSA_PUBLIC_PEM_FILE_PATH);
        boolean isValid = SignatureUtils.verifyHexSignature(fileData, signature, publicKey, SignatureAlgorithm.SHA256withRSA);

        if (isValid) {
            System.out.println("文件完整，未被篡改！");
        } else {
            System.out.println("文件已被篡改！");
        }

    }
}
