package com.p8499.lang.ime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.p8499.lang.ime.rime.RimeStatus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import polanski.option.Option;

public class ActivityPreference extends AppCompatActivity {
    @BindView(R.id.select_lang)
    protected RelativeLayout mSelectLang;
    @BindView(R.id.select_lang_val)
    protected TextView mSelectLangVal;

    protected Presenter presenter = new Presenter();

    //region [initialization]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        ButterKnife.bind(this);
        presenter.request();
    }

    //endregion

    //region [activity lifecycle]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            presenter.selectSchema(data.getStringExtra("schemaId"));
        }
    }

    //endregion

    //region [form control]
    @OnClick(R.id.select_lang)
    public void onSelectLangClick(View v) {
        Intent intent = new Intent(this, ActivitySchemas.class);
        intent.putExtra("schemaId", (String) mSelectLangVal.getTag());
        startActivityForResult(intent, 0);
    }

    //endregion

    //region [presenter methods]
    public void onSessionId(long sessionId) {
        RimeStatus status = JniWrapper.rimeGetStatus(sessionId);
        mSelectLangVal.setTag(status.getSchemaId());
        mSelectLangVal.setText(status.getSchemaName());
    }

    //endregion

    class Presenter {
        public void request() {
            Flowable.empty()
                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnComplete(() -> Option.ofObj(getIntent()).filter(intent -> intent.hasExtra("sessionId")).
//                            ifSome(intent -> onSessionId(intent.getLongExtra("sessionId", 0))))
                    .doOnComplete(() -> Option.ofObj(JniWrapper.rimeConfigGetString("user", "var/previously_selected_schema"))
                            /*.ifSome(s -> onSchema(schemaId))*/)
                    .subscribe();
        }

        public void selectSchema(String schemaId) {
            Flowable.empty()
                    .observeOn(AndroidSchedulers.mainThread())
//                    .doOnComplete(() -> Option.ofObj(getIntent()).filter(intent -> intent.hasExtra("sessionId"))
//                            .ifSome(intent -> JniWrapper.rimeSelectSchema(intent.getLongExtra("sessionId", 0), schemaId))
//                            .ifSome(intent -> onSessionId(intent.getLongExtra("sessionId", 0))))
                    .doOnComplete(() -> Option.ofObj(schemaId)
                            .ifSome(s -> JniWrapper.rimeConfigSetString("user", "var/previously_selected_schema", schemaId))
                            /*.ifSome(s -> onSchema(schemaId))*/)
                    .subscribe();
        }
    }
}
