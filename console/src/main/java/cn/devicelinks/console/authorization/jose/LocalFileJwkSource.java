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

package cn.devicelinks.console.authorization.jose;

import cn.devicelinks.common.DeviceLinksVersion;
import cn.devicelinks.common.utils.RSAKeyUtils;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;

/**
 * 用于生成JWT令牌的{@link JWKSource}实现类
 * <p>
 * 加载指定位置的"RSA密钥对"提供给授权服务器进行授权、鉴权使用，密钥对存储位置"{user.home}/devicelinks/key"
 *
 * @since 1.0
 */
@Slf4j
public class LocalFileJwkSource implements JWKSource<SecurityContext> {
    private static final String KEY_ID = String.valueOf(DeviceLinksVersion.SERIAL_VERSION_UID);
    private static final String USER_DIR = System.getProperty("user.home");
    private static final String KEY_PAIR_BASE_DIR = USER_DIR + File.separator + "devicelinks" + File.separator + "key";
    public static final String RSA_PRIVATE_PEM_FILE_PATH = KEY_PAIR_BASE_DIR + File.separator + "rsa_private.pem";
    public static final String RSA_PUBLIC_PEM_FILE_PATH = KEY_PAIR_BASE_DIR + File.separator + "rsa_public.pem";
    private RSAKey rsaKey;

    @Override
    public List<JWK> get(JWKSelector jwkSelector, SecurityContext context) throws KeySourceException {
        try {
            JWKSet jwkSet = new JWKSet(this.getRsaKey());
            return jwkSelector.select(jwkSet);
        } catch (Exception e) {
            log.error("An exception was encountered when jwk selector.", e);
        }
        return null;
    }

    private RSAKey loadRSAKeyFromPemFile() {
        try {
            PrivateKey privateKey = RSAKeyUtils.readPemPrivateKey(RSA_PRIVATE_PEM_FILE_PATH);
            PublicKey publicKey = RSAKeyUtils.readPemPublicKey(RSA_PUBLIC_PEM_FILE_PATH);
            // @formatter:off
            return new RSAKey.Builder((RSAPublicKey) publicKey)
                    .privateKey(privateKey)
                    .keyID(KEY_ID)
                    .build();
            // @formatter:on
        } catch (Exception e) {
            log.error("build RSAKey exception.", e);
        }
        throw new RuntimeException("build RSAKey exception.");
    }

    public RSAKey getRsaKey() {
        if (rsaKey == null) {
            this.rsaKey = this.loadRSAKeyFromPemFile();
        }
        return rsaKey;
    }
}
