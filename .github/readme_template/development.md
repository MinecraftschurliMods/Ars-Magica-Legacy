## Development

### Setup

1. Clone the repository.
2. Run `./gradlew genIntellijRuns` / `./gradlew genEclipseRuns` / `./gradlew genVSCodeRuns` depending on your IDE to set up the workspace.
3. Open the project in your IDE.

### Launching the game

#### Client

To launch the client either:

1. Run the `runClient` run configuration generated in setup.
2. Run `./gradlew runClient` in the project directory.

#### Server

To launch the dedicated server either:

1. Run the `runServer` run configuration generated in setup.
2. Run `./gradlew runServer` in the project directory.

### Running the data generators

To run the data generators either:

1. Run the `runData` run configuration generated in setup.
2. Run `./gradlew runData` in the project directory.

### Testing

#### Adding test cases

1. Create a new class in `src/test/java` in the package `com.github.minecraftschurli.arsmagicalegacy.test`.
2. Add the `@PrefixGameTestTemplate(false)` and `@GameTestHolder(ArsMagicaAPI.MOD_ID)` annotations to the class.
3. Add the `@GameTest` annotation to the test method and specify the template name (e.g. `@GameTest(template = "example")`). Use the `empty` template for tests that don't require a specific template.
4. Write the test case. Use the `GameTestHelper` parameter of your test method to fail or succeed. Your testcase is required to call succeed at least once or it will fail with a timeout.

#### Running tests

To run the tests either:

1. Run the `runGameTestServer` run configuration generated in setup.
2. Run `./gradlew runGameTestServer` in the project directory.
3. Launch the client and use the `/test` command.

### Building

Run `./gradlew build` to build the mod. The resulting jars will be located in `build/libs`.

## Legal disclaimer

Ars Magica is a trademark of Atlas Games®, used with permission.

Some textures used in this mod are property of D3miurge, used with permission.
