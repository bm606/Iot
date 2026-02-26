package com.multiai.collaboration.entity;

import lombok.Data;

@Data
public class AiRequest {
    private String model;
    private String content;
    private String system;
    private int maxTokens;
    private double temperature;
}