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

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
/**
 * Metainformation for shared echograms.
 */
public class EchogramInfo {
    @PrimaryKey
    public long id;
    // ignore long ownerID;

    public long userID;
    public Date timestamp;
    public String latitude;
    public String longitude;
    public String biomass;
    public String source;

    public String echogramUrl;

    // ignore SnapUser owner;
}
