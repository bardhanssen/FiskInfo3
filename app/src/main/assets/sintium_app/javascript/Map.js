function closeInfoDrawer(e) {
    e.getMap().getSelectionHandler().clearSelection();
    infoDrawer.close();
}

// Instantiating layer switcher

var zoomControl = Sintium.zoomControl();

var layerSwitcher = Sintium.sidebarLayerSwitcher({
    position: "left"
});

// Instantiating map
var map = Sintium.map({
    domId: "map",
    layers: [toolsLayer, vesselsLayer, seaBottomInstallationsLayer, maritimeBordersLayer, fishRegulationsGroup, seismicGroup, iceGroup, tradeAreaGroup],
    use: [infoDrawer, vesselInfoDrawer, layerSwitcher],
    controls: [zoomControl],
    zoomOnClusterClick: true,
    onClickEmptySpace: closeInfoDrawer,
    onClickCluster: closeInfoDrawer,
    hitTolerance: 4,
    rotation: false
});