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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mattfred.streamit.R;
import com.mattfred.streamit.model.Movie;
import com.mattfred.streamit.model.ResultAndView;
import com.mattfred.streamit.model.Show;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * List Adapter for show search results
 */
public class MovieAdapter extends ArrayAdapter<Object> {

    private final Context context;
    private final List<Object> results;

    private final LruCache<Integer, Bitmap> imageCache;

    public MovieAdapter(Context context, List<Object> objects) {
        super(context, R.layout.item_movie, objects);
        this.context = context;
        this.results = objects;

        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        imageCache = new LruCache<>(cacheSize);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ResultAndView container;

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_movie, parent, false);
            container = new ResultAndView();
            container.textView = (TextView) convertView.findViewById(R.id.tv_movie_title);
            container.progressBar = (ProgressBar) convertView.findViewById(R.id.progress_bar);
            container.imageView = (ImageView) convertView.findViewById(R.id.iv_movie_icon);
            convertView.setTag(container);
        } else {
            container = (ResultAndView) convertView.getTag();
        }

        String title = "";
        int id = 0;
        container.imageView.setImageBitmap(null);

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

        container.textView.setText(title);

        container.progressBar.setVisibility(View.VISIBLE);

        Bitmap bitmap = imageCache.get(id);
        if (bitmap != null) {
            container.imageView.setImageBitmap(bitmap);
            container.progressBar.setVisibility(View.INVISIBLE);
        } else {
            ImageLoader loader = new ImageLoader();
            loader.execute(container);
        }

        return convertView;
    }

    private class ImageLoader extends AsyncTask<ResultAndView, Void, ResultAndView> {

        @Override
        protected ResultAndView doInBackground(ResultAndView... params) {
            ResultAndView container = params[0];
            String url;
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
        protected void onPostExecute(ResultAndView container) {
            container.imageView.setImageBitmap(container.bitmap);
            if (container.isMovie) {
                imageCache.put(container.movie.getId(), container.bitmap);
            } else {
                imageCache.put(container.show.getId(), container.bitmap);
            }
            container.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
