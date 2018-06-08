package drawoverexample.tomer.com.drawoverotherapps

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import java.util.*

/**
 * DrawOverOtherApps
 * Created by Tomer Rosenfeld on 6/8/18.
 * No Android project is complete without Samsung specific conflicts <3
 */
class SamsungResolver {
	companion object {
		/*
		We handle overlays differently on Samsung devices,
		because Samsung is a terrible company with terrible developers who break Android
	    */
		private var _isSamsung: Boolean? = null
		internal val Context.isSamsung: Boolean
			get() {
				if (_isSamsung != null)
					return _isSamsung as Boolean
				else
					_isSamsung = isLauncherInstalled(this, "com.sec.android.app.launcher")
				return _isSamsung as Boolean
			}
		
		/**
		 * Check if a launcher application is installed
		 */
		fun isLauncherInstalled(context: Context, packageName: String): Boolean {
			val filterCategory = IntentFilter(Intent.ACTION_MAIN)
			filterCategory.addCategory(Intent.CATEGORY_HOME)
			
			val filters = ArrayList<IntentFilter>()
			filters.add(filterCategory)
			
			val activities = ArrayList<ComponentName>()
			val packageManager = context.packageManager
			
			packageManager.getPreferredActivities(filters, activities, null)
			return activities.any { packageName == it.packageName }
		}
	}
}