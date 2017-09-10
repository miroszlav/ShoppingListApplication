package hu.miroszlav.shoppinglistapplication.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.miroszlav.shoppinglistapplication.R;
import hu.miroszlav.shoppinglistapplication.model.Token;
import hu.miroszlav.shoppinglistapplication.service.LoginService;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider.getInjectorProvider;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

public class LoginActivity extends RxAppCompatActivity {

    @Inject LoginService loginService;

    @BindView(R.id.username) EditText userNameView;
    @BindView(R.id.password) EditText passwordView;
    @BindView(R.id.login_progress) View progressView;
    @BindView(R.id.login_form) View loginFormView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getInjectorProvider().getMainComponent().inject(this);
    }

    @OnClick(R.id.sign_in_button)
    void doLogin() {
        loginService.login(userNameView.getText().toString(), passwordView.getText().toString())
                .subscribeOn(io())
                .observeOn(mainThread())
                .compose(this.<Token>bindToLifecycle())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Token token) {
                        loginService.storeToken(token);
                        startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        makeText(LoginActivity.this, R.string.invalid_credentials, LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                    }
                });

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

}

