/*
 * Copyright 2018 Dmitriy Ponomarenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dimowner.audiorecorder.util;

import com.dimowner.audiorecorder.AppConstants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class TimeUtils {

    /** Date format: May 16, 03:30 PM */
    private static SimpleDateFormat dateFormat12H = new SimpleDateFormat("MMM dd, hh:mm aa", Locale.US);

    /** Date format: May 16, 15:30 */
    private static SimpleDateFormat dateFormat24H = new SimpleDateFormat("MMM dd, HH:mm", Locale.US);


    private TimeUtils() {}

    public static String formatTimeIntervalMinSec(long length) {
        TimeUnit timeUnit = TimeUnit.MILLISECONDS;
        long numMinutes = timeUnit.toMinutes(length);
        long numSeconds = timeUnit.toSeconds(length);
        return String.format(Locale.getDefault(), "%02d:%02d", numMinutes, numSeconds % 60);
    }

    public static String formatTimeIntervalMinSecMills(long mills) {
        long min = mills/(60 * 1000);
        long sec = (mills/1000)%60;
        long m = (mills/10)%100;
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", min, sec, m);
    }

    public static String formatTime(long timeMills, int timeFormat) {
        if (timeMills <= 0) {
            return "";
        }
        if (timeFormat == AppConstants.TIME_FORMAT_12H) {
            return dateFormat12H.format(new Date(timeMills));
        } else {
            return dateFormat24H.format(new Date(timeMills));
        }
    }

    public static String formatTime(long timeMills) {
        if (timeMills <= 0) {
            return "";
        }
        return dateFormat24H.format(new Date(timeMills));
    }

}
