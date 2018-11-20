/*
 * Copyright (c) 2014. Kleomenis Katevas
 * Kleomenis Katevas, k.katevas@imperial.ac.uk
 *
 * This file is part of SensingKit-Android library.
 * For more information, please visit http://www.sensingkit.org
 *
 * SensingKit-Android is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SensingKit-Android is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with SensingKit-Android.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.sensingkit.sensingkitlib.data;

import org.sensingkit.sensingkitlib.SKSensorType;

import java.util.HashMap;
import java.util.Locale;

/**
 *  An instance of SKStepCounterData encapsulates measurements related to the Step Counter sensor.
 */
public class SKStepCounterData extends SKAbstractData {

    @SuppressWarnings("unused")
    private static final String TAG = SKStepCounterData.class.getSimpleName();

    protected final int steps;

    /**
     * Initialize the instance
     *
     * @param timestamp Time in milliseconds (the difference between the current time and midnight, January 1, 1970 UTC)
     *
     * @param steps Number of steps
     */
    public SKStepCounterData(long timestamp, int steps) {

        super(SKSensorType.STEP_COUNTER, timestamp);

        this.steps = steps;
    }

    /**
     * Get the csv header of the Step Counter sensor data
     *
     * @return String with a CSV formatted header that describes the data of the Step Counter sensor.
     */
    @SuppressWarnings("unused")
    public static String csvHeader() {
        return "timeIntervalSince1970,numberOfSteps";
    }

    /**
     * Get Step Counter sensor data in CSV format
     *
     * @return String in CSV format: timeIntervalSince1970, numberOfSteps
     */
    @Override
    public String getDataInCSV() {
        return String.format(Locale.US, "%d,%d", this.timestamp, this.steps);
    }

    /**
     * Get the Step Counter sensor data in dictionary format
     *
     * @return Dictionary containing the Step Counter sensor data in dictionary format:
     * sensor type, sensor type in string, timeIntervalSince1970, numberOfSteps
     */
    @Override
    public HashMap getDataInDict() {
        HashMap multiMap = new HashMap<>();

        multiMap.put("sensorType",this.getSensorType());
        multiMap.put("sensorTypeString",this.getSensorType().toString());
        multiMap.put("timestamp",this.timestamp);
        multiMap.put("numberOfSteps",this.steps);

        return(multiMap);
    }

    /**
     * Get number of steps
     *
     * @return number of steps
     */
    @SuppressWarnings("unused")
    public int getSteps() {
        return this.steps;
    }

}
