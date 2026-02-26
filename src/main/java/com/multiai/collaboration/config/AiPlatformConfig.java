package com.multiai.collaboration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "multiai")
public class AiPlatformConfig {
    
    private Platforms platforms = new Platforms();
    private Collaboration collaboration = new Collaboration();
    
    @Data
    public static class Platforms {
        private Qwen qwen = new Qwen();
        private Doubao doubao = new Doubao();
        private Deepseek deepseek = new Deepseek();
    }
    
    @Data
    public static class Qwen {
        private String apiKey;
        private String baseUrl;
        private String model;
    }
    
    @Data
    public static class Doubao {
        private String apiKey;
        private String baseUrl;
        private String model;
    }
    
    @Data
    public static class Deepseek {
        private String apiKey;
        private String baseUrl;
        private String model;
    }
    
    @Data
    public static class Collaboration {
        private int timeout = 30000;
        private int retryCount = 3;
        private int maxToken = 2000;
    }
}