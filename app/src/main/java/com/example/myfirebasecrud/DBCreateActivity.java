package com.example.myfirebasecrud;

import static android.text.TextUtils.isEmpty;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBCreateActivity extends AppCompatActivity
{
    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;

    private Context context;
    // variable fields EditText dan Button
    private Button btSubmit;
    private EditText etEmail;
    private EditText etNama;
    private EditText etGetHari;
    private Spinner etHari;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbcreate);

        // inisialisasi fields EditText dan Button
        etEmail = (EditText) findViewById(R.id.email_peserta);
        etNama = (EditText) findViewById(R.id.nama_peserta);
        etGetHari = (EditText) findViewById(R.id.hr_peserta);
        etHari = (Spinner) findViewById(R.id.spinnerHari);
        btSubmit = (Button) findViewById(R.id.bt_submit);

        // mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        //Final Update
        final Peserta peserta = (Peserta) getIntent().getSerializableExtra("data");

        if (peserta != null) {
            //ini untuk update
            etEmail.setText(peserta.getEmail());
            etNama.setText(peserta.getNama());
            etGetHari.setText(peserta.getHari());
            etHari.getSelectedItem().toString();
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    peserta.setEmail(etEmail.getText().toString());
                    peserta.setNama(etNama.getText().toString());
                    peserta.setHari(etHari.getSelectedItem().toString());
                    updatePeserta(peserta);
                }
            });
        } else {
            //ini untuk input

            // kode yang dipanggil ketika tombol Submit diklik
            btSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!isEmpty(etEmail.getText().toString()) &&
                            !isEmpty(etNama.getText().toString()))
                        submitPeserta(new Peserta(etEmail.getText().toString(),
                                etNama.getText().toString(), etHari.getSelectedItem().toString()));
                    else
                        Snackbar.make(findViewById(R.id.bt_submit), "Data Peserta tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                    InputMethodManager imm = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(
                            etNama.getWindowToken(), 0);
                }
            });

        }
    }

    private void updatePeserta(Peserta peserta) {
            // Update Peserta
            database.child("pesertaEvent")
                    .child(peserta.getKey())
                    .setValue(peserta)
                    .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Snackbar.make(findViewById(R.id.bt_submit), "Data Updated Successfully", Snackbar.LENGTH_LONG).setAction("OKE", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                finish();
                            }
                        }).show();
                    }
        });
    }

    //fungsi Simpan data Peserta
    private void submitPeserta(Peserta peserta) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime
         Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */
        database.child("pesertaEvent").push().setValue(peserta ).addOnSuccessListener(this, new
                OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        etEmail.setText("");
                        etNama.setText("");
                        etHari.getSelectedItem().toString();
                        Snackbar.make(findViewById(R.id.bt_submit), "Data Added Successfully", Snackbar.LENGTH_LONG).show();
                    }
                });
    }

    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, DBCreateActivity.class);
    }
}

