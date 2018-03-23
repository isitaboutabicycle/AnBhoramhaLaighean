package aip.laighean.boramha.anoraalaiean;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

//import org.json.simple.parser.JSONParser;

public class AnBhorumhaLaighean extends AppCompatActivity {

    //private TextView mTextMessage;
    private ListView mListView;
    private EditText boscaCuardach;
    private TextWatcher fairtheoir;
    private String foclooir;
    private JSONArray foclooirJSON;
    private String LAATHAIR_CHOMHAID_AN_FHOCLOORA = "Foclooir.json";
/*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borumha_laighean);

        // amharc liosta á thúsú
        mListView = (ListView) findViewById(R.id.foclooir);
        boscaCuardach = (EditText) findViewById(R.id.cuardaigh);

        ArrayList<Iontraail> iontraalacha = new ArrayList<Iontraail>();

        // JSON á léamh ó chomhad
        foclooir = leeighJSONooChomhad(getApplicationContext(), LAATHAIR_CHOMHAID_AN_FHOCLOORA);

        // iontraalacha á líonú
        try{
            // sraith JSON á cruthú ó na sonraí
            foclooirJSON = new JSONArray(foclooir);

            for(int i = 0; i < foclooirJSON.length(); i++){

                JSONObject jo = (JSONObject) foclooirJSON.get(i);

                // obiachtaí á gcruthú as an sraith JSON
                Iontraail io = new Iontraail((String) jo.get("teidil"), (String) jo.get("rannCainte"), (String) jo.get("sainmhiiniuu"));

                iontraalacha.add(io);
            }

        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "Fadhb leis an JSON: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }

        final FeilireIontraalacha adapter = new FeilireIontraalacha(this, iontraalacha);
        mListView.setAdapter(adapter);

        fairtheoir = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = boscaCuardach.getText().toString();
                adapter.filter(text);
            }
        };

        boscaCuardach.addTextChangedListener(fairtheoir);

        //BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public static String caighdeaanaigh(String inchur){
        String aschur = inchur.toLowerCase(Locale.getDefault())
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replace("ḃ", "bh")
                .replace("ċ", "ch")
                .replace("ḋ", "dh")
                .replace("ḟ", "fh")
                .replace("ġ", "gh")
                .replace('ɼ', 'r')
                .replace("ṁ", "mh")
                .replace("ṗ", "ph")
                .replace("ṡ", "sh")
                .replace('ſ', 's')
                .replace("ẛ", "sh")
                .replace("ṫ", "th")
                .replace("n̄", "nn")
                .replace('⁊', '&')
                ;

        return aschur;
    }

    public static String bainSiintii(String inchur){
        String aschur = inchur.toLowerCase(Locale.getDefault())
                .replace('á', 'a')
                .replace('é', 'e')
                .replace('í', 'i')
                .replace('ó', 'o')
                .replace('ú', 'u')
                .replace('ḃ', 'b')
                .replace('ċ', 'c')
                .replace('ḋ', 'd')
                .replace('ḟ', 'f')
                .replace('ġ', 'g')
                .replace('ɼ', 'r')
                .replace('ṁ', 'm')
                .replace('ṗ', 'p')
                .replace('ṡ', 's')
                .replace('ſ', 's')
                .replace('ẛ', 's')
                .replace('ṫ', 't')
                .replace("n̄", "n")
                .replace('⁊', '&')
                ;

        return aschur;
    }

    public String leeighJSONooChomhad(Context comhtheeacs, String comhad){
        String json = null;

        try{

            InputStream sruth = comhtheeacs.getAssets().open(comhad);
            int meeid = sruth.available();
            byte[] maolaan = new byte[meeid];
            sruth.read(maolaan);
            sruth.close();

            json = new String(maolaan, "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this.getApplicationContext(), R.string.fadhb_comhad, Toast.LENGTH_LONG).show();
            return null;
        }

        return json;
    }

}
