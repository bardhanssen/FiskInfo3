function closeInfoDrawer(e) {
    e.getMap().getSelectionHandler().clearSelection();
    infoDrawer.close();
    //vesselInfoDrawer.close();
}

// Instantiating layer switcher

const zoomControl = Sintium.zoomControl();

const layerSwitcher = Sintium.sidebarLayerSwitcher({
    position: "left",
    dataLayerLabel: 'Datalag',
    baseLayerLabel: 'Kartlag'
});

const seaMapLayer = Sintium.wmsLayer({
    layerId: "Sjøkart",
    url: "https://opencache.statkart.no/gatekeeper/gk/gk.open?",
    params: {
        LAYERS: 'sjokartraster',
        VERSION: '1.1.1'
    },
    visible: true
});

const baseLayer = Sintium.baseLayer({
    layerId: "Verdenskart",
    layer: Sintium.getDefaultLightTileLayer(),
    darkLayer: Sintium.getDefaultDarkTileLayer()
});

// Instantiating map
const map = Sintium.map({
    domId: "map",
    layers: [baseLayer, seaBottomInstallationsLayer, maritimeBordersLayer, fishRegulationsGroup, seismicGroup, iceGroup, tradeAreaGroup, seaMapLayer],
    use: [infoDrawer, layerSwitcher],
    controls: [zoomControl],
    zoomOnClusterClick: true,
    onClickEmptySpace: closeInfoDrawer,
    onClickCluster: closeInfoDrawer,
    hitTolerance: 4,
    rotation: false
});

function addToolsAndVesselsLayerToMap() {
    map.layer(toolsLayer);
    map.layer(vesselsLayer);
    map.use(vesselInfoDrawer);
}
