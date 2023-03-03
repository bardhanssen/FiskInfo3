package no.sintef.fiskinfo.worker

import android.content.Context
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.work.Data
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.google.common.util.concurrent.ListenableFuture
import no.sintef.fiskinfo.BuildConfig

class SpriceSubmitIcingReportWorker (appContext: Context, workerParams: WorkerParameters) : ListenableWorker(appContext, workerParams) {

    override fun startWork(): ListenableFuture<Result> {
        val data: Data = inputData

        // TODO: Implement worker

        return CallbackToFutureAdapter.getFuture {
            it.set(Result.success())
        }
    }
}