package jdclient.com.example.jdclient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.http.entity.StringEntity;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.http.auth.*;

@SpringBootApplication
public class JdclientApplication {

    @Value("${api.server.auth.username}")
    private String ServerUserName;
    @Value("${api.server.auth.password}")
    private String ServerUserPwd;

    @Value("${jdapi.token.url}")
    private String TokenUrl;

    @Value("${jdapi.service.url.voice}")
    private String InvoiceUrl;

    public static void main(String[] args) {
        VistVoice();
        VistAccount();
        SpringApplication.run(JdclientApplication.class, args);
    }

    /*
    * 动态获取Token
    * */
    private static String GetToken() {
        HttpHelp _httpHelp = new HttpHelp();
        String userToken = "";
        HttpClient httpClient = new DefaultHttpClient();
        try {
            String password = _httpHelp.getSha1("JdEdipg537$");
            System.out.println(password);
            String TokenProduUrl = "http://ediws.jd.com/services/auth/user";

            HttpGet req = new HttpGet(TokenProduUrl + "?username=pg&password=" + password);
            req.addHeader("Accept", "application/json");
            HttpResponse resp = httpClient.execute(req);
            HttpEntity entity = resp.getEntity();
            InputStream input = entity.getContent();
            String resultJson = _httpHelp.read(input);
            System.out.println("Token读取结果:" + resultJson);
            //创建json解析器
            JsonParser parse = new JsonParser();
            JsonObject json = (JsonObject) parse.parse(resultJson);
            if (json.get("result").getAsString().equals("Success")) {
                System.out.println("token:" + json.get("token").getAsString());
                userToken = json.get("token").getAsString();
                System.out.println("获取Token:"+userToken);
            } else {
                System.out.println("result:" + json.get("result").getAsString());
            }

            return userToken;

        } catch (IOException ex) {
            System.out.println("发生异常:" + ex);
        }
        System.out.println("-----------------------End GetToken-----------------------");
        return userToken;
    }

    /**
     * 发票核销
     */
    private static  void VistVoice()
    {
        HttpHelp _httpHelp = new HttpHelp();
        HttpClient httpClient = new DefaultHttpClient();
        String userToken=GetToken();
        if (!userToken .equals("")) {

            HttpPost httpPost = new HttpPost("http://ediws.jd.com/services/finance/verificationInvoice");
            httpPost.setHeader("Content-Type", "application/xml");
            httpPost.setHeader("Authorization", "Bearer " + userToken);

            HttpResponse response = null;
            try {
                StringEntity strEntity = new StringEntity(HttpHelp.InVoiceXml(), "utf-8");
                httpPost.setEntity(strEntity);

                response = httpClient.execute(httpPost);
                String temp = "";
                try {
                    HttpEntity entity = response.getEntity();
                    temp = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("发票核销返回的结果: " + temp);
                } catch (Exception e) {
                    System.out.println("Exception httpClient.execute\n" + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("发票核销异常\n" + e.getMessage()); }
        }
        else {
            System.out.println("发票核销Token获取失败");
        }
    }

    /**
     * 对账
     */
    private static  void VistAccount() {
        HttpHelp _httpHelp = new HttpHelp();
        HttpClient httpClient = new DefaultHttpClient();
        String userToken = GetToken();
        if (!userToken.equals("")) {

            HttpPost httpPost = new HttpPost("http://ediws.jd.com/services/finance/accountCheck");
            httpPost.setHeader("Content-Type", "application/xml");
            httpPost.setHeader("Authorization", "Bearer " + userToken);

            HttpResponse response = null;
            try {
                StringEntity strEntity = new StringEntity(HttpHelp.AccountXml(), "utf-8");
                httpPost.setEntity(strEntity);

                response = httpClient.execute(httpPost);
                String temp = "";
                try {
                    HttpEntity entity = response.getEntity();
                    temp = EntityUtils.toString(entity, "UTF-8");
                    System.out.println("对账返回的结果: " + temp);
                } catch (Exception e) {
                    System.out.println("Exception httpClient.execute\n" + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("对账异常\n" + e.getMessage());
            }
        } else {
            System.out.println("对账Token获取失败");
        }
    }
}
