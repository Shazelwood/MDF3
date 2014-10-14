package com.hazelwood.imageduplicator;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class Grid_Fragment extends Fragment {
    public static final String TAG = "GRIDFRAGMENT_TAG";
    private ActionMode mActionMode;

    private static final String ARG_PARAM = "param1";

    private ArrayList<String> mParam;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public static Grid_Fragment newInstance(ArrayList<String> strings) {
        Grid_Fragment fragment = new Grid_Fragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM, strings);
        fragment.setArguments(args);
        return fragment;
    }

    public Grid_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getStringArrayList(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.grid_fragment, container, false);
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        GridView gridView = (GridView) getActivity().findViewById(R.id.grid_fragment);
        final Grid_Adapter adapter = new Grid_Adapter(getActivity(), mParam);
        gridView.setAdapter(adapter);

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                mActionMode = getActivity().startActionMode(new ActionMode.Callback() {
                    // Called when the action mode is created; startActionMode() was called
                    @Override
                    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                        // Inflate a menu resource providing context menu items
                        MenuInflater inflater = mode.getMenuInflater();
                        inflater.inflate(R.menu.actions, menu);
                        return true;
                    }

                    // Called each time the action mode is shown. Always called after onCreateActionMode, but
                    // may be called multiple times if the mode is invalidated.
                    @Override
                    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                        return false; // Return false if nothing is done
                    }

                    // Called when the user selects a contextual menu item
                    @Override
                    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:
                                Log.d(TAG, "DELETE");
                                mParam.remove(i);
                                adapter.notifyDataSetChanged();

                                try {
                                    File extFolder = getActivity().getExternalFilesDir(null);
                                    File extFile = new File(extFolder, "PICTURES.dat");
                                    FileOutputStream fos = new FileOutputStream(extFile);
                                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                                    oos.writeObject(mParam);
                                    oos.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                mode.finish(); // Action picked, so close the CAB
                                return true;
                            case R.id.action_share:

                                File root = Environment.getExternalStorageDirectory();
                                Intent share = new Intent(Intent.ACTION_SEND);
                                share.setType("image/*");
                                share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(mParam.get(i))));
                                startActivity(Intent.createChooser(share,"Share via"));

                                Log.d(TAG, "SHARE");
                                mode.finish();
                            default:
                                return false;
                        }
                    }

                    // Called when the user exits the action mode
                    @Override
                    public void onDestroyActionMode(ActionMode mode) {
                        mActionMode = null;
                    }
                });
                view.setSelected(true);
                return true;
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
