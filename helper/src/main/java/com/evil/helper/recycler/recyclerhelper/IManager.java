package com.evil.helper.recycler.recyclerhelper;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @author noah
 * @email fengxiaocan@gmail.com
 * @create 6/6/18
 * @desc ...
 */
public abstract class IManager<T extends RecyclerView.LayoutManager> extends IHelper {

    T mLayoutManager;

    IManager(RecyclerView recyclerView) {
        super(recyclerView);
        mLayoutManager = createManager();
    }

    abstract T createManager();

    public T getLayoutManager() {
        return mLayoutManager;
    }

    public abstract IManager orientation(boolean isHorizontal);

    public abstract IManager reverseLayout(boolean reverseLayout);

    @Override
    public void init() {
        recyclerView.setLayoutManager(mLayoutManager);
    }

    public CommonDecoration addCommonDecoration() {
        this.init();
        return new CommonDecoration(recyclerView);
    }

    public AroundDecoration addAroundDecoration() {
        this.init();
        return new AroundDecoration(recyclerView);
    }

    public DividerDecoration addDividerDecoration() {
        this.init();
        return new DividerDecoration(recyclerView);
    }

    public SpaceDecoration addSpaceDecoration() {
        this.init();
        return new SpaceDecoration(recyclerView);
    }

    public LineDecoration addLineDecoration() {
        this.init();
        return new LineDecoration(recyclerView);
    }

}
