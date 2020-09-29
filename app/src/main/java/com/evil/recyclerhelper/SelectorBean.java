package com.evil.recyclerhelper;

import com.evil.recycler.inface.IRecyclerSelector;

public class SelectorBean implements IRecyclerSelector {
    private boolean selected;

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public void onSelected(boolean isSelected) {
        this.selected = isSelected;
    }
}
