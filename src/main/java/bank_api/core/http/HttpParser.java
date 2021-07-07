package bank_api.core.http;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class HttpParser {

    private HttpParser() {
    }

    public static HashMap<String, String> parseRequest(HttpExchange exchange) throws HttpParsingException {
        String initial;
        String prms[];
        String cmd[];
        String temp[];
        String method;
        HashMap<String, String> params = new HashMap<>();
        int idx, i;

        initial = exchange.getRequestURI().toString();
        method = exchange.getRequestMethod();
        if (initial == null || initial.length() == 0) {
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }
        if (Character.isWhitespace(initial.charAt(0))) {
            // starting whitespace, return bad request
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

        if (method.equals("GET") || method.equals("POST")) {

            idx = initial.indexOf('?');
            if (idx > 0) {
                prms = initial.substring(idx + 1).split("&");

                for (i = 0; i < prms.length; i++) {
                    temp = prms[i].split("=");
                    if (temp.length == 2) {
                        params.put(temp[0], temp[1]);
                    } else if (temp.length == 1 && prms[i].indexOf('=') == prms[i].length() - 1) {
                        // handle empty string separatedly
                        params.put(temp[0], "");
                    }
                }
            }
        } else {
            // meh not understand, bad request
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
        }

        return params;
    }
}
