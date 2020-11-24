zIndex = -1;

function vesselSelectionFunction(e) {
    if (selectedFeature) unsetSelectedFeature();
    selectedFeature = e.popFeature();
    const coordinate = selectedFeature.getCenterCoordinate();
    const record = e.popRecord();
    const tools = getMaxThreeToolsFromCallSign(record.get("callsign"));
    const destination = record.get("destination");
    const name = record.get("name");

    vesselInfoTemplate.setData({
        title: !name ? "Mangler navn" : name,
        subtitle: vesselCodeToShipTypeName(record),
        shipType: record.get("shipType"),
        sog: record.get("sog"),
        cog: record.get("cog"),
        info: {
            "Signal mottatt": formattedDate(record.get("timeStamp")),
            "Posisjon": formatLocation(coordinate),
            "Destinasjon": !destination ? "Mangler destinasjon" : destination,
            "Marinogram": marinogramLink(coordinate)
        },
        tools: tools,
        hasTools: tools.length > 0
    });

    const elems = document.querySelectorAll('.collapsible');
    M.Collapsible.init(elems, {});
    vesselInfoDrawer.open(null, closeSheetCallBack);
}

const selectedText = new ol.style.Text({
   text: "",
   font: "bold 13px sans-serif",
   offsetY: 28,
   fill: new ol.style.Fill({
       color: 'black'
   })
});

const fillFishingVessel = new ol.style.Fill({ color: "#4b0000" });
const strokeFishingVessel = new ol.style.Stroke({
    color: "#7d0000",
    width: 2
});

const fillOtherVessel = new ol.style.Fill({ color: "#7c7c80" });
const strokeOtherVessel = new ol.style.Stroke({
    color: "#adadb2",
    width: 2
});

const clusterStyle = new ol.style.Style({
    image: new ol.style.Circle({
        radius: 10,
        fill: new ol.style.Fill({ color: "#7c7c80" }),
        stroke: new ol.style.Stroke({ color: "#aeaeb3" })
    }),
    text: new ol.style.Text({
        text: "",
        fill: new ol.style.Fill({
            color: "white"
        })
    })
});

const pointStyleFishingVessel = new ol.style.Style({
    image: new ol.style.Circle({
        radius: 5,
        fill: fillFishingVessel,
        stroke: strokeFishingVessel
    })
});

const pointIconStyleFishingVessel = new ol.style.Style({
    image: new ol.style.Icon(({
        anchor: [0.5, 0.5],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
        src: './images/boat_brown.svg',
        rotation: 0,
        scale: 0.8
    }))
});

const pointStyleOtherVessel = new ol.style.Style({
    image: new ol.style.Circle({
        radius: 5,
        fill: fillOtherVessel,
        stroke: strokeOtherVessel
    })
});

const pointIconStyleOtherVessel = new ol.style.Style({
    image: new ol.style.Icon(({
        anchor: [0.5, 0.5],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
        src: './images/boat_gray.svg',
        rotation: 0,
        scale: 0.8
    }))
});

const pointStyleFishingVesselSelected = new ol.style.Style({
    image: new ol.style.Icon(({
        anchor: [0.5, 0.5],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
        src: './images/boat_brown.svg',
        rotation: 0,
        scale: 1.3
    })),
    text: selectedText,
    zIndex: Number.POSITIVE_INFINITY
});

const pointStyleOtherVesselSelected = new ol.style.Style({
    image: new ol.style.Icon(({
        anchor: [0.5, 0.5],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
        src: './images/boat_gray.svg',
        rotation: 0,
        scale: 1.3
    })),
    text: selectedText,
    zIndex: Number.POSITIVE_INFINITY
});

function vesselStyleFunction(feature, resolution) {
    const key = feature.getFeatureKey();
    const selected = feature.getSelected();
    const zoom = Math.ceil( (Math.log(resolution) - Math.log(156543.03390625) ) / Math.log(0.5));

    if (feature.isCluster()) {
        const clusterCount = feature.getRecordKeys().length;
        const clusterText = nFormatter(clusterCount, 0);
        clusterStyle.setZIndex(zIndex--);
        clusterStyle.getText().setText(clusterText);
        return clusterStyle;
    } else {
        const record = vesselsSource.getDataContainer().getRecord(key);
        const isVesselShip = record.get("shipType") === 30;
        if (selected) {
            const name = record.get("name") || "Mangler navn";
            const selectedStyle = isVesselShip ? pointStyleFishingVesselSelected : pointStyleOtherVesselSelected;
            selectedStyle.getText().setText(name);
            const rotation = degreesToRadians(record.get("cog"));
            selectedStyle.getImage().setRotation(rotation);
            return selectedStyle;
        } else {
            let style;

            if (zoom < unrollAtZoom) {
                style = isVesselShip ? pointStyleFishingVessel : pointStyleOtherVessel;
            } else {
                style = isVesselShip ? pointIconStyleFishingVessel : pointIconStyleOtherVessel;
                const rotation = degreesToRadians(record.get("cog"));
                style.getImage().setRotation(rotation);
            }

            style.setZIndex(zIndex--);
            return style;
        }
    }
}

// Instantiating vessel layer

let vesselsSource;
let vesselsLayer;

function setupVesselsLayer() {

    vesselsSource = Sintium.dataSource({
        url: "https://pilot.barentswatch.net/bwapi/v1/geodata/ais/positions?xmin=0&ymin=25&xmax=60&ymax=95",
        //url: "https://www.barentswatch.no/api/v1/geodata/ais/openpositions?xmin=0&ymin=25&xmax=60&ymax=95",
        authenticator: authenticator
    });

    vesselsLayer = Sintium.vectorLayer2({
        layerId: 'AIS',
        dataSource: vesselsSource,
        clustered: true,
        lonProperty: "lon",
        latProperty: "lat",
        visible: false,
        lazyLoad: false,
        useThread: true,
        unrollClustersAtZoom: unrollAtZoom,
        styleFunction: vesselStyleFunction,
        clusterRadius: 150
    });

    vesselsLayer.addSelection({
        selector: "single",
        condition: "click",
        callback: vesselSelectionFunction
    });


    vesselsLayer.getFeatureSource().onFeatureChange(function(features) {
        features.forEach(selectFeature);
    });

}

function selectFeature(feature) {
    if (feature.getFeatureKey() !== selectedKey) return;
    map.getSelectionHandler().clearSelection();
    map.getSelectionHandler().setSelection(feature);
    selectedKey = null;
}

function showVesselAndBottomsheet(callsignal) {
    try {
        selectedKey = vesselMap[callsignal].key;
        const record = vesselsSource.getDataContainer().getRecord(selectedKey);
        if (!record) return;
        const coordinates = [record.get("lon"), record.get("lat")];
        map.zoomToCoordinates(coordinates, unrollAtZoom);
        vesselsLayer.getFeatureSource().getFeatures().forEach(selectFeature);
    } catch (err) {
        alert(err);
    }
}
