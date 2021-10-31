package allen.frame.entry;

import java.io.Serializable;

import allen.frame.tools.StringUtils;

public class LoginInfo implements Serializable {

    private String token;
    private Info user;
    public class Info implements Serializable{
        private String[] gids;
        private String[] orgId;
        private String[] roles;
        private User user;

        public String[] getGids() {
            return gids;
        }

        public void setGids(String[] gids) {
            this.gids = gids;
        }

        public String[] getOrgId() {
            return orgId;
        }

        public void setOrgId(String[] orgId) {
            this.orgId = orgId;
        }

        public String[] getRoles() {
            return roles;
        }

        public void setRoles(String[] roles) {
            this.roles = roles;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Info getUser() {
        return user;
    }

    public void setUser(Info user) {
        this.user = user;
    }
}
