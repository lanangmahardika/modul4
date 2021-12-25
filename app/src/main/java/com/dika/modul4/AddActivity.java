package com.dika.modul4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private EditText txtNamaLengkap;
    private String namaLengkap;
    private EditText inputtglsewa;
    private String tglSewa;
    private SeekBar seekBar;
    private TextView lbl_umur;
    private String nilaiUmur;
    private String umur;
    private Button btnDaftar;
    private RadioGroup radioGroup;
    private String gender;
    private CheckBox mPancingFullset, mTasPancing, m1SetKailpancing, mJoranPancing, mReelPancing, mLainnya;
    private RadioButton rblaki;
    private RadioButton rbperempuan;
    private String penyewaan;

    private DataHelper db;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);



        txtNamaLengkap = findViewById(R.id.input_namalengkap);
        inputtglsewa = findViewById(R.id.input_tgl_sewa);
        seekBar = findViewById(R.id.seekbar);
        lbl_umur = findViewById(R.id.lbl_umur);
        btnDaftar = findViewById(R.id.daftar);
        radioGroup = findViewById(R.id.radioGroupGender);
        rblaki = findViewById(R.id.laki_laki);
        rbperempuan = findViewById(R.id.perempuan);
        mPancingFullset = findViewById(R.id.pancing_fullset);
        mTasPancing = findViewById(R.id.tas_pancing);
        m1SetKailpancing = findViewById(R.id.kail_pancing);
        mJoranPancing = findViewById(R.id.joran_pancing);
        mReelPancing = findViewById(R.id.reel_pancing);
        mLainnya = findViewById(R.id.lainnya);

        db = new DataHelper(this);



        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        inputtglsewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = day+"/"+month+"/"+year;
                        inputtglsewa.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                nilaiUmur = String.valueOf(i);
                lbl_umur.setText("Umur : " + nilaiUmur);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                namaLengkap = txtNamaLengkap.getText().toString();
                tglSewa = inputtglsewa.getText().toString();
                umur = nilaiUmur;
                gender = getGenderSelected();
                penyewaan = getPenyewaanSelected();


                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(AddActivity.this);
                builder.setIcon(R.drawable.warning);
                builder.setTitle("Daftarkan");
                builder.setMessage(
                        "Apakah anda sudah yakin dengan data anda ?\n\n" +
                                "Nama Lengkap : \n" + namaLengkap + "\n\n" +
                                "Tanggal Sewa : \n" + tglSewa + "\n\n" +
                                "Umur : \n" + umur + "\n\n" +
                                "Gender :\n" + gender + "\n\n" +
                                "Penyewaan Yang di Pilih :\n" + penyewaan + ""
                );

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Data anda berhasil terdaftarkan !", Toast.LENGTH_SHORT).show();

                        Intent layout2 = new Intent(AddActivity.this, MainActivity.class);

                        user = new User();
                        user.setNamaLengkap(namaLengkap);
                        user.setTglSewa(tglSewa);
                        user.setUmur(umur);
                        user.setGender(gender);
                        user.setPenyewaan(penyewaan);

                        db.insert(user);

                        startActivity(layout2);
                        finish();

                    }
                });

                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });


    }
    private String getPenyewaanSelected(){
        String penyewaan = "";

        if (mPancingFullset.isChecked()) {
            penyewaan += mPancingFullset.getText().toString() + "\n";
        }
        if (mTasPancing.isChecked()) {
            penyewaan += mTasPancing.getText().toString() + "\n";
        }
        if (m1SetKailpancing.isChecked()) {
            penyewaan += m1SetKailpancing.getText().toString() + "\n";
        }
        if (mJoranPancing.isChecked()) {
            penyewaan += mJoranPancing.getText().toString() + "\n";
        }
        if (mReelPancing.isChecked()) {
            penyewaan += mReelPancing.getText().toString() + "\n";
        }
        if (mLainnya.isChecked()) {
            penyewaan += mLainnya.getText().toString() + "\n";
        }

        return penyewaan;

    }

    private String getGenderSelected(){
        String gender = "";

        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == rblaki.getId()){
            gender = "Laki-Laki";
        }
        else if (selectedId == rbperempuan.getId()){
            gender = "Perempuan";
        }

        return gender;
    }

}