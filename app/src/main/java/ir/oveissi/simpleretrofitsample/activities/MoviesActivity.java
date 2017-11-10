package ir.oveissi.simpleretrofitsample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import ir.oveissi.simpleretrofitsample.Constants;
import ir.oveissi.simpleretrofitsample.R;
import ir.oveissi.simpleretrofitsample.adapters.MoviesAdapter;
import ir.oveissi.simpleretrofitsample.data.ApiClient;
import ir.oveissi.simpleretrofitsample.data.ApiInterface;
import ir.oveissi.simpleretrofitsample.data.jsonmodel.Movie;
import ir.oveissi.simpleretrofitsample.data.jsonmodel.TmpMovies;
import ir.oveissi.simpleretrofitsample.utils.EndlessRecyclerOnScrollListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity {


    RecyclerView rv;
    MoviesAdapter mListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        rv=(RecyclerView)findViewById(R.id.rvMovies);
        rv.setLayoutManager(new LinearLayoutManager(this));
        mListAdapter=new MoviesAdapter(this, new ArrayList<Movie>());
        rv.setAdapter(mListAdapter);
        rv.addOnScrollListener(new EndlessRecyclerOnScrollListener((LinearLayoutManager) rv.getLayoutManager()) {
            @Override
            public void onLoadMore(int current_page) {
                get_data_from_webservice(current_page);
            }
        });
        get_data_from_webservice(1);
    }

    private void get_data_from_webservice(int page) {

        Call<TmpMovies> call=
                ApiClient.getClient().create(ApiInterface.class).getMoviesByTitle(Constants.API_KEY,"batman",page);
        call.enqueue(new Callback<TmpMovies>() {
            @Override
            public void onResponse(Call<TmpMovies> call, Response<TmpMovies> response) {
                if(response.isSuccessful())
                {
                    for(Movie m:response.body().Search)
                    {
                        mListAdapter.addItem(m);
                    }
                }

            }

            @Override
            public void onFailure(Call<TmpMovies> call, Throwable t) {
                Toast.makeText(MoviesActivity.this, "خطا در دریافت", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
