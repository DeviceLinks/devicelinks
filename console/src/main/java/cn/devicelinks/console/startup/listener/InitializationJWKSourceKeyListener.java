/*
 *   Copyright (C) 2024-2025  DeviceLinks
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

package cn.devicelinks.console.startup.listener;

import cn.devicelinks.common.startup.AbstractStartupEventListener;
import cn.devicelinks.common.startup.ServerStartupEvent;
import cn.devicelinks.common.utils.RSAKeyUtils;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;

import static cn.devicelinks.console.authorization.jose.LocalFileJwkSource.RSA_PRIVATE_PEM_FILE_PATH;
import static cn.devicelinks.console.authorization.jose.LocalFileJwkSource.RSA_PUBLIC_PEM_FILE_PATH;


/**
 * 初始化RSA格式的密钥对，用于授权服务器的授权、鉴权等操作
 * <p>
 * 如果需要自定义密钥对，首先进入"{user.home}/devicelinks/key"目录，然后通过openssl按照如下步骤生成自定义的密钥对：
 * {@code
 * // shell script
 * <p>
 * // 1. 生成密钥
 * // openssl genrsa -out rsa.pem 2048
 * // 2. 转换pkcs#8格式
 * // openssl pkcs8 -topk8 -inform PEM -in rsa.pem -outform PEM -nocrypt > rsa_private.pem
 * // 3. 导出公钥
 * // openssl rsa -in rsa_private.pem -pubout -outform PEM -out  rsa_public.pem
 * }
 *
 * @author 恒宇少年
 * @see RSAKey
 * @since 1.0
 */
@Service
@Slf4j
public class InitializationJWKSourceKeyListener extends AbstractStartupEventListener {
    @Override
    public void invokeListening(ServerStartupEvent event) {
        try {
            File privateKeyFile = new File(RSA_PRIVATE_PEM_FILE_PATH);
            File publicKeyFile = new File(RSA_PUBLIC_PEM_FILE_PATH);
            if (!privateKeyFile.exists() || !publicKeyFile.exists()) {
                RSAKeyUtils.generatePemKeyPairToFile(RSA_PRIVATE_PEM_FILE_PATH, RSA_PUBLIC_PEM_FILE_PATH);
                log.info("The default RSA KeyPair initialization completed, if you need to modify, please check https://github.com/DeviceLinks/devicelinks/issues.");
            }
        } catch (Exception e) {
            log.error("An exception was encountered while initializing RSAKey.", e);
        }
    }
}
