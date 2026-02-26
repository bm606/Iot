package com.multiai.collaboration.controller;

import com.multiai.collaboration.entity.AiRequest;
import com.multiai.collaboration.service.CollaborationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/collaboration")
@Slf4j
public class CollaborationController {
    
    @Autowired
    private CollaborationService collaborationService;
    
    @PostMapping("/start")
    public ResponseEntity<?> startCollaboration(@RequestBody Map<String, String> request) {
        try {
            String initialRequirement = request.get("requirement");
            if (initialRequirement == null || initialRequirement.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Requirement is required"));
            }
            
            log.info("Starting collaboration with requirement: {}", initialRequirement);
            
            // 启动协作流程
            Map<String, Object> result = collaborationService.executeCollaboration(initialRequirement);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error starting collaboration", e);
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to start collaboration: " + e.getMessage()));
        }
    }
    
    @PostMapping("/call-ai")
    public ResponseEntity<?> callSingleAi(@RequestBody AiRequest request) {
        try {
            log.info("Calling AI with request: model={}, content length={}", request.getModel(), request.getContent().length());
            
            // 直接调用AI接口
            Object result = collaborationService.callSpecificAi(request);
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error calling AI", e);
            return ResponseEntity.internalServerError().body(Map.of("error", "Failed to call AI: " + e.getMessage()));
        }
    }
}