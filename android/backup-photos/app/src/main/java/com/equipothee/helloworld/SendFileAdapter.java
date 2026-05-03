package com.equipothee.helloworld;

import android.graphics.Color;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SendFileAdapter extends RecyclerView.Adapter<SendFileAdapter.ViewHolder> {

    private final List<SendFileInfo> items;
    private final OnItemClickListener listener;

    public SendFileAdapter(List<SendFileInfo> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_send_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SendFileInfo item = items.get(position);
        holder.fileNameTextView.setText(item.getFileName());
        holder.fileNameTextView.setTextColor(getColorForState(item.getState()));
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private int getColorForState(SendFileInfo.State state) {
        switch (state) {
            case A_ENVOYER:
                return Color.parseColor("#2196F3"); // bleu
            case NE_PAS_ENVOYER:
                return Color.parseColor("#4CAF50"); // vert
            case ENVOI_OK:
                return Color.parseColor("#4CAF50"); // vert
            case ENVOI_KO:
                return Color.parseColor("#F44336"); // rouge
            default:
                return Color.WHITE;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SendFileInfo item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fileNameTextView;

        ViewHolder(View itemView) {
            super(itemView);
            fileNameTextView = itemView.findViewById(R.id.fileNameTextView);
        }
    }
}