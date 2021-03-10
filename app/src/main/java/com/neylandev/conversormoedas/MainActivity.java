package com.neylandev.conversormoedas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.neylandev.conversormoedas.model.Moedas;
import com.neylandev.conversormoedas.service.HTTPService;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {
    private ViewHolder mViewHolder = new ViewHolder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mViewHolder.editValue = findViewById(R.id.edit_value);
        this.mViewHolder.textDolar = findViewById(R.id.text_dolar);
        this.mViewHolder.textEuro = findViewById(R.id.text_euro);
        this.mViewHolder.buttonCalculate = findViewById(R.id.button_calculate);

        this.mViewHolder.buttonCalculate.setOnClickListener(this);
        this.mViewHolder.editValue.setOnFocusChangeListener(this);
        this.clearValues();
    }

    private void clearValues() {
        this.mViewHolder.textDolar.setText("");
        this.mViewHolder.textEuro.setText("");
    }

    @Override
    public void onClick(View v) {
        Moedas moedas = new Moedas();
        if(v.getId() == R.id.button_calculate){
            String value = this.mViewHolder.editValue.getText().toString();
            if("".equals(value)){
                // Mostra mensagem pro usuário
                Toast.makeText(this, this.getString(R.string.informe_valor), Toast.LENGTH_LONG).show();
            } else {
                Double real = Double.valueOf(value);
                HTTPService service = new HTTPService();
                try {
                    moedas = service.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.mViewHolder.textDolar.setText(String.format("%.2f",(real/moedas.getDolar()))+" $");
                this.mViewHolder.textEuro.setText(String.format("%.2f",(real/moedas.getEuro()))+" €");
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(v.getId() == R.id.edit_value){
            this.mViewHolder.editValue.setText("");
        }
    }

    private static class ViewHolder {
        EditText editValue;
        TextView textDolar;
        TextView textEuro;
        Button buttonCalculate;
    }
}