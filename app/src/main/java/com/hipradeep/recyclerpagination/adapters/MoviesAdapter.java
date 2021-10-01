package com.hipradeep.recyclerpagination.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.hipradeep.recyclerpagination.R;
import com.hipradeep.recyclerpagination.models.MoviesData;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<BaseViewHolder > {
    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    List<MoviesData> list;
    Context context;

    public MoviesAdapter(List<MoviesData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public BaseViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new MyViewHolder(
                        LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(
                        LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);


    }
    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == list.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    public void addItems(List<MoviesData> postItems) {
        list.addAll(postItems);
        notifyDataSetChanged();
    }
    public void addLoading() {
        isLoaderVisible = true;
        list.add(new MoviesData());
        notifyItemInserted(list.size() - 1);
    }
    public void removeLoading() {
        isLoaderVisible = false;
        int position = list.size() - 1;
        MoviesData item = getItem(position);
        if (item != null) {
            list.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }
    MoviesData getItem(int position) {
        return list.get(position);
    }

    public class MyViewHolder extends BaseViewHolder {

        ImageView iv_movies_poster;
        TextView tv_movies_title, tv_type,tv_movies_years,tv_imdb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_movies_poster=itemView.findViewById(R.id.iv_movies_poster);
            tv_movies_title=itemView.findViewById(R.id.tv_movies_title);
            tv_type=itemView.findViewById(R.id.tv_type);
            tv_movies_years=itemView.findViewById(R.id.tv_movies_years);
            tv_imdb=itemView.findViewById(R.id.tv_imdb);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            MoviesData data=list.get(position);
            tv_movies_title.setText(data.getTitle());
            tv_type.setText(data.getType());
            tv_movies_years.setText(data.getYear());
            tv_imdb.setText(data.getImdbID());

            Glide.with(context).load(data.getPoster())
                    .placeholder(R.drawable.video_placeholder).dontAnimate()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .error(R.drawable.video_placeholder)
                    .apply(new RequestOptions().transform( new RoundedCorners(12)))
                    .into(iv_movies_poster);
        }
    }
    public class ProgressHolder extends BaseViewHolder {

        ProgressHolder(View itemView) {
            super(itemView);

        }
        @Override
        protected void clear() {
        }
    }

}

