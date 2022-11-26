package com.example.asmmob201;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;


public class Manhinhc2Activity extends AppCompatActivity{
    //LOGIN FACEBOOK
    CallbackManager callbackManager;
    LoginButton btnLogin;

    //LOGIN GOOGLE
    GoogleSignInClient mGoogleSignInClient;
    SignInButton btnSignInButton;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhc2);
        Button btnDangNhap = findViewById(R.id.btnDangNhap);
        //LOGIN BẰNG DATABASE
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Manhinhc2Activity.this, LoginActivity.class));
            }
        });

        //LOGIN FACEBOOK
        btnLogin = findViewById(R.id.btnLogin);
        callbackManager = CallbackManager.Factory.create();
        //check người dùng đã đăng nhập trước đó hay chưa?
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
        if (isLoggedIn) {
            getUserProfile(accessToken);
            Toast.makeText(this, "Đã đăng nhập", Toast.LENGTH_SHORT).show();
        }
        //nút đăng nhập/logout
        btnLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserProfile(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(Manhinhc2Activity.this, "Hủy đăng nhập", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(Manhinhc2Activity.this, "Lỗi trong quá trình đăng nhập", Toast.LENGTH_SHORT).show();
            }
        });

        //LOGIN GOOGLE
        //ánh xạ
        btnSignInButton = findViewById(R.id.btnLoginGoogle);
        btnLogout = findViewById(R.id.btnLogout);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        btnSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                checkLogin.launch(signInIntent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        btnSignInButton.setVisibility(View.VISIBLE);
                        btnLogout.setVisibility(View.GONE);
                        Toast.makeText(Manhinhc2Activity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    //LOGIN GOOGLE
    ActivityResultLauncher<Intent> checkLogin = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            //đăng nhập thành công
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            String displayName = account.getDisplayName();
                            String email = account.getEmail();
                            Toast.makeText(Manhinhc2Activity.this, "Đăng nhập thành công - " + displayName + " - " + email, Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                               editor.putString("displayName", account.getDisplayName());
                               editor.commit();
                            startActivity(new Intent(Manhinhc2Activity.this, MainActivity.class));
                            btnSignInButton.setVisibility(View.GONE);
                            btnLogout.setVisibility(View.VISIBLE);
                        } catch (ApiException e) {
                            //đăng nhập thất bại
                            Toast.makeText(Manhinhc2Activity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    //LOGIN GOOGLE
    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null) {
            Toast.makeText(this, "Đã đăng nhập tài khoản " + account.getEmail(), Toast.LENGTH_SHORT).show();
            btnSignInButton.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        }
    }

    //LOGIN FACEBOOK
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void getUserProfile(AccessToken accessToken) {
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String name = object.getString("name");
                    String email = object.getString("id");
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    Toast.makeText(Manhinhc2Activity.this, name + " - " + email + " - " + image, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Manhinhc2Activity.this, MainActivity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture.width(200)");
        request.setParameters(parameters);
        request.executeAsync();
    }

}