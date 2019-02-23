package blumenhausradau.de.gunnarstictactoe;

import android.os.*;
import android.support.annotation.*;
import android.support.v4.app.*;
import android.view.*;
import android.widget.*;


public class My_Five_DialogFragment extends DialogFragment {
    private static final String TAG = "MyCustomDialog";
    public EditText player1, player2;
    public String spieler1, spieler2;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_setting_players,container, false);

        player1 = view.findViewById(R.id.editspieler1);
        player2 = view.findViewById(R.id.editspieler2);

        Button abbrechen = view.findViewById(R.id.button_abbrechen);
        Button ok = view.findViewById(R.id.button_ok);

        //Abbrechen

        abbrechen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        //OK und zurück zur MainActivity

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spieler1 = player1.getText().toString();
                spieler2 = player2.getText().toString();

                if(spieler1.equals("")|| spieler2.equals("")){
                    Toast.makeText(getActivity(), "Bitte gib Namen für die Spieler ein", Toast.LENGTH_LONG).show();
                }else{
                    ((Five_FiveActivity)getActivity()).username1.setText(spieler1);
                    ((Five_FiveActivity)getActivity()).username2.setText(spieler2);
                    getDialog().dismiss();
                }


            }
        });
        return view;
    }
}
