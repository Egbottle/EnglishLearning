package com.example.loverecite.fragment.bookfragment;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.loverecite.ChooseActivity;
import com.example.loverecite.R;
import com.example.loverecite.ReciteActivity;
import com.example.loverecite.datautils.TransportInfo;
import com.example.loverecite.okhttp.HttpTest;

public class BookFragment extends Fragment {

    private BookViewModel mViewModel;

    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        // TODO: Use the ViewModel
        LinearLayout linearLayout1 = getView().findViewById(R.id.cet4);
        LinearLayout linearLayout2 = getView().findViewById(R.id.cet6);
        //点击cet4
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent1=new Intent(getActivity(), ChooseActivity.class);
                intent1.putExtra("book","CET4");
                startActivity(intent1);
            }
        });
        //点击cet6触发
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1=new Intent(getActivity(), ChooseActivity.class);
                intent1.putExtra("book","CET6");
                startActivity(intent1);
            }
        });
    }
}