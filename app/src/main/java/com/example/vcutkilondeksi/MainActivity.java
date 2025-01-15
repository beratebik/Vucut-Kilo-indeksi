package com.example.vcutkilondeksi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextBoy, editTextKilo, editTextYas;
    private RadioGroup radioGroupCinsiyet;
    private TextView textViewSonuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextBoy = findViewById(R.id.editTextBoy);
        editTextKilo = findViewById(R.id.editTextKilo);
        editTextYas = findViewById(R.id.editTextYas);
        radioGroupCinsiyet = findViewById(R.id.radioGroupCinsiyet);
        textViewSonuc = findViewById(R.id.textViewSonuc);
        Button buttonHesapla = findViewById(R.id.buttonHesapla);

        buttonHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hesapla();
            }
        });
    }

    private void hesapla() {
        String boyStr = editTextBoy.getText().toString();
        String kiloStr = editTextKilo.getText().toString();
        String yasStr = editTextYas.getText().toString();

        // Cinsiyet kontrolü
        int selectedCinsiyetId = radioGroupCinsiyet.getCheckedRadioButtonId();
        if (selectedCinsiyetId == -1) {
            textViewSonuc.setVisibility(View.VISIBLE);
            textViewSonuc.setText("Lütfen cinsiyet seçin!");
            return;
        }

        RadioButton selectedCinsiyet = findViewById(selectedCinsiyetId);
        String cinsiyet = selectedCinsiyet.getText().toString();

        if (!boyStr.isEmpty() && !kiloStr.isEmpty() && !yasStr.isEmpty()) {
            double boy = Double.parseDouble(boyStr);
            double kilo = Double.parseDouble(kiloStr);
            int yas = Integer.parseInt(yasStr);

            // VKİ Hesaplama
            double vki = kilo / Math.pow(boy / 100, 2);

            // BMR Hesaplama(metabolizma hızı)
            double bmr;
            if (cinsiyet.equals("Kadın")) {
                bmr = (10 * kilo) + (6.25 * boy) - (5 * yas) - 161;
            } else { // Erkek
                bmr = (10 * kilo) + (6.25 * boy) - (5 * yas) + 5;
            }

            // Günlük Kalori İhtiyacı (örnek: orta aktif)
            double kaloriIhtiyaci = bmr * 1.55;

            // Durum
            String durum;
            if (vki < 18.5) {
                durum = "Zayıf";
            } else if (vki < 24.9) {
                durum = "Normal";
            } else if (vki < 29.9) {
                durum = "Kilolu";
            } else {
                durum = "Obez";
            }

            textViewSonuc.setVisibility(View.VISIBLE);
            textViewSonuc.setText(String.format(
                    "Cinsiyet: %s\nVKI: %.2f\nDurum: %s\nGünlük Kalori İhtiyacı: %.0f kcal",
                    cinsiyet, vki, durum, kaloriIhtiyaci
            ));
        } else {
            textViewSonuc.setVisibility(View.VISIBLE);
            textViewSonuc.setText("Lütfen tüm alanları doldurun!");
        }
    }
}