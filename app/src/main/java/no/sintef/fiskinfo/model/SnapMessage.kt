/**
 * Copyright (C) 2019 SINTEF
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
package no.sintef.fiskinfo.model

//import androidx.room.Entity
//import androidx.room.PrimaryKey

import java.util.Date

//@Entity
/**
 * The SnapMessage contains "envelope" information, title and comments for shared
 * echogram snap messages. This is used both to send and receive messages.
 */
class SnapMessage {
//    @PrimaryKey
    var id: Long = 0
    var senderID: Long = 0
    var senderEmail: String? = null

    var title: String? = null
    var comment: String? = null
    var sendTimestamp: Date? = null
    var sharePublicly: Boolean = false

    var echogramInfoID: Long = 0

    //public ArrayList<String> receiverID;

    var echogramInfo: SnapMetadata? = null
    var sender: SnapUser? = null
    var receivers: MutableList<SnapReceiver>? = null
}
