package com.evil.recyclerhelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.evil.helper.recycler.decoration.RecyclerDividerProps;
import com.evil.helper.recycler.decoration.RecyclerViewDivider;
import com.evil.helper.recycler.inface.IRecycleData;
import com.evil.helper.recycler.inface.OnAdapterItemClickListener;
import com.evil.helper.recycler.inface.OnMenuItemClickListener;
import com.evil.helper.recycler.recyclerhelper.RecyclerHelper;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private TestAdapter mTestAdapter;
    private Button mBtAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mTestAdapter = new TestAdapter();
        mTestAdapter.setOnItemClickListener(new OnAdapterItemClickListener<IRecycleData>() {
            public void onItemClick(View view, List<IRecycleData> list, int position) {
//                Toast.makeText(MainActivity.this, "点击content", Toast.LENGTH_SHORT)
//                     .show();
                startActivity(new Intent(MainActivity.this,TestActivity.class));
            }
        });
        mTestAdapter.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            public void onMenuItemClick(View v, int position, int menuPosition) {
                Toast.makeText(MainActivity.this, "点击menu", Toast.LENGTH_SHORT)
                     .show();
            }
        });

        RecyclerHelper.with(mRecyclerView)
                      .linearManager()
                      .matchWidth()
                      .animation()
                      .adapter(mTestAdapter)
                      .init();
        mRecyclerView.addItemDecoration(new RecyclerViewDivider(RecyclerDividerProps.with(Color.BLACK)
                                                                                    .height(3)
                                                                                    .marginLeft(30)
                                                                                    .marginRight(50)
                                                                                    .build()));
        //		mTestAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty,mRecyclerView,false));


        //		RxThread.newThread().open().observeOnMain().subscriber(new RxSubscriber<RecyclerData>() {
        //			@Override
        //			public void onSubscribe(RxEmitter<RecyclerData> rxEmitter) {
        //				for (int i = 0;i < 30;i++) {
        //					rxEmitter.onNext(new RecyclerData());
        //				}
        //			}
        //		}).acceptOnSync().acceptor(new SimpleRxAcceptor<RecyclerData>() {
        //			@Override
        //			public void onNext(RecyclerData recyclerData) {
        //
        //			}
        //
        //			@Override
        //			public void onComplete() {
        //				mTestAdapter.notifyDataSetChanged();
        //			}
        //		});
        mBtAdd = (Button) findViewById(R.id.bt_add);
        mBtAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                mTestAdapter.addDataAndNotify(new RecyclerData());
                break;
        }
    }

    private class RecyclerData implements IRecycleData {

        @Override
        public int getRecycleType() {
            return 0;
        }
    }
}
