package cn.zwz.basics.security.utils;

import java.io.*;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.*;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class WeiChatUtils implements X509TrustManager {

    @ApiModelProperty(value = "朗世企业ID")
    public static final String YH_CORPID = "wwf94bb44e76e308f8";

    @ApiModelProperty(value = "企业微信密匙")
    public static final String YHYY_CORPSECRET = "gK0Ko_byZgG7R84QEyLvs3KgAIocSoYeuZh1wg-AJD4";

    /**
     * 企业微信 艺涵 应用Token
     * @return
     */
    public static String getToken(){
        String s= httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=" + YH_CORPID + "&corpsecret=" + YHYY_CORPSECRET,"GET",null);
        JSONObject err = JSON.parseObject(s);
        if(err.getString("errmsg").equals("ok")){
            return err.getString("access_token");
        }
        return null;
    }

    public static String getCustomerName(String wxid,String inToken,String outToken) {
        if(wxid.startsWith("wo") || wxid.startsWith("wm")) {
            String s= httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/externalcontact/get?access_token=" + outToken + "&external_userid=" + wxid,"GET",null);
            JSONObject jsonObject = JSON.parseObject(s);
            if(jsonObject.getString("errcode").equals("0")) {
                JSONObject externalContact = jsonObject.getJSONObject("external_contact");
                return externalContact.getString("name");
            }else {
                return "未知";
            }
        }
        if(wxid.startsWith("wb")) {
            return "机器人";
        }
        String s= httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/user/get?access_token=" + inToken + "&userid=" + wxid,"GET",null);
        JSONObject jsonObject = JSON.parseObject(s);
        if(jsonObject.getString("errcode").equals("0")) {
            return jsonObject.getString("name");
        }else {
            return "未知";
        }
    }


    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    /*
     * 处理https GET/POST请求
     * 请求地址、请求方法、参数
     * */
    public static String httpsRequest(String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer=null;
        try{
            //创建SSLContext
            SSLContext sslContext=SSLContext.getInstance("SSL");
            TrustManager[] tm={new WeiChatUtils()};
            //初始化
            sslContext.init(null, tm, new java.security.SecureRandom());;
            //获取SSLSocketFactory对象
            SSLSocketFactory ssf=sslContext.getSocketFactory();
            URL url=new URL(requestUrl);
            HttpsURLConnection conn=(HttpsURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            //设置当前实例使用的SSLSoctetFactory
            conn.setSSLSocketFactory(ssf);
            conn.connect();
            //往服务器端写内容
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is=conn.getInputStream();
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Wechatdepartment {
        //   部门名称
        private String name;
        //    英文名称
        private String name_en;
        //    父部门id
        private int parentid;
        //   在父部门中的次序值
        private int order;
        //    部门id
        private int id;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class WeChatUser {
        //    成员名称。长度为1~64个utf8字符
        private String name;
        //    成员别名。长度1~32个utf8字符
        private String alias;
        //    手机号码。企业内必须唯一，mobile/email二者不能同时为空
        private String mobile;
        //    用户id
        private String userid;
        //    成员所属部门id列表,不超过100个
        private String[] department;
        //    部门内的排序值，默认为0
        private String[] order;
        //    职务信息
        private  String position;
        //    性别。1表示男性，2表示女性
        private  String gender;
        //    邮箱
        private  String email;
        //    个数必须和参数department的个数一致，
//    表示在所在的部门内是否为上级。1表示为上级，0表示非上级。
        private  String is_leader_in_dept;
        //    主部门
        private  String main_department;
        //    批量删除id列表
        private List<String> useridlist;
        //    座机
        private String telephone;

        // "external_profile":{"external_attr":[{"type":0,"name":"短号","text":{"value":"649507"}}]
        private ExternalProfile external_profile;
    }

    @Data
    private static class ExternalProfile {
        private ExternalAttr[] external_attr;
    }

    @Data
    private static class ExternalAttr {
        private String type;
        private ExternalText text;
        private String name;
    }

    @Data
    private static class ExternalText {
        private String value;
    }
}

