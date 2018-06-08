package drawoverexample.tomer.com.drawoverotherapps

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

class MainActivity : AppCompatActivity() {
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		checkAndAskDrawOverPermission() // First make sure we have the correct permissions
		startOverlayDisplay() // Start the overlay display, you can do it even if the screen is locked!
	}
	
	/**
	 * Since Android 6.0 we need to ask for the permission to show our app above other apps
	 */
	private fun checkAndAskDrawOverPermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
			if (!Settings.canDrawOverlays(this)) {
				val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
				intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
				startActivity(intent)
			}
	}
	
	/**
	 * Get the appropriate always on display,
	 *  Activity on Android O, service if lower (service is preferable because we have more conrtol over it)
	 */
	private fun getAlwaysOnDisplayIntent(): Intent {
		return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			Intent(this, AlwaysOnActivity::class.java)
		} else {
			Intent(this, AlwaysOnService::class.java)
		}
	}
	
	
	/**
	 * Figure out what device we are running on and start the overlay display appropriately
	 */
	private fun startOverlayDisplay() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
			if (!Settings.canDrawOverlays(this)) {
				Toast.makeText(this, "Can't draw over other apps!", Toast.LENGTH_LONG).show()
				return
			}
		}
		val intent = getAlwaysOnDisplayIntent()
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			// Hide the activity from the user, create the illusion that the activity is not actually an activity, but a legitimate overlay display
			intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
			startActivity(intent)
		} else {
			startService(intent)
		}
	}
}
