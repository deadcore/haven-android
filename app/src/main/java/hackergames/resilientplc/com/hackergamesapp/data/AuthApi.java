package hackergames.resilientplc.com.hackergamesapp.data;

import hackergames.resilientplc.com.hackergamesapp.data.model.User;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by eduar on 18/01/2018.
 */

public interface AuthApi {
    @Headers({"Accept:text/plain"})
    @POST("auth")
    Observable<String> authUser(@Body User user);
}
