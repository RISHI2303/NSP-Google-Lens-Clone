package com.example.googlelensclone.imagelabeler

import androidx.core.content.ContextCompat
import com.example.googlelensclone.BaseLensActivity


class ImageLabelingActivity : BaseLensActivity() {
    override val imageAnalyzer = ImageLabelAnalyzer()

    override fun startScanner() {
        startImageLabeling()
    }

    private fun startImageLabeling() {
        imageAnalysis.setAnalyzer(
            ContextCompat.getMainExecutor(this),
            imageAnalyzer
        )
    }
}
