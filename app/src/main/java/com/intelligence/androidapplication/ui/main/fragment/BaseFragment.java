package com.intelligence.androidapplication.ui.main.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.intelligence.androidapplication.ui.BaseActivity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import butterknife.ButterKnife;

/**
 * Created by Administrator
 * on 2017/9/18.
 */

public abstract class BaseFragment extends Fragment {
    protected String TAG;
    protected BaseActivity mActivity;
    protected View rootView;
    private ProgressDialog mDialog;
    private Toast toast = null;
    /**
     * rootView是否初始化标志，防止回调函数在rootView为空的时候触发
     */
    private boolean hasCreateView;

    /**
     * 当前Fragment是否处于可见状态标志，防止因ViewPager的缓存机制而导致回调函数的触发
     */
    private boolean isFragmentVisible;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TAG = this.getClass().getSimpleName();
        mActivity = (BaseActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getActivity().getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;        // a|=b的意思就是把a和b按位或然后赋值给a   按位或的意思就是先把a和b都换成2进制，然后用或操作，相当于a=a|b
        } else {
            winParams.flags &= ~bits;        //&是位运算里面，与运算  a&=b相当于 a = a&b  ~非运算符
        }
        win.setAttributes(winParams);
    }

    public void setStatusWhite(boolean status) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {            //系统版本大于19
            setTranslucentStatus(true);
        }

        Class clazz = getActivity().getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            if (status) {
                extraFlagField.invoke(getActivity().getWindow(), darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
            } else {
                extraFlagField.invoke(getActivity().getWindow(), 0, darkModeFlag);//清除黑色字体
            }
        } catch (Exception e) {

        }
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null)
            rootView = inflater.inflate(getLayoutResource(), container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        if (!hasCreateView && getUserVisibleHint()) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
    }

    protected void showDialog(String value) {
        if (mDialog == null)
            mDialog = new ProgressDialog(getContext());
        mDialog.setCancelable(false);
        mDialog.setMessage(value);
        mDialog.show();
    }

    protected void disMiss() {
        if (mDialog != null)
            mDialog.dismiss();
    }


    //获取布局文件
    protected abstract int getLayoutResource();

    //初始化数据
    public abstract void initData();

    //初始化view
    protected abstract void initView();

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    protected void showTextToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(mActivity.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView == null) {
            return;
        }
        hasCreateView = true;
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            onFragmentVisibleChange(false);
            isFragmentVisible = false;
        }
    }

    private void initVariable() {
        hasCreateView = false;
        isFragmentVisible = false;
    }

    /**************************************************************
     *  自定义的回调方法，子类可根据需求重写
     *************************************************************/

    /**
     * 当前fragment可见状态发生变化时会回调该方法
     * 如果当前fragment是第一次加载，等待onCreateView后才会回调该方法，其它情况回调时机跟 {@link #setUserVisibleHint(boolean)}一致
     * 在该回调方法中你可以做一些加载数据操作，甚至是控件的操作，因为配合fragment的view复用机制，你不用担心在对控件操作中会报 null 异常
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {
        Log.w("fragment", "onFragmentVisibleChange -> isVisible: " + isVisible);
    }

}
