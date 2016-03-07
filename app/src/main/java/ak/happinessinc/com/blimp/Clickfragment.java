package ak.happinessinc.com.blimp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Clickfragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Clickfragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Clickfragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static Uri IMGURI;
    static  final  int REQUEST_IMAGE_CAPTURE=1;

    private OnFragmentInteractionListener mListener;
    public Clickfragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Clickfragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Clickfragment newInstance(String param1, String param2) {
        Clickfragment fragment = new Clickfragment();
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
ImageView preview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_clickfragment, container, false);
        FloatingActionButton cameraButton= (FloatingActionButton) rootView.findViewById(R.id.fab);
        preview=(ImageView) rootView.findViewById(R.id.imageView);
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startCamIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (startCamIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    File f = new File(Environment.getExternalStorageDirectory(), "Blimp");
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                    File file = new File(Environment.getExternalStorageDirectory() + "/Blimp/", "photoblimp" + new Date().getTime() + ".png");
                    Uri imgUri = Uri.fromFile(file);
                    IMGURI = imgUri;
                    if (file != null) {
                        startCamIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                        startActivityForResult(startCamIntent, REQUEST_IMAGE_CAPTURE);
                    }


                }
            }
        });

        return rootView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE ) {
           DisplayPreview(IMGURI);
        }
    }
    public void DisplayPreview(Uri imageUri){
       //preview.setImageURI(imageUri);
        Picasso.with(getContext())
                .load(imageUri)
                .into(preview);

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
}