package cn.lyjj.thepublic.api;

public class TipApi {
    public static final String TipList = "/api/appeal/my";
    public static final String TipPublicAdd = "/api/appeal/publicapp";
    public static final String TipGridAdd = "/api/appeal/gridmember";//网格员上报
    public static final String TipLeaderAdd = "/api/appeal/leader";//领导上报

    /**
     * 获取爆料详情
     */
    public static final String TipDetail = "/api/appeal/publicdetail";
    /**
     * 办理记录
     */
    public static final String TipProgress = "/api/appealProcess/getlist";
    /**
     * 评价详情
     */
    public static final String TipEval = "/api/appealEvaluate/get";

    /**
     * 附件上传
     */
    public static final String upload = "/api/localStorage/app";
    /**
     * 快速上报
     */
    public static final String SmartAdd = "/api/appeal/gridmember";
    /**
     * 快速上报单位选择
     */
    public static final String SmartOrgChoice = "/api/org/app";
}
