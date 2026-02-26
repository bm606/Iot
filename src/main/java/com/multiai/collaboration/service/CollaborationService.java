package com.multiai.collaboration.service;

import com.multiai.collaboration.dto.CollaborationResult;
import com.multiai.collaboration.entity.AiRequest;
import com.multiai.collaboration.entity.AiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * 主要的协作服务类，实现完整的AI协作流程
 * 流程：任务初始化→千问（业务）输出需求→豆包（产品）输出方案→DeepSeek（开发）输出实现→千问验收→闭环/迭代
 */
@Service
@Slf4j
public class CollaborationService {
    
    @Autowired
    private AiApiService aiApiService;
    
    /**
     * 执行完整的AI协作流程
     *
     * @param initialRequirement 初始需求
     * @return 协作结果
     */
    public Map<String, Object> executeCollaboration(String initialRequirement) {
        String taskId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        
        log.info("Starting collaboration with ID: {}, requirement: {}", taskId, initialRequirement);
        
        // 初始化协作结果
        CollaborationResult result = new CollaborationResult();
        result.setTaskId(taskId);
        result.setStatus("INIT");
        result.setBusinessRequirement(initialRequirement);
        result.setStartTime(startTime);
        result.setCollaborationLog(new ArrayList<>());
        
        try {
            // 记录初始步骤
            logStep(result, "INIT", "协作流程初始化", initialRequirement);
            
            // 第一步：千问作为业务角色，细化需求
            log.info("Step 1: Calling Qwen for business requirement refinement...");
            result.setStatus("BUSINESS");
            String refinedRequirement = callQwenForBusinessRequirement(initialRequirement);
            result.setBusinessRequirement(refinedRequirement);
            logStep(result, "BUSINESS", "业务需求细化完成", refinedRequirement);
            
            // 第二步：豆包作为产品角色，制定产品方案
            log.info("Step 2: Calling Doubao for product planning...");
            result.setStatus("PRODUCT");
            String productPlan = callDoubaoForProductPlan(refinedRequirement);
            result.setProductPlan(productPlan);
            logStep(result, "PRODUCT", "产品方案制定完成", productPlan);
            
            // 第三步：DeepSeek作为开发角色，提供实现方案
            log.info("Step 3: Calling DeepSeek for development implementation...");
            result.setStatus("DEVELOPMENT");
            String developmentImpl = callDeepSeekForDevelopment(refinedRequirement, productPlan);
            result.setDevelopmentImplementation(developmentImpl);
            logStep(result, "DEVELOPMENT", "开发实现方案完成", developmentImpl);
            
            // 第四步：千问作为QA角色，进行验收验证
            log.info("Step 4: Calling Qwen for QA verification...");
            result.setStatus("QA");
            String qaVerification = callQwenForQAVerification(refinedRequirement, productPlan, developmentImpl);
            result.setQaVerification(qaVerification);
            logStep(result, "QA", "QA验收验证完成", qaVerification);
            
            // 检查是否通过验收
            if (isAcceptable(qaVerification)) {
                result.setStatus("COMPLETED");
                log.info("Collaboration completed successfully for task: {}", taskId);
            } else {
                result.setStatus("NEEDS_ITERATION");
                log.warn("Collaboration needs iteration for task: {}", taskId);
            }
            
        } catch (Exception e) {
            log.error("Error during collaboration for task: " + taskId, e);
            result.setStatus("FAILED");
            result.setErrorMessage(e.getMessage());
        } finally {
            result.setEndTime(System.currentTimeMillis());
            log.info("Collaboration ended with status: {} for task: {}, duration: {}ms", 
                    result.getStatus(), taskId, result.getEndTime() - result.getStartTime());
        }
        
        // 转换为Map返回
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("taskId", result.getTaskId());
        resultMap.put("status", result.getStatus());
        resultMap.put("businessRequirement", result.getBusinessRequirement());
        resultMap.put("productPlan", result.getProductPlan());
        resultMap.put("developmentImplementation", result.getDevelopmentImplementation());
        resultMap.put("qaVerification", result.getQaVerification());
        resultMap.put("collaborationLog", result.getCollaborationLog());
        resultMap.put("errorMessage", result.getErrorMessage());
        resultMap.put("startTime", result.getStartTime());
        resultMap.put("endTime", result.getEndTime());
        resultMap.put("duration", result.getEndTime() - result.getStartTime());
        
        return resultMap;
    }
    
    /**
     * 使用千问细化业务需求
     */
    private String callQwenForBusinessRequirement(String initialRequirement) throws IOException {
        String systemPrompt = "你是资深的业务分析师，负责将初步的业务想法细化为具体、可执行的业务需求。请从商业价值、用户痛点、市场机会等角度分析，并提供详细的需求描述。";
        
        String userPrompt = "请帮我细化以下业务需求，提供详细的业务背景、目标用户、核心功能、预期效果等信息：\n\n" + initialRequirement;
        
        AiRequest request = new AiRequest();
        request.setModel(null); // 使用配置中的默认模型
        request.setSystem(systemPrompt);
        request.setContent(userPrompt);
        request.setMaxTokens(1000);
        request.setTemperature(0.7);
        
        AiResponse response = aiApiService.callQwen(request);
        return extractContentFromResponse(response);
    }
    
    /**
     * 使用豆包制定产品方案
     */
    private String callDoubaoForProductPlan(String businessRequirement) throws IOException {
        String systemPrompt = "你是资深的产品经理，负责将业务需求转化为具体的产品方案。请设计产品的功能架构、用户流程、界面原型、商业模式等，并考虑技术可行性和用户体验。";
        
        String userPrompt = "基于以下业务需求，请制定详细的产品方案：\n\n" + businessRequirement;
        
        AiRequest request = new AiRequest();
        request.setModel(null); // 使用配置中的默认模型
        request.setSystem(systemPrompt);
        request.setContent(userPrompt);
        request.setMaxTokens(1000);
        request.setTemperature(0.7);
        
        AiResponse response = aiApiService.callDoubao(request);
        return extractContentFromResponse(response);
    }
    
    /**
     * 使用DeepSeek提供开发实现方案
     */
    private String callDeepSeekForDevelopment(String businessRequirement, String productPlan) throws IOException {
        String systemPrompt = "你是资深的软件架构师和开发者，负责根据业务需求和产品方案，提供详细的技术实现方案。包括技术选型、系统架构、数据库设计、接口定义、开发计划等。";
        
        String userPrompt = "请基于以下业务需求和产品方案，提供详细的技术实现方案：\n\n业务需求：\n" + businessRequirement + 
                           "\n\n产品方案：\n" + productPlan;
        
        AiRequest request = new AiRequest();
        request.setModel(null); // 使用配置中的默认模型
        request.setSystem(systemPrompt);
        request.setContent(userPrompt);
        request.setMaxTokens(1500);
        request.setTemperature(0.7);
        
        AiResponse response = aiApiService.callDeepSeek(request);
        return extractContentFromResponse(response);
    }
    
    /**
     * 使用千问进行QA验收验证
     */
    private String callQwenForQAVerification(String businessRequirement, String productPlan, String developmentImpl) throws IOException {
        String systemPrompt = "你是资深的质量保证专家，负责对业务需求、产品方案和开发实现进行全面的验收验证。请评估实现是否满足原始需求，是否存在风险点，并给出改进建议或确认通过验收。";
        
        String userPrompt = "请对以下内容进行验收验证：\n\n业务需求：\n" + businessRequirement +
                           "\n\n产品方案：\n" + productPlan +
                           "\n\n开发实现方案：\n" + developmentImpl +
                           "\n\n请评估开发实现是否满足业务需求和产品方案，指出潜在问题或风险，并给出验收结论（通过/不通过及原因）。";
        
        AiRequest request = new AiRequest();
        request.setModel(null); // 使用配置中的默认模型
        request.setSystem(systemPrompt);
        request.setContent(userPrompt);
        request.setMaxTokens(1000);
        request.setTemperature(0.7);
        
        AiResponse response = aiApiService.callQwen(request);
        return extractContentFromResponse(response);
    }
    
    /**
     * 从AI响应中提取内容
     */
    private String extractContentFromResponse(AiResponse response) {
        if (response == null || response.getChoices() == null || response.getChoices().length == 0) {
            return "";
        }
        
        String content = response.getChoices()[0].getMessage().getContent();
        if (content == null) {
            return "";
        }
        
        // 去除可能的多余空格和换行符
        return content.trim();
    }
    
    /**
     * 检查QA验证结果是否可接受
     */
    private boolean isAcceptable(String qaVerification) {
        // 简单的检查逻辑：如果包含"不通过"或"拒绝"等关键词，则认为不可接受
        String lowerCaseVerification = qaVerification.toLowerCase();
        return !(lowerCaseVerification.contains("不通过") || 
                 lowerCaseVerification.contains("拒绝") || 
                 lowerCaseVerification.contains("不符合") ||
                 lowerCaseVerification.contains("需要改进") ||
                 lowerCaseVerification.contains("存在问题"));
    }
    
    /**
     * 记录协作步骤日志
     */
    private void logStep(CollaborationResult result, String stage, String description, String content) {
        Map<String, Object> stepLog = new HashMap<>();
        stepLog.put("stage", stage);
        stepLog.put("description", description);
        stepLog.put("content", content);
        stepLog.put("timestamp", System.currentTimeMillis());
        result.getCollaborationLog().add(stepLog);
    }
    
    /**
     * 调用特定AI接口
     */
    public Object callSpecificAi(AiRequest request) throws IOException {
        // 这里可以根据模型名称决定调用哪个AI平台
        // 为了演示，我们简单地尝试所有平台
        try {
            // 尝试调用千问
            if (request.getModel() != null && request.getModel().toLowerCase().contains("qwen")) {
                return aiApiService.callQwen(request);
            }
            // 尝试调用豆包
            else if (request.getModel() != null && request.getModel().toLowerCase().contains("doubao")) {
                return aiApiService.callDoubao(request);
            }
            // 尝试调用DeepSeek
            else if (request.getModel() != null && request.getModel().toLowerCase().contains("deepseek")) {
                return aiApiService.callDeepSeek(request);
            }
            // 如果没有指定特定模型，可以使用默认行为
            else {
                // 默认使用千问
                return aiApiService.callQwen(request);
            }
        } catch (IOException e) {
            log.error("Error calling specific AI", e);
            throw e;
        }
    }
}