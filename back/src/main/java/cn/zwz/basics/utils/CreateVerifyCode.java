package cn.zwz.basics.utils;

import cn.hutool.core.util.StrUtil;
import cn.zwz.basics.exception.ZwzException;
import cn.zwz.data.utils.ZwzNullUtils;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 随机字符验证码生成工具类
 * @author 郑为中
 */
public class CreateVerifyCode {

    @ApiModelProperty(value = "验证码")
    private String code = null;

    @ApiModelProperty(value = "验证码字符个数")
    private int charactersNumber = 4;

    @ApiModelProperty(value = "图片高度")
    private int imagePeripheralHeight = 40;

    @ApiModelProperty(value = "图片宽度")
    private int imagePeripheralWidth = 160;

    @ApiModelProperty(value = "干扰线数")
    private int lineCount = 20;

    @ApiModelProperty(value = "图片Buffer")
    private BufferedImage buffImg = null;

    Random random = new Random();

    public CreateVerifyCode() {
        creatImage();
    }

    public CreateVerifyCode(int imageWidth, int imageHeight) {
        this.imagePeripheralWidth = imageWidth;
        this.imagePeripheralHeight = imageHeight;
        creatImage();
    }

    public CreateVerifyCode(int imageWidth, int imageHeight, int codeCount) {
        this.imagePeripheralWidth = imageWidth;
        this.imagePeripheralHeight = imageHeight;
        this.charactersNumber = codeCount;
        creatImage();
    }

    public CreateVerifyCode(int imageWidth, int imageHeight, int codeCount, int lineCount) {
        this.imagePeripheralWidth = imageWidth;
        this.imagePeripheralHeight = imageHeight;
        this.charactersNumber = codeCount;
        this.lineCount = lineCount;
        creatImage();
    }

    public CreateVerifyCode(int imageWidth, int imageHeight, int codeCount, int lineCount, String code) {
        this.imagePeripheralWidth = imageWidth;
        this.imagePeripheralHeight = imageHeight;
        this.charactersNumber = codeCount;
        this.lineCount = lineCount;
        creatImage(code);
    }

    /**
     * 生成图片
     */
    private void creatImage() {
        // 字体的宽度
        int fontWidth = imagePeripheralWidth / charactersNumber;
        // 字体的高度
        int fontHeight = imagePeripheralHeight - 5;
        int codeY = imagePeripheralHeight - 8;

        // 图像buffer
        buffImg = new BufferedImage(imagePeripheralWidth, imagePeripheralHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffImg.getGraphics();
        //Graphics2D g = buffImg.createGraphics();
        // 设置背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, imagePeripheralWidth, imagePeripheralHeight);

        // 设置字体
        //Font font1 = getFont(fontHeight);
        Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
        g.setFont(font);

        // 设置干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(imagePeripheralWidth);
            int ys = random.nextInt(imagePeripheralHeight);
            int xe = xs + random.nextInt(imagePeripheralWidth);
            int ye = ys + random.nextInt(imagePeripheralHeight);
            g.setColor(getRandColor(1, 255));
            g.drawLine(xs, ys, xe, ye);
        }

        // 添加噪点 噪声率
        float yawpRate = 0.01f;
        int area = (int) (yawpRate * imagePeripheralWidth * imagePeripheralHeight);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(imagePeripheralWidth);
            int y = random.nextInt(imagePeripheralHeight);

            buffImg.setRGB(x, y, random.nextInt(255));
        }

        // 得到随机字符
        String str1 = randomStr(charactersNumber);
        this.code = str1;
        for (int i = 0; i < charactersNumber; i++) {
            String strRand = str1.substring(i, i + 1);
            g.setColor(getRandColor(1, 255));
            g.drawString(strRand, i*fontWidth+3, codeY);
        }

    }

    @ApiOperation(value = "图片生成工具类")
    private void creatImage(String code) {
        if(ZwzNullUtils.isNull(code)){
            throw new ZwzException("图形验证码过期了，再生成个新的哦!");
        }
        this.code = code;
        buffImg = new BufferedImage(imagePeripheralWidth, imagePeripheralHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffImg.getGraphics();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, imagePeripheralWidth, imagePeripheralHeight);
        Font font = new Font("Fixedsys", Font.BOLD, imagePeripheralHeight - 5);
        g.setFont(font);
        float yawpRate = 0.01f;
        int area = (int) (yawpRate * imagePeripheralWidth * imagePeripheralHeight);
        for (int i = 0; i < area; i++) {
            buffImg.setRGB(random.nextInt(imagePeripheralWidth), random.nextInt(imagePeripheralHeight), random.nextInt(255));
        }
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(imagePeripheralWidth);
            int ys = random.nextInt(imagePeripheralHeight);
            int xe = xs + random.nextInt(imagePeripheralWidth);
            int ye = ys + random.nextInt(imagePeripheralHeight);
            g.setColor(getRandColor(2, 254));
            g.drawLine(xs, ys, xe, ye);
        }
        for (int i = 0; i < code.length(); i++) {
            String strRand = code.substring(i, i + 1);
            g.setColor(getRandColor(2, 254));
            g.drawString(strRand, i * (imagePeripheralWidth / charactersNumber) + 3, imagePeripheralHeight - 8);
        }
    }

    @ApiOperation(value = "随机生成验证码")
    public String randomStr(int size) {
        String str1 = "0123456789";
        String str2 = "";
        for (int i = 0; i < size; i++) {
            double randomIndex = Math.random();
            double randomNumber = randomIndex * (str1.length() - 1);
            str2 += str1.charAt((int) randomNumber);
        }
        return str2;
    }

    @ApiOperation(value = "随机生成验证码颜色")
    private Color getRandColor(int color1, int color2) {
        color1 = color1 > 255 ? 255 : color1;
        color2 = color2 > 255 ? 255 : color2;
        return new Color(color1 + random.nextInt(color2 - color1), color1 + random.nextInt(color2 - color1), color1 + random.nextInt(color2 - color1));
    }

    /**
     * 产生随机字体
     */
    private Font getFont(int size) {
        Random random = new Random();
        Font[] font = new Font[5];
        font[0] = new Font("Ravie", Font.PLAIN, size);
        font[1] = new Font("Antique Olive Compact", Font.PLAIN, size);
        font[2] = new Font("Fixedsys", Font.PLAIN, size);
        font[3] = new Font("Wide Latin", Font.PLAIN, size);
        font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, size);
        return font[random.nextInt(5)];
    }

    // 扭曲方法
    private void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private void shearX(Graphics g, int imageWidth, int imageHeight, Color color) {
        int period = random.nextInt(2);
        for (int i = 0; i < imageHeight; i ++) {
            double doubleValue = (double) (period >> 1) * Math.sin((double) i / (double) period + (2.0 * Math.PI * (double) random.nextInt(2)));
            g.copyArea(0, i, imageWidth, 1, doubleToInteger(doubleValue), 0);
            // 加边框
            g.setColor(color);
            g.drawLine(doubleToInteger(doubleValue), i, 0, i);
            g.drawLine(doubleToInteger(doubleValue) + imageWidth, i, imageWidth, i);
        }
    }

    private Integer doubleToInteger(double number) {
        return (int) number;
    }

    private void shearY(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(40) + 10;
        for (int i = 0; i < w1; i++) {
            double doubleValue = (double) (period >> 1) * Math.sin((double) i / (double) period + (2.0 * Math.PI * 7.0) / 20.0);
            g.copyArea(i, 0, 1, h1, 0, doubleToInteger(doubleValue));
            // 加边框
            g.setColor(color);
            g.drawLine(i, doubleToInteger(doubleValue), i, 0);
            g.drawLine(i, doubleToInteger(doubleValue) + h1, i, h1);
        }
    }

    public void write(OutputStream sos) throws IOException {
        ImageIO.write(buffImg, "png", sos);
        sos.close();
    }

    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public String getCode() {
        return code.toLowerCase();
    }
}
