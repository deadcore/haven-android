package hackergames.resilientplc.com.hackergamesapp.filter;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hackergames.resilientplc.com.hackergamesapp.R;

/**
 * Created by eduar on 19/01/2018.
 */

public class SelectableRowView extends RelativeLayout {

    @BindView(R.id.row_text)
    TextView rowLabelText;

    @BindView(R.id.toggle_button)
    RadioButton radioButton;


    public SelectableRowView(Context context) {
        this(context, null);
    }

    public SelectableRowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectableRowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SelectableRowView, 0, 0);
        try {
            rowLabelText.setText(typedArray.getString(R.styleable.SelectableRowView_row_text));
        } finally {
            typedArray.recycle();
        }

    }

    private void init(Context context) {
        View view = inflate(context, R.layout.selectable_row_layout, this);
        ButterKnife.bind(this, view);
    }

    public void removeSelection() {
        radioButton.setChecked(false);
    }

    public void select() {
        radioButton.setChecked(true);

    }


}
