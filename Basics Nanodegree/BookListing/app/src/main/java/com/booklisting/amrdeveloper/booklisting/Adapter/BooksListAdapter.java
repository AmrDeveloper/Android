package com.booklisting.amrdeveloper.booklisting.Adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.booklisting.amrdeveloper.booklisting.Model.Book;
import com.booklisting.amrdeveloper.booklisting.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class BooksListAdapter extends ArrayAdapter<Book> {


    public BooksListAdapter(Context context, List<Book> dataList) {
        super(context, 0,dataList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            //Inflate Layout If Native View equal null
            view = LayoutInflater.from(getContext()).inflate(R.layout.book_layout,parent,false);
        }

        //Current Book on List
        Book currentBook = getItem(position);

        //Image View for Book Image
        ImageView book_image = (ImageView)view.findViewById(R.id.book_image);
        //Download Image And Put It On Image View
        new AsyncTaskDownloader(book_image).execute(currentBook.getBookImageUrl());

        //Title Text View
        TextView book_title = (TextView)view.findViewById(R.id.book_title);
        //set current book title on this text view
        book_title.setText(currentBook.getBookTitle());
        return view;
    }


    //AsyncTask For Download Image
    class AsyncTaskDownloader extends AsyncTask<String,Void,Bitmap>{

        private ImageView image;
        AsyncTaskDownloader(ImageView image){
            this.image = image;

        }
        @Override
        protected Bitmap doInBackground(String... strings) {
            if((strings == null) || (strings.length < 0)){
                return null;
            }
            Bitmap image = imageDownloader(strings[0]);
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
        }

        //Download Image
        protected Bitmap imageDownloader(String imgLink){
            Bitmap logo = null;
            InputStream inputStream = null;
            try
            {
                inputStream = new URL(imgLink).openStream();
                logo = BitmapFactory.decodeStream(inputStream);
            }
            catch(Exception e){

            }
            finally {
                if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return logo;
        }
    }

}
