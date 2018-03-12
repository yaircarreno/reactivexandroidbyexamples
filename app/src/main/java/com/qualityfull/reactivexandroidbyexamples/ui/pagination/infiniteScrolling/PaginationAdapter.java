package com.qualityfull.reactivexandroidbyexamples.ui.pagination.infiniteScrolling;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qualityfull.reactivexandroidbyexamples.R;
import com.qualityfull.reactivexandroidbyexamples.data.model.response.marvel.Result;
import com.qualityfull.reactivexandroidbyexamples.util.ViewUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaginationAdapter extends RecyclerView.Adapter<PaginationAdapter.ViewHolder> {

    private static final int PX_WIGHT = ViewUtil.dpToPx(100);
    private static final int Px_HEIGHT = ViewUtil.dpToPx(100);
    private static final String EXTENSION = ".jpg";

    private List<Result> mCharacterList = null;
    private Context mContext;

    @Inject
    public PaginationAdapter(Context context) {
        this.mCharacterList = new ArrayList<>();
        this.mContext = context;
    }

    @Override
    public PaginationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pagination, parent, false);
        return new PaginationAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Result character = mCharacterList.get(position);
        holder.mTitleTextView.setText(character.getName());
        holder.mOverViewTextView.setText(character.getDescription());

        Picasso.with(mContext)
                .load(character.getThumbnail().getPath() + EXTENSION)
                .placeholder(R.drawable.item_placeholder)
                .resize(PX_WIGHT, Px_HEIGHT)
                .into(holder.mThumbnailImageView);
    }

    @Override
    public int getItemCount() {
        return mCharacterList.size();
    }

    public void setOptions(List<Result> characterList) {
        this.mCharacterList = characterList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title_textView)
        TextView mTitleTextView;

        @BindView(R.id.overview_textView)
        TextView mOverViewTextView;

        @BindView(R.id.thumbnail_imageView)
        ImageView mThumbnailImageView;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
