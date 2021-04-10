package co.curesense.tb.customfonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class Button_Roboto_Medium extends androidx.appcompat.widget.AppCompatTextView {

    public Button_Roboto_Medium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public Button_Roboto_Medium(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Button_Roboto_Medium(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Questrial-Regular.ttf");
            setTypeface(tf);
        }
    }
}
