package drawoverexample.tomer.com.drawoverotherapps

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager

class AlwaysOnActivity : AppCompatActivity() {
	
	private val flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
			View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
			View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
			View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
			View.SYSTEM_UI_FLAG_FULLSCREEN or
			View.SCREEN_STATE_ON or
			View.KEEP_SCREEN_ON
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_always_on)
		fullScreen()
		val alwaysOnView = OverlayDisplayView(this)
		setContentView(alwaysOnView)
	}
	
	/**
	 * Turn to full screen to make the illusion that the display is an entire overlay rather than an activity
	 */
	private fun fullScreen() {
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
				WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
				WindowManager.LayoutParams.FLAG_FULLSCREEN or
				WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
				WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
		
		window.decorView.systemUiVisibility = flags
		
		val decorView = window.decorView
		decorView
				.setOnSystemUiVisibilityChangeListener { visibility ->
					if (visibility and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
						decorView.systemUiVisibility = flags
					}
				}
	}
}
