package utils;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * @author ：LiuShihao
 * @date ：Created in 2022/4/15 5:33 下午
 * @desc ：响应数据类
 */
@Data
public class ResponseData extends HashMap<String, Object> {
    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /** 时间戳 */
    public static final String TIME_TAG = "datetime";

    public String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    public ResponseData() {
    }
    public ResponseData(String code,String msg) {
        super.put(CODE_TAG,code);
        super.put(MSG_TAG,msg);
        super.put(TIME_TAG,now);
    }

    public ResponseData(String code,String msg,Object data) {
        super.put(CODE_TAG,code);
        super.put(MSG_TAG,msg);
        if(data != null){
            super.put(DATA_TAG,data);
        }
        super.put(TIME_TAG,now);
    }

    public static ResponseData success() {
        return ResponseData.success("操作成功");
    }
    public static ResponseData success(String msg) {
        return ResponseData.success(msg,null);
    }

    public static ResponseData success(Object data) {
        return ResponseData.success("操作成功",data);
    }
    public static ResponseData success(String msg,Object data) {
        return new ResponseData("0000",msg,data);
    }



    public static ResponseData error() {
        return ResponseData.success("操作失败");
    }
    public static ResponseData error(String msg) {
        return ResponseData.success(msg,null);
    }

    public static ResponseData error(String msg,Object data) {
        return new ResponseData("9999",msg,data);
    }







}
