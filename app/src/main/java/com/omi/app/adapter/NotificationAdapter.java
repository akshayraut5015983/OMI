package com.omi.app.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omi.app.R;
import com.omi.app.activity.NotificationActivity;
import com.omi.app.db.DBHelper;
import com.omi.app.models.NotificationModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private List<NotificationModel> arrNotificationModels;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private DBHelper dbHelper;

    public NotificationAdapter(List<NotificationModel> arrNotificationModels, Context mContext, DBHelper dbHelper) {
        this.arrNotificationModels = arrNotificationModels;
        this.mContext = mContext;
        this.dbHelper=dbHelper;
        mLayoutInflater = (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mLayoutInflater.inflate(R.layout.item_notification, viewGroup, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, final int i) {

        NotificationModel notificationModel = arrNotificationModels.get(i);
        notificationViewHolder.title.setText(notificationModel.getTitle());
        notificationViewHolder.body.setText(notificationModel.getBody());
        notificationViewHolder.time.setText(formatDate(notificationModel.getTimestamp()));


        notificationViewHolder.mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        dbHelper.deleteNotification(arrNotificationModels.get(i));
                    ((NotificationActivity) mContext).onResume();
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrNotificationModels.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView title, body, time;
        ImageView mIvDelete;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            body = itemView.findViewById(R.id.tv_message);
            time = itemView.findViewById(R.id.tv_date);
            mIvDelete = itemView.findViewById(R.id.iv_delete);
        }


    }

    private String formatDate(String dateStr) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = fmt.parse(dateStr);
            SimpleDateFormat fmtOut = new SimpleDateFormat("HH:mm:ss d MMM yyyy");
            return fmtOut.format(date);
        } catch (ParseException e) {

        }

        return "";
    }

    void deleteItem(int index) {
        arrNotificationModels.remove(index);
        notifyItemRemoved(index);
    }
}
