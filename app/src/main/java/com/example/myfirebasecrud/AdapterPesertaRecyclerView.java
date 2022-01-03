package com.example.myfirebasecrud;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
public class AdapterPesertaRecyclerView extends
        RecyclerView.Adapter<AdapterPesertaRecyclerView.ViewHolder>
{

    private ArrayList<Peserta> daftarpesertaEvent;
    private Context context;

    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;
       
        /**
         * Inisiasi data dan variabel yang akan digunakan
         */
    public AdapterPesertaRecyclerView(ArrayList<Peserta> pesertaEvent, Context ctx){
        daftarpesertaEvent = pesertaEvent;
        context = ctx;
    }

 /**
         * Inisiasi View
         * Di tutorial ini kita hanya menggunakan data String untuk tiap item
         * dan juga view nya hanyalah satu TextView
         */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail, tvHari;

        ViewHolder(View v) {
            super(v);
            tvEmail = (TextView) v.findViewById(R.id.tv_emailpeserta);
            tvHari = (TextView) v.findViewById(R.id.tv_haripeserta);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        /**
         *  Inisiasi ViewHolder
         */
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peserta,
                parent, false);
        // mengeset ukuran view, margin, padding, dan parameter layout lainnya
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        /**
         *  Menampilkan data pada view
         */
        final String email = daftarpesertaEvent.get(position).getEmail();
        final String name = daftarpesertaEvent.get(position).getNama();
        final String day = daftarpesertaEvent.get(position).getHari();
        holder.tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 *  Read detail data
                 */
            }
        });
        holder.tvEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //Update
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.dialogue_view);
                dialog.setTitle("Pilih Aksi");
                dialog.show();

                Button editButton=(Button)dialog.findViewById(R.id.bt_edit_data);
                Button delButton=(Button)dialog.findViewById(R.id.bt_delete_data);

                //aksi tombol edit di klik
                editButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                // Update Peserta
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("Ubah Data");
                                alert.setMessage("Anda yakin akan mengubah data?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        context.startActivity(DBCreateActivity.getActIntent((Activity) context).putExtra("data",
                                                daftarpesertaEvent.get(position)));
                                    }
                                });
                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }
                        }
                );

                //aksi buttondelete di klik
                delButton.setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                /**
                                 *  Delete data
                                 */
                                dialog.dismiss();
                                // Delete Peserta
                                database = FirebaseDatabase.getInstance().getReference();
                                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                alert.setTitle("DELETE DATA");
                                alert.setMessage("Are You Sure to Remove Data?");
                                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        database.child("pesertaEvent")
                                                .child(daftarpesertaEvent.get(position).getKey())
                                                .removeValue()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(context, "Data Removed Successfully", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                alert.show();
                            }
                        }
                );

                return true;
            }
        });
        holder.tvEmail.setText(email);
        holder.tvHari.setText(day);
    }

    @Override
    public int getItemCount() {
        /**
         * Mengembalikan jumlah item
         */
        return daftarpesertaEvent.size();
    }
}


