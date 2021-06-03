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
            name: "SettingFeature",
            targets: ["SettingFeature"]
        )
    ],
    dependencies: [
    ],
    targets: [
        .target(
            name: "AboutFeature",
            dependencies: []
        ),
        .target(
            name: "AppFeature",
            dependencies: [
                .target(name: "AboutFeature"),
                .target(name: "FavoritesFeature"),
                .target(name: "HomeFeature"),
                .target(name: "MediaFeature"),
                .target(name: "SettingFeature"),
                .target(name: "Styleguide"),
            ]
        ),
        .target(
            name: "Component",
            dependencies: [
                .target(name: "Styleguide"),
            ]
        ),
        .target(
            name: "FavoritesFeature",
            dependencies: []
        ),
        .target(
            name: "HomeFeature",
            dependencies: []
        ),
        .target(
            name: "MediaFeature",
            dependencies: []
        ),
        .target(
            name: "Repository",
            dependencies: []
        ),
        .target(
            name: "SettingFeature",
            dependencies: [
                .target(name: "Styleguide"),
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
        dependencies: ["AboutFeature"]
    ),
    .testTarget(
        name: "AppFeatureTests",
        dependencies: ["AppFeature"]
    ),
    .testTarget(
        name: "FavoritesFeatureTests",
        dependencies: ["FavoritesFeature"]
    ),
    .testTarget(
        name: "HomeFeatureTests",
        dependencies: ["HomeFeature"]
    ),
    .testTarget(
        name: "MediaFeatureTests",
        dependencies: ["MediaFeature"]
    ),
])
