package id.co.myproject.nicepos_supplier.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.nicepos_supplier.BuildConfig;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.model.Barang;
import id.co.myproject.nicepos_supplier.view.home.DetailBarangActivity;

import static id.co.myproject.nicepos_supplier.util.Helper.rupiahFormat;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    List<Barang> barangList = new ArrayList<>();
    Context context;

    public BarangAdapter(Context context) {
        this.context = context;
    }

    public void setBarangList(List<Barang> barangList){
        this.barangList.clear();
        this.barangList.addAll(barangList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BarangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangAdapter.ViewHolder holder, int position) {
        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.bg_barang);
        if (barangList.size() > 0) {
            holder.tvbarang.setText(barangList.get(position).getNamaBarang());
            holder.tvHarga.setText(rupiahFormat(Integer.valueOf(barangList.get(position).getHarga())));
            Glide.with(context).applyDefaultRequestOptions(options).load(BuildConfig.BASE_URL_GAMBAR + "barang/" + barangList.get(position).getGambar()).into(holder.ivBarang);

        }
        holder.btnCek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailBarangActivity.class);
                intent.putExtra("id_barang", barangList.get(position).getIdBarang());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return barangList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivBarang;
        TextView tvbarang;
        TextView btnCek;
        TextView tvHarga;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBarang = itemView.findViewById(R.id.iv_barang);
            tvbarang = itemView.findViewById(R.id.tv_nama_barang);
            btnCek = itemView.findViewById(R.id.btn_cek);
            tvHarga = itemView.findViewById(R.id.tv_harga_barang);
        }
    }


}
