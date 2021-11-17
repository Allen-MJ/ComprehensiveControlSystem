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
     * 退出登录
     */
    public static final String _logout = "/auth/logoutOfApp";
    /**
     * 分页查询通知公告
     */
    public static final String _getNotice = "/api/Notice/queryOfApp";
    /**
     *  数据类型字典
     */
    public static final String _getType = "/api/dictDetail/queryOfApp";
    /**
     * 问卷调查列表
     */
    public static final String _getWjdcList = "/api/Poll/queryAllOfPhone";
    /**
     * 问卷调查详情
     */
    public static final String _getWjdcInfo = "/api/Poll/findById";
    /**
     * 提交问卷
     */
    public static final String _submitAnswer = "/api/Poll/saveFeedBack";

    /**
     * 个人信息更改
     */
    public static final String _updateUserInfo = "/api/users/updateOfapp";
    /**
     * 23.  根据公告id查询公告详情
     */
    public static final String _getNoticeInfo = "/api/Notice/findById";
    /**
     * 15. 分页查询当前登录人点赞的文章
     */
    public static final String _getZanList= "/api/HandyService/queryUserLikeByLoginUser";
    /**
     * 16. 分页查询当前登录人关注的文章
     */
    public static final String _getGuanzhuList= "/api/HandyService/queryUserConcernByLoginUser";
    /**
     *17. 分页查询当前登录人评论过的文章
     */
    public static final String _getPinglunList= "/api/HandyService/queryUserCommentByLoginUser";
    /**
     * 19. 根据文章id分页查询文章评论详情
     */
    public static final String _getDiscussList= "/api/UserComment";
    /**
     *11. 文章关注添加
     */
    public static final String _addGuanZhu= "/api/HandyService/addFocused";
    /**
     * 12. 取消文章关注
     */
    public static final String _cancelGuanZhu= "/api/HandyService/delFocused";
    /**
     * 13. 文章评论添加
     */
    public static final String _addDiscuss= "/api/HandyService/addServiceComment";
    /**
     * 14. 文章评论删除
     */
    public static final String _delDiscuss= "/api/HandyService/delServiceComment";
    /**
     * 文章点赞记录添加
     */
    public static final String _addZan= "/api/HandyService/addUserLike";
    /**
     * 删除公众app文章点赞记录
     */
    public static final String _delZan= "/api/HandyService/delUserLike";

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
