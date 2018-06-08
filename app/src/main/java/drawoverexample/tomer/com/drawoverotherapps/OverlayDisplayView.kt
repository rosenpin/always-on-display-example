package drawoverexample.tomer.com.drawoverotherapps

import android.content.Context
import android.graphics.Color
import android.os.PowerManager
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView

/**
 * DrawOverOtherApps
 * Created by Tomer Rosenfeld on 6/8/18.
 */
class OverlayDisplayView(context: Context) : LinearLayout(context) {
	companion object {
		const val FLAGS: Int =
				WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED or
						WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
						WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM or
						WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
						WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
						WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
						WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
						WindowManager.LayoutParams.FLAG_DIM_BEHIND
	}
	
	override fun onAttachedToWindow() {
		super.onAttachedToWindow()
		// Create an example text view
		val exampleTextView = TextView(context)
		setBackgroundColor(Color.BLACK)
		exampleTextView.text = "Hello Screen!"
		exampleTextView.textSize = 120f
		exampleTextView.setTextColor(Color.WHITE)
		// Add the view to the layout
		this.addView(exampleTextView)
		// Acquire the wake lock, in other words, prevent the device from falling asleep.
		// TODO: Don't forget to dismiss it when the service should be stopped!!!!
		val stayAwakeWakeLock = (context.getSystemService(Context.POWER_SERVICE) as PowerManager)
				.newWakeLock(268435482, "Stay awake tag")
		stayAwakeWakeLock.setReferenceCounted(false)
		stayAwakeWakeLock.acquire()
	}
}