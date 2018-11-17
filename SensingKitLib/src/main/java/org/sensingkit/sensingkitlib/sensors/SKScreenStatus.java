/*
 * Copyright (c) 2015. Kleomenis Katevas
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

package org.sensingkit.sensingkitlib.sensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.sensingkit.sensingkitlib.SKException;
import org.sensingkit.sensingkitlib.SKExceptionErrorCode;
import org.sensingkit.sensingkitlib.SKSensorType;
import org.sensingkit.sensingkitlib.configuration.SKConfiguration;
import org.sensingkit.sensingkitlib.configuration.SKScreenStatusConfiguration;
import org.sensingkit.sensingkitlib.data.SKAbstractData;
import org.sensingkit.sensingkitlib.data.SKScreenStatusData;

public class SKScreenStatus extends SKAbstractSensor {

    private final BroadcastReceiver mBroadcastReceiver;

    @SuppressWarnings("unused")
    private static final String TAG = SKScreenStatus.class.getName();

    public SKScreenStatus(final Context context, final SKScreenStatusConfiguration configuration) throws SKException {
        super(context, SKSensorType.SCREEN_STATUS, configuration);

        mBroadcastReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {

                // Read Status
                int status;

                String action = intent.getAction();

                switch (action) {
                    case Intent.ACTION_SCREEN_OFF:
                        status = SKScreenStatusData.SCREEN_OFF;
                        break;
                    case Intent.ACTION_SCREEN_ON:
                        status = SKScreenStatusData.SCREEN_ON;
                        break;
                    case Intent.ACTION_USER_PRESENT:
                        status = SKScreenStatusData.SCREEN_UNLOCKED;
                        break;
                    default:
                        status = SKScreenStatusData.SCREEN_UNKNOWN;
                        break;
                }

                // Build the data object
                SKAbstractData data = new SKScreenStatusData(System.currentTimeMillis(), status);

                // Submit sensor data object
                submitSensorData(data);
            }
        };
    }

    @Override
    public void setConfiguration(SKConfiguration configuration) throws SKException {

        // Check if the correct configuration type provided
        if (!(configuration instanceof SKScreenStatusConfiguration)) {
            throw new SKException(TAG, "Wrong SKConfiguration class provided (" + configuration.getClass() + ") for sensor SKScreenStatus.",
                    SKExceptionErrorCode.CONFIGURATION_NOT_VALID);
        }

        // Set the configuration
        super.setConfiguration(configuration);
    }

    @Override
    public SKConfiguration getConfiguration() {
        return new SKScreenStatusConfiguration((SKScreenStatusConfiguration)mConfiguration);
    }

    @Override
    protected boolean shouldPostSensorData(SKAbstractData data) {

        // Always post sensor data
        return true;
    }

    @Override
    public void startSensing() {

        this.isSensing = true;

        registerLocalBroadcastManager();
    }

    @Override
    public void stopSensing() {

        unregisterLocalBroadcastManager();

        this.isSensing = false;
    }

    private void registerLocalBroadcastManager() {

        // Register for SCREEN_STATUS ON, OFF and UNLOCKED (USER_PRESENT) notifications
        mApplicationContext.registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
        mApplicationContext.registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
        mApplicationContext.registerReceiver(mBroadcastReceiver, new IntentFilter(Intent.ACTION_USER_PRESENT));
    }

    private void unregisterLocalBroadcastManager() {
        mApplicationContext.unregisterReceiver(mBroadcastReceiver);
    }
}
