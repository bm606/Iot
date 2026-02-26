package com.multiai.collaboration.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollaborationResult {
    private String taskId;
    private String status; // INIT, BUSINESS, PRODUCT, DEVELOPMENT, QA, COMPLETED, FAILED
    private String businessRequirement; // 业务需求
    private String productPlan; // 产品方案
    private String developmentImplementation; // 开发实现
    private String qaVerification; // QA验证结果
    private List<Map<String, Object>> collaborationLog; // 协作日志
    private String errorMessage; // 错误信息
    private long startTime;
    private long endTime;
}