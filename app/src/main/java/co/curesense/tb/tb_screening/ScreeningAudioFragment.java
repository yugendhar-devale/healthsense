package co.curesense.tb.tb_screening;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import java.io.File;
import java.io.IOException;

import co.curesense.tb.R;
import co.curesense.tb.utils.FFT;
import co.curesense.tb.utils.VisualizerView;
import rm.com.audiowave.AudioWaveView;
import rm.com.audiowave.OnProgressListener;

//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;

public class ScreeningAudioFragment extends Fragment {

    Handler h;
    int x = 0;
    int step = 10;

    File audiofile = null;

    private VisualizerView visualizerView;
    private MediaRecorder recorder = new MediaRecorder();
    private Handler handler = new Handler();
    final Runnable updater = new Runnable() {
        public void run() {
            handler.postDelayed(this, 1);
            int maxAmplitude = recorder.getMaxAmplitude();
            if (maxAmplitude != 0) {
                visualizerView.addAmplitude(maxAmplitude);
            }
            double[] x = {maxAmplitude};
            double[] y = {(maxAmplitude / 3.142) / 2};
            doFFT(x, y);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Please note the third parameter should be false, otherwise a java.lang.IllegalStateException maybe thrown.
        final View retView = inflater.inflate(R.layout.fragment_screening_audio, container, false);

        visualizerView = (VisualizerView) retView.findViewById(R.id.visualizer);

        final AudioWaveView wave = retView.findViewById(R.id.wave);
        OnProgressListener listener = new OnProgressListener() {
            @Override
            public void onStartTracking(float v) {

            }

            @Override
            public void onStopTracking(float v) {

            }

            @Override
            public void onProgressChanged(float v, boolean b) {

            }
        };

        wave.setOnProgressListener(listener);

        h = new Handler();

        final Runnable r = new Runnable() {
            @Override
            public void run() {
                x = x + step;
                wave.setProgress(x);
                if (x < 100) {
                    h.postDelayed(this, 1000);
                } else {
                    h.removeCallbacksAndMessages(null);

                    handler.removeCallbacks(updater);
                    recorder.stop();
                    recorder.reset();
                    recorder.release();

                    //addRecordingToMediaLibrary();

                    Toast.makeText(getContext(), "Making analysis decision", Toast.LENGTH_LONG).show();
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ScreeningAudioResultFragment screeningAudioResultFragment
                                    = new ScreeningAudioResultFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.container, screeningAudioResultFragment)
                                    .addToBackStack("tb").commit();
                        }
                    }, 5000);

                }
            }
        };
        retView.findViewById(R.id.record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                h.postDelayed(r, 1000);

                try {
                    recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//                    recorder.setOutputFile(audiofile.getAbsolutePath());
//                    Log.d("yug", "audiofile path:" + audiofile.getAbsolutePath());
                    recorder.setOutputFile("/dev/null");
                    recorder.prepare();
                    recorder.start();

                    handler.post(updater);
                } catch (IllegalStateException | IOException ignored) {
                }

            }
        });

        retView.findViewById(R.id.spectrum_visualizer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context ctx = getActivity();
                Intent i = ctx.getPackageManager()
                        .getLaunchIntentForPackage("github.bewantbe.audio_analyzer_for_android");
                ctx.startActivity(i);
            }
        });

        return retView;
    }

    public void doFFT(double[] x, double[] y) {
//        FFT fft = new FFT(1024);
//        fft.fft(x, y);
    }

    @Override
    public void onDestroy() {
        h.removeCallbacksAndMessages(null);
        super.onDestroy();
    }

    protected void addRecordingToMediaLibrary() {
        //creating content values of size 4
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());

        //creating content resolver and storing it in the external content uri
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(base, values);

        //sending broadcast message to scan the media file so that it can be available
        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
        Toast.makeText(getActivity(), "Added File " + newUri, Toast.LENGTH_LONG).show();
    }

}
