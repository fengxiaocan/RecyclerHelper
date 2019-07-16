# 说明:
* 一个简化RecyclerView初始化的工具类,一行代码搞定LayoutManager、分割线、adapter等初始化工作，同时有三个简化版Adapter实现类，可以极大节省代码
*使用方法:

        RecyclerHelper.with(mRecyclerView)
             .linearManager()
             .matchWidth()
             .addDividerDecoration()
             .animation()
             .adapter(mTestAdapter)
             .init();            
     
        ComRecyclerViewAdapter:一个可以添加头部跟尾部View的 recyclerview adapter，数据需实现IRecyclerData接口
        MultipleRecyclerViewAdapter:一个可以实现多种类型的View的 recyclerview adapter，数据需实现IRecyclerData接口
        SwipeRecyclerViewAdapter:一个可以支持侧滑删除（目前只支持右侧滑动）的 recyclerview adapter，数据需实现IRecyclerData接口
     
#2.引入说明:
        Step 1. Add the JitPack repository to your build file
        
        Add it in your root build.gradle at the end of repositories:
        
        	allprojects {
        		repositories {
        			...
        			maven { url 'https://jitpack.io' }
        		}
        	}
        	
        Step 2. Add the dependency
        
        	dependencies {
        	        implementation 'com.github.fengxiaocan:RecyclerHelper:latest.release'//直接引用最新的
        	}
