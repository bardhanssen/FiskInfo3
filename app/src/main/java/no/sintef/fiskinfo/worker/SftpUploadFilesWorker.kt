package no.sintef.fiskinfo.worker

import android.content.Context
import android.util.Log
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.ajhuntsman.ksftp.FilePair
import com.ajhuntsman.ksftp.SftpClient
import com.ajhuntsman.ksftp.SftpConnectionParametersBuilder
import com.google.common.util.concurrent.ListenableFuture
import no.sintef.fiskinfo.BuildConfig
import java.io.File

class SftpUploadFilesWorker(appContext: Context, workerParams: WorkerParameters) : ListenableWorker(appContext, workerParams) {

    override fun startWork(): ListenableFuture<Result> {
        // TODO: Get file names and webKitFormBoundaryId
        val files: List<File> = listOf()
        val webKitFormBoundaryId: String = "file.txt"

        val orapUsername = BuildConfig.SPRICE_ORAP_SFTP_USER_NAME
        val orapPassword = BuildConfig.SPRICE_ORAP_SFTP_PASSWORD
        val filePairs = ArrayList<FilePair>()

        val connectionParameters = SftpConnectionParametersBuilder.newInstance().createConnectionParameters()
            .withHost(BuildConfig.SPRICE_ORAP_SFTP_URL)
            .withPort(BuildConfig.SPRICE_ORAP_SFTP_PORT_NUMBER)
            .withUsername(orapUsername)
            .withPassword(orapPassword.toByteArray())
            .create()

        for (file in files) {
            if (file.exists()) {
                val filePath: String = file.absolutePath
                var fileName = file.name
                val filenameLength = fileName.lastIndexOf('.', fileName.length)
                fileName =
                    "${fileName.substring(0, filenameLength)}_${webKitFormBoundaryId}${fileName.substring(fileName.lastIndexOf('.', fileName.length))}".replace(
                        "[/<>:\"|?*]".toRegex(),
                        ""
                    )

                val remoteFileName = "/dev/${fileName}"

                filePairs.add(FilePair(filePath, remoteFileName))
            } else {
                Log.d("SPRICE SFTP", "Path does not exist: ${file.path}")
            }
        }

        SftpClient
            .create(connectionParameters)
            .upload(filePairs, 60)

        return CallbackToFutureAdapter.getFuture {
            it.set(Result.success())
        }
    }
}