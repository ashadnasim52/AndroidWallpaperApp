
# Add this global rule
-keepattributes Signature

# This rule will properly ProGuard all the model classes in
# the package com.yourcompany.models. Modify to fit the structure
# of your app.
-keepclassmembers byashad.qoutespicture.picture.quotes.picturequotes.** {
  *;
}

-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }