package com.example.mvpweather;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MVPWeatherAdapter extends RecyclerView.Adapter<MVPWeatherAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MVPWeather> mvpWeatherArrayList;

    public MVPWeatherAdapter (Context context, ArrayList<MVPWeather> mvpWeatherArrayList){
        this.context = context;
        this.mvpWeatherArrayList = mvpWeatherArrayList;
    }

    @NonNull
    @Override
    public MVPWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater..from(context).inflate(R.layout.weather_rv_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MVPWeatherAdapter.ViewHolder holder, int position) {
        MVPWeather modal = mvpWeatherArrayList.get(position);
        holder.temperatureTV.setText(modal.getTemperature()+"°C");
        Picasso.get().load().into(holder.conditionIV);
        holder.windTV.setText(modal.getWindSpeed()+"Km/Hr");
        SimpleDateFormat input = new SimpleDateFormat(pattern: "yyyy-MM-dd  hh:mm");
        SimpleDateFormat output = new SimpleDateFormat(pattern: "hh:mm  aa");
        try {
            Date t =  input.parse(modal.getTime());
            holder.timeTV.setText(output.format(t));
        }catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mvpWeatherArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView windTV,temperatureTV,timeTV;
        private ImageView conditionIV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            windTV = itemView.findViewById(R.id.idTVWindSpeed);
            temperatureTV = itemView.findViewById(R.id.idTVTemperature);
            timeTV = itemView.findViewById(R.id.idTVTime);
            conditionIV = itemView.findViewById(R.id.idIVCondition);
        }
    }
}
