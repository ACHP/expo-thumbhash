package expo.modules.thumbhash

import android.graphics.Bitmap

fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    // Step 1: Create an IntArray to store the pixel values.
    val intArray = IntArray(bitmap.width * bitmap.height)

    // Step 2: Use the getPixels() method to get pixel values.
    bitmap.getPixels(intArray, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

    // Step 3: Convert the IntArray to ByteArray.
    val byteArray = ByteArray(bitmap.width * bitmap.height * 4) // * 4 because of R, G, B, A channels.

    for (i in intArray.indices) {
        byteArray[i * 4] = ((intArray[i] and 0xFF0000) shr 16).toByte()       // Red channel
        byteArray[i * 4 + 1] = ((intArray[i] and 0xFF00) shr 8).toByte()         // Green channel
        byteArray[i * 4 + 2] = (intArray[i] and 0xFF).toByte()                   // Blue channel
        byteArray[i * 4 + 3] = ((intArray[i] and 0xFF000000.toInt()) shr 24).toByte() // Alpha channel
    }

    return byteArray
}
