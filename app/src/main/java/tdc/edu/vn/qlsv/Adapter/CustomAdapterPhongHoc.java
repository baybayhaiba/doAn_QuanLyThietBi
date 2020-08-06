package tdc.edu.vn.qlsv.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

import tdc.edu.vn.qlsv.Model.Main;
import tdc.edu.vn.qlsv.Model.PhongHoc;
import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.R;

public class CustomAdapterPhongHoc extends RecyclerView.Adapter<CustomAdapterPhongHoc.MyViewHolder> implements Filterable {


    private int layoutID;
    private ArrayList<PhongHoc> data;
    private ArrayList<PhongHoc> dataAll;
    private CustomAdapterPhongHoc.ListenerPhongHoc listener;
    public static interface ListenerPhongHoc{
        public void onClick(int position);
    }


    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<PhongHoc> filteredList=new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(dataAll);
            }else{
                for(PhongHoc phongHoc:dataAll){
                    if(phongHoc.getMaPhong().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(phongHoc);
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
            data.clear();
            data.addAll((Collection<? extends PhongHoc>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public CustomAdapterPhongHoc(int layoutID, ArrayList<PhongHoc> data) {
        this.layoutID = layoutID;
        this.data = data;
        this.dataAll=new ArrayList<>(data);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvMaPhong;
        private TextView tvLoaiPhong;
        private TextView tvTangPhong;
        private CardView cardView;

        public MyViewHolder(@NonNull CardView v) {
            super(v);
            tvMaPhong=(TextView)v.findViewById(R.id.custom_maPhong);
            tvLoaiPhong=(TextView)v.findViewById(R.id.custom_tenPhong);
            tvTangPhong=(TextView)v.findViewById(R.id.custom_tangPhong);
            cardView=v;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        CardView cardView= (CardView) inflater.inflate(layoutID,viewGroup,false);
        return new MyViewHolder(cardView);
    }



    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        final CardView cardView=myViewHolder.cardView;
        PhongHoc phongHoc=data.get(i);
        myViewHolder.tvMaPhong.setText(phongHoc.getMaPhong());
        myViewHolder.tvLoaiPhong.setText(phongHoc.getLoaiPhong());
        myViewHolder.tvTangPhong.setText(String.valueOf(phongHoc.getTang()));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                    listener.onClick(myViewHolder.getAdapterPosition());
            }
        });

    }

    public void setListener(CustomAdapterPhongHoc.ListenerPhongHoc listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
