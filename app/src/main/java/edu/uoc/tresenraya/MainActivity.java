package edu.uoc.tresenraya;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Vamos a crear las variables en esta parte

    private Button[][] casillas = new Button[3][3];
    private Button resetbtn;


    private boolean jugador1turno = true;
    private int rondas;

    private int judador1puntitos = 0;

    private int jugador2puntitos = 0;

     private TextView textViewjugador1, textViewjugador2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//Referencias de los textViews lo primero que hacemos
         textViewjugador1=findViewById(R.id.jugador1puntos);
         textViewjugador2=findViewById(R.id.jugador2puntos);

        //Referencia del array de botones a los botones que tenemos como id btn_0,_1,_2....

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
//De esta manera de forma dinamica referenciamos todos los botones que haran de casillas
                String ButtonID = "btn_" + i + j;
                int resID = getResources().getIdentifier(ButtonID, "id", getPackageName());
                casillas[i][j] = findViewById(resID);
                //Añadimos el listener al pulsar implementamos el metodo .....
                casillas[i][j].setOnClickListener(this);

            }
        }

        resetbtn = findViewById(R.id.resetjuego);
        //Un nuevo onclicklistener para q sea diferente cuando pulsemos el btn de resetear ira aqui debajo.

        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetearTablero();

            }
        });
        
    }

    //Este será el método que se sobreescribe para ver que pasa cuando pulsamos a los botones o casillas....
    @Override
    public void onClick(View v) {

        //En este metodo decimos que si no esta vacia la casilla osea vacia de texto que vuelva no se......
        if(!((Button)v).getText().toString().equals("")){
            return;
        }

        //Si esta vacía la casilla vemos si es el turno del jugador 1 o no....
        if(jugador1turno){
            ((Button)v).setText("X");

        }else{
            ((Button)v).setText("O");

        }

        rondas++;

        if(checkforwin()) {

            if(jugador1turno) {
                jugador1gana();
            }else {
                jugador2gana();
            }
        }else if (rondas == 9) {
            empate();
        }else{
            //CAMBIAMOS DE TRUE A FALSE Y VICEVERSA SI NO HAY EMPATE NI GANADOR SI ESTRUE SERA PUESTO EN FALSE
           jugador1turno =! jugador1turno;

        }

    }


//----------------------------METODOS----------------------------------------------------------------------------------------
    private boolean checkforwin() {
        //En un array de String igual que los botones recorremos y metemos en ese array de string lo que haya en los botones
        String [][] field=new String[3][3];

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                
                field[i][j]=casillas[i][j].getText().toString();
            }
        }

        for(int i=0; i<3;i++)
          {
//compara las casillas al lado y lo ultimo que las tres casillas no esten vacias
            if (field[i][0].equals(field[i][1]) &&
                    field[i][0].equals(field[i][2]) && !field[i][0].equals("")) {
                return true;
            }
          }
//Columnas y filas de esta forma estan checkeadas

        for(int i=0; i<3;i++)
        {
            if(field[0][i].equals(field[1][i]) &&
                    field[0][i].equals(field[2][i]) && !field[0][i].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1]) &&
                field[0][0].equals(field[2][2]) && !field[0][0].equals("")){
            return true;
        }

        if(field[0][2].equals(field[1][1]) &&
                field[0][2].equals(field[2][0]) && !field[0][2].equals("")){
            return true;
            }
        return false;
    }

    private void jugador1gana()
    {
        judador1puntitos++;
        Toast.makeText(this, "Jugador 1 gana", Toast.LENGTH_SHORT).show();
        textViewjugador1.setText("Jugador1: "+judador1puntitos);
        resetearTablero();

    }
    private void jugador2gana(){
        jugador2puntitos++;
        Toast.makeText(this, "Jugador 2 gana", Toast.LENGTH_SHORT).show();
        textViewjugador2.setText("Jugador2: "+jugador2puntitos);
        resetearTablero();
    }
    private void empate(){

        Toast.makeText(this, "Empate", Toast.LENGTH_SHORT).show();
        resetearTablero();
    }

    private void resetearTablero(){

        for(int i=0;i<3;i++) {
            for(int j=0;j<3;j++){
                casillas[i][j].setText("");
            }
        }
         rondas = 0;
        jugador1turno = true;
    }
}
