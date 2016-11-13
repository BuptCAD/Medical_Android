package cn.wydewy.medicalapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements OnClickListener {
    //private LinearLayout tabHospital,tabMy;
    private TextView btnhospital, btnmy;
    private Fragment_hospital fHospital;
    private Fragment_my fMy;
    private FragmentManager fManager;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initView();
        initEvent();
        fManager = getSupportFragmentManager();
    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            page = bundle.getInt("page");
        }
        setSelect(page);
    }

    private void initEvent() {
        btnhospital.setOnClickListener(this);
        btnmy.setOnClickListener(this);
    }

    private void initView() {
        btnhospital = (TextView) findViewById(R.id.btn1);
        btnmy = (TextView) findViewById(R.id.btn2);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                setSelect(0);
                break;
            case R.id.btn2:
                if (MedicalApplication.getInstance().isLog()) {
                    setSelect(1);
                } else {
                    startActivity(new Intent(MainActivity.this, Login_Activity.class));
                }
                break;
        }
    }

    private void setSelect(int i) {
        FragmentTransaction transaction = fManager.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                if (fHospital == null) {
                    fHospital = new Fragment_hospital();
                    transaction.add(R.id.id_content, fHospital);
                } else {
                    transaction.show(fHospital);
                }
                break;
            case 1:
                if (fMy == null) {
                    fMy = new Fragment_my();
                    transaction.add(R.id.id_content, fMy);
                } else {
                    transaction.show(fMy);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (fHospital != null) {
            transaction.hide(fHospital);
        }
        if (fMy != null) {
            transaction.hide(fMy);
        }
    }

    public void ChangeOnClick(View view) {
        switch (view.getId()) {
            case R.id.item1:
                Intent it = new Intent(this, Introduce_Activity.class);
                startActivity(it);
                break;

            case R.id.item2:
                Intent it2 = new Intent(this, Guide_Activity.class);
                startActivity(it2);
                break;
            case R.id.item4:
                Intent it4 = new Intent(this, Schedule_Activity.class);
                startActivity(it4);
                break;
            case R.id.item5:
                Intent it5 = new Intent(this, Order_Activity.class);
                startActivity(it5);
                break;
        }
    }


    private long exitTime = 0;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN ) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {

                Toast toast = Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.touch_again_to_exit),
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}

