package ak.happinessinc.com.blimp.viewholders;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ak.happinessinc.com.blimp.FullscreenActivity;
import ak.happinessinc.com.blimp.R;
import ak.happinessinc.com.blimp.helpers.PictureMetaData;
import ak.happinessinc.com.blimp.helpers.StaticHelper;

/**
 * Created by Ananthakrishna on 04-03-2016.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public ImageView pictureView;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        pictureView = (ImageView)itemView.findViewById(R.id.galleryActivity_photoiMageView);
    }
    ArrayList<PictureMetaData> list;
    @Override
    public void onClick(View view) {
            if(!StaticHelper.Flags.SELECT_FLAG) {
                Intent intent = new Intent(view.getContext(), FullscreenActivity.class);
                list = new ArrayList<>();
                list.add(new PictureMetaData((Uri) pictureView.getTag()));
                intent.putParcelableArrayListExtra("pics", list);
                view.getContext().startActivity(intent);

            }}

}