package com.evil.recyclerhelper;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.decoration.RecyclerDividerProps;
import com.evil.recycler.decoration.RecyclerViewDivider;
import com.evil.recycler.helper.RecyclerHelper;
import com.evil.recycler.holder.BaseRecyclerHolder;
import com.evil.recycler.inface.OnAdapterItemClickListener;
import com.evil.recycler.inface.OnFooterItemClickListener;
import com.evil.recycler.inface.OnHeaderItemClickListener;
import com.evil.recycler.inface.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TestAdapter2 mTestAdapter;
    private Button mBtAdd;
    private Button mBtSet;
    private Button mBtInstert;
    private Button mBtDelect;
    private Button mBtAddHeader;
    private Button mBtAddFooter;
    private Button mBtRemoveHeader;
    private Button mBtRemoveFooter;

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
        mBtAddHeader = findViewById(R.id.bt_add_header);
        mBtAddHeader.setOnClickListener(this);
        mBtAddFooter = findViewById(R.id.bt_add_footer);
        mBtAddFooter.setOnClickListener(this);
        mBtRemoveHeader = findViewById(R.id.bt_remove_header);
        mBtRemoveHeader.setOnClickListener(this);
        mBtRemoveFooter = findViewById(R.id.bt_remove_footer);
        mBtRemoveFooter.setOnClickListener(this);

        mTestAdapter.setEmptyCompatHeaderOrFooter(true);
        mTestAdapter.setUseEmpty(true);
        mTestAdapter.setEmptyView(mRecyclerView, R.layout.view_empty);
        mTestAdapter.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View v, int position, int menuPosition) {
                Toast.makeText(MainActivity.this,
                        "position=" + position + "侧滑菜单 menuPosition=" + menuPosition,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mTestAdapter.setOnItemClickListener(new OnAdapterItemClickListener<RecyclerData>() {
            @Override
            public void onItemClick(View view, BaseRecyclerHolder baseRecyclerHolder, int position)
            {
                Toast.makeText(MainActivity.this, "条目 position=" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mTestAdapter.setOnHeaderClickListener(new OnHeaderItemClickListener() {
            @Override
            public void onHeaderClick(View view, int position) {
                Toast.makeText(MainActivity.this, "头部 position=" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mTestAdapter.setOnFooterClickListener(new OnFooterItemClickListener() {
            @Override
            public void onFooterClick(View view, int position) {
                Toast.makeText(MainActivity.this, "尾部 position=" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
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
                mTestAdapter.removeAndNotify(0);
                break;
            case R.id.bt_add_header:
                mTestAdapter.addHeader(mRecyclerView, R.layout.view_header);
                break;
            case R.id.bt_add_footer:
                mTestAdapter.addFooter(mRecyclerView, R.layout.view_footer);
                break;
            case R.id.bt_remove_header:
                mTestAdapter.removeHeader(0);
                break;
            case R.id.bt_remove_footer:
                mTestAdapter.removeFooter(0);
                break;
        }
    }

}
