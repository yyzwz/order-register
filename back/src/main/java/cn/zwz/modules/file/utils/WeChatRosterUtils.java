package cn.zwz.modules.file.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;

/**
 * 企业微信人事操作工具类
 */
public class WeChatRosterUtils {

    /**
     * 添加员工
     * @param com 公司
     * @param mobile 电话
     * @param jobNumber 工号
     * @param name 姓名
     * @param depList 部门列表
     * @return
     */
    public static int addUser(String com, String mobile, String jobNumber, String name, List<String> depList){
        String token = "";
        String userId = "";
        String mrdepid = "";
        WeChatUser weChatUser = new WeChatUser();
        weChatUser.setAlias(name);
        weChatUser.setMobile(mobile);
        weChatUser.setName(name);
        weChatUser.setUserid(userId);
        String[] split;
        if(depList.size() == 0) {
            split = new String[1];
            split[0] = mrdepid;
        } else {
            split = new String[depList.size()];
            for(int i = 0 ; i < depList.size() ; i ++) {
                split[i] = depList.get(i);
            }
        }
        weChatUser.setDepartment(split);
        String json = JSON.toJSONString(weChatUser);
        String s= httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/user/create?access_token="+ token,"POST",json);
        JSONObject jsonObject = JSON.parseObject(s);
        return jsonObject.getInteger("errcode");
    }

    /**
     * 更新员工姓名
     * @param com 公司
     * @param userId 用户ID
     * @param name 员工姓名
     * @return
     */
    public static boolean editUserName(String com, String userId, String name) {
        String token = "";
        WeChatUser weChatUser = new WeChatUser();
        weChatUser.setAlias(name);
        weChatUser.setName(name);
        weChatUser.setUserid(userId);
        String json = JSON.toJSONString(weChatUser);
        String s= httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token="+ token,"POST",json);
        JSONObject jsonObject = JSON.parseObject(s);
        String errcode = jsonObject.getString("errcode");
        if(errcode.equals("0")) {
            return true;
        }
        return false;
    }

    public static boolean editUser(String com, String userId, String name, List<Integer> depList) {
        String token = "";
        String mrdepid = "";
        WeChatUser weChatUser = new WeChatUser();
        weChatUser.setAlias(name);
        weChatUser.setName(name);
        weChatUser.setUserid(userId);
        String[] split;
        if(depList.size() > 0) {
            split = new String[depList.size()];
            for(int i = 0 ; i < depList.size() ; i ++){
                split[i] = depList.get(i) + "";
            }
        } else {
            split = new String[1];
            split[0] = mrdepid;
        }
        weChatUser.setDepartment(split);
        String json = JSON.toJSONString(weChatUser);
        String s= httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/user/update?access_token="+ token,"POST",json);
        JSONObject jsonObject = JSON.parseObject(s);
        String errcode = jsonObject.getString("errcode");
        if(errcode.equals("0")) {
            return true;
        }
        return false;
    }

    /**
     * 删除员工
     * @param com 公司
     * @param userId 企微ID
     * @return
     */
    public static boolean deleteUser(String com, String userId) {
        String token = "";
        String s=httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/user/delete?access_token="+token+"&userid="+userId,"GET",null);
        JSONObject jsonObject = JSON.parseObject(s);
        String errcode = jsonObject.getString("errcode");
        if(errcode.equals("0") || errcode.equals("60111")) { // 60111 员工不存在
            return true;
        }
        return false;
    }


    /**
     * 增加部门
     * @param com 公司
     * @param pid 父ID
     * @param name 部门标题
     * @return
     */
    public static int addDep(String com,int pid,String name){
        String token = "";
        WechatAddDepartment wa=new WechatAddDepartment();
        wa.setName(name);
        wa.setParentid(pid + "");
        String json = JSON.toJSONString(wa);
        String s= httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token="+ token,"POST",json);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String errcode = jsonObject.getString("errcode");
        if(errcode.equals("0")) {
            return jsonObject.getInteger("id");
        }else {
            return 0;
        }
    }

    /**
     * 编辑部门
     * @param com 公司
     * @param id 部门ID
     * @param pid 父部门ID
     * @param name 部门名称
     * @return
     */
    public static boolean editDep(String com,int id,int pid,String name){
        String token = "";
        WechatSmallDepartment wechatdepartment= new WechatSmallDepartment();
        wechatdepartment.setId(id);
        wechatdepartment.setName(name);
        wechatdepartment.setParentid(pid);
        String json = JSON.toJSONString(wechatdepartment);
        String s= httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token="+token,"POST",json);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String errcode = jsonObject.getString("errcode");
        if(errcode.equals("0")) {
            return true;
        }
        return false;
    }

    /**
     * 删除部门
     * @param com 公司
     * @param id 部门ID
     * @return
     */
    public static boolean deleteDep(String com,int id){
        String token = "";
        String s= httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/department/delete?access_token="+token+"&id=" + id,"GET",null);
        JSONObject jsonObject = JSONObject.parseObject(s);
        String errcode = jsonObject.getString("errcode");
        if(errcode.equals("0")) {
            return true;
        }
        return false;
    }


    /**
     * 企业微信 艺涵 通讯录Token
     * @return
     */
    public static String getActionToken(){
        String s= httpsRequest("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=&corpsecret=","GET",null);
        JSONObject err = JSON.parseObject(s);
        if(err.getString("errmsg").equals("ok")){
            return err.getString("access_token");
        }
        return null;
    }

    /**
     * 处理https GET/POST请求
     * 请求地址、请求方法、参数
     */
    public static String httpsRequest(String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer=null;
        try{
            //创建SSLContext
            SSLContext sslContext=SSLContext.getInstance("SSL");
            TrustManager[] tm={(TrustManager) new WeChatRosterUtils()};
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
    private static class WechatAddDepartment {
        private String name;
        private String parentid;
    }

    @Data
    public static class WechatSmallDepartment {
        private String name;
        private int id;
        private int parentid;
    }
}
