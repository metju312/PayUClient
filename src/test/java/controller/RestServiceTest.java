package controller;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RestServiceTest {
    @org.junit.Test
    public void orderProducts() throws Exception {
        AuthorizeResponse authorizeResponse = RestService.getAuthorization();

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost("https://secure.snd.payu.com/api/v2_1/orders");
        postRequest.addHeader("Accept", "application/json");
        postRequest.addHeader("Content-Type", "application/json");
        postRequest.addHeader("Authorization", authorizeResponse.getTokenType() + " " + authorizeResponse.getAccessToken());
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("Wireless Mouse for Laptop", 21000, 1));
        products.add(new Product("Mouse", 11000, 2));
        OrderRequest orderRequest = RestService.testOrderRequest(products);

        Gson gson = new Gson();
        String orderRequestJSON = gson.toJson(orderRequest);
        System.out.println();
        System.out.println("OrderRequestJSON");
        System.out.println(orderRequestJSON);
        postRequest.setEntity(new StringEntity(orderRequestJSON));

        HttpResponse response = httpClient.execute(postRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(statusCode, 302);
    }

    @org.junit.Test
    public void getAuthorization() throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost("https://secure.snd.payu.com/pl/standard/user/oauth/authorize");
        postRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
        nameValuePairs.add(new BasicNameValuePair("client_id", "301703"));
        nameValuePairs.add(new BasicNameValuePair("client_secret", "2524f3d446a031bdbd05bf7c42224f76"));
        postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = httpClient.execute(postRequest);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(statusCode, 200);
    }

}