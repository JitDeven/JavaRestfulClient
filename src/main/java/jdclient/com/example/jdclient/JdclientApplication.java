package jdclient.com.example.jdclient;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import ModelView.*;
import Utility.*;

import  java.io.*;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
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
import org.springframework.beans.factory.annotation.Value;

// JSON
import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.apache.http.auth.*;
// 2 调用远程webpai获取Token值
// 3 每次请求时添加TokenToken

@SpringBootApplication
public class JdclientApplication {

    @Value("${api.server.auth.username}")
    private String ServerUserName;
    @Value("${api.server.auth.password}")
    private String ServerUserPwd;

    @Value("${jdapi.token.url}")
    private String TokenUrl;

    public static void main(String[] args) {
        // VistApi();
        GetToken();
        SpringApplication.run(JdclientApplication.class, args);
    }

    private static void GetToken() {
        HttpHelp _httpHelp = new HttpHelp();
        String userToken = "";
        HttpClient httpClient = new DefaultHttpClient();
        try {

            String password = _httpHelp.getBase64(_httpHelp.getSha1("JdEdipg537$"));
            System.out.println(password);
            String TokenTestUrl="http://ediwstest.jd.com/services/auth/user";
            String TokenProduUrl="http://ediws.jd.com/services/auth/user";

            HttpGet req = new HttpGet(TokenProduUrl+"?username=pg&password=" + password);
            req.addHeader("Accept", "application/json");
            HttpResponse resp = httpClient.execute(req);
            HttpEntity entity = resp.getEntity();
            InputStream input = entity.getContent();
            String resultJson = _httpHelp.read(input);
            System.out.println("Token读取结果:" + resultJson);
            //创建json解析器
            JsonParser parse = new JsonParser();
            JsonObject json = (JsonObject) parse.parse(resultJson);
            if (json.get("result").getAsString() == "Success") {
                System.out.println("token:" + json.get("token").getAsString());
                userToken = json.get("token").getAsString();
            } else {
                System.out.println("result:" + json.get("result").getAsString());
            }

        } catch (IOException ex) {
            System.out.println("发生异常:" + ex);
        }
        System.out.println("-----------------------End GetToken-----------------------");
        // if (userToken != "") {
        if (true) {
            // 发票核销
            HttpPost httpPost = new HttpPost("http://ediws.jd.com/services/finance/verificationInvoice");
            // httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
            httpPost.setHeader("Content-Type", "application/text");
            userToken = "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNqqVsoqyVSyUjJIS7FMNTAw101JTTPQNUm0tNS1NE02100zMDJKMjRNTDFOMlLSUcpMLFGyMjQ1NDIxNzCyMNNRKi5NAuquzMzJBMkWFwM5Xi4Kri6eCu5B_qEBQMGy1LyU_CLn_JRUhMLUigKIMaamZmYWZrUAAAAA__8._bF8TFgw4e2YZNaM0qqIpYjMTeAAFWqBg0UQsgo0HIt44DeCoLS5gvKlKxaEhInWeWypAyOX4n8BLG99SnPRDQ";
            httpPost.setHeader("Authorization", "Bearer " + userToken);

            HttpResponse response = null;
            try {
                 List<NameValuePair> params = new ArrayList<NameValuePair>();
                 //  需要向京东确认requestbody具体的名称，2 TOken验证的值
                 params.add(new BasicNameValuePair("requestbody", "test wyg"));
                httpPost.setEntity(new UrlEncodedFormEntity(params));
                response = httpClient.execute(httpPost);
            } catch (Exception e) {
                System.out.println("Exception httpClient.execute\n" + e.getMessage());
            }

            String temp = "";
            try {
                HttpEntity entity = response.getEntity();
                temp = EntityUtils.toString(entity, "UTF-8");
                System.out.println("发票核销返回的结果: " + temp);
            } catch (Exception e) {
                System.out.println("Exception httpClient.execute\n" + e.getMessage());
            }
        }
    }

    //http://www.baeldung.com/httpclient-post-http-request
    private static void VistApi() {
        System.out.println("-----------------------Start VistApi-----------------------");
        HttpClient httpClient = new DefaultHttpClient();
        /// TODO:读取配置文件https://blog.csdn.net/qq_32786873/article/details/52840745
        HttpPost httpPost = new HttpPost("http://localhost:8080/token/checkIn");
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");

        /// TODO:验证方式一：Basic或Bearer
        String auth = "wangyg" + ":" + "123456";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);
        // String authHeader = "Basic " + new String(encodedAuth);
        httpPost.setHeader("Authorization", authHeader);

        /// TODO:设置参数
        //set params
        // List<NameValuePair> params = new ArrayList<NameValuePair>();
        /// params.add(new BasicNameValuePair("name", "inputParam"));

        // try {
        //     httpPost.setEntity(new UrlEncodedFormEntity(params));
        // } catch (Exception e) {
        //     System.out.println("Exception httpPost.setEntity");
        // }

        //response
        HttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
        } catch (Exception e) {
            System.out.println("Exception httpClient.execute\n" + e.getMessage());
        }

        //get response into String
        String temp = "";
        try {
            HttpEntity entity = response.getEntity();
            temp = EntityUtils.toString(entity, "UTF-8");
            System.out.println("#####################Response value is#####################: " + temp);
        } catch (Exception e) {
            System.out.println("Exception httpClient.execute\n" + e.getMessage());
        }

        System.out.println("-----------------------End VistApi-----------------------");
    }
}
