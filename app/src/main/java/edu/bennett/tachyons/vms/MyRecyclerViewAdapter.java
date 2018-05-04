package edu.bennett.tachyons.vms;

/**
 * Created by veeki on 8/19/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;


public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> implements Filterable {
    public static boolean isPicasso = false;
    static TokenSaver ts = new TokenSaver();
    private static String token;
    private static ArrayList<DataObject> mDataset;
    private static boolean alert = false;
    public Picasso picasso;
    private ArrayList<DataObject> mArrayList;
    current_visitors c = new current_visitors();
    private Context context;

    public MyRecyclerViewAdapter(Context context, ArrayList<DataObject> mDataset) {
        this.context = context;
        this.mDataset = mDataset;
        this.mArrayList = mDataset;
        //Set it to false to avoid nullPointerException
        //This is to stop app from crashing when activity is relaunched
        //Object picasso becomes null but isPicasso remains true, which causes the exception
        isPicasso = false;


    }

    // picasso to load images
    public void buildPicasso() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request newRequest = chain.request().newBuilder()
                                .addHeader("Authorization", "Token " + token)
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();
        Picasso.Builder picassoBuilder = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(client));
        picasso = picassoBuilder.build();
        isPicasso = true;
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
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        token = ts.getToken(context);
        holder.id.setText(mDataset.get(position).getid());
        holder.visitee_name.setText(mDataset.get(position).getname());
        holder.mbnumber.setText(mDataset.get(position).getmobile());

        holder.setIsRecyclable(false);


        if (!isPicasso) {
            buildPicasso();
        }


        // to display the images of the visitee
        picasso.load(mDataset.get(position).getmimageuri())
                .placeholder(R.drawable.visitor)
                .error(R.drawable.splash_logo)
                .fit().centerCrop()
                .into(holder.imagevisitee);


        // click listener to exit the visitor
        holder.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // c.alertdialog();
                    RequestInterceptor requestInterceptor = new RequestInterceptor() {

                        @Override
                        public void intercept(RequestFacade request) {
                            request.addHeader("Authorization", "Token " + token);
                        }
                    };

                    final RestAdapter adapter = new RestAdapter.Builder().setEndpoint(GetDetails.BASE_URL)
                            .setRequestInterceptor(requestInterceptor).build();

                    //Creating Rest Services
                    GetDetails get_details = adapter.create(GetDetails.class);

                    //Calling method to get check login
                    get_details.exit_user(mDataset.get(position).getvisiteeid(), new Callback<ExitData>() {
                        @Override
                        public void success(ExitData jsonData, retrofit.client.Response response) {
                            if (jsonData.getSuccess()) {
                                mDataset.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                notifyItemRangeChanged(position, mDataset.size());
                                notifyDataSetChanged();
                            } else {
                                Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Toast.makeText(context, "Error, Try Again!!", Toast.LENGTH_SHORT).show();

                        }
                    });

            }
        });



    }

    // filter for the search view
    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mDataset = mArrayList;
                } else {

                    ArrayList<DataObject> filteredList = new ArrayList<>();

                    for (DataObject data : mArrayList) {

                        if (data.getid().toLowerCase().contains(charString.toLowerCase()) || data.getname().toLowerCase().contains(charString.toLowerCase())) {

                            filteredList.add(data);
                        }
                    }

                    mDataset = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataset;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mDataset = (ArrayList<DataObject>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        static ImageView imagevisitee;
        static Button exit;
        TextView id, mbnumber;
        TextView visitee_name;

        public DataObjectHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.idnum);
            visitee_name = (TextView) itemView.findViewById(R.id.name);
            mbnumber = (TextView) itemView.findViewById(R.id.mbnumber);
            imagevisitee = (ImageView) itemView.findViewById(R.id.thumbnail);
            exit = (Button) itemView.findViewById(R.id.exit);

        }
    }

}
