package com.interglobalmegacorp.interactivestory.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.interglobalmegacorp.interactivestory.R;
import com.interglobalmegacorp.interactivestory.model.Page;
import com.interglobalmegacorp.interactivestory.model.Story;


public class StoryActivity extends Activity {

    private Story mStory = new Story(); // instantiate a new story variable
    private ImageView mImageView; // set member vars for page elements
    private TextView mTextView;
    private Button mChoice1;
    private Button mChoice2;
    private String mName;
    private Page mCurrentPage;

    public static final String TAG = StoryActivity.class.getSimpleName(); // set TAG for log

    @Override
    protected void onCreate(Bundle savedInstanceState) { // when the activity starts
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story); // use the story layout

        Intent intent = getIntent(); // get the intent that started the activity


        if (mName == null){
            mName = "Friend";
        }
        Log.d(TAG, mName);

        mName = intent.getStringExtra(getString(R.string.key_name)); // get the name from the intent (ie what the user typed in on the main activity
        mImageView = (ImageView) findViewById(R.id.storyImageView); //set up variable instances of each page element
        mTextView = (TextView) findViewById(R.id.storyTextView);
        mChoice1 = (Button) findViewById(R.id.choiceButton1);
        mChoice2 = (Button) findViewById(R.id.choiceButton2);

        loadPage(0); // call loadPage function, passing 0 as the page parameter
    }
    @TargetApi(21)
    private void loadPage(int choice){
        mCurrentPage = mStory.getPage(choice); // set current page to passed parameter

        Drawable drawable = getResources().getDrawable(mCurrentPage.getImageId(), null); // define new image var
        mImageView.setImageDrawable(drawable); // set the image view to that image

        String pageText = mCurrentPage.getText(); //
        pageText = String.format(pageText, mName);
        mTextView.setText(pageText);

        if (mCurrentPage.isFinal()){
            mChoice1.setVisibility(View.INVISIBLE);
            mChoice2.setText("Play again");
            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            mChoice1.setText(mCurrentPage.getChoice1().getText());
            mChoice2.setText(mCurrentPage.getChoice2().getText());

            mChoice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice1().getNextPage();
                    loadPage(nextPage);
                }
            });

            mChoice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int nextPage = mCurrentPage.getChoice2().getNextPage();
                    loadPage(nextPage);
                }
            });
        }
    }

}
