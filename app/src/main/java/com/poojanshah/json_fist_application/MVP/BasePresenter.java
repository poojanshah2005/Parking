package com.poojanshah.json_fist_application.MVP;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by shahp on 18/07/2017.
 */

public class BasePresenter<T extends MVPView> implements MVPPresenter<T> {

    //https://medium.com/@trionkidnapper/android-mvp-an-end-to-if-view-null-42bb6262a5d1
    private T view;

    private CompositeDisposable compositeSubscription = new CompositeDisposable();

    @Override
    public void attachView(T mvpView) {
        view = mvpView;

    }

    @Override
    public void detachView() {
        compositeSubscription.clear();
        view = null;
    }

    public T getView() {
        return view;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) {
            throw new MvpViewNotAttachedException();
        }
    }

    private boolean isViewAttached() {
        return view != null;
    }

    public void addSubscription(Disposable subscription) {
        this.compositeSubscription.add(subscription);
    }

    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter");
        }
    }
}