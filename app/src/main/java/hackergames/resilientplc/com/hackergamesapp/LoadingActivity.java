package hackergames.resilientplc.com.hackergamesapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import hackergames.resilientplc.com.hackergamesapp.chat.ChatActivity;

public class LoadingActivity extends Activity {

    @BindView(R.id.avi)
    AVLoadingIndicatorView loadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            LoadingActivity.this.startActivity(
                    new Intent(LoadingActivity.this, ChatActivity.class));
            finish();
        }, TimeUnit.SECONDS.toMillis(3));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadingIndicatorView.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        loadingIndicatorView.hide();
    }

}
