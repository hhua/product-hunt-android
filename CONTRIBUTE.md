## Set up dev environment
1. Download [Android Studio](http://developer.android.com/sdk/index.html)
2. Download [Android SDK](http://developer.android.com/sdk/installing/index.html?pkg=studio) and config
3. Get [Additional SDK packages](http://developer.android.com/sdk/installing/adding-packages.html)
4. Set up [Virtual Devices](http://developer.android.com/tools/devices/index.html) / personally I recommend [Genymotion](https://www.genymotion.com/)
5. Or you can use your real device for [test](http://developer.android.com/tools/device.html)

## How to run "Oh My Product Hunt"

### Solution One: For non-git users
1. Download the "Oh My Product Hunt" [zip package](https://github.com/hhua/product-hunt-android/archive/master.zip)
2. Unzip the package
3. Use Android Studio to open the unpackaged folder. Android Studio will build the app for you
4. Create an app on [Product Hunt API console](https://api.producthunt.com/v1/oauth/applications). Keep your API key, API secret and callback url
5. Fill your information [here](https://github.com/hhua/product-hunt-android/blob/master/app/src/main/java/com/hhua/android/producthunt/utils/ApiConfig.java#L5)
6. You also need to update [AndroidManifest](https://github.com/hhua/product-hunt-android/blob/master/app/src/main/AndroidManifest.xml#L39-L41)
7. [Build the app](http://developer.android.com/tools/building/building-studio.html), and you should see app running in either virtual device or your Android phone

### Solution Two: For git users
1. git clone git@github.com:hhua/product-hunt-android.git
3. Use Android Studio to open the downloaded folder. Android Studio will build the app for you.
4. Create an app on [Product Hunt API console](https://api.producthunt.com/v1/oauth/applications). Keep your API key, API secret and callback url
5. Fill your information [here](https://github.com/hhua/product-hunt-android/blob/master/app/src/main/java/com/hhua/android/producthunt/utils/ApiConfig.java#L5)
6. You also need to update [AndroidManifest](https://github.com/hhua/product-hunt-android/blob/master/app/src/main/AndroidManifest.xml#L39-L41)
7. [Build the app](http://developer.android.com/tools/building/building-studio.html), and you should see app running in either virtual device or your Android phone

## Contribute
1. Make a PR against master branch

