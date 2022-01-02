package com.swrevo.BannerOnline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.text.*;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.AuthCredential;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.TextView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import com.google.android.gms.*;

public class GooglesignActivity extends AppCompatActivity {
	
	
	private  static final String TAG = "GoogleActivity";
	private  static final int RC_SIGN_IN = 9001;
	private  GoogleSignInClient mGoogleSignInClient;
	private  FirebaseAuth mAuth;
	
	private LinearLayout linear1;
	private LinearLayout btngooglesign;
	private LinearLayout ui_user;
	private Button sign;
	private Button sign_out;
	private TextView email;
	private TextView uid;
	private Button go;
	
	private FirebaseAuth auth;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private Intent i = new Intent();
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.googlesign);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initialize(_savedInstanceState);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		btngooglesign = (LinearLayout) findViewById(R.id.btngooglesign);
		ui_user = (LinearLayout) findViewById(R.id.ui_user);
		sign = (Button) findViewById(R.id.sign);
		sign_out = (Button) findViewById(R.id.sign_out);
		email = (TextView) findViewById(R.id.email);
		uid = (TextView) findViewById(R.id.uid);
		go = (Button) findViewById(R.id.go);
		auth = FirebaseAuth.getInstance();
		
		sign.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				signIn();
			}
		});
		
		sign_out.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				signOut();
			}
		});
		
		go.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), MainActivity.class);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	private void initializeLogic() {
		 // [START config_signin]
		        // Configure Google Sign In
		        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
		                .requestIdToken("949723815584-lu7l8aniok1n5rpu723cd8q9rmerlh04.apps.googleusercontent.com")
		                .requestEmail()
		                .build();
		        // [END config_signin]
		
		        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
		
		        // [START initialize_auth]
		        // Initialize Firebase Auth
		        mAuth = FirebaseAuth.getInstance();
		        // [END initialize_auth]
		ui_user.setVisibility(View.GONE);
	}
	
	// [START onactivityresult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // [START_EXCLUDE]
                _UI_DESTROY(ui_user);
                // [END_EXCLUDE]
            }
        }
    }
    // [END onactivityresult] 
	
	@Override
	public void onStart() {
		super.onStart();
		// Check if user is signed in (non-null) and update UI accordingly.
		        FirebaseUser currentUser = mAuth.getCurrentUser();
	}
	
	@Override
	public void onBackPressed() {
		
	}
	private void _Lib1 () {
	}
	// [START auth_with_google]
	private void firebaseAuthWithGoogle(String idToken) {
	AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
	mAuth.signInWithCredential(credential)
	.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
	@Override
	public void onComplete(@NonNull Task<AuthResult> task) {
	if (task.isSuccessful()) {
	// Sign in success, update UI with the signed-in user's information
	Log.d(TAG, "signInWithCredential:success");
	FirebaseUser user = mAuth.getCurrentUser();
	_UI_SHOW(ui_user);
	//If Success
	} else {
	// If sign in fails, display a message to the user.
	Log.w(TAG, "signInWithCredential:failure", task.getException());
	_UI_DESTROY(ui_user);
	SketchwareUtil.showMessage(getApplicationContext(), "Authentication Failed");
	}
	}
	});
	}
	// [END auth_with_google]
	{
	}
	
	
	private void _Lib2 () {
	}
	// [START signin]
	    private void signIn() {
		        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
		        startActivityForResult(signInIntent, RC_SIGN_IN);
		    }
	    // [END signin]
	{
	}
	
	
	private void _Lib3 () {
	}
	private void signOut() {
		        // Firebase sign out
		        mAuth.signOut();
		
		        // Google sign out
		        mGoogleSignInClient.signOut().addOnCompleteListener(this,
		                new OnCompleteListener<Void>() {
			                    @Override
			                    public void onComplete(@NonNull Task<Void> task) {
				                        
				_UI_DESTROY(ui_user);
				//Clear UI User
				
				                    }
			                });
		    }
	{
	}
	
	
	private void _UI_SHOW (final View _view) {
		email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
		uid.setText(FirebaseAuth.getInstance().getCurrentUser().getUid());
		_view.setVisibility(View.VISIBLE);
	}
	
	
	private void _UI_DESTROY (final View _view) {
		email.setText("");
		uid.setText("");
		_view.setVisibility(View.GONE);
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}
