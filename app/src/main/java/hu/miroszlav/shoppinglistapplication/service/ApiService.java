package hu.miroszlav.shoppinglistapplication.service;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.miroszlav.shoppinglistapplication.model.LoginInfo;
import hu.miroszlav.shoppinglistapplication.model.Token;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.POST;

@Singleton
public final class ApiService {

    private ServerApi serverApi;

    @Inject
    public ApiService(Retrofit retrofit) {
        serverApi = retrofit.create(ServerApi.class);
    }

    public Observable<Token> doLogin(LoginInfo loginInfo) {
        return serverApi.login(loginInfo);
    }

    private interface ServerApi {

        @POST("login")
        Observable<Token> login(@Body LoginInfo loginInfo);

    }
}
