package wu.chukho.foodjunkies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomGridViewActivity extends BaseAdapter {

    private Context mContext;
    private final String[] gridViewString;
    private final int[] gridViewImage;

    public CustomGridViewActivity(Context context, String[] gridViewString, int[] gridViewImage) {
        this.mContext = context;
        this.gridViewString = gridViewString;
        this.gridViewImage = gridViewImage;
    }

    @Override
    public int getCount() {
        return gridViewString.length;
    }

    @Override
    public Object getItem(int item) {
        return null;
    }

    @Override
    public long getItemId(int itemId) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null) {
            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(gridViewString[i]);
            imageViewAndroid.setImageResource(gridViewImage[i]);
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
