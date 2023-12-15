/*
  Copyright (C) 2020 SINTEF

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at


  http://www.apache.org/licenses/LICENSE-2.0


  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
 */

package no.sintef.fiskinfo.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.util.TypedValue;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TilHavsUtility {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    // copy from InputStream
    // -----------------------------------------------------------------------
    /**
     * Copy bytes from an <code>InputStream</code> to an
     * <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     * <p>
     * Large streams (over 2GB) will return a bytes copied value of
     * <code>-1</code> after the copy has completed since the correct number of
     * bytes cannot be returned as an int. For large streams use the
     * <code>copyLarge(InputStream, OutputStream)</code> method.
     *
     * @param input
     *            the <code>InputStream</code> to read from
     * @param output
     *            the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException
     *             if the input or output is null
     * @throws IOException
     *             if an I/O error occurs
     * @throws ArithmeticException
     *             if the byte count is too large
     */
    public static int copy(InputStream input, OutputStream output) throws IOException {
        long count = copyLarge(input, output);
        if (count > Integer.MAX_VALUE) {
            return -1;
        }
        return (int) count;
    }

    /**
     * Copy bytes from a large (over 2GB) <code>InputStream</code> to an
     * <code>OutputStream</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     *
     * @param input
     *            the <code>InputStream</code> to read from
     * @param output
     *            the <code>OutputStream</code> to write to
     * @return the number of bytes copied
     * @throws NullPointerException
     *             if the input or output is null
     * @throws IOException
     *             if an I/O error occurs
     */
    public static long copyLarge(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }

    // read toByteArray
    // -----------------------------------------------------------------------
    /**
     * Get the contents of an <code>InputStream</code> as a <code>byte[]</code>.
     * <p>
     * This method buffers the input internally, so there is no need to use a
     * <code>BufferedInputStream</code>.
     *
     * @param input
     *            the <code>InputStream</code> to read from
     * @return the requested byte array
     * @throws NullPointerException
     *             if the input is null
     * @throws IOException
     *             if an I/O error occurs
     */
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }


    /**
     * Truncates a double value to the number of decimals given
     *
     * @param number
     *            The number to truncate
     * @param numberofDecimals
     *            Number of decimals of the truncated number
     * @return
     *            The truncated number.
     */
    @SuppressWarnings("unused")
    public Double truncateDecimal(double number, int numberofDecimals) {
        if (number > 0) {
            return new BigDecimal(String.valueOf(number)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR).doubleValue();
        } else {
            return new BigDecimal(String.valueOf(number)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING).doubleValue();
        }
    }

    /**
     * Checks that the given string is a valid E-mail address
     *
     * @param email
     *            the address to check
     * @return true if address is a valid E-mail address, false otherwise.
     */
    public static boolean isEmailValid(String email) {
        String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Appends a item from a JsonArray to a <code>ExpandableListAdapater</code>
     *
     * @param subscriptions
     *            A JSON array containing all the available subscriptions
     * @param field
     *            The field name in the <code>ExpandableListAdapater</code>
     * @param fieldsToExtract
     *            The fields from the subscriptions to retrieve and store in the
     *            <code>ExpandableListAdapater</code>
     */
    @SuppressWarnings("unused")
    public void appendSubscriptionItemsToView(JSONArray subscriptions, List<String> field, List<String> fieldsToExtract) {
        if ((subscriptions == null) || (subscriptions.isNull(0))) {
            return;
        }

        String title = "";
        for (int i = 0; i < subscriptions.length(); i++) {
            try {
                JSONObject currentSubscription = subscriptions.getJSONObject(i);
                for (String fieldValue : fieldsToExtract) {

                    title += (fieldValue.equals("LastUpdated") ? currentSubscription.getString(fieldValue).replace('T', ' ')
                            : currentSubscription.getString(fieldValue)) + "\n";
                }
                title = title.substring(0, title.length() - 1);
                field.add(title);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            title = "";
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
/*
    public int subscriptionApiNameToIconId(String apiName) {
        int retVal = -1;

        switch(apiName.toLowerCase()) {
            case "fishingfacility":
                retVal = R.drawable.ikon_kystfiske;
                break;
            case "iceedge":
                retVal = R.drawable.ikon_is_tjenester;
                break;
            case "icechart":
                retVal = R.drawable.ikon_is_tjenester;
                break;
            case "npdfacility":
                retVal = R.drawable.ikon_olje_og_gass;
                break;
            case "npdsurveyplanned":
                retVal = R.drawable.ikon_olje_og_gass;
                break;
            case "npdsurveyongoing":
                retVal = R.drawable.ikon_olje_og_gass;
                break;
        }

        return retVal;
    }
*/
    /**
     * Checks if external storage is available for read and write.
     *
     * @return True if external storage is available, false otherwise.
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();

        return Environment.MEDIA_MOUNTED.equals(state);
    }
/*TEMP
    public boolean writeDataToExternalStorage(Activity activity, byte[] data, String writableName, String format, String downloadSavePath, boolean showToasts) {

        if (FiskInfoUtility.shouldAskPermission()) {
            String[] perms = {"android.permission.WRITE_EXTERNAL_STORAGE"};
            int permsRequestCode = 0x001;
//            activity.requestPermissions(perms, permsRequestCode);

            ActivityCompat.requestPermissions(activity, new String[]{WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        String filePath;
        OutputStream outputStream = null;
        filePath = downloadSavePath;
        boolean success = false;
        String fileEnding = format;

        File directory = filePath == null ? null : new File(filePath);

        if(directory != null && !directory.isDirectory() && !directory.mkdirs()) {
            directory = null;
        }

        if(directory == null) {
            String directoryPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
            String directoryName = "FiskInfo";
            filePath = directoryPath + "/" + directoryName + "/";
            new File(filePath).mkdirs();
        }

        if(fileEnding != null && fileEnding.equals(activity.getBaseContext().getString(R.string.olex))) {
            fileEnding = "olx.gz";
        }

        try {
            File dir = new File(filePath);
            File file = new File(filePath + writableName + "." + fileEnding);

            if(!dir.canWrite()) {
                if(showToasts) {
                    Toast.makeText(activity.getBaseContext(), R.string.error_cannot_write_to_directory, Toast.LENGTH_LONG);
                }

                throw new IOException(activity.getResources().getString(R.string.error_cannot_write_to_directory));
            }

            // index 1 is leef when plugged in. Dirs[1].getAbsoluteFile() is writable
//            File[] dirs = ContextCompat.getExternalFilesDirs(activity.getBaseContext(), null);

            outputStream = new FileOutputStream(file);
            outputStream.write(data);

            if(showToasts) {
                Toast.makeText(activity.getBaseContext(), "Fil lagret til " + filePath, Toast.LENGTH_LONG).show();
            }

            success = true;
        } catch (IOException e) {
            if(showToasts) {
                Toast.makeText(activity.getBaseContext(), R.string.disk_write_failed, Toast.LENGTH_LONG).show();
            }
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return success;
    }
*/
    /**
     * Get the contents of an <code>FiskInfoPolygon2D</code> and writes it to
     * disk This method catches all <code>Exceptions</code> internally,
     * therefore it should be usable directly
     *
     * @param path
     *            the <code>Path</code> of the file to write
     * @param polygon
     *            the <code>FiskInfoPolygon2d</code> which is written to disk
     */
/*TEMP    public void serializeFiskInfoPolygon2D(String path, FiskInfoPolygon2D polygon) {
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(polygon);
            out.close();
            fileOut.close();
            Log.d("FiskInfo", "Serialization successfull, the data should be stored in the specified path");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    /**
     * Retrieve a serialized <code>FiskInfoPolygon2D</code> from disk as a
     * <code>FiskInfoPolygon2D class</code>
     *
     * @param path
     *            the <code>Path</code> to read from
     * @return the Requested <code>FiskInfoPolygon2D</code>
     */
/*TEMP    public FiskInfoPolygon2D deserializeFiskInfoPolygon2D(String path) {
        FiskInfoPolygon2D polygon = null;
        FileInputStream fileIn;
        ObjectInputStream in;

        try {
            System.out.println("Starting deserializing");
            fileIn = new FileInputStream(path);
            in = new ObjectInputStream(fileIn);
            polygon = (FiskInfoPolygon2D) in.readObject();


            in.close();
            fileIn.close();
            Log.d("FiskInfo", "Deserialization successful, the data should be stored in the input class");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            Log.d("Utility", "Deserialization failed, couldn't find class");
            e.printStackTrace();
        }

        return polygon;
    }
*/

    // Compare dates from strings
    // -----------------------------------------------------------------------
    /**
     * <p>
     * <b> public int compare(mDate, dateToCOmpareWith, format) </b>
     * </p>
     *
     * Compares two dates with each other using the rules of the given date
     * format.
     *
     * @param mDate
     *            The date you want to compare
     * @param dateToCompareWith
     *            The date comparing with
     * @param format
     *            An accepted format which must be valid for a
     *            <code>SimpleDateFormat</code>
     * @return <p>
     *         returns 0 if the dates are equal
     *         </p>
     *         <p>
     *         returns > 0 if mDate is larger then the date to compare with
     *         </p>
     *         <p>
     *         returns < 0 if mDate is smaller then the date to compare with
     *         </p>
     */
    public static int compareDates(String mDate, String dateToCompareWith, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(mDate);
            date2 = sdf.parse(dateToCompareWith);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1.compareTo(date2);
    }

    /**
     * Takes either a latitude or longitude coordinate and returns the same coordinate as DMS (Degrees, minutes, seconds)
     * @param coord
     *      The coordinate to convert
     * @return
     *      The given coordinate in DMS format
     */
    public static String decimalToDMS(double coord) {
        double absCoord = Math.abs(coord);
        String output, degrees, minutes, seconds;

        double mod = absCoord % 1;
        int intPart = (int) absCoord;
        degrees = String.valueOf(intPart);

        absCoord = mod * 60;
        mod = absCoord % 1;
        intPart = (int) absCoord;
        minutes = String.valueOf(intPart);

        absCoord = mod * 60;
        intPart = (int) absCoord;
        seconds = String.valueOf(intPart);

        output = degrees + "°" + minutes + "'" + seconds + "\"";

        return output;
    }

    public static double[] decimalToDMSArray(double coord) {
        double absCoord = Math.abs(coord);
        double[] output = new double[3];

        double mod = absCoord % 1;
        double intPart = (int) absCoord;
        output[0] = intPart;

        absCoord = mod * 60;
        mod = absCoord % 1;
        intPart = (int) absCoord;
        output[1] = intPart;

        absCoord = mod * 60;
        intPart = absCoord;
        output[2] = intPart;

        return output;
    }

    public static double DMSToDecimal(double[] coord) {
        return coord[0] + (coord[1] / 60) + (coord[2] / 3600);
    }

    public static String decimalToDDM(double coord, int numberOfDecimals) {
        double absCoord = Math.abs(coord);
        String output, degrees, minutes;

        double mod = absCoord % 1;
        int intPart = (int) absCoord;
        degrees = String.valueOf(intPart);

        absCoord = mod * 60;
        mod = absCoord % 1;
        intPart = (int) absCoord;
        mod = intPart + mod;

        String roundingFormat = "%." + numberOfDecimals + "f";
        minutes =  numberOfDecimals != -1 ? String.format(Locale.getDefault(), roundingFormat, mod) : String.valueOf(mod);
        output = degrees + "° " + minutes + "'";

        return output;
    }

    public static double[] decimalToDDMArray(double coord) {
        // TODO: implement
        double absCoord = Math.abs(coord);
        double[] output = new double[3];

        double mod = absCoord % 1;
        int intPart = (int) absCoord;
        output[0] = intPart;

        absCoord = mod * 60;
        mod = absCoord % 1;
        intPart = (int) absCoord;
        output[1] = intPart;
        output[1] += mod;

        return output;


    }

    public static double DDMToDecimal(double[] coord) {
       return coord[0] + (coord[1] / 60.0);
    }

    public static Date iso08601ParseDate(String input) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz", Locale.getDefault());
        if (input.endsWith("Z")) {
            input = input.substring(0, input.length() - 1) + "GMT-00:00";
        } else {
            int inset = 6;
            String start = input.substring(0, input.length() - inset);
            String end = input.substring(input.length() - inset, input.length());
            input = start + "GMT" + end;
        }
        return sdf.parse(input);
    }

    @SuppressWarnings("unused")
    public static String iso08601ToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz", Locale.getDefault());
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(timeZone);
        String output = sdf.format(date);
        int insetFirst = 9;
        int insetLast = 6;
        String retval = output.substring(0, output.length() - insetFirst) + output.substring(output.length() - insetLast, output.length());
        return retval.replaceAll("UTC", "+00:00");
    }

    /*
     * Checks that the given string contains coordinates in a valid format in
     * regards to the given projection.
     *
     * @param coordinates
     *      The coordinates to check
     * @param projection
     *      The coordinate projection used
     * @return
     *      Whether coordinate format is valid or not
     */
    @SuppressWarnings("unused")
/*TEMP    public boolean checkCoordinates(String coordinates, ProjectionType projection) {
        boolean retVal = false;
        System.out.println("coords: " + coordinates);
        System.out.println("projection: " + projection);

        if (coordinates.length() == 0) {
            retVal = false;
        } else {
            switch (projection) {
                case EPSG_3857:
                    retVal = checkProjectionEPSG3857(coordinates);
                    break;
                case EPSG_4326:
                    retVal = checkProjectionEPSG4326(coordinates);
                    break;
                case EPSG_23030:
                    retVal = checkProjectionEPSG23030(coordinates);
                    break;
                case EPSG_900913:
                    retVal = checkProjectionEPSG900913(coordinates);
                    break;
                default:
                    return retVal;
            }
        }

        return retVal;
    }
*/
    private boolean checkProjectionEPSG3857(String coordinates) {
        try {
            int commaSeparatorIndex = coordinates.indexOf(",");
            double latitude = Double.parseDouble(coordinates.substring(0, commaSeparatorIndex - 1));
            double longitude = Double.parseDouble(coordinates.substring(commaSeparatorIndex + 1, coordinates.length() - 1));

            double EPSG3857MinX = -20026376.39;
            double EPSG3857MaxX = 20026376.39;
            double EPSG3857MinY = -20048966.10;
            double EPSG3857MaxY = 20048966.10;

            return !(latitude < EPSG3857MinX || latitude > EPSG3857MaxX || longitude < EPSG3857MinY || longitude > EPSG3857MaxY);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkProjectionEPSG4326(String coordinates) {
        try {
            int commaSeparatorIndex = coordinates.indexOf(",");
            double latitude = Double.parseDouble(coordinates.substring(0, commaSeparatorIndex - 1));
            double longitude = Double.parseDouble(coordinates.substring(commaSeparatorIndex + 1, coordinates.length() - 1));
            double EPSG4326MinX = -180.0;
            double EPSG4326MaxX = 180.0;
            double EPSG4326MinY = -90.0;
            double EPSG4326MaxY = 90.0;

            return !(latitude < EPSG4326MinX || latitude > EPSG4326MaxX || longitude < EPSG4326MinY || longitude > EPSG4326MaxY);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkProjectionEPSG23030(String coordinates) {
        try {
            int commaSeparatorIndex = coordinates.indexOf(",");
            double latitude = Double.parseDouble(coordinates.substring(0, commaSeparatorIndex - 1));
            double longitude = Double.parseDouble(coordinates.substring(commaSeparatorIndex + 1, coordinates.length() - 1));
            double EPSG23030MinX = 229395.8528;
            double EPSG23030MaxX = 770604.1472;
            double EPSG23030MinY = 3982627.8377;
            double EPSG23030MaxY = 7095075.2268;

            return !(latitude < EPSG23030MinX || latitude > EPSG23030MaxX || longitude < EPSG23030MinY || longitude > EPSG23030MaxY);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean checkProjectionEPSG900913(String coordinates) {
        try {
            int commaSeparatorIndex = coordinates.indexOf(",");
            double latitude = Double.parseDouble(coordinates.substring(0, commaSeparatorIndex - 1));
            double longitude = Double.parseDouble(coordinates.substring(commaSeparatorIndex + 1, coordinates.length() - 1));

			/*
			 * These are based on the spherical metricator bounds of OpenLayers
			 * and as we are currently using OpenLayer these are the bounds to use.
			 */
            double EPSG900913MinX = -20037508.34;
            double EPSG900913MaxX = 20037508.34;
            double EPSG900913MinY = -20037508.34;
            double EPSG900913MaxY = 20037508.34;

            return !(latitude < EPSG900913MinX || latitude > EPSG900913MaxX || longitude < EPSG900913MinY || longitude > EPSG900913MaxY);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Convert Dp to Pixel
     */
    public static int dpToPx(float dp, Resources resources){
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
        return (int) px;
    }

    public static int getRelativeTop(View myView) {
        if(myView.getId() == android.R.id.content)
            return myView.getTop();
        else
            return myView.getTop() + getRelativeTop((View) myView.getParent());
    }

    public static int getRelativeLeft(View myView) {
        if(myView.getId() == android.R.id.content)
            return myView.getLeft();
        else
            return myView.getLeft() + getRelativeLeft((View) myView.getParent());
    }

    public static boolean validateName(String name) {
        return name != null && name.length() >= 2;// TODO: Add regex matching
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.length() >= 8; //TODO: Add regex matching
    }

    public static boolean validateIRCS(String ircs) {
        boolean success = false;

        if(ircs != null && (ircs.length() >= 4))
        {
            success = true;
        }

        return success;
    }

    /*
     *  A valid MMSI is a 9 digit code.
     */
    public static boolean validateMMSI(String mmsi) {
        boolean success = false;

        if(mmsi != null && (mmsi.length() == 9 &&
                mmsi.substring(0, 1).matches("[2-7]") &&
                mmsi.matches("^[0-9]*$")))
        {
            success = true;
        }

        return success;
    }

    public static boolean validateIMO(String imo) {
        boolean success = false;

        if(imo != null && ((imo.length() == 7 &&
                imo.substring(0, 1).matches("[0-9]") &&
                imo.matches("^[0-9]*$"))))
        {
            int[] imoCheckArray = new int[6];
            int checkSum = 0;

            for(int i = 0; i < 6; i++) {
                imoCheckArray[i] = Character.getNumericValue(imo.charAt(i));
            }

            for(int i = 0; i < 6; i++) {
                checkSum += imoCheckArray[i] * (7 - i);
            }
            success = Character.getNumericValue(imo.charAt(imo.length() - 1)) == checkSum % 10;
        }

        return success;
    }

    /**
     * Matches the given string against the following pattern:
     *      County: Either two alpha characters or one alpha character followed by a space
     *      numbers: four numbers
     *      Municipality: one or two alpha characters
     * @param regnum
     * @return
     */
    public static boolean validateRegistrationNumber(String regnum) {
        return regnum != null && regnum.matches("^[a-zA-Z]{1,2}\\d{1,4}[a-zA-Z]{1,2}$");
    }

    public static boolean validateVesselName(String name) {
        return name != null && name.length() > 0;// TODO: Add regex matching
    }

    /**
     *  Validates required fields of the user settings.
     *  The following fields are required:
     *      - Contact person name
     *      - Contact person phone number
     *      - Email address
     *      - Vessel name
     *      - Either one of the following fields: IMO, IRCS, MMSI, or registration number
     *  @param userSettings
     *  @return true if all required settings are valid, false otherwise
     */
/* TODO Implement this

    public static boolean validateUserSettings(UserSettings userSettings) {
        return  (userSettings != null) &&
                validateName(userSettings.getContactPersonName()) &&
                validatePhoneNumber(userSettings.getContactPersonPhone()) &&
                isEmailValid(userSettings.getContactPersonEmail()) &&
                validateVesselName(userSettings.getVesselName()) &&
                validateRegistrationNumber(userSettings.getRegistrationNumber()) &&
                (validateIMO(userSettings.getImo()) ||
                validateIRCS(userSettings.getIrcs()) ||
                validateMMSI(userSettings.getMmsi()));
    }

    public static boolean match(String str1, String str2) {
        if (((str1 == null) || (str1.isEmpty())) && ((str2 == null) || (str2.isEmpty())))
            return true;
        else
            return str1 != null && str1.equals(str2);
    }

    public static boolean toolProfileMatchUserSettings(ToolEntry entry, UserSettings settings) {
        if ((entry == null) || (settings == null))
            return false;

        return
                match(entry.getContactPersonName(), settings.getContactPersonName()) &&
                        match(entry.getContactPersonPhone(), settings.getContactPersonPhone()) &&
                        match(entry.getContactPersonEmail(), settings.getContactPersonEmail()) &&
                        match(entry.getVesselName(), settings.getVesselName()) &&
                        match(entry.getVesselPhone(), settings.getVesselPhone()) &&
                        match(entry.getIRCS(), settings.getIrcs()) &&
                        match(entry.getMMSI(), settings.getMmsi()) &&
                        match(entry.getIMO(), settings.getImo()) &&
                        match(entry.getRegNum(), settings.getRegistrationNumber());
    }
*/

    /**
     * Validates the county code part of a vessel registration number, case insensitive.
     * A valid country code consists of one or two alphabetical characters in the range a-z.
     * @param countyCode
     * @return
     */
    public static boolean isValidCountyCode(String countyCode) {
        return countyCode != null && countyCode.matches("^[a-zA-Z]{1,2}$");
    }

    /**
     * Validates the vessel number part of a vessel registration number
     * A valid vessel number consists of one to four digits in the range 0-9.
     * @param vesselNumber
     * @return
     */
    public static boolean isValidVesselNumber(String vesselNumber) {
        return vesselNumber != null && vesselNumber.matches("^\\d{1,4}$");
    }

    /**
     * Validates the municipality code part of a vessel registration number
     * A valid municipality code consists of one or two alphabetical characters in the range a-z.
     * @param municipalityCode
     * @return
     */
    public static boolean isValidMunicipalityCode(String municipalityCode) {
        return municipalityCode != null && municipalityCode.matches("^[a-zA-Z]{1,2}$");
    }

    public static String formatRegistrationNumber(String regnum) {
        if(regnum == null || !validateRegistrationNumber(regnum)) {
            return null;
        }

        StringBuilder registrationNumber = new StringBuilder();
        String countyCode = regnum.substring(0, Character.isDigit(regnum.charAt(1)) ? 1 : 2);
        String vesselNumber = regnum.substring(Character.isDigit(regnum.charAt(1)) ? 1 : 2, Character.isDigit(regnum.charAt(regnum.length() - 2)) ? regnum.length() - 1 : regnum.length() - 2);
        String municipalityCode = regnum.substring(Character.isDigit(regnum.charAt(regnum.length() - 2)) ? regnum.length() - 1 : regnum.length() - 2, regnum.length());

        while(vesselNumber.length() < 4) {
            vesselNumber = "0" + vesselNumber;
        }

        registrationNumber.append(countyCode);
        registrationNumber.append(vesselNumber);
        registrationNumber.append(municipalityCode);

        return registrationNumber.toString();
    }

    /**
     * Replaces the regional characters 'æ, ø, å' with 'ae, oe, aa'. Conserves case.
     * @param string String to be replaced
     * @return String with regional characters replaced.
     */
    public static String ReplaceRegionalCharacters(String string) {
        String retval = string == null ? "" : string;
        retval = retval.replace("æ", "ae");
        retval = retval.replace("Æ", "ae");
        retval = retval.replace("ø", "oe");
        retval = retval.replace("Ø", "OE");
        retval = retval.replace("å", "aa");
        retval = retval.replace("Å", "AA");

        return retval;
    }

    public static String getHyperLinkString(String url, String link) {
        return "<a href=" + url + ">" + link + "</a>";
    }

    public static boolean shouldAskPermission(){
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1;
    }
}