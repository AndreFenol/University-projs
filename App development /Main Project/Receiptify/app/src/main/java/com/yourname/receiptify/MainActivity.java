package com.yourname.receiptify;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yourname.receiptify.activities.CameraActivity;
import com.yourname.receiptify.fragments.ExpensesFragment;
import com.yourname.receiptify.fragments.CategoriesFragment;
import com.yourname.receiptify.fragments.HomeFragment;
import com.yourname.receiptify.fragments.SettingsFragment;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener,
        EasyPermissions.PermissionCallbacks {

    private static final String[] CAMERA_PERMS = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int CAMERA_REQUEST_CODE = 123;

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton fabCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupBottomNavigation();
        setupFloatingActionButton();

        // Load default fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }

    private void initViews() {
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        fabCamera = findViewById(R.id.fab_scan_receipt);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
    }

    private void setupFloatingActionButton() {
        fabCamera.setOnClickListener(v -> {
            if (EasyPermissions.hasPermissions(this, CAMERA_PERMS)) {
                startCameraActivity();
            } else {
                EasyPermissions.requestPermissions(this,
                        "Camera permission is required to scan receipts",
                        CAMERA_REQUEST_CODE, CAMERA_PERMS);
            }
        });
    }

    private void startCameraActivity() {
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            fragment = new HomeFragment();
        } else if (itemId == R.id.nav_expenses) {
            fragment = new ExpensesFragment();
        } else if (itemId == R.id.nav_categories) {
            fragment = new CategoriesFragment();
        } else if (itemId == R.id.nav_settings) {
            fragment = new SettingsFragment();
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);

            // Only add to back stack if it's not the home fragment
            if (!(fragment instanceof HomeFragment)) {
                transaction.addToBackStack(null);
            }

            transaction.commit();
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            startCameraActivity();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        // Handle permission denied - could show explanation dialog
    }
}