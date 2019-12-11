package com.guyj.picturetags;

import java.util.List;

/**
 * 根据自己的业务定义实体类
 */
public class PictureTagBean {

    public String nickname;
    public String headUrl;
    public String title;
    public String detail;
    public int attentionStatus;
    public int type;
    public int praiseCount;
    public int commentCount;
    public int productCount;
    public String createTime;
    public List<DynamicViewList> dynamicViewList;

    /**
     * 图文信息类 ，根据需要自己修改
     * width/height 是发布方的图文显示长宽，不一定是图片的原始长宽
     */
    public static class DynamicViewList{
        public String imgUrl;
        public int id;
        public String jointPictrue;
        public String width;
        public String height;
        public String videoAddress;
        public List<DynamicViewProduct> dynamicViewProduct;

        /**
         * 针对width/height的xy坐标位置，根据需要自己修改
         */
        public static class DynamicViewProduct{
            public String title;
            public String xAxis;
            public String yAxis;
            public String productId;
            public String style;
            public String type;
            public int dynamicId;
            public int dynamicViewId;
        }
    }
}
