package com.intelligence.androidapplication.ui.navigation.fragment;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.intelligence.androidapplication.R;
import com.intelligence.androidapplication.ui.main.model.MainModel;
import com.intelligence.androidlibrary.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragement {
    private TextView tv_wo;
    private List<MainModel> mainModels;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        mainModels = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            MainModel mainModel = new MainModel();
            mainModel.setName("数据链" + i + 1);
            mainModel.setNameNo(i + 1 + "");
            mainModels.add(mainModel);
        }
        tv_wo = (TextView)view.findViewById(R.id.tv_wo);
        tv_wo.setText("测试");
        setDislistPopupMenu(tv_wo);


    }

    @Override
    protected void getDataFromServer() {

        Toast.makeText(mContext, "HomeFragment页面请求数据了", Toast.LENGTH_SHORT).show();

    }

    //
    private void setDislistPopupMenu(View view) {
        Context wrapper = new ContextThemeWrapper(mContext, R.style.PopupMenuStyles);
        final PopupMenu popupMenu = new PopupMenu(wrapper, view);
        for (int i = 0; i < mainModels.size(); i++) {
            popupMenu.getMenu().add(0, i, 0, mainModels.get(i).getName()).setTitleCondensed(mainModels.get(i).getNameNo());
        }
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                LogUtil.d(item.getTitle() + "," + item.getTitleCondensed());
                String productName = item.getTitle().toString();
                String productNo = item.getTitleCondensed().toString();
                tv_wo.setText(productName);
                return true;
            }
        });
        popupMenu.show();
    }
}
