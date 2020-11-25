
function isLayerSwitcherOpen() {
    return layerSwitcher.isOpen();
}

function closeLayerSwitcher() {
    layerSwitcher.close()
}

function isBottomSheetOpen() {
    return infoDrawer.isOpen() || vesselInfoDrawer.isOpen();
}

function closeBottomSheet() {
    infoDrawer.close();
    vesselInfoDrawer.close();
    map.getSelectionHandler().clearSelection();
}

function getToolColors() {
    return JSON.stringify(toolsLayerColors);
}

function requestToolColors() {
    if (!!App.setToolColors) {
        const stringifiedColors = getToolColors();
        App.setToolColors(stringifiedColors);
    } else {
        console.warn('Before calling requestColors, App must implement setToolColors')
    }
}

function getLayerNames() {
    const layerNames = map.getLayerHandler().getLayerNames().slice().reverse();
    return JSON.stringify(layerNames);
}

function requestLayerNames() {
    if (!!App.setLayerNames) {
        const stringifiedLayerNames = getLayers();
        App.setLayerNames(stringifiedLayerNames);
    } else {
        console.warn('Before calling requestLayers, App must implement setLayers')
    }
}

function getVisibleLayerNames() {
    const layerNames = map.getLayerHandler().getVisibleLayerNames().slice().reverse();
    return JSON.stringify(layerNames);
}

function requestVisibleLayerNames() {
    if (!!App.setVisibleLayerNames) {
        const stringifiedLayerNames = getVisibleLayerNames();
        App.setVisibleLayerNames(stringifiedLayerNames);
    } else {
        console.warn('Before calling requestVisibleLayerNames, App must implement setVisibleLayerNames')
    }
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
