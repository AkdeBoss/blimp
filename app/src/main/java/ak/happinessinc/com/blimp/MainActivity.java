package ak.happinessinc.com.blimp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ak.happinessinc.com.blimp.adapters.TabAdapter;
import ak.happinessinc.com.blimp.imgurmodel.ImageResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private String[] tabs = { "Click", "Gallery" };
    private ViewPager viewPager;
    private TabAdapter tabsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabsAdapter= new TabAdapter(getSupportFragmentManager(),tabs.length);
        viewPager.setAdapter(tabsAdapter);
        PagerTabStrip pagerTabStrip=(PagerTabStrip) findViewById(R.id.pagerTabStrip_MainActivity);
        pagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_PX,40);

    }



}
