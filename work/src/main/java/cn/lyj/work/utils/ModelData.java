package cn.lyj.work.utils;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import allen.frame.tools.Constants;
import cn.lyj.core.entry.Model;
import cn.lyj.core.house.HouseListActivity;
import cn.lyj.core.house.RentHouseListActivity;
import cn.lyj.core.log.LogListActivity;
import cn.lyj.core.person.HousePersonListActivity;
import cn.lyj.core.person.TransientPersonListActivity;
import cn.lyj.core.place.SocialPlaceListActivity;
import cn.lyj.core.place.UnSocialPlaceListActivity;
import cn.lyj.core.task.MyTaskListActivity;
import cn.lyjj.tipoff.SmartTipActivity;
import cn.lyjj.tipoff.TipOffListActivity;
import cn.lyjj.tipoff.TipoffActivity;

public class ModelData {
    private ModelData(){
    }
    public static ModelData init(){
        return new ModelData();
    }

    public List<Model> getGrid(){
        List<Model> list = new ArrayList<>();
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
        item1.setList(items1);
        list.add(item1);
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
        return list;
    }

    public List<Model> getHome(){
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

    private int getResId(String id){
        switch (id){
            case "户籍人口":
                break;
            case "流动人口":
                break;
            case "实有房屋":
                break;
            case "出租房":
                break;
            case "非公有制经济":
                break;
            case "社会组织":
                break;
            case "我的任务":
                break;
            case "工作日志":
                break;
        }
        return 0;
    }

    public void onClickListener(Context context,Model model){
        switch (model.getId()){
            case "户籍人口":
                context.startActivity(new Intent(context, HousePersonListActivity.class));
                break;
            case "流动人口":
                context.startActivity(new Intent(context, TransientPersonListActivity.class));
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
                context.startActivity(new Intent(context, SmartTipActivity.class));
                break;
            case "事件上报":
                context.startActivity(new Intent(context, TipoffActivity.class).putExtra(Constants.Key_1,1));
                break;
            case "事件查询":
                context.startActivity(new Intent(context, TipOffListActivity.class));
                break;
        }
    }
}