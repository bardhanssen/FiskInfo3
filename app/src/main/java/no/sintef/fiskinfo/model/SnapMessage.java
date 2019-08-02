/**
 * Copyright (C) 2019 SINTEF
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package no.sintef.fiskinfo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
/**
 * The SnapMessage contains "envelope" information, title and comments for shared
 * echogram snap messages. This is used both to send and receive messages.
 */
public class SnapMessage {
    @PrimaryKey
    public long id;
    public long senderID;
    public String senderEmail;

    public String title;
    public String comment;
    public Date sendTimestamp;
    public boolean sharePublicly;

    public long echogramInfoID;

    //public ArrayList<String> receiverID;

    public EchogramInfo echogramInfo;
    public SnapUser sender;
    public List<SnapReceiver> receivers;

    public EchogramInfo getEchogramInfo() {
        return echogramInfo;
    }
}
