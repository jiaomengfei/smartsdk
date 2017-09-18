package bean;

import java.io.Serializable;

/**
 * Home主页
 */

public class Home implements Serializable {
    private int code;
    private String msg;
    //功能代码
    private String function_CODE;
    //功能名称
    private String function_NAME;
    //功能链接
    private String function_URL;
    //图片路径
    private String img_PATH;

    public Home(int code, String msg, String function_CODE, String function_NAME, String function_URL, String img_PATH) {
        this.code = code;
        this.msg = msg;
        this.function_CODE = function_CODE;
        this.function_NAME = function_NAME;
        this.function_URL = function_URL;
        this.img_PATH = img_PATH;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFunction_CODE() {
        return function_CODE;
    }

    public void setFunction_CODE(String function_CODE) {
        this.function_CODE = function_CODE;
    }

    public String getFunction_NAME() {
        return function_NAME;
    }

    public void setFunction_NAME(String function_NAME) {
        this.function_NAME = function_NAME;
    }

    public String getFunction_URL() {
        return function_URL;
    }

    public void setFunction_URL(String function_URL) {
        this.function_URL = function_URL;
    }

    public String getImg_PATH() {
        return img_PATH;
    }

    public void setImg_PATH(String img_PATH) {
        this.img_PATH = img_PATH;
    }

    @Override
    public String toString() {
        return "Home{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", function_CODE='" + function_CODE + '\'' +
                ", function_NAME='" + function_NAME + '\'' +
                ", function_URL='" + function_URL + '\'' +
                ", img_PATH='" + img_PATH + '\'' +
                '}';
    }
}
