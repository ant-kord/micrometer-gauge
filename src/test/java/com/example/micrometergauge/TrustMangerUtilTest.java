package com.example.micrometergauge;

import com.example.micrometergauge.dto.CertificateDTO;
import com.example.micrometergauge.util.TrustManagerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TrustMangerUtilTest {

    private static final String ALIAS = "kordyukevich";

    @Autowired
    private TrustManagerUtil trustManagerUtil;

    @Test
    public void whenLoadingDefaultKeyStore_thenCertificatesArePresent() throws Exception {
        List<CertificateDTO> certificates = trustManagerUtil.getCertificates();

        assertFalse(certificates.isEmpty());
    }

    @Test
    void whenLoadingKeyStore_thenCertificateIsPresent() throws KeyStoreException {
        KeyStore keyStore = trustManagerUtil.loadKeyStore();

        Certificate certificate = keyStore.getCertificate(ALIAS);

        assertNotNull(certificate);
    }
}
