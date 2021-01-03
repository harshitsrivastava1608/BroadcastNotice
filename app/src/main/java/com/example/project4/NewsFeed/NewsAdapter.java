package com.example.project4.NewsFeed;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project4.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewAdapter>{
    private List<NewsData> list;
    private Context context;
    private String category;
    public NewsAdapter(List<NewsData> list, Context context, String category) {
        this.list = list;
        this.context = context;
        this.category=category;
    }

    @NonNull
    @Override
    public NewsViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_item_layout,parent,false);
        return new NewsViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewAdapter holder, int position) {
        NewsData item=list.get(position);
        holder.title.setText(item.getTitle());
       holder.description.setText(item.getDescription());
//        holder.category.setText(item.getCategory());
        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.btnViewNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NoticeInFull.class);

                intent.putExtra("image",item.getImage());


                context.startActivity(intent);
            }
        });
        holder.btnDeleteNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteContent(item.getKey(),item.getCategory(),item.getTitle());
                notification(v);
               /* Intent intent=new Intent(context, UpdateToAdminActivity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("email",item.getEmail());
                intent.putExtra("phone",item.getPhone());
                intent.putExtra("image",item.getImage());
                intent.putExtra("key",item.getKey());
                intent.putExtra("category",category);

                context.startActivity(intent);
            */
            }
        });
    }

    private void deleteContent(String key,String category,String title) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("News").child(category).child(key);
        StorageReference storage=FirebaseStorage.getInstance().getReference("News").child(title+category);
        reference.removeValue();
        storage.delete();
        Toast.makeText(context,"Deleted Successfully",Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NewsViewAdapter extends RecyclerView.ViewHolder {
        private TextView title,description,category;private Button btnDeleteNews,btnViewNews;private ImageView imageView;
        public NewsViewAdapter(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.NoticeTitle);
            description=itemView.findViewById(R.id.addNewsDesc);
            //category=itemView.findViewById(R.id.Category);
            btnDeleteNews=itemView.findViewById(R.id.btnDeleteNews);
            btnViewNews=itemView.findViewById(R.id.btnViewNews);
           imageView=itemView.findViewById(R.id.NoticeImage);
        }
    }
    private void notification(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("n", "n", NotificationManager.IMPORTANCE_DEFAULT);
            //channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = v.getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "n")
                    .setSmallIcon(R.drawable.ic_baseline_notifications_24)
                    .setContentTitle("Notice Information").setContentText("Data Deleted!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(1, mBuilder.build());
        }
    }
}
