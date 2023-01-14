package com.example.and319;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListHolder>  {
    private Context context;
   // private connect2Server connect;
    //private List<Ticket> list;
    private List<Ticket> ticket_list;

    public TicketListAdapter(Context context, List<Ticket> ticket_list) {
        this.context = context;
        this.ticket_list = ticket_list;
    }

    public Object getItem(int position) {
        if (ticket_list == null) {
            return null;
        }
        return ticket_list.get(position);
    }

    @Override
    public TicketListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ticket_card_item, parent, false);
        return new TicketListHolder(view);
    }

    @Override
    public void onBindViewHolder(TicketListHolder holder, int position) {
        Ticket currentItem = ticket_list.get(position);

        holder.TicketName.setText(currentItem.getTicketName());
        holder.TicketDateTime.setText(currentItem.getTicketTime());
        holder.TicketSeat.setText(currentItem.getTicketSeat());
        //holder.TicketStatus.setText(currentItem.getTicketStatus());
        holder.TicketNumber.setText(currentItem.getTicketCode());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (ticket_list == null) {
            return 0;
        }
        return ticket_list.size();
    }

//    @Override
//    public View getView(int position, View view, ViewGroup parent) {
//        View convertView;
//        ViewHolder viewholder;
//
//        if (view == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.ticket_item, null);
//            viewholder = new ViewHolder();
//            viewholder.TicketName = (TextView)convertView.findViewById(R.id.TextViewFName);
//            viewholder.TicketDateTime = (TextView)convertView.findViewById(R.id.TextViewFDateTime);
//            viewholder.TicketSeat = (TextView)convertView.findViewById(R.id.TextViewFSeat);
//            viewholder.TicketSig = (TextView) convertView.findViewById(R.id.signal);
//            viewholder.TicketNum = (TextView) convertView.findViewById(R.id.ticketnum);
//            viewholder.TicketStatus = (TextView)convertView.findViewById(R.id.ticket_status);
//            //viewholder.test = (TextView)convertView.findViewById(R.id.test);
//            /*这个地方要多加两个viewholder！！*/
//
//            convertView.setTag(viewholder);
//        } else {
//            convertView = view;
//            viewholder = (ViewHolder)convertView.getTag();
//        }
//
//        Ticket Ticket = ticket_list.get(position);
//        viewholder.TicketName.setText(Ticket.getTicketName());
//        viewholder.TicketDateTime.setText(Ticket.getTicketIntroduce());
//        viewholder.TicketSeat.setText(Ticket.getTicketTime());
//        viewholder.TicketNum.setText(Ticket.getTicketCode());
//        viewholder.TicketStatus.setText(Ticket.getTicketStatus());
//
//
//
//        /**************************Set Text 失败************************************************/
//        if(Ticket.getTicketStatus().equals("Not used")){
//            //viewholder.TicketNum.setText(Ticket.getTicketCode());
//            viewholder.TicketNum.setTextColor(Color.parseColor("#F06292"));
//            //viewholder.TicketStatus.setText(Ticket.getTicketStatus());
//            viewholder.TicketStatus.setTextColor(Color.parseColor("#F06292"));
//        }
//        else{
//            //viewholder.TicketNum.setText(Ticket.getTicketCode());
//            viewholder.TicketNum.setTextColor(Color.parseColor("#8B8378"));
//            //viewholder.TicketStatus.setText(Ticket.getTicketStatus());
//            viewholder.TicketStatus.setTextColor(Color.parseColor("#8B8378"));
//        }
//        /*****************************Set Text 失败*********************************************/
//
//
//        return convertView;
//    }

//    public interface ItemClickListener {
//        void onItemClick(int position);
//    }
//
//    private class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView TicketName;
//        public TextView TicketDateTime;
//        public TextView TicketSeat;
//        public TextView TicketSig;
//        public TextView TicketNum;
//        public TextView TicketStatus;
//       // public TextView test;
//
//        public ViewHolder(View itemView, final ItemClickListener listener) {
//            super(itemView);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getLayoutPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
//        }
//    }
}



