package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class RestService {
    public static OrderResponse orderProducts(AuthorizeResponse authorizeResponse, List<Product> productList) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost("https://secure.snd.payu.com/api/v2_1/orders");
        postRequest.addHeader("Accept", "application/json");
        postRequest.addHeader("Content-Type", "application/json");
        postRequest.addHeader("Authorization", authorizeResponse.getTokenType() + " " + authorizeResponse.getAccessToken());
        OrderRequest orderRequest = testOrderRequest(productList);


        Gson gson = new Gson();
        String orderRequestJSON = gson.toJson(orderRequest);
        System.out.println();
        System.out.println("OrderRequestJSON");
        System.out.println(orderRequestJSON);
        postRequest.setEntity(new StringEntity(orderRequestJSON));

        HttpResponse response = httpClient.execute(postRequest);
        if (response.getStatusLine().getStatusCode() != 302) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        }

        InputStreamReader inputStreamReader = new InputStreamReader((response.getEntity().getContent()));
        ObjectMapper mapper = new ObjectMapper();
        OrderResponse orderResponse = mapper.readValue(inputStreamReader, OrderResponse.class);
        System.out.println();
        System.out.println(orderResponse);
        return orderResponse;
    }

    private static OrderRequest testOrderRequest(List<Product> productList) {
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setNotifyUrl("http://payuclient.mystore");
        orderRequest.setCustomerIp("127.0.0.1");
        orderRequest.setMerchantPosId("301703");
        orderRequest.setDescription("RTV market");
        orderRequest.setCurrencyCode("PLN");
//        List<Product> products = new ArrayList<Product>();
//        products.add(new Product("Wireless Mouse for Laptop", 21000, 1));
//        products.add(new Product("Mouse", 11000, 2));
        int totalAmount = 0;
        for (Product product : productList) {
            totalAmount += product.getQuantity()*product.getUnitPrice();
        }
        orderRequest.setProducts(productList);
        orderRequest.setTotalAmount(totalAmount);
        orderRequest.setPayMethods(new PayMethod("PBL", "t"));
        return orderRequest;
    }

    public static AuthorizeResponse getAuthorization() throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost postRequest = new HttpPost("https://secure.snd.payu.com/pl/standard/user/oauth/authorize");
        postRequest.addHeader("Content-Type", "application/x-www-form-urlencoded");
        List nameValuePairs = new ArrayList();
        nameValuePairs.add(new BasicNameValuePair("grant_type", "client_credentials"));
        nameValuePairs.add(new BasicNameValuePair("client_id", "301703"));
        nameValuePairs.add(new BasicNameValuePair("client_secret", "2524f3d446a031bdbd05bf7c42224f76"));
        postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = httpClient.execute(postRequest);
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
        }

        InputStreamReader inputStreamReader = new InputStreamReader((response.getEntity().getContent()));

        ObjectMapper mapper = new ObjectMapper();
        AuthorizeResponse authorizeResponse = mapper.readValue(inputStreamReader, AuthorizeResponse.class);
        System.out.println(authorizeResponse);
        return authorizeResponse;
    }
}
