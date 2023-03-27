# Contributing to Ars Magica: Legacy

First of all, thank you for taking your time to contribute to this project. It's people like you that allow this mod to
grow into something even better.

## I've found an issue!

If you found something you believe to be an **issue**, please **open an issue** in the **Issues tab** of this
repository and fill out the form you are presented with. Alternatively, you can join the **Discord server** and discuss
the issue in more depth with us. Usually, this helps the team track down the issue and speeds up the bug fixing process.

## New features

Similarly, **new features** can be suggested through the **Issues tab** or the **Discord**. The latter is generally
preferred, as giving feedback is easier there.

## The contribution process

### Setup

If you want to **contribute** bugfixes or new features, we welcome you with open arms! We have just a few easy
**requirements**:

- **You must know your way around Java, your IDE, and the Minecraft Forge ecosystem.** We simply don't have the time to
  teach people Java or modding as they go.
- **You must conform with the styleguide** (see below).
- **You should know your way around Git and GitHub.** If you don't know what a branch or a PR is, please familiarize
  yourself with that first.
- **For new features, contact us beforehand.** Use one of the aforementioned ways to do so. For bugfixes, approval by
  the team is a good thing to have, but not required.

We recommend using the **IntelliJ** IDE, however, you can use Eclipse or other IDEs as well. Note that we won't be able
to support the

If you meet those requirements, you can start contributing. First, you should fork the repository and create a new
branch. As we support multiple versions at a time, we generally develop off the **older version** (1.18.2 at the time of
writing) and port features forward, so you should fork the older version's branch.

You should pick a branch name that describes what you're doing. For example, a good branch name would be something like
`feature/translations-pt-br` or `fix/occulus`. Prefixes are appreciated, but not required.

### Implementing, launching and testing

Like with most other Minecraft mod environments, after Gradle setup has completed, you can execute the run generator
task for your IDE (e.g. `genIntellijRuns`). Ars Magica: Legacy has 4 run configurations: the usual `runClient`,
`runData` and `runServer`, alongside the `runGameTestServer` configuration. The game test server configuration is likely
irrelevant for you, unless you plan to modify the test sourceset.

Once your environment is up and running, you can implement your fix or feature. Again, ideally keep us updated on what
you're working on, so we can make sure this still fits the original idea.

### Getting help

Everyone is a beginner at first, and helping is nothing to be ashamed for. We will more than gladly help you, just ask
on the Discord server.

### Styleguide

We generally recommend using IntelliJ and the **Editorconfig plugin**, as this accurately represents the style used by
this project. You can then rearrange your code according to the project's editorconfig file (Ctrl+Alt+L in IntelliJ).

Some of the most important style choices are:

- **4 spaces** (not 8 spaces, not tabs)
- **Opening braces on same lines** (e.g. `if (condition) {`)
- **Closing braces and else on same lines** (e.g. `} else {`)
- **Braces for one-line statements**, except for `break`, `continue`, `return` or `throw` statements
- Use **explicit types** instead of `var`, except for avoiding class name conflicts or when using `ArsMagicaAPI` method
  calls

### Assets and data

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

### Getting and addressing feedback

Once your PR is opened, one of the team members will **review** it. If changes are required, they will be communicated
through **review comments**. If you're unsure what the team wants, ask in the comment, or ask on Discord.

### Finalization

When your PR has no comments left (this might already be the case on the first review), it will be **approved and
merged** by a team member. You can then delete your branch if you want.

## Porting forward

After your PR has been merged, we would ask you to port its functionality forward to newer versions. This is especially
the case for larger PRs; smaller bugfixes can also be part of one of the regular forward-porting PRs made by the team.
Porting forward is also not a requirement for functionality changes that have been reworked in newer versions.

For porting forward, we ask you to reimplement your change from scratch, as version diffs tend to be very large. In most
**cases, looking through the old PR's changed files and cherry-picking the necessary changes is all you need to do.

If for whatever reason (scheduling conflicts, internal reworks in Minecraft or Forge, etc.) you cannot handle the
porting, please reach out to the team. A solution will be found.
