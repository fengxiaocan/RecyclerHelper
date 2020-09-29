package com.evil.recyclerhelper;

import android.view.View;
import android.widget.CheckBox;

import com.evil.recycler.adapter.SelectedRecyclerViewAdapter;
import com.evil.recycler.holder.SelectedRecyclerHolder;

public class TestSelectAdapter extends SelectedRecyclerViewAdapter<SelectorBean, TestSelectAdapter.SelectHolder> {

    @Override
    public SelectHolder createViewHolder(View view, int viewType) {
        return new SelectHolder(view);
    }

    @Override
    public int onCreateLayoutRes(int viewType) {
        return R.layout.item_selected;
    }

    public static class SelectHolder extends SelectedRecyclerHolder<SelectorBean> {
        private CheckBox checkbox;

        public SelectHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onSelectedChanged(boolean selected) {
            checkbox.setChecked(selected);
        }

        @Override
        public void onSelectableChanged(boolean selectable) {
            checkbox.setVisibility(selectable ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onBindData(final SelectorBean data) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelected(!data.isSelected());
                }
            });
        }

        @Override
        public void initView(View rootView) {
            checkbox = rootView.findViewById(R.id.checkbox);
            checkbox.setClickable(false);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    getSelectedAdapter().setSelectable(!isSelectable());
                    return false;
                }
            });
        }
    }
}
