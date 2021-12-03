package cn.lyj.core.entry;

import java.io.Serializable;

public class ZsZhPersonEntity implements Serializable {

    /**
     * b1900 : 4d455aa93e52485585bed914bb83e749
     * b1901 : 500225199604051258
     * b1902 : 李伟
     * b1903 : 精神病
     * b1904 : 1
     * b1905 : 1996-04-05
     * b1906 : 01
     * b1907 : 重庆市大足县
     * b1908 : 10
     * b1909 : 13
     * b1909Obj : {"code":"13","codeName":"群众"}
     * b1910 : 2
     * b1910Obj : {"code":"2","codeName":"本科教育"}
     * b1912 : 4
     * b1913 : 啊实打实的
     * b1914 :
     * b1915 : 13685748596
     * b1916 : 500105
     * b1916Obj : {"code":"500105","codeName":"重庆市江北区"}
     * b1917 : 阿斯顿发送到发
     * b1918 : 500111
     * b1918Obj : {"code":"500111","codeName":"重庆市大足区"}
     * b1919 : 啊实打实的
     * b1920 : 01
     * b1921 : 1
     * b1923 : 阿斯顿撒
     * b1924 : 13585857485
     * b1926 : 3
     * b1927 : 0
     * b1930 : 02
     * b1931 : 1
     * b1933 :
     * b1935 : ["01","03"]
     * b1936 :
     * createBy : admin
     * createTime : 2021-11-02 16:24:53
     * deleted : 0
     * gid : 828280c57c64053d017c6408fc5c0000
     * gidObj : {"orgFullName":"一网格","orgId":"828280c57c64053d017c6408fc5c0000","orgName":"一网格","orgNo":"500111103001001"}
     * updateBy : admin
     * updateTime : 2021-11-08 17:02:42
     */

    private String b1900;
    private String b1901;
    private String b1902;
    private String b1903;
    private String b1904;
    private String b1904Name;
    private String b1905;
    private String b1906;
    private String b1907;
    private String b1908;
    private String b1909;
    private B1909ObjBean b1909Obj;
    private String b1910;
    private B1910ObjBean b1910Obj;
    private String b1911;
    private String b1912;
    private String b1913;
    private String b1914;
    private String b1915;
    private String b1916;
    private B1916ObjBean b1916Obj;
    private String b1917;
    private String b1918;
    private B1918ObjBean b1918Obj;
    private String b1919;
    private String b1920;
    private String b1921;
    private String b1923;
    private String b1924;
    private String b1926;
    private String b1927;
    private String b1930;
    private String b1931;
    private String b1933;
    private String b1935;
    private String b1936;
    private String createBy;
    private String createTime;
    private int deleted;
    private String gid;
    private GidObjBean gidObj;
    private String updateBy;
    private String updateTime;

    public String getB1900() {
        return b1900;
    }

    public void setB1900(String b1900) {
        this.b1900 = b1900;
    }

    public String getB1901() {
        return b1901;
    }

    public void setB1901(String b1901) {
        this.b1901 = b1901;
    }

    public String getB1902() {
        return b1902;
    }

    public void setB1902(String b1902) {
        this.b1902 = b1902;
    }

    public String getB1903() {
        return b1903;
    }

    public void setB1903(String b1903) {
        this.b1903 = b1903;
    }

    public String getB1904() {
        return b1904;
    }
    public String getB1904Name() {
        String name = "";
        switch (b1904){
            case "1":
                name = "男";
                break;
            case "2":
                name = "女";
                break;
        }
        return name;
    }

    public void setB1904(String b1904) {
        this.b1904 = b1904;
    }

    public String getB1905() {
        return b1905;
    }

    public void setB1905(String b1905) {
        this.b1905 = b1905;
    }

    public String getB1906() {
        return b1906;
    }

    public void setB1906(String b1906) {
        this.b1906 = b1906;
    }

    public String getB1907() {
        return b1907;
    }

    public void setB1907(String b1907) {
        this.b1907 = b1907;
    }

    public String getB1908() {
        return b1908;
    }

    public void setB1908(String b1908) {
        this.b1908 = b1908;
    }

    public String getB1909() {
        return b1909;
    }

    public void setB1909(String b1909) {
        this.b1909 = b1909;
    }

    public B1909ObjBean getB1909Obj() {
        return b1909Obj;
    }

    public void setB1909Obj(B1909ObjBean b1909Obj) {
        this.b1909Obj = b1909Obj;
    }

    public String getB1910() {
        return b1910;
    }

    public void setB1910(String b1910) {
        this.b1910 = b1910;
    }

    public B1910ObjBean getB1910Obj() {
        return b1910Obj;
    }

    public void setB1910Obj(B1910ObjBean b1910Obj) {
        this.b1910Obj = b1910Obj;
    }

    public String getB1911() {
        return b1911;
    }

    public void setB1911(String b1911) {
        this.b1911 = b1911;
    }

    public String getB1912() {
        return b1912;
    }

    public void setB1912(String b1912) {
        this.b1912 = b1912;
    }

    public String getB1913() {
        return b1913;
    }

    public void setB1913(String b1913) {
        this.b1913 = b1913;
    }

    public String getB1914() {
        return b1914;
    }

    public void setB1914(String b1914) {
        this.b1914 = b1914;
    }

    public String getB1915() {
        return b1915;
    }

    public void setB1915(String b1915) {
        this.b1915 = b1915;
    }

    public String getB1916() {
        return b1916;
    }

    public void setB1916(String b1916) {
        this.b1916 = b1916;
    }

    public B1916ObjBean getB1916Obj() {
        return b1916Obj;
    }

    public void setB1916Obj(B1916ObjBean b1916Obj) {
        this.b1916Obj = b1916Obj;
    }

    public String getB1917() {
        return b1917;
    }

    public void setB1917(String b1917) {
        this.b1917 = b1917;
    }

    public String getB1918() {
        return b1918;
    }

    public void setB1918(String b1918) {
        this.b1918 = b1918;
    }

    public B1918ObjBean getB1918Obj() {
        return b1918Obj;
    }

    public void setB1918Obj(B1918ObjBean b1918Obj) {
        this.b1918Obj = b1918Obj;
    }

    public String getB1919() {
        return b1919;
    }

    public void setB1919(String b1919) {
        this.b1919 = b1919;
    }

    public String getB1920() {
        return b1920;
    }

    public void setB1920(String b1920) {
        this.b1920 = b1920;
    }

    public String getB1921() {
        return b1921;
    }

    public void setB1921(String b1921) {
        this.b1921 = b1921;
    }

    public String getB1923() {
        return b1923;
    }

    public void setB1923(String b1923) {
        this.b1923 = b1923;
    }

    public String getB1924() {
        return b1924;
    }

    public void setB1924(String b1924) {
        this.b1924 = b1924;
    }

    public String getB1926() {
        return b1926;
    }

    public void setB1926(String b1926) {
        this.b1926 = b1926;
    }

    public String getB1927() {
        return b1927;
    }

    public void setB1927(String b1927) {
        this.b1927 = b1927;
    }

    public String getB1930() {
        return b1930;
    }

    public void setB1930(String b1930) {
        this.b1930 = b1930;
    }

    public String getB1931() {
        return b1931;
    }

    public void setB1931(String b1931) {
        this.b1931 = b1931;
    }

    public String getB1933() {
        return b1933;
    }

    public void setB1933(String b1933) {
        this.b1933 = b1933;
    }

    public String getB1935() {
        return b1935;
    }

    public void setB1935(String b1935) {
        this.b1935 = b1935;
    }

    public String getB1936() {
        return b1936;
    }

    public void setB1936(String b1936) {
        this.b1936 = b1936;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public GidObjBean getGidObj() {
        return gidObj;
    }

    public void setGidObj(GidObjBean gidObj) {
        this.gidObj = gidObj;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public static class B1909ObjBean implements Serializable{
        /**
         * code : 13
         * codeName : 群众
         */

        private String code;
        private String codeName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }
    }

    public static class B1910ObjBean implements Serializable{
        /**
         * code : 2
         * codeName : 本科教育
         */

        private String code;
        private String codeName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }
    }

    public static class B1916ObjBean implements Serializable{
        /**
         * code : 500105
         * codeName : 重庆市江北区
         */

        private String code;
        private String codeName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }
    }

    public static class B1918ObjBean implements Serializable{
        /**
         * code : 500111
         * codeName : 重庆市大足区
         */

        private String code;
        private String codeName;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCodeName() {
            return codeName;
        }

        public void setCodeName(String codeName) {
            this.codeName = codeName;
        }
    }

    public static class GidObjBean implements Serializable{
        /**
         * orgFullName : 一网格
         * orgId : 828280c57c64053d017c6408fc5c0000
         * orgName : 一网格
         * orgNo : 500111103001001
         */

        private String orgFullName;
        private String orgId;
        private String orgName;
        private String orgNo;

        public String getOrgFullName() {
            return orgFullName;
        }

        public void setOrgFullName(String orgFullName) {
            this.orgFullName = orgFullName;
        }

        public String getOrgId() {
            return orgId;
        }

        public void setOrgId(String orgId) {
            this.orgId = orgId;
        }

        public String getOrgName() {
            return orgName;
        }

        public void setOrgName(String orgName) {
            this.orgName = orgName;
        }

        public String getOrgNo() {
            return orgNo;
        }

        public void setOrgNo(String orgNo) {
            this.orgNo = orgNo;
        }
    }
}
