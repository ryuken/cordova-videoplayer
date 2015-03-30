var exec = require('cordova/exec');

module.exports = {
    play: function(url, srt, error) {

        /* Aligning with w3c spec */

		exec(null, error, "VideoPlayer", "play", [url, srt]);
    }
};