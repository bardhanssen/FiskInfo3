function toolsSelectionFunction(e) {
    if (selectedFeature) unsetSelectedFeature();

    selectedFeature = e.popFeature();
    const coordinate = selectedFeature.getCenterCoordinate();
    const record = e.popRecord();
    const toolTypeCode = record.get("toolTypeCode");
    const toolName = formatToolType(toolTypeCode);

    const vesselName = record.get("vesselName");
    const callSignal = record.get("ircs");

    infoTemplate.setData({
        title: toolName,
        subTitle: "Redskap",
        info: {
            "Tid i havet": formatDateDifference(record.get("setupDateTime")),
            "Satt": formattedDate(record.get("setupDateTime")),
            "Posisjon": formatLocation(coordinate),
            "Se Marinogram": marinogramLink(coordinate)
        },
        infoWithHeader: {
            "Om Eier": {
                "Fartøy": showVesselLink(callSignal, vesselName),
                "Telefon": record.get("vesselPhone"),
                "Kallesignal(IRCS)": callSignal,
                "MMSI": record.get("mmsi"),
                "IMO": record.get("imo"),
                "E-post": record.get("vesselEmail")
            }
        },
        moreInfoFish: true
    });

    infoDrawer.open(null, closeSheetCallBack);
}

const toolsLayerColors = [
    "#2b83ba",
    "#d4c683",
    "#abdda4",
    "#fdae61",
    "#6bb0af",
    "#d7191c",
    "#ea643f",
    "#222831"
];

let toolsSource;
let toolsLayer;

function setupToolsLayer() {
    toolsSource = Sintium.dataSource({
        url: "https://pilot.barentswatch.net/bwapi/v1/geodata/fishingfacility/",
        useCrossfilter: true,
        preprocess: data => JSON.parse(data).fishingFacilities,
        columns: {
            wktGeometry: "geometryWKT"
        },
        authenticator: authenticator
    });

    toolsLayer = Sintium.vectorLayer2({
        layerId: 'Redskap',
        dataSource: toolsSource,
        clusteredByProperty: "toolTypeCode",
        addPointToGeometries: true,
        visible: true,
        lazyLoad: true,
        useThread: true,
        unrollClustersAtZoom: unrollAtZoom,
        clusterRadius: 150,
        style: {
            colors: toolsLayerColors
        },
        selectedStyle: {
            single: {
                size: 18,
                shape: "triangle",
                textFromProperty: "toolTypeCode",
                textOffset: 28
            }
        }
    });

    toolsLayer.addSelection({
        selector: "single",
        condition: "click",
        callback: toolsSelectionFunction
    });

    toolsLayer.getFeatureSource().onFeatureChange(function(features) {
        features.forEach(selectFeature);
    }); 
}

function selectFeature(feature) {
    if (feature.getFeatureKey() !== selectedKey) return;
    map.getSelectionHandler().clearSelection();
    map.getSelectionHandler().setSelection(feature);
    selectedKey = null;
}

function locateTool(key) {
    selectedKey = key;
    const record = toolsSource.getDataContainer().getRecord(selectedKey);
    if (!record) return;
    const geometry = record.getGeometry();
    const coordinates = ol.extent.getCenter(geometry.getExtent());
    map.zoomToCoordinates(coordinates, unrollAtZoom);
    toolsLayer.getFeatureSource().getFeatures().forEach(selectFeature);
}
