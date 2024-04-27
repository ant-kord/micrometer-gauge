package com.example.micrometergauge.util;

import com.example.micrometergauge.dto.CertificateDTO;
import org.cryptacular.util.CertUtil;
import org.springframework.stereotype.Component;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TrustManagerUtil {

    public List<CertificateDTO> readCertificates() throws Exception {
        KeyStore keyStore = loadKeyStore();
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        List<TrustManager> trustManagers = Arrays.asList(trustManagerFactory.getTrustManagers());

        return trustManagers.stream()
                .filter(X509TrustManager.class::isInstance)
                .map(X509TrustManager.class::cast)
                .map(trustManager -> Arrays.asList(trustManager.getAcceptedIssuers()))
                .flatMap(stream -> stream.stream())
                .map(x509Certificate -> {
                    Date expiresOn= x509Certificate.getNotAfter();
                    Date now = new Date();
                    long expires = (expiresOn.getTime() - now.getTime())/(1000*60*60*24);
                    return new CertificateDTO(CertUtil.subjectCN(x509Certificate), expires);
                })
                .collect(Collectors.toList());

    }

    private KeyStore loadKeyStore() {
        String relativeCacertsPath = "/lib/security/cacerts".replace("/", File.separator);
        String filename = System.getProperty("java.home") + relativeCacertsPath;
        KeyStore keystore;
        try (FileInputStream is = new FileInputStream(filename)) {

            keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            String password = "changeit";
            keystore.load(is, password.toCharArray());


        } catch (FileNotFoundException | KeyStoreException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CertificateException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return keystore;
    }
}
