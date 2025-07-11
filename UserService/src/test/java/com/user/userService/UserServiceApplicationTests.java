package com.user.userService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.userService.models.ECommerceUser;
import com.user.userService.models.Review;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

// import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserServiceApplicationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    private <T> ResponseEntity<T> getAuthorizedResponse(String url, HttpMethod method, Class<T> responseType, String username, String password) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        HttpEntity<Void> request = new HttpEntity<>(headers);
             
        return restTemplate.exchange(url, method, request, responseType);
    }

    @Test
    void contextLoads() {
        System.out.println("Context loads successfully");
    }

    @Test
    void testReturnCurrentUser() {
        ResponseEntity<String> response = getAuthorizedResponse("/me", HttpMethod.GET, String.class, "user", "password");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        String responseBody = response.getBody();
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> jsonResponse = new ObjectMapper().readValue(responseBody, Map.class);

            assertThat(jsonResponse).isNotNull();
            assertThat(jsonResponse.get("username")).isEqualTo("user");
            assertThat(jsonResponse.get("password")).isNull();
            assertThat(jsonResponse.get("enabled")).isEqualTo(true);

            @SuppressWarnings("unchecked")
            List<Map<String, String>> authorities = (List<Map<String, String>>) jsonResponse.get("authorities");
            assertThat(authorities).isNotNull();
            assertThat(authorities).hasSize(1);
            assertThat(authorities.get(0).get("authority")).isEqualTo("ROLE_USER");

        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
            assertThat(false).isTrue(); // Fail the test if exception occurs
        }
    }

    @Test
    void testSignUpUserAndUserExists() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String requestBody = "username=testuser&email=testuser@example.com&password=testpassword&firstName=Test&lastName=User";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Void> response = restTemplate.postForEntity("/register/", request, Void.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        // Verify the user exists using the GET endpoint
        ResponseEntity<String> response1 = getAuthorizedResponse(
            "/internal/user?username=user",
            HttpMethod.GET,
            String.class,
            "user",
            "password"
        );

        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);

        try {
            // Parse the JSON response as a Map
            @SuppressWarnings("unchecked")
            Map<String, Object> userJson = new ObjectMapper().readValue(response1.getBody(), Map.class);

            assertThat(userJson.get("username")).isEqualTo("user");
            assertThat(userJson.get("email")).isEqualTo("user@example.com");
            assertThat(userJson.get("firstName")).isEqualTo("UserFirstName");
            assertThat(userJson.get("lastName")).isEqualTo("UserLastName");
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
            assertThat(false).isTrue(); // Fail the test if exception occurs
        }
    }

    @Test
    void testAddReview() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("user", "password");

        // Add 3 reviews to vehicleId 123
        for (int i = 1; i <= 3; i++) {
            String requestBody = String.format("vehicleId=123&reviewTitle=Review%d&reviewBody=This is review %d for vehicle 123&starRating=4.%d", i, i, i);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Void> response = restTemplate.postForEntity("/addReview", request, Void.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }

        // Add 2 reviews to vehicleId 456
        for (int i = 1; i <= 2; i++) {
            String requestBody = String.format("vehicleId=456&reviewTitle=Review%d&reviewBody=This is review %d for vehicle 456&starRating=3.%d", i, i, i);
            HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Void> response = restTemplate.postForEntity("/addReview", request, Void.class);
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        }



        // now get the reviews
        ResponseEntity<String> response = restTemplate.exchange(
            "/internal/reviews?vehicleId=123",
            HttpMethod.GET,
            new HttpEntity<>(headers),
            String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            List<Map<String, Object>> reviewsList = objectMapper.readValue(response.getBody(), List.class);

            assertThat(reviewsList).isNotNull();
            assertThat(reviewsList.size()).isEqualTo(3);

            for (int i = 0; i < reviewsList.size(); i++) {
                Map<String, Object> review = reviewsList.get(i);
                assertThat(review.get("vehicleId")).isEqualTo("123");
                assertThat(review.get("reviewTitle")).isEqualTo("Review" + (i + 1));
                assertThat(review.get("reviewBody")).isEqualTo("This is review " + (i + 1) + " for vehicle 123");
                assertThat(review.get("starRating")).isEqualTo(4.0 + (i + 1) * 0.1);
            }
        } catch (com.fasterxml.jackson.core.JsonProcessingException e) {
                e.printStackTrace();
                assertThat(false).isTrue(); // Fail the test if exception occurs
            }


    }
}
