package cn.lyj.core;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import allen.frame.tools.Constants;
import cn.lyj.core.chart.ChartActivity;
import cn.lyj.core.chart.EvenScaleChartActivity;
import cn.lyj.core.chart.EventModeActivity;
import cn.lyj.core.chart.EventMonthActivity;
import cn.lyj.core.chart.EventStateActivity;
import cn.lyj.core.chart.ZaqyChartActivity;
import cn.lyj.core.chart.ZdpcChartActivity;
import cn.lyj.core.entry.Model;
import cn.lyj.core.grid.GridListActivity;
import cn.lyj.core.grid.GridmanagerListActivity;
import cn.lyj.core.house.HouseListActivity;
import cn.lyj.core.house.RentHouseListActivity;
import cn.lyj.core.log.LogListActivity;
import cn.lyj.core.person.CzPersonListActivity;
import cn.lyj.core.person.DrugPersonListActivity;
import cn.lyj.core.person.HIVPersonListActivity;
import cn.lyj.core.person.HousePersonListActivity;
import cn.lyj.core.person.JwPersonListActivity;
import cn.lyj.core.person.SqjzPersonListActivity;
import cn.lyj.core.person.TransientPersonListActivity;
import cn.lyj.core.person.XjPersonListActivity;
import cn.lyj.core.person.XmsfPersonListActivity;
import cn.lyj.core.person.YxgjPersonListActivity;
import cn.lyj.core.person.ZsZhPersonListActivity;
import cn.lyj.core.place.SocialPlaceListActivity;
import cn.lyj.core.place.UnSocialPlaceListActivity;
import cn.lyj.core.task.MyTaskListActivity;
import cn.lyj.core.word.ReceivListActivity;
import cn.lyj.core.word.SendListActivity;

public class ModelData {
    private ModelData(){
    }
    public static ModelData init(){
        return new ModelData();
    }

    public List<Model> getGrid(){
        List<Model> list = new ArrayList<>();
        Model item4 = new Model();
        item4.setName("基础信息");
        List<Model> items4 = new ArrayList<>();
        Model item41 = new Model();
        item41.setName("网格管理");
        item41.setId("网格管理");
        item41.setResId(getResId("网格管理"));
        items4.add(item41);
        Model item42 = new Model();
        item42.setName("网格人员");
        item42.setId("网格人员");
        item42.setResId(getResId("网格人员"));
        items4.add(item42);
        item4.setList(items4);
        list.add(item4);
        Model item1 = new Model();
        item1.setName("实有人口管理");
        List<Model> items1 = new ArrayList<>();
        Model item11 = new Model();
        item11.setName("户籍人口");
        item11.setId("户籍人口");
        item11.setResId(getResId("户籍人口"));
        items1.add(item11);
        Model item12 = new Model();
        item12.setName("流动人口");
        item12.setId("流动人口");
        item12.setResId(getResId("流动人口"));
        items1.add(item12);
        Model item13 = new Model();
        item13.setName("常住人口");
        item13.setId("常住人口");
        item13.setResId(getResId("常住人口"));
        items1.add(item13);
        Model item14 = new Model();
        item14.setName("境外人口");
        item14.setId("境外人口");
        item14.setResId(getResId("境外人口"));
        items1.add(item14);
        item1.setList(items1);
        list.add(item1);
        Model item5 = new Model();
        item5.setName("特殊人口");
        List<Model> items5 = new ArrayList<>();
        Model item51 = new Model();
        item51.setName("邪教人员");
        item51.setId("邪教人员");
        item51.setResId(getResId("邪教人员"));
        items5.add(item51);
        Model item52 = new Model();
        item52.setName("言行过激人员");
        item52.setId("言行过激人员");
        item52.setResId(getResId("言行过激人员"));
        items5.add(item52);
        Model item53 = new Model();
        item53.setName("刑满释放人员");
        item53.setId("刑满释放人员");
        item53.setResId(getResId("刑满释放人员"));
        items5.add(item53);
        Model item54 = new Model();
        item54.setName("社区矫正人员");
        item54.setId("社区矫正人员");
        item54.setResId(getResId("社区矫正人员"));
        items5.add(item54);
        Model item55 = new Model();
        item55.setName("肇事肇祸严重精神障碍患者");
        item55.setId("肇事肇祸严重精神障碍患者");
        item55.setResId(getResId("肇事肇祸严重精神障碍患者"));
        items5.add(item55);
        Model item56 = new Model();
        item56.setName("吸毒人员");
        item56.setId("吸毒人员");
        item56.setResId(getResId("吸毒人员"));
        items5.add(item56);
        Model item57 = new Model();
        item57.setName("艾滋病人");
        item57.setId("艾滋病人");
        item57.setResId(getResId("艾滋病人"));
        items5.add(item57);
        item5.setList(items5);
        list.add(item5);
        Model item2 = new Model();
        item2.setName("实有房屋管理");
        List<Model> items2 = new ArrayList<>();
        Model item21 = new Model();
        item21.setName("实有房屋");
        item21.setId("实有房屋");
        item21.setResId(getResId("实有房屋"));
        items2.add(item21);
        Model item22 = new Model();
        item22.setName("出租房");
        item22.setId("出租房");
        item22.setResId(getResId("出租房"));
        items2.add(item22);
        item2.setList(items2);
        list.add(item2);

        Model item3 = new Model();
        item3.setName("组织场所管理");
        List<Model> items3 = new ArrayList<>();
        Model item31 = new Model();
        item31.setName("非公有制经济");
        item31.setId("非公有制经济");
        item31.setResId(getResId("非公有制经济"));
        items3.add(item31);
        Model item32 = new Model();
        item32.setName("社会组织");
        item32.setId("社会组织");
        item32.setResId(getResId("社会组织"));
        items3.add(item32);
        item3.setList(items3);
        list.add(item3);
        return list;
    }

    public List<Model> getWork(){
        List<Model> list = new ArrayList<>();
        Model item1 = new Model();
        item1.setName("任务、办公");
        List<Model> items1 = new ArrayList<>();
        Model item11 = new Model();
        item11.setName("我的任务");
        item11.setId("我的任务");
        item11.setResId(getResId("我的任务"));
        items1.add(item11);
        Model item12 = new Model();
        item12.setName("工作日志");
        item12.setId("工作日志");
        item12.setResId(getResId("工作日志"));
        items1.add(item12);
        item1.setList(items1);
        list.add(item1);

        Model item2 = new Model();
        item2.setName("公文收发");
        List<Model> items2 = new ArrayList<>();
        Model item21 = new Model();
        item21.setName("我的收文");
        item21.setId("我的收文");
        item21.setResId(getResId("我的收文"));
        items2.add(item21);
        Model item22 = new Model();
        item22.setName("我的发文");
        item22.setId("我的发文");
        item22.setResId(getResId("我的发文"));
        items2.add(item22);
        item2.setList(items2);
        list.add(item2);

        Model item3 = new Model();
        item3.setName("网格考核");
        List<Model> items3 = new ArrayList<>();
        Model item31 = new Model();
        item31.setName("我的收文");
        item31.setId("我的收文");
        item31.setResId(getResId("我的收文"));
        items3.add(item31);
        Model item32 = new Model();
        item32.setName("我的发文");
        item32.setId("我的发文");
        item32.setResId(getResId("我的发文"));
        items3.add(item32);
        item3.setList(items3);
        list.add(item3);
        return list;
    }

    public List<Model> getWorkHome(){
        List<Model> list = new ArrayList<>();
        Model ks = new Model();
        ks.setId("快速上报");
        ks.setName("快速上报");
        ks.setResId(getResId("快速上报"));
        list.add(ks);
        Model sth = new Model();
        sth.setId("事件上报");
        sth.setName("事件上报");
        sth.setResId(getResId("事件上报"));
        list.add(sth);
        Model cx = new Model();
        cx.setId("事件查询");
        cx.setName("事件查询");
        cx.setResId(getResId("事件查询"));
        list.add(cx);
        return list;
    }

    public List<Model> getLeaderHome(){
        List<Model> menus = new ArrayList<>();
        Model item1 = new Model();
        item1.setName("事项处理");
        List<Model> list = new ArrayList<>();
        Model ks = new Model();
        ks.setId("快速上报");
        ks.setName("快速上报");
        ks.setResId(getResId("快速上报"));
        list.add(ks);
        Model sth = new Model();
        sth.setId("事件上报");
        sth.setName("事件上报");
        sth.setResId(getResId("事件上报"));
        list.add(sth);
        Model cx = new Model();
        cx.setId("事件查询");
        cx.setName("事件查询");
        cx.setResId(getResId("事件查询"));
        list.add(cx);
        item1.setList(list);
        menus.add(item1);
        return menus;
    }
    public List<Model> getTj(){
        List<Model> menus = new ArrayList<>();

        Model zddq = new Model();
        zddq.setName("重点地区（场所）统计");
        List<Model> zddqitems = new ArrayList<>();
        Model zddqitems1 = new Model();
        zddqitems1.setId("重点排查整治数");
        zddqitems1.setName("重点排查整治数");
        zddqitems1.setResId(getResId("重点排查整治数"));
        zddqitems.add(zddqitems1);
        Model zddqitems2 = new Model();
        zddqitems2.setId("不同治安区域所占比例");
        zddqitems2.setName("不同治安区域所占比例");
        zddqitems2.setResId(getResId("不同治安区域所占比例"));
        zddqitems.add(zddqitems2);
        zddq.setList(zddqitems);
        menus.add(zddq);

        Model mdsj = new Model();
        mdsj.setName("矛盾事件统计");
        List<Model> mdsjitems = new ArrayList<>();
        Model mdsjitems2 = new Model();
        mdsjitems2.setId("矛盾纠纷排查调处以事件规模");
        mdsjitems2.setName("矛盾事件以事件规模");
        mdsjitems2.setResId(getResId("矛盾纠纷排查调处以事件规模"));
        mdsjitems.add(mdsjitems2);
        Model mdsjitems3 = new Model();
        mdsjitems3.setId("矛盾纠纷排查调处以事件处理状态");
        mdsjitems3.setName("矛盾事件以事件处理状态");
        mdsjitems3.setResId(getResId("矛盾纠纷排查调处以事件处理状态"));
        mdsjitems.add(mdsjitems3);
        Model mdsjitems4 = new Model();
        mdsjitems4.setId("矛盾纠纷排查调处月事件量");
        mdsjitems4.setName("矛盾事件月事件量");
        mdsjitems4.setResId(getResId("矛盾纠纷排查调处月事件量"));
        mdsjitems.add(mdsjitems4);
        Model mdsjitems5 = new Model();
        mdsjitems5.setId("矛盾纠纷排查调处以调解方式");
        mdsjitems5.setName("矛盾事件以调解方式");
        mdsjitems5.setResId(getResId("矛盾纠纷排查调处以调解方式"));
        mdsjitems.add(mdsjitems5);
        mdsj.setList(mdsjitems);
        menus.add(mdsj);

        Model item1 = new Model();
        item1.setName("人口统计");
        String[] ids = new String[]{"1","2","3","4","5"};
        String[] names = new String[]{"实有人口地区分布","特殊人口地区分布","特殊人口性别比例",
                "特殊人口年龄结构","特殊人口文化程度"};
        int[] resIds = new int[]{R.mipmap.tb_1,R.mipmap.tb_2,R.mipmap.tb_3,R.mipmap.tb_4,R.mipmap.tb_5};
        List<Model> list = new ArrayList<>();
        for(int i=0;i<ids.length;i++){
            Model item = new Model();
            item.setId(ids[i]);
            item.setName(names[i]);
            item.setResId(resIds[i]);
            list.add(item);
        }
        item1.setList(list);
        menus.add(item1);
        Model item2 = new Model();
        item2.setName("重点青少年统计");
        String[] ids2 = new String[]{"7","8","9","10","11"};
        String[] names2 = new String[]{"重点青少年地区分布","重点青少年性别比例",
                "重点青少年年龄结构","重点青少年文化程度",
                "重点青少年类型分布"};
        int[] resIds2 = new int[]{R.mipmap.tb_6,R.mipmap.tb_7,R.mipmap.tb_8,R.mipmap.tb_9,R.mipmap.tb_10};
        List<Model> list2 = new ArrayList<>();
        for(int i=0;i<ids2.length;i++){
            Model item = new Model();
            item.setId(ids2[i]);
            item.setName(names2[i]);
            item.setResId(resIds2[i]);
            list2.add(item);
        }
        item2.setList(list2);
        menus.add(item2);
        Model item3 = new Model();
        item3.setName("出租房统计");
        String[] ids3 = new String[]{"12","13"};
        String[] names3 = new String[]{"出租房地区分布","出租房出租用途比例"};
        int[] resIds3 = new int[]{R.mipmap.tb_11,
                R.mipmap.tb_12};
        List<Model> list3 = new ArrayList<>();
        for(int i=0;i<ids3.length;i++){
            Model item = new Model();
            item.setId(ids3[i]);
            item.setName(names3[i]);
            item.setResId(resIds3[i]);
            list3.add(item);
        }
        item3.setList(list3);
        menus.add(item3);
        return menus;
    }

    private int getResId(String id){
        int resId = 0;
        switch (id){
            case "户籍人口":
                resId = R.mipmap.core_hjrk;
                break;
            case "流动人口":
                resId = R.mipmap.core_ldrk;
                break;
            case "实有房屋":
                resId = R.mipmap.core_syfw;
                break;
            case "出租房":
                resId = R.mipmap.core_rent;
                break;
            case "非公有制经济":
                resId = R.mipmap.core_gyz;
                break;
            case "社会组织":
                resId = R.mipmap.core_shzz;
                break;
            case "我的任务":
                resId = R.mipmap.core_task;
                break;
            case "工作日志":
                resId = R.mipmap.core_log;
                break;
            case "我的收文":
                resId = R.mipmap.core_wdsw;
                break;
            case "我的发文":
                resId = R.mipmap.core_wdfw;
                break;
            case "快速上报":
                resId = R.mipmap.core_kssb;
                break;
            case "事件上报":
                resId = R.mipmap.core_sxsb;
                break;
            case "事件查询":
                resId = R.mipmap.core_sxcx;
                break;
            case "网格管理":
                resId = R.mipmap.core_wggl;
                break;
            case "网格人员":
                resId = R.mipmap.core_wgryl;
                break;
            case "常住人口":
                resId = R.mipmap.core_czrk;
                break;
            case "境外人口":
                resId = R.mipmap.core_jwry;
                break;
            case "邪教人员":
                resId = R.mipmap.core_xjry;
                break;
            case "言行过激人员":
                resId = R.mipmap.core_yxgj;
                break;
            case "刑满释放人员":
                resId = R.mipmap.core_xmsf;
                break;
            case "社区矫正人员":
                resId = R.mipmap.core_sqjz;
                break;
            case "肇事肇祸严重精神障碍患者":
                resId = R.mipmap.core_jsza;
                break;
            case "吸毒人员":
                resId = R.mipmap.core_xdry;
                break;
            case "艾滋病人":
                resId = R.mipmap.core_azbr;
                break;
            case "重点排查整治数":
                resId = R.mipmap.core_zdpc;
                break;
            case "不同治安区域所占比例":
                resId = R.mipmap.core_zaqy;
                break;
            case "矛盾纠纷排查调处以事件规模":
                resId = R.mipmap.core_mdjf_1;
                break;
            case "矛盾纠纷排查调处以事件处理状态":
                resId = R.mipmap.core_mdjf_2;
                break;
            case "矛盾纠纷排查调处月事件量":
                resId = R.mipmap.core_mdjf_3;
                break;
            case "矛盾纠纷排查调处以调解方式":
                resId = R.mipmap.core_mdjf_4;
                break;
        }
        return resId;
    }

    public void onClickListener(Context context, Model model){
        switch (model.getId()){
            case "户籍人口":
                context.startActivity(new Intent(context, HousePersonListActivity.class));
                break;
            case "流动人口":
                context.startActivity(new Intent(context, TransientPersonListActivity.class));
                break;
            case "艾滋病人":
                context.startActivity(new Intent(context, HIVPersonListActivity.class));
                break;
            case "吸毒人员":
                context.startActivity(new Intent(context, DrugPersonListActivity.class));
                break;
            case "肇事肇祸严重精神障碍患者":
                context.startActivity(new Intent(context, ZsZhPersonListActivity.class));
                break;
            case "社区矫正人员":
                context.startActivity(new Intent(context, SqjzPersonListActivity.class));
                break;
            case "刑满释放人员":
                context.startActivity(new Intent(context, XmsfPersonListActivity.class));
                break;
            case "言行过激人员":
                context.startActivity(new Intent(context, YxgjPersonListActivity.class));
                break;
            case "邪教人员":
                context.startActivity(new Intent(context, XjPersonListActivity.class));
                break;
            case "境外人口":
                context.startActivity(new Intent(context, JwPersonListActivity.class));
                break;
            case "常住人口":
                context.startActivity(new Intent(context, CzPersonListActivity.class));
                break;
            case "实有房屋":
                context.startActivity(new Intent(context, HouseListActivity.class));
                break;
            case "出租房":
                context.startActivity(new Intent(context, RentHouseListActivity.class));
                break;
            case "非公有制经济":
                context.startActivity(new Intent(context, UnSocialPlaceListActivity.class));
                break;
            case "社会组织":
                context.startActivity(new Intent(context, SocialPlaceListActivity.class));
                break;
            case "我的任务":
                context.startActivity(new Intent(context, MyTaskListActivity.class));
                break;
            case "工作日志":
                context.startActivity(new Intent(context, LogListActivity.class));
                break;
            case "快速上报":
                context.startActivity(new Intent().setClassName(context,"cn.lyjj.thepublic.SmartTipActivity"));
                break;
            case "事件上报":
                context.startActivity(new Intent().setClassName(context,"cn.lyjj.thepublic.TipoffActivity")
                        .putExtra(Constants.Key_1,1));
                break;
            case "事件查询":
                context.startActivity(new Intent().setClassName(context,"cn.lyjj.thepublic.TipOffListActivity")
                        .putExtra(Constants.Key_1,"事件查询"));
                break;
            case "我的发文":
                context.startActivity(new Intent(context, SendListActivity.class));
                break;
            case "我的收文":
                context.startActivity(new Intent(context, ReceivListActivity.class));
                break;
            case "网格管理":
                context.startActivity(new Intent(context, GridListActivity.class));
                break;
            case "网格人员":
                context.startActivity(new Intent(context, GridmanagerListActivity.class));
                break;
            case "重点排查整治数":
                context.startActivity(new Intent(context, ZdpcChartActivity.class).putExtra(Constants.ObjectFirst,model));
                break;
            case "不同治安区域所占比例":
                context.startActivity(new Intent(context, ZaqyChartActivity.class).putExtra(Constants.ObjectFirst,model));
                break;
            case "矛盾纠纷排查调处以事件规模":
                context.startActivity(new Intent(context, EvenScaleChartActivity.class).putExtra(Constants.ObjectFirst,model));
                break;
            case "矛盾纠纷排查调处以事件处理状态":
                context.startActivity(new Intent(context, EventStateActivity.class).putExtra(Constants.ObjectFirst,model));
                break;
            case "矛盾纠纷排查调处月事件量":
                context.startActivity(new Intent(context, EventMonthActivity.class).putExtra(Constants.ObjectFirst,model));
                break;
            case "矛盾纠纷排查调处以调解方式":
                context.startActivity(new Intent(context, EventModeActivity.class).putExtra(Constants.ObjectFirst,model));
                break;
            default:
                context.startActivity(new Intent(context, ChartActivity.class).putExtra(Constants.ObjectFirst,model));
                break;
        }
    }
}
