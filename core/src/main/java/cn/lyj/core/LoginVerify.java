package cn.lyj.core;

import java.util.List;

import allen.frame.entry.Role;

public class LoginVerify {
    private List<Role> roles;
    public LoginVerify(List<Role> roles){
        this.roles = roles;
    }

    /**
     * 判断是否领导
     * @return
     */
    public boolean isLeader(){
        boolean isleader = false;
        for (Role role:roles){
            isleader = isleader||role.getLevel()==5;
        }
        return isleader;
    }

    /**
     * 是否具有网格员职能
     * @return
     */
    public boolean isWgUser(){
        boolean isWgUser = false;
        for (Role role:roles){
            isWgUser = isWgUser||role.getLevel()==4;
        }
        return isWgUser;
    }

    /**
     * 角色名称
     * @return
     */
    public String getRoleNames(){
        StringBuffer rolssb = new StringBuffer();
        if(roles==null||roles.size()==0){
            return "";
        }
        for (Role role:roles){
            rolssb.append("、"+role.getName());
        }
        return rolssb.delete(0,1).toString();
    }
}
