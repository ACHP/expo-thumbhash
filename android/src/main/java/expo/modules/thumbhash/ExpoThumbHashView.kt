package expo.modules.thumbhash

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.widget.ImageView
import expo.modules.kotlin.AppContext
import expo.modules.kotlin.viewevent.EventDispatcher
import expo.modules.kotlin.views.ExpoView
import kotlin.system.measureTimeMillis

class ExpoThumbHashView(context: Context, appContext: AppContext) : ExpoView(context, appContext) {

    private val onLoaded by EventDispatcher()

    // We keep a reference to the current hash for now
    private var currentHash: String? = null

    private val imageView = ImageView(context).also {
        it.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        addView(it)
    }

    internal fun setThumbHash(hash: String) {
        currentHash = hash
        val ms = measureTimeMillis{
            val image = ThumbHash.thumbHashToRGBA(Base64.decode(hash, Base64.DEFAULT))
            val intArray = IntArray(image.width * image.height)
            for (i in intArray.indices) {
                val r = image.rgba[i * 4].toInt() and 0xFF
                val g = image.rgba[i * 4 + 1].toInt() and 0xFF
                val b = image.rgba[i * 4 + 2].toInt() and 0xFF
                val a = image.rgba[i * 4 + 3].toInt() and 0xFF

                intArray[i] = ((a and 0xff) shl 24) or ((r and 0xff) shl 16) or ((g and 0xff) shl 8) or (b and 0xff)
            }
            imageView.setImageBitmap(Bitmap.createBitmap(intArray, image.width, image.height , Bitmap.Config.ARGB_8888))
        }
        println("setThumbHash perf: $ms")
        onLoaded(mapOf("hash" to hash))
    }

    internal fun clear(){
        val bitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        imageView.setImageBitmap(bitmap)
        // TODO maybe
        //removeView(imageView);
    }
}
