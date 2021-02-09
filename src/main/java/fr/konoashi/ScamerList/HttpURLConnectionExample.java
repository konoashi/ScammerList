package fr.konoashi.ScamerList;




import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionExample {
    
       public static final String DEFAULT_USER_AGENT = "Java-Async-Http";

    private final Map<String, String> headers; // HTTP request headers

    private int connectionTimeout = 20000; // in milliseconds
    private int dataRetrievalTimeout = 20000; // in milliseconds
    private boolean followRedirects = true; // automatically follow HTTP redirects?

    public HttpURLConnectionExample() {
        headers = Collections.synchronizedMap(new LinkedHashMap<String, String>());
        setUserAgent(DEFAULT_USER_AGENT);
    }



    public static String main(String url) throws IOException {


        System.out.println("GET DONE");
        return sendGET(url);


    }

    private static String sendGET(String GET_URL) throws IOException {
        URL obj = new URL(GET_URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();





            // print result

            return response.toString();





    }
    public void setUserAgent(String userAgent) {
        headers.put("User-Agent", userAgent);
    }


}
