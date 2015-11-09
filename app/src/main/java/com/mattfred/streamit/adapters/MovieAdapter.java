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
import com.mattfred.streamit.model.MovieAndView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by Matthew on 11/5/2015.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {

    private Context context;
    private List<Movie> movies;

    private LruCache<Integer, Bitmap> imageCache;

    public MovieAdapter(Context context, int resource, List<Movie> movies) {
        super(context, resource, movies);
        this.context = context;
        this.movies = movies;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_movie, parent, false);
        Movie movie = movies.get(position);

        TextView textView = (TextView) view.findViewById(R.id.tv_movie_title);
        textView.setText(movie.getTitle());

        Bitmap bitmap = imageCache.get(movie.getId());
        if (bitmap != null) {
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_movie_icon);
            imageView.setImageBitmap(bitmap);
        } else {
            MovieAndView container = new MovieAndView();
            container.movie = movie;
            container.view = view;

            ImageLoader loader = new ImageLoader();
            loader.execute(container);
        }

        return view;
    }

    private class ImageLoader extends AsyncTask<MovieAndView, Void, MovieAndView> {

        @Override
        protected MovieAndView doInBackground(MovieAndView... params) {
            MovieAndView container = params[0];
            Movie movie = container.movie;

            try {
                String imageUrl = movie.getPoster_120x171();
                InputStream in = (InputStream) new URL(imageUrl).getContent();
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
        protected void onPostExecute(MovieAndView result) {
            ImageView imageView = (ImageView) result.view.findViewById(R.id.iv_movie_icon);
            imageView.setImageBitmap(result.bitmap);
            imageCache.put(result.movie.getId(), result.bitmap);
        }
    }
}
