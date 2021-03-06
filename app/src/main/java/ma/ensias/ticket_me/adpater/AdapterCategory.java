package ma.ensias.ticket_me.adpater;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.activities.StatsActivity;
import ma.ensias.ticket_me.model.CategoryTicket;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.ViewHolder> {


    private final LinkedList<CategoryTicket> categories;
    private final Context context;
    private final int idEvent;

    public AdapterCategory(int idEvent, LinkedList<CategoryTicket> categories,Context context)
    {
        this.categories = categories;
        this.context = context;
        this.idEvent = idEvent;
    }
    @NonNull
    @Override
    public AdapterCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.category,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull AdapterCategory.ViewHolder holder, int position) {
        CategoryTicket category = this.categories.get(position);
        holder.name.setText(category.getName());
        holder.places.setText(category.getNumberOfPlaces()+" Places");
        holder.price.setText(category.getPrice()+" DH");
        holder.statistics.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), StatsActivity.class);
            intent.putExtra("id_category", category.getId());
            intent.putExtra("id_event", idEvent);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {

        return categories.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name,places,price;
        private ImageView statistics;

        public ViewHolder(final View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.name_value);
            price = itemView.findViewById(R.id.price_value);
            places = itemView.findViewById(R.id.number_of_places_available_value);
            statistics = itemView.findViewById(R.id.icon_chart);

        }
    }
}
