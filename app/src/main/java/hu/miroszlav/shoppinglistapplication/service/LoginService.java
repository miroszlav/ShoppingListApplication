package hu.miroszlav.shoppinglistapplication.service;


import com.f2prateek.rx.preferences2.RxSharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import hu.miroszlav.shoppinglistapplication.model.LoginInfo;
import hu.miroszlav.shoppinglistapplication.model.Token;
import io.reactivex.Observable;

@Singleton
public final class LoginService {

    private ApiService apiService;
    private RxSharedPreferences rxSharedPreferences;

    @Inject
    public LoginService(ApiService apiService, RxSharedPreferences rxSharedPreferences) {
        this.apiService = apiService;
        this.rxSharedPreferences = rxSharedPreferences;
    }

    public Observable<Token> login(String userName, String password) {
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setUserName(userName);
        loginInfo.setPassword(password);
        return apiService.doLogin(loginInfo);
    }

    public void storeToken(Token token) {
        rxSharedPreferences.getString("TOKEN").set(token.getToken());
    }

    public boolean isLoggedIn() {
        return rxSharedPreferences.getString("TOKEN").isSet();
    }

    public String getToken() {
        return rxSharedPreferences.getString("TOKEN").get();
    }

    public void logout() {
        rxSharedPreferences.getString("TOKEN").delete();
    }

}
