package com.example.whafa;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static int ID_SELECTED_MEMBER;
    String TAG = "aldy ganteng";

    RecyclerView rvMember, rvPengeluaran;
    TextView tvHasil;
    EditText edtNominal;
    ArrayList<ItemMember> itemMembers;
    Button btnReset, btnPengeluaran, btnHitung, btnRibuan;
    MemberAdapter memberAdapter;
    PengeluaranAdapter pengeluaranAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        addMember();
        setRecyclerView();

    }

    private String addSeparator(long number) {
        return String.format("%,d", number);
    }

    private void initView() {
        itemMembers = new ArrayList<>();
        rvMember = findViewById(R.id.rvMember);
        rvPengeluaran = findViewById(R.id.rvPengeluaran);
        tvHasil = findViewById(R.id.tvHasil);
        edtNominal = findViewById(R.id.edtNominal);
        btnReset = findViewById(R.id.btnReset);
        btnPengeluaran = findViewById(R.id.btnAdd);
        btnHitung = findViewById(R.id.btnHitung);
        btnRibuan = findViewById(R.id.btnRibuan);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        btnPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBelanja();
            }
        });

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitungBill();
            }
        });

        btnRibuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String awal = edtNominal.getText().toString();
                awal += "000";

                edtNominal.setText(awal);
                edtNominal.setSelection(edtNominal.getText().length());
            }
        });
    }

    private void setRecyclerView(){
        memberAdapter = new MemberAdapter(this, itemMembers);
        rvMember.setAdapter(memberAdapter);
        rvMember.setLayoutManager(new LinearLayoutManager(this));

        pengeluaranAdapter = new PengeluaranAdapter(this, itemMembers);
        rvPengeluaran.setAdapter(pengeluaranAdapter);
        rvPengeluaran.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void addMember(){
        itemMembers.add(new ItemMember(0, "Aldy", true));
        itemMembers.add(new ItemMember(1, "Whafa", false));
    }

    private void reset(){
        for (int i = 0; i < itemMembers.size(); i++){
            ArrayList<Long> kosong = new ArrayList<>();
            itemMembers.get(i).setListBelanja(kosong);
            memberAdapter.notifyDataSetChanged();
        }
    }

    private void addBelanja(){
        if (edtNominal.getText().toString().matches("")){
            Toast.makeText(this, "bruh", Toast.LENGTH_SHORT).show();
            return;
        }

        long belanja = Long.parseLong(edtNominal.getText().toString());

        if (belanja == 0){
            Toast.makeText(this, "bruh", Toast.LENGTH_SHORT).show();
            return;
        }

        itemMembers.get(ID_SELECTED_MEMBER).addBelanja(belanja);
        memberAdapter.notifyDataSetChanged();
        edtNominal.setText("");
    }

    private void hitungBill(){
        //=======================================================================
        //hitung total bill semua orang
        for (ItemMember check : itemMembers){
            Log.e("aldy", "size list belanja " + check.getNama() + " : " + check.getListBelanja().size());
            if (check.getListBelanja().size() == 0) {
                tvHasil.setText("Rp 0");
                Toast.makeText(this, "bruh", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        long totalBill = 0;

        ArrayList<Long> listBill = new ArrayList<>();

        for (ItemMember a : itemMembers){
            long myBill = 0;

            for (Long b : a.getListBelanja()){
                myBill += b;
            }
            listBill.add(myBill);
        }

        for (Long bill : listBill){
            totalBill += bill;
        }

        long selisih = totalBill/itemMembers.size();

        String result = "Total Semua Rp " + addSeparator(totalBill);
        result += "\n=========================";

        //=======================================================================
        //hitung total bill per orang

        for (int j = 0; j < itemMembers.size(); j++){
            result += "\n" + itemMembers.get(j).getNama() + " " + addSeparator(itemMembers.get(j).getTotalBelanja());
        }

        result += "\n=========================";

        result += "\n Average " + addSeparator(selisih);

        result += "\n=========================";

        //=======================================================================
        //hitung hutang per orang

        for (ItemMember hutang : itemMembers){
            if (hutang.getTotalBelanja() < selisih){
                long bayar = hutang.getTotalBelanja() - selisih;

                result += "\n" + hutang.getNama() + " hutang " + addSeparator(Math.abs(bayar));
            }
        }

        tvHasil.setText(result);
    }
}