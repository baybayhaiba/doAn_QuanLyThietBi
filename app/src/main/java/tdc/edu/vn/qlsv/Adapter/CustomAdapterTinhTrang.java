package tdc.edu.vn.qlsv.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import tdc.edu.vn.qlsv.Database.DataTinhTrang;
import tdc.edu.vn.qlsv.Model.TinhTrangThietBi;
import tdc.edu.vn.qlsv.R;

public class CustomAdapterTinhTrang extends RecyclerView.Adapter<CustomAdapterTinhTrang.ViewHolder> {
    Context context;
    ArrayList<TinhTrangThietBi> listTinhTrang;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private LayoutInflater inflater;

    public CustomAdapterTinhTrang(Context context,ArrayList<TinhTrangThietBi> tinhTrangThietBi){
        this.context=context;
        this.listTinhTrang =tinhTrangThietBi;
    }

    public CustomAdapterTinhTrang.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        final CardView cardView=(CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_custom_tinhtrang,parent,false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final TinhTrangThietBi tinhTrangThietBi=listTinhTrang.get(i);
        viewHolder.tv_qty.setText("Thiết bị thứ "+(i+1));
        viewHolder.tv_tinhTrang.setText("Tình trạng:"+tinhTrangThietBi.getTinhTrang());
        viewHolder.imgBt_delete.setImageResource(R.drawable.ic_baseline_close_24);
        viewHolder.imgBt_edit.setImageResource(R.drawable.ic_baseline_create_24);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Hello", "onClick: "+tinhTrangThietBi.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTinhTrang.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private CardView cardView;
        private TextView tv_qty;
        private TextView tv_tinhTrang;
        private ImageView imgBt_edit;
        private ImageView imgBt_delete;
        public ViewHolder(CardView v){
            super(v);
            cardView=v;
            tv_qty=cardView.findViewById(R.id.info_qty);
            tv_tinhTrang=cardView.findViewById(R.id.info_tinhTrang);
            imgBt_delete=cardView.findViewById(R.id.bt_info_delete);
            imgBt_edit=cardView.findViewById(R.id.bt_info_edit);
            imgBt_edit.setOnClickListener(this);
            imgBt_delete.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            int position;
            TinhTrangThietBi tinhTrangThietBi;

            switch (view.getId()){
                case R.id.bt_info_edit:
                    position=getAdapterPosition();
                    tinhTrangThietBi=listTinhTrang.get(position);
                    UpdateItem(tinhTrangThietBi);
                    break;
                case R.id.bt_info_delete:
                    position=getAdapterPosition();
                    tinhTrangThietBi=listTinhTrang.get(position);
                    deletItem(tinhTrangThietBi.getId());
                    break;
            }
        }
        private void UpdateItem(final TinhTrangThietBi tinhTrangThietBi){
            AlertDialog.Builder builder;
            final Spinner spinner;
            Button saveButton;

            builder=new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            final View view=inflater.inflate(R.layout.popup_tinhtrang,null);
            spinner=view.findViewById(R.id.sp_tinhTrang);
            saveButton=view.findViewById(R.id.bt_TinhTrang);
            spinner.setSelection(getIndex(spinner,tinhTrangThietBi.getTinhTrang()));
            builder.setView(view);
            dialog=builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tinhTrangThietBi.setTinhTrang(spinner.getSelectedItem().toString());
                    DataTinhTrang dataTinhTrang=new DataTinhTrang(context);
                    dataTinhTrang.UpdateTinhTrang(tinhTrangThietBi);
                    notifyItemChanged(getAdapterPosition(),tinhTrangThietBi);
                    dialog.cancel();
                }
            });
        }
        private int getIndex(Spinner spinner, String myString){

            int index = 0;

            for (int i=0;i<spinner.getCount();i++){
                if (spinner.getItemAtPosition(i).equals(myString)){
                    index = i;
                }
            }
            return index;
        }
        private void deletItem(final int id){
            builder=new AlertDialog.Builder(context);
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có thật sự muốn xóa không ?");
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DataTinhTrang data=new DataTinhTrang(context);
                    data.DeleteTinhTrang(id);
                    listTinhTrang.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                }
            });
            dialog=builder.create();
            dialog.show();
        }
    }

}
