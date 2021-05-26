package fr.umontpellier.localisermonenfant.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

import fr.umontpellier.localisermonenfant.R;
import fr.umontpellier.localisermonenfant.models.Child;

public class ChildsAdapter extends RecyclerView.Adapter<ChildsAdapter.MyViewHolder> {
    public List<Child> children;
    final List<Integer> photoTest = Arrays.asList(R.drawable.fille1, R.drawable.fille2, R.drawable.fille3, R.drawable.fille4);

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView lastName;
        TextView code;
        ImageView photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            lastName = (TextView) itemView.findViewById(R.id.nameLast);
            code = (TextView) itemView.findViewById(R.id.codeChil);
            photo = (ImageView) itemView.findViewById(R.id.photo);
        }
    }

    public ChildsAdapter() {
    }

    public ChildsAdapter(List<Child> children) {
        this.children = children;
    }

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_child, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       int seq = position;
        if (position > 3) {
            seq = 0;
        }
        Child child = children.get(position);
        holder.name.setText(child.getFirstname());
        holder.lastName.setText(child.getLastname());
        holder.code.setText(child.getCode().toString());
        holder.photo.setImageResource(photoTest.get(seq));
    }

    @Override
    public int getItemCount() {
        return children.size();
    }

    public Child getChild(int index) {
        return children.get(index);
    }

}
