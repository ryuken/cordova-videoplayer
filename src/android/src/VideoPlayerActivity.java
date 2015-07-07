package videoplayer;

import videoplayer.FakeR;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity
{
	private FakeR fakeR;

	private VideoView myVideoView;
	private int position = 0;
	private ProgressDialog progressDialog;
	private MediaController mediaControls;

	@Override
	public void onCreate(Bundle savedInstance)
	{
		super.onCreate(savedInstance);

		getActionBar().hide();

		fakeR = new FakeR(this);

		// set the main layout of the activity
		setContentView(fakeR.getId("layout", "activity_main"));

		//set the media controller buttons
		if (mediaControls == null) {
			mediaControls = new MediaController(VideoPlayerActivity.this);
		}

		Bundle args = getIntent().getExtras();

		//initialize the VideoView
		myVideoView = (VideoView) findViewById(fakeR.getId("id", "video_view"));

		// create a progress bar while the video file is loading
		progressDialog = new ProgressDialog(VideoPlayerActivity.this);
		// set a title for the progress bar
		progressDialog.setTitle("De video wordt geladen.");
		// set a message for the progress bar
		//progressDialog.setMessage("Loading...");
		//set the progress bar not cancelable on users' touch
		progressDialog.setCancelable(false);
		// show the progress bar
		progressDialog.show();

		try {
			//set the media controller in the VideoView
			myVideoView.setMediaController(mediaControls);

			//set the uri of the video to be played
			myVideoView.setVideoURI(Uri.parse(args.getString("url")));

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
			progressDialog.hide();
		}

		myVideoView.requestFocus();
		//we also set an setOnPreparedListener in order to know when the video file is ready for playback
		myVideoView.setOnPreparedListener(new OnPreparedListener()
	  	{
			public void onPrepared(MediaPlayer mediaPlayer)
			{
				// close the progress bar and play the video
				progressDialog.dismiss();
				//if we have a position on savedInstanceState, the video playback should start from here
				myVideoView.seekTo(position);
				if (position == 0)
				{
					myVideoView.start();
				}
				else
				{
					//if we come from a resumed activity, video playback will be paused
					myVideoView.pause();
				}
			}
		});
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState)
	{
		super.onSaveInstanceState(savedInstanceState);
		//we use onSaveInstanceState in order to store the video playback position for orientation change
		savedInstanceState.putInt("Position", myVideoView.getCurrentPosition());
		myVideoView.pause();
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
		//we use onRestoreInstanceState in order to play the video playback from the stored position
		position = savedInstanceState.getInt("Position");
		myVideoView.seekTo(position);
	}
}
