package com.evil.recyclerhelper;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.decoration.RecyclerDividerProps;
import com.evil.recycler.decoration.RecyclerViewDivider;
import com.evil.recycler.helper.RecyclerHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TestAdapter2 mTestAdapter;
    private Button mBtAdd;
    private Button mBtSet;
    private Button mBtInstert;
    private Button mBtDelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mTestAdapter = new TestAdapter2();
        RecyclerHelper.with(mRecyclerView).linearManager().matchWidth().animation().adapter(
                mTestAdapter).init();
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(RecyclerDividerProps.with(
                Color.BLACK).height(3).marginLeft(30).marginRight(50).build()));
        mBtAdd = findViewById(R.id.bt_add);
        mBtAdd.setOnClickListener(this);
        mBtSet = findViewById(R.id.bt_set);
        mBtSet.setOnClickListener(this);
        mBtInstert = findViewById(R.id.bt_instert);
        mBtInstert.setOnClickListener(this);
        mBtDelect = findViewById(R.id.bt_delect);
        mBtDelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                List<RecyclerData> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    RecyclerData data = new RecyclerData();
                    list.add(data);
                }
                mTestAdapter.addDatasAndNotify(list);
                break;
            case R.id.bt_set:
                List<RecyclerData> lists = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    RecyclerData data = new RecyclerData();
                    lists.add(data);
                }
                mTestAdapter.setDatasAndNotify(lists);
                break;
            case R.id.bt_instert:
                List<RecyclerData> list2 = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    RecyclerData data = new RecyclerData();
                    list2.add(data);
                }
                mTestAdapter.insertDatasAndNotify(RecyclerData.random.nextInt(6), list2);
                break;
            case R.id.bt_delect:
//                mTestAdapter.removeAndNotify(RecyclerData.random.nextInt(5));
                mTestAdapter.removeAndNotify(1);
                break;
        }
    }

}
