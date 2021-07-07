package examples_socket.gubernik.bank.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import examples_socket.gubernik.bank.model.MainPageRequest;
import examples_socket.gubernik.bank.model.MainPageResponse;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class MainHandler implements HttpHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        try {

            switch (method) {
                case "GET":
                    System.out.println();
                    break;
                case "POST":

                    MainPageRequest request = getRequest(httpExchange);

                    MainPageResponse response = new MainPageResponse();
                    response.setHelloText("Привет " + request.getUserName());
                    writeResponse(response, httpExchange);
                    break;
                default:
                    throw new RuntimeException("Не поддерживается метод " + method);
            }
        } catch (Throwable t) {
            httpExchange.sendResponseHeaders(500, 1);
        }
    }

    private MainPageRequest getRequest(HttpExchange httpExchange) {
        try {
            return objectMapper.readValue(httpExchange.getRequestBody(), MainPageRequest.class);
        } catch (IOException e) {
            System.out.println("Ошибка чтения тела запроса");
            throw new RuntimeException();
        }
    }

    private String objectToJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            System.out.println("Ошибка записи тела ответа");
            throw new RuntimeException();
        }
    }

    private void writeResponse(Object body, HttpExchange httpExchange) {
        try (OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpExchange.getResponseBody())) {
            ObjectMapper mapper = new ObjectMapper();
            String responseBody = mapper.writeValueAsString(body);
            outputStreamWriter.write(responseBody);
            httpExchange.sendResponseHeaders(200, responseBody.getBytes(StandardCharsets.UTF_8).length);
        } catch (IOException e) {
            System.out.println("Ошибка записи тела ответа");
            throw new RuntimeException();
        }
    }
}
