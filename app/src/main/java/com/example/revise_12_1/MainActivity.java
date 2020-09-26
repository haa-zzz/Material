package com.example.revise_12_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/*
最佳的UI体验--Material Design 实战
    1.什么是Material Design
        Material Design 是谷歌的一套全新的界面设计语言，包含了视觉，运动，互动效果等特性。
        Design Support库，将Material Design中最具代表性的一些控件和效果进行了封装
    2.Toolbar       //自定义标题栏
        1.修改styles文件，改为不带ActionBar的主题
        2.添加Toolbar控件
        3.设置支持动作栏
       添加菜单：和普通的添加菜单一样
                app:showAsAction 指示按钮的显示位置
                always永远显示在Toolbar中,ifRoom屏幕空间足够的情况下显示在Toolbar中，never永远显示在菜单中
         注：在菜单项指定图标时，Toolbar的action按钮只显示图标，菜单中的action只显示文字
     3.滑动菜单 DrawerLayout
        友好提示：在Toolbar最左边加入导航按钮
      3.2 NavigationView      Design Support库中的控件，在这用于优化滑动菜单页面
            CircleImageView 快速圆形的ImageView，非常适合个人资料图像。
     4.悬浮按钮和可交互提示
        4.1FloatingActionButton
        点击事件
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
            }
        });
        4.2可交互提示：Snackbar 允许在提示中加入一个可交互按钮为用户点击按钮时可以执行一些额外的操作

        4.3 CoordinatorLayout 加强版的FrameLayout,可以监听所有子控件的各种事件，自动帮我们做出最为合理的响应
     5.卡片式布局
        5.1 CardView   由appcompat-v7库提供，实际上也是一个FrameLayout,只是额外提供圆角和阴影效果
                Glide库，是一个超级强大的图片加载库，不仅可以用来加载本地图片，还可以加载网络图片，GIF图片，甚至是本地视频

        5.2 AppBarLayout实际上是一个垂直方向的LinearLayout,他的内部做了很多滚动事件的封装
            AppBarLayout 只有作为 CoordinatorLayout 的直接子 View 时才能正常工作，为了让 AppBarLayout 能够知道何时滚动其子 View，
            我们还应该在 CoordinatorLayout 布局中提供一个可滚动 View，我们称之为 Scrolling View。
            Scrolling View 和 AppBarLayout 之间的关联，通过将 Scrolling View 的 Behavior 设为 AppBarLayout.ScrollingViewBehavior 来建立。

            作者：王小贱_ww
            链接：https://www.jianshu.com/p/f070636d1df3
            来源：简书
            著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     6.下拉刷新
        SwipeRefreshLayout 由support-v4库提供
     7.可折叠式标题栏
        CollapsingToolbarLayout作用于Toolbar基础之上的布局，丰富Toolbar的效果，只能作为AppBarLayout的直接子类布局使用





 */
public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Mydapt adapt;
    private List<Fruit> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);      //让导航按钮显示出来
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);   //设置导航按钮图标，默认是返回的图标
        }
        navigationView.setCheckedItem(R.id.item_cgl1);      //设置菜单项的默认选中
        //对菜单项设置监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();        //将滑动菜单关闭
                return true;
            }
        });
        //FloatingActionButton点击事件
        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Data delete",Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"click",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();

            }
        });
        //RecyclerView
        addlist();
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        GridLayoutManager manager = new GridLayoutManager(MainActivity.this,2);
        adapt = new Mydapt(list);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapt);
        //下拉刷新：
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.srf);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);       //设置颜色
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addlist();
                        adapt.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);        //刷新事件结束，并隐藏刷新进度条
                    }
                });
            }
        }).start();
    }

    private void addlist() {
        list.clear();
        Fruit[] fruits = {new Fruit("grape",R.drawable.grape),new Fruit("orange",R.drawable.orange),
                new Fruit("pear",R.drawable.pear),new Fruit("watemelon",R.drawable.watemelon)};
        for(int i  =0; i < 50 ;i++){
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            list.add(fruits[index]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbarmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
                break;
            case R.id.item2:
                break;
            case R.id.item3:
                break;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }
}