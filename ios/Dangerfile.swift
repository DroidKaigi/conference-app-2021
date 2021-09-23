import Danger

func run(_ danger: DangerDSL) {
    // Run SwiftLint
    SwiftLint.lint(inline: true)

    if let github = danger.github {
        let pr = github.pullRequest

        // Make it more obvious that a PR is a work in progress and shouldn't be merged yet
        if pr.title.lowercased().contains("wip") {
            danger.warn("PR is classed as Work in Progress")
        }
    }

    danger.message("Hello Danger🚀")
}

// MARK: -
run(Danger())
