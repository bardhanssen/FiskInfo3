function errorBox(errorMessage) {
    let html = "<div class=\"error\">";
    html += "<div class=\"error-icon\"><i class=\"fas fa-exclamation-triangle\" ></i></div>";
    html += errorMessage;
    html += "</div>";
    return html;
}

function getPositionFromGeometry(geometry) {
    return ol.proj.transform(ol.extent.getCenter(geometry.getExtent()), 'EPSG:3857', 'EPSG:4326');
}

function nFormatter(num, digits) {
    const si = [
        { value: 1, symbol: "" },
        { value: 1E3, symbol: "k" },
        { value: 1E6, symbol: "M" },
        { value: 1E9, symbol: "G" },
        { value: 1E12, symbol: "T" },
        { value: 1E15, symbol: "P" },
        { value: 1E18, symbol: "E" }
    ];
    const rx = /\.0+$|(\.[0-9]*[1-9])0+$/;
    let i;
    for (i = si.length - 1; i > 0; i--) {
        if (num >= si[i].value) {
            break;
        }
    }
    return (num / si[i].value).toFixed(digits).replace(rx, "$1") + si[i].symbol;
}

function degreesToRadians(degrees) {
    const pi = Math.PI;
    return parseFloat(degrees) * (pi / 180);
}

function marinogramError() {
    const marinogramDiv = document.getElementById("marinogram");
    if (marinogramDiv == null) return;
    marinogramDiv.innerHTML = errorBox("Marinogram er ikke tilgjengelig for valg punkt.")
}

function unsetSelectedFeature()  {
    selectedFeature.removeText();
    selectedFeature = null;
}

function getMaxThreeToolsFromCallSign(callsign) {
    if (vesselMap[callsign] === undefined) return [];
    if (vesselMap[callsign].tools === undefined) return [];
    return vesselMap[callsign].tools.slice(0, 3);
}

function vesselCodeToShipTypeName(record) {
    const number = record.get("shipType");
    switch (number) {
        case 30: return "Fiskefartøy";
        case 31:
        case 32: return "Taubåt";
        case 33: return "Mudringsfartøy";
        case 34: return "Dykkerfartøy";
        case 35: return "Militært fartøy";
        case 36: return "Seilbåt";
        case 37: return "Fritidsbåt";
        case 51: return "Søk og redningsfartøy";
        default:
            return "Ukjent type";
    }
}

function landCodeToCountryName(value) {
    let countryName = "";
    const landCodeArray = value.split(";");
    for (let i = 0; i < landCodeArray.length; i++) {
        switch(landCodeArray[i].toLowerCase()) {
            case "is":
                countryName += "Island, ";
                break;
            case "no":
                countryName += "Norge, ";
                break;
            case "gb":
                countryName += "Storbritannia, ";
                break;
            case "gl":
                countryName += "Grønland, ";
                break;
            case "dk":
                countryName += "Danmark, ";
                break;
            case "sj":
                countryName += "Svalberg og Jan Mayen, ";
                break;
            case "ru":
                countryName += "Russland, ";
                break;
        }
    }

    if (countryName.length > 2)
        countryName = countryName.substring(0, countryName.length - 2);

    return countryName;
}

function formatDateDifference(dateString) {
    const date = new Date(dateString);
    let totalSeconds = (new Date().getTime() - date.getTime())/1000;
    const days = Math.floor(totalSeconds/(3600*24));
    totalSeconds -= days*3600*24;
    const hours = Math.floor(totalSeconds/3600);
    totalSeconds -= hours*3600;
    const minutes = Math.round(totalSeconds/60);
    return days + " døgn, " + hours + " timer, " + minutes + " minutter";
}

function missingIfNull(value) {
    return !value ? "Mangler" : value;
}

function formattedDate(dateString) {
    if (dateString == null) return "Mangler";

    const months = ['januar', 'februar', 'mars', 'april', 'mai', 'juni', 'juli', 'august', 'september', 'oktober', 'november', 'desember'];
    const date = new Date(dateString);
    const year = date.getFullYear();
    const dayOfMonth = date.getDate();
    const month = months[date.getMonth()];
    return dayOfMonth + ". " + month + " " + year;
}

function formattedTimePeriod(fromDate, toDate) {
    return formattedDate(fromDate) + " - " + formattedDate(toDate);
}

function formatLocation(coordinates) {
    const lon = coordinates[0];
    const lat = coordinates[1];
    const convertLat = Math.abs(lat);
    const latDegree = Math.floor(convertLat);
    const latMinutes = Math.floor(((convertLat - latDegree) * 60)*1000)/1000;
    const latCardinal = ((lat > 0) ? "N" : "S");

    const convertLon = Math.abs(lon);
    const lonDegree = Math.floor(convertLon);
    const lonMinute = Math.floor(((convertLon - lonDegree) * 60)*1000)/1000;
    const lonCardinal = ((lon > 0) ? "E" : "W");
    return latDegree + "° " + latMinutes + " " + latCardinal + " " + lonDegree  + "° " + lonMinute + " " + lonCardinal;
}

function marinogramLink(coordinate) {
    const lon = coordinate[0];
    const lat = coordinate[1];
    let link = "<a href=\"https://www.yr.no/sted/Hav/";
    link += lat + "_" + lon;
    link += "\">Se marinogram fra yr.no</a>";
    return link;
}

function showVesselLink(ircs, vesselName) {
    return "<a target='_blank' href='javascript:showVesselAndBottomsheet(" + "\"" + ircs + "\"" + ")'>" + vesselName + "</a>";
}

function formatToolType(toolTypeCode) {
    if (toolTypeCode)
        toolTypeCode = toolTypeCode.toLowerCase();
    switch (toolTypeCode) {
        case "mooring": return "Fortøyningssystem";
        case "longline": return "Line";
        case "crabpot": return "Teine";
        case "nets": return "Garn";
        case "sensorcable": return "Sensor / kabel";
        case "danpurseine": return "Snurpenot";
        case "unk":
        default:
            return "Ukjent redskap";
    }
}

/**
 * Determine the mobile operating system.
 * This function returns one of 'iOS', 'Android', 'Windows Phone', or 'unknown'.
 *
 * @returns {String}
 */
function getMobileOperatingSystem() {
    const userAgent = navigator.userAgent || navigator.vendor || window.opera;

    // Windows Phone must come first because its UA also contains "Android"
    if (/windows phone/i.test(userAgent)) {
        return "Windows Phone";
    }

    if (/android/i.test(userAgent)) {
        return "Android";
    }

    // iOS detection from: http://stackoverflow.com/a/9039885/177710
    if (/iPad|iPhone|iPod/.test(userAgent) && !window.MSStream) {
        return "iOS";
    }

    return "unknown";
}
