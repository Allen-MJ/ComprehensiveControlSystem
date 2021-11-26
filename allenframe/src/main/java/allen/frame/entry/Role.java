package allen.frame.entry;

import java.io.Serializable;

public class Role implements Serializable {
    private String dataScope;
    private int id;
    private int level;
    private String name;
    private String username;
    private String updateTime;
    private Units orgs;

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Units getOrgs() {
        return orgs;
    }

    public void setOrgs(Units orgs) {
        this.orgs = orgs;
    }
}
