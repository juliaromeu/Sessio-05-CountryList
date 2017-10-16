package edu.upc.eseiaat.pma.countrylist;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class CountryListActivity extends AppCompatActivity {

    //ArrayAdapter = Adaptador de la lista más larga que lo que muestra la pantalla
    private ArrayAdapter<String> adapter;

    //ArrayList = Contenedor secuencial
    private ArrayList<String> country_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_list);

        String[] countries = getResources().getStringArray(R.array.countries);

        //Conversión de countries a country_list
        //ArrayList convierte una tabla en una lista
        country_list = new ArrayList<>(Arrays.asList(countries));

        ListView list = (ListView) findViewById(R.id.country_list);

        //Todos los ListViews tienen un adaptador, hace de intermidiario
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, country_list);
        list.setAdapter(adapter);

        //Normal-Click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int pos, long id) {
                Toast.makeText(
                        CountryListActivity.this,
                        String.format(("You choose '%s'"),country_list.get(pos)),
                        Toast.LENGTH_SHORT)
                        .show();
        }
        });

        //Long-Click --> Borrar
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View item, final int pos, long id) {
                //Cuadro de diálogo previo para confirmar que realmente queremos eliminar el país
                AlertDialog.Builder builder = new AlertDialog.Builder(CountryListActivity.this);
                builder.setTitle(R.string.confirmation);
                String msg = getResources().getString(R.string.confirm_message);
                builder.setMessage(msg + " " + country_list.get(pos)+ "?");
                //Botón positivo
                builder.setPositiveButton(R.string.erase, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        country_list.remove(pos);
                        //Avisar que han cambiado los datos (se ha eliminado un país)
                        adapter.notifyDataSetChanged();
                    }
                });
                //Botón negativo
                //Android ya tiene un botón de cancelación en todos los idiomas, "null" es porque no necesitamos Listener
                builder.setNegativeButton(android.R.string.cancel, null);
                builder.create().show();

                //(true)No hacer el click normal porque se ha producido un long click
                return true;
            }
        });



    }
}
