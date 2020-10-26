package com.utils.simple.ui.home;

import android.app.Application;

import com.utils.simple.R;
import com.utils.simple.bean.CompatBean;
import com.utils.simple.ui.compat.CompatActivityUtilsActivity;
import com.utils.simple.ui.compat.CompatAppUtilsActivity;
import com.utils.simple.ui.compat.CompatArrayUtilsActivity;
import com.utils.simple.ui.compat.CompatBrightnessUtilsActivity;
import com.utils.simple.ui.compat.CompatCacheUtilsActivity;
import com.utils.simple.ui.compat.CompatDateUtilsActivity;
import com.utils.simple.ui.compat.CompatDeviceUtilsActivity;
import com.utils.simple.ui.compat.CompatEncodeUtilsActivity;
import com.utils.simple.ui.compat.CompatFileUtilsActivity;
import com.utils.simple.ui.compat.CompatImageUtilsActivity;
import com.utils.simple.ui.compat.CompatIntentUtilsActivity;
import com.utils.simple.ui.compat.CompatKeyBoardUtilsActivity;
import com.utils.simple.ui.compat.CompatMapUtilsActivity;
import com.utils.simple.ui.compat.CompatNetWorkUtilsActivity;
import com.utils.simple.ui.compat.CompatRegexUtilsActivity;
import com.utils.simple.ui.compat.CompatRomUtilsActivity;
import com.utils.simple.ui.compat.CompatSPUtilsActivity;
import com.utils.simple.ui.compat.CompatScreenUtilsActivity;
import com.utils.simple.ui.compat.CompatShellUtilsActivity;
import com.utils.simple.ui.compat.CompatStringUtilsActivity;
import com.utils.simple.ui.compat.CompatVibrateUtilsActivity;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<CompatBean>> mCompatList;


    public HomeViewModel(@NonNull Application application) {
        super(application);

        mCompatList = new MutableLiveData<>();

        initCompatList();
    }

    private void initCompatList() {

        List<CompatBean> compatBeanList = Arrays.asList(
                new CompatBean(getApplication().getString(R.string.compat_app_utils), CompatAppUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_activity_utils), CompatActivityUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_array_utils), CompatArrayUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_brightness_utils), CompatBrightnessUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_cache_utils), CompatCacheUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_date_utils), CompatDateUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_device_utils), CompatDeviceUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_encode_utils), CompatEncodeUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_file_utils), CompatFileUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_image_utils), CompatImageUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_intent_utils), CompatIntentUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_key_board_utils), CompatKeyBoardUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_map_utils), CompatMapUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_net_work_utils), CompatNetWorkUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_regex_utils), CompatRegexUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_rom_utils), CompatRomUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_screen_utils), CompatScreenUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_shell_utils), CompatShellUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_sp_utils), CompatSPUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_string_utils), CompatStringUtilsActivity.class),
                new CompatBean(getApplication().getString(R.string.compat_vibrate_utils), CompatVibrateUtilsActivity.class)

                );


        this.mCompatList.setValue(compatBeanList);
    }


    public LiveData<List<CompatBean>> getCompatList() {
        return mCompatList;
    }

}