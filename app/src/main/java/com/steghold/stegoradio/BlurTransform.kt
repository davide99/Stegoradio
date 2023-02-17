package com.steghold.stegoradio

import android.graphics.Bitmap
import android.graphics.Color
import com.squareup.picasso.Transformation


class BlurTransform : Transformation {
    override fun transform(bitmap: Bitmap): Bitmap {
        return blur(bitmap, 64)
    }

    private fun blur(src: Bitmap, ratio: Int): Bitmap {
        val width = src.width
        val height = src.height

        var pixColor: Int

        var newR = 0
        var newG = 0
        var newB = 0

        val gauss = intArrayOf(1, 2, 1, 2, 4, 2, 1, 2, 1)
        var idx: Int

        val pixels = IntArray(width * height)
        src.getPixels(pixels, 0, width, 0, 0, width, height)

        for (i in 1 until height - 1) {
            for (k in 1 until width - 1) {
                idx = 0

                for (m in -1..1) {
                    for (n in -1..1) {
                        pixColor = pixels[(i + m) * width + k + n]

                        newR += Color.red(pixColor) * gauss[idx]
                        newG += Color.green(pixColor) * gauss[idx]
                        newB += Color.blue(pixColor) * gauss[idx]
                        idx++
                    }
                }

                newR = (newR / ratio).coerceIn(0, 255)
                newG = (newG / ratio).coerceIn(0, 255)
                newB = (newB / ratio).coerceIn(0, 255)

                pixels[i * width + k] = Color.rgb(newR, newG, newB)

                newR = 0
                newG = 0
                newB = 0
            }
        }

        src.setPixels(pixels, 0, width, 0, 0, width, height)
        return src
    }

    override fun key(): String {
        return "blur"
    }
}