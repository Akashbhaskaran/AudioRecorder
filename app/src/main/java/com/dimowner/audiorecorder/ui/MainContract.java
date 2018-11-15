/*
 * Copyright 2018 Dmitriy Ponomarenko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dimowner.audiorecorder.ui;

import android.content.Context;

import com.dimowner.audiorecorder.Contract;
import com.dimowner.audiorecorder.audio.SoundFile;

public interface MainContract {

	interface View extends Contract.View {

		void showRecordingStart();

		void showRecordingStop();

		void showPlayStart();

		void showPlayPause();

		void showPlayStop();

		void showWaveForm(int[] waveForm);

		void showDuration(String duration);

		void onPlayProgress(long mills, int px);
	}

	interface UserActionsListener extends Contract.UserActionsListener<MainContract.View> {

		void recordingClicked();

		void playClicked();

		void deleteAll();

		void loadLastRecord();

		void updateRecordingDir(Context context);
	}
}
