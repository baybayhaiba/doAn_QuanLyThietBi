package tdc.edu.vn.qlsv.Adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlythietbi.R;

import java.util.ArrayList;
import java.util.Vector;

import tdc.edu.vn.qltb.Class.ThietBi;

public class CustomAdapterTB extends RecyclerView.Adapter<CustomAdapterTB.MyViewHolder> {
    private int layoutID;
    private ArrayList<ThietBi> data;
    private Listener listener;
    public static interface Listener{
        public void onClick(int position);
    }

    public CustomAdapterTB(int layoutID, ArrayList<ThietBi> data) {
        this.layoutID = layoutID;
        this.data = data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView info_xuatXu;
        private TextView info_maTB;
        private TextView info_tenTB;
        private TextView info_maLoai;
        private CardView cardView;

        public MyViewHolder(@NonNull CardView v) {
            super(v);
            info_xuatXu=itemView.findViewById(R.id.info_xuatXu);
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
        else
            myViewHolder.info_xuatXu.setImageResource(R.drawable.korean);

        myViewHolder.info_maLoai.setText("Mã loại:"+thietBi.getMaLoaiTB());
        myViewHolder.info_tenTB.setText("Tên Thiết Bị:"+thietBi.getTenTB());
        myViewHolder.info_maTB.setText("Mã thiết bị:"+thietBi.getMaTB());

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
