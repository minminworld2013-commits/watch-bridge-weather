# Watch Bridge Weather

Watch Bridge Weather is a **modified version of [Breezy Weather](https://github.com/breezy-weather/breezy-weather)**
(LGPL-3.0). It is not affiliated with or endorsed by the Breezy Weather project.

- Google Play: <https://play.google.com/store/apps/details?id=com.minminworld.watchbridgeweather>
- Privacy policy: <https://minminworld2013-commits.github.io/app-legal/watchbridgeweather.html>
- Contact: minminworld.com@gmail.com

## What is different from Breezy Weather

- Own name, application ID (`com.minminworld.watchbridgeweather`) and icons
- Weather data from MET Norway (CC BY 4.0) only; coordinates sent with max 4 decimals (MET terms of service)
- Location search and place names through the device geocoder (Android Geocoder)
- Approximate location only; no background location; no battery-optimization exemption request;
  permissions are not re-requested after the user has answered
- One banner ad (Google Mobile Ads) on the main screen, requested only after Google UMP consent allows it
- Weather can be sent to the Watch Bridge app on the same phone through the Gadgetbridge weather broadcast,
  with missing min/max temperatures filled from known values
- Korean menu names; the About screen marks the app as a modified version of Breezy Weather

The modified code lives mainly in `app/src/main/kotlin/org/breezyweather/wbw/` and is marked with `WBW` comments
elsewhere. The upstream README is kept as [README.breezy-weather.md](README.breezy-weather.md).

## License

Same as Breezy Weather: GNU LGPL-3.0 ([LICENSE](LICENSE)) with the additional terms in
[LICENSE_ADDITIONAL](LICENSE_ADDITIONAL). "Breezy Weather" and its logo belong to their owners and are used here
only to identify the original project.

## Building

Standard Android Gradle build (`./gradlew assembleBasicRelease`). Release signing and ad unit IDs are read from
local, uncommitted files (`keystore.properties`, `local.properties`); without them the build uses Google's public test
ad IDs.
