package cn.zwz.basics.security.utils;

import cn.zwz.data.utils.ZwzNullUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 企微消息发送工具类
 * @author 郑为中
 */
public class ZwzWxNoticeUtils {

    private static final String BASE_URL = "https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=";

    private static final String USER_ID_ERR = "81013";

    @ApiModelProperty(value = "朗世企业ID")
    public static final String YH_CORPID = "wwf94bb44e76e308f8";

    /**
     * 发送文本消息
     * @param userId 微信ID
     * @param content 内容
     */
    public static String sendInputMessage(int company,String userId,String content,String token){
        if(content == null || ZwzNullUtils.isNull(content)) {
            return "NULL";
        }

        String json = JSON.toJSONString(new ZwzWeChatNoticeInput(userId,"text",YH_CORPID,new ZwzWeChatNoticeInputItem(content),0,1));
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        System.out.println(s);
        JSONObject ans1 = JSONObject.parseObject(s);
        String jsonStr2 = ans1.getString("errcode");
        if(jsonStr2.equals("0")){
            return ans1.getString("msgid");
        } else if(jsonStr2.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 企微上传文件
     * @param path 文件路径
     * @param fileType 文件类型 image file
     * @return
     */
    public static String uploadWeChatFile(String path, String fileType,String token) {
        try {
            JSONObject jsonObject = WeChatUploadMeidaUtils.UploadMeida(fileType,path,token);
            System.out.println(jsonObject.toJSONString());
            String errcode = jsonObject.getString("errcode");
            if(errcode.equals("0")) {
                System.out.println("文件上传成功");
                System.out.println(jsonObject.getString("media_id"));
                return jsonObject.getString("media_id");
            }
            return "文件上传失败";
        } catch (Exception e) {
            return "文件上传失败";
        }
    }

    /**
     * 发送图片消息
     * @param userId 用户微信ID
     * @param mediaId 文件微信ID
     * @return
     */
    public static String sendImageMessage(int company,String userId,String mediaId,String token) {
        ZwzWeiChatNoticeImage image = new ZwzWeiChatNoticeImage(userId,"image",YH_CORPID,new ZwzWeChatNoticeImageItem(mediaId),0,1);
        String json = JSON.toJSONString(image);
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 发送视频消息
     * @param userId 用户微信ID
     * @param mediaId 文件微信ID
     * @return
     */
    public static String sendVideoMessage(int company,String userId,String mediaId,String title,String description,String token) {
        ZwzWeiChatNoticeVideo video = new ZwzWeiChatNoticeVideo(userId,"video",YH_CORPID,new ZwzWeChatNoticeVideoItem(mediaId,title,description),0,1);
        String json = JSON.toJSONString(video);
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 发送文件消息
     * @param userId 用户微信ID
     * @param mediaId 文件微信ID
     * @return
     */
    public static String sendFileMessage(int company,String userId,String mediaId,String token) {
        ZwzWeiChatNoticeFile file = new ZwzWeiChatNoticeFile(userId,"file",YH_CORPID,new ZwzWeChatNoticeFileItem(mediaId),0,1);
        String json = JSON.toJSONString(file);
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 发送文本卡片消息
     * @param userId 用户微信ID
     * @return
     */
    public static String sendTextCardMessage(int company,String userId,String title,String description,String url,String btntxt,String token) {
        ZwzWeiChatNoticeTextCard file = new ZwzWeiChatNoticeTextCard(userId,"textcard",YH_CORPID,new ZwzWeChatNoticeTextCardItem(title,description,url,btntxt),0,1);
        String json = JSON.toJSONString(file);
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 发送图文消息
     * @param userId 用户微信ID
     * @return
     */
    public static String sendTuWenMessage(String userId,String title,String description,String url,String picUrl,String token) {
        List<ZwzWeChatNoticeTuWenItemValue> tuWenList = new ArrayList<>();
        tuWenList.add(new ZwzWeChatNoticeTuWenItemValue(title, description, url, picUrl));
        ZwzWeChatNoticeTuWen file = new ZwzWeChatNoticeTuWen(userId,"news","1000002",new ZwzWeChatNoticeTuWenItem(tuWenList),0,1);
        String json = JSON.toJSONString(file);
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        JSONObject ans1 = JSONObject.parseObject(s);
        String errcode = ans1.getString("errcode");
        if(errcode.equals("0")){
            return ans1.getString("msgid");
        } else if(errcode.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * 发送Markdown消息
     * @param userId 微信ID
     * @param content 内容
     */
    public static String sendMarkdownMessage(int company,String userId,String content,String token){
        if(content == null || ZwzNullUtils.isNull(content)) {
            return "NULL";
        }
        String json = JSON.toJSONString(new ZwzWeChatNoticeMarkdown(userId,"markdown",YH_CORPID,new ZwzWeChatNoticeMarkdownItem(content),0,1));
        String s= WeiChatUtils.httpsRequest(BASE_URL + token,"POST",json);
        System.out.println(s);
        JSONObject ans1 = JSONObject.parseObject(s);
        String jsonStr2 = ans1.getString("errcode");
        if(jsonStr2.equals("0")){
            return ans1.getString("msgid");
        } else if(jsonStr2.equals("81013")) {
            return USER_ID_ERR;
        }
        return "FAIL";
    }

    /**
     * Markdown消息B类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeMarkdown {
        private String touser;
        private String msgtype;
        private String agentid;
        private ZwzWeChatNoticeMarkdownItem markdown;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * Markdown消息A类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeMarkdownItem {
        private String content;
    }

    /**
     * 图文消息B类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeTuWen {
        private String touser;
        private String msgtype;
        private String agentid;
        private ZwzWeChatNoticeTuWenItem news;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 图文消息A类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeTuWenItem {
        private List<ZwzWeChatNoticeTuWenItemValue> articles;
    }

    /**
     * 图文消息A类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeTuWenItemValue {
        private String title;
        private String description;
        private String url;
        private String picurl;
    }

    /**
     * 文本卡片消息B类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeiChatNoticeTextCard {
        private String touser;
        private String msgtype;
        private String agentid;
        private ZwzWeChatNoticeTextCardItem textcard;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 文本卡片消息A类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeTextCardItem {
        private String title;
        private String description;
        private String url;
        private String btntxt;
    }

    /**
     * 文件消息B类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeiChatNoticeFile {
        private String touser;
        private String msgtype;
        private String agentid;
        private ZwzWeChatNoticeFileItem file;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 文件消息A类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeFileItem {
        private String media_id;
    }

    /**
     * 视频消息B类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeiChatNoticeVideo {
        private String touser;
        private String msgtype;
        private String agentid;
        private ZwzWeChatNoticeVideoItem video;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 视频消息A类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeVideoItem {
        private String media_id;
        private String title;
        private String description;
    }

    /**
     * 图片消息B类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeiChatNoticeImage {
        private String touser;
        private String msgtype;
        private String agentid;
        private ZwzWeChatNoticeImageItem image;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 图片消息A类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeImageItem {
        private String media_id;
    }

    /**
     * 普通文本B类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeInput {
        private String touser;
        private String msgtype;
        private String agentid;
        private ZwzWeChatNoticeInputItem text;
        private int safe;
        private int enable_duplicate_check;
    }

    /**
     * 普通文本A类
     */
    @Data
    @AllArgsConstructor
    private static class ZwzWeChatNoticeInputItem {
        private String content;
    }
}
