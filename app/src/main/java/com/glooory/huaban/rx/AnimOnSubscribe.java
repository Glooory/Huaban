package com.glooory.huaban.rx;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Subscriber;

import static com.jakewharton.rxbinding.internal.Preconditions.checkUiThread;

/**
 * Created by Glooory on 2016/9/2 0002.
 */
public class AnimOnSubscribe implements Observable.OnSubscribe<Void> {

    final Animator animator;

    public AnimOnSubscribe(Animator animator) {
        this.animator = animator;
    }


    @Override
    public void call(final Subscriber<? super Void> subscriber) {
        checkUiThread(); //检查是否在 UI 线程调用
        Animator.AnimatorListener listener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                subscriber.onNext(null);
                Logger.d("onAnimationStart() ");
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                subscriber.onCompleted();
                Logger.d("onAnimationEnd() ");
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        };

        AnimatorListenerAdapter adapter = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                subscriber.onNext(null);
//                Logger.d("onAnimationStart()");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                subscriber.onCompleted();
//                Logger.d("onAnimationEnd()");
            }
        };

        animator.addListener(adapter);
        animator.start();

    }
}
