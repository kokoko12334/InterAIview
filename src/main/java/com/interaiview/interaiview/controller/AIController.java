package com.interaiview.interaiview.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.interaiview.interaiview.dto.QaPairsDTO;
import com.interaiview.interaiview.service.AIService;
import com.interaiview.interaiview.util.FileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.0.146:3000", "https://aiinterview-dcff3.web.app"}, allowedHeaders = "*")
@RequestMapping("/api/ai")
public class AIController {

    private final AIService aiService;

    @PostMapping("/upload")
    public ResponseEntity<?> generateQuestion(@RequestParam("file") MultipartFile file) throws IOException {
        ResponseEntity<String> validationResponse = FileValidator.validateFile(file);

        if (validationResponse != null) {
            return validationResponse;
        }

        String fileType = FileValidator.getFileType(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(aiService.generateQuestion(file, fileType));
    }

    @PostMapping("/result")
    public ResponseEntity<?> generateInterviewResult(@RequestBody QaPairsDTO qas) throws JsonProcessingException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(aiService.generateInterviewResult(qas));
    }
}