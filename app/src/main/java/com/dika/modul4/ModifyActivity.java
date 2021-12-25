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

public class
ModifyActivity extends AppCompatActivity {
    private EditText edtNamaLengkap;
    private String namaLengkap;
    private EditText inputtglsewa;
    private String tglSewa;
    private SeekBar seekBar;
    private TextView lbl_umur;
    private String umur;
    private Button btnUbah;
    private RadioGroup radioGroup;
    private String gender;
    private RadioButton rblaki;
    private RadioButton rbperempuan;
    private CheckBox mPancingFullset, mTasPancing, m1SetKailpancing, mJoranPancing, mReelPancing, mLainnya;
    private String mResult = "";
    private String penyewaan;
    private int id;

    private DataHelper db;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        edtNamaLengkap = findViewById(R.id.input_namalengkap);
        inputtglsewa = findViewById(R.id.input_tgl_sewa);
        seekBar = findViewById(R.id.seekbar);
        lbl_umur = findViewById(R.id.lbl_umur);
        btnUbah = findViewById(R.id.ubah);
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

        Intent intent = getIntent();
        id = intent.getExtras().getInt("id");
        namaLengkap = intent.getExtras().getString("namaLengkap");
        tglSewa = intent.getExtras().getString("tglSewa");
        umur = intent.getExtras().getString("umur");
        gender = intent.getExtras().getString("gender");
        penyewaan = intent.getExtras().getString("penyewaan");

        edtNamaLengkap.setText(namaLengkap);
        inputtglsewa.setText(tglSewa);
        lbl_umur.setText("Umur : " + umur);
        setGenderSelected();
        setPenyewaanSelected();
        seekBar.setProgress(Integer.parseInt(umur));

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);


        inputtglsewa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        ModifyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month +1;
                        String date = day+"/"+month+"/"+year;
                        inputtglsewa.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                umur = String.valueOf(i);
                lbl_umur.setText("Umur : " + umur);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaLengkap = edtNamaLengkap.getText().toString().trim();
                tglSewa = inputtglsewa.getText().toString().trim();
                gender = getGenderSelected();
                penyewaan = getPenyewaanSelected();

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(ModifyActivity.this);
                builder.setIcon(R.drawable.warning);
                builder.setTitle("Daftarkan");
                builder.setMessage(
                        "Apakah anda sudah yakin dengan data anda ?\n\n" +
                                "Nama Lengkap : \n" + namaLengkap + "\n\n" +
                                "Tanggal Sewa : \n" + tglSewa + "\n\n" +
                                "Umur : \n" + umur + "\n\n" +
                                "Gender :\n" + gender + "\n\n" +
                                "Penyewaan Yang di Pilih :\n" +penyewaan + ""
                );

                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getApplicationContext(), "Data anda berhasil terdaftarkan !", Toast.LENGTH_SHORT).show();

                        Intent layout2 = new Intent(ModifyActivity.this, MainActivity.class);

                        user = new User();
                        user.setId(id);
                        user.setNamaLengkap(namaLengkap);
                        user.setTglSewa(tglSewa);
                        user.setUmur(umur);
                        user.setGender(gender);
                        user.setPenyewaan(penyewaan);

                        db.update(user);

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

    private void setPenyewaanSelected() {
        if (penyewaan.contains("Pancing Fullset")) {
            mPancingFullset.setChecked(true);
        }
        if (penyewaan.contains("Tas Pancing")) {
            mTasPancing.setChecked(true);
        }
        if (penyewaan.contains("1 Set Kail Pancing")) {
            m1SetKailpancing.setChecked(true);
        }
        if (penyewaan.contains("Joran Pancing")) {
            mJoranPancing.setChecked(true);
        }
        if (penyewaan.contains("Reel Pancing")) {
            mReelPancing.setChecked(true);
        }
        if (penyewaan.contains("Lainnya")) {
            mLainnya.setChecked(true);
        }
    }

    private String getPenyewaanSelected(){
        String penyewaan ="";

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


    private void setGenderSelected(){
        if (gender.equals("Laki-Laki")) {
            rblaki.setChecked(true);
        } else if (gender.equals("Perempuan")){
            rbperempuan.setChecked(true);
        }
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
