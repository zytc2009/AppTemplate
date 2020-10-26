package com.http.test;

import java.io.Serializable;

public class NewsBean implements Serializable {

   private String path;
   private String image;
   private String passtime;
   private String title;

   public void setPath(String path) {
      this.path = path;
   }

   public void setImage(String image) {
      this.image = image;
   }

   public void setPasstime(String passtime) {
      this.passtime = passtime;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getPath() {
      return path;
   }

   public String getImage() {
      return image;
   }

   public String getPasstime() {
      return passtime;
   }

   public String getTitle() {
      return title;
   }
}
