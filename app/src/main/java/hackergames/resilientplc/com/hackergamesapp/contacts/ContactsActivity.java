package hackergames.resilientplc.com.hackergamesapp.contacts;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hackergames.resilientplc.com.hackergamesapp.LoadingActivity;
import hackergames.resilientplc.com.hackergamesapp.R;
import hackergames.resilientplc.com.hackergamesapp.filter.SelectableRowView;

public class ContactsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Get support");

        Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        backArrow = DrawableCompat.wrap(backArrow);
        DrawableCompat.setTint(backArrow, ContextCompat.getColor(this, R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(backArrow);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({ R.id.message, R.id.message2, R.id.message3})
    public void pickSelection1(ImageView imageView) {
        startActivity(new Intent(this, LoadingActivity.class));
    }

}
