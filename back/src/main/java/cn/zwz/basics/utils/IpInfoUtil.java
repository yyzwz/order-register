package cn.zwz.basics.utils;

import cn.hutool.http.HttpUtil;
import cn.zwz.data.utils.ZwzNullUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

/**
 * IP地址工具类
 * @author 郑为中
 */
@Slf4j
@Component
public class IpInfoUtil {

    @ApiModelProperty(value = "腾讯地图的KEY值")
    private String key = "XXXXX-XXXXX-XXXXX-XXXXX-XXXXX";

    private static final String IP_HEADER_PRE_ONE = "x-forwarded-for";

    private static final String IP_HEADER_PRE_TWO = "Proxy-Client-IP";

    private static final String IP_HEADER_PRE_THREE = "WL-Proxy-Client-IP";

    private static final String IP_HEADER_NONE = "unknown";

    private static final String IP_HEADER_LOCAL_HOST = "127.0.0.1";

    private static final String IP_HEADER_LOCAL_LONG_HOST = "0:0:0:0:0:0:0:1";

    private static final String TXDT_URL_PRE = "https://apis.map.qq.com/ws/location/v1/ip?key=";

    @ApiOperation(value = "查询IP地址的区县")
    public String getIpCity(HttpServletRequest request){
        String url = TXDT_URL_PRE + key + "&ip=" + getRequestIpAddress(request);
        String resultStr = "本地测试";
        try {
            JsonObject objectResult = JsonParser.parseString(HttpUtil.get(url, 3000)).getAsJsonObject();
            if(Objects.equals(objectResult.get("status").getAsString(),"0")) {
                JsonObject adInfo = objectResult.get("result").getAsJsonObject().get("ad_info").getAsJsonObject();
                String nationStr = adInfo.get("nation").getAsString();
                String provinceStr = adInfo.get("province").getAsString();
                String cityStr = adInfo.get("city").getAsString();
                String districtStr = adInfo.get("district").getAsString();
                if(!ZwzNullUtils.isNull(nationStr) && ZwzNullUtils.isNull(provinceStr)){
                    resultStr = nationStr;
                } else {
                    resultStr = provinceStr;
                    if(!ZwzNullUtils.isNull(cityStr)){
                        resultStr += "-" + cityStr;
                    }
                    if(!ZwzNullUtils.isNull(districtStr)){
                        resultStr += "-" + districtStr;
                    }
                }
            }
        } catch (Exception e) {
            log.warn("区县查询失败");
        }
        return resultStr;
    }

    @ApiOperation(value = "查询请求的IP地址")
    public String getRequestIpAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader(IP_HEADER_PRE_ONE);
        if (ipAddress == null || ipAddress.length() < 1 || IP_HEADER_NONE.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(IP_HEADER_PRE_TWO);
        }
        if (ipAddress == null || ipAddress.length() < 1 || IP_HEADER_NONE.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader(IP_HEADER_PRE_THREE);
        }
        if (ipAddress == null || ipAddress.length() < 1 || IP_HEADER_NONE.equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (Objects.equals(IP_HEADER_LOCAL_HOST,ipAddress)) {
                InetAddress inetAddress = null;
                try {
                    inetAddress = InetAddress.getLocalHost();
                } catch (UnknownHostException exception) {
                    exception.printStackTrace();
                }
                ipAddress = inetAddress.getHostAddress();
            }
        }
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        if(Objects.equals(IP_HEADER_LOCAL_LONG_HOST,ipAddress)){
            ipAddress = IP_HEADER_LOCAL_HOST;
        }
        return ipAddress;
    }
}
