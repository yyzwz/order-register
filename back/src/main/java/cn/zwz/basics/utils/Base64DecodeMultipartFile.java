package cn.zwz.basics.utils;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

import java.io.*;

/**
 * base64转为multipartFile工具类
 * @author 郑为中
 */
public class Base64DecodeMultipartFile implements MultipartFile {

    @ApiModelProperty(value = "图片内容")
    private final byte[] imgContent;

    @ApiModelProperty(value = "图片抬头")
    private final String header;

    private static final String IMAGE_PATH_STEP_STR = ".";

    private static final String IMAGE_PATH_SPLIT_STR = ";";

    private static final String IMAGE_HEADER_SPLIT_STR = "/";

    private static final String IMAGE_TYPE_SPLIT_STR = ":";

    private static final String IMAGE_BASE_SPLIT_STR = ",";

    public Base64DecodeMultipartFile(byte[] imgContent, String header) {
        this.imgContent = imgContent;
        this.header = header.split(IMAGE_PATH_SPLIT_STR)[0];
    }

    @Override
    @ApiOperation(value = "获取图片的大小")
    public long getSize() {
        return imgContent.length;
    }

    @Override
    @ApiOperation(value = "获取图片的输入流")
    public InputStream getInputStream() {
        return new ByteArrayInputStream(imgContent);
    }

    @Override
    @ApiOperation(value = "获取图片的字节流")
    public byte[] getBytes() {
        return imgContent;
    }

    @Override
    @ApiOperation(value = "获取图片名称")
    public String getName() {
        return System.currentTimeMillis() + Math.random() + IMAGE_PATH_STEP_STR + header.split(IMAGE_HEADER_SPLIT_STR)[1];
    }

    @Override
    @ApiOperation(value = "图片转换")
    public void transferTo(File dest) throws IOException, IllegalStateException {
        new FileOutputStream(dest).write(imgContent);
    }

    @Override
    @ApiOperation(value = "判断图片是否为空")
    public boolean isEmpty() {
        return imgContent == null || imgContent.length == 0;
    }

    @Override
    @ApiOperation(value = "获取图片完整路径")
    public String getOriginalFilename() {
        return System.currentTimeMillis() + (int)Math.random() * 10000 + IMAGE_PATH_STEP_STR + header.split(IMAGE_HEADER_SPLIT_STR)[1];
    }

    @Override
    @ApiOperation(value = "获取图片类型")
    public String getContentType() {
        return header.split(IMAGE_TYPE_SPLIT_STR)[1];
    }

    public static MultipartFile base64Convert(String base64) {
        String[] baseStrs = base64.split(IMAGE_BASE_SPLIT_STR);
        byte[] b = Base64.getDecoder().decode(baseStrs[1]);
        for (int i = 0; i < b.length; i++) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        return new Base64DecodeMultipartFile(b, baseStrs[0]);
    }
}

