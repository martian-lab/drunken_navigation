package com.martianlab.drunkennavigation.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import kotlinx.android.synthetic.main.fragment_scan.*
import android.media.ToneGenerator
import android.media.AudioManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.vision.Detector
import com.martianlab.drunkennavigation.DrunkApp
import com.martianlab.drunkennavigation.R
import com.martianlab.drunkennavigation.presentation.viewmodel.ScanViewModel
import javax.inject.Inject


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ScanFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ScanFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ScanFragment : Fragment() {

    lateinit var barcodeDetector : BarcodeDetector
    lateinit var cameraSource : CameraSource

    var scanResult = ""

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var qRscanViewModel: ScanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        (activity?.application as DrunkApp).component.inject(this)
        super.onActivityCreated(savedInstanceState)

        qRscanViewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(ScanViewModel::class.java)
        initStuff()
    }


    fun initStuff() {

        /* Initializing objects */

        barcodeDetector = BarcodeDetector.Builder(context)
            .setBarcodeFormats(Barcode.QR_CODE)
            .build()

        setProcessor()

        cameraSource = CameraSource.Builder(context, barcodeDetector)
            .setRequestedPreviewSize(768, 768)
            .setAutoFocusEnabled(true)
            .build();

        /* Adding Callback method to SurfaceView */
        surfaceQRScanner.holder.addCallback(object : SurfaceHolder.Callback {

            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                        cameraSource.start(surfaceQRScanner.getHolder());
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })

    }



    fun setProcessor(){
        /* Adding Processor to Barcode detector */
        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {}

            override fun receiveDetections(detections: Detector.Detections<Barcode>) {
                val barcodes = detections.detectedItems /* Retrieving QR Code */

                if (barcodes.size() > 0) {

                    barcodeDetector.release() /* Releasing barcodeDetector */

                    val toneNotification = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100) /* Setting beep sound */
                    toneNotification.startTone(ToneGenerator.TONE_PROP_BEEP, 150)

                    scanResult = barcodes.valueAt(0).displayValue.toString() /* Retrieving text from QR Code */



                    activity?.runOnUiThread {
                        qRscanViewModel.setScannedText(scanResult)
                        //println( scanResult )
                        //Toast.makeText(context, scanResult, Toast.LENGTH_LONG).show()
                    }

                    Thread.sleep(200)

                    setProcessor()

                }
            }
        })
    }


}
