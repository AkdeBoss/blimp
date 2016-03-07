package ak.happinessinc.com.blimp.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import ak.happinessinc.com.blimp.R;
import ak.happinessinc.com.blimp.helpers.PictureMetaData;
import ak.happinessinc.com.blimp.helpers.StaticHelper;
import ak.happinessinc.com.blimp.imgurmodel.Upload;
import ak.happinessinc.com.blimp.viewholders.RecyclerViewHolder;

/**
 * Created by Ananthakrishna on 04-03-2016.
 */
public class RecyclerViewAdapter  extends RecyclerView.Adapter<RecyclerViewHolder> {

    private ArrayList<PictureMetaData> itemList;
    private Context context;

    public RecyclerViewAdapter(Context context, ArrayList<PictureMetaData> itemList) {
        this.itemList = itemList;
        this.context = context;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, null);
        RecyclerViewHolder rcv = new RecyclerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        Picasso.with(context)
                .load(itemList.get(position).getUri())
                .resize(200,200)
                .into(holder.pictureView);
        holder.pictureView.setTag(itemList.get(position).getUri());


    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }


    private SparseBooleanArray selectedItems;

    public void toggleSelection(int pos) {
        notifyItemChanged(pos);
        if (selectedItems.get(pos, false)) {
            selectedItems.delete(pos);
        }
        else {
            selectedItems.put(pos, true);
            StaticHelper.Flags.TOGGLE_FLAG=true;
        }

    }

    public void clearSelections() {
        selectedItems.clear();
        notifyDataSetChanged();
    }

    public PictureMetaData getItem(int i){
        return itemList.get(i);
    }

    public int getSelectedItemCount() {
        return selectedItems.size();
    }

    public List<Integer> getSelectedItems() {
        List<Integer> items =
                new ArrayList<Integer>(selectedItems.size());
        for (int i = 0; i < selectedItems.size(); i++) {
            items.add(selectedItems.keyAt(i));
        }
        return items;
    }

    public void removeData(int position) {
        File file = new File(itemList.get(position).getUri().getPath());
        file.delete();
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    public void swap(ArrayList<PictureMetaData> data){
        itemList.clear();
        itemList.addAll(data);
        notifyDataSetChanged();
    }


}
