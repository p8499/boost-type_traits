package com.p8499.lang.ime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.p8499.lang.ime.rime.RimeStatus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ActivityPreference extends AppCompatActivity {
    @BindView(R.id.select_lang)
    protected RelativeLayout mSelectLang;
    @BindView(R.id.select_lang_val)
    protected TextView mSelectLangVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        ButterKnife.bind(this);
        //intent
        Intent intent = getIntent();
        if (intent.hasExtra("sessionId"))
            onSessionId(intent.getLongExtra("sessionId", 0));
    }

    public void onSessionId(long sessionId) {
        RimeStatus status = JniWrapper.rimeGetStatus(sessionId);
        mSelectLangVal.setTag(status.getSchemaId());
        mSelectLangVal.setText(status.getSchemaName());
    }
    /*call me with sessionId, just let me know which rime session you want to configure, then i'll act with that session.*/
}
