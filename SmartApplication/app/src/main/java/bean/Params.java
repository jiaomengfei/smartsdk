package bean;

public class Params {

    private int code;
    private String msg;
    //参数编码
    private String parm_KEY;
    //    参数值
    private String parm_VALUE;


    public Params(int code, String msg, String parm_KEY, String parm_VALUE) {
        this.code = code;
        this.msg = msg;
        this.parm_KEY = parm_KEY;
        this.parm_VALUE = parm_VALUE;
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

    public String getParm_KEY() {
        return parm_KEY;
    }

    public void setParm_KEY(String parm_KEY) {
        this.parm_KEY = parm_KEY;
    }

    public String getParm_VALUE() {
        return parm_VALUE;
    }

    public void setParm_VALUE(String parm_VALUE) {
        this.parm_VALUE = parm_VALUE;
    }

    @Override
    public String toString() {
        return "Params{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", parm_KEY='" + parm_KEY + '\'' +
                ", parm_VALUE='" + parm_VALUE + '\'' +
                '}';
    }
}
