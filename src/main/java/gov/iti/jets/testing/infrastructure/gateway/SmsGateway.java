package gov.iti.jets.testing.infrastructure.gateway;

import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SmsGateway {

    public static final String SMS_SERVICE_URL = "http://sms-service.com/api";

    public static final String SEND_SMS_PATH = "/send";

    private static final SmsGateway INSTANCE = new SmsGateway(SMS_SERVICE_URL);
    private final String smsServiceUrl;

    public SmsGateway(String smsServiceUrl) {
        this.smsServiceUrl = smsServiceUrl + SEND_SMS_PATH; // http://sms-service/api/send
    }

    public static SmsGateway getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public void sendSms(String phoneNumber, String message) {
        // Spring -> RestTemplate, WebClient
        // OkHttp...
        HttpClient httpClient = HttpClient.newHttpClient();
        // Json libraries -> Jackson
        String postBodyJson = """
                {
                "phoneNumber": "%s",
                "message": "%s"
                }
                """.formatted(phoneNumber, message);

        HttpRequest postRequest = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(postBodyJson))
                .uri(URI.create(smsServiceUrl))
                .build();

        HttpResponse<String> response =
                httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }
}
