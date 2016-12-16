package tulv.vocab.layout;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import tulv.vocab.adapter.VocabAdapter;

/**
 * Created by tulv on 10/19/2016.
 */
public class VocabLayout extends LinearLayout {
    private float scale = VocabAdapter.BIG_SCALE;

    public VocabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VocabLayout(Context context) {
        super(context);
    }

    public void setScaleBoth(float scale) {
        this.scale = scale;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = this.getWidth();
        int h = this.getHeight();
        canvas.scale(this.scale, this.scale, w / 2, h / 2);

        super.onDraw(canvas);
    }
}

