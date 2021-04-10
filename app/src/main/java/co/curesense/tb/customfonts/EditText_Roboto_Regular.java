package co.curesense.tb.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class EditText_Roboto_Regular extends androidx.appcompat.widget.AppCompatTextView {

    public EditText_Roboto_Regular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public EditText_Roboto_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditText_Roboto_Regular(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/proxima/proxima_alt_regular.ttf");
            setTypeface(tf);
        }
    }

}