import Foundation

@objc public class SunmiUHF: NSObject {
    @objc public func echo(_ value: String) -> String {
        print(value)
        return value
    }
}
