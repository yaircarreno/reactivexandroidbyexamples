package com.qualityfull.reactivexandroidbyexamples.util;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import timber.log.Timber;

public class ObservableLoggerTransformer<R> implements ObservableTransformer<R, R> {

    private final String tag;

    private ObservableLoggerTransformer(String tag) {
        this.tag = tag;
    }

    public static <R> ObservableLoggerTransformer<R> debugLog(String tag) {
        return new ObservableLoggerTransformer<>(tag);
    }

    @Override
    public ObservableSource<R> apply(Observable<R> upstream) {
        return upstream
                .doOnNext(v -> this.log("doOnNext", v))
                .doOnError(error -> this.log("doOnError", error))
                .doOnComplete(() -> this.log("doOnComplete", upstream.toString()))
                .doOnTerminate(() -> this.log("doOnTerminate", upstream.toString()))
                .doOnDispose(() -> this.log("doOnDispose", upstream.toString()))
                .doOnSubscribe(v -> this.log("doOnSubscribe", upstream.toString()));
    }

    private void log(String stage, Object item) {
        Timber.tag(tag);
        Timber.d("%s: %s:%s", stage, Thread.currentThread().getName(), item);
    }

    private void log(String stage, Throwable throwable) {
        Timber.tag(tag);
        Timber.d("%s: %s: error %s", stage, Thread.currentThread().getName(), throwable);
    }
}
