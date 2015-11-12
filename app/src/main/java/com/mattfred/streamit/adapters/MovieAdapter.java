package com.mattfred.streamit.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mattfred.streamit.R;
import com.mattfred.streamit.model.Movie;
import com.mattfred.streamit.model.ResultAndView;
import com.mattfred.streamit.model.Show;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Matthew on 11/5/2015.
 */
public class MovieAdapter extends ArrayAdapter<Object> {

    private Context context;
    private List<Object> results;

    private LruCache<Integer, Bitmap> imageCache;

    public MovieAdapter(Context context, int resource, List<Object> objects) {
        super(context, resource, objects);
        this.context = context;
        this.results = objects;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_movie, parent, false);

        ResultAndView container = new ResultAndView();
        String title = "";
        int id = 0;

        if (results.get(position) instanceof Movie) {
            Movie movie = (Movie) results.get(position);
            title = movie.getTitle();
            id = movie.getId();
            container.movie = movie;
            container.isMovie = true;
        } else if (results.get(position) instanceof Show) {
            Show show = (Show) results.get(position);
            title = show.getTitle();
            id = show.getId();
            container.show = show;
            container.isMovie = false;
        }

        TextView textView = (TextView) view.findViewById(R.id.tv_movie_title);
        textView.setText(title);

        Bitmap bitmap = imageCache.get(id);
        if (bitmap != null) {
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_movie_icon);
            imageView.setImageBitmap(bitmap);
        } else {
            container.view = view;
            ImageLoader loader = new ImageLoader();
            loader.execute(container);
        }

        return view;
    }



    private class ImageLoader extends AsyncTask<ResultAndView, Void, ResultAndView> {

        @Override
        protected ResultAndView doInBackground(ResultAndView... params) {
            ResultAndView container = params[0];
            String url = "";
            if (container.isMovie) {
                Movie movie = container.movie;
                url = movie.getPoster_120x171();
            } else {
                Show show = container.show;
                url = show.getArtwork_208x117();
            }

            try {
                InputStream in = (InputStream) new URL(url).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                in.close();
                container.bitmap = bitmap;
                return container;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(ResultAndView result) {
            ImageView imageView = (ImageView) result.view.findViewById(R.id.iv_movie_icon);
            imageView.setImageBitmap(result.bitmap);
            if (result.isMovie) {
                imageCache.put(result.movie.getId(), result.bitmap);
            } else {
                imageCache.put(result.show.getId(), result.bitmap);
            }
        }
    }
}
