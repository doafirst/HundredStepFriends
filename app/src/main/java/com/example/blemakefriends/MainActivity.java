package com.example.blemakefriends;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.os.Bundle;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.CallbackManager;
import com.facebook.login.Login;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "HundredStep";
    private Button facebookButton;
    private ImageView ig;
    private LoginButton facebookButton1;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        facebookButton1 = (LoginButton) findViewById(R.id.login_button);
        ig = findViewById(R.id.imageView);
        callbackManager = CallbackManager.Factory.create();
        facebookButton1.setPermissions(Arrays.asList("public_profile, user_link, user_photos"));

        //String pic1 = "https://platform-lookaside.fbsbx.com/platform/profilepic/?asid=6644254275590067&height=50&width=50&ext=1679790809&hash=AeQp4eozZZHvkqABt7g";//jsonObject.getClass("picture");
        //Picasso.get().load(pic1).into(ig);
        Log.d(TAG,"APP Start");

        facebookButton1.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d(TAG,"==========Login Success==========");
                Log.d(TAG,loginResult.toString());
                Log.d(TAG,"USER ID=" + loginResult.getAccessToken().getUserId());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.d(TAG,"==========Login ERROR==========");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG,"onActivityResult");
        GraphRequest graph = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(@Nullable JSONObject jsonObject, @Nullable GraphResponse graphResponse) {
                Log.d(TAG,jsonObject.toString());
                try {
                    String id = jsonObject.getString("id");
                    String pic = jsonObject.getJSONObject("picture").getJSONObject("data").getString("url");
                    String link = jsonObject.getString("link");
                    Log.d(TAG,pic);
                    //Picasso.get().load("https://graph.facebook.com/100000166802056/picture?type=large").into(ig);
                    Picasso.get().load(pic).into(ig);
                }catch (JSONException e){

                }
            }

        });
        Bundle bundle = new Bundle();
        bundle.putString("fields","name, id, first_name, last_name, picture, link");
        graph.setParameters(bundle);
        graph.executeAsync();
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(@Nullable AccessToken accessToken, @Nullable AccessToken accessToken1) {
            if(accessToken1 == null){
                LoginManager.getInstance().logOut();
                ig.setImageResource(0);
            }
        }
    };
    @Override
    protected void onDestroy(){
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }
}