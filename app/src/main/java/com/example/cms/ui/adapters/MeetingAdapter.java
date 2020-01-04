package com.example.cms.ui.adapters;

import com.example.cms.Models.MeetingInfo;
import com.example.cms.R;
import com.example.cms.ui.MeetingActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MeetingsViewHolder> {

    private List<MeetingInfo> mMeetings;
    private Context mContext;

    public MeetingAdapter(Context context) {
        mContext = context;
    }

    public List<MeetingInfo> getMeetings() {
        return mMeetings;
    }

    public void setMeetings(List<MeetingInfo> meetings) {
        mMeetings = meetings;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MeetingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.meetings_viewholder, parent,
                false);
        return new MeetingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingsViewHolder holder, int position) {
        String scheduledTime =
                mMeetings.get(position).getStartTime() + " - " + mMeetings.get(position)
                        .getEndTime();
        holder.mScheduledTimeView.setText(scheduledTime);
        holder.mDescriptionView.setText(mMeetings.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if (mMeetings == null) {
            return 0;
        } else {
            return mMeetings.size();
        }
    }

    class MeetingsViewHolder extends RecyclerView.ViewHolder {
        private TextView mScheduledTimeView;
        private TextView mDescriptionView;

        public MeetingsViewHolder(@NonNull View itemView) {
            super(itemView);
            mScheduledTimeView = itemView.findViewById(R.id.scheduledTime);
            mDescriptionView = itemView.findViewById(R.id.description);
        }
    }
}
