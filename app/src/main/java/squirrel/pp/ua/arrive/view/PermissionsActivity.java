package squirrel.pp.ua.arrive.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import squirrel.pp.ua.arrive.R;

public class PermissionsActivity extends AppCompatActivity {
    public static final String ACTION_PERMISSION = "squirrel.pp.ua.arrive.view.Permission";

    PermissionsFragment permissionsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);
        permissionsFragment = (PermissionsFragment) getSupportFragmentManager()
                .findFragmentById(R.id.permissionsFragment);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionsFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        permissionsFragment.onActivityResult(requestCode, resultCode, data);
    }
}
