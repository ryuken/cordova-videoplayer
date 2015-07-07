package videoplayer;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

public class VideoPlayer extends CordovaPlugin
{
    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArray of arguments for the plugin.
     * @param callbackContext   The callback context used when calling back into JavaScript.
     * @return                  True when the action was valid, false otherwise.
     */
	@Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
	{
		if (action.equals("play"))
		{
			Intent intent = new Intent(cordova.getActivity(), VideoPlayerActivity.class);
			intent.putExtra("url", args.getString(0));
			intent.putExtra("srt", args.getString(1));

			this.cordova.getActivity().startActivity(intent);

			return true;
        }

        return false;
    }
}
