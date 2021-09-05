// swift-tools-version:5.3

import PackageDescription

// MARK: - Main Package
var package = Package(
    name: "DroidKaigi2021Package",
    defaultLocalization: "ja",
    platforms: [
        .iOS(.v14)
    ],
    products: [
        .library(
            name: "AboutFeature",
            targets: ["AboutFeature"]
        ),
        .library(
            name: "AppFeature",
            targets: ["AppFeature"]
        ),
        .library(
            name: "Component",
            targets: ["Component"]
        ),
        .library(
            name: "FavoritesFeature",
            targets: ["FavoritesFeature"]
        ),
        .library(
            name: "HomeFeature",
            targets: ["HomeFeature"]
        ),
        .library(
            name: "MediaFeature",
            targets: ["MediaFeature"]
        ),
        .library(
            name: "Model",
            targets: ["Model"]
        ),
        .library(
            name: "Repository",
            targets: ["Repository"]
        ),
        .library(
            name: "SettingFeature",
            targets: ["SettingFeature"]
        ),
        .library(
            name: "Styleguide",
            targets: ["Styleguide"]
        ),
        .library(
            name: "Utility",
            targets: ["Utility"]
        ),
        .library(
            name: "DroidKaigiMPP",
            targets: ["DroidKaigiMPP"]
        ),
    ],
    dependencies: [
        .package(url: "https://github.com/pointfreeco/swift-composable-architecture.git", .exact("0.19.0")),
        .package(name: "Introspect", url: "https://github.com/siteline/SwiftUI-Introspect.git", .upToNextMajor(from: "0.1.3")),
        .package(url: "https://github.com/kean/NukeUI.git", .exact("0.6.1")),
        .package(name: "Firebase", url: "https://github.com/firebase/firebase-ios-sdk.git", .exact("8.1.1")),
        .package(url: "https://github.com/noreasonprojects/ModernAVPlayer.git", .exact("1.7.3")),
        .package(name: "SnapshotTesting", url: "https://github.com/pointfreeco/swift-snapshot-testing.git", .exact("1.9.0")),
    ],
    targets: [
        .target(
            name: "AboutFeature",
            dependencies: [
                .target(name: "Component"),
                .target(name: "Styleguide"),
                .product(name: "Introspect", package: "Introspect"),
                .product(name: "ComposableArchitecture", package: "swift-composable-architecture"),
            ]
        ),
        .target(
            name: "AppFeature",
            dependencies: [
                .target(name: "AboutFeature"),
                .target(name: "Component"),
                .target(name: "FavoritesFeature"),
                .target(name: "HomeFeature"),
                .target(name: "MediaFeature"),
                .target(name: "Player"),
                .target(name: "Repository"),
                .target(name: "SettingFeature"),
                .target(name: "Styleguide"),
                .product(name: "ComposableArchitecture", package: "swift-composable-architecture"),
            ]
        ),
        .target(
            name: "Component",
            dependencies: [
                .target(name: "Model"),
                .target(name: "Styleguide"),
                .product(name: "Introspect", package: "Introspect"),
                .product(name: "NukeUI", package: "NukeUI"),
            ]
        ),
        .target(
            name: "FavoritesFeature",
            dependencies: [
                .target(name: "Component"),
                .target(name: "Model"),
                .target(name: "Repository"),
                .target(name: "Styleguide"),
                .product(name: "ComposableArchitecture", package: "swift-composable-architecture"),
            ]
        ),
        .target(
            name: "HomeFeature",
            dependencies: [
                .target(name: "Component"),
                .target(name: "Repository"),
                .target(name: "Styleguide"),
                .product(name: "ComposableArchitecture", package: "swift-composable-architecture"),
            ]
        ),
        .target(
            name: "MediaFeature",
            dependencies: [
                .target(name: "Component"),
                .target(name: "Model"),
                .target(name: "Repository"),
                .target(name: "Styleguide"),
                .product(name: "ComposableArchitecture", package: "swift-composable-architecture"),
                .product(name: "Introspect", package: "Introspect"),
            ]
        ),
        .target(
            name: "Model",
            dependencies: [
                .target(name: "DroidKaigiMPP"),
            ]
        ),
        .target(
            name: "Player",
            dependencies: [
                .product(name: "ModernAVPlayer", package: "ModernAVPlayer")
            ]
        ),
        .target(
            name: "Repository",
            dependencies: [
                .target(name: "DroidKaigiMPP"),
                .target(name: "Model"),
                .product(name: "FirebaseAuth", package: "Firebase"),
            ]
        ),
        .target(
            name: "SettingFeature",
            dependencies: [
                .target(name: "Component"),
                .target(name: "Styleguide"),
                .product(name: "ComposableArchitecture", package: "swift-composable-architecture"),
            ]
        ),
        .target(
            name: "Styleguide",
            resources: [
                .process("Color.xcassets"),
                .process("Image.xcassets"),
                .process("Resources"),
            ]
        ),
        .target(
            name: "Utility",
            dependencies: []
        ),
    ]
)

// MARK: - Test Targets
package.targets.append(contentsOf: [
    .testTarget(
        name: "AboutFeatureTests",
        dependencies: [
            "AboutFeature",
            "TestUtils",
        ],
        exclude: ["__Snapshots__"]
    ),
    .testTarget(
        name: "AppFeatureTests",
        dependencies: [
            "AppFeature",
            "TestUtils",
        ],
        exclude: ["__Snapshots__"]
    ),
    .testTarget(
        name: "ComponentTests",
        dependencies: [
            "Component",
            "TestUtils",
        ],
        exclude: ["__Snapshots__"]
    ),
    .testTarget(
        name: "FavoritesFeatureTests",
        dependencies: [
            "FavoritesFeature",
            "TestUtils",
        ],
        exclude: ["__Snapshots__"]
    ),
    .testTarget(
        name: "HomeFeatureTests",
        dependencies: [
            "HomeFeature",
            "TestUtils",
        ],
        exclude: ["__Snapshots__"]
    ),
    .testTarget(
        name: "MediaFeatureTests",
        dependencies: [
            "MediaFeature",
            "TestUtils",
        ],
        exclude: ["__Snapshots__"]
    ),
    .testTarget(
        name: "SettingFeatureTests",
        dependencies: [
            "SettingFeature",
            "TestUtils",
        ],
        exclude: ["__Snapshots__"]
    ),
    .target(
        name: "TestUtils",
        dependencies: [
            .product(name: "SnapshotTesting", package: "SnapshotTesting"),
        ],
        path: "Tests/TestUtils"
    ),
])

// MARK: - Library Targets
package.targets.append(contentsOf: [
    .binaryTarget(
        name: "DroidKaigiMPP",
        path: "build/xcframeworks/DroidKaigiMPP.xcframework"
    )
])
