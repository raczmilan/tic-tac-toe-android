package lab2.xo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "lab2.XO.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startGame(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        EditText editText1 = (EditText) findViewById(R.id.edit_label1);
        EditText editText2 = (EditText) findViewById(R.id.edit_label2);
        String firstPlayer = editText1.getText().toString();
        String secondPlayer = editText2.getText().toString();
        if(firstPlayer.equals("") || secondPlayer.equals("")){
            CharSequence text = "Insert a name for both players";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        } else {
            intent.putExtra(EXTRA_MESSAGE, new String[]{firstPlayer, secondPlayer});
            startActivity(intent);
        }
    }
}