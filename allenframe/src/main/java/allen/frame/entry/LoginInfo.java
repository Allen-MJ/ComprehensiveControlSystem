package allen.frame.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class LoginInfo implements Serializable {

    private String token;
    private User user;
    public class User implements Serializable{

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
