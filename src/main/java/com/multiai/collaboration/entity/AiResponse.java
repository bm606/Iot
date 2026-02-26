package com.multiai.collaboration.entity;

import lombok.Data;

@Data
public class AiResponse {
    private String id;
    private String object;
    private long created;
    private String model;
    private Choice[] choices;
    private Usage usage;
    
    @lombok.Data
    public static class Choice {
        private int index;
        private Message message;
        private Object finish_reason;
    }
    
    @lombok.Data
    public static class Message {
        private String role;
        private String content;
    }
    
    @lombok.Data
    public static class Usage {
        private int prompt_tokens;
        private int completion_tokens;
        private int total_tokens;
    }
}