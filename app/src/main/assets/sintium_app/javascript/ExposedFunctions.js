
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

token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkU1MDY0NDAxN0NGNDVDQjU5NDRCRDE0ODhCRkU4QjAyQTc2MDc2NzMiLCJ0eXAiOiJhdCtqd3QiLCJ4NXQiOiI1UVpFQVh6MFhMV1VTOUZJaV82TEFxZGdkbk0ifQ.eyJuYmYiOjE2MDEyODg1NjEsImV4cCI6MTYwMTI5MjE2MSwiaXNzIjoiaHR0cHM6Ly9pZC5iYXJlbnRzd2F0Y2gubmV0IiwiYXVkIjoiYXBpIiwiY2xpZW50X2lkIjoic2ludGVmZmlza2luZm9hcHAiLCJzdWIiOiJhOGRjOGQ1ZS02ODE3LTQyZDUtYTYwZi01NDY2NWE3OWM4NmQiLCJhdXRoX3RpbWUiOjE2MDA0MTE2MzAsImlkcCI6ImxvY2FsIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidG9yZS5zeXZlcnNlbkBzaW50ZWYubm8iLCJyb2xlIjoidXNlciIsImVtYWlsIjoidG9yZS5zeXZlcnNlbkBzaW50ZWYubm8iLCJzY29wZSI6WyJvcGVuaWQiLCJhcGkiLCJvZmZsaW5lX2FjY2VzcyJdLCJhbXIiOlsicHdkIl19.cL-UEFi8d0evPINQRjKeQR_gNPvpYkPjoDztgxPXkq6VQhJywC9i9ypehaxCAvBoqmCr0l3cf9ah3GMZLrMXF_0NGk5JcVBW-I6UBGZkNFEsKVA9gFL9s8_HTxtUhs7bk_xOqU4C1bRxY88xUvY17d0l_4x_qYmSlip78LVYGj7rIkC9TEar3fzqYxr6dAc5iDHtgEC7rEfg7F_gwRobkYINJGcXAGErmCSPWnMcPQv8oEUVBfkaTkC3xiDVaEgK0veoW5dbmCY8_p5ipuI1PYN6w64AGzSINGGzMnDmi3gZRcMDwTbdwhGmdZQUyKhMluCXGWRtiouSnTGMvop95_6OiHnh7Mnzc3zznCnwxa0_kvlx5IOMnpCzWICAKuVY7bnI3fUkLsXZZ_htfTD-KGWF4NQNrUCT8yvInc11NIXej0mE-6QaCQExcxNJcUJdU_zvFFJ5orHqUlecrfyeowOKJRMtVqsYJidmfRFew-UvRnGuHGIsxk4NzCJ3lqmYfLRrIkoMV71NHX-qzC7NLkFDs1v786h-5RAxWBQUK0W-B0it7PxNkKtF-4iKJbY8f7UyGzildu31dKOjTuCvK9cLX_QJwl9nULafE73ATQEtCKnmhFSFko3g9GCG6qoEB3sQzRWO-IMhq4qtH9YHTO1_p6MVht42NPC-YHj36cs";
setToken(token);

if (!!App.ready) {
    App.ready();
}
