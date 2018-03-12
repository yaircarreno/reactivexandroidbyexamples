package com.qualityfull.reactivexandroidbyexamples.ui.pagination.menu;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.jakewharton.rxbinding2.view.RxView;
import com.qualityfull.reactivexandroidbyexamples.R;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class PaginationMenuAdapter extends RecyclerView.Adapter<PaginationMenuAdapter.ViewHolder> {

    private String[] mOptions;
    private PublishSubject<Integer> mViewClickProcessor = PublishSubject.create();

    @Inject
    PaginationMenuAdapter() {
    }

    void setOptions(String[] mOptions) {
        this.mOptions = mOptions;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.info_text)
        TextView mTextView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @SuppressLint("RxLeakedSubscription")
    @Override
    public PaginationMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, parent, false);
        final ViewHolder viewHolder = new PaginationMenuAdapter.ViewHolder(itemView);

        RxView.clicks(itemView)
                .takeUntil(RxView.detaches(parent))
                .map(aVoid -> itemView)
                .subscribe(r -> {
                    Timber.i(viewHolder.getAdapterPosition() + "");
                    mViewClickProcessor.onNext(viewHolder.getAdapterPosition());
                }, Timber::e);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mOptions[position]);
    }

    @Override
    public int getItemCount() {
        return mOptions.length;
    }

    Observable<Integer> getViewClickedObservable() {
        return mViewClickProcessor;
    }

}
