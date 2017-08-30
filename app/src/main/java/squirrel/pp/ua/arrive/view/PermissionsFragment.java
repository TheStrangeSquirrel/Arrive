package squirrel.pp.ua.arrive.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import javax.inject.Inject;

import squirrel.pp.ua.arrive.App;
import squirrel.pp.ua.arrive.R;
import squirrel.pp.ua.arrive.presenter.PermissionsPresenter;


public class PermissionsFragment extends Fragment implements PermissionsView {

    @Inject
    PermissionsPresenter presenter;

    private Button bGetPermissions;
    private Button bOpenApplicationSettings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getComponentManager().getPermissionsComponent(this).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_permissions, container);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        findViews(view);
        setListeners();
    }

    private void findViews(View view) {
        bGetPermissions = (Button) view.findViewById(R.id.bGetPermissions);
        bOpenApplicationSettings = (Button) view.findViewById(R.id.bOpenApplicationSettings);
    }

    private void setListeners() {
        bGetPermissions.setOnClickListener(v -> presenter.onClickGetPermissions());
        bOpenApplicationSettings.setOnClickListener(v -> presenter.onClickOpenApplicationSettings());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showPermissionsNotReceivedMassage() {
        Toast.makeText(getContext(), getString(R.string.permissionsNotReceivedMassage),
                Toast.LENGTH_LONG).show();
    }
}
