# FiskInfo3 for Android
The goal of FiskInfo is to be a one-stop-shop for digital distribution of important information for the fishing fleet.

FiskInfo is developed by [SINTEF](https://www.sintef.no/) in collaboration with [Fiskeri- og havbruksnæringens Forskningsfond (FHF)](http://www.fhf.no/) and [Barentswatch](https://www.barentswatch.no/). FiskInfo is available as a [web site](https://www.barentswatch.no/en/fishinfo/) from Barentswatch and as a mobile app for [Android](https://play.google.com/store/apps/details?id=fiskinfoo.no.sintef.fiskinfoo&hl=no) and [iOS](https://itunes.apple.com/no/app/fiskinfo/id1081341585?mt=8). 

This repository contains a new version of the Android that is under development. The new version will be a rewrite of the current Android app
(available in the [SINTEF-SE/Fiskinfo](https://github.com/SINTEF-SE/Fiskinfo) repository)
targeting more recent versions of the Android SDK, and will be based on Kotlin and AndroidX / JetPack.

Currently this version mainly contains the following:
* New navigation structure with empty placeholders for main fragments
* Initial prototype of Snapfish - a new feature for sharing echograms

The map implementation is based on the Sintium map, which is contained as a git subtree in this repository. To update the subtree, use
git subtree pull --prefix app/src/main/assets/sintium_app https://github.com/torbval/fiskinfo3-sintium-app.git master --squash