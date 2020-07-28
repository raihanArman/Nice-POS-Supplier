package id.co.myproject.nicepos_supplier.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.model.Pesanan;

public class PesananAdapter extends RecyclerView.Adapter<PesananAdapter.ViewHolder> {

    List<Pesanan> pesananList = new ArrayList<>();
    Context context;

    public PesananAdapter(Context context) {
        this.context = context;
    }

    public void setPesananList(List<Pesanan> pesananList){
        this.pesananList.clear();
        this.pesananList.addAll(pesananList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PesananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_request, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PesananAdapter.ViewHolder holder, int position) {
        holder.tv_barang.setText(pesananList.get(position).getNamaBarang());
        holder.tv_qty.setText(pesananList.get(position).getQty()+pesananList.get(position).getSatuan());
    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_barang, tv_qty;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_barang = itemView.findViewById(R.id.tv_barang);
            tv_qty = itemView.findViewById(R.id.tv_qty);
        }
    }
}
