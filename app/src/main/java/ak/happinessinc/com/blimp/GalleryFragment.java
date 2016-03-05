package ak.happinessinc.com.blimp;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.SyncStateContract;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ak.happinessinc.com.blimp.adapters.RecyclerViewAdapter;
import ak.happinessinc.com.blimp.helpers.PictureMetaData;
import ak.happinessinc.com.blimp.helpers.StaticHelper;
import ak.happinessinc.com.blimp.viewholders.RecyclerViewHolder;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GalleryFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GalleryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GalleryFragment extends Fragment implements ActionMode.Callback,RecyclerView.OnItemTouchListener,View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public GalleryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GalleryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(String param1, String param2) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {refresh(); }
    }

    private GridLayoutManager lLayout;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerAdapter;
    GestureDetectorCompat gestureDetector;
    ActionMode actionMode;
    ArrayList<PictureMetaData> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_gallery, container, false);
        list=getAllItemList();
        lLayout = new GridLayoutManager(getContext(), 4);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lLayout);
        recyclerAdapter= new RecyclerViewAdapter(getContext(), list);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnItemTouchListener(this);
        gestureDetector = new GestureDetectorCompat(getActivity().getApplicationContext(), new simpleGestureListener());

        return rootView;
    }

    public void refresh(){
        Toast.makeText(getActivity().getApplicationContext(),"yo",Toast.LENGTH_LONG).show();
        list=getAllItemList();
        recyclerAdapter.notifyDataSetChanged();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    private void myToggleSelection(int idx) {
        recyclerAdapter.toggleSelection(idx);
        String title = getString(R.string.selected_count,recyclerAdapter.getSelectedItemCount());
        actionMode.setTitle(title);
    }





    File[] allFiles;

    private ArrayList<PictureMetaData> getAllItemList(){
        ArrayList<PictureMetaData> allItems = new ArrayList<PictureMetaData>();

        File folder = new File(Environment.getExternalStorageDirectory().getPath()+"/Blimp/");
        allFiles = folder.listFiles();


            for(int i=0;i<allFiles.length;i++){
                allItems.add(i,new PictureMetaData(Uri.fromFile(allFiles[i])));
            }


        return allItems;
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {

        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_multiselect, menu);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {

        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                List<Integer> selectedItemPositions =
                        recyclerAdapter.getSelectedItems();
                for(int i= selectedItemPositions.size()-1;i>=0;i--) {
                recyclerAdapter.removeData(selectedItemPositions.get(i));
            }
            actionMode.finish();
            return true;

            case R.id.menu_sync:
                 selectedItemPositions =
                        recyclerAdapter.getSelectedItems();
                for(int i= selectedItemPositions.size()-1;i>=0;i--) {
                    //sync
                }
                actionMode.finish();
                return true;
            default:
                return false;
        }

    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        StaticHelper.Flags.SELECT_FLAG=false;
        recyclerAdapter.clearSelections();
        this.actionMode = null;

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        if (v!=null && v.getId() == R.id.card_view) {
           v.setActivated(true);
            int idx = recyclerView.getChildPosition(v);
            if (actionMode != null) {
                myToggleSelection(idx);
                return;
            }

        }
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class simpleGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null) {
                return;
            }
            StaticHelper.Flags.SELECT_FLAG=true;
            actionMode = getActivity().startActionMode(GalleryFragment.this);
            int idx = recyclerView.getChildPosition(view);
            myToggleSelection(idx);
            super.onLongPress(e);
        }
    }

}
