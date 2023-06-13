# API Changes

- The `api/datagen` has been reworked. All of our data providers now inherit from a common superclass that contains
  some (mostly IO-related) functionality and defines the base methods. If you work with data generators in any way, this
  mainly means some renaming.