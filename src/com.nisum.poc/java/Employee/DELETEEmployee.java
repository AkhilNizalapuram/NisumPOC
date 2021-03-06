package Employee;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;
import static org.testng.Assert.assertEquals;


public class DELETEEmployee {
    private static Logger log = Logger.getLogger(String.valueOf(DELETEEmployee.class));

    protected static Properties prop = null;
    static {
        try {
            prop = PropertiesClass.loader_properties();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deleteEmployee() {

        RestAssured.baseURI = prop.getProperty("URI");

        Response response = null;

        try {
            response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .delete("/delete/2");
        } catch (Exception e) {
            e.printStackTrace();
        }
        int statusCode = response.getStatusCode();
        Headers allHeaders = response.headers();
        String statusLine = response.getStatusLine();
        ResponseBody body = response.getBody();
        String bodyAsString = body.asString();

        assertEquals(statusLine /*actual value*/, "HTTP/1.1 200 OK" /*expected value*/, "Correct status code returned");
        System.out.println("Status Line :" + response.getStatusLine());
        log.info("Verified status line");

        assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
        System.out.println("Status Code :" + response.getStatusCode());
        log.info("Verified status code");

        for (Header header : allHeaders) {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }

        String contentType = response.header("Content-Type");
        assertEquals(contentType /* actual value */, "application/json;charset=utf-8" /* expected value */);
        log.info("Verified Content-Type in Header");

        String serverType = response.header("Server");
        assertEquals(serverType /* actual value */, "nginx/1.16.0" /* expected value */);
        log.info("Verified serverType in Header");

    }

}
