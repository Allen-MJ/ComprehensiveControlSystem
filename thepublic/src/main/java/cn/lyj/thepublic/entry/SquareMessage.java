package cn.lyj.thepublic.entry;

import java.io.Serializable;
import java.util.List;

public class SquareMessage implements Serializable {

    /**
     * content : [{"createTime":"2021-10-11 17:31:43","serviceContent":"<p>将于2021-10-11在冉家坝地铁口举行<\/p><p><br><\/p><p><img src=\"http://localhost:30050\\file\\图片\\81bceb8b9a43743bb1873936bb49f3d4-20211011053113841.jpeg\" style=\"max-width:100%;\"><br><\/p><p><br><\/p><p>期待期待期待。。。<\/p>","serviceId":"53aa71f33e7148e18d2a585dae4fc871","serviceTitle":"第一届社会招聘会来了！","serviceType":"2","serviceUp":true},{"createTime":"2021-10-12 09:26:02","serviceContent":"<p>第一届社会招聘会来了！<br><\/p>","serviceId":"54599e7f235a4ba291fb6d46466ff3d9","serviceTitle":"第二届社会招聘会来了！","serviceType":"2","serviceUp":false}]
     * totalElements : 2
     */

    private int totalElements;
    private List<ContentBean> content;

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * createTime : 2021-10-11 17:31:43
         * serviceContent : <p>将于2021-10-11在冉家坝地铁口举行</p><p><br></p><p><img src="http://localhost:30050\file\图片\81bceb8b9a43743bb1873936bb49f3d4-20211011053113841.jpeg" style="max-width:100%;"><br></p><p><br></p><p>期待期待期待。。。</p>
         * serviceId : 53aa71f33e7148e18d2a585dae4fc871
         * serviceTitle : 第一届社会招聘会来了！
         * serviceType : 2
         * serviceUp : true
         */

        private String createTime;
        private String serviceContent;
        private String serviceId;
        private String serviceTitle;
        private String serviceType;
        private boolean serviceUp;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getServiceContent() {
            return serviceContent;
        }

        public void setServiceContent(String serviceContent) {
            this.serviceContent = serviceContent;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceTitle() {
            return serviceTitle;
        }

        public void setServiceTitle(String serviceTitle) {
            this.serviceTitle = serviceTitle;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public boolean isServiceUp() {
            return serviceUp;
        }

        public void setServiceUp(boolean serviceUp) {
            this.serviceUp = serviceUp;
        }
    }
}
