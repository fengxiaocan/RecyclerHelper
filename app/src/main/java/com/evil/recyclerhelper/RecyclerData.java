package com.evil.recyclerhelper;

import com.evil.recycler.inface.IRecyclerData;

import java.util.Random;

public class RecyclerData implements IRecyclerData {
    public static final Random random = new Random();
    public int id = random.nextInt(100);
    public String name = String.valueOf(id);

    @Override
    public int recycleType() {
        return 0;
    }
}
