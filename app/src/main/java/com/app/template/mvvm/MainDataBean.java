package com.app.template.mvvm;

import android.util.Log;
import android.widget.ImageView;

import com.app.template.BR;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

public class MainDataBean extends BaseObservable {
    private String title;
    private String desc;
    private String image;
    private boolean rememberMe;

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(BR.title);
    }

    @Bindable
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
        notifyPropertyChanged(BR.image);
    }

    @BindingAdapter("bind:img")
    public static void setImage(ImageView view, String url){
          //显示图片
        Log.d("MainDataBean", "setImage() url="+url);
    }


    @Bindable
    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        Log.d("MainDataBean", "setRememberMe() rememberMe="+rememberMe);
        if(rememberMe == this.rememberMe){
            return;
        }
        this.rememberMe = rememberMe;
        notifyPropertyChanged(BR.rememberMe);
    }


}
