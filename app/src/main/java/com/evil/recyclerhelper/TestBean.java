package com.evil.recyclerhelper;

import com.evil.recycler.inface.IRecyclerData;

public class TestBean implements IRecyclerData{
    private int height;
    private String url;

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url=url;
    }

    public int getHeight(){
        return height;
    }

    public void setHeight(int height){
        this.height=height;
    }

    @Override
    public int recycleType(){
        return 0;
    }
}
