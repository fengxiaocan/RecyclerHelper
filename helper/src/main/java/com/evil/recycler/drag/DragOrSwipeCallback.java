package com.evil.recycler.drag;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.adapter.BaseRecyclerViewAdapter;
import com.evil.recycler.adapter.ComRecyclerViewAdapter;
import com.evil.recycler.holder.BaseRecyclerHolder;

import java.util.List;


public class DragOrSwipeCallback extends ItemTouchHelper.SimpleCallback {
    private final SelectAnimation selectAnimation;
    protected boolean isCanDrag;
    protected boolean isCanSwipe;

    public DragOrSwipeCallback(boolean canDrag, boolean canSwipe) {
        this(canDrag, canSwipe, new DefaultAnimation());
    }

    public DragOrSwipeCallback(boolean canDrag, boolean canSwipe, SelectAnimation selectAnimation) {
        super(canDrag ? ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT : 0, canSwipe ? ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT : 0);
        this.isCanDrag = canDrag;
        this.isCanSwipe = canSwipe;
        this.selectAnimation = selectAnimation;
    }

    @Override
    public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof BaseRecyclerHolder) {
            if (!((BaseRecyclerHolder) viewHolder).canSwipe()) {
                return 0;
            }
        }
        return super.getSwipeDirs(recyclerView, viewHolder);
    }

    @Override
    public int getDragDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof BaseRecyclerHolder) {
            if (!((BaseRecyclerHolder) viewHolder).canDrag()) {
                return 0;
            }
        }
        return super.getDragDirs(recyclerView, viewHolder);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter.getItemCount() < 2) {
            return false;
        }
        if (adapter instanceof BaseRecyclerViewAdapter) {
            BaseRecyclerViewAdapter adapter1 = (BaseRecyclerViewAdapter) adapter;
            if (isCanDrag(viewHolder) && isCanDrag(target)) {
                //得到当拖拽的viewHolder的Position
                int fromPosition = viewHolder.getBindingAdapterPosition();
                //拿到当前拖拽到的item的viewHolder
                int toPosition = target.getBindingAdapterPosition();
                int offset = 0;
                if (adapter1 instanceof ComRecyclerViewAdapter) {
                    offset = ((ComRecyclerViewAdapter) adapter1).getHeaderCount();
                }

                List datas = adapter1.getDatas();

                Object o = datas.get(fromPosition - offset);
                datas.remove(fromPosition - offset);
                datas.add(toPosition - offset, o);

                recyclerView.getAdapter().notifyItemMoved(fromPosition, toPosition);
                return true;
            }
        }
        return false;
    }

    protected final boolean isCanDrag(RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof BaseRecyclerHolder) {
            BaseRecyclerHolder recyclerHolder = (BaseRecyclerHolder) viewHolder;
            return recyclerHolder.canDrag();
        }
        return false;
    }

    /**
     * 侧滑删除回调的方法
     *
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        if (viewHolder instanceof BaseRecyclerHolder) {
            BaseRecyclerHolder viewHolder1 = (BaseRecyclerHolder) viewHolder;
            if (viewHolder1.canSwipe()) {
                viewHolder1.removeAndNotifySelf();
            }
        }
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return super.isLongPressDragEnabled();
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return super.isItemViewSwipeEnabled();
    }

    /**
     * 长按时调用
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if (selectAnimation != null) {
            selectAnimation.onSelected(viewHolder, actionState);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (selectAnimation != null) {
            selectAnimation.onCancel(viewHolder);
        }
        super.clearView(recyclerView, viewHolder);
    }

    public interface SelectAnimation {
        void onSelected(RecyclerView.ViewHolder viewHolder, int actionState);

        void onCancel(RecyclerView.ViewHolder viewHolder);
    }

    public static class DefaultAnimation implements SelectAnimation {
        @Override
        public void onSelected(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (viewHolder != null && viewHolder.itemView != null && actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                viewHolder.itemView.setScaleX(1.1f);
                viewHolder.itemView.setScaleY(1.1f);
                viewHolder.itemView.setAlpha(0.9f);
            }
        }

        @Override
        public void onCancel(RecyclerView.ViewHolder viewHolder) {
            if (viewHolder != null && viewHolder.itemView != null) {
                viewHolder.itemView.setScaleX(1f);
                viewHolder.itemView.setScaleY(1f);
                viewHolder.itemView.setAlpha(1f);
            }
        }
    }
}
