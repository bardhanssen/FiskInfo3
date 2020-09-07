function ongoingSeismicActivitySelectionFunction(e) {
    if (selectedFeature) unsetSelectedFeature();

    selectedFeature = e.popFeature();
    const record = e.popRecord();

    infoTemplate.setData({
        title: record.get("surveyname"),
        subTitle: "Pågående seismikk",
        info: {
            "Område": record.get("geoarea"),
            "Seismikkfartøy": record.get("vesselall"),
            "Type": record.get("surmaintyp"),
            "Undertype": record.get("surparttyp"),
            "Periode": formattedTimePeriod(record.get("plnfrmdate"), record.get("plntodate")),
            "Ansvarlig selskap": record.get("compreport"),
            "Kilde": record.get("sourcetype"),
            "Sensortype": record.get("sensortype"),
            "Sensorantall": record.get("senslength"),
            "Sensorlengde": record.get("sensnote")
        }
    });

    infoDrawer.open();
}

function plannedSeismicActivitySelectionFunction(e) {
    if (selectedFeature) unsetSelectedFeature();

    selectedFeature = e.popFeature();
    const record = e.popRecord();
    infoTemplate.setData({
        title: record.get("surveyname"),
        subTitle: "Planlagt seismikk",
        info: {
            "Område": record.get("geoarea"),
            "Seismikkfartøy": record.get("vesselall"),
            "Type": record.get("surmaintyp"),
            "Undertype": record.get("surparttyp"),
            "Periode": formattedTimePeriod(record.get("plnfrmdate"), record.get("plntodate")),
            "Ansvarlig selskap": record.get("compreport"),
            "Kilde": record.get("sourcetype"),
            "Sensortype": record.get("sensortype"),
            "Sensorantall": record.get("senslength"),
            "Sensorlengde": record.get("sensnote")
        }
    });

    infoDrawer.open();
}

function ongoingElectromagneticSurveysSelectionFunction(e) {
    selectedFeature = e.popFeature();
    const record = e.popRecord();
    infoTemplate.setData({
        title: record.get("surveyname"),
        subTitle: "Pågående elektromagnetiske undersøkelser",
        info: {
            "Område": record.get("geoarea"),
            "Seismikkfartøy": record.get("vesselall"),
            "Type": record.get("surmaintyp"),
            "Undertype": record.get("surparttyp"),
            "Periode": formattedTimePeriod(record.get("plnfrmdate"), record.get("plntodate")),
            "Ansvarlig selskap": record.get("compreport"),
            "Kilde": record.get("sourcetype"),
            "Sensortype": record.get("sensortype"),
            "Sensorantall": record.get("senslength"),
            "Sensorlengde": record.get("sensnote")
        }
    });

    infoDrawer.open();
}

function plannedElectromagneticSurveysFunction(e) {
    selectedFeature = e.popFeature();
    const record = e.popRecord();
    infoTemplate.setData({
        title: record.get("surveyname"),
        subTitle: "Planlagte elektromagnetiske undersøkelser",
        info: {
            "Område": record.get("geoarea"),
            "Seismikkfartøy": record.get("vesselall"),
            "Type": record.get("surmaintyp"),
            "Undertype": record.get("surparttyp"),
            "Periode": formattedTimePeriod(record.get("plnfrmdate"), record.get("plntodate")),
            "Ansvarlig selskap": record.get("compreport"),
            "Kilde": record.get("sourcetype"),
            "Sensortype": record.get("sensortype"),
            "Sensorantall": record.get("senslength"),
            "Sensorlengde": record.get("sensnote")
        }
    });

    infoDrawer.open();
}

// Instantiating ongoing seismic activity layer
const ongoingSeismicActivitySource = Sintium.dataSource({
    url: "https://www.barentswatch.no/api/v1/geodata/download/npdsurveyongoing/?format=JSON",
});

const ongoingSeismicActivityLayer = Sintium.vectorLayer2({
    layerId: "Pågående seismikk",
    dataSource: ongoingSeismicActivitySource,
    visible: false,
    style: {
        single: {
            fillColor: "rgba(255, 255, 255, 0.3)",
            strokeColor: "rgba(119, 190, 149, 1.0)"
        }
    },
    selectedStyle: {
        single: {
            fillColor: "rgba(119, 190, 149, 0.5)",
            strokeColor: "rgba(119, 190, 149, 1.0)"
        }
    }
});

ongoingSeismicActivityLayer.addSelection({
    selector: "single",
    condition: "click",
    callback: ongoingSeismicActivitySelectionFunction
});

// Instantiating planned seismic activity layer
const plannedSeismicActivitySource = Sintium.dataSource({
    url: "https://www.barentswatch.no/api/v1/geodata/download/npdsurveyplanned/?format=JSON",
});

const plannedSeismicActivityLayer = Sintium.vectorLayer2({
    layerId: "Planlagt seismikk",
    dataSource: plannedSeismicActivitySource,
    visible: false,
    style: {
        single: {
            fillColor: "rgba(255, 255, 255, 0.3)",
            strokeColor: "rgba(237, 128, 128, 1.0)"
        }
    },
    selectedStyle: {
        single: {
            fillColor: "rgba(237, 128, 128, 0.5)",
            strokeColor: "rgba(237, 128, 128, 1)"
        }
    }
});

plannedSeismicActivityLayer.addSelection({
    selector: "single",
    condition: "click",
    callback: plannedSeismicActivitySelectionFunction
});

// Instantiating planned seismic activity layer
const ongoingElectromagneticSurveysSource = Sintium.dataSource({
    url: "https://www.barentswatch.no/api/v1/geodata/download/npdemsurveyongoing/?format=JSON",
    dataProjection: "EPSG:23032",
});

const ongoingElectromagneticSurveysLayer = Sintium.vectorLayer2({
    layerId: "Pågående elektromagnetiske undersøkelser",
    dataSource: ongoingElectromagneticSurveysSource,
    visible: false,
    style: {
        single: {
            fillColor: "rgba(255, 255, 255, 0.3)",
            strokeColor: "rgba(128, 133, 233, 1.0)"
        }
    },
    selectedStyle: {
        single: {
            fillColor: "rgba(128, 133, 233, 0.5)",
            strokeColor: "rgba(128, 133, 233, 1)"
        }
    }
});

ongoingElectromagneticSurveysLayer.addSelection({
    selector: "single",
    condition: "click",
    callback: ongoingElectromagneticSurveysSelectionFunction
});

// Instantiating planned seismic activity layer
const plannedElectromagneticSurveysSource = Sintium.dataSource({
    url: "https://www.barentswatch.no/api/v1/geodata/download/npdemsurveyplanned/?format=JSON",
    dataProjection: "EPSG:23032"
});

const plannedElectromagneticSurveysLayer = Sintium.vectorLayer2({
    layerId: "Planlagte elektromagnetiske undersøkelser",
    dataSource: plannedElectromagneticSurveysSource,
    visible: false,
    style: {
        single: {
            fillColor: "rgba(255, 255, 255, 0.3)",
            strokeColor: "rgba(241, 92, 128, 1.0)"
        }
    },
    selectedStyle: {
        single: {
            fillColor: "rgba(241, 92, 128, 0.5)",
            strokeColor: "rgba(241, 92, 128, 1)"
        }
    }
});

plannedElectromagneticSurveysLayer.addSelection({
    selector: "single",
    condition: "click",
    callback: plannedElectromagneticSurveysFunction
});

// Instantiating seismic group
const seismicGroup = Sintium.layerGroup("Seismikk", ongoingSeismicActivityLayer, plannedSeismicActivityLayer, ongoingElectromagneticSurveysLayer, plannedElectromagneticSurveysLayer);
