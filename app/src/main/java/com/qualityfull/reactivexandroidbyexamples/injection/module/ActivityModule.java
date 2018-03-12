package com.qualityfull.reactivexandroidbyexamples.injection.module;

import android.app.Activity;
import android.content.Context;
import com.qualityfull.reactivexandroidbyexamples.injection.qualifier.ActivityContext;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.infiniteScrolling.PaginationAdapter;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mActivity;
    }

    @Provides
    PaginationAdapter providesPaginationAdapter() {
        return new PaginationAdapter(mActivity);
    }
}
