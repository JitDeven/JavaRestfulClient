package jdclient.com.example.jdclient;

import ModelView.*;
import Utility.*;

import org.apache.http.client.*;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.*;
import java.lang.*;


// 1 创建spring.boot 项目作为客户端程序自动运行
// 2 调用远程webpai获取Token值

@SpringBootApplication
public class JdclientApplication {

    public static void main(String[] args) {
        VistApi();
        SpringApplication.run(JdclientApplication.class, args);
    }

    private static void VistApi() {
        System.out.println("-----------------------Start VistApi-----------------------");
        HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost("http://localhost:8080/token/checkIn");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        String auth = "wangyg" + ":" + "123456";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Bearer " + new String(encodedAuth);
        // String authHeader = "Basic " + new String(encodedAuth);

        httpPost.setHeader("Authorization", authHeader);
        //set params
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("name", "inputParam"));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(params));
        } catch (Exception e) {
            System.out.println("Exception httpPost.setEntity");
        }

        //response
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (Exception e) {
            System.out.println("Exception httpClient.execute\n"+e.getMessage());
        }

        //get response into String
        String temp = "";
        try {
            HttpEntity entity = response.getEntity();
            temp = EntityUtils.toString(entity, "UTF-8");
            System.out.println("#####################Response value is#####################: "+temp);
        } catch (Exception e) {
            System.out.println("Exception httpClient.execute\n"+e.getMessage());
        }

        System.out.println("-----------------------End VistApi-----------------------");
    }

    private  void SerializeTest()
    {
        // 序列化和反序列號 https://blog.csdn.net/xiaobao5214/article/details/52210152

        TokenResult token = new TokenResult();
        token.setTtl(1000);
        token.setResult("successful");
        token.setMsg("this is test value");
        token.setToken("eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNqqVsoqyVSyUjJIS7FMNTAw101JTTPQNUm0tNS1NE02100zMDJKMjRNTDFOMlLSUcpMLFGyMjQ1NDIxNzCyMNNRKi5NAuquzMzJBMkWFwM5Xi4Kri6eCu5B_qEBQMGy1LyU_CLn_JRUhMLUigKIMaamZmYWZrUAAAAA__8._bF8TFgw4e2YZNaM0qqIpYjMTeAAFWqBg0UQsgo0HIt44DeCoLS5gvKlKxaEhInWeWypAyOX4n8BLG99SnPRDQ");

        try {
            System.out.println("序列化");
            byte[] data = SerializeUtils.serialize(token,TokenResult.class);
            for (byte b : data) {
                System.out.print(b);
            }
            System.out.println();
            System.out.println("反序列化");
            TokenResult user2 = SerializeUtils.deSerialize(data,TokenResult.class);
            System.out.println(token);

        } catch (Exception ex) {

        }
    }
}
