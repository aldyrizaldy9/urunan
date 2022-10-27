package com.example.whafa;

import java.util.ArrayList;

public class ItemMember {
    int id;
    String nama;
    ArrayList<Long> listBelanja = new ArrayList<>();
    long totalBelanja = 0;
    boolean selected;

//    public ItemMember(int id, String nama) {
//        this.id = id;
//        this.nama = nama;
//    }

    public ItemMember(int id, String nama, boolean selected) {
        this.id = id;
        this.nama = nama;
        this.selected = selected;
    }

    public void addBelanja(long belanja){
        listBelanja.add(belanja);

        totalBelanja += belanja;
    }

    public void removeLastBelanja(){
        listBelanja.remove(listBelanja.size()-1);

        totalBelanja = 0;
        for (Long a : listBelanja){
            totalBelanja += a;
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public ArrayList<Long> getListBelanja() {
        return listBelanja;
    }

    public void setListBelanja(ArrayList<Long> listBelanja) {
        this.listBelanja = listBelanja;
    }

    public long getTotalBelanja() {
        return totalBelanja;
    }

    public void setTotalBelanja(long totalBelanja) {
        this.totalBelanja = totalBelanja;
    }
}
