package ir.oveissi.simpleretrofitsample.data;

import ir.oveissi.simpleretrofitsample.data.jsonmodel.DetailMovie;
import ir.oveissi.simpleretrofitsample.data.jsonmodel.TmpMovies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Abbas on 24/05/2016.
 */
public interface ApiInterface {
        @GET("?")
        Call<TmpMovies> getMoviesByTitle(@Query("s") String title, @Query("page") int page);

        @GET("?")
        Call<DetailMovie> getDetailMovieByID(@Query("i") String movie_id,
                                             @Query("plot") String plot,
                                             @Query("r") String r);

}
