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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TrustMangerUtilTest {

    @Autowired
    private TrustManagerUtil trustManagerUtil;

    @Test
    public void whenLoadingDefaultKeyStore_thenCertificatesArePresent() throws Exception {
        List<CertificateDTO> certificates = trustManagerUtil.getCertificates();

        assertFalse(certificates.isEmpty());
    }

    @Test
    void whenLoadingKeyStore_thenCertificateIsPresent() throws Exception {
        List<CertificateDTO> certificates = trustManagerUtil.getCertificates();

        CertificateDTO certificate = new CertificateDTO("Amazon Root CA 1", 5012);

        assertTrue(certificates.contains(certificate));
    }
}
