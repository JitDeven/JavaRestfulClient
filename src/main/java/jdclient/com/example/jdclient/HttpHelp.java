package jdclient.com.example.jdclient;

import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;
import java.lang.*;
import org.apache.commons.codec.*;

public  class HttpHelp {

    public String read(InputStream input) {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
        }
        return sb.toString();
    }

    //加密
    public  String getBase64(String str) {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    public  String getSha1(String str) {
//        if (null == str || 0 == str.length()) {
//            return null;
//        }
//        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//                'a', 'b', 'c', 'd', 'e', 'f'};
//        try {
//            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
//            mdTemp.update(str.getBytes("UTF-8"));
//
//            byte[] md = mdTemp.digest();
//            int j = md.length;
//            char[] buf = new char[j * 2];
//            int k = 0;
//            for (int i = 0; i < j; i++) {
//                byte byte0 = md[i];
//                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
//                buf[k++] = hexDigits[byte0 & 0xf];
//            }
//            return new String(buf);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
        try {
            MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
            digest.update(str.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

//    public static byte[] sha(byte[] data) {
//        return getShaDigest().digest(data);
//    }


    public static String InVoiceXml()
    {
        String xml=new String();
        xml="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ProductCirculationData>\n" +
                "    <TransferSubject>\n" +
                "        <DocumentID>单据流水号</DocumentID>\n" +
                "        <DataSender>数据发送方</DataSender>\n" +
                "        <DataReceiver>数据接收方</DataReceiver>\n" +
                "        <DataGenerationTime>数据生成时间</DataGenerationTime>\n" +
                "        <DocumentType>发票核销申请</DocumentType>\n" +
                "    </TransferSubject>\n" +
                "    <VerifyRuleInfo>\n" +
                "        <!-- 供应商简码 -->\n" +
                "        <VendorCode>code001</VendorCode>\n" +
                "        <!-- 供应商名称 -->\n" +
                "        <VendorName>宝洁</VendorName>\n" +
                "        <!-- 唯一识别码 -->\n" +
                "        <UniqueCode>000111</UniqueCode>\n" +
                "        <!-- 业务类型 01核销申请 02作废 -->\n" +
                "        <BusinessType>01</BusinessType>\n" +
                "        <!-- 总金额 -->\n" +
                "        <TotalAmount>10000</TotalAmount>\n" +
                "        <BusinessInfos>\n" +
                "            <BusinessInfo>\n" +
                "                <SettleNo>1001</SettleNo>\n" +
                "                <PayID>10001</PayID>\n" +
                "                <RowType>单据类型</RowType>\n" +
                "                <BusinessCode>100001</BusinessCode>\n" +
                "                <PurchaseNo>采购单号</PurchaseNo>\n" +
                "                <BillNo>供应商SO编号</BillNo>\n" +
                "                <Amount>金额</Amount>\n" +
                "                <Remark>备注</Remark>\n" +
                "            </BusinessInfo>\n" +
                "            <BusinessInfo>\n" +
                "                <SettleNo>1002</SettleNo>\n" +
                "                <PayID>10002</PayID>\n" +
                "                <RowType>单据类型</RowType>\n" +
                "                <OrderCode>100002</OrderCode>\n" +
                "                <PurchaseNo>采购单号</PurchaseNo>\n" +
                "                <BillNo>供应商SO编号</BillNo>\n" +
                "                <Amount>金额</Amount>\n" +
                "                <Remark>备注</Remark>\n" +
                "            </BusinessInfo>\n" +
                "        </BusinessInfos>\n" +
                "        <InvoiceInfos>\n" +
                "            <InvoiceInfo>\n" +
                "                <InvoiceNo>发票号码</InvoiceNo>\n" +
                "                <InvoiceCode>发票代码</InvoiceCode>\n" +
                "                <Amount>价税合计金额</Amount>\n" +
                "            </InvoiceInfo>\n" +
                "            <InvoiceInfo>\n" +
                "                <InvoiceNo>发票号码</InvoiceNo>\n" +
                "                <InvoiceCode>发票代码</InvoiceCode>\n" +
                "                <Amount>价税合计金额</Amount>\n" +
                "            </InvoiceInfo>\n" +
                "        </InvoiceInfos>\n" +
                "    </VerifyRuleInfo>\n" +
                "</ProductCirculationData>\n";

        return xml;
    }
}
