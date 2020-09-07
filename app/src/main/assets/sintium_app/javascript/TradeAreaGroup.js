// Instantiating trade area bank fishing layer
const tradeAreaBankFishingSource = Sintium.dataSource({
    url: "https://www.barentswatch.no/api/v1/geodata/download/tradearea-bankfiske1/?format=JSON",
});

const tradeAreaBankFishingLayer = Sintium.vectorLayer2({
    layerId: "Fartsområde bankfiske 1",
    dataSource: tradeAreaBankFishingSource,
    visible: false,
    style: {
        single: {
            fillColor: "#1c5385",
            strokeSize: 3
        }
    }
});

// Instantiating trade area bank fishing layer
const tradeAreaSmallCostSource = Sintium.dataSource({
    url: "https://www.barentswatch.no/api/v1/geodata/download/tradearea-litenkystfart/?format=JSON"
});

const tradeAreaSmallCostLayer = Sintium.vectorLayer2({
    layerId: "Fartsområde liten kystfart",
    dataSource: tradeAreaSmallCostSource,
    visible: false,
    style: {
        single: {
            fillColor: "#1c5385",
            strokeSize: 3
        }
    }
});

// Instantiating ice group
const tradeAreaGroup = Sintium.layerGroup("Fartsområder", tradeAreaBankFishingLayer, tradeAreaSmallCostLayer);
