package com.hazelwood.sqlitelab;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Hazelwood on 10/21/14.
 */
public class Form_Fragment extends Fragment {
    public static final String NEW_PICTURE = "com.android.camera.NEW_PICTURE";
    Intent intent;

    public static final String TAG = "FORMTAG";
    ImageView imageView;
    Uri file;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static final int REQUEST_CAMERA = 0x01001;

    private double mParam1;
    private double mParam2;
    String field1, field2, field3;
    float field4;
    Uri image;
    File path;

    private OnFragmentInteractionListener mListener;

    public static Form_Fragment newInstance(double param1, double param2) {
        Form_Fragment fragment = new Form_Fragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_PARAM1, param1);
        args.putDouble(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public Form_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getDouble(ARG_PARAM1);
            mParam2 = getArguments().getDouble(ARG_PARAM2);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.form_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        DataHelper helper = new DataHelper(getActivity());




        if (id == R.id.action_save) {

            String imagePath = file.getPath();

            Log.d(TAG, "SAVE");
            EditText editText1 = (EditText) getActivity().findViewById(R.id.firstNameET);
            EditText editText2 = (EditText) getActivity().findViewById(R.id.lastNameET);
            EditText editText3 = (EditText) getActivity().findViewById(R.id.hireDateET);
            EditText editText4 = (EditText) getActivity().findViewById(R.id.payRateET);

            field1 = editText1.getText().toString();
            field2 = editText2.getText().toString();
            field3 = editText3.getText().toString();
            field4 = Float.parseFloat(editText4.getText().toString());

            helper.save(field1, field2, field3, field4, imagePath);
            getActivity().finish();

            return true;
        }
        return false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.form_fragment, container, false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, String.valueOf(mParam1) + " " + String.valueOf(mParam2));

        imageView = (ImageView) getActivity().findViewById(R.id.pictureBTN);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = getOutputUri();
                intent = new Intent(NEW_PICTURE);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, file);
                startActivityForResult(takePicture, REQUEST_CAMERA);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode != Activity.RESULT_CANCELED){
            if (data == null){
                imageView.setImageURI(file);
                image = file;
//                addImageToGallery(file);
            } else {
                Bitmap thumb = (Bitmap)data.getParcelableExtra("data");
                imageView.setImageBitmap(thumb);
            }
        }
    }

    private Uri getOutputUri(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyy_HHmmss");
        Date today = new Date(System.currentTimeMillis());
        String imageName = simpleDateFormat.format(today);

        File imgDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File appDir = new File(imgDir, "SQLite");
        appDir.mkdirs();

        File image = new File(appDir, imageName + ".jpg");
        path = image;

        try{
            image.createNewFile();
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return Uri.fromFile(image);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


}
