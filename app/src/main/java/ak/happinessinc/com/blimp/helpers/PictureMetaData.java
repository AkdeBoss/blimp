package ak.happinessinc.com.blimp.helpers;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Ananthakrishna on 04-03-2016.
 */
public class PictureMetaData implements Parcelable {
    Uri imageUri;

   public PictureMetaData(Uri uri){
       this.imageUri=uri;
   }

    public  Uri getUri(){
        return imageUri;
    }


    protected PictureMetaData(Parcel in) {
        imageUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<PictureMetaData> CREATOR = new Creator<PictureMetaData>() {
        @Override
        public PictureMetaData createFromParcel(Parcel in) {
            return new PictureMetaData(in);
        }

        @Override
        public PictureMetaData[] newArray(int size) {
            return new PictureMetaData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(imageUri, flags);
    }
}
