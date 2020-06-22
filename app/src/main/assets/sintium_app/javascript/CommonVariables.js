let selectedFeature;
let selectedKey = null;

const tokenApiURL = "https://www.barentswatch.no/api/token";
const token = Android.getToken();
console.log("TOKEN " + token);
const authenticator = Sintium.OAuthAuthenticator(tokenApiURL, token);
const unrollAtZoom = 12;

vesselMap = {};