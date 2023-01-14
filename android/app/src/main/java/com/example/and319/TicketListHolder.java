package com.example.and319;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TicketListHolder extends RecyclerView.ViewHolder {
    public TextView TicketName;
    public TextView TicketDateTime;
    public TextView TicketSeat;
    //public TextView TicketCode;
    //public TextView TicketStatus;
    public TextView TicketNumber;

    public TicketListHolder (View itemView) {
        super(itemView);

        TicketName = itemView.findViewById(R.id.item_name);
        TicketDateTime = itemView.findViewById(R.id.item_date);
        TicketSeat = itemView.findViewById(R.id.item_seat);
//        TicketCode = itemView.findViewById(R.id.signal);
//        TicketStatus = itemView.findViewById(R.id.ticket_status);
        TicketNumber = itemView.findViewById(R.id.item_number);

    }
}
