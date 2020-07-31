package tdc.edu.vn.qlsv.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Model.Main;
import tdc.edu.vn.qlsv.Model.ThietBi;
import tdc.edu.vn.qlsv.R;

public class CustomAdapterMain extends RecyclerView.Adapter<CustomAdapterMain.MyViewHolder>{
    private int layoutID;
    private ArrayList<Main> data;
    private ListenerMain listener;
    public static interface ListenerMain{
        public void onClick(int position);
    }

    public CustomAdapterMain(int layoutID, ArrayList<Main> data) {
        this.layoutID = layoutID;
        this.data = data;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView info_anhMoTa;
        private TextView info_kieuLoai;
        private TextView info_soLuong;
        private CardView cardView;

        public MyViewHolder(@NonNull CardView v) {
            super(v);
            info_anhMoTa=v.findViewById(R.id.main_kieuLoai);
            info_soLuong=v.findViewById(R.id.main_soLuong);
            info_kieuLoai =v.findViewById(R.id.main_moTa);
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
        myViewHolder.info_anhMoTa.setImageResource(data.get(i).getImage());
        myViewHolder.info_kieuLoai.setText(data.get(i).getTitle());
        myViewHolder.info_soLuong.setText("Số lượng:"+data.get(i).getAmout());
        cardView.setCardBackgroundColor(data.get(i).getColorCardView());
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null)
                    listener.onClick(myViewHolder.getAdapterPosition());
            }
        });

    }

    public void setListener(ListenerMain listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
