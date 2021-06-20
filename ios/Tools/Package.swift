// swift-tools-version:5.3

import PackageDescription

let package = Package(
    name: "Tools",
    products: [
    ],
    dependencies: [
        .package(url: "https://github.com/mono0926/LicensePlist.git", .exact("3.13.0")),
        .package(url: "https://github.com/SwiftGen/SwiftGen.git", .exact("6.4.0")),
        .package(url: "https://github.com/realm/SwiftLint.git", .exact("0.43.1")),
        .package(url: "https://github.com/thii/xcbeautify.git", .exact("0.9.1")),
    ],
    targets: [
    ]
)
