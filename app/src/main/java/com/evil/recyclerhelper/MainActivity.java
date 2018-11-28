package com.evil.recyclerhelper;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.evil.helper.recycler.inface.IRecycleData;
import com.evil.helper.recycler.inface.OnAdapterItemClickListener;
import com.evil.helper.recycler.inface.SimplePositionListener;
import com.evil.helper.recycler.recyclerhelper.RecyclerHelper;

import java.util.List;

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
		mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
		mTestAdapter = new TestAdapter();
		RecyclerHelper.with(mRecyclerView).linearManager().matchWidth().addAroundDecoration()
		              .space(50,50,50,50,new SimplePositionListener(){
			            
		              }).animation().adapter(mTestAdapter).init();
		//		mTestAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.view_empty,mRecyclerView,false));
		mTestAdapter.setEmptyView(R.layout.view_empty,mRecyclerView);
		mTestAdapter.setOnItemClickListener(new OnAdapterItemClickListener<IRecycleData>() {
			@Override
			public void onItemClick(View view,List<IRecycleData> list,int position) {
				Toast.makeText(MainActivity.this,"点击了" + position,Toast.LENGTH_SHORT).show();
			}
		});
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
		mBtAdd = (Button)findViewById(R.id.bt_add);
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
