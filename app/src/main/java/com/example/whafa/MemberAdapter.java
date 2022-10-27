package com.example.whafa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    Context context;
    ArrayList<ItemMember> itemList;

    public MemberAdapter(Context context, ArrayList<ItemMember> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_member, parent, false);
        return new MemberAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemMember itemMember = itemList.get(position);
        int posisi = position;

        ArrayList<Long> listBelanja = itemMember.getListBelanja();

        holder.tvNama.setText("Pengeluaran " + itemMember.getNama());

        holder.btnUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listBelanja.size() == 0){
                    Toast.makeText(context, "bruh", Toast.LENGTH_SHORT).show();
                    return;
                }

                itemList.get(posisi).removeLastBelanja();
                notifyDataSetChanged();

                holder.tvList.setText(setTextListBelanja(listBelanja));
            }
        });

        holder.tvList.setText(setTextListBelanja(listBelanja));
    }

    private String setTextListBelanja(ArrayList<Long> listBelanja){
        String result = "";

        if (listBelanja.size() == 0){
            return "- isi dulu dong";
        }

        for (int i = 0; i < listBelanja.size(); i++){
            if (i == 0){
                result += "- " + addSeparator(listBelanja.get(i));
            } else {
                result += "\n- " + addSeparator(listBelanja.get(i));
            }
        }
        return result;
    }

    private String addSeparator(long number) {
        return String.format("%,d", number);
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvList;
        Button btnUndo;

        public ViewHolder(@NonNull View v) {
            super(v);

            tvNama = v.findViewById(R.id.tv_c_nama);
            tvList = v.findViewById(R.id.tv_c_listbelanja);
            btnUndo = v.findViewById(R.id.btn_c_undo);
        }
    }
}
