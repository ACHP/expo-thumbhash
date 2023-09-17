package expo.modules.thumbhash

import android.graphics.Bitmap
import android.util.Base64
import com.facebook.common.executors.CallerThreadExecutor
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSource
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber
import com.facebook.imagepipeline.image.CloseableImage
import com.facebook.imagepipeline.request.ImageRequest
import expo.modules.kotlin.Promise
import expo.modules.kotlin.modules.Module
import expo.modules.kotlin.modules.ModuleDefinition
import expo.modules.thumbhash.ThumbHash.rgbaToThumbHash


class ExpoThumbhashModule : Module() {

  // Each module class must implement the definition function. The definition consists of components
  // that describes the module's functionality and behavior.
  // See https://docs.expo.dev/modules/module-api for more details about available components.
  override fun definition() = ModuleDefinition {
    // Sets the name of the module that JavaScript code will use to refer to the module. Takes a string as an argument.
    // Can be inferred from module's class name, but it's recommended to set it explicitly for clarity.
    // The module will be accessible from `requireNativeModule('ExpoThumbhash')` in JavaScript.
    Name("ExpoThumbHash")

    // Sets constant properties on the module. Can take a dictionary or a closure that returns a dictionary.
    Constants(
      "PI" to Math.PI
    )

    // Defines event names that the module can send to JavaScript.
    Events("onChange")

    // Defines a JavaScript synchronous function that runs the native code on the JavaScript thread.
    Function("hello") {
      "Hello, is it me you're looking for ?"
    }

    // Defines a JavaScript function that always returns a Promise and whose native code
    // is by default dispatched on the different thread than the JavaScript runtime runs on.
    AsyncFunction("encode") { value: String, promise: Promise ->


      val dataSource = Fresco.getImagePipeline().fetchDecodedImage(ImageRequest.fromUri(value), appContext)

      dataSource.subscribe(object : BaseBitmapDataSubscriber() {
        override fun onNewResultImpl(bitmap: Bitmap?) {
          if(bitmap == null){
            promise.reject("LOAD_ERROR", null, Exception("Didn't received any bitmap"))
            return@onNewResultImpl
          } else {
            try{
              val byteArray = bitmapToByteArray(Bitmap.createScaledBitmap(bitmap, 64, 64, false))
              promise.resolve(String(Base64.encode(rgbaToThumbHash(64, 64, byteArray), Base64.DEFAULT), Charsets.UTF_8))
            } catch (e: Throwable){
              e.printStackTrace()
              promise.reject("CODE", e.message, e)
            }
          }
          dataSource.close()
        }

        override fun onFailureImpl(dataSource: DataSource<CloseableReference<CloseableImage>>) {
          try {
            if (dataSource.failureCause != null) {
              promise.reject("LOAD_ERROR", dataSource.failureCause?.message, dataSource.failureCause)
            } else {
              promise.reject("LOAD_ERROR", null, Exception("Failed to load URI!"))
            }
          } finally {
            dataSource.close()
          }
        }

      }, CallerThreadExecutor.getInstance())
    }

    // Enables the module to be used as a native view. Definition components that are accepted as part of
    // the view definition: Prop, Events.
    View(ExpoThumbHashView::class) {
      Events("onLoaded")
      // Defines a setter for the `name` prop.
      Prop("hash") { view: ExpoThumbHashView, prop: String? ->
        if(prop != null){
          view.setThumbHash(prop);
        }else{
          view.clear()
        }
      }
    }
  }
}
