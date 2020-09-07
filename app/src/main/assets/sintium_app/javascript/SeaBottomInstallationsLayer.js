function seaBottomInstallationsFunction(e) {
    if (selectedFeature) unsetSelectedFeature(selectedFeature);

    selectedFeature = e.popFeature();
    const record = e.popRecord();
    const coordinate = selectedFeature.getCenterCoordinate();
    infoTemplate.setData({
        title: record.get("facname"),
        subTitle: "Havbunnsinstallasjoner",
        info: {
            "Type": record.get("fackind"),
            "Funksjon": record.get("facfunc"),
            "Dybde": record.get("waterdepth"),
            "Tilhørende felt": record.get("belong2nm"),
            "Oppstart": formattedDate(record.get("dtstartup")),
            "Operatør": record.get("curopernam"),
            "Posisjon: ": formatLocation(coordinate),
            "Marinogram": marinogramLink(coordinate)
        }
    });
    infoDrawer.open();
    selectedFeature.setText(record.get("facname"), 28);
}

// Instantiating sea bottom installations layer
const seaBottomInstallationsSource = Sintium.dataSource({
    url: "https://www.barentswatch.no/api/v1/geodata/download/npdfacility/?format=JSON",
});

const seaBottomInstallationsLayer = Sintium.vectorLayer2({
    layerId: "Havbunnsinstallasjoner",
    dataSource: seaBottomInstallationsSource,
    visible: false,
    clustered: true,
    style: {
        single: {
            size: 5,
            shape: "circle",
            fillColor: "rgba(102, 204, 255, 1.0)",
            strokeColor: "rgba(8, 113, 114, 1.0)"
        },
        cluster: {
            fillColor: "rgba(102, 204, 255, 1.0)",
            strokeColor: "rgba(8, 113, 114, 1.0)",
            size: 13
        }
    },
    selectedStyle: {
        single: {
            size: 18,
            shape: "circle",
            fillColor: "rgba(102, 204, 255, 1.0)",
            strokeColor: "rgba(8, 113, 114, 1.0)"
        },
        cluster: {
            size: 13
        }
    }
});

seaBottomInstallationsLayer.addSelection({
    selector: "single",
    condition: "click",
    callback: seaBottomInstallationsFunction
});