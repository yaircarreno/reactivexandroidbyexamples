package com.qualityfull.reactivexandroidbyexamples.injection.component;

import com.qualityfull.reactivexandroidbyexamples.injection.module.ActivityModule;
import com.qualityfull.reactivexandroidbyexamples.injection.scope.PerActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.home.HomeActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.orchestration.auth.AuthActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.orchestration.menu.OrchestrationMenuActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.orchestration.multipleapi.MultipleApiActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.orchestration.storing.StoringActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.infiniteScrolling.PaginationActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.loadMore.PaginationLoadMoreActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.menu.PaginationMenuActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.withPublishSubject.PaginationPublishActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.pagination.withSwitchMap.PaginationSwitchMapActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.searchbox.SearchBoxActivity;
import com.qualityfull.reactivexandroidbyexamples.ui.validationforms.ValidationFormsActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(PaginationMenuActivity paginationMenuActivity);

    void inject(PaginationActivity paginationActivity);

    void inject(PaginationLoadMoreActivity paginationLoadMoreActivity);

    void inject(PaginationSwitchMapActivity paginationSwitchMapActivity);

    void inject(PaginationPublishActivity paginationPublishActivity);

    void inject(SearchBoxActivity searchBoxActivity);

    void inject(ValidationFormsActivity validationFormsActivity);

    void inject(OrchestrationMenuActivity orchestrationMenuActivity);

    void inject(AuthActivity authActivity);

    void inject(MultipleApiActivity multipleApiActivity);

    void inject(StoringActivity storingActivity);
}
