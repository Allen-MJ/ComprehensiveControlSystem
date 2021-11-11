package cn.lyj.core.api;

public class CoreApi {


    public static final String core_Type = "/api/dictDetail/queryOfApp";
    /**
     * 登录验证码
     */
    public static final String authCode = "/auth/app/code";
    /**
     * 登录
     */
    public static final String authLogin = "/auth/app/login";
    /**
     * 户籍人口查询
     */
    public static final String _core_1 = "/api/tb12/appQuery";
    /**
     * 户籍人口添加
     */
    public static final String _core_2 = "/api/tb12/appAdd";
    /**
     * 户籍人口修改
     */
    public static final String _core_3 = "/api/tb12/appUpdate";
    /**
     * 户籍人口删除
     */
    public static final String _core_4 = "/api/tb12/appDel";
    /**
     * 流动人口添加
     */
    public static final String _core_5 = "/api/tb13/appAdd";
    /**
     * 流动人口删除
     */
    public static final String _core_6 = "/api/tb13/appDel";
    /**
     * 流动人口修改
     */
    public static final String _core_7 = "/api/tb13/appUpdate";
    /**
     * 流动人口查询
     */
    public static final String _core_8 = "/api/tb13/appQuery";
    /**
     * 实有房屋查询
     */
    public static final String _core_9 = "/api/houseinfo/appQuery";
    /**
     * 出租房查询
     */
    public static final String _core_10 = "/api/tb16/appQuery";
    /*工作日志start*/
    /**
     * 获取工作日志列表
     */
    public static final String _core_11 = "/api/gridWorkLog/gridmember";
    /**
     * 新增(post),修改(put)工作日志
     */
    public static final String CoreaddLog = "/api/gridWorkLog/gridmember";
    /*工作日志end*/
    /**
     * 我的任务列表
     */
    public static final String _get_task_list = "/api/gridTask/gridmember";
    /**
     * 任务受理
     */
    public static final String _task_accept = "/api/gridTask/gridmember/accepttask";
    /**
     * 任务反馈
     */
    public static final String _task_feed = "/api/gridTask/gridmember/feedback";
    /**
     * 查看任务反馈详情进度
     */
    public static final String _get_feed_list = "/api/gridTask/gridmember/feedbackdetail";
    /**
     * 统计图数据
     * decisionType 必填 查询统计类型 字符串
     * time 选填 查询截止时间 YYYY-MM-DD字符串 （出租房统计不支持此参数）
     * orgid 选填 查询范围，机构id 字符串（地区分布的图表不支持此参数）
     */
    public static final String CoreChart = "/api/decision/appDecision";
}
