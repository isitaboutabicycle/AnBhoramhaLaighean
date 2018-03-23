package aip.laighean.boramha.anoraalaiean;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Cathal on 20/02/2018.
 */

public class FeilireIontraalacha extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<Iontraail> mDataSource;
    private ArrayList<Iontraail> cooipSonraii;

    public FeilireIontraalacha(Context comhtheeacs, ArrayList<Iontraail> baill){
        mContext = comhtheeacs;
        mDataSource = baill;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        cooipSonraii = new ArrayList<Iontraail>();

        cooipSonraii.addAll(baill);
    }

    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position; // id uathúil do gach ball liosta - d'fhéadfadh sé a bheith níos snasta
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.iontraail, parent, false);

        // Get title element
        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.TeidilNahIontraala);

        // Get subtitle element
        TextView subtitleTextView =
                (TextView) rowView.findViewById(R.id.RannCainteNahIontraala);

        // Get detail element
        TextView detailTextView =
                (TextView) rowView.findViewById(R.id.SainmhiiniuuNahIontraala);

        // sonraí á gcur isteach san amharc
        Iontraail iontraail = (Iontraail) getItem(position);
        boolean rannAnn = false;
        if(iontraail.rannCainte != null && iontraail.rannCainte != ""){rannAnn = true;}
        titleTextView.setText(iontraail.teidil);
        if(rannAnn){
            subtitleTextView.setText(
                    (iontraail.rannCainte == iontraail.sainmhiiniuu) ? "" : "\t\t("+iontraail.rannCainte+")"
            );
        } else {
            subtitleTextView.setText("");
        }
        detailTextView.setText(
                (iontraail.sainmhiiniuu == "" || iontraail.sainmhiiniuu == null) ? iontraail.rannCainte : iontraail.sainmhiiniuu
        );

        // cló á roghnú
        Typeface titleTypeFace = Typeface.createFromAsset(mContext.getAssets(), "bunchlo.ttf");
        Typeface descriptionTypeFace = Typeface.createFromAsset(mContext.getAssets(), "urchlo.ttf");
        titleTextView.setTypeface(titleTypeFace);
        subtitleTextView.setTypeface(descriptionTypeFace);
        detailTextView.setTypeface(descriptionTypeFace);

        return rowView;
    }

    public void filter(String charText) {

        mDataSource.clear();
        if (charText.length() == 0) {
            mDataSource.addAll(cooipSonraii);
        }
        else
        {
            String cuardaithe = charText.toLowerCase(Locale.getDefault());
            String cuardaitheCaighdeaanach = AnBhorumhaLaighean.caighdeaanaigh(cuardaithe);

            for (Iontraail iontraail : cooipSonraii )
            {
                //todo: glac caractair le buailte ⁊rl freisin
                String teidilLom = AnBhorumhaLaighean.bainSiintii(iontraail.teidil.toLowerCase(Locale.getDefault()));
                String teidilCaighdeaanach = AnBhorumhaLaighean.caighdeaanaigh(iontraail.teidil.toLowerCase(Locale.getDefault()));

                if (teidilLom.contains(cuardaithe)
                        || teidilLom.contains(cuardaitheCaighdeaanach)
                        || teidilCaighdeaanach.contains(cuardaithe)
                        || teidilCaighdeaanach.contains(cuardaitheCaighdeaanach))
                {
                    mDataSource.add(iontraail);
                }
            }

            if(mDataSource.size() < 2){
                for (Iontraail iontraail : cooipSonraii )
                {
                    String rannCainteLom = AnBhorumhaLaighean.bainSiintii(iontraail.rannCainte.toLowerCase(Locale.getDefault()));
                    String rannCaighdeaanach = AnBhorumhaLaighean.caighdeaanaigh(iontraail.rannCainte.toLowerCase(Locale.getDefault()));
                    String sainmhiiniuuLom = AnBhorumhaLaighean.bainSiintii(iontraail.sainmhiiniuu.toLowerCase(Locale.getDefault()));
                    String sainmhiiniuuCaighdeaanach = AnBhorumhaLaighean.caighdeaanaigh(iontraail.sainmhiiniuu.toLowerCase(Locale.getDefault()));

                    if (       rannCainteLom.contains(cuardaithe)
                            || rannCainteLom.contains(cuardaitheCaighdeaanach)
                            || rannCaighdeaanach.contains(cuardaithe)
                            || rannCaighdeaanach.contains(cuardaitheCaighdeaanach)
                            || sainmhiiniuuLom.contains(cuardaithe)
                            || sainmhiiniuuLom.contains(cuardaitheCaighdeaanach)
                            || sainmhiiniuuCaighdeaanach.contains(cuardaithe)
                            || sainmhiiniuuCaighdeaanach.contains(cuardaitheCaighdeaanach))
                    {
                        mDataSource.add(iontraail);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }
}
