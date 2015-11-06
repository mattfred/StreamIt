package com.mattfred.streamit;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

public class ProgressDialog extends Dialog {

    private TextView textView;

    public ProgressDialog(Context context) {
        super(context);
        setContentView(R.layout.activity_progress_dialog);
        setCancelable(false);

        textView = (TextView) findViewById(R.id.loading_text);
    }

    public void setText(String text) {
        textView.setText(text);
    }
}
