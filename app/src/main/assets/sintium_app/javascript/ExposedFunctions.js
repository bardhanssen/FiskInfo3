
function getColors() {
    const stringifiedColors = JSON.stringify(toolsLayerColors);
    if (!!App.setToolColors) {
        App.setToolColors(stringifiedColors);
    }
    return (stringifiedColors);
}

function getLayers() {
    const layerNames = map.getLayerHandler().getLayerNames().slice().reverse();
    const stringifiedLayerNames = JSON.stringify(layerNames);
    if (!!App.setLayers) {
        App.setLayers(stringifiedLayerNames);
    }
    return (stringifiedLayerNames);
}

function toggleLayers(layers) {
    const layerHandler = map.getLayerHandler();
    const layerNames = layerHandler.getLayerNames();
    layerNames.forEach(layer => {
        const visibility = layers.indexOf(layer) >= 0;
        layerHandler.setLayerVisibleById(layer, visibility);
    });
}

function toggleLayer(layer, visibility) {
    const layerHandler = map.getLayerHandler();
    layerHandler.setLayerVisibleById(layer, visibility);
}

function fail() {
    alert("Noe gikk galt, venligst sjekk om du har internett- eller Ggps (gps, glonass osv) forbindelse");
}

function populateUserPosition(callback) {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(callback, fail, {timeout: 60000});
        return true;
    } else {
        return false;
    }
}

function zoomToUserPosition() {
    populateUserPosition(function (position) {
        const userPosition = ol.proj.transform([position.coords.longitude, position.coords.latitude], 'EPSG:4326', 'EPSG:3857');
        map.zoomToCoordinates([position.coords.longitude, position.coords.latitude], 10)
    });
}

function closeBottomSheet() {
    infoDrawer.close();
    vesselInfoDrawer.close();
    map.getSelectionHandler().clearSelection();
}

function setToken(_token) {
    token = _token;
    if (!authenticator) {
        authenticator = Sintium.OAuthAuthenticator(tokenApiURL, token);
        setupToolsLayer();
        setupVesselsLayer();
        buildVesselLookup();
        addToolsAndVesselsLayerToMap();
    } else {
        console.log('update with setToken');
        authenticator.setToken(_token);
    }
}

if (!!App.ready) {
    App.ready();
}
