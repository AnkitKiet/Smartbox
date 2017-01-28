package edu.smartbox.fragments;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.smartbox.R;
import edu.smartbox.activities.Activity_SendNotice_teacher;
import edu.smartbox.activities.Activity_Send_TextAssignment;
import edu.smartbox.activities.Activity_imageUpload;
import edu.smartbox.activities.Principal_notice;

/**
 * Created by Ankit on 22/01/17.
 */
public class Teachers_HomeFragment extends Fragment {

    @Bind(R.id.imageAssignment)
    ImageView imgAssignment;
    @Bind(R.id.imageNotice)
    ImageView imgNotice;
    @Bind(R.id.imagePrincipalNotice)
    ImageView imgPrincipalNotice;
    private View parentView;

    public Teachers_HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        parentView = inflater.inflate(R.layout.teachers_home_fragment, container, false);
        ButterKnife.bind(this, parentView);
        populate();
        return parentView;
    }

    private void populate() {
        Picasso.with(getActivity()).load("http://www.smartboxapp.esy.es/app_images/assignment.jpg").into(imgAssignment);
        Picasso.with(getActivity()).load("http://www.smartboxapp.esy.es/app_images/imp_notice.jpg").into(imgNotice);
        Picasso.with(getActivity()).load("http://www.smartboxapp.esy.es/app_images/principal_notice.jpg").into(imgPrincipalNotice);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View bottomSheetView = getActivity().getLayoutInflater().inflate(R.layout.bottomsheet_assignment, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        imgNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Activity_SendNotice_teacher.class);
                startActivity(i);
            }
        });


        imgPrincipalNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), Principal_notice.class);
                startActivity(intent);
            }
        });


        imgAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.show();

            }
        });

       // LinearLayout pdfButton = (LinearLayout) bottomSheetDialog.findViewById(R.id.pdf);
        LinearLayout imageButton = (LinearLayout) bottomSheetDialog.findViewById(R.id.image);
        LinearLayout textButton = (LinearLayout) bottomSheetDialog.findViewById(R.id.text);
//        pdfButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "pdf button pressed", Toast.LENGTH_SHORT).show();
//
//            }
//        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_imageUpload.class);
                startActivity(intent);

            }
        });

        textButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),Activity_Send_TextAssignment.class);
                startActivity(i);

            }
        });


    }


}
