package ir.oveissi.simpleretrofitsample.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import ir.oveissi.simpleretrofitsample.Constants;
import ir.oveissi.simpleretrofitsample.R;
import ir.oveissi.simpleretrofitsample.data.ApiClient;
import ir.oveissi.simpleretrofitsample.data.ApiInterface;
import ir.oveissi.simpleretrofitsample.data.jsonmodel.DetailMovie;
import ir.oveissi.simpleretrofitsample.data.jsonmodel.Movie;
import ir.oveissi.simpleretrofitsample.data.jsonmodel.TmpMovies;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DetailActivity extends AppCompatActivity {

    TextView tvTitle,tvRate,tvRuntime,tvDirector;
    ImageView imPoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle=(TextView)findViewById(R.id.tvTitle);
        tvRate=(TextView)findViewById(R.id.tvRate);
        tvRuntime=(TextView)findViewById(R.id.tvRuntime);
        tvDirector=(TextView)findViewById(R.id.tvDirector);
        imPoster=(ImageView)findViewById(R.id.imPoster);

        String movie_id="";
        if(getIntent().getExtras()!=null)
        {
            movie_id=getIntent().getStringExtra("movie_id");
        }
        get_data_from_webservice(movie_id);
    }

    private void get_data_from_webservice(String movie_id) {

        Call<DetailMovie> call=
                ApiClient.getClient().create(ApiInterface.class).getDetailMovieByID(Constants.API_KEY,movie_id,"short","json");
        call.enqueue(new Callback<DetailMovie>() {
            @Override
            public void onResponse(Call<DetailMovie> call, Response<DetailMovie> response) {
                if(response.isSuccessful())
                {
                    tvTitle.setText(response.body().Title);
                    tvRate.setText(response.body().Rated);
                    tvRuntime.setText(response.body().Runtime);
                    tvDirector.setText(response.body().Director);
                    Picasso.with(DetailActivity.this)
                            .load(response.body().Poster)
                            .placeholder(R.drawable.placeholder)
                            .into(imPoster);
                }

            }

            @Override
            public void onFailure(Call<DetailMovie> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "خطا در دریافت", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
