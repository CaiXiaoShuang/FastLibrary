package com.intelligence.androidapplication.ui.main.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.intelligence.androidapplication.R;
import com.intelligence.androidlibrary.zxing.android.CaptureActivity;
import com.intelligence.androidlibrary.zxing.encode.CodeCreator;

import butterknife.Bind;

import static android.app.Activity.RESULT_OK;

public class HomeFragment extends BaseFragment {
    @Bind(R.id.tv_wo)
    TextView tv_wo;
    @Bind(R.id.bt_scan)
    Button bt_scan;
    @Bind(R.id.iv_qr_code)
    ImageView iv_qr_code;
    @Bind(R.id.iv_bar_code)
    ImageView iv_bar_code;

    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";
    private static final int REQUEST_CODE_SCAN = 0x0000;

    public static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        try {
            iv_qr_code.setImageBitmap(new CodeCreator().createQRCode("123456"));
        } catch (WriterException e) {
            e.printStackTrace();
        }

        iv_bar_code.setImageBitmap(new CodeCreator().creatBarcode(mActivity,"123456"));

    }

    @Override
    protected void initView() {
        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                tv_wo.setText("解码结果： \n" + content);

            }
        }
    }
}
