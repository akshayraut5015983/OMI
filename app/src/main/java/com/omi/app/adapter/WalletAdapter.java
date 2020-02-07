package com.omi.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.models.WalletItems;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletViewHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<WalletItems> arrWalletItems;
    SimpleDateFormat dateFormat, serverFormat;

    public WalletAdapter(Context mContext, List<WalletItems> arrWalletItems) {
        this.mContext = mContext;
        this.arrWalletItems = arrWalletItems;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.custom_wallet_history,viewGroup,false);
        return new WalletViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder walletViewHolder, int i) {
        WalletItems walletHistory = arrWalletItems.get(i);
        if (walletHistory != null) {
            dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            serverFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                walletViewHolder.txt_trans_date.setText(dateFormat.format(serverFormat.parse(walletHistory.transDate)));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            walletViewHolder.txt_trans_desc.setText(walletHistory.transDesc);

            // myViewHolder.txt_trans_date.setText(walletHistory.getTrans_date());
            if (walletHistory.status.equals("1")) {
                walletViewHolder.txt_trans_amount.setText("\u20b9" + walletHistory.transAmt);
            } else if (walletHistory.status.equals("2")) {
                walletViewHolder.txt_trans_amount.setTextColor(mContext.getResources().getColor(R.color.red));
                walletViewHolder.txt_trans_amount.setText("\u20b9" + walletHistory.transAmt);
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrWalletItems.size();
    }


    public class WalletViewHolder extends RecyclerView.ViewHolder {
        TextView txt_trans_amount, txt_trans_desc, txt_trans_date;
        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_trans_amount = itemView.findViewById(R.id.txt_trans_amount);
            txt_trans_date = itemView.findViewById(R.id.txt_trans_date);
            txt_trans_desc = itemView.findViewById(R.id.txt_trans_des);
        }
    }
}
