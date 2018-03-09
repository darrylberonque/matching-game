package darrylb.seas.edu.matchinggame;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


/**
 * Created by Darryl on 2/11/18.
 */

public class CardsAdapter extends BaseAdapter {

    private int[] blankImages;
    Context context;

    public CardsAdapter(Context context, int size) {
        this.context = context;
        this.blankImages = new int[size];
        for(int i = 0; i < blankImages.length; i++) {
            blankImages[i] = R.drawable.back_of_card_two;
        }
    }

    @Override
    public int getCount() {
        return blankImages.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView cardImage;

        if(view == null) {
            cardImage = new ImageView(context);
            cardImage.setAdjustViewBounds(true);
            cardImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            cardImage.setPadding(5, 5,5,5);
        } else {
            cardImage = (ImageView) view;
            cardImage.setAdjustViewBounds(true);
            cardImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            cardImage.setPadding(5,5,5,5);
        }

        cardImage.setImageResource(blankImages[i]);
        return cardImage;
    }
}
