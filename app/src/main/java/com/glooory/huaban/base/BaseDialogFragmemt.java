package com.glooory.huaban.base;

import android.support.v7.app.AppCompatDialogFragment;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Glooory on 2016/9/19 0019 20:08.
 */
public class BaseDialogFragmemt extends AppCompatDialogFragment {
    private CompositeSubscription mCompositeSubscription;

    public void addSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }

        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }
}
