geoJSON = new ol.format.GeoJSON();

function buildVesselLookup() {
    vesselsSource
        .onDataAdded(function(dataContainer) {
            dataContainer.forEachRecord(function(vessel) {
                const callSign = vessel.get("callsign");
                const name = vessel.get("name");
                const key = vessel.key();

                if (vesselMap[callSign] === undefined)
                    vesselMap[callSign] = {
                        key: key
                    };
                else
                    vesselMap[callSign].key = key;

                vesselSearchData.push({
                    "name": name,
                    "callsign": callSign
                });
            });

            const autoCompleteData = JSON.stringify(vesselSearchData);
            if (!!App.setAutoCompleteData) {
                App.setAutoCompleteData(autoCompleteData);
            }
            if (!!App.aisFinishedLoading) {
                App.aisFinishedLoading();
            }
        }, true);

    toolsSource
        .onDataAdded(function(dataContainer) {
            dataContainer.forEachRecord(function(tool) {
                const callSign = tool.get("ircs");
                const toolTypeCode = tool.get("tooltypecode");
                const setupTime = tool.get("setupdatetime");
                const key = tool.key();

                const coordinate = getPositionFromGeometry(tool.getGeometry());

                const toolData = {
                    type: formatToolType(toolTypeCode),
                    key: key,
                    info: {
                        "Tid i havet": {
                            icon: "date_range",
                            value: formatDateDifference(setupTime)
                        },
                        "Satt": {
                            icon: "date_range",
                            value: formattedDate(setupTime)
                        },
                        "Posisjon": {
                            icon: "place",
                            value: formatLocation(coordinate)
                        }
                    }
                };

                if (callSign === null) return;

                if (vesselMap[callSign] === undefined)
                    vesselMap[callSign] = {};

                if (vesselMap[callSign].tools === undefined)
                    vesselMap[callSign].tools = [toolData];
                else
                    vesselMap[callSign].tools.push(toolData);
            });
            if (!!App.toolsFinishedLoading) {
                App.toolsFinishedLoading();
            }
        }, true);
}
