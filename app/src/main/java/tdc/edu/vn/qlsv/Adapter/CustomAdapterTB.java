package tdc.edu.vn.qlsv.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import java.util.Collections;
import java.util.List;

import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.R;


public class CustomAdapterTB extends RecyclerView.Adapter<CustomAdapterTB.MyViewHolder> implements Filterable {
    private int layoutID;
    private ArrayList<ThietBi> data;
    private ArrayList<ThietBi> dataAll;
    private Listener listener;


    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ThietBi> filteredList=new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(dataAll);
            }else{
                for(ThietBi tb:dataAll){
                    if(tb.getTenTB().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(tb);
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
            data.addAll((Collection<? extends ThietBi>) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public static interface Listener{
        public void onClick(int position);
    }

    public CustomAdapterTB(int layoutID, ArrayList<ThietBi> data) {
        this.layoutID = layoutID;
        this.data = data;
        this.dataAll=new ArrayList<>(data);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView infoImageTB;
        private ImageView info_xuatXu;
        private TextView info_maTB;
        private TextView info_tenTB;
        private TextView info_maLoai;
        private CardView cardView;

        public MyViewHolder(@NonNull CardView v) {
            super(v);
            infoImageTB=itemView.findViewById(R.id.info_imageThietBi);
            info_xuatXu=itemView.findViewById(R.id.info_xuatXuThietBi);
            info_maTB=itemView.findViewById(R.id.info_maTB);
            info_tenTB=itemView.findViewById(R.id.info_tenTB);
            info_maLoai=itemView.findViewById(R.id.info_loaiTB);
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
        ThietBi thietBi=data.get(i);
        if(thietBi.getXuatXuTB().equals("Việt Nam"))
            myViewHolder.info_xuatXu.setImageResource(R.drawable.vietnam);
        else if (thietBi.getXuatXuTB().equals("Mỹ"))
            myViewHolder.info_xuatXu.setImageResource(R.drawable.usa);
        else if(thietBi.getXuatXuTB().equals("Nhật"))
            myViewHolder.info_xuatXu.setImageResource(R.drawable.japan);
        else if(thietBi.getXuatXuTB().equals("Thái Lan"))
            myViewHolder.info_xuatXu.setImageResource(R.drawable.thailan);
        else
            myViewHolder.info_xuatXu.setImageResource(R.drawable.korean);

        myViewHolder.info_maLoai.setText("Mã loại:"+thietBi.getMaLoaiTB());
        myViewHolder.info_tenTB.setText("Tên Thiết Bị:"+thietBi.getTenTB());
        myViewHolder.info_maTB.setText("Mã thiết bị:"+thietBi.getMaTB());
        if(thietBi.getImageTB()==null)
            myViewHolder.infoImageTB.setImageResource(R.drawable.choosepicture);
        else
        {
            Bitmap bitmapToImage = BitmapFactory.decodeByteArray
                    (thietBi.getImageTB(), 0, thietBi.getImageTB().length);
            myViewHolder.infoImageTB.setImageBitmap(Bitmap.
                    createScaledBitmap(bitmapToImage,120,120,false));
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                    listener.onClick(myViewHolder.getAdapterPosition());
            }
        });

    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
