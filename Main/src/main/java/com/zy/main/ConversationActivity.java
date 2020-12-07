package com.zy.main;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.zy.main.presenter.MessagePresenter;
import com.zy.main.utils.InputMethodUtils;
import com.ui.core.base.BaseActivity;
import androidx.annotation.Nullable;


public class ConversationActivity extends BaseActivity<MessagePresenter> implements View.OnClickListener {

    private EditText mEditText;
    private Button stickerButton;
    private View stickerPanel;
    private View conversationView;

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {
        initViews();
        initCallbacks();
        initOnClickListeners();
    }


    private void initViews() {
        //input text
        mEditText = findViewById(R.id.edit_text_conversation);
        //Sticker button.
        stickerButton = findViewById(R.id.button_sticker_conversation);
        //sticker panel
        stickerPanel = findViewById(R.id.input_sticker_conversation);
        //sticker grid view
        conversationView = findViewById(R.id.conversationView);
    }

    private void initOnClickListeners() {
        stickerButton.setOnClickListener(this);
    }

    private void initCallbacks() {
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    onEditClick();
                }
                return false;
            }
        });

        conversationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeStickerPanel();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                mEditText.clearFocus();
                InputMethodUtils.closeInputMethod(mEditText);
            }
        });

        getPresenter().registerKeyboardListener(conversationView);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_conversation;
    }

    @Override
    protected MessagePresenter createPresenter() {
        return new MessagePresenter();
    }

    @Override
    protected void initData() {
        initInputMethodKeyboard();
    }

      /**
     * Calculate the height of keyboard and save it,then we can use it immediately next time.
     */
    private void initInputMethodKeyboard() {
        //keyboard height
        int height = getPresenter().getKeyboardHeight();
        if (height > 0) {
            ViewGroup.LayoutParams layoutParamsSticker = stickerPanel.getLayoutParams();
            layoutParamsSticker.height = height;
        }
    }

    /**
     * Please don't change the event order.
     * Some click event must be executed only if user is not deleted by other side.
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_sticker_conversation) {
            onStickerButtonClick();
        }
    }

    /**
     * close sticker panel layout
     */
    private void closeStickerPanel() {
        if (stickerPanel.getVisibility() == View.VISIBLE) {
//            conversationLinearLayout.requestLayoutDelay();
            stickerPanel.setVisibility(View.GONE);
        }
    }
    /**
     * Open or close sticker panel.
     */
    private void onStickerButtonClick() {
        if (isStickerPanelShowing()) {//sticky to keyboard
            mEditText.requestFocus();
            InputMethodUtils.openInputMethod(mEditText);
            stickerPanel.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stickerPanel.setVisibility(View.GONE);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                }
            }, 300);
        } else {
            openStickerPanel();
        }
    }

    private void onEditClick(){
        if (isStickerPanelShowing()){
            onStickerButtonClick();
        }else{

        }
    }


    /**
     * Open sticker panel layout.
     */
    private void openStickerPanel() {
        if (stickerPanel.getVisibility() != View.VISIBLE) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
            stickerPanel.setVisibility(View.VISIBLE);
            InputMethodUtils.closeInputMethod(mEditText);
        }
    }


    /**
     * If the sticker panel is open now.
     *
     * @return
     */
    private boolean isStickerPanelShowing() {
        return stickerPanel.getVisibility() == View.VISIBLE;
    }

    @Override
    protected void onPause() {
        mEditText.clearFocus();
        InputMethodUtils.closeInputMethod(mEditText);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        closeStickerPanel();
        super.onPause();
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }
}
