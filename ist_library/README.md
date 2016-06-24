#如何使用ist_library
##1 在build.gradle里的dependencies内粘贴如下代码：compile 'com.weather.widget.lib:ist:1.0.46'
##2 在string.xml里增加如下string

```xml
<string name="ist_facebook_app_id">**************_</string>
<string name="ist_default_placement_id">**************_**************</string>
<string name="ist_token_id">****************************</string>
<string name="ist_default_placement_interstitial_id">**************_**************</string>
```
应用内没有插屏广告则不需要增加：ist_default_placement_interstitial_id