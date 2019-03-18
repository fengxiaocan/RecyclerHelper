package com.evil.helper.recycler.recyclerhelper;

import androidx.recyclerview.widget.RecyclerView;

import com.evil.helper.recycler.decoration.AroundDecorationProps;
import com.evil.helper.recycler.decoration.AroundItemDecoration;
import com.evil.helper.recycler.inface.OnPositionListener;

/**
 * The type Common decoration.
 *
 * @author noah
 * @email fengxiaocan @gmail.com
 * @create 6 /6/18
 * @desc ...
 */
public class AroundDecoration extends IDecoration<AroundItemDecoration> {
	/**
	 * Instantiates a new Common decoration.
	 *
	 * @param recyclerView the recycler view
	 */
	AroundDecoration(RecyclerView recyclerView) {
		super(recyclerView);
	}
	
	@Override
	AroundItemDecoration createDecoration() {
		return AroundItemDecoration.builder().build();
	}
	
	/**
	 * Color common decoration.
	 * 设置装饰块的颜色
	 *
	 * @param color the color
	 * @return the common decoration
	 */
	public AroundDecoration color(int color) {
		itemDecoration.setDecorationColor(color);
		return this;
	}
	
	public AroundDecoration addDecoration(int viewType,AroundDecorationProps props) {
		itemDecoration.addDecoration(viewType,props);
		return this;
	}
	
	public AroundDecoration setDecoration(AroundDecorationProps props) {
		itemDecoration.setDecoration(props);
		return this;
	}
	
	
	public AroundDecoration space(int space) {
		AroundDecorationProps props = AroundDecorationProps.builder().space(space).build();
		return setDecoration(props);
	}
	
	public AroundDecoration space(int space,OnPositionListener listener) {
		AroundDecorationProps props = AroundDecorationProps.builder().space(space)
		                                                   .listener(listener).build();
		return setDecoration(props);
	}
	
	public AroundDecoration space(int left,int top,int right,int bottom) {
		AroundDecorationProps props = AroundDecorationProps.builder().leftSpace(left).topSpace(top)
		                                                   .rightSpace(right).bottomSpace(bottom)
		                                                   .build();
		return setDecoration(props);
	}
	
	public AroundDecoration space(
			int left,int top,int right,int bottom,OnPositionListener listener)
	{
		AroundDecorationProps props = AroundDecorationProps.builder().leftSpace(left).topSpace(top)
		                                                   .rightSpace(right).bottomSpace(bottom)
		                                                   .listener(listener).build();
		return setDecoration(props);
	}
	
	
	public AroundDecoration space(int viewType,int left,int top,int right,int bottom) {
		AroundDecorationProps props = AroundDecorationProps.builder().leftSpace(left).topSpace(top)
		                                                   .rightSpace(right).bottomSpace(bottom)
		                                                   .build();
		return addDecoration(viewType,props);
	}
	
	
	public AroundDecoration space(
			int viewType,int left,int top,int right,int bottom,OnPositionListener listener)
	{
		AroundDecorationProps props = AroundDecorationProps.builder().leftSpace(left).topSpace(top)
		                                                   .rightSpace(right).bottomSpace(bottom)
		                                                   .listener(listener).build();
		return addDecoration(viewType,props);
	}
	
	public AroundDecoration space(int viewType,int space) {
		AroundDecorationProps props = AroundDecorationProps.builder().space(space).build();
		return addDecoration(viewType,props);
	}
	
	public AroundDecoration space(int viewType,int space,OnPositionListener listener) {
		AroundDecorationProps props = AroundDecorationProps.builder().space(space)
		                                                   .listener(listener).build();
		return addDecoration(viewType,props);
	}
}
