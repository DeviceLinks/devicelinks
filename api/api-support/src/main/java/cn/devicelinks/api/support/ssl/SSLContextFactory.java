package cn.devicelinks.api.support.ssl;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.InputStream;
import java.security.KeyStore;

/**
 * The {@link SSLContext} Factory
 *
 * @author 恒宇少年
 * @since 1.0
 */
public class SSLContextFactory {
    private static final String KEY_STORE_TYPE = "JKS";
    private static final String SSL_PROTOCOL = "TLS";

    public static SSLContext createSSLContext(String keyStorePath, String keyStorePassword, String trustStorePath, String trustStorePassword) throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KEY_STORE_TYPE);
        try (InputStream keyStoreStream = SSLContextFactory.class.getResourceAsStream(keyStorePath)) {
            keyStore.load(keyStoreStream, keyStorePassword.toCharArray());
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

        KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE);
        try (InputStream trustStoreStream = SSLContextFactory.class.getResourceAsStream(trustStorePath)) {
            trustStore.load(trustStoreStream, trustStorePassword.toCharArray());
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance(SSL_PROTOCOL);
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        return sslContext;
    }
}
