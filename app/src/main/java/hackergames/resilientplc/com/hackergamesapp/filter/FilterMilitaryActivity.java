package hackergames.resilientplc.com.hackergamesapp.filter;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hackergames.resilientplc.com.hackergamesapp.HackersGameApplication;
import hackergames.resilientplc.com.hackergamesapp.R;
import hackergames.resilientplc.com.hackergamesapp.contacts.ContactsActivity;
import toothpick.Toothpick;

public class FilterMilitaryActivity extends AppCompatActivity {

    @BindView(R.id.select_veteran)
    SelectableRowView selectableRowViewVeteran;
    @BindView(R.id.select_professional)
    SelectableRowView selectableRowViewProfessional;

    @BindView(R.id.select_2_nothing)
    SelectableRowView selectableRowViewNothing;
    @BindView(R.id.select_2_mental)
    SelectableRowView selectableRowViewMental;
    @BindView(R.id.select_2_financial)
    SelectableRowView selectableRowViewFinancial;
    @BindView(R.id.select_2_housing)
    SelectableRowView selectableRowViewHousing;
    @BindView(R.id.select_2_drug_abuse)
    SelectableRowView selectableRowViewDrugAbuse;
    @BindView(R.id.select_2_job)
    SelectableRowView selectableRowViewJob;

    @BindView(R.id.fab)
    FloatingActionButton floatingActionButton;

    @BindView(R.id.container2)
    LinearLayout container2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_military);
        ButterKnife.bind(this);
        Toothpick.inject(this, Toothpick.openScopes(HackersGameApplication.SCOPE));

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Get support");

        Drawable backArrow = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
        backArrow = DrawableCompat.wrap(backArrow);
        DrawableCompat.setTint(backArrow, ContextCompat.getColor(this, R.color.white));
        getSupportActionBar().setHomeAsUpIndicator(backArrow);


    }

    @OnClick({ R.id.select_veteran, R.id.select_professional})
    public void pickSelection1(SelectableRowView selectedView) {
        selectableRowViewProfessional.removeSelection();
        selectableRowViewVeteran.removeSelection();

        selectedView.select();
        container2.setVisibility(View.VISIBLE);
    }

    @OnClick({ R.id.select_2_nothing, R.id.select_2_mental, R.id.select_2_job, R.id.select_2_drug_abuse, R.id.select_2_financial, R.id.select_2_housing})
    public void pickSelection2(SelectableRowView selectedView) {
        selectableRowViewNothing.removeSelection();
        selectableRowViewMental.removeSelection();
        selectableRowViewJob.removeSelection();
        selectableRowViewDrugAbuse.removeSelection();
        selectableRowViewFinancial.removeSelection();
        selectableRowViewHousing.removeSelection();

        selectedView.select();
        floatingActionButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.fab)
    public void fabClick(){
        startActivity(new Intent(this, ContactsActivity.class));
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

}
