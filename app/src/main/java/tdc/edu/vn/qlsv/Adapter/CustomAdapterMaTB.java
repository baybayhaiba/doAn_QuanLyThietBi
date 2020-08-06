package tdc.edu.vn.qlsv.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tdc.edu.vn.qlsv.Model.LoaiThietBi;
import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.R;

public class CustomAdapterMaTB extends BaseAdapter implements Filterable {
    Activity activity;
    ArrayList<LoaiThietBi> loaiTB;
    ArrayList<LoaiThietBi> loaiTBAll;

    public CustomAdapterMaTB(Activity activity, ArrayList<LoaiThietBi> loaiTB){
        this.activity=activity;
        this.loaiTB=loaiTB;
        loaiTBAll=new ArrayList<>(loaiTB);
    }
    @Override
    public int getCount() {
        return loaiTB.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiTB.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=activity.getLayoutInflater();
        view=inflater.inflate(R.layout.list_custom_loaitb,null);
        TextView tvMaLoai=view.findViewById(R.id.custom_maTB);
        TextView tvTenLoai=view.findViewById(R.id.custom_loaiTB);
        tvMaLoai.setText("Mã Loại:"+loaiTB.get(i).getMaLoai());
        tvTenLoai.setText("Tên Loại:"+loaiTB.get(i).getTenLoai());
        return view;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<LoaiThietBi> filteredList=new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(loaiTBAll);
            }else{
                for(LoaiThietBi ltb:loaiTBAll){
                    if(ltb.getTenLoai().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(ltb);
                    }
                }
            }
            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }
        //run on ui thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            loaiTB.clear();
            loaiTB.addAll((Collection<? extends LoaiThietBi>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
