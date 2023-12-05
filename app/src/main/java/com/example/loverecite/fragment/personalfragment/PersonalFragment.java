package com.example.loverecite.fragment.personalfragment;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loverecite.CetInfoActivity;
import com.example.loverecite.ChangeInfoActivity;
import com.example.loverecite.FragmentActivity;
import com.example.loverecite.LoginActivity;
import com.example.loverecite.R;
import com.example.loverecite.ReciteActivity;
import com.example.loverecite.datautils.TransportInfo;
import com.example.loverecite.okhttp.HttpTest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PersonalFragment extends Fragment {

    private PersonalViewModel mViewModel;

    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PersonalViewModel.class);
        // TODO: Use the ViewModel
        ConstraintLayout constraintLayout = getView().findViewById(R.id.user_info);
        ConstraintLayout ctl1 = getView().findViewById(R.id.cet4_check);
        ConstraintLayout ctl2 = getView().findViewById(R.id.cet6_check);
        ConstraintLayout ctl3 = getView().findViewById(R.id.login_out);
        TextView tv1 = getView().findViewById(R.id.cet4_text);
        TextView tv2 = getView().findViewById(R.id.cet6_text);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(getActivity(), ChangeInfoActivity.class);
                startActivity(intent1);
            }
        });

        class MyClick implements View.OnClickListener{
            private int caseid;
            MyClick(int caseid){
                this.caseid = caseid;
            }
            public void onClick(View view) {
                HttpTest.getInstance().sendCheckRequest(TransportInfo.getUsername(), caseid, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity().getApplicationContext(),"请求失败，请检查网络！",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String result = response.body().string();
                        if(result.length()==3){
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity().getApplicationContext(),"服务器端获取信息失败！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Intent intent = new Intent(getActivity(), CetInfoActivity.class);
                            intent.putExtra("data",result);
                            intent.putExtra("id",caseid);
                            startActivity(intent);
                        }
                    }
                });
            }
        }
        MyClick mc1 = new MyClick(0);
        MyClick mc2 = new MyClick(1);

        ctl1.setOnClickListener(mc1);
        ctl2.setOnClickListener(mc2);

        ctl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences share = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                share.edit().putBoolean("LoginBool",false).apply();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

}