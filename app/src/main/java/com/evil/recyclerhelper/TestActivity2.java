package com.evil.recyclerhelper;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.helper.RecyclerHelper;
import com.evil.rxlib.RxEmitter;
import com.evil.rxlib.RxSubscriber;
import com.evil.rxlib.RxThread;
import com.evil.rxlib.SimpleRxAcceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestActivity2 extends AppCompatActivity implements Runnable{

    private RecyclerView mRecyclerView;
    private TestAdapter3 adapter;
    private Random random=new Random();
    private final int max=(int)dpToPx(350);
    private final int min=(int)dpToPx(40);
    private final String[] URLS={
            "file:///android_asset/5d0aa2ec174b1b5d8efe0b328b8bcb34.jpg",
            "file:///android_asset/6f01a4108316992cd62c01f808bbdece.jpg",
            "file:///android_asset/af35efe832ffc4404bcb958452ea7007.jpg",
            "file:///android_asset/75eba6ce8fde85ef092925a33c9b75df.jpg",
            "file:///android_asset/d0772726620340ce69b40acbf12dc58b.jpg",
            "file:///android_asset/87f87d53fe0f51cacb2f22d1736ff203.jpg",
            "file:///android_asset/bfd3972cbdd0b07f30d27ae86c186907.jpg",
            "file:///android_asset/df1d7f8d2be313dd5cfe2efbf28baf9d.jpg",
            "file:///android_asset/94ec689081eb5b2f8c067867454657cc.jpg",
            "file:///android_asset/01591bf81fcba0f70823fd9de074d22a.jpg",
            "file:///android_asset/a0cd47a9161440d858efff4956233051.jpg",
            "file:///android_asset/177c5b79950a90e262750d43800044e0.jpg",
            "file:///android_asset/efcceb0d1d5715c07b12ec0edf68e9ad.jpg",
            "file:///android_asset/dcb4e1b2c81283a15d4f8174bf540324.jpg",
            "file:///android_asset/23730d071611a375cfb0931997d9bbd0.jpg",
            "file:///android_asset/55e03cf54cd68169820d93660c60e974.jpg",
            "file:///android_asset/336cc35c833a74e11829d623118bbc15.jpg",
            "file:///android_asset/815a7988cb8f4d6245a5f8719ae09e8a.jpg",
            "file:///android_asset/36c02cc8ece9ad50ae3076743fd307f6.jpg",
            "file:///android_asset/ad70e6e8b00a4e5c88afeb5e37293145.jpg",
            "file:///android_asset/8062b19a62537059cc335c27419f4d4e.jpg",
            "file:///android_asset/65e2b5f0ce4aa1cb305fa1235cc56c8c.jpg",
            "file:///android_asset/9a0343a3710ce58f88aeecdc99ff2153.jpg",
            "file:///android_asset/e0c4fe7134e1c07243ebec2652bf1a9c.jpg",
            "file:///android_asset/0079a148224876ccd5bf6bee98f670fd.jpg",
            "file:///android_asset/2d91b23c78fb2fc6d308df6998311c59.jpg",
            "file:///android_asset/ee798189703bb95da997956164e52079.jpg",
            "file:///android_asset/8315d51e5a9a63a4562dd22244478c0f.jpg",
            "file:///android_asset/0b872914cf016ccb4281b688bc567622.jpg",
            "file:///android_asset/11d2dc294181e51e2ca33d9fc52149c9.jpg",
            "file:///android_asset/ecdc0da6594cac4257e2e06dc05196ea.jpg",
            "file:///android_asset/8f7d7b82d2065867c1dd1d4d58d80060.jpg",
            "file:///android_asset/cde9c909a86fda72cfa1e5fd68a3cc6d.jpg",
            "file:///android_asset/ee95bb3c5c95b5901bdf566e4a13d30e.jpg",
            "file:///android_asset/685ac5475228fb24d706ea12999065e8.jpg",
            "file:///android_asset/5ec2ce1b9733d02f4d1bab06cec8bca2.jpg",
            "file:///android_asset/ced252fbd9de7501e4e71f77b63398a6.jpg",
            "file:///android_asset/abd2f0e8f5d3ac93d3df56f1f403124b.jpg",
            "file:///android_asset/6516e46ee1b6d6c6d774f429ab5cfd49.jpg",
            "file:///android_asset/9658cd5d97dfde1412cdafc63b367a3b.jpg",
            "file:///android_asset/5d87bef186c004e910ea9bb888dce110.jpg",
            "file:///android_asset/ccdaec22d7ad42a089647b6a8415d09d.jpg",
            "file:///android_asset/cf6af99afa5d310f4d66edc90a3e1890.jpg",
            "file:///android_asset/594b50b123a6167a8013f06ee452cd10.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        initView();
    }

    private void initView(){
        mRecyclerView=findViewById(R.id.recycler_view);

        adapter=new TestAdapter3();
        RecyclerHelper.with(mRecyclerView)
                      .staggeredManager()
                      .animation()
                      .adapter(adapter)
                      .init();

        start();


    }

    private void start(){
        RxThread.newThread()
                .observeOnMain()
                .open()
                .subscriber(new RxSubscriber<List<TestBean>>(){
                    @Override
                    public void onSubscribe(RxEmitter<List<TestBean>> rxEmitter){
                        List<TestBean> beans=new ArrayList<>();
                        for(int i=0;i<50;i++){
                            beans.add(random());
                        }
                        rxEmitter.onNext(beans);
                    }
                })
                .acceptor(new SimpleRxAcceptor<List<TestBean>>(){
                    @Override
                    public void onNext(List<TestBean> testBeans){
                        adapter.setDatasAndNotify(testBeans);
                    }
                });
    }

    private TestBean random(){
        TestBean testBean=new TestBean();
        int i=min+random.nextInt(max);
        testBean.setHeight(i);
        testBean.setUrl(URLS[random.nextInt(URLS.length)]);
        return testBean;
    }

    private static float dpToPx(float dp){
        return Resources.getSystem()
                        .getDisplayMetrics().density*dp;
    }

    @Override
    public void run(){
        start();
//        mRecyclerView.postDelayed(this,3000);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mRecyclerView.postDelayed(this,5000);
    }

    @Override
    protected void onPause(){
        super.onPause();
        mRecyclerView.removeCallbacks(this);
    }
}
