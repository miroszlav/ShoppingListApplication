package hu.miroszlav.shoppinglistapplication.oauth;

import com.f2prateek.rx.preferences2.RxSharedPreferences;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public final class OauthInterceptor implements Interceptor {

    private final RxSharedPreferences rxSharedPreferences;

    @Inject
    public OauthInterceptor(RxSharedPreferences rxSharedPreferences) {
        this.rxSharedPreferences = rxSharedPreferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        if (rxSharedPreferences.getString("TOKEN").isSet()) {
            builder.header("Authorization", "Bearer " + rxSharedPreferences.getString("TOKEN").get());
        }

        return chain.proceed(builder.build());
    }
}
