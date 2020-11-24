# fiskinfo3-sintium-app
### Top down

Functions available for each mobile app to interact with javascript code:

Function | Description
------------ | ------------ 
```getColors()``` | Get stringified list of colors for different tooltypes in tool layer
```getLayers()``` | Get stringified list of layer names
```toggleLayers(layers)``` | Takes a list of layers, layers in list will be toggled visible, layers not in layer will be toggled invisible.
```toggleLayer(layer, visibility)``` | Takes layer and toggles visibility
```fail()``` | Triggers fail alert in webview
```populateUserPosition(callback)``` | Calls navigator.geolocation.getCurrentPosition with the given callback
```zoomToUserPosition()``` | Zooms to user position
```closeBottomSheet()``` | Close web apps bottom sheet
```setToken(token)``` | Sets authentication token for the Vessels- and Toolslayer

#### Bottom up
Each mobile app must expose an interface to the javascript code (which must be available as the javascript object App.) 
This interface can but is not required to implement the following functions:

Function | Description
------------ | ------------ 
```App.setAutoCompleteData(stringifiedAutoCompleteData)``` | Shares search data with mobile app.
```App.toolsFinishedLoading()``` | Tells the mobile app that the webview has finished loading tools data.
```App.aisFinishedLoading()``` | Tells the mobile app that the webview has finished loading AIS data.
```App.ready()``` | Tells the mobile app that the webview is ready to accept token.
```App.setToolColors(stringifiedColors)``` | Gets called when the mobile app calls 'javascript:getColors()'
```App.setLayers(stringifiedLayerNames)``` | Gets called when the mobile app calls 'javascript:getLayers()'
