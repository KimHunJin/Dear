package kr.team12.hackathon.dear;

import android.content.Context;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.tsengvn.typekit.Typekit;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;

import kr.team12.hackathon.dear.activity.AboutUsActivity;
import kr.team12.hackathon.dear.activity.LicenseActivity;
import kr.team12.hackathon.dear.adapter.MainListAdapter;
import kr.team12.hackathon.dear.dao.TextService;
import kr.team12.hackathon.dear.dto.DataBean;
import kr.team12.hackathon.dear.dto.JsonBean;
import kr.team12.hackathon.dear.item.MainListItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private static String URL = "http://ordernow.co.kr:3000/";

    // 뒤로가기 버튼 사용
    private long mBackPress;
    private static final int mInterval = 2000;

    private LocationManager locationMangaer = null;
    private LocationListener locationListener = null;

    private static final String TAG = "Debug";


    // 레이아웃 선언
    DrawerLayout mDrawer;
    EditText edtContents;
    ImageView imgSearch, imgShare, imgShare2;
    ImageView btnKeyWordSearch;
    LinearLayout lineShow;
    RelativeLayout relShow;
    ListView lstContext;
    ImageView iBtnHB;
    AutoCompleteTextView autoTv;
    NavigationView mNavigation;

    // 어댑터 추가
    MainListAdapter listAdapter;

    // 아이템 추가
    ArrayList<MainListItem> listItem = new ArrayList<>();

    String strType = "hello";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "NotoSansCJKkr-Thin.otf"));


        layoutSetting();

        listAdapter = new MainListAdapter(this, R.layout.item_list, listItem);

//        init();
//        parsing2();

    }

    // 레이아웃 세팅
    private void layoutSetting() {
        edtContents = (EditText) findViewById(R.id.edtMainContents);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        imgShare = (ImageView) findViewById(R.id.imgShare1);
        lineShow = (LinearLayout) findViewById(R.id.lineShowSearch);
        relShow = (RelativeLayout) findViewById(R.id.relShowMain);
        btnKeyWordSearch = (ImageView) findViewById(R.id.btnKeyWord);
        lstContext = (ListView) findViewById(R.id.lstContext);
        iBtnHB = (ImageView) findViewById(R.id.btnHB);
        autoTv = (AutoCompleteTextView) findViewById(R.id.autoKeyWord);
        imgShare2 = (ImageView) findViewById(R.id.imgShare2);
        mDrawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigation = (NavigationView) findViewById(R.id.mainNav);

        imgSearch.setOnClickListener(this);
        btnKeyWordSearch.setOnClickListener(this);
        lstContext.setOnItemClickListener(this);
        imgShare.setOnClickListener(this);
        iBtnHB.setOnClickListener(this);
        imgShare2.setOnClickListener(this);
        autoTv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.availableHello)));
        initDrawerContent(mNavigation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgSearch: {
                relShow.setVisibility(View.GONE);
                lineShow.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.btnKeyWord: {
                // 리스트뷰를 보여준다.

                listItem.clear();
                if (autoTv.getText().toString() != "") {
                    parsing2(strType, autoTv.getText().toString());

                    lstContext.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();

//                    lstContext.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "키워드를 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            }
            case R.id.imgShare1:
            case R.id.imgShare2: {
                Intent it = new Intent(Intent.ACTION_SEND);
                it.setType("text/plain");
                it.putExtra(Intent.EXTRA_TEXT, edtContents.getText().toString());
                startActivity(it);
                break;
            }
            case R.id.btnHB: {
                if (strType.equals("bye")) {
                    iBtnHB.setBackgroundResource(R.drawable.hello);
                    strType = "hello";
                    autoTv.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.availableHello)));
                } else if (strType.equals("hello")) {
                    iBtnHB.setBackgroundResource(R.drawable.bye);
                    strType = "bye";
                    autoTv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,getResources().getStringArray(R.array.availableBye)));
                }
            }
        }
    }


    // 뒤로가기 버튼 클릭 이벤트
    public void onBackPressed() {
        if (lineShow.getVisibility() == View.VISIBLE) {

            relShow.setVisibility(View.VISIBLE);
            lineShow.setVisibility(View.GONE);

        } else {
            if (mBackPress + mInterval > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                Toast.makeText(MainActivity.this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
            }
            mBackPress = System.currentTimeMillis();
        }
    }


    // 아이템 클릭 리스너
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        edtContents.setText(edtContents.getText().toString() + listItem.get(position).getContext());
        lstContext.setVisibility(View.GONE);
    }

    // 리스트 테스트용
    void init() {
        for (int i = 0; i < 10; i++) {
            listItem.add(new MainListItem(i, "안녕" + i));
        }
    }

    private void parsing2(String type, String word) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        TextService textService = retrofit.create(TextService.class);
        Call<JsonBean> call = textService.getData(new DataBean(type, word));
        call.enqueue(new Callback<JsonBean>() {
            @Override
            public void onResponse(Call<JsonBean> call, Response<JsonBean> response) {
                if (response.isSuccessful()) {
                    JsonBean dataResponose = response.body();
                    Log.e("size", dataResponose.getData().size() + "");
                    for (int i = 0; i < dataResponose.getData().size(); i++) {
                        listItem.add(new MainListItem(i, dataResponose.getData().get(i)));
//                        Log.e("size " + i, dataResponose.getData().get(i));
                    }
                    listAdapter.notifyDataSetChanged();
                    lstContext.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<JsonBean> call, Throwable t) {
                Log.e("error", t.getMessage());
            }
        });
    }

    private void initDrawerContent(NavigationView nView) {
        nView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                SelectDrawerItem(menuItem);
                return true;
            }
        });
    }

    private void SelectDrawerItem(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_create_by: {
                startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
                break;
            }
            case R.id.menu_license : {
                startActivity(new Intent(getApplicationContext(), LicenseActivity.class));
                break;
            }
        }
        mDrawer.closeDrawers();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

}
