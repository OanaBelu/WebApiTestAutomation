import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class Get200a extends BaseClass {

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

    @Test
    public void baseURLReturn200() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT);

        response = client.execute(get);
        int actualStatus = response.getStatusLine().getStatusCode();

        Assert.assertEquals(actualStatus, 200);
    }

    @Test
    public void rateLimitReturn200() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/rate_limit");

        response = client.execute(get);
        int actualStatus = response.getStatusLine().getStatusCode();

        Assert.assertEquals(actualStatus, 200);
    }

    @Test
    public void searchReposReturn200() throws IOException {
        HttpGet get = new HttpGet(BASE_ENDPOINT + "/search/repositories?q=java");

        response = client.execute(get);
        int actualStatus = response.getStatusLine().getStatusCode();

        Assert.assertEquals(actualStatus, 200);
    }
}

