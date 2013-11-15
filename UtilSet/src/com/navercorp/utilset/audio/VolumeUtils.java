package com.navercorp.utilset.audio;

import android.content.Context;
import android.media.AudioManager;

/**
 * Controls Media Volume
 * 
 * @author jaemin.woo
 * 
 */
public class VolumeUtils {
	public interface OnVolumeChangedListener {
		public void onVolumeChanged(int volume);
	}
	
	private VolumeUtils() {}

	/**
	 * Returns current media volume
	 * @param context
	 * @return current volume
	 */
	public static int getCurrentVolume(Context context) {
		AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
	}

	/**
	 * Sets media volume
	 * 
	 * @param context Context
	 * @param volume volume to be changed
	 */
	public static void setVolume(Context context, int volume) {
		adjustMediaVolume(context, volume, 0);
	}
	
	/**
	 * Sets media volume and displays volume level
	 * 
	 * @param context Context
	 * @param volume volume to be changed
	 */
	public static void setVolumeWithLevel(Context context, int volume) {
		adjustMediaVolume(context, volume, AudioManager.FLAG_SHOW_UI);
	}
	
	/**
	 * Increases media volume
	 * 
	 * @param context
	 */
	public static void increaseVolume(Context context) {
		adjustMediaVolume(context, getCurrentVolume(context) + 1, 0);
	}
	
	/**
	 * Increases media volume and displays volume level
	 * 
	 * @param context
	 */
	public static void increaseVolumeWithLevel(Context context) {
		adjustMediaVolume(context, getCurrentVolume(context) + 1, AudioManager.FLAG_SHOW_UI);
	}
	
	/**
	 * Decreases media volume
	 * 
	 * @param context
	 */
	public static void decreaseVolume(Context context) {
		adjustMediaVolume(context, getCurrentVolume(context) - 1, 0);
	}
	
	/**
	 * Decreases media volume and displays volume level
	 * 
	 * @param context
	 */
	public static void decreaseVolumeWithLevel(Context context) {
		adjustMediaVolume(context, getCurrentVolume(context) - 1, AudioManager.FLAG_SHOW_UI);
	}

	/** Returns maximum volume the media volume can have
	 * 
	 * @param context Context
	 * @return Maximum volume
	 */
	public static int getMaximumVolume(Context context) {
		return ((AudioManager) context.getSystemService(Context.AUDIO_SERVICE)).getStreamMaxVolume(AudioManager.STREAM_MUSIC);
	}
	
	/** Returns minimum volume the media volume can have
	 * 
	 * @param context Context
	 * @return Minimum volume
	 */
	public static int getMinimumVolume(Context context) {
		return 0;
	}
	
	private static void adjustMediaVolume(Context context, int volume, int flag) {
		final int MAX_VOLUME = getMaximumVolume(context);
		final int MIN_VOLUME = 0;

		if( volume < MIN_VOLUME ) {
			volume = MIN_VOLUME;
		} else if( volume > MAX_VOLUME ) {
			volume = MAX_VOLUME;
		}
		
		AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		if (audioManager != null) {
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, flag);
		}
	}
}
