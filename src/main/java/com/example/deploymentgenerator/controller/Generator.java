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

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Generator {
    @GetMapping("/")
    public String home(Model model) {
        List<Feature> features = Arrays.asList(
                new Feature("feature1", "Feature 1"),
                new Feature("feature2", "Feature 2"),
                new Feature("feature3", "Feature 3")
        );
        model.addAttribute("features", features);
        model.addAttribute("selectedFeatures", new String[] {}); // Initialize selectedFeatures array
        return "index";
    }

    // Map to store YAML content for each feature
    private final Map<String, String> featureYamlMap = new HashMap<>();
    public Generator() {
        // Populate the map with YAML content for each feature
        featureYamlMap.put("feature1", generateFeature1Yaml(null, null)); // Initialize with null placeholders
        featureYamlMap.put("feature2", generateFeature2Yaml(null, null)); // Initialize with null placeholders
        featureYamlMap.put("feature3", generateFeature3Yaml(null, null)); // Initialize with null placeholders
    // Add more feature YAML mappings as needed
}

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateYaml(@RequestParam(required = false) String[] selectedFeatures,
                                               @RequestParam(required = false) String username,
                                               @RequestParam(required = false) String password) {
        // Generate YAML content based on selectedFeatures
        String yamlContent = generateYamlContent(selectedFeatures, username, password);

        // Convert the YAML content to bytes
        byte[] yamlBytes = yamlContent.getBytes(StandardCharsets.UTF_8);

        // Prepare the response headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/x-yaml")); // Set content type to YAML
        headers.setContentDispositionFormData("attachment", "generated.yaml");

        // Return the file content as a byte array with appropriate headers
        return ResponseEntity.ok()
                .headers(headers)
                .body(yamlBytes);
    }

    private String generateYamlContent(String[] selectedFeatures, String username, String password) {
        // Logic to generate YAML content based on selectedFeatures
        // For simplicity, let's just return a dummy YAML content

        StringBuilder yamlBuilder = new StringBuilder();
        yamlBuilder.append("apiVersion: v1\n");
        yamlBuilder.append("kind: ConfigMap\n");
        yamlBuilder.append("metadata:\n");
        yamlBuilder.append("  name: spring-cloud-gateway-config\n");
        yamlBuilder.append("data:\n");
        yamlBuilder.append("  application-config.yaml: |\n");

        if (selectedFeatures != null) {
            for (String feature : selectedFeatures) {
                String featureYaml = featureYamlMap.get(feature);
                if (featureYaml != null) {
                    // Replace placeholders with user-provided values if available
                    if (username != null && password != null) {
                        featureYaml = featureYaml.replace("{{SAMPLE_LOGBACK_USERNAME}}", username)
                                .replace("{{SAMPLE_LOGBACK_PASSWORD}}", password);
                    }
                    yamlBuilder.append(featureYaml).append("\n");
                }
            }
        }

        return yamlBuilder.toString();
    }

    // Method to generate YAML content for feature1
    private String generateFeature1Yaml(String username, String password) {
        StringBuilder feature1Yaml = new StringBuilder();
        feature1Yaml.append("# YAML content for feature1\n");
        feature1Yaml.append("key1: value1\n");
        feature1Yaml.append("key2:\n");
        feature1Yaml.append("  username: ").append(username != null ? username : "{{SAMPLE_LOGBACK_USERNAME}}").append("\n");
        feature1Yaml.append("  password: ").append(password != null ? password : "{{SAMPLE_LOGBACK_PASSWORD}}").append("\n");
        feature1Yaml.append("\n");
        return feature1Yaml.toString();
    }

    // Method to generate YAML content for feature2
    private String generateFeature2Yaml(String username, String password) {
        StringBuilder feature2Yaml = new StringBuilder();
        feature2Yaml.append("# YAML content for feature2\n");
        feature2Yaml.append("key3: value3\n");
        feature2Yaml.append("key4:\n");
        feature2Yaml.append("  - item3\n");
        feature2Yaml.append("  - item4\n");
        feature2Yaml.append("\n");
        return feature2Yaml.toString();
    }

    // Method to generate YAML content for feature2
    private String generateFeature3Yaml(String username, String password) {
        StringBuilder feature3Yaml = new StringBuilder();
        feature3Yaml.append("# YAML content for feature2\n");
        feature3Yaml.append("key5: value5\n");
        feature3Yaml.append("key6:\n");
        feature3Yaml.append("  - item5\n");
        feature3Yaml.append("  - item6\n");
        feature3Yaml.append("\n");
        return feature3Yaml.toString();
    }
}