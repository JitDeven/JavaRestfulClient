package jdclient.com.example.jdclient;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;
import java.io.*;
import java.nio.charset.StandardCharsets;
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
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            byte digestBytes[] = digest.digest(str.getBytes(StandardCharsets.UTF_8)); // SHA-1 ( password )
            String passwdDigest = Base64.encode(digestBytes); // Base64 ( SHA-1 ( password ) )
            return passwdDigest;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /*
    * 发票核销request xml
    * */
    public static String InVoiceXml() {
        String xml = new String();
        xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<ProductCirculationData>\n" +
                "    <TransferSubject>\n" +
                "        <DocumentID>单据流水号</DocumentID>\n" +
                "        <DataSender>数据发送方</DataSender>\n" +
                "        <DataReceiver>数据接收方</DataReceiver>\n" +
                "        <DataGenerationTime>数据生成时间</DataGenerationTime>\n" +
                "        <DocumentType>发票核销申请</DocumentType>\n" +
                "    </TransferSubject>\n" +
                "    <VerifyRuleInfo>\n" +
                "        <VendorCode>code001</VendorCode>\n" +
                "        <VendorName>宝洁</VendorName>\n" +
                "        <UniqueCode>000111</UniqueCode>\n" +
                "        <BusinessType>01</BusinessType>\n" +
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

    /*
     *对账request xml
    * */
    public static String AccountXml() {
        String xml = new String();
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
                "        <VendorCode>code001</VendorCode>\n" +
                "        <VendorName>宝洁</VendorName>\n" +
                "        <UniqueCode>000111</UniqueCode>\n" +
                "        <BusinessType>01</BusinessType>\n" +
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
