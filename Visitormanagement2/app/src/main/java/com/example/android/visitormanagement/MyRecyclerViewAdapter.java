package com.example.android.visitormanagement;

/**
 * Created by NAYA on 8/19/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyRecyclerViewAdapter extends RecyclerView
        .Adapter<MyRecyclerViewAdapter
        .DataObjectHolder> implements Filterable {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    current_visitors cv = new current_visitors();
    visitors_option vo = new visitors_option();
    private static  String token;
    static TokenSaver ts = new TokenSaver();
    private ArrayList<DataObject> mDataset;
    private static MyClickListener myClickListener;
    private ArrayList<String> idArray;
    private ArrayList<DataObject> mFilteredList;


    public static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener  {

        TextView id, mbnumber;
        TextView visitee_name;
        static ImageView imagevisitee;

        public DataObjectHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.idnum);
            visitee_name = (TextView) itemView.findViewById(R.id.name);
            mbnumber = (TextView) itemView.findViewById(R.id.mbnumber);
            imagevisitee = (ImageView) itemView.findViewById(R.id.thumbnail);
            Log.i(LOG_TAG, "Adding Listener");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter(ArrayList<DataObject> myDataset) {

       mDataset = myDataset;
   }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        token = ts.getToken(holder.itemView.getContext());
        holder.id.setText(mDataset.get(position).getmText1());
        holder.visitee_name.setText(mDataset.get(position).getmText2());
        holder.mbnumber.setText(mDataset.get(position).getmText3());
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Token "+ token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Picasso picasso = new Picasso.Builder(holder.itemView.getContext())
                .downloader(new OkHttp3Downloader(client))
                .build();

        picasso.load("https://congruent-multisyst.000webhostapp.com/v1/photo/thumb/"+mDataset.get(position).getmText4())
                .into(holder.imagevisitee);
        //holder.imagevisitee.setImageBitmap(mDataset.get(position).get);

    }
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = mDataset;
                } else {

                    ArrayList<DataObject> filteredList = new ArrayList<>();

                    for (DataObject dataObject : mDataset) {

                        if (dataObject.getmText1().contains(charString) || dataObject.getmText2().contains(charString) || dataObject.getmText3().contains(charString)) {

                            filteredList.add(dataObject);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<DataObject>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void addItem(DataObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }


    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }


    @Override
    public int getItemCount() {

        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    public void setFilter(List<String> visitee_id) {
        idArray = new ArrayList<>();
        idArray.addAll(visitee_id);
        notifyDataSetChanged();
    }





}
