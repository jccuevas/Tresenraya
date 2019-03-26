package com.example.tresenraya;

import android.media.Image;
import android.os.Build;
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
    private boolean turno = true;
    private int pulsadas = 0;

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
            if (ganador() == 0) {
                if (tablero[celda] == 0) {
                    if (turno) {
                        tablero[celda] = 1;
                        casilla.setImageResource(dameFicha(turno));
                    } else {
                        casilla.setImageResource(dameFicha(turno));
                        tablero[celda] = -1;
                    }
                    turno = !turno;
                    pulsadas++;
                    int gana = ganador();
                    jugadorGanador(ganador());
                    if (gana != 0) {
                        return;

                    }
                } else {
                    Toast.makeText(this, R.string.ocupada, Toast.LENGTH_SHORT).show();
                }


                if (pulsadas == 9) {
                    jugadorGanador(ganador());
                    return;
                }
                jugador(turno);
            } else {
                Toast.makeText(this, "Comience una nueva partida", Toast.LENGTH_SHORT).show();
            }
        } catch (NumberFormatException nex) {
            nex.printStackTrace();
        }
    }

    private void resetea() {
        pulsadas = 0;
        for (int i = 0; i < tablero.length; i++) {
            tablero[i] = 0;
            ImageView casilla = findViewById(casillas[i]);
            casilla.setImageResource(R.drawable.ic_casilla);
            casilla.setBackgroundColor(0xFFFFFFFF);

        }
        turno = true;
        jugador(turno);
    }

    private int ganador() {
        int valor = 0;
        int resultado = 0;
        int a = 0, b = 0, c = 0;

        //Horizontales

        valor = tablero[0] + tablero[1] + tablero[2];

        if (valor == 3) {
            resultado = 1;
            a=0;b=1;c=2;
        }
        if (valor == -3) {
            resultado = -1;
            a=0;b=1;c=2;
        }

        valor = tablero[3] + tablero[4] + tablero[5];
        if (valor == 3) {
            resultado = 1;
            a=3;b=4;c=5;
        }
        if (valor == -3) {
            a=3;b=4;c=5;
            resultado = -1;
        }


        valor = tablero[6] + tablero[7] + tablero[8];

        if (valor == 3) {
            a=6;b=7;c=8;
            resultado = 1;
        }
        if (valor == -3) {
            a=6;b=7;c=8;
            resultado = -1;
        }

        //Verticales
        valor = tablero[0] + tablero[3] + tablero[6];
        if (valor == 3) {
            a=0;b=3;c=6;
            resultado = 1;
        }
        if (valor == -3) {
            a=0;b=3;c=6;
            resultado = -1;
        }

        valor = tablero[1] + tablero[4] + tablero[7];
        if (valor == 3) {
            a=1;b=4;c=7;
            resultado = 1;
        }
        if (valor == -3) {
            a=1;b=4;c=7;
            resultado = -1;
        }

        valor = tablero[2] + tablero[5] + tablero[8];
        if (valor == 3) {
            a=2;b=5;c=8;
            resultado = 1;
        }
        if (valor == -3) {
            a=2;b=5;c=8;
            resultado = -1;
        }

        //Diagonales
        valor = tablero[0] + tablero[4] + tablero[8];
        if (valor == 3) {
            a=0;b=4;c=8;
            resultado = 1;
        }
        if (valor == -3) {
            a=0;b=4;c=8;
            resultado = -1;
        }

        valor = tablero[2] + tablero[4] + tablero[6];
        if (valor == 3) {
            a=2;b=4;c=6;
            resultado = 1;
        }
        if (valor == -3) {
            a=2;b=4;c=6;
            resultado = -1;
        }

        if (resultado != 0)
            pintaGanador(a, b, c);

        return resultado;
    }

    private void pintaGanador(int a, int b, int c) {
        findViewById(casillas[a]).setBackgroundColor(0x7700FF00);
        findViewById(casillas[b]).setBackgroundColor(0x7700FF00);
        findViewById(casillas[c]).setBackgroundColor(0x7700FF00);

    }

    private void jugador(boolean turno) {
        TextView jugador = findViewById(R.id.jugador);
        jugador.setText(turno ? "Jugador A" : "Jugador B");

        ImageView ficha = findViewById(R.id.ficha);
        ficha.setImageResource(dameFicha(turno));
    }

    private int dameFicha(boolean turno) {
        return turno ? R.drawable.ic_cruz : R.drawable.ic_circulo;
    }

    private void jugadorGanador(int ganador) {
        TextView jugador = findViewById(R.id.jugador);
        if (ganador == 1) {
            jugador.setText("Gana el jugador A");

        } else if (ganador == -1) {
            jugador.setText("Gana el jugador B");
        } else
            jugador.setText("Empate");
    }
}
