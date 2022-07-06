package no.sintef.fiskinfo.model.orap

object OrapConstants {
    object FormDataNames {
        const val ACTION = "Action"
        const val TAG = "tag"
        const val REG_EPOC = "reg_epoc"
        const val USER = "user"
        const val PASSWORD = "password"
        const val MELDINGSTYPE = "Meldingstype"
        const val HIDDEN_TERMIN = "Hidden_termin"
        const val HIDDEN_MESS = "Hidden_mess"
        const val HIDDEN_KL_MESS = "Hidden_kl_mess"
        const val HIDDEN_KL_STATUS = "Hidden_kl_status"
        const val HIDDEN_FILE = "Hidden_file"
        const val HIDDEN_TYPE = "Hidden_type"
        const val LSTEP = "lstep"
    }

    object FormValues {
        const val ACTION_SUBMIT_REPORT = "Send melding til met.no"
        const val REPORT_MESSAGE_TYPE = "17-Ship(SPRICE)_1timer_v3"
        const val HIDDEN_MESSAGE_TEMPLATE = "012345678      ${username},17,,,,,,,,,,,${HW},${PW},,,,,,,,,,,,,,,${EsEs},,${LaLa},${LoLo},1,,,,,,\n" +
                "\n" +
                "\n"
        const val HIDDEN_KL_MESS = "kldata/nationalnr=01998/type=317/test/received_time=\"${timeStampAtReportingTime}\"\n" + // Format of timestamp: YYYY-MM-DD HH:MM:NN+00
                "IX,WW,VV,HL,NN,NH,CL,CM,CH,W1,W2,HW,PW,DW1,PW1,HW1,DW2,PW2,HW2,DD,FF,CI,SI,BI,DI,ZI,XIS,ES,ERS,MLAT,MLON,TA,UU,UH,PR,PO,PP,AA,MDIR,MSPEED\n" +
                "${timeStampOfObservation},3,,,,,,,,,,,${HW},${PW},,,,,,,,,,,,,,,${EsEs},,${LaLa},${LoLo},1,-6,,,,,,,\n" + // Format of timestamp: YYYYMMDDHH00
                "Orap_smsformat_input ${epochAtReportingTime} ${username},17,,,,,,,,,,,${HW},${PW},,,,,,,,,,,,,,,${EsEs},,${LaLa},${LoLo},1,,,,,,\n" +
                "Local_kvalobs_data /var/www/orap//orap_data//xenial-test//317/1/${username}/orap_${timeStampAtReportingTime}.txt;" // Format of timestamp: YYYYMMDD_HHMMSS_000
        const val HIDDEN_KL_STATUS = "${timeStampOfObservationMultipliedByTen} || 012345678      ${userName},17,,,,,,,,,,,${HW},${PW},,,,,,,,,,,,,,,${EsEs},,${LaLa},${LoLo},1,,,,,,"
        const val HIDDEN_FILE_VALUE = "gsmin1??.???"
        const val HIDDEN_TYPE = "17-Ship(SPRICE)_1timer_v3"
        const val LSTEP = 3
    }
}