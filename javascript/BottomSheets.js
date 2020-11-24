const infoTemplate = Sintium.templateWidget({
    domId: "info-template"
});

const infoDrawer = Sintium.drawer({
    position: 'bottom',
    widgets: [infoTemplate],
    previewSize: 72,
    size: '100%'
});

const vesselInfoTemplate = Sintium.templateWidget({
    domId: "vessel-info-template"
});

const vesselInfoDrawer = Sintium.drawer({
    position: 'bottom',
    widgets: [vesselInfoTemplate],
    previewSize: 72,
    size: '100%'
});

function closeSheetCallBack() {
    map.getSelectionHandler().clearSelection();
}