package com.evil.recyclerhelper;

import com.evil.recycler.inface.IDiffData;
import com.evil.recycler.inface.IRecyclerData;

import java.util.Random;

public class RecyclerData implements IRecyclerData, IDiffData<RecyclerData> {
    public static final Random random = new Random();
    public int id = random.nextInt(100);
    public String name = String.valueOf(id);

    @Override
    public int recycleType() {
        return 0;
    }

    @Override
    public boolean isEquals(RecyclerData obj) {
        return this.id == obj.id;
    }

    @Override
    public boolean isSameData(RecyclerData obj) {
        return this.name.equals(obj.name) ;
    }
}
