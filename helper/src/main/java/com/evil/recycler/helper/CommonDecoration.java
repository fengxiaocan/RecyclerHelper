package com.evil.recycler.helper;

import androidx.recyclerview.widget.RecyclerView;

import com.evil.recycler.decoration.CommonItemDecoration;
import com.evil.recycler.decoration.ItemDecorationProps;

/**
 * The type Common decoration.
 *
 * @author noah
 * @email fengxiaocan @gmail.com
 * @create 6 /6/18
 * @desc ...
 */
public class CommonDecoration extends IDecoration<CommonItemDecoration> {
    /**
     * Instantiates a new Common decoration.
     *
     * @param recyclerView the recycler view
     */
    CommonDecoration(RecyclerView recyclerView) {
        super(recyclerView);
    }

    @Override
    CommonItemDecoration createDecoration() {
        return CommonItemDecoration.builder().build();
    }

    /**
     * Color common decoration.
     * 设置装饰块的颜色
     *
     * @param color the color
     * @return the common decoration
     */
    public CommonDecoration color(int color) {
        itemDecoration.setDecorationColor(color);
        return this;
    }

    /**
     * Add decoration common decoration.
     * 添加装饰块
     *
     * @param viewType the recyclerView viewtype
     * @param props the props
     * @return the common decoration
     */
    public CommonDecoration addDecoration(int viewType,ItemDecorationProps props) {
        itemDecoration.addDecoration(viewType,props);
        return this;
    }

    /**
     * Sets decoration.
     *
     * @param props the props
     * @return the decoration
     */
    public CommonDecoration setDecoration(ItemDecorationProps props) {
        itemDecoration.setDecoration(props);
        return this;
    }

    /**
     * Space common decoration.
     *
     * @param space the space
     * @return the common decoration
     */
    public CommonDecoration space(int space) {
        ItemDecorationProps props = ItemDecorationProps.builder().space(space).build();
        return setDecoration(props);
    }

    /**
     * Space common decoration.
     *
     * @param vertical the vertical
     * @param horizontal the horizontal
     * @return the common decoration
     */
    public CommonDecoration space(int vertical,int horizontal) {
        ItemDecorationProps props = ItemDecorationProps.builder()
                                                       .verticalSpace(vertical)
                                                       .horizontalSpace(horizontal)
                                                       .build();
        return setDecoration(props);
    }

    /**
     * Space common decoration.
     *
     * @param viewType the recyclerView viewtype
     * @param vertical the vertical
     * @param horizontal the horizontal
     * @return the common decoration
     */
    public CommonDecoration space(int viewType,int vertical,int horizontal) {
        ItemDecorationProps props = ItemDecorationProps.builder()
                                                       .verticalSpace(vertical)
                                                       .horizontalSpace(horizontal)
                                                       .build();
        return addDecoration(viewType,props);
    }

    /**
     * Space common decoration.
     *
     * @param vertical the vertical
     * @param horizontal the horizontal
     * @param first the first
     * @param last the last
     * @return the common decoration
     */
    public CommonDecoration space(int vertical,int horizontal,int first,int last) {
        ItemDecorationProps props = ItemDecorationProps.builder()
                                                       .verticalSpace(vertical)
                                                       .horizontalSpace(horizontal)
                                                       .firstSpace(first)
                                                       .lastSpace(last)
                                                       .build();
        return setDecoration(props);
    }

    /**
     * Space common decoration.
     *
     * @param viewType the recyclerView viewtype
     * @param vertical the vertical
     * @param horizontal the horizontal
     * @param first the first
     * @param last the last
     * @return the common decoration
     */
    public CommonDecoration space(int viewType,int vertical,int horizontal,int first,int last) {
        ItemDecorationProps props = ItemDecorationProps.builder()
                                                       .verticalSpace(vertical)
                                                       .horizontalSpace(horizontal)
                                                       .firstSpace(first)
                                                       .lastSpace(last)
                                                       .build();
        return addDecoration(viewType,props);
    }
}
