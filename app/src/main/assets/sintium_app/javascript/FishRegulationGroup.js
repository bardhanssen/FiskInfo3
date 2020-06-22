function jMessageSelectionFunction(e) {
    selectedFeature = e.popFeature();
    const record = e.popRecord();
    infoTemplate.setData({
        title: record.get("name"),
        subTitle: "Midlertidig stengt område",
        description: record.get("description"),
        info: {
            "Stengt fra dato": formattedDate(record.get("closed_date")),
            "Stengt for": record.get("type_name"),
            "Fiskegruppe": record.get("speciestype_name"),
            "Område": record.get("area_name"),
            "J-melding": record.get("jmelding_name")
        },
        moreInfoFish: true
    });
    infoDrawer.open();
}

function prohibitedAreasSelectionFunction(e) {
    selectedFeature = e.popFeature();
    const record = e.popRecord();
    infoTemplate.setData({
        title: record.get( "navn"),
        subTitle: "Forbudsområde - Korallrev",
        description: record.get("info")
    });

    infoDrawer.open();
}

function fjordLinesSelectionFunction(e) {
    selectedFeature = e.popFeature();
    const record = e.popRecord();
    infoTemplate.setData({
        title: "Kysttorsk",
        subTitle: "Fjordlinjer - Kysttorsk",
        description: "Fra " + record.get("start_point_description") + " til " + record.get("end_point_description")
    });
    infoDrawer.open();
}

// Instantiating j messages layer
const jMessagesSource = Sintium.dataSource({
    url: "https://www.barentswatch.no/api/v1/geodata/download/jmelding/?format=JSON",
    useThread: false
});

const jMessagesLayer = Sintium.vectorLayer2({
    layerId: "J-Meldinger - stengte omr.",
    dataSource: jMessagesSource,
    visible: false,
    style: {
        single: {
            fillColor: "rgba(255, 255, 255, 0.3)",
            strokeColor: "rgba(144, 237, 125, 1.0)"
        }
    },
    selectedStyle: {
        single: {
            fillColor: "rgba(144, 237, 125, 0.5)",
            strokeColor: "rgba(144, 237, 125, 1)"
        }
    }
});
jMessagesLayer.addSelection({
    selector: "single",
    condition: "click",
    callback: jMessageSelectionFunction
});

// Instantiating prohibited areas layer
const prohibitedAreasSource = Sintium.dataSource({
    url: "https://www.barentswatch.no/api/v1/geodata/download/coralreef/?format=JSON",
});

const prohibitedAreasLayer = Sintium.vectorLayer2({
    layerId: "Forbudsområde - Korallrev",
    dataSource: prohibitedAreasSource,
    visible: false,
    style: {
        single: {
            fillColor: "rgba(255, 255, 255, 0.3)",
            strokeColor: "rgba(228, 211, 84, 1.0)"
        }
    },
    selectedStyle: {
        single: {
            fillColor: "rgba(228, 211, 84, 0.5)",
            strokeColor: "rgba(228, 211, 84, 1)"
        }
    }
});

prohibitedAreasLayer.addSelection({
    selector: "single",
    condition: "click",
    callback: prohibitedAreasSelectionFunction
});

// Instantiating fjord lines layer
const fjordLinesSource = Sintium.dataSource({
    url: "https://www.barentswatch.no/api/v1/geodata/download/coastalcodregulations/?format=JSON",
});

const fjordLinesLayer = Sintium.vectorLayer2({
    layerId: "Fjordlinjer - kysttorsk",
    dataSource: fjordLinesSource,
    visible: false,
    style: {
        single: {
            fillColor: "#2b908f",
            strokeSize: 2
        }
    },
    selectedStyle: {
        single: {
            fillColor: "#2b908f",
            strokeSize: 4
        }
    }
});

fjordLinesLayer.addSelection({
    selector: "single",
    condition: "click",
    callback: fjordLinesSelectionFunction
});


// Instantiating fish regulations group
const fishRegulationsGroup = Sintium.layerGroup("Fiskerireguleringer", jMessagesLayer, prohibitedAreasLayer, fjordLinesLayer);
