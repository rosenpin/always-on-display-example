# AlwaysOnDisplayExample
An example of an always on display for android devices. This is just an example implementation and shouldn't actually be used. 

### Example behaviour

In android O+, an activity will be shown above all other elements indefinitely.

In android N- a service will be shown above all elements, in this example it will automatically be dismissed after 20 seconds.

### Extendability

If you want, for example, the always on display to only show up after the screen is locked, just call the startOverlayDisplay() function in an intent that will be triggered by a ACTION_SCREEN_OFF receiver. 

If you want it to happen on boot use a BOOT_COMPLETED receiver for example.

Read more about receivers here:

https://developer.android.com/reference/android/content/BroadcastReceiver

https://developer.android.com/guide/topics/manifest/receiver-element
