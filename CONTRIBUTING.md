# Contributing to Ars Magica: Legacy

First of all, thank you for taking your time to contribute to this project. It's people like you that allow this mod to
grow into something even better.

## I've Found an Issue!

If you found something you believe to be an **issue**, please **open an issue** in the **Issues tab** of this
repository and fill out the form you are presented with. Alternatively, you can join the **Discord server** and discuss
the issue in more depth with us. Usually, this helps the team track down the issue and speeds up the bug fixing process.

## New Features

Similarly, **new features** can be suggested through the **Issues tab** or the **Discord**. The latter is generally
preferred, as giving feedback is easier there.

## The Contribution Process

### Setup

If you want to **contribute** bugfixes or new features, we welcome you with open arms! We have just a few easy
**requirements**:

- **You must know your way around Java, your IDE, and the Minecraft and NeoForge ecosystem.** We simply don't have the 
  time to teach people Java or modding as they go.
- **You must conform with the styleguide** (see below).
- **You should know your way around Git and GitHub.** If you don't know what a branch or a PR is, please familiarize
  yourself with that first.
- **For new features, contact us beforehand.** Use one of the aforementioned ways to do so. For bugfixes, approval by
  the team is a good thing to have, but not required.
- We recommend using the **IntelliJ** IDE, however, you can use Eclipse as well.

If you meet those requirements, you can start contributing. First, you should fork the repository and create a new
branch. We generally only support the latest version (26.1 at the time of writing), so that's what you should branch
off.

You should pick a branch name that describes what you're doing. For example, a good branch name would be something like
`feature/translations-pt-br` or `fix/occulus`. Prefixes are appreciated, but not required.

### Implementing, Launching and Testing

Like with most other Minecraft mod environments, the run configurations will show up after Gradle has finished importing.
Ars Magica: Legacy has 5 run configurations: the usual `Client`, `ClientData`, `Server` and `ServerData`, alongside the
`GameTestServer` configuration. The game test server configuration is likely irrelevant for you, unless you plan to
modify the test sourceset.

Once your environment is up and running, you can implement your fix or feature. Again, ideally keep us updated on what
you're working on, so we can make sure this still fits the original idea.

### Getting Help

Everyone is a beginner at first, and helping is nothing to be ashamed for. We will more than gladly help you, just ask
on the Discord server.

### Styleguide

We generally recommend using IntelliJ and the **Editorconfig plugin**, as this accurately represents the style used by
this project. You can then rearrange your code according to the project's editorconfig file (Ctrl+Alt+L in IntelliJ).

Some of the most important style choices are:

- **4 spaces** (not 2 spaces, not 8 spaces, not tabs)
- **Opening braces on same lines** (e.g. `if (condition) {`)
- **Closing braces and else on same lines** (e.g. `} else {`)
- **Braces for one-line statements**, except for `break`, `continue`, `return` or `throw` statements
- Use **explicit types** instead of `var`

### Assets and Data

Generally, **datagen** is preferred over raw JSON files. This means that if you e.g. add a new item, you should add the
corresponding datagen as well. For more complex JSON models, please contact the team if you're not sure how to proceed.
Obviously, this isn't required for texture and sound files.

## The PR

### Opening

The PR title and descriptions should accurately **describe what the PR does**. Usually, the PR name would correspond to
your branch name, and the description would give an overview of what the PR changes, and why. Smaller PRs don't need a
description if the title says it all.

To reiterate on the previous example of the branch `feature/translations-pt-br`, the name and descriptions would look
something like this:

```
Title: Add translations for pt_br

Description: This PR adds translations for Brazilian Portuguese. It also adds a variant of the AMLanguageProvider to
generate these translations.
```

### Getting and Addressing Geedback

Once your PR is opened, one of the team members will **review** it. If changes are required, they will be communicated
through **review comments**. If you're unsure what the team wants, ask in the comment, or ask on Discord.

### Finalization

When your PR has no comments left (this might already be the case on the first review), it will be **approved and
merged** by a team member. You can then delete your branch if you want.
