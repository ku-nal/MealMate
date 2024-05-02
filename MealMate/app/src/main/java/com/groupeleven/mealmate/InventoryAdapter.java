package com.groupeleven.mealmate;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

// Adapter class to implement RecyclerView
public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.IngredientsViewHolder> {

    private List<IngredientsData> ingredientsDataList;
    private Context context;

    public InventoryAdapter(Context context, List<IngredientsData> ingredientsDataList) {
        this.context = context;
        this.ingredientsDataList = ingredientsDataList;
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView1;
        TextView textView;
        CardView cardView;

        TextView unitView;

        EditText editText;
        // Define views within the card_item.xml layout here
        // For example: ImageView imageView;

        public IngredientsViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            // Initialize views here
            // For example: imageView = itemView.findViewById(R.id.ingt_img_id);
            textView = cardView.findViewById(R.id.ingt_name);

            imgView1 = cardView.findViewById(R.id.ingt_img_id);

            unitView = cardView.findViewById(R.id.ingt_qty_unit);

            editText = cardView.findViewById(R.id.ingt_qty_num);

        }
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new IngredientsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position) {
        // Bind data to the views within the card_item.xml layout
        // For example: holder.imageView.setImageResource(ingredientsDataList.get(position).getImageResource());
        IngredientsData ingredientsData = ingredientsDataList.get(position);
        holder.textView.setText(ingredientsData.getIngredientName());
        Log.d("imgurl",ingredientsData.getIngredientImg());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.transform(new MultiTransformation<>(new BlurTransformation(20, 1)));

        Glide.with(holder.cardView)
                .load(ingredientsData.getIngredientImg())
                .apply(requestOptions)
                .into(holder.imgView1);
        holder.unitView.setText(ingredientsData.getUnit());
        holder.editText.setText(ingredientsData.getIngredientQty()+"");

    }

    @Override
    public int getItemCount() {
        return ingredientsDataList.size();
    }
}
