package com.example.rebackend.client;

import com.example.rebackend.dto.AIMessage;
import com.example.rebackend.dto.ProjectDTO;
import com.example.rebackend.dto.SkillsDTO;
import com.example.rebackend.dto.UniversityDTO;
import com.example.rebackend.model.StudentAccount;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static com.google.gson.JsonParser.parseString;

@Component
public class FreeAIClient {

    private static final String apiKey = "9f6d307eef4148c2b752346605bb8037";
    private static final String prompt = """
            Generate a professional CV summary based on the following details:
            Name:%s
            University:%s
            Projects:%s
            Skills:%s
            """;

    public String retrieveGeneratedDescription(StudentAccount studentAccount, List<UniversityDTO> universities, List<SkillsDTO> skills, List<ProjectDTO> projects) throws IOException, InterruptedException {
        var userAIMessage = new AIMessage();
        userAIMessage.setRole("user");
        userAIMessage.setContent(createCompletePrompt(prompt, studentAccount, universities, skills, projects));

        var requestBody = prepareRequestBody(userAIMessage);

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
            return parseString(response.body())
                    .getAsJsonObject()
                    .getAsJsonArray("choices")
                    .get(0)
                    .getAsJsonObject()
                    .get("message")
                    .getAsJsonObject()
                    .get("content")
                    .getAsString();
        } else {
            return response.body();
        }
    }

    private static JsonObject prepareRequestBody(AIMessage userAIMessage) {
        var gson = new Gson();
        var prompt = gson.toJsonTree(List.of(userAIMessage));

        var requestBody = new JsonObject();
        requestBody.addProperty("model", "gpt-4o");
        requestBody.add("messages", prompt);
        requestBody.addProperty("temperature", 0.7);
        requestBody.addProperty("max_tokens", 256);
        return requestBody;
    }

    private String createCompletePrompt(String prompt, StudentAccount studentAccount, List<UniversityDTO> universities, List<SkillsDTO> skills, List<ProjectDTO> projects) {
        return String.format(prompt,
                studentAccount.getFirstName() + ' ' + studentAccount.getLastName(),
                universities,
                projects,
                skills);
    }

}
