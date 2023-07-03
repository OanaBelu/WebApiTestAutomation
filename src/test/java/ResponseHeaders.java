import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.entity.ContentType;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ResponseHeaders extends BaseClass {

    public static final String BASE_ENDPOINT = "https://api.github.com";
    CloseableHttpClient client;
    CloseableHttpResponse response;

    @BeforeMethod
    public void setup() {
        client = HttpClientBuilder.create().build();
    }

    @AfterMethod
    public void closeResources() throws IOException {
        if (response != null) {
            response.close();
        }
        if (client != null) {
            client.close();
        }
    }

    @Test()
    public void contentTypeIsJson() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);

        response = client.execute(get);

        Header contentType = response.getEntity().getContentType();
        Assert.assertEquals(contentType.getValue(), "application/json; charset=utf-8");

        ContentType ct = ContentType.getOrDefault(response.getEntity());
        Assert.assertEquals(ct.getMimeType(), "application/json");
    }

    @Test()
    public void serverIsGithub() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);

        response = client.execute(get);

        String headerValue = ResponseUtils.getHeader(response, "Server");

        Assert.assertEquals(headerValue, "GitHub.com");
    }

    @Test()
    public void xRateLimitIsSixty() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);

        response = client.execute(get);

        String limitVal = ResponseUtils.getHeaderJava8Way(response, "x-RateLimit-Sixty");

        Assert.assertEquals(limitVal, "60");
    }

    private String getHeader(CloseableHttpResponse response, String headerName) {
        // Get all headers
        Header[] headers= response.getAllHeaders();
        List<Header> httpHeaders= Arrays.asList(headers);
        String returnHeader = "";

        //Loop over the headers list
        for (Header header: httpHeaders){
            if(headerName.equalsIgnoreCase(header.getName())) {
                returnHeader = header.getValue();

            }
        }
        //If no header found - throw an exception
        if(returnHeader.isEmpty()){
            throw new RuntimeException("Ddn't find the header" + headerName);
        }
        //Return the header
        return returnHeader;
    }
}


