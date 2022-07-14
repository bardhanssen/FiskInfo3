/**
 * Copyright (C) 2020 SINTEF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.sintef.fiskinfo.api.orap

import no.sintef.fiskinfo.model.orap.CheckReportRequestBody
import no.sintef.fiskinfo.model.orap.GetReportsRequestBody
import no.sintef.fiskinfo.model.orap.ReportIcingRequestBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Interface to API service from Orap
 */
interface OrapService {
    companion object {
        const val prefix = "cgi-bin/"
        const val ObsRaport = prefix + "ObsRaport.cgi/"
    }

    @POST( ObsRaport)
    fun checkReport(@Body body : CheckReportRequestBody): Call<Response>

    @POST( ObsRaport)
    fun submitReport(@Body body : ReportIcingRequestBody): Call<Response>

    @GET( ObsRaport)
    fun getReports(@Body body : GetReportsRequestBody): Call<Response>

}
