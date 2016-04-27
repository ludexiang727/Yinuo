package com.yinuo.helper;

import android.view.View;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by ludexiang on 2016/4/27.
 */
public class UiHelper {
    /**
     * @param view
     * @param from
     * @param to
     * @param duration
     * @param isStart
     * @return
     */
    public static Animator scaleZoomOut(View view, float from, float to, long duration, boolean isStart) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", from, to);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", from, to);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", from, to);
        set.setDuration(duration);
        set.playTogether(scaleX, scaleY, alpha);
        if (isStart) {
            set.start();
        }
        return set;
    }

    public static Animator translateY(View view, float from, float to, long duration, boolean isStart) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator translationY = ObjectAnimator.ofFloat(view, "translationY", from, to);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", from, to);
        set.setDuration(duration);
        set.playTogether(translationY, alpha);
        if (isStart) {
            set.start();
        }
        return set;
    }

    public static Animator alpha(View view, float from, float to, long duration, boolean isStart) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", from, to);
        alpha.setDuration(duration);
        if (isStart) {
            alpha.start();
        }
        return alpha;
    }
}
