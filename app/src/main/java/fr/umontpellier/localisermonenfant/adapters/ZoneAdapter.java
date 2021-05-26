package fr.umontpellier.localisermonenfant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.models.Child;
import fr.umontpellier.localisermonenfant.models.Zone;

public class ZoneAdapter extends RecyclerView.Adapter<ZoneAdapter.MyViewHolder> {
    public List<Zone> zones;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView latitude;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            latitude = itemView.findViewById(R.id.idLatitude);
        }
    }

    public ZoneAdapter() {
    }

    public ZoneAdapter(List<Zone> zones) {
        this.zones = zones;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_zone, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Zone zone = zones.get(position);
        holder.latitude.setText(zone.getLatitude().toString());
    }

    @Override
    public int getItemCount() {
        return zones.size();
    }

    public Zone getZone(int index){
        return zones.get(index);
    }

}
