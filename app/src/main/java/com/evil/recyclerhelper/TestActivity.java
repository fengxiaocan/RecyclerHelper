package com.evil.recyclerhelper;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.evil.recycler.decoration.RecyclerDividerProps;
import com.evil.recycler.decoration.RecyclerViewDivider;
import com.evil.recycler.inface.IRecyclerData;
import com.evil.recycler.inface.OnAdapterItemClickListener;
import com.evil.recycler.helper.RecyclerHelper;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TestAdapter2 mTestAdapter;
    private Button mBtAdd;
    private Button mBtEmpty;
    private Button mBtError;
    private Button mBtLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mTestAdapter = new TestAdapter2();
        mTestAdapter.setOnItemClickListener(new OnAdapterItemClickListener<RecyclerData>() {
            public void onItemClick(View view, List<RecyclerData> list, int position) {
                Toast.makeText(TestActivity.this, "点击content", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerHelper.with(mRecyclerView).linearManager().matchWidth().animation().adapter(
                mTestAdapter).init();

        mRecyclerView.addItemDecoration(new RecyclerViewDivider(RecyclerDividerProps.with(
                Color.BLACK).height(3).marginLeft(30).marginRight(50).build()));
        mTestAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty,mRecyclerView,false));
        mTestAdapter.setLoadingView(LayoutInflater.from(this).inflate(R.layout.view_loading,mRecyclerView,false));
        mTestAdapter.setErrorView(LayoutInflater.from(this).inflate(R.layout.view_error,mRecyclerView,false));
        mTestAdapter.addHeader(LayoutInflater.from(this).inflate(R.layout.view_header,mRecyclerView,false));
        mTestAdapter.addFooter(LayoutInflater.from(this).inflate(R.layout.view_footer,mRecyclerView,false));
        mTestAdapter.setEmptyCompatHeaderOrFooter(true);
        mTestAdapter.setAutoShowEmpty(true);
        mTestAdapter.showDefaultView();

        mBtAdd = (Button) findViewById(R.id.bt_add);
        mBtAdd.setOnClickListener(this);
        mBtEmpty = (Button) findViewById(R.id.bt_empty);
        mBtEmpty.setOnClickListener(this);
        mBtError = (Button) findViewById(R.id.bt_error);
        mBtError.setOnClickListener(this);
        mBtLoad = (Button) findViewById(R.id.bt_load);
        mBtLoad.setOnClickListener(this);
        mRecyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(view);
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                mTestAdapter.showDefaultView();
                mTestAdapter.addDataAndNotify(new RecyclerData());
                break;
            case R.id.bt_empty:
                mTestAdapter.showEmptyView();
                break;
            case R.id.bt_error:
                mTestAdapter.showErrorView();
                break;
            case R.id.bt_load:
                mTestAdapter.showLoadingView();
                break;
        }
    }
}
