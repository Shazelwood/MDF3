package com.hazelwood.maps;

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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Form_Fragment extends Fragment {
    public static final String TAG = "FORMFRAGMENT_TAG";
    ImageView imageView;
    Uri file;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static final int REQUEST_CAMERA = 0x01001;

    private double mParam1;
    private double mParam2;
    String field1, field2;
    Uri image;
    File path;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Form_Fragment.
     */
    public static Form_Fragment newInstance(double param1, double param2) {
        Form_Fragment fragment = new Form_Fragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_PARAM1, param1);
        args.putDouble(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public Form_Fragment() {
        // Required empty public constructor
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
        getActivity().getMenuInflater().inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            Log.d(TAG, "SAVE");
            EditText editText1 = (EditText) getActivity().findViewById(R.id.fieldONE);
            EditText editText2 = (EditText) getActivity().findViewById(R.id.fieldTWO);

            field1 = editText1.getText().toString();
            field2 = editText2.getText().toString();

            ArrayList<Form> formArrayList = new ArrayList<Form>();

            try {
                File extFolder = getActivity().getExternalFilesDir(null);
                File file = new File(extFolder, "MAP.dat");

                if (file.exists()) {
                    FileInputStream fin = new FileInputStream(file);
                    ObjectInputStream oin = new ObjectInputStream(fin);
                    formArrayList = (ArrayList<Form>) oin.readObject();
                    oin.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }

            Log.d(TAG, "Field ONE: " + field1);
            Log.d(TAG, field2);
            Log.d(TAG, " " + image);
            Log.d(TAG," " + path);
            Log.d(TAG,String.valueOf(mParam1));
            Log.d(TAG, String.valueOf(mParam2));

            ArrayList<Form> forms = new ArrayList<Form>();
            if (formArrayList != null){
                forms = formArrayList;
            }

            forms.add(new Form(field1, field2, image.getPath(), path, mParam1, mParam2));

            try {
                File extFolder = getActivity().getExternalFilesDir(null);
                File extFile = new File(extFolder, "MAP.dat");
                FileOutputStream fos = new FileOutputStream(extFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(forms);
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            getActivity().finish();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.form_fragment, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

        imageView = (ImageView) getActivity().findViewById(R.id.pictureFIELD);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = getOutputUri();
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
        File appDir = new File(imgDir, "MapApp");
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

    public void addImageToGallery(Uri image){
        Intent scan = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        scan.setData(image);
        getActivity().sendBroadcast(scan);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
