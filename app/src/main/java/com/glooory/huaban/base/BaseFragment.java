package com.glooory.huaban.base;

import android.support.v4.app.Fragment;

import com.umeng.analytics.MobclickAgent;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Glooory on 2016/8/27 0027.
 */
public abstract class BaseFragment extends Fragment {

    private CompositeSubscription mCompositeSubscription;

    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription subscription) {
        if (subscription == null) {
            return;
        }

        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(subscription);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "@"
                + Integer.toHexString(hashCode());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
        MyApplication.getRefwatcher(getActivity()).watch(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getClass().getSimpleName());
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getSimpleName());
    }

}
