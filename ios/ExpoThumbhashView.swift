import ExpoModulesCore
import WebKit

class ExpoThumbhashView: ExpoView {
    
    let imageView = UIImageView()

    required init(appContext: AppContext? = nil) {
        super.init(appContext: appContext)
        clipsToBounds = true
        addSubview(imageView)
    }

    func base64PaddedString(_ str: String) -> String {
        let remainder = str.count % 4
        
        if remainder > 0 {
            let paddingNeeded = 4 - remainder
            return str + String(repeating: "=", count: paddingNeeded)
        }
        
        return str
    }
    
    func renderHash(hash: String) {
        if let data = Data(base64Encoded: base64PaddedString(hash)) {
            imageView.image = thumbHashToImage(hash: data)
            imageView.image = thumbHashToImage(hash: data)
        } else {
            NSLog("Test ICI ?" + hash)
            imageView.image = UIGraphicsImageRenderer(size: CGSize(width: 1, height: 1)).image {ctx in }
        }
    }
    
    func clear() {
        imageView.image = UIGraphicsImageRenderer(size: CGSize(width: 1, height: 1)).image {ctx in }
    }

    override func layoutSubviews() {
        imageView.frame = bounds
    }
}
