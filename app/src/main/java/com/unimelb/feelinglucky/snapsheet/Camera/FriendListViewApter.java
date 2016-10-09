package com.unimelb.feelinglucky.snapsheet.Camera;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.unimelb.feelinglucky.snapsheet.Bean.ReturnMessage;
import com.unimelb.feelinglucky.snapsheet.NetworkService.AddFriendService;
import com.unimelb.feelinglucky.snapsheet.NetworkService.NetworkSettings;
import com.unimelb.feelinglucky.snapsheet.R;
import com.unimelb.feelinglucky.snapsheet.Util.SharedPreferencesUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac on 16/10/6.
 */

public class FriendListViewApter extends BaseAdapter {

    private List<WifiP2pDevice> mPeers;
    private Context mContext;
    private LayoutInflater inflater;

    public FriendListViewApter (Context context, List<WifiP2pDevice> peers) {
        mPeers = peers;
        mContext = context;

        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mPeers.size();
    }

    @Override
    public Object getItem(int position) {
        return mPeers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.scan_friend_item, null);
            viewHolder.name = (TextView) convertView.findViewById(R.id.scan_friend_name);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (mPeers != null) {
            viewHolder.name.setText(mPeers.get(position).deviceName);
            viewHolder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(NetworkSettings.baseUrl).build();
                    AddFriendService addFriendService = retrofit.create(AddFriendService.class);
                    Call call = addFriendService.addFriends(SharedPreferencesUtils.getSharedPreferences(mContext).getString("username", null), mPeers.get(position).deviceName);
                    call.enqueue(new Callback() {
                        @Override
                        public void onResponse(Call call, Response response) {
                            if (response.isSuccessful()) {
                                ReturnMessage message = (ReturnMessage) response.body();
                                if (message.isSuccess()) {
                                    Toast.makeText(mContext,"successful",Toast.LENGTH_LONG).show();
                                } else {
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call call, Throwable t) {

                        }
                    });
                }
            });
        } else  {
            viewHolder.name.setText("hello");
        }
        return convertView;
    }

    private class ViewHolder{
        TextView name;
    }
}
