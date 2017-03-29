package com.example.avinash.nittcart;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsoluteLayout;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.ramotion.foldingcell.FoldingCell;
import com.readystatesoftware.viewbadger.BadgeView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.mauker.materialsearchview.MaterialSearchView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import it.sephiroth.android.library.tooltip.Tooltip;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    List<Item> movieList = new ArrayList<Item>();
    static boolean flag = false;
    com.getbase.floatingactionbutton.FloatingActionButton floatingActionButton;
    static RelativeLayout frameLayout;
    postadd p;
    static InputMethodManager imm;
    static int width;
    static int height;
    ArrayList<String> arr;
    private MaterialSearchView searchView;
    GridLayoutManager lLayout;
    static RelativeLayout coordinatorLayout;
    TabLayout tabHost;
    private Resources res;
    boolean oncreate = false;
    RecyclerView recyclerView;
    View cart,dashboard;
    static String layoutmode = "GRID_LAYOUT";
    static boolean isInternetConnected = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//--------------------connection manager--------------------------------
        registerReceiver(
                new ConnectivityChangeReceiver(),
                new IntentFilter(
                        ConnectivityManager.CONNECTIVITY_ACTION));

        recyclerView =(RecyclerView)findViewById(R.id.recycler_view);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.adjustTintAlpha(0.8f);
        arr = new ArrayList<>();
        res = this.getResources();
        oncreate = true;
        tabHost = (TabLayout) this.findViewById(R.id.tabHost);
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        AppBarLayout.LayoutParams params2= new AppBarLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 65, getResources().getDisplayMetrics());
        tabHost.setLayoutParams(params2);
        coordinatorLayout = (RelativeLayout)findViewById(R.id.snackbar_coordinator);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        tabHost.setSelectedTabIndicatorColor(Color.parseColor("#efefef"));
        tabHost.setTabTextColors(Color.parseColor("#5b839c"), Color.parseColor("#3baf79"));
        settab();

        updateBadgeCount(tabHost.getTabAt(2).getCustomView(), 0);
        updateBadgeCount(tabHost.getTabAt(3).getCustomView(),0);
        updateBadgeCount(tabHost.getTabAt(4).getCustomView(),0);

        tabHost.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TabReSelected(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                TabReSelected(tab.getPosition());
            }
        });

        Log.d("create", "inside");
        
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }

        });


            searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Do something when the suggestion list is clicked.
                    String suggestion = searchView.getSuggestionAtPosition(position);

                    searchView.setQuery(suggestion, false);
                }
            });


            imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            final WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitleTextColor(Color.GRAY);


        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_donate) {
                        // The tab with id R.id.tab_favorites was selected,
                        // change your content accordingly.
                    }
                }
            });

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();


            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setUpWindow();
                }
            }, 500);
            //setUpWindow();
   /*     findViewById(R.id.button).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent intent = new Intent(MainActivity.this, FoldingCellActivity.class);
                startActivity(intent);
                return false;
            }
        });


      frameLayout = (RelativeLayout) findViewById(R.id.fabLayout);
        //frameLayout.getBackground().setAlpha(100);
        final FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fab_menu);
        fabMenu.setRotation(-90);

        fabMenu.setOnFloatingActionsMenuUpdateListener(new FloatingActionsMenu.OnFloatingActionsMenuUpdateListener() {
            @Override
            public void onMenuExpanded() {
                floatingActionButton = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_event);
                frameLayout.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        fabMenu.collapse();
                        return true;
                    }
                });
                p = new postadd(windowManager, MainActivity.this, fabMenu);
                //floatingActionMenu.setFocusable(true);
            }


            @Override
            public void onMenuCollapsed() {
                //frameLayout.getBackground().setAlpha(0);
                p.dismiss();
                frameLayout.setOnTouchListener(null);
            }
        });

*/

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                //postadd p = new postadd(windowManager,MainActivity.this);

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

       */
        }

    public void checkNetwork(){
        ConnectivityManager cm =
                (ConnectivityManager)MainActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isInternetConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    public void updateBadgeCount(View view,int count){
        BadgeView badge = new BadgeView(MainActivity.this, view);
        badge.setText(""+count);
        badge.setBadgeBackgroundColor(Color.parseColor("#dabb1f"));
        //badge.setBackgroundColor(Color.parseColor("#4f9b62"));
        badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badge.setBadgeMargin(0);
        badge.setTextSize(12);
        badge.show();
    }

    public void setUpWindow() {

        lLayout = new GridLayoutManager(MainActivity.this, 2);
        FoldingCellActivity  f= new FoldingCellActivity(recyclerView
        ,new LinearLayoutManager(getApplicationContext()),MainActivity.this,lLayout);
    }


    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        //moveTaskToBack(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (searchView.isOpen()) searchView.closeSearch();

        else if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("start", "inside");
        oncreate = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        //searchView.clearSuggestions();
    }

    public LinearLayout customtabView(int drawableid,String labeltext){
        ImageView tabicon = new ImageView(this);
        TextView label = new TextView(this);
        label.setText(labeltext);
        label.setSingleLine();
        label.setTypeface(null, Typeface.BOLD);
        //tabicon.setPadding(0, 0, 0, 0);
        label.setTextSize(10);
        label.setTextColor(Color.parseColor("#5b839c"));
        label.setGravity(Gravity.CENTER|Gravity.CENTER_VERTICAL);
        //label.setPadding(0, 0, 0, 0);
        tabicon.setImageResource(drawableid);
        LinearLayout l = new LinearLayout(this);
        l.setOrientation(LinearLayout.VERTICAL);
        l.addView(tabicon, 0);
        l.addView(label, 1);
        //l.setPadding(0,10,0,10);
        LinearLayout.LayoutParams params= new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
        , ViewGroup.LayoutParams.WRAP_CONTENT);
         l.setLayoutParams(params);
        tabHost.setMinimumHeight(l.getHeight()+20);
        return l;
    }

    public void settab(){
        {
            Log.d("settab", "inside");
            tabHost.addTab(
                    tabHost.newTab()
                            .setCustomView(customtabView(R.drawable.ic_home,"Home"))
            );
            tabHost.addTab(
                    tabHost.newTab()
                            .setCustomView(customtabView(R.drawable.ic_add_item, "Postadd"))
            );
            tabHost.addTab(
                    tabHost.newTab()
                            .setCustomView(customtabView(R.drawable.ic_cart, "Cart"))
            );
            tabHost.addTab(
                    tabHost.newTab()
                            .setCustomView(customtabView(R.drawable.ic_notification, "Notificati.."))
            );
            tabHost.addTab(
                    tabHost.newTab()
                            .setCustomView(customtabView(R.drawable.ic_user, "Dashboard"))
            );
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.activityResumed();
        //String[] arr = getResources().getStringArray(R.array.suggestions);
        //searchView.addSuggestions(arr);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.item_search) {
            searchView.setVisibility(View.VISIBLE);
            searchView.openSearch();
            return true;
        }

        if (id == R.id.action_list_to_grid) {

            if (!((Animatable) item.getIcon()).isRunning()) {
                if (lLayout.getSpanCount() == 1) {
                    layoutmode = "GRID_LAYOUT";
                    item.setIcon(AnimatedVectorDrawableCompat.create(MainActivity.this, R.drawable.avd_list_to_grid));
                    lLayout.setSpanCount(2);
                } else {
                    layoutmode = "LIST_LAYOUT";
                    item.setIcon(AnimatedVectorDrawableCompat.create(MainActivity.this, R.drawable.avd_grid_to_list));
                    lLayout.setSpanCount(1);
                }
                ((Animatable) item.getIcon()).start();
                FoldingCellActivity.mAdapter.notifyItemRangeChanged(0, FoldingCellActivity.mAdapter.getItemCount());

            }
                return true;
            }


            return super.onOptionsItemSelected(item);
        }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void TabReSelected(int position) {
        switch (position){
            case 0:break;
            case 1://pickimage();
                        Intent postadd = new Intent(getApplicationContext(), postadd.class);
                        startActivity(postadd);
                        overridePendingTransition(R.anim.up_from_bottom, R.anim.activity_slide_up);
                break;
            case 2: Intent intent = new Intent(getApplicationContext(), Cart.class);
                startActivity(intent);
                //finish();
                overridePendingTransition(R.anim.up_from_bottom, R.anim.activity_slide_up );
                break;
            case 3:break;
            case 4:break;
        }
    }

    @Override
    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("state", oncreate);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedIntancestate){
        super.onRestoreInstanceState(savedIntancestate);
        oncreate = savedIntancestate.getBoolean("state");
        Log.d("save","state");
    }

}
