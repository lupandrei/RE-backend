package com.example.rebackend.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

import com.example.rebackend.dto.ProjectDTO;
import com.example.rebackend.dto.SkillsDTO;
import com.example.rebackend.model.Project;
import com.example.rebackend.model.Skill;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OpenAICVSummary {

    public static void main(String[] args) throws Exception {
        String apiKey = "9f6d307eef4148c2b752346605bb8037";

        // CV Data
        String name = "John Doe";
        String university = "BSc in Computer Science";
        var project = new ProjectDTO();
        project.setName("Online shop");
        project.setDescription("this projects consists of a web application that allows users to buy and list online items");
        var projects = List.of(project);
        var skill1 = new SkillsDTO();
        skill1.setName("React");
        skill1.setLevel(3);
        var skill2 = new SkillsDTO();
        skill2.setName("Java");
        skill2.setLevel(4);
        var skills = List.of(skill1, skill2);

        // Prepare the prompt
        String prompt = String.format("""
                Generate a professional CV summary based on the following details:
                Name: %s
                University: %s
                Projects: %s
                Skills: %s
                """, name, university, String.join(", ", projects.toString()), String.join(", ", skills.toString()));

        String promptString = """
                Generate a professional CV summary based on the following details:
                Name: John Doe
                University: UBB
                Projects: Online shop, description:users are allowed to buy and sell things
                Skills: React, level=beginner, Java, level=intermediate
                """;

        var userAIMessage = new AIMessage();
        userAIMessage.setRole("user");
        userAIMessage.setContent(promptString);

        Gson gson = new Gson();
        var prompt1 = gson.toJsonTree(List.of(userAIMessage));

        // Create the JSON request
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-4o");
        requestBody.add("messages", prompt1);
        requestBody.addProperty("temperature", 0.7);
        requestBody.addProperty("max_tokens", 256);


        // Send the request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.aimlapi.com/v1/chat/completions"))
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Parse the response
        if (response.statusCode() == 201 || response.statusCode() == 200) {
            JsonObject responseJson = JsonParser.parseString(response.body()).getAsJsonObject();
            var response1 = responseJson.getAsJsonArray("choices");

            var generatedText = responseJson
                    .getAsJsonArray("choices")
                    .get(0)
                    .getAsJsonObject()
                    .get("message")
                    .getAsJsonObject()
                    .get("content")
                    .getAsString();

            System.out.println("Generated CV Summary:\n" + generatedText);
        } else {
            System.err.println("Error: " + response.statusCode() + " - " + response.body());
        }
    }
}

