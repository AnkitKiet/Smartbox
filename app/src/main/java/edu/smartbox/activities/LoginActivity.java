package edu.smartbox.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.nightonke.boommenu.Animation.BoomEnum;
import com.nightonke.boommenu.Animation.EaseEnum;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.global.AppConfig;
import edu.smartbox.global.AppController;
import edu.smartbox.ui.SnackBar;
import edu.smartbox.util.NetworkCheck;

/**
 * Created by Ankit on 29/12/16.
 */
public class LoginActivity extends AppCompatActivity {

    private ProgressDialog mProgress;
    private BoomMenuButton bmb;
    @Bind(R.id.edtUniqe)
    EditText edtUnique;
    @Bind(R.id.edtPin)
    EditText edtPin;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pref = getApplication().getSharedPreferences("Options", MODE_PRIVATE);
        ButterKnife.bind(this);
        mProgress = new ProgressDialog(this);
        bmb = (BoomMenuButton) findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.Ham);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.HAM_2);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.HAM_2);
        bmb.setBoomEnum(BoomEnum.PARABOLA_4);
        bmb.setShowScaleEaseEnum(EaseEnum.EaseOutBack);

        HamButton.Builder builder = new HamButton.Builder()
                .normalImageRes(R.mipmap.profilee)
                .normalTextRes(R.string.login)
                .subNormalTextRes(R.string.parentslogin)

                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        String unique, pin;

                        // When the boom-button corresponding this builder is clicked.
                        //  Toast.makeText(MainActivity.this, "Clicked " + index, Toast.LENGTH_SHORT).show();
                        unique = edtUnique.getText().toString().trim();
                        pin = edtPin.getText().toString().trim();
                        if (unique.equals("") || pin.equals("")) {
                            SnackBar.show(LoginActivity.this, "Enter Credentials");
                        } else {
                            if (NetworkCheck.isNetworkAvailable(LoginActivity.this))

                                login(unique, pin, "1");
                            else
                                SnackBar.noInternet(LoginActivity.this);
                        }
                    }
                });

        bmb.addBuilder(builder);

        HamButton.Builder builder1 = new HamButton.Builder()
                .normalImageRes(R.mipmap.profilee)
                .normalTextRes(R.string.login)
                .subNormalTextRes(R.string.teacherlogin)
                .listener(new OnBMClickListener() {
                    @Override
                    public void onBoomButtonClick(int index) {
                        // When the boom-button corresponding this builder is clicked.
                        String unique, pin;
                        unique = edtUnique.getText().toString().trim();
                        pin = edtPin.getText().toString().trim();
                        if (NetworkCheck.isNetworkAvailable(LoginActivity.this)) {
                            if (unique.equals("") || pin.equals("")) {
                                SnackBar.show(LoginActivity.this, "Enter Credentials");
                            } else {
                                login(unique, pin, "2");
                            }
                        } else {
                            SnackBar.show(LoginActivity.this, "No Internet Connection");
                        }
                    }
                });
        bmb.addBuilder(builder1);
    }

    private void login(final String unique, final String pin, final String type) {
        mProgress.setMessage("Logging In");
        mProgress.show();
        String tag_json_arry = "json_array_req";
        String url = "http://www.smartboxapp.esy.es/api/login.php";
        StringRequest getreq = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonobj = new JSONObject(response);
                            boolean exist = jsonobj.getBoolean("valid");
                            if (exist) {
                                if (type.equals("1")) {
                                    String sname = jsonobj.getString("name");
                                    String sage = jsonobj.getString("age");
                                    String spic = jsonobj.getString("pic");
                                    String sgender = jsonobj.getString("gender");
                                    String sclass = jsonobj.getString("class");
                                    String ssec = jsonobj.getString("sec");
                                    String libraryid = jsonobj.getString("library_id");
                                    String pname = jsonobj.getString("parent_name");
                                    String pemail = jsonobj.getString("parent_email");
                                    String pmob = jsonobj.getString("parent_mobile");
                                    String address = jsonobj.getString("address");
                                    String bus = jsonobj.getString("bus");
                                    String route = jsonobj.getString("route");
                                    String roll = jsonobj.getString("roll");

                                    editor = pref.edit();
                                    editor.putString("profile_photo", spic);
                                    editor.putString("roll", roll);
                                    editor.putString("sname", sname);
                                    editor.putString("sage", sage);
                                    editor.putString("sclass", sclass);
                                    editor.putString("ssec", ssec);
                                    editor.putString("sgen", sgender);
                                    editor.putString("libid", libraryid);
                                    editor.putString("pemail", pemail);
                                    editor.putString("pmobile", pmob);
                                    editor.putString("pname", pname);
                                    editor.putString("address", address);
                                    editor.putString("bus", bus);
                                    editor.putString("route", route);
                                    editor.commit();
                                    mProgress.dismiss();
                                    Toast.makeText(LoginActivity.this, "Welcome To Smartbox", Toast.LENGTH_SHORT).show();
                                    AppConfig.login(LoginActivity.this);
                                    Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);

                                }
                                if (type.equals("2")) {
                                    editor = pref.edit();
                                    editor.putString("tname", jsonobj.getString("name"));
                                    editor.putString("tmobile", jsonobj.getString("mobile"));
                                    editor.putString("tclass", jsonobj.getString("class"));
                                    editor.putString("tsec", jsonobj.getString("sec"));
                                    editor.putString("tssnno", jsonobj.getString("ssnno"));
                                    editor.commit();
                                    mProgress.dismiss();
                                    Toast.makeText(LoginActivity.this, "Welcome To Smartbox", Toast.LENGTH_SHORT).show();
                                    AppConfig.loginteacher(LoginActivity.this);
                                    Intent i = new Intent(LoginActivity.this, Activity_TeachersDashboard.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);

                                }
                            } else {
                                mProgress.dismiss();
                                SnackBar.show(LoginActivity.this, "Invalid Credentials");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgress.dismiss();
                SnackBar.show(LoginActivity.this, "Try Again");
                // VolleyLog.d(TAG, "Error: " + error.getMessage());

            }

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("uniqueid", unique);
                params.put("type", type);
                params.put("pin", pin);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(getreq, tag_json_arry);

    }


}
