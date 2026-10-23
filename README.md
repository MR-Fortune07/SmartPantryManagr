Smart Pantry Manager

This is an Android app that helps reduce food waste. A user adds the ingredients they have at home, and the app suggests only the recipes they can cook right now with exactly those ingredients.

Features
- Add, edit and delete pantry items (name, quantity, unit, optional expiry date)
- Input validation on the ingredient form
- 22 preloaded recipes
- Strict matching: a recipe is suggested only if every ingredient is in the
  pantry in the required amount
- Handles plurals (tomato / tomatoes) and unit differences (kg / g)
- Recipe detail screen with the full ingredient list and method
- "Almost there" tab for recipes missing one ingredient
- Settings: expiring-soon alert and default unit


Database: SQLite
I chose to use SQLite because it is local, it works offline, it does not need a server or account, and data persists after the app is closed.

How to run:

1.First clone the repository from GitHub https://github.com/MR-Fortune07/SmartPantryManagr.git
2.Open the folder in Android Studio. 
3.Wait for Gradle to sync.
4.Pick an emulator or a phone with USB debugging, then press the Run button.
5.The app starts on the pantry screen,and the 22 recipes are loaded into the database the first time it launches.
