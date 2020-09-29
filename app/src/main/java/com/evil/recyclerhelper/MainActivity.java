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
import com.evil.recycler.holder.RecyclerViewHolder;
import com.evil.recycler.inface.OnAdapterItemClickListener;
import com.evil.recycler.inface.OnFooterItemClickListener;
import com.evil.recycler.inface.OnHeaderItemClickListener;
import com.evil.recycler.inface.OnMenuItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TestSelectAdapter mTestAdapter;
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
        mTestAdapter = new TestSelectAdapter();
        RecyclerHelper.with(mRecyclerView).linearManager().matchWidth().animation().dragAndSwipe().adapter(
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

//        mTestAdapter.setEmptyCompatHeaderOrFooter(true);
//        mTestAdapter.setUseEmpty(true);
        mTestAdapter.setEmptyView(mRecyclerView, R.layout.view_empty);
        mTestAdapter.setMultiSelected(true);
//        mTestAdapter.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//            @Override
//            public void onMenuItemClick(View v, int position, int menuPosition) {
//                Toast.makeText(MainActivity.this,
//                        "position=" + position + "侧滑菜单 menuPosition=" + menuPosition,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//        mTestAdapter.setOnItemClickListener(new OnAdapterItemClickListener<RecyclerData>() {
//            @Override
//            public void onItemClick(View view, RecyclerViewHolder<RecyclerData> baseRecyclerHolder,
//                    int position)
//            {
//                Toast.makeText(MainActivity.this, "条目 position=" + position, Toast.LENGTH_SHORT)
//                     .show();
//            }
//
//        });
//        mTestAdapter.setOnHeaderClickListener(new OnHeaderItemClickListener() {
//            @Override
//            public void onHeaderClick(View view, int position) {
//                Toast.makeText(MainActivity.this, "头部 position=" + position, Toast.LENGTH_SHORT)
//                     .show();
//            }
//        });
//        mTestAdapter.setOnFooterClickListener(new OnFooterItemClickListener() {
//            @Override
//            public void onFooterClick(View view, int position) {
//                Toast.makeText(MainActivity.this, "尾部 position=" + position, Toast.LENGTH_SHORT)
//                     .show();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                List<SelectorBean> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    SelectorBean data = new SelectorBean();
                    list.add(data);
                }
                mTestAdapter.addDatasAndNotify(list);
                break;
            case R.id.bt_set:
                List<SelectorBean> lists = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    SelectorBean data = new SelectorBean();
                    lists.add(data);
                }
                mTestAdapter.setDatasAndNotify(lists);
                break;
            case R.id.bt_instert:
                List<SelectorBean> list2 = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    SelectorBean data = new SelectorBean();
                    list2.add(data);
                }
                mTestAdapter.insertDatasAndNotify(RecyclerData.random.nextInt(6), list2);
                break;
            case R.id.bt_delect:
                mTestAdapter.removeAndNotify(0);
                break;
//            case R.id.bt_add_header:
//                mTestAdapter.addHeader(mRecyclerView, R.layout.view_header);
//                break;
//            case R.id.bt_add_footer:
//                mTestAdapter.addFooter(mRecyclerView, R.layout.view_footer);
//                break;
//            case R.id.bt_remove_header:
//                mTestAdapter.removeHeader(0);
//                break;
//            case R.id.bt_remove_footer:
//                mTestAdapter.removeFooter(0);
//                break;
        }
    }

}
