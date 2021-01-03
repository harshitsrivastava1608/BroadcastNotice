package com.example.project4.Admins;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.AdminViewAdapter>{
    private List<AdminData> list;
    private Context context;
    private String category;
    public AdminAdapter(List<AdminData> list, Context context,String category) {
        this.list = list;
        this.context = context;
        this.category=category;
    }

    @NonNull
    @Override
    public AdminViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.admin_item_layout,parent,false);
        return new AdminViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminViewAdapter holder, int position) {
        AdminData item=list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.phone.setText(item.getPhone());
        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,UpdateToAdminActivity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("phone",item.getPhone());
                intent.putExtra("image",item.getImage());
                intent.putExtra("key",item.getKey());
                intent.putExtra("category",category);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdminViewAdapter extends RecyclerView.ViewHolder {
        private TextView name,email,phone;private Button update;private ImageView imageView;
        public AdminViewAdapter(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.adminName);
            email=itemView.findViewById(R.id.adminEmail);
            phone=itemView.findViewById(R.id.adminPhone);
           update=itemView.findViewById(R.id.btnUpdate);
           imageView=itemView.findViewById(R.id.adminPic);
        }
    }
}