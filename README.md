Movie Finder
===============================================================

Before you install the application
----------------------------------
You will need the following to install and run the .apk file:
- Android SDK (http://developer.android.com/sdk)
- An Android device allowing USB debugging or an Android Emulator


How to run and install the application
---------------------------------------
In the app/ directory you will find a file named app.apk.
This is the file used to distribute and install our application.

Either make sure to navigate in to the platform-tools/ directory
or make it available in your PATH.


#### Follow this if you have an Android device:

1. Make sure it's setup and connected via USB
2. Run the following command:

adb -d install path/to/app.apk


#### Follow this if you have an Android Emulator:

1. Execute the android tool with avd option:
android avd
2. In the Virtual Devices view, select an AVD and click Start.
3. Install the apk from your SDK's tools/ folder:
adb install path/to/app.apk

A note on Facebook
-----------------------------------
#### The Facebook Application
We've created a regular application on Facebook Developer called BlueJava
that now runs in developer mode. Therefore integration with the Facebook app
only works for authorized devices. Authorizing a new device is done by adding a
key hash.

If you want access and try it out, just contact Tobias (tobias@andersen.net).

#### Faceook Android SDK
Since our application uses Facebook to both login and
post content to Facebook, we use the Facebok Android SDK.

The person adding it to our project recieved 40 000 lines of code in gitinspector,
so we decided to add it to our .gitignore. It now lies as a zip file in the libs/ directory instead.

If you're having trouble getting it to work, just contact Tobias (tobias@andersen.net).
