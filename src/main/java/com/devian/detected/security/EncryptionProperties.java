package com.devian.detected.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("encryption")
public class EncryptionProperties {

    private boolean enabled;
    private String key;
}
