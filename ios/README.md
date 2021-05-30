# DroidKaigi 2021 iOS Application

## Get started
- Run `$ make open` to open this project.

### How to preview SwiftUI View

Change to build scheme which places the view  you want to preview.

For example, if you preview `AboutScreen.swift` file, change build scheme to `AboutFeature` .

## Requirements

Xcode version is 12.4

## Project structure

```
ios
├── DroidKaigi\ 2021
|   |   // Debug Configuration project
│   ├── Debug.xcodeproj/
|   |   // App Sources
│   ├── DroidKaigi\ 2021/
│   ├── Package.swift
|   |   // Release Configuration project
│   └── Release.xcodeproj/
├── DroidKaigi2021.xcworkspace
├── Mintfile
├── Package.swift
├── README.md
|   // Swift Pacakge Sources
├── Sources
|   // Swift Pacakge Tests
└── Tests
```

### Modules

- Feature modules
    - Module for each feature.
- Component
    - Common components
    - [Defined components](https://www.figma.com/file/D2VLqh0xOXbH0zB6cTz053/DroidKaigi_2021_official_App-(iOS)?node-id=140%3A8796) are mainly placed.
- Repository (If needed)
    - Connecting with KMM Repository layer
- Utility
    - Common logic, extensions
- etc (If needed)
