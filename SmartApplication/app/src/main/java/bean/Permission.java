package bean;

/**
 * Created by jmf on 2017/7/25 0025.
 */

public class Permission {
    public String name;
    public int protectionLevel;

    public Permission(String name, int protectionLevel) {
        this.name = name;
        this.protectionLevel = protectionLevel;
    }

    public String name() {
        return this.name.substring(this.name.lastIndexOf(".") + 1);
    }

    public String protectionLevel() {
        String strLevel = null;
        switch(this.protectionLevel) {
            case 0:
                strLevel = "PROTECTION_NORMAL";
            case 1:
                strLevel = "PROTECTION_DANGEROUS";
            case 2:
                strLevel = "PROTECTION_SIGNATURE";
            case 18:
                strLevel = "PROTECTION_PRIVILEGED";
            case 3:
                strLevel = "PROTECTION_SYSTEM";
            default:
                strLevel = "<UNKNOWN>";
                return strLevel;
        }
    }
}
