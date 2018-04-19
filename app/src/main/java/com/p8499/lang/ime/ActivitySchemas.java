package com.p8499.lang.ime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.p8499.lang.ime.rime.RimeSchemaListItem;
import com.p8499.lang.ime.rime.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import polanski.option.Option;

public class ActivitySchemas extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.refresh)
    protected SwipeRefreshLayout mRefresh;
    @BindView(R.id.schemas)
    protected RecyclerView mSchemasView;
    protected Presenter presenter = new Presenter();

    //region [initialization]
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schemas);
        ButterKnife.bind(this);
        mRefresh.setOnRefreshListener(this);
        mSchemasView.setAdapter(new SchemasAdapter());
        mSchemasView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        presenter.request();
    }

    //endregion

    //region [form control]
    @Override
    public void onRefresh() {
        presenter.request();
    }

    //endregion

    //region [presenter methods]
    public void onSchemas(RimeSchemaListItem[] schemas) {
        SchemasAdapter adapter = (SchemasAdapter) mSchemasView.getAdapter();
        adapter.setSchemas(schemas);
        adapter.notifyDataSetChanged();
        mRefresh.setRefreshing(false);
    }

    public void onSchemaId(String schemaId) {
        SchemasAdapter adapter = (SchemasAdapter) mSchemasView.getAdapter();
        adapter.setSelectedSchemaId(schemaId);
        adapter.notifyDataSetChanged();
    }
    //endregion

    class SchemasViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.select_lang_val)
        public TextView mSelectLangVal;
        @BindView(R.id.select_lang_check)
        public ImageView mSelectLangCheck;

        public SchemasViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.select_lang)
        public void onSelectLangClick(View v) {
            Intent intent = new Intent();
            intent.putExtra("schemaId", (String) mSelectLangVal.getTag());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    class SchemasAdapter extends RecyclerView.Adapter<SchemasViewHolder> {
        /**
         * data to present: all schemas
         */
        protected RimeSchemaListItem[] mSchemas;

        /**
         * data to present: selected schema id
         */
        protected String mSelectedSchemaId;

        public RimeSchemaListItem[] getSchemas() {
            return mSchemas;
        }

        public SchemasAdapter setSchemas(RimeSchemaListItem[] schemas) {
            this.mSchemas = schemas;
            return this;
        }

        public String getSelectedSchemaId() {
            return mSelectedSchemaId;
        }

        public SchemasAdapter setSelectedSchemaId(String selectedSchemaId) {
            this.mSelectedSchemaId = selectedSchemaId;
            return this;
        }

        @NonNull
        @Override
        public SchemasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new SchemasViewHolder(getLayoutInflater().inflate(R.layout.activity_schemas_recycler, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull SchemasViewHolder holder, int position) {
            RimeSchemaListItem schema = mSchemas[position];
            holder.mSelectLangVal.setTag(schema.getSchemaId());
            holder.mSelectLangVal.setText(schema.getName());
            holder.mSelectLangCheck.setVisibility(Utils.equals(mSelectedSchemaId, schema.getSchemaId()) ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public int getItemCount() {
            return mSchemas == null ? 0 : mSchemas.length;
        }
    }

    class Presenter {
        public void request() {
            Flowable.empty()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnComplete(() -> onSchemas(JniWrapper.rimeGetSchemaList()))
                    .doOnComplete(() -> Option.ofObj(getIntent()).filter(intent -> intent.hasExtra("schemaId")).ifSome(intent -> onSchemaId(intent.getStringExtra("schemaId"))))
                    .subscribe();
        }
    }
}
