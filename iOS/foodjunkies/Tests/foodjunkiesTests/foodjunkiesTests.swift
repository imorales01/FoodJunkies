import XCTest
@testable import foodjunkies

class foodjunkiesTests: XCTestCase {
    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct
        // results.
        XCTAssertEqual(foodjunkies().text, "Hello, World!")
    }


    static var allTests = [
        ("testExample", testExample),
    ]
}
