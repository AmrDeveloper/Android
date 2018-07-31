package com.worldnews.amrdeveloper.worldnews.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.worldnews.amrdeveloper.worldnews.R;
import com.worldnews.amrdeveloper.worldnews.data.NewsContract;
import com.worldnews.amrdeveloper.worldnews.utils.Utility;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by AmrDeveloper on 5/7/2018.
 */

public class NewsCursorAdapter extends CursorAdapter {

    /**
     * Constructs a new {@link NewsCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public NewsCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.news_layout, parent, false);
    }

    /**
     * This method binds the news data (in the current row pointed to by cursor) to the given
     * list item layout. For example, the name for the current news can be set on the name TextView
     * in the list item layout.
     *
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CardView newsItemLayout = view.findViewById(R.id.newsCardView);

        // Find individual views that we want to modify in the list item layout
        TextView title = view.findViewById(R.id.newsTitle);
        TextView newsPillar = view.findViewById(R.id.newsPillar);
        TextView newsData = view.findViewById(R.id.newsData);
        TextView description = view.findViewById(R.id.description);
        TextView author = view.findViewById(R.id.author);
        ImageView newsImage = view.findViewById(R.id.newsImage);

        ImageButton shareButton = view.findViewById(R.id.shareButton);

        //Visibility gone all time because no image in database for all news
        newsImage.setVisibility(View.GONE);

        // Find the columns of news attributes that we're interested in
        int idColumnIndex = cursor.getColumnIndex(NewsContract.NewsEntry._ID);
        int titleColumnIndex = cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NEWS_TITLE);
        int sectionColumnIndex = cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NEWS_SECTION);
        int dateColumnIndex = cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NEWS_DATE);
        int authorColumnIndex = cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NEWS_AUTHOR);
        int descriptionColumnIndex = cursor.getColumnIndex(NewsContract.NewsEntry.COLUMN_NEWS_DESC);

        // Read the medicine attributes from the Cursor for the current medicine
        final int rowId = cursor.getInt(idColumnIndex);
        String newsTitle = cursor.getString(titleColumnIndex);
        String newsSection = cursor.getString(sectionColumnIndex);
        String newsDate = Utility.dateFormat(cursor.getString(dateColumnIndex));
        String newsAuthor = cursor.getString(authorColumnIndex);
        String newsDesc = cursor.getString(descriptionColumnIndex);

        // Update the TextViews with the attributes for the current news
        title.setText(newsTitle);
        newsPillar.setText(newsSection);
        newsData.setText(newsDate);
        author.setText(newsAuthor);
        description.setText(newsDesc);

        //OnClick Implementation for List Item
        newsItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                showNoConnectionDialog(context);
            }
        });

        //OnClick Implementation for share ImageButton
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                showNoConnectionDialog(context);
            }
        });
    }

    private void showNoConnectionDialog(Context context) {
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(context,R.style.AlertDialogStyle);
        builder.setMessage(R.string.no_connection);

        // Create and show the AlertDialog
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        //Dismiss Dialog after n time
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                alertDialog.dismiss();
                timer.cancel();
            }
        }, 3000);

    }
}

