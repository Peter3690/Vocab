package tulv.vocab.Transformer;

/**
 * Created by tulv on 10/12/2016.
 */

import android.view.View;

/**
 * Created by tulv on 9/21/2016.
 */
public class CubeOutTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        view.setPivotX(position < 0f ? view.getWidth() : 0f);
        view.setPivotY(view.getHeight() * 0.5f);
        view.setRotationY(90f * position);

    }

    @Override
    public boolean isPagingEnabled() {
        return true;
    }

}
