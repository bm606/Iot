package com.multiai.collaboration.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.multiai.collaboration.config.AiPlatformConfig;
import com.multiai.collaboration.entity.AiRequest;
import com.multiai.collaboration.entity.AiResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class AiApiService {
    
    @Autowired
    private AiPlatformConfig config;
    
    private final OkHttpClient client;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public AiApiService() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }
    
    public AiResponse callQwen(AiRequest request) throws IOException {
        String url = config.getPlatforms().getQwen().getBaseUrl() + "/services/aigc/text-generation/generation";
        
        // 构建请求体
        String requestBody = buildDashScopeRequestBody(request);
        
        Request httpRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                .addHeader("Authorization", "Bearer " + config.getPlatforms().getQwen().getApiKey())
                .addHeader("Content-Type", "application/json")
                .build();
        
        return executeRequest(httpRequest);
    }
    
    public AiResponse callDoubao(AiRequest request) throws IOException {
        String url = config.getPlatforms().getDoubao().getBaseUrl() + "/chat/completions";
        
        // 构建请求体
        String requestBody = buildStandardRequestBody(request);
        
        Request httpRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                .addHeader("Authorization", "Bearer " + config.getPlatforms().getDoubao().getApiKey())
                .addHeader("Content-Type", "application/json")
                .build();
        
        return executeRequest(httpRequest);
    }
    
    public AiResponse callDeepSeek(AiRequest request) throws IOException {
        String url = config.getPlatforms().getDeepseek().getBaseUrl();
        
        // 构建请求体
        String requestBody = buildStandardRequestBody(request);
        
        Request httpRequest = new Request.Builder()
                .url(url)
                .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                .addHeader("Authorization", "Bearer " + config.getPlatforms().getDeepseek().getApiKey())
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();
        
        return executeRequest(httpRequest);
    }
    
    private String buildStandardRequestBody(AiRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"model\": \"").append(request.getModel() != null ? request.getModel() : "default-model").append("\",");
        sb.append("\"messages\": [");
        
        if (request.getSystem() != null && !request.getSystem().isEmpty()) {
            sb.append("{\"role\": \"system\", \"content\": \"").append(escapeJson(request.getSystem())).append("\"},");
        }
        sb.append("{\"role\": \"user\", \"content\": \"").append(escapeJson(request.getContent())).append("\"}");
        sb.append("],");
        sb.append("\"max_tokens\": ").append(request.getMaxTokens() > 0 ? request.getMaxTokens() : config.getCollaboration().getMaxToken()).append(",");
        sb.append("\"temperature\": ").append(request.getTemperature() > 0 ? request.getTemperature() : 0.7).append("");
        sb.append("}");
        return sb.toString();
    }
    
    private String buildDashScopeRequestBody(AiRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"model\": \"").append(request.getModel() != null ? request.getModel() : config.getPlatforms().getQwen().getModel()).append("\",");
        sb.append("\"input\": {");
        sb.append("\"messages\": [");
        
        if (request.getSystem() != null && !request.getSystem().isEmpty()) {
            sb.append("{\"role\": \"system\", \"content\": \"").append(escapeJson(request.getSystem())).append("\"},");
        }
        sb.append("{\"role\": \"user\", \"content\": \"").append(escapeJson(request.getContent())).append("\"}");
        sb.append("]");
        sb.append("},");
        sb.append("\"parameters\": {");
        sb.append("\"max_tokens\": ").append(request.getMaxTokens() > 0 ? request.getMaxTokens() : config.getCollaboration().getMaxToken()).append(",");
        sb.append("\"temperature\": ").append(request.getTemperature() > 0 ? request.getTemperature() : 0.7).append("");
        sb.append("}");
        sb.append("}");
        return sb.toString();
    }
    
    private AiResponse executeRequest(Request request) throws IOException {
        log.debug("Making API request to: {}", request.url());
        log.debug("Request headers: {}", request.headers());
        
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code() + ", message: " + response.body().string());
            }
            
            String responseBody = response.body().string();
            log.debug("API response: {}", responseBody);
            
            return objectMapper.readValue(responseBody, AiResponse.class);
        }
    }
    
    private String escapeJson(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("\\", "\\\\")
                   .replace("\"", "\\\"")
                   .replace("\n", "\\n")
                   .replace("\r", "\\r")
                   .replace("\t", "\\t");
    }
}