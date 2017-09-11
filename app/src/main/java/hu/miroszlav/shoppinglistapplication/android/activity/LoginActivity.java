package hu.miroszlav.shoppinglistapplication.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.miroszlav.shoppinglistapplication.R;
import hu.miroszlav.shoppinglistapplication.model.Token;
import hu.miroszlav.shoppinglistapplication.service.LoginService;
import hu.miroszlav.shoppinglistapplication.util.AbstractObserver;
import hu.miroszlav.shoppinglistapplication.validator.LoginValidator;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;

import static android.view.View.*;
import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static hu.miroszlav.shoppinglistapplication.di.ComponentInjectorProvider.getInjectorProvider;
import static hu.miroszlav.shoppinglistapplication.validator.LoginValidator.validatePassword;
import static hu.miroszlav.shoppinglistapplication.validator.LoginValidator.validateUserName;
import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

public class LoginActivity extends RxAppCompatActivity {

    @Inject LoginService loginService;

    @BindView(R.id.username) TextInputEditText userNameView;
    @BindView(R.id.password) TextInputEditText passwordView;
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
        if (validateUserName(userNameView.getText().toString()) && validatePassword(passwordView.getText().toString())) {
            loginFormView.setVisibility(GONE);
            progressView.setVisibility(VISIBLE);
            loginService.login(userNameView.getText().toString(), passwordView.getText().toString())
                    .subscribeOn(io())
                    .observeOn(mainThread())
                    .doAfterTerminate(new Action() {
                        @Override
                        public void run() throws Exception {
                            progressView.setVisibility(GONE);
                            loginFormView.setVisibility(VISIBLE);
                        }
                    })
                    .compose(this.<Token>bindToLifecycle())
                    .subscribe(new AbstractObserver<Token>() {
                        @Override
                        public void onNext(@NonNull Token token) {
                            loginService.storeToken(token);
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            super.onError(e);
                            makeText(LoginActivity.this, R.string.invalid_credentials, LENGTH_SHORT).show();
                        }
                    });
        } else {
            makeText(this, R.string.required_field_error, LENGTH_SHORT).show();
        }
    }

}

