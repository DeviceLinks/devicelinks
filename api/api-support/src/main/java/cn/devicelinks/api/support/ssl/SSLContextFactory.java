package cn.devicelinks.api.support.ssl;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
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
        try (InputStream keyStoreStream = getInputStream(keyStorePath)) {
            keyStore.load(keyStoreStream, keyStorePassword.toCharArray());
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, keyStorePassword.toCharArray());

        KeyStore trustStore = KeyStore.getInstance(KEY_STORE_TYPE);
        try (InputStream trustStoreStream = getInputStream(trustStorePath)) {
            trustStore.load(trustStoreStream, trustStorePassword.toCharArray());
        }

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance(SSL_PROTOCOL);
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);

        return sslContext;
    }

    private static InputStream getInputStream(String path) throws Exception {
        if (path.startsWith("classpath:")) {
            String resourcePath = path.substring("classpath:".length());
            InputStream stream = SSLContextFactory.class.getResourceAsStream(resourcePath.startsWith("/") ? resourcePath : "/" + resourcePath);
            if (stream == null) {
                throw new IllegalArgumentException("Resource not found: " + path);
            }
            return stream;
        } else {
            return new FileInputStream(path);
        }
    }
}
