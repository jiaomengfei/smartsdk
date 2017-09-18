package utils;


public class Cans {
    //保存home数据
    public static final String MENU_LIST_HOME = "menu_list_home";
    //保存全部数据
    public static final String MENU_LIST_MORE = "menu_list_all";

    //系统参数

    /*内网测试环境*/
    public static String GET_PARAMS = Constant.CONFIG.getBaseUrl() + "config/getParams";

    /*生产测试环境*/
//    public static String GET_PARAMS = "https://newbjyd.ebjyd.com/config/getParams";

    //正式环境
//    public static String GET_PARAMS = "https://ebjyd.poolfee.com/config/getParams";


    /**
     * 首页图标
     */
    public static String GET_HOME = "getHome";
    /**
     * 轮播图
     */
    public static String GET_ADS = "getAds";

    /**
     * 登录
     */
    public static String MEMBER_LOGIN = "memberLogin";
    /**
     * 个人注册
     */
    public static String MEMBER_REGISTER = "memberRegister";
    /**
     * 企业注册
     */
    public static String COMP_REGISTER = "companyRegister";
    /**
     * 验证手机号存在
     */
    public static String CHECK_REGISTER = "vaildatePhone";

    /**
     * 请求验证码
     */
    public static String GET_CODE = "getValidateCode";
    /**
     * 验证码校验
     */
    public static String CHECK_CODE = "checkCode";
    /**
     * 忘记密码
     */
    public static String FORGET_PSWD = "retrievePassword";
    /**
     * 号码归属地
     */
    public static String PHONE_CONTACTS = "http://apis.juhe.cn/mobile/get?phone=";
    /**
     * 号码归属地Key
     */
    public static String API_KEY = "&key=54156e4d835736c4dcef49922c29aad0";
    /**
     * 检查更新
     */
    public static String CHECK_VERSION = Constant.CONFIG.getBaseUrl() + "/config/checkVersionNew/android";
    /**
     * 进入程序检查更新
     */
    public static String VERSIONUPDATE = Constant.CONFIG.getBaseUrl() + "/config/VersionUpdate/android";

    /**
     * 话费充值
     */
    public static String PHONE = "getCellularPhoneReplenishingList/";
    /**
     * 油卡充值
     */
    public static String OIL_CARD = "getOilCardRechargeList/";
    /**
     * 普惠专线
     */
    public static String PHZX = "puhuiSpecialRailwayLine/";

    /**
     * 首页信息
     */
    public static String MEMBER_PERSONAL = "member/personal";
    /**
     * 实名认证中获得实名认证次数的信息等
     */
    public static String MEMBER_REALINFO = "member/realInfo";
    /**
     * 银行卡列表展示
     */
    public static String MEMBER_CARD = "member/memberCard";
    /**
     * 银行卡删除
     */
    public static String MEMBER_CARD_REMOVE = "member/memberCardRemove";
    /**
     * 银行卡设置默认
     */
    public static String MEMBER_CARD_DEFAULT = "member/memberCarddefault";
    /**
     * 退出登录
     */
    public static String MEMBER_LAYOUT = "layout";

    /**
     * 修改登录密码
     */
    public static String UPDATE_PSWD = "member/updateUserPwd";
    /**
     * 修改交易密码
     */
    public static String UPDATE_PAYPSWD = "member/updatePayPwd";
    /**
     * 忘记交易密码
     */
    public static String FORGET_PAYPSWD = "member/findPayPwd";
    /**
     * 更改手机号
     */
    public static String CHNAGE_PHONE = "member/changePhone";

    /**
     * 费率信息
     */
    public static String RATEINFO = "rateInfo";
    /**
     * 提现信息
     */
    public static String TIXIAN = "presentInformation";
    /**
     * 获得提现最大值最小值和手续费
     */
    public static String WITHTRAWLRULE = "withtrawlRule";


    /**
     * 申请代理
     */
    public static String APPLYAGENT = "member/applyAgent";

    /**
     * 分润明细
     */
    public static String SHARE_PROFIT = "member/memberShareProfit";
    /**
     * 返佣明细
     */
    public static String COMM_DETAIL = "member/memberCommissionDetail";
    /**
     * 冻结金额
     */
    public static String FROZEN = "member/frozen";

    /**
     * 分享记录
     */
    public static String SHARE_DETAIL = "member/details";
    /**
     * 分享人数
     */
    public static String SHARE_COUNT = "member/count";

    /**
     * 是否有消息
     */
    public static String MESSAGE_EXIT = "member/findMessageIsExit";
    /**
     * 个人
     */
    public static String MESSAGE = "member/memMessage";
    /**
     * 系统
     */
    public static String MESSAGE_SYS = "member/sysMessage";
    /**
     * 添加银行卡
     */
    public static String ADD_CARD = "member/memberCardAdd";

    /**
     * 易道博识
     */
    public static String EXORC_FACE = "http://cloud.exocr.com/face";
    public static String POLICE_FACE = "http://id.exocr.com/verify";

    /**
     * 订单信息
     */
    public static String ORDERINFO = "order/memberOrderCreate";
    /**
     * 实名状态
     */
    public static String REALNAME_SUMBIT = "member/realNameSumbit";
    /**
     * 图片上传
     */
    public static String UPLOAD_IMG = "member/realNameUploadImg";

    /**
     * 校验支付密码
     */
    public static String CHECK_PAY = "member/valiatePayPwd";
    /**
     * 预支付
     */
    public static String PRE_PAY = "pay/prePay";
    /**
     * 确认支付
     */
    public static String SUBMIT_PAY = "pay/submitPay";

    /**
     * 提现
     */
    public static String TIXIAN_PAY = "/withraw/submit";

    /**
     * 风控
     */
    public static String RISK = "member/checkRisk";
    /**
     * 帐单
     */
    public static String ORDER_LIST = "order/memberOrderList";
    /**
     * 帐单类型
     */
    public static String ORDER_TYPE = "orderType";

    /**
     * 支付链接
     */
    public static String HTML_PAY = "htmlPay";

    /**
     * 短连接
     */
    public static String SUO_IM = "http://suo.im/api.php?format=json&url=";

    /**
     * 分享链接
     */
    public static String SHARE_URL = "registrationGuide/";

    /**
     * 余额支付
     */
    public static String PAY_AMOUNT = "pay/payAmount";

    /**
     * 校验账户
     */
    public static String CHECK_MEMINFO = "member/checkMemInfo";

    /**
     * 水电煤
     */
    public static String LIFE_SERVICES = "member/lifeServices";

    /**
     * 手机商城
     */
    public static String MOBILE_MALL = "member/mobileMall";


}
