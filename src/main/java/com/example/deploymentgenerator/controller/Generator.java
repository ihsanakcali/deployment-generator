package com.example.deploymentgenerator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.example.deploymentgenerator.model.Feature;
import java.util.Arrays;
import java.util.List;

@Controller
public class Generator {
    @GetMapping("/")
    public String home(Model model) {
        List<Feature> features = Arrays.asList(
                new Feature("Database", "database"),
                new Feature("Logging", "logging"),
                new Feature("Security", "security")
                // Add more features as needed
        );
        model.addAttribute("features", features);
        return "index";
    }



    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateYaml(@RequestParam(required = false) String[] selectedFeatures) {
        // Generate YAML content based on selectedFeatures
        String yamlContent = generateYamlContent(selectedFeatures);

        // Convert the YAML content to bytes
        byte[] yamlBytes = yamlContent.getBytes();

        // Prepare the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "generated.yaml");

        // Return the file content as a byte array with appropriate headers
        return new ResponseEntity<>(yamlBytes, headers, HttpStatus.OK);
    }

    private String generateYamlContent(String[] selectedFeatures) {
        // Logic to generate YAML file based on selectedFeatures
        // For simplicity, let's just return a dummy YAML content
        StringBuilder yamlBuilder = new StringBuilder();
        yamlBuilder.append("Generated YAML content:\n");
        if (selectedFeatures != null) {
            for (String feature : selectedFeatures) {
                yamlBuilder.append("- ").append(feature).append("\n");
            }
        }
        return yamlBuilder.toString();
    }
}