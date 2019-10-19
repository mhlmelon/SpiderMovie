package com.eunke.model;


public class Movie {
   private Integer id;
   private String title; //标题
   private String picUrl; // 图片
   private String introduction;//简介
   private String downloadUrl;//下载链接
   private String targetUrl; // 电影地址

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getPicUrl() {
      return picUrl;
   }

   public void setPicUrl(String picUrl) {
      this.picUrl = picUrl;
   }

   public String getIntroduction() {
      return introduction;
   }

   public void setIntroduction(String introduction) {
      this.introduction = introduction;
   }

   public String getDownloadUrl() {
      return downloadUrl;
   }

   public void setDownloadUrl(String downloadUrl) {
      this.downloadUrl = downloadUrl;
   }

   public String getTargetUrl() {
      return targetUrl;
   }

   public void setTargetUrl(String targetUrl) {
      this.targetUrl = targetUrl;
   }
}
