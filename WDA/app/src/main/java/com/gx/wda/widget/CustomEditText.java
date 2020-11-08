package com.gx.wda.widget;

import android.app.Activity;
import android.content.Context;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import com.gx.wda.MyApplication;
import com.gx.wda.dialog.KeyBoardDialog;
import com.gx.wda.util.KeyBoardUtil;

import java.lang.reflect.Method;

public class CustomEditText extends AppCompatEditText {
    /**
     * 初始化键盘(禁止系统键盘)
     * @param myActivity
     */
    public void initKeyBoard(Activity myActivity) {
        this.setFocusable(false);
        this.setFocusableInTouchMode(false);
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            this.setInputType(InputType.TYPE_NULL);
        } else {
            myActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setSoftInputShownOnFocus;
                setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setSoftInputShownOnFocus.setAccessible(true);
                setSoftInputShownOnFocus.invoke(this, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 点击时设置键盘
     * @param myActivity
     */
    public void setKeyBoard(Activity myActivity) {
        KeyBoardDialog keyBoardDialog = new KeyBoardDialog(myActivity);
        MyApplication myApplication= (MyApplication) myActivity.getApplication();
        if (myApplication.isCall()){//远程操作
            KeyBoardUtil.hideKeyboard(this);
            keyBoardDialog.show(this);
        }else {//本地操作（显示键盘）
            this.setFocusable(true);
            this.setFocusableInTouchMode(true);
            KeyBoardUtil.showKeyboard(this);
        }
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public OnFocusChangeListener getOnFocusChangeListener() {
        return super.getOnFocusChangeListener();
    }

}
