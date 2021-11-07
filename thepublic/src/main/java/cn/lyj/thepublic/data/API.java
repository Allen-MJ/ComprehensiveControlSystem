package cn.lyj.thepublic.data;

public class API {
    /**
     * 用户注册
     */
    public static final String _1 = "/api/users/register";
    /**
     * 用户登录
     */
    public static final String _2 = "/auth/loginOfApp";
    /**
     * 公众爆料添加
     */
    public static final String _3 = "/api/appeal/publicapp";
    /**
     * 5.  获取便民服务类别
     */
//    public static final String _getType = "/api/dictDetail/queryOfApp?dictName=handy_service_type&page=0&size=9999";
    /**
     * 6. 分页查询便民服务信息
     */
    public static final String _getSquare = "/api/HandyService/queryOfApp";
    /**
     * 分页查询通知公告
     */
    public static final String _getNotice = "/api/Notice/queryOfApp";
    public static final String _getType = "/api/dictDetail/queryOfApp";
    /**
     * 附件上传
     */
    public static final String upload = "/api/localStorage/app";
    /**
     * 获取当前人爆料列表
     */
    public static final String _4 = "/api/appeal/my";
    /**
     * 获取爆料详情
     */
    public static final String _5 = "/api/appeal/publicdetail";
    /**
     * 办理记录
     */
    public static final String _6 = "/api/appealProcess/getlist";
    /**
     * 评价详情
     */
    public static final String _7 = "/api/appealEvaluate/get";

}
