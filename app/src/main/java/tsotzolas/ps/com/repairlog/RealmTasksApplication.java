/*
 * Copyright 2016 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tsotzolas.ps.com.repairlog;

import android.app.Application;

//import com.facebook.FacebookSdk;

import io.realm.Realm;
import io.realm.log.LogLevel;
import io.realm.log.RealmLog;

/**
 * Είναι το αρχείο το οποίο του λές που είναι ο Realm Object Server σου
 * για να κάνει τον συγχρονισμό
 */
public class RealmTasksApplication extends Application {

    public static final String AUTH_URL = "http://" +"83.212.105.36" + ":9080/auth";
    public static final String REALM_URL = "realm://" + "83.212.105.36" + ":9080/~/gr.repairlog.tsotzolas.android";
    public static final String DEFAULT_LIST_ID = "80EB1620-165B-4600-A1B1-D97032FDD9A0";
    public static String DEFAULT_LIST_NAME = "My Tasks";

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
//        FacebookSdk.sdkInitialize(this);
        RealmLog.setLevel(LogLevel.TRACE);
    }
}