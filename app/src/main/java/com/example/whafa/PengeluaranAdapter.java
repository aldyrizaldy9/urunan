package com.example.whafa;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PengeluaranAdapter extends RecyclerView.Adapter<PengeluaranAdapter.ViewHolder> {
    Context context;
    ArrayList<ItemMember> itemMembers;

    public PengeluaranAdapter(Context context, ArrayList<ItemMember> itemMembers) {
        this.context = context;
        this.itemMembers = itemMembers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_pengeluaran, parent, false);
        return new PengeluaranAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int posisi = position;

        holder.btnNama.setText(itemMembers.get(posisi).getNama());

        boolean selected = itemMembers.get(posisi).isSelected();
        if (selected) {
            MainActivity.ID_SELECTED_MEMBER = posisi;
            holder.btnNama.setBackgroundColor(Color.parseColor("#57f542"));
        } else {
            holder.btnNama.setBackgroundColor(Color.parseColor("#f57b42"));
        }

        holder.btnNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                itemMembers.get(posisi).setSelected(true);

                //ngeset semua selain ini jadi false
                for (int i = 0; i < itemMembers.size(); i++){
                    if (i == posisi){
                        itemMembers.get(i).setSelected(true);
                        MainActivity.ID_SELECTED_MEMBER = i;
                    } else {
                        itemMembers.get(i).setSelected(false);
                    }
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemMembers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button btnNama;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btnNama = itemView.findViewById(R.id.btn_c_namapengeluaran);
        }
    }
}
