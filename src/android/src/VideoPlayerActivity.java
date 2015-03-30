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

		//initialize the VideoView
		myVideoView = (VideoView) findViewById(fakeR.getId("id", "video_view"));

		// create a progress bar while the video file is loading
		progressDialog = new ProgressDialog(VideoPlayerActivity.this);
		// set a title for the progress bar
		progressDialog.setTitle("JavaCodeGeeks Android Video View Example");
		// set a message for the progress bar
		progressDialog.setMessage("Loading...");
		//set the progress bar not cancelable on users' touch
		progressDialog.setCancelable(false);
		// show the progress bar
		progressDialog.show();

		try {
			//set the media controller in the VideoView
			myVideoView.setMediaController(mediaControls);

			//set the uri of the video to be played
			myVideoView.setVideoURI(Uri.parse("http://r8---sn-u2oxu-f5f6.googlevideo.com/videoplayback?ip=176.31.228.114&pl=22&source=youtube&mv=u&dur=7576.485&ms=au&ipbits=0&mm=31&fexp=900720%2C907263%2C913446%2C917000%2C934954%2C936110%2C936931%2C9405714%2C9406237%2C9407103%2C9407821%2C9407887%2C9408101%2C948124%2C948701%2C948703%2C951511%2C951703%2C952612%2C957201%2C961404%2C961406&id=c59f2f856e5cb2fb&key=yt5&upn=Dkk0eMTZV3s&expire=1427732446&sparams=dur%2Cid%2Cip%2Cipbits%2Citag%2Cmm%2Cms%2Cmv%2Cpl%2Cratebypass%2Csource%2Cupn%2Cexpire&sver=3&itag=18&mt=1427710642&signature=9E91073ACFE5EC5185FA01B36AE8859199371FA4.7407E84814C5EFF825FCF752C272C571F748EE36&ratebypass=yes&signature="));

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
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