package com.cskaoyan.bean;

import java.util.List;

/**
 * @author pqk
 * @since 2022/07/20 14:04
 */

public class related {


    public class DataEntity {
        /**
         * total : 2
         * pages : 1
         * limit : 6
         * page : 1
         * list : [{"brief":"水杯","picUrl":"http://182.92.235.201:8083/wx/storage/fetch/5qxpab7onxv5wyg0d663.jpg","name":"水杯啊啊啊啊","counterPrice":30,"id":1181089,"isNew":true,"retailPrice":20,"isHot":false},{"brief":"运动型水壶","picUrl":"http://182.92.235.201:8083/wx/storage/fetch/v0d989z29ttmdu4onf6r.jpg","name":"水杯","counterPrice":3,"id":1181011,"isNew":true,"retailPrice":100,"isHot":false}]
         */
        private int total;
        private int pages;
        private int limit;
        private int page;
        private List<ListEntity> list;


        public class ListEntity {
            /**
             * brief : 水杯
             * picUrl : http://182.92.235.201:8083/wx/storage/fetch/5qxpab7onxv5wyg0d663.jpg
             * name : 水杯啊啊啊啊
             * counterPrice : 30.0
             * id : 1181089
             * isNew : true
             * retailPrice : 20.0
             * isHot : false
             */
            private String brief;
            private String picUrl;
            private String name;
            private double counterPrice;
            private int id;
            private boolean isNew;
            private double retailPrice;
            private boolean isHot;
        }
    }
}
