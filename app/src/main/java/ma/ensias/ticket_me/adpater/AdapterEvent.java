package ma.ensias.ticket_me.adpater;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedList;
import ma.ensias.ticket_me.R;
import ma.ensias.ticket_me.activities.EventActivity;
import ma.ensias.ticket_me.model.Event;

public class AdapterEvent extends RecyclerView.Adapter<AdapterEvent.ViewHolderEvent>  {

    private final LinkedList<Event> events;
    private final Context context;

    public AdapterEvent(LinkedList<Event>  events, Context context)
    {
        this.events = events;
        this.context = context;
    }
    @NonNull
    @Override
    public AdapterEvent.ViewHolderEvent onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.event_item,parent,false);
        return new ViewHolderEvent(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEvent.ViewHolderEvent holder, int position) {
        Event event = this.events.get(position);
        holder.name_event.setText(event.getName());
        holder.button_explore.setOnClickListener(v->{
            Intent intent = new Intent(holder.button_explore.getContext(),EventActivity.class);
            intent.putExtra("id_event",event.getId());
            holder.button_explore.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {

        return this.events.size();
    }


    public class ViewHolderEvent extends RecyclerView.ViewHolder {

        private TextView name_event;
        private ImageView button_explore;

        public ViewHolderEvent(final View itemView) {
            super(itemView);
            name_event = itemView.findViewById(R.id.name_of_event);
            button_explore = itemView.findViewById(R.id.button_explore_event);
        }
    }
}
