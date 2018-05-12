package Utility;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import  org.apache.http.auth.AuthScope;

import  org.apache.http.auth.UsernamePasswordCredentials;

// http://www.baeldung.com/httpclient-4-basic-authentication
//https://blog.csdn.net/cao478208248/article/details/42214769
public class WebHttpHlep {

    public static String HttPost(String url,String contentType,HashMap<String,String> requestParams,boolean isAuth,String userName,String pwd)
    {
        HttpClient httpClient = new DefaultHttpClient();

        // HttpPost httpPost = new HttpPost("http://localhost:8080/token/checkIn");
        HttpPost httpPost = new HttpPost(url);
        // httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        httpPost.setHeader("Content-Type", contentType);

        // Basci验证
//        String auth = "wangyg" + ":" + "123456";
//        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
//        String authHeader = "Bearer " + new String(encodedAuth);
//        httpPost.setHeader("Authorization", authHeader);

        if(isAuth) {
            // 用户名和密码验证
//            CredentialsProvider provider = new BasicCredentialsProvider();
//            UsernamePasswordCredentials credentials
//                    = new UsernamePasswordCredentials(userName, pwd);
//            provider.setCredentials(AuthScope.ANY, credentials);
        }
        //set params
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        // params.add(new BasicNameValuePair("name", "inputParam"));

        if (null!=requestParams)
        {
            Iterator i = requestParams.entrySet().iterator();
            while (i.hasNext()) {
                Object obj = i.next();
                String key = obj.toString();
                String value = requestParams.get(key).toString();
                params.add(new BasicNameValuePair(key, value));
            }
        }


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
            return temp;
        } catch (Exception e) {
            System.out.println("Exception httpClient.execute\n"+e.getMessage());
        }
        return  "";

    }

}
