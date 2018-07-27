package com.evil.recyclerhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.evil.helper.recycler.inface.IRecycleData;
import com.evil.helper.recycler.recyclerhelper.RecyclerHelper;
import com.evil.rxlib.RxEmitter;
import com.evil.rxlib.RxSubscriber;
import com.evil.rxlib.RxThread;
import com.evil.rxlib.SimpleRxAcceptor;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TestAdapter mTestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mTestAdapter = new TestAdapter();
        RecyclerHelper.with(mRecyclerView)
                      .linearManager()
                      .matchWidth()
                      .addDividerDecoration()
                      .animation()
                      .adapter(mTestAdapter)
                      .init();
        for (int i = 0;i < 30;i++) {
            mTestAdapter.addData(new RecyclerData());
        }
        RxThread.newThread().open().observeOnMain().subscriber(new RxSubscriber<RecyclerData>() {
            @Override
            public void onSubscribe(RxEmitter<RecyclerData> rxEmitter) {
                for (int i = 0;i < 30;i++) {
                    rxEmitter.onNext(new RecyclerData());
                }
            }
        }).acceptOnSync().acceptor(new SimpleRxAcceptor<RecyclerData>() {
            @Override
            public void onNext(RecyclerData recyclerData) {
                mTestAdapter.addData(recyclerData);
            }

            @Override
            public void onComplete() {
                mTestAdapter.notifyDataSetChanged();
            }
        });
    }

    private class RecyclerData implements IRecycleData {

        @Override
        public int getRecycleType() {
            return 0;
        }
    }
}
