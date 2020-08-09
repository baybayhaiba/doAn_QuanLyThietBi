package tdc.edu.vn.qlsv.Adapter;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

import de.hdodenhof.circleimageview.CircleImageView;
import tdc.edu.vn.qlsv.Database.DataCTSD;
import tdc.edu.vn.qlsv.Database.DataThietBi;
import tdc.edu.vn.qlsv.GiaoDien.ActivityTinhTrang;
import tdc.edu.vn.qlsv.Model.ChiTietSuDung;
import tdc.edu.vn.qlsv.Model.PhongHoc;
import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.R;

public class CustomAdapterCTSD extends RecyclerView.Adapter<CustomAdapterCTSD.MyViewHolder> implements Filterable {
    private Context context;
    private int layoutId;
    private ArrayList<ChiTietSuDung> data;
    private ArrayList<ChiTietSuDung> dataAll;
    private ListenerCTSD listener;
    DataCTSD databaseCTSD;
    public static interface ListenerCTSD{
        public void onClick(int position);
    }

    public CustomAdapterCTSD(Context context, int layoutId, ArrayList<ChiTietSuDung> data) {
        this.context = context;
        this.layoutId = layoutId;
        this.data = data;
        this.dataAll = new ArrayList<>(data);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgCTSD;
        private TextView tvMaPhong;
        private TextView tvNgaySD;
        private TextView soLuongCTSD;
        private ImageView imgDetail;
        private CardView cardView;


        public MyViewHolder(@NonNull CardView v) {
            super(v);
            imgCTSD = itemView.findViewById(R.id.info_imageThietBiCTSD);
            tvMaPhong = itemView.findViewById(R.id.info_maPhongCTSD);
            tvNgaySD = itemView.findViewById(R.id.info_ngayCTSD);
            soLuongCTSD = itemView.findViewById(R.id.info_soLuong);
            imgDetail= itemView.findViewById(R.id.info_detail);
            cardView = v;
        }
    }

    @NonNull
    @Override
    public CustomAdapterCTSD.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        CardView cardView = (CardView) inflater.inflate(layoutId, viewGroup, false);
        return new MyViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomAdapterCTSD.MyViewHolder myViewHolder, int i) {
        final CardView cardView = myViewHolder.cardView;
        final ChiTietSuDung chiTietSuDung = data.get(i);
        databaseCTSD=new DataCTSD(context);

        final byte[] getImage=databaseCTSD.getImageThietBi(chiTietSuDung.getMaTB());
        if(getImage!=null)
        {
            Bitmap bitmapToImage = BitmapFactory.decodeByteArray
                    (getImage, 0, getImage.length);
            myViewHolder.imgCTSD.setImageBitmap(Bitmap.
                    createScaledBitmap(bitmapToImage,120,120,false));
        }
        else {
            myViewHolder.imgCTSD.setImageResource(R.drawable.choosepicture);
        }
        myViewHolder.tvMaPhong.setText("Mã Phòng:"+chiTietSuDung.getMaPhong());
        myViewHolder.tvNgaySD.setText("Ngày sử dụng:"+chiTietSuDung.getNgaySuDung());
//        if (chiTietSuDung.getSoLuong() < 10)
//            myViewHolder.soLuongCTSD.setBackgroundResource(R.drawable.background_white);
//
//        else if (chiTietSuDung.getSoLuong() < 20)
//            myViewHolder.soLuongCTSD.setBackgroundResource(R.drawable.background_yellow);
//        else
//            myViewHolder.soLuongCTSD.setBackgroundResource(R.drawable.background_red);
        myViewHolder.soLuongCTSD.setText("Số lượng:"+String.valueOf(chiTietSuDung.getSoLuong()));

        myViewHolder.imgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,ActivityTinhTrang.class);
                intent.putExtra("date",chiTietSuDung.getNgaySuDung());
                intent.putExtra("maTB",chiTietSuDung.getMaTB());
                intent.putExtra("maPhong",chiTietSuDung.getMaPhong());
                intent.putExtra("id",chiTietSuDung.getId());
                context.startActivity(intent);
            }
        });
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onClick(myViewHolder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter=new Filter() {
        //run on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<ChiTietSuDung> filteredList=new ArrayList<>();

            if(charSequence.toString().isEmpty()){
                filteredList.addAll(dataAll);
            }else{
                for(ChiTietSuDung tb:dataAll){
                    if(tb.getMaPhong().toLowerCase().contains(charSequence.toString().toLowerCase())){
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
            data.addAll((Collection<? extends ChiTietSuDung>) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public void setListener(ListenerCTSD listener) {
        this.listener = listener;
    }
}
