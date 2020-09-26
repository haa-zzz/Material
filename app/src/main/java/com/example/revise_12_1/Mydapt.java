package com.example.revise_12_1;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

public class Mydapt extends RecyclerView.Adapter<Mydapt.ViewHolder> {
    private List<Fruit> list;
    private Context mcontext;
    public Mydapt(List<Fruit> list) {
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            imageView = itemView.findViewById(R.id.item_image);
            textView = itemView.findViewById(R.id.item_textview);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(mcontext==null)
            mcontext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();
                Fruit fruit = list.get(position);
                Intent intent = new Intent(mcontext,MainActivity2.class);
                intent.putExtra(MainActivity2.FRUIT_NAME,fruit.getName());
                intent.putExtra(MainActivity2.FRUIT_IMAGE_ID,fruit.getImageId());
                mcontext.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Fruit fruit = list.get(position);
        holder.textView.setText(fruit.getName());
        Glide.with(mcontext).load(fruit.getImageId()).into(holder.imageView);       //用Glide加载图片

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
