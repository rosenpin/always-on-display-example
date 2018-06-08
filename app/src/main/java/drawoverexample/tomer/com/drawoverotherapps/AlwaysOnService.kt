package drawoverexample.tomer.com.drawoverotherapps

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import drawoverexample.tomer.com.drawoverotherapps.SamsungResolver.Companion.isSamsung

class AlwaysOnService : Service() {
	
	var windowParams: WindowManager.LayoutParams? = null
	
	override fun onBind(intent: Intent): IBinder? {
		return null
	}
	
	
	/**
	 * Get the layout params depending on the device we are running on
	 */
	private fun getAppropriateLayoutParams(): Int {
		return when {
			Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
			isSamsung -> WindowManager.LayoutParams.TYPE_TOAST
			else -> WindowManager.LayoutParams.TYPE_SYSTEM_ERROR
		}
	}
	
	override fun onStartCommand(origIntent: Intent?, flags: Int, startId: Int): Int {
		if (windowParams == null) {
			val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager // Get the window manager, we need it to add windows
			val alwaysOnView = OverlayDisplayView(this) // Initialize our view
			windowParams = WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,
					getAppropriateLayoutParams(),
					OverlayDisplayView.FLAGS, -2) // Create the layout params needed by the window manager
			windowParams?.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN // Hide the navigation bar
			windowManager.addView(alwaysOnView, windowParams) // Add our view as a window using the windows manager
			Handler().postDelayed({
				// Remove the view.
				// The phone will be unusable if you don't do it.
				// You can also create a touch listener for the view or something of the sort to create some kind of a stop condition for the service.
				windowManager.removeView(alwaysOnView)
				stopSelf()
				Toast.makeText(this, "Dismissing the service automatically.", Toast.LENGTH_LONG).show()
			}, 20000)
		}
		return super.onStartCommand(origIntent, flags, startId)
	}
}
