package hackergames.resilientplc.com.hackergamesapp.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hackergames.resilientplc.com.hackergamesapp.R;
import hackergames.resilientplc.com.hackergamesapp.filter.FilterMilitaryActivity;
import hackergames.resilientplc.com.hackergamesapp.filter.FilterProfessionalActivity;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.get_support_button)
    public void onGetSupportClick() {
        startActivity(new Intent(this, FilterMilitaryActivity.class));
    }
    @OnClick(R.id.give_support_button)
    public void onGiveSupportClick() {
        startActivity(new Intent(this, FilterProfessionalActivity.class));
    }
}
