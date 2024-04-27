package com.example.micrometergauge.metrics;

import com.example.micrometergauge.util.TrustManagerUtil;
import com.example.micrometergauge.dto.CertificateDTO;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.MultiGauge;
import io.micrometer.core.instrument.Tags;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MetricsCertificate {
    private MultiGauge expiresDaysCount;
    private TrustManagerUtil trustManagerUtil;
    private Logger log = LoggerFactory.getLogger(MetricsCertificate.class);


    public MetricsCertificate(MeterRegistry registry) {

        expiresDaysCount = MultiGauge.builder("expires.certificate.count").tag("name","expiresDays").register(registry);
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0 * * ?")
    public void getCertificateExpiresDaysGauges() throws Exception {

        List<CertificateDTO> certificates = trustManagerUtil.getCertificates();
        expiresDaysCount.register(
                certificates.stream()
                        .map(certificateDTO -> MultiGauge.Row.of(Tags.of(
                                "name", ""+ certificateDTO.getName(),
                                "expiresDays", ""+certificateDTO.getExpires()),  certificates.size()))
                        .collect(Collectors.toList())
                ,true);


    }

    @Autowired
    public void setTrustManagerUtil(TrustManagerUtil trustManagerUtil) {
        this.trustManagerUtil = trustManagerUtil;
    }
}
