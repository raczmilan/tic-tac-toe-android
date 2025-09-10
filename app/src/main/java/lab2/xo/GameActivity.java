package lab2.xo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class GameActivity extends AppCompatActivity {
    private String player1;
    private String player2;
    private int currentPlayer = 1;
    private final int[][] grid = new int[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(myToolbar);

        View view = getWindow().getDecorView();
        SharedPreferences prefs = getSharedPreferences("XOPrefs", MODE_PRIVATE);
        int color = prefs.getInt("pickedColor", Color.WHITE);
        view.setBackgroundColor(color);

        Intent intent = getIntent();
        String[] message = intent.getStringArrayExtra(MainActivity.EXTRA_MESSAGE);
        int a = (int)Math.round(Math.random());
        assert message != null;
        this.player1 = message[a];
        this.player2 = message[1 - a];

        String msg = player1 + "'s turn";
        TextView textView = (TextView) findViewById(R.id.show_state);
        textView.setText(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toolbar_item) {
            int initialColor = Color.WHITE;

            ColorPickerDialog colorPickerDialog = new ColorPickerDialog(this, initialColor, new ColorPickerDialog.OnColorSelectedListener() {
                @Override
                public void onColorSelected(int color) {
                    View view = getWindow().getDecorView();
                    view.setBackgroundColor(color);
                    SharedPreferences prefs = getSharedPreferences("XOPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putInt("pickedColor", color);
                    editor.apply();
                }
            });
            colorPickerDialog.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void showColorPickerDialog(View view) {
    }

    private void showToast(int color) {
        String rgbString = "R: " + Color.red(color) + " B: " + Color.blue(color) + " G: " + Color.green(color);
        Toast.makeText(this, rgbString, Toast.LENGTH_SHORT).show();
    }

    public void buttonPressed(View view){
        String buttonText;
        boolean gameOver = true;
        boolean winnerExists = false;
        String message;

        String name = view.getResources().getResourceName(view.getId());
        int position = Integer.parseInt(name.substring(name.length()-1));
        int i = (position-1)/3;
        int j = (position-1)%3;

        if(currentPlayer == 1){
            buttonText = "X";
            grid[i][j] = 1;
            currentPlayer = 2;
            message = player2 + "'s turn";
        } else {
            buttonText = "0";
            grid[i][j] = 2;
            currentPlayer = 1;
            message = player1 + "'s turn";
        }

        Button button = (Button) view;
        button.setText(buttonText);
        button.setTextSize(30);

        if(grid[i][0] == grid[i][1] && grid[i][0] == grid[i][2] && grid[i][0] != 0
            || grid[0][j] == grid[1][j] && grid[0][j] == grid[2][j] && grid[0][j] != 0
            || grid[0][0] == grid[1][1] && grid[0][0] == grid[2][2] && grid[0][0] != 0
            || grid[0][2] == grid[1][1] && grid[0][2] == grid[2][0] && grid[0][2] != 0){

            winnerExists = true;

            if(currentPlayer == 1){
                message = player2 + " won!";
            } else {
                message = player1 + " won!";
            }
        }

        if(winnerExists){
            findViewById(R.id.button1).setEnabled(false);
            findViewById(R.id.button2).setEnabled(false);
            findViewById(R.id.button3).setEnabled(false);
            findViewById(R.id.button4).setEnabled(false);
            findViewById(R.id.button5).setEnabled(false);
            findViewById(R.id.button6).setEnabled(false);
            findViewById(R.id.button7).setEnabled(false);
            findViewById(R.id.button8).setEnabled(false);
            findViewById(R.id.button9).setEnabled(false);
        } else {
            for (int a = 0; a < 3; a++) {
                for (int b = 0; b < 3; b++) {
                    if (grid[a][b] == 0) {
                        gameOver = false;
                        break;
                    }
                }
                if (!gameOver) {
                    break;
                }
            }

            if(gameOver){
                message = "Game Over - no winner";
            }
        }

        TextView textView = (TextView) findViewById(R.id.show_state);
        textView.setText(message);
        view.setEnabled(false);
    }
}