package com.example.tresenraya;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean turno = false;
    private int pulsadas =0;

    private int[] tablero = new int[9];
    private int[] casillas = new int[]{R.id.casilla01, R.id.casilla02, R.id.casilla03, R.id.casilla04, R.id.casilla05, R.id.casilla06, R.id.casilla07, R.id.casilla08, R.id.casilla09};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetea();
            }
        });

        jugador(turno);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public void ponFicha(View view) {
        try {
            int celda = Integer.parseInt((String) view.getTag()) - 1;

            Log.d("pulsada", (String) view.getTag());
            ImageView casilla = (ImageView) view;
            if (tablero[celda] == 0 && ganador()==0) {
                if (turno) {
                    tablero[celda] = 1;
                    casilla.setImageResource(R.drawable.ic_circulo);
                } else {
                    casilla.setImageResource(R.drawable.ic_cruz);
                    tablero[celda] = -1;
                }
                turno = !turno;
                pulsadas++;
                int gana=ganador();
                if(gana!=0)
                {
                    String ganador="";
                    if(gana==1) {
                        ganador="Jugador B";
                    }else{
                        ganador="Jugador A";
                    }

                    Toast.makeText(this,"Ganador es el "+ganador,Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(this, R.string.ocupada,Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException nex) {
            nex.printStackTrace();
        }

        if(pulsadas==9)
            ganador();

        jugador(turno);
    }

    private void resetea() {
        pulsadas=0;
        for (int i = 0; i < tablero.length; i++) {
            tablero[i] = 0;
            ImageView casilla = findViewById(casillas[i]);
            casilla.setImageResource(R.drawable.ic_casilla);
        }
    }

    private int ganador() {
        int valor=0;
        //Horizontales
        valor=tablero[0]+tablero[1]+tablero[2];
        if(valor ==3)
            return 1;
        if(valor==-3)
            return -1;
        valor=tablero[3]+tablero[4]+tablero[5];
        if(valor ==3)
            return 1;
        if(valor==-3)
            return -1;

        valor=tablero[6]+tablero[7]+tablero[8];
        if(valor ==3)
            return 1;
        if(valor==-3)
            return -1;

        //Verticales
        valor=tablero[0]+tablero[3]+tablero[6];
        if(valor ==3)
            return 1;
        if(valor==-3)
            return -1;

        valor=tablero[1]+tablero[4]+tablero[7];
        if(valor ==3)
            return 1;
        if(valor==-3)
            return -1;

        valor=tablero[2]+tablero[5]+tablero[8];
        if(valor ==3)
            return 1;
        if(valor==-3)
            return -1;

        //Diagonales
        valor=tablero[0]+tablero[4]+tablero[8];
        if(valor ==3)
            return 1;
        if(valor==-3)
            return -1;

        valor=tablero[2]+tablero[4]+tablero[6];
        if(valor ==3)
            return 1;
        if(valor==-3)
            return -1;
        return 0;
    }

    private void jugador(boolean turno){
        TextView jugador = findViewById(R.id.jugador);
        jugador.setText(turno?"Jugador B":"Jugador A");
    }
}
