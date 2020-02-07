package com.omi.app.models;


    public class MyListData {
        private String description;
        private int imgId;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        private int value;
        public MyListData(String description, int imgId, int value) {
            this.description = description;
            this.imgId = imgId;
            this.value=value;
        }
        public String getDescription() {
            return description;
        }
        public void setDescription(String description) {
            this.description = description;
        }
        public int getImgId() {
            return imgId;
        }
        public void setImgId(int imgId) {
            this.imgId = imgId;
        }
    }

