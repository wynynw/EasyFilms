package com.example.and319;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.List;

public class FilmListAdapter extends BaseAdapter implements OnClickListener {
    private Context context;
    private List<Film> list;
    private InnerItemOnclickListener listener;

    public FilmListAdapter(Context context, List<Film> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        if (list == null) {
            return null;
        }
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View convertView;
        ViewHolder viewholder;

        if (view == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.film_item, null);
            viewholder = new ViewHolder();
            viewholder.filmPoster = (ImageView)convertView.findViewById(R.id.ImageViewFPoster);
            viewholder.filmName = (TextView)convertView.findViewById(R.id.TextViewFName);
            viewholder.filmTimeOn = (TextView)convertView.findViewById(R.id.TextViewFTimeOn);
            viewholder.filmSelect = (Button) convertView.findViewById(R.id.Select);
            convertView.setTag(viewholder);
        } else {
            convertView = view;
            viewholder = (ViewHolder)convertView.getTag();
        }

        Film film = list.get(position);
        viewholder.filmPoster.setImageBitmap(film.getFilmPoster());
        viewholder.filmName.setText(film.getFilmName());
        viewholder.filmTimeOn.setText(film.getFilmTimeOn());
        viewholder.filmSelect.setOnClickListener(this);
        viewholder.filmSelect.setTag(position);
        return convertView;
    }

    private class ViewHolder {
        public ImageView filmPoster;
        public TextView filmName;
        public TextView filmTimeOn;
        public Button filmSelect;
    }

    @Override
    public void onClick(View view) {
        listener.itemClick(view);
    }

    interface InnerItemOnclickListener{
        void itemClick(View view);
    }

    public void setOnInnerItemOnClickListener(InnerItemOnclickListener listener){
        this.listener=listener;
    }


}

