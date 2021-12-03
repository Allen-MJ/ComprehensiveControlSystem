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
    public static final String HousePersonAdd = "/api/tb12/appAdd";
    /**
     * 户籍人口修改
     */
    public static final String HousePersonUpdate = "/api/tb12/appUpdate";
    /**
     * 户籍人口删除
     */
    public static final String HousePersonDelete = "/api/tb12/appDel";
    /**
     * 流动人口添加
     */
    public static final String TranPersonAdd = "/api/tb13/appAdd";
    /**
     * 流动人口删除
     */
    public static final String TranPersonDelete = "/api/tb13/appDel";
    /**
     * 流动人口修改
     */
    public static final String TranPersonUpdate = "/api/tb13/appUpdate";
    /**
     * 流动人口查询
     */
    public static final String TranPersonQuery = "/api/tb13/appQuery";
    /**
     * 实有房屋查询
     */
    public static final String House = "/api/houseinfo/appQuery";
    public static final String HouseAdd = "/api/houseinfo/appAdd";
    public static final String HouseDelete = "/api/houseinfo/appDel";
    public static final String HouseUpdate = "/api/houseinfo/appUpdate";
    /**
     * 出租房查询
     */
    public static final String RentHouse = "/api/tb16/appQuery";
    public static final String RentHouseAdd = "/api/tb16/appAdd";
    public static final String RentHouseUpdate = "/api/tb16";
    public static final String RentHouseDelete = "/api/tb16/appDel";
    /**
     *
     *
     */
    public static final String _core_12 = "/api/tb24/gridmember";
    /**
     * 非公有制经济组织
     */
    public static final String _core_13 = "/api/tb23/gridmember";
    /*工作日志start*/
    /**
     * 获取工作日志列表
     */
    public static final String _core_11 = "/api/gridWorkLog/gridmember";
    /**
     * 新增(post),修改(put),删除(delete)工作日志
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
    /**
     * 按时间区间统计治安重点排查整治数
     */
    public static final String CoreEventsAreal = "/api/app/stat/events/areal_dist";
    /**
     * 按时间区间统计和组织统计不同治安区域所占比例
     */
    public static final String CoreStatAreal = "/api/app/stat/area_type";
    /**
     * 按时间区间统计矛盾纠纷排查调处以事件规模
     */
    public static final String CoreEventsScale = "/api/app/stat/events/scale";
    /**
     * 按时间区间和网格ID统计矛盾纠纷排查调处以事件处理状态
     */
    public static final String CoreEventsState = "/api/app/stat/events/state";
    /**
     * 按月和网格ID统计矛盾纠纷排查调处月事件量
     */
    public static final String CoreEventsMonth = "/api/app/stat/events/month";
    /**
     * 按时间区间和网格ID统计矛盾纠纷排查调处以调解方式
     */
    public static final String CoreEventsMode = "/api/app/stat/events/mode";

    /**
     * 网格员巡逻定位
     */
    public static final String GridMap = "/api/gridMemberTrack/add";

    /**
     * 查询(get),新增（post）,修改（put）,删除（delete）公文
     */
    public static final String Missive = "/api/missive/leader";
    /**
     * 收文签收
     */
    public static final String MissiveSign = "/api/missive/leader/sign";
    /**
     * 查询接收人
     */
    public static final String QueReciviPerson = "/api/gridPower/users";
    /**
     * 查询网格列表
     */
    public static final String Grids = "/api/gridInfo/gridmember";
    /**
     * 查询网格人员列表
     */
    public static final String GridPerson = "/api/gridPower/adminmembers";

    /**
     * 艾滋病人员列表
     */
    public static final String get_HIVPerson = "/api/tb21/appQuery";
    public static final String del_HIVPerson = "/api/tb21/appDel";
    public static final String add_HIVPerson = "/api/tb21/appAdd";
    public static final String update_HIVPerson = "/api/tb21/appUpdate";
    /**
     * 吸毒人员
     */
    public static final String get_DrugPerson = "/api/tb20/appQuery";
    public static final String del_DrugPerson = "/api/tb20/appDel";
    public static final String add_DrugPerson = "/api/tb20/appAdd";
    public static final String update_DrugPerson = "/api/tb21/appUpdate";
    /**
     * 肇事肇祸严重精神障碍患者
     */
    public static final String get_ZszhPerson = "/api/tb19/appQuery";
    public static final String del_ZszhPerson = "/api/tb19/appDel";
    /**
     * 社区矫正人员
     */
    public static final String get_SqjzPerson = "/api/tb18/appQuery";
    public static final String del_SqjzPerson = "/api/tb18/appDel";
    /**
     * 刑满释放人员
     */
    public static final String get_XmsfPerson = "/api/tb17/appQuery";
    public static final String del_XmsfPerson = "/api/tb17/appDel";
    public static final String add_XmsfPerson = "/api/tb17/appAdd";
    public static final String update_XmsfPerson = "/api/tb17/appUpdate";
    /**
     * 言行过激人员
     */
    public static final String get_YxgjPerson = "/api/yxgj/appQuery";
    public static final String del_YxgjPerson = "/api/yxgj/appDel";
    public static final String add_YxgjPerson = "/api/yxgj/appAdd";
    public static final String update_YxgjPerson = "/api/yxgj/appUpdate";

    /**
     * 邪教人员
     */
    public static final String get_FxjPerson = "/api/fxj/appQuery";
    public static final String del_FxjPerson = "/api/fxj/appDel";
    public static final String add_FxjPerson = "/api/fxj/appAdd";
    public static final String update_FxjPerson = "/api/fxj/appUpdate";
    /**
     * 境外人口
     */
    public static final String get_JwPerson = "/api/tb15/appQuery";
    public static final String del_JwjPerson = "/api/tb15/appDel";
    public static final String add_JwjPerson = "/api/tb15/appAdd";
    public static final String update_JwjPerson = "/api/tb15/appUpdate";
    /**
     * 常住人口
     */
    public static final String get_CzPerson = "/api/tb1213/appQuery";
    /**
     * 系统通知
     */
    public static final String systemNotice = "/api/systemNotice/app";
}
