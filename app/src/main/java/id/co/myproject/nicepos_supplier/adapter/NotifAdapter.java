package id.co.myproject.nicepos_supplier.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import id.co.myproject.nicepos_supplier.BuildConfig;
import id.co.myproject.nicepos_supplier.R;
import id.co.myproject.nicepos_supplier.model.Notification;
import id.co.myproject.nicepos_supplier.util.TimeStampFormatter;
import id.co.myproject.nicepos_supplier.view.home.MainActivity;
import id.co.myproject.nicepos_supplier.view.home.notification.DetailRequestFragment;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.ViewHolder> {

    List<Notification> notificationList = new ArrayList<>();
    Context context;
    TimeStampFormatter timeStampFormatter;

    public NotifAdapter(Context context, TimeStampFormatter timeStampFormatter) {
        this.context = context;
        this.timeStampFormatter = timeStampFormatter;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList.clear();
        this.notificationList.addAll(notificationList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotifAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notif, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifAdapter.ViewHolder holder, int position) {
        if (notificationList.size() > 0){
            Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"cafe/"+notificationList.get(position).getGambar()).into(holder.ivUser);
            holder.tvUser.setText(notificationList.get(position).getNamaUser()+" - "+notificationList.get(position).getNamaCafe());
            holder.tvTanggal.setText(timeStampFormatter.format(notificationList.get(position).getTanggal()));
            holder.tvJumlah.setText("Jumlah pesanan : "+notificationList.get(position).getJumlahPesan());
            holder.tvStatus.setText(notificationList.get(position).getStatus());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DetailRequestFragment detailRequestFragment = new DetailRequestFragment();
                    Bundle bundle = new Bundle();
                    detailRequestFragment.setArguments(bundle);
                    bundle.putString("id_request", notificationList.get(position).getIdRequest());
                    ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frame_home, detailRequestFragment)
                            .addToBackStack("null")
                            .commit();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView ivUser;
        TextView tvUser, tvTanggal, tvJumlah, tvStatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivUser = itemView.findViewById(R.id.iv_cafe);
            tvUser = itemView.findViewById(R.id.tv_user);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
            tvJumlah =itemView.findViewById(R.id.tv_jumlah_pesanan);
            tvStatus = itemView.findViewById(R.id.tv_status);
        }
    }
}
