package com.mar;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mar.appmonitor.AppMonitorUtil;
import com.mar.fragments.AnalysisFragment;
import com.mar.fragments.MonitorFragment;
import com.mar.fragments.RestrictionFragment;
import com.mar.services.AppLockService;
import com.mar.utils.Preference;

public class MainSwipeableActivity extends AppCompatActivity {
    public static final int usage_access_request = 8898;
    public static final int draw_over_other_app_request = 9989;
    private static final String TAG = "MainSwipeableActivity";
    private Intent serviceintent;
    private CoordinatorLayout coordinatorLayout;
    private AlertDialog alertDialog;
    private Preference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_swipable);
        Toolbar toolbar = findViewById(R.id.toolbar);
        coordinatorLayout = findViewById(R.id.main_content);
        setSupportActionBar(toolbar);
        serviceintent = new Intent(this, AppLockService.class);
        preference = new Preference(this);
        if (AppMonitorUtil.hasUsageStatsPermission(getApplicationContext()) &&
                AppMonitorUtil.hasDrawOverOtherAppPermission(getApplicationContext())) {
            Log.i(TAG, "run: Usage Permission Granted Draw Over Other App is Allowed");
        } else {
            if (!AppMonitorUtil.hasUsageStatsPermission(getApplicationContext())) {
                getUsagePermission();
            } else if (!AppMonitorUtil.hasDrawOverOtherAppPermission(getApplicationContext())) {
                getAccessUsageOverlay();
            }
        }

        TabLayout tabLayout = findViewById(R.id.tab_groups);
        ViewPager mViewPager = findViewById(R.id.viewpager);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_swipable, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings Page", Toast.LENGTH_SHORT).show();
            case R.id.about_settings:
                Toast.makeText(this, "About App Info", Toast.LENGTH_SHORT).show();
            default:
                Toast.makeText(this, "No Menu Item Found", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case usage_access_request:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(MainSwipeableActivity.this, MainSwipeableActivity.class));
                    finish();
                    break;
                } else {
                    Log.i(TAG, "onActivityResult: Usage Access Permission Not Allowed");
                    break;
                }
            case draw_over_other_app_request:
                if (resultCode == RESULT_OK) {
                    startActivity(new Intent(MainSwipeableActivity.this, MainSwipeableActivity.class));
                    finish();
                    break;
                } else {
                    Log.i(TAG, "onActivityResult: Usage Access Permission Not Allowed");
                    break;
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void lockapps(View view) {
        if (!preference.getIsAppLockPinSet()) {
            showPinDialog(1);
        } else {
            startService(serviceintent);
            Snackbar.make(coordinatorLayout, "Apps are Already Locked With Pin", Snackbar.LENGTH_SHORT).show();
        }
        Log.i(TAG, "lockapps: service started directly by using stored pin");
    }

    public void unlockapps(View view) {
        showPinDialog(2);
        Log.i(TAG, "unlockapps: service stopped directly by skipping pin");
    }

    public void showPinDialog(int action) {
        View inflater = LayoutInflater.from(this).inflate(R.layout.pin_dialog, null);
        TextInputLayout pass_input_layout = inflater.findViewById(R.id.input_layout);
        TextInputEditText pass_edtx = inflater.findViewById(R.id.input);
        if (action == 1) {
            //lock dialog
            alertDialog = new AlertDialog.Builder(this)
                    .setView(inflater)
                    .setTitle("Set Lock Pin")
                    .setCancelable(false)
                    .setPositiveButton("Done", null)
                    .setNegativeButton("cancel", null)
                    .create();

            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button doneButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    doneButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //add pin to shared preference on pin entered
                            String pinText = pass_edtx.getText().toString();
                            if (pinText.length() == 4) {
                                int pin = Integer.parseInt(pinText);
                                preference.setAppLockPin(pin);
                                preference.setIsAppLockPinSet(true);
                                startService(serviceintent);
                                alertDialog.setCancelable(true);
                                dialog.dismiss();
                                Snackbar.make(coordinatorLayout, "Restricted Apps are Locked", Snackbar.LENGTH_SHORT).show();
                                Log.i(TAG, "onClickDone: pin is Set For First Time in App = " + pinText);
                            } else {
                                Log.i(TAG, "onClickDone: pin is Not equal to 4 digit = " + pinText);
                            }
                        }
                    });
                }
            });
            alertDialog.show();
        }
        if (action == 2) {
            //unlock dialog
            alertDialog = new AlertDialog.Builder(this)
                    .setView(inflater)
                    .setTitle("Enter Lock Pin")
                    .setCancelable(false)
                    .setPositiveButton("Ok", null)
                    .setNegativeButton("cancel", null)
                    .create();

            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    Button okButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String pinText = pass_edtx.getText().toString();
                            if (pinText.length() == 4) {
                                int pin = Integer.parseInt(pinText);
                                if (preference.getIsAppLockPinSet()) {
                                    if (pin == preference.getAppLockPin()) {
                                        stopService(serviceintent);
                                        alertDialog.setCancelable(true);
                                        dialog.dismiss();
                                        Snackbar.make(coordinatorLayout, "Restricted Apps are Unlocked", Snackbar.LENGTH_SHORT).show();
                                        Log.i(TAG, "onClickOk : lock service stopped by checking the input as = " + pinText);
                                    } else {
                                        pass_input_layout.setHint("Please Enter Correct Pin !");
                                        pass_edtx.setText("");
                                        pass_edtx.setHint("Try Again");
                                        Log.i(TAG, "onClickOk : Pin is Incorrect = " + pinText);
                                    }//if entered pin is same as stored check
                                } else {
                                    pass_input_layout.setHint("Pin Is Not Set Yet !");
                                    pass_edtx.setHint("Please Set Pin");
                                    Log.i(TAG, "onClickOk: Pin is Not Stored in Preference = " + pinText);
                                }//if pin is stored before check
                            } else {
                                pass_input_layout.setHint("Entered Pin is Not 4 Digit");
                                pass_edtx.setHint("Enter Correct Pin");
                                Log.i(TAG, "onClickOk: Pin is Not Same Digit as Stored = " + pinText);
                            }
                        }
                    });
                }
            });
            alertDialog.show();
        }
    }

    public void getUsagePermission() {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
        startActivityForResult(intent, usage_access_request);
    }

    public void getAccessUsageOverlay() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            Intent i = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(i, draw_over_other_app_request);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new MonitorFragment();
                case 1:
                    return new AnalysisFragment();
                case 2:
                    return new RestrictionFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
