package hackergames.resilientplc.com.hackergamesapp.onboarding;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hackergames.resilientplc.com.hackergamesapp.HackersGameApplication;
import hackergames.resilientplc.com.hackergamesapp.R;
import hackergames.resilientplc.com.hackergamesapp.data.model.User;
import hackergames.resilientplc.com.hackergamesapp.signup.SignupActivity;

public class OnboardingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.military_button)
    public void onMilitaryButtonClick() {
        HackersGameApplication.setUser(new User("peter", "peter", "peter", User.MILITARY));
        initDependencies();
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

    @OnClick(R.id.professional_button)
    public void onProfessionalButtonClick() {
        HackersGameApplication.setUser(new User("Harry", "Harry", "harry", User.PROFESSIONAL));
        initDependencies();
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

    private void initDependencies() {
        HackersGameApplication.initDependencies();
    }
}
