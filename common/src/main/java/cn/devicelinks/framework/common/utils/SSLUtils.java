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

package cn.devicelinks.framework.common.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.FileReader;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Collection;

/**
 * SSL 工具类
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SSLUtils {

    private static final String SSL_PROTOCOL = "TLSv1.3";

    private static final String CERTIFICATE_TYPE = "X.509";

    private static final String TRUST_MANAGER_FACTORY = "X509";

    private static final String JCA_PEM_KEY_PROVIDER_NAME = "BC";

    /**
     * 获取单向认证的 SSLSocketFactory
     *
     * @param caCrtFile CA证书文件
     * @return {@link SSLSocketFactory}
     * @throws Exception 异常信息
     */
    public static SSLSocketFactory getSingleSocketFactory(final String caCrtFile) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // Load CA certificates
        KeyStore caKs = loadCAKeyStore(caCrtFile);
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(caKs);
        SSLContext sslContext = SSLContext.getInstance(SSL_PROTOCOL);
        sslContext.init(null, tmf.getTrustManagers(), null);
        return sslContext.getSocketFactory();
    }

    /**
     * 获取双向认证的 SSLSocketFactory
     *
     * @param caCrtFile CA证书文件
     * @param crtFile   证书文件
     * @param keyFile   私钥文件
     * @param password  私钥密码
     * @return {@link SSLSocketFactory}
     * @throws Exception 异常信息
     */
    public static SSLSocketFactory getSocketFactory(final String caCrtFile,
                                                    final String crtFile, final String keyFile, final String password)
            throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // Load CA certificates
        KeyStore caKs = loadCAKeyStore(caCrtFile);

        // Load client certificate chain and key
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(null, null);

        // Load the entire client certificate chain
        Certificate[] chain;
        try (FileInputStream fis = new FileInputStream(crtFile)) {
            CertificateFactory cf = CertificateFactory.getInstance(CERTIFICATE_TYPE);
            Collection<? extends Certificate> certs = cf.generateCertificates(fis);
            chain = certs.toArray(new Certificate[0]);
        }

        // Load client private key
        try (PEMParser pemParser = new PEMParser(new FileReader(keyFile))) {
            Object object = pemParser.readObject();
            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(JCA_PEM_KEY_PROVIDER_NAME);
            KeyPair key = converter.getKeyPair((PEMKeyPair) object);
            ks.setKeyEntry("private-key", key.getPrivate(), password.toCharArray(), chain);
        }

        // Set up key managers and trust managers
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TRUST_MANAGER_FACTORY);
        tmf.init(caKs);
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(ks, password.toCharArray());

        // finally, create SSL socket factory
        SSLContext context = SSLContext.getInstance(SSL_PROTOCOL);
        context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return context.getSocketFactory();
    }

    private static KeyStore loadCAKeyStore(String caCrtFile) throws Exception {
        KeyStore caKs = KeyStore.getInstance(KeyStore.getDefaultType());
        caKs.load(null, null);
        try (FileInputStream fis = new FileInputStream(caCrtFile)) {
            CertificateFactory cf = CertificateFactory.getInstance(CERTIFICATE_TYPE);
            Collection<? extends Certificate> caCerts = cf.generateCertificates(fis);
            int certIndex = 1;
            for (Certificate caCert : caCerts) {
                String alias = "ca-certificate-" + certIndex++;
                caKs.setCertificateEntry(alias, caCert);
            }
        }
        return caKs;
    }
}
