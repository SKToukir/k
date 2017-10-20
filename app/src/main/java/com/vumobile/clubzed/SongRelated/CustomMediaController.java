package com.vumobile.clubzed.SongRelated;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MediaController;

/**
 * Created by it-6 on 2/25/2016.
 */
public class CustomMediaController  extends MediaController {


    public CustomMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
    }

    public CustomMediaController(Context context) {
        super(context);
    }

    @Override
    public void show(int timeout) {
        super.show(0);
    }

}