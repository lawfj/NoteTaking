package zhj.notetaking.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import es.dmoral.toasty.Toasty;
import zhj.notetaking.R;
import zhj.notetaking.domain.User;



/**
 * 登陆页面
 */
public class LoginActivity extends BaseActivity {
    public static final int LOGIN_OK=1;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    AppCompatButton _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;
    private User mUserInfo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setStartAnim(false);
        setCloseAnim(false);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();

//                BmobUser.loginByAccount("admin", "123456", new LogInListener<User>() {
//
//                    @Override
//                    public void done(User user, BmobException e) {
//                        if (user != null) {
//                            Log.i("smile", "用户登陆成功");
//                            onLoginSuccess();
//                        } else {
//                            Log.i("smile", "用户登陆失败");
//                        }
//                    }
//                });
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }


    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("登录中...");
        progressDialog.show();

        String userStr = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        BmobUser.loginByAccount(userStr, password, new LogInListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if (user != null) {
                    Log.i("smile", "用户登陆成功");
                    onLoginSuccess();
                    progressDialog.dismiss();
                } else {
                    progressDialog.dismiss();
                    _loginButton.setEnabled(true);
                    Log.i("smile", "用户登陆失败");
                    Toasty.error(LoginActivity.this, "登录失败，请检查用户名和密码", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        mUserInfo = BmobUser.getCurrentUser(User.class);
        _loginButton.setEnabled(true);
        setResult(LOGIN_OK, null);
        finish();
    }

    public void onLoginFailed() {
        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || email.length() < 3) {
            Toasty.warning(getBaseContext(),"用户名至少三个字符",Toast.LENGTH_SHORT).show();
//            _emailText.setError("至少三个字符");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            Toasty.warning(getBaseContext(),"密码长度大于四，小于十",Toast.LENGTH_SHORT).show();
//            _passwordText.setError("密码长度大于四，小于十");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

}
