package com.example.library.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.library.R;


/**
 * Created by Mr'Tang on 2018/4/17.
 */

public class CommonDialog extends Dialog {

    private BaseAdapter mAdpater;
    private AdapterView.OnItemClickListener mItemClickListener;

    private String title;
    private String contentText;
    private String cancel;
    private String ok;
    private int type;//1--正常模式  2--按钮只有一个（有head）  3--按钮只有一个（无head） 4--按钮两个（无head） 5--list模式
    private View.OnClickListener cancelListener;
    private View.OnClickListener okListener;


    public CommonDialog(Context context) {
        super(context, R.style.MyDialogStyleBottom);
    }

    public CommonDialog(Context context, int type, String title, String contentText, String cancel, String ok) {
        this(context);
        this.type = type;
        this.title = title;
        this.contentText = contentText;
        this.cancel = cancel;
        this.ok = ok;
    }

    public CommonDialog(Context context, String title, BaseAdapter mAdpater, AdapterView.OnItemClickListener mItemClickListener, String cancel, String ok) {
        this(context);
        this.type = 5;
        this.title = title;
        this.contentText = "";
        this.mAdpater = mAdpater;
        this.mItemClickListener = mItemClickListener;
        this.cancel = cancel;
        this.ok = ok;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common);
        TextView titleTextView = findViewById(R.id.widget_title);
        TextView contentTextView = findViewById(R.id.widget_contentTextView);
        ListView contentListView = findViewById(R.id.widget_contentListView);
        Button cancelButton = findViewById(R.id.widget_cancel);
        Button okButton = findViewById(R.id.widget_ok);
        Button sureButton = findViewById(R.id.widget_sure);
        LinearLayout widget_layout_head = findViewById(R.id.widget_layout_head);
        LinearLayout widget_layout_content = findViewById(R.id.widget_layout_content);
        LinearLayout widget_layout_bottom = findViewById(R.id.widget_layout_bottom);
        View line2 = findViewById(R.id.line2);
        titleTextView.setText(title);

        switch (type) {
            case 1://1--正常模式

                break;
            case 2://2--按钮只有一个（有head）
                cancelButton.setVisibility(View.GONE);
                okButton.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                sureButton.setVisibility(View.VISIBLE);
                break;
            case 3://3--按钮只有一个（无head）
                cancelButton.setVisibility(View.GONE);
                okButton.setVisibility(View.GONE);
                sureButton.setVisibility(View.VISIBLE);
                widget_layout_head.setVisibility(View.GONE);
                line2.setVisibility(View.GONE);
                widget_layout_content.setBackgroundResource(R.drawable.common_dialog_up_white);
                break;
            case 4://4--按钮两个（无head）
                widget_layout_head.setVisibility(View.GONE);
                widget_layout_content.setBackgroundResource(R.drawable.common_dialog_up_white);
                break;
            case 5://5--list模式
                contentTextView.setVisibility(View.GONE);
                contentListView.setVisibility(View.VISIBLE);
                widget_layout_bottom.setVisibility(View.GONE);
                widget_layout_content.setBackgroundResource(R.drawable.common_dialog_btn_sure);
                contentListView.setAdapter(mAdpater);
                contentListView.setOnItemClickListener(mItemClickListener);
                break;
            default:

                break;
        }
        titleTextView.setText(title.isEmpty() ? "温馨提示" : title);
        contentTextView.setText(contentText.isEmpty() ? "你好~" : contentText);
        cancelButton.setText(cancel.isEmpty() ? "否" : cancel);
        okButton.setText(ok.isEmpty() ? "是" : ok);
        sureButton.setText(ok.isEmpty() ? "是" : ok);
        cancelButton.setOnClickListener(getCancelListener());
        okButton.setOnClickListener(getOkListener());
        sureButton.setOnClickListener(getOkListener());
        setCanceledOnTouchOutside(false);
    }


    public View.OnClickListener getCancelListener() {
        if (cancelListener == null) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            };
        }
        return cancelListener;
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }

    public View.OnClickListener getOkListener() {
        return okListener;
    }

    public void setOkListener(View.OnClickListener okListener) {
        this.okListener = okListener;
    }
}

