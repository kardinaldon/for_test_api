import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

private class CrptApi {
    private final Semaphore semaphore;
    private final int maxPermits;
    private final long period;
    private final TimeUnit timeUnit;
    private volatile long lastRefillTime;

    public CrptApi(TimeUnit timeUnit, int requestLimit) {
        this.maxPermits = requestLimit;
        this.period = 1;
        this.timeUnit = timeUnit;
        this.semaphore = new Semaphore(requestLimit);
        this.lastRefillTime = System.currentTimeMillis();
    }

    public Optional<HttpResponse<String>> sendApiCall(String url, String httpMethod, String requestBody) throws Exception {
        if (crptApi.tryAcquire()) {
            HttpClient client = HttpClient.newHttpClient();
            System.out.println(url);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(java.time.Duration.ofSeconds(10))
                    .header("Content-Type", "application/json")
                    .method(httpMethod, HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString());

            return Optional.of(response);
        } else {
            return Optional.empty();
        }

    }

    private boolean tryAcquire() {
        refill();
        return semaphore.tryAcquire();
    }

    private void refill() {
        long now = System.currentTimeMillis();
        long timeElapsed = now - lastRefillTime;
        long timeWindow = timeUnit.toMillis(period);

        if (timeElapsed >= timeWindow) {
            synchronized (this) {
                if (now - lastRefillTime >= timeWindow) {
                    semaphore.drainPermits();
                    semaphore.release(maxPermits);
                    lastRefillTime = now;
                }
            }
        }
    }
    public static class ApiModel {
            private static ApiModel INSTANCE;
            private String jsonApiModel;

            private ApiModel() {}

            public static ApiModel getInstance() {
                if(INSTANCE == null) {
                    INSTANCE = new ApiModel();

                    ObjectMapper mapper = new ObjectMapper();
                    ObjectNode jsonNode = mapper.createObjectNode();
                    jsonNode.put("document_format", "MANUAL");
                    jsonNode.put("product_document",
                            Base64.getEncoder().encodeToString(("product_document").getBytes()));
                    jsonNode.put("product_group", "1 clothes");
                    jsonNode.put("type", "LP_INTRODUCE_GOODS");

                    INSTANCE.jsonApiModel = mapper.writeValueAsString(jsonNode);
                }
                return INSTANCE;
            }

            public String getJsonApiModel(){
                return jsonApiModel;
            }
        }
}
