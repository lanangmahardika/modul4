package com.dika.modul4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class
ViewActivity extends AppCompatActivity {
    TextView txtNamaLengkap;
    TextView txtTglSewa;
    TextView txtUmur;
    TextView txtGender;
    TextView txtPenyewaan;
    String namaLengkap;
    String tglSewa;
    String umur;
    String gender;
    String penyewaan;

    private DataHelper db;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        db = new DataHelper(this);
        userList = db.selectUserData();


        txtNamaLengkap = findViewById(R.id.isi_namalengkap);
        txtTglSewa = findViewById(R.id.isi_tgl_sewa);
        txtUmur = findViewById(R.id.isi_umur);
        txtGender = findViewById(R.id.isi_gender);
        txtPenyewaan = findViewById(R.id.isi_penyewaan);

        namaLengkap = userList.get(userList.size()-1).getNamaLengkap();
        tglSewa = userList.get(userList.size()-1).getTglSewa();
        umur = userList.get(userList.size()-1).getUmur();
        gender = userList.get(userList.size()-1).getGender();
        penyewaan = userList.get(userList.size()-1).getPenyewaan();

        txtNamaLengkap.setText(namaLengkap);
        txtTglSewa.setText(tglSewa);
        txtUmur.setText(umur);
        txtGender.setText(gender);
        txtPenyewaan.setText(penyewaan);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Menampilkan Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"Menjeda Activity", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Toast.makeText(this," Memulai Activity Kembali", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Menghancurkan activity", Toast.LENGTH_SHORT).show();
    }


}
