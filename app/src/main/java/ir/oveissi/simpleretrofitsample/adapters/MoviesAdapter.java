package ir.oveissi.simpleretrofitsample.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.oveissi.simpleretrofitsample.R;
import ir.oveissi.simpleretrofitsample.activities.DetailActivity;
import ir.oveissi.simpleretrofitsample.data.jsonmodel.Movie;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    public Context mContext;
    public List<Movie> itemsData;

    public MoviesAdapter(Context mContext, List<Movie> itemsData) {
        this.mContext = mContext;
        this.itemsData = itemsData;
    }

    public void clear()
    {
        this.itemsData.clear();
        notifyDataSetChanged();
    }
    public void addItem(Movie post)
    {
        this.itemsData.add(post);
        notifyItemInserted(this.itemsData.size()-1);
    }

    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_movie,  parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {


        viewHolder.tvMovieTitle.setText(itemsData.get(position).Title);
        viewHolder.tvMovieType.setText(itemsData.get(position).Type);
        Picasso.with(mContext)
                .load(itemsData.get(position).Poster)
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.imPoster);


        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=viewHolder.getAdapterPosition();
                Intent i=new Intent(mContext, DetailActivity.class);
                i.putExtra("movie_id",itemsData.get(pos).imdbID);
                mContext.startActivity(i);
            }
        });

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMovieTitle;
        public TextView tvMovieType;
        public ImageView imPoster;
        public CardView cv;
        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            tvMovieTitle = (TextView) itemLayoutView.findViewById(R.id.tvMovieTitle);
            tvMovieType = (TextView) itemLayoutView.findViewById(R.id.tvMovieType);
            imPoster = (ImageView) itemLayoutView.findViewById(R.id.imPoster);
            cv = (CardView) itemLayoutView.findViewById(R.id.cv);
        }
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }
}