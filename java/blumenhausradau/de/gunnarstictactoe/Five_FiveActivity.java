package blumenhausradau.de.gunnarstictactoe;

import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.*;

import org.w3c.dom.*;

import java.util.*;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class Five_FiveActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Five_FiveActivity";

    //changes double X.0 to long X, otherwise double X.5 stays the same
    public static String fmt(double d)
    {if(d == (long) d)return String.format(Locale.GERMANY,"%d",(long)d);else return String.format("%s",d);}

    private Button[][] buttons = new Button[5][5];
    private boolean player1Turn = true;
    private int roundCount;
    private double player1Points;
    private double player2Points;
    public TextView textViewPlayer1;
    public TextView username1;
    public TextView textViewPlayer2;
    public TextView username2;
    public String spielerpunkte1,spielerpunkte2;
    private Drawable d;
    private Context context;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.five_scale_menu, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_five);
        context = Five_FiveActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        textViewPlayer1 = findViewById(R.id.player1_punkte);
        spielerpunkte1 = textViewPlayer1.getText().toString();
        username1 = findViewById(R.id.text_view_p1);

        textViewPlayer2 = findViewById(R.id.player2_punkte);
        spielerpunkte2 = textViewPlayer2.getText().toString();
        username2 = findViewById(R.id.text_view_p2);

        //transfers the original usernames from other activities into TextView of this Activity
        //if app starts, there is no Intent() that opened MainActivity, getExtras() == null, causes NullPointerException
        if(getIntent().getExtras() !=null){
            Bundle bundle = getIntent().getExtras();
            String data1 = bundle.getString("username1");
            String data2 = bundle.getString("username2");
            String data3 = bundle.getString("userpoints1");
            String data4 = bundle.getString("userpoints2");
            username1.setText(data1);
            username2.setText(data2);
            textViewPlayer1.setText(data3);
            textViewPlayer2.setText(data4);
        }

        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);



        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }


        //Reset game results
        Button buttonReset = findViewById(R.id.button_reset);
        d = buttonReset.getBackground();
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        //Set new Players name
        Button btnplayerreset = findViewById(R.id.player_reset_button);

        btnplayerreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                My_Five_DialogFragment dialog = new My_Five_DialogFragment();
                dialog.show(getSupportFragmentManager(),"MyCustomDialog");
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
            //((Button) v).setBackground(getResources().getDrawable(R.drawable.button_red));
            v.setBackgroundResource(R.color.darkred);
            ((Button) v).setTextColor(getResources().getColor(R.color.white));
            //((Button)v).setBackgroundResource(R.drawable.button_red);
        } else {
            ((Button) v).setText("O");
            ((Button) v).setBackgroundResource(R.color.blue);
            ((Button) v).setTextColor(getResources().getColor(R.color.white));
            //((Button)v).setBackgroundResource(R.drawable.bblue);
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCount == 25) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        //check if horizontal buttons are equal to first button
        for (int i = 0; i < 5; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && field[i][0].equals(field[i][3])
                    && field[i][0].equals(field[i][4])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        //check if vertical buttons are equal to first button
        for (int i = 0; i < 5; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && field[0][i].equals(field[3][i])
                    && field[0][i].equals(field[4][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        //check if top left button equals to diagonal buttons
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && field[0][0].equals(field[3][3])
                && field[0][0].equals(field[4][4])
                && !field[0][0].equals("")) {
            return true;
        }
        //check if top left button equals to diagonal buttons
        if (field[0][4].equals(field[1][3])
                && field[0][4].equals(field[2][2])
                && field[0][4].equals(field[3][1])
                && field[0][4].equals(field[4][0])
                && !field[0][4].equals("")) {
            return true;
        }

        return false;
    }

    private void player1Wins() {
        player1Points += 1;

        Toast toast = Toast.makeText(context, getString(R.string.spieler1_gewonnen,(username1).getText().toString()), Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_background_red);
        TextView text = view.findViewById(android.R.id.message);
        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
        text.setTextColor(Color.parseColor("#000000"));
        text.setPadding(100,20,100,20);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setTextSize(25);
        toast.show();


        //Toast.makeText(this, getString(R.string.spieler1_gewonnen,(username1).getText().toString()), Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }

    private void player2Wins() {
        player2Points += 1;
        Toast toast = Toast.makeText(context, getString(R.string.spieler2_gewonnen,(username2).getText().toString()), Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_background_blue);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
        text.setTextColor(Color.parseColor("#000000"));
        text.setPadding(100,20,100,20);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setTextSize(25);
        toast.show();
        //Toast.makeText(this, getString(R.string.spieler2_gewonnen,(username2).getText().toString()), Toast.LENGTH_LONG).show();
        updatePointsText();
        resetBoard();
    }

    private void draw() {
        player1Points += 0.5;
        player2Points += 0.5;
        Toast toast = Toast.makeText(context, "Unentschieden. Nochmal!", Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundResource(R.drawable.toast_background_blue);
        TextView text = (TextView) view.findViewById(android.R.id.message);
        /*Here you can do anything with above textview like text.setTextColor(Color.parseColor("#000000"));*/
        text.setTextColor(Color.parseColor("#000000"));
        text.setPadding(100,20,100,20);
        text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        text.setTextSize(25);
        toast.show();

        updatePointsText();



        //Toast.makeText(this, "Unentschieden. Nochmal!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        textViewPlayer1.setText(fmt(player1Points));
        textViewPlayer2.setText(fmt(player2Points));
    }

    private void resetBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setBackground(d);
            }
        }

        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePointsText();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCount);
        outState.putDouble("player1Points", player1Points);
        outState.putDouble("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getDouble("player1Points");
        player2Points = savedInstanceState.getDouble("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String name1 = username1.getText().toString();
        String name2 = username2.getText().toString();
        String points1 = textViewPlayer1.getText().toString();
        String points2 = textViewPlayer2.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("username1", name1);
        bundle.putString("username2",name2);
        bundle.putString("userpoints1",points1);
        bundle.putString("userpoints2",points2);
        Log.d(TAG, "onOptionsItemSelected: usernames:" + username1.getText().toString() + "," + username2.getText().toString());

        switch (item.getItemId()){
            case R.id.item1:
                Intent intent1 = new Intent(context,MainActivity.class);
                intent1.putExtras(bundle);
                startActivity(intent1);
                finish();
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(context,Four_FourActivity.class);
                intent2.putExtras(bundle);
                startActivity(intent2);
                finish();
                return true;
        }return false;
    }




}