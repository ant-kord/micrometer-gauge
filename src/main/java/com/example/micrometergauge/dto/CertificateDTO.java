package com.example.micrometergauge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CertificateDTO {

    private String name;
    private long expires;
}
