package cn.zwz.modules.file.utils;

import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public class WeChatUploadMeidaUtils {
    /**
     * 新增临时素材
     *
     * @param fileType
     * @param filePath
     * @return
     * @throws Exception
     */
    public static JSONObject UploadMeida(String fileType, String filePath,String token) throws Exception {
        // 返回结果
        String result = null;
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {

            throw new IOException("文件不存在");
        }
        String uploadTempMaterial_url="https://qyapi.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
        uploadTempMaterial_url = uploadTempMaterial_url.replace("ACCESS_TOKEN", token).replace("TYPE", fileType);
        URL url = new URL(uploadTempMaterial_url);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        // 设置请求头信息
        conn.setRequestProperty("Connection", "Keep-Alive");
        conn.setRequestProperty("Charset", "UTF-8");
        // 设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
        // 请求正文信息
        // 第一部分
        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"media\"; filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");
        // 获得输出流
        OutputStream out = new DataOutputStream(conn.getOutputStream());
        // 输出表头
        out.write(sb.toString().getBytes("UTF-8"));
        // 文件正文部分
        // 把文件以流的方式 推送道URL中
        DataInputStream din = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] buffer = new byte[1024];
        while ((bytes = din.read(buffer)) != -1) {
            out.write(buffer, 0, bytes);
        }
        din.close();
        // 结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("UTF-8");
        out.write(foot);
        out.flush();
        out.close();
        if (HttpsURLConnection.HTTP_OK == conn.getResponseCode()) {

            StringBuffer strbuffer = null;
            BufferedReader reader = null;
            try {
                strbuffer = new StringBuffer();
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String lineString = null;
                while ((lineString = reader.readLine()) != null) {
                    strbuffer.append(lineString);

                }
                if (result == null) {
                    result = strbuffer.toString();

                }
            } catch (IOException e) {

                e.printStackTrace();
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject;
    }
}
