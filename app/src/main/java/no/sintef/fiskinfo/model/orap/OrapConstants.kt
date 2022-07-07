package no.sintef.fiskinfo.model.orap

object OrapConstants {
    const val BOUNDARY_ID_LENGTH = 16
    const val HIDDEN_KL_MESSAGE_RECEIVED_TIME_FORMAT = "YYYY-MM-DD HH:MM:NN+00"
    const val HIDDEN_KL_MESSAGE_OBSERVATION_TIMESTAMP = "YYYYMMDDHH00"
    const val HIDDEN_KL_MESSAGE_RECEIVED_FILE_NAME_TIMESTAMP = "YYYYMMDD_HHMMSS_000"
    const val ACTION_TAG_DATE_FORMAT = "YYYYMMDD"

    object FormDataNames {
        const val ACTION = "Action"
        const val TAG = "tag"
        const val REG_EPOC = "reg_epoc"
        const val USER = "user"
        const val PASSWORD = "password"
        const val MESSAGE_TYPE = "Meldingstype"
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
        const val HIDDEN_FILE_VALUE = "gsmin1??.???"
        const val HIDDEN_TYPE = "17-Ship(SPRICE)_1timer_v3"
        const val LSTEP = 3
    }
}