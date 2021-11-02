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
    public static final String _3 = "/api/expose";
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
}
