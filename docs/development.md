# Local Development Workflow

## Environment Setup
*   **OS Expected:** Linux/Ubuntu (or compatible OS)
*   **IDE:** Android Studio (latest stable release)
*   **JDK:** Java 17+

## Common Commands
If executing via the integrated Linux terminal:

*   **Build the App:**
    `./gradlew assembleDebug`
*   **Run Unit Tests:**
    `./gradlew testDebugUnitTest`
*   **Clean Project:**
    `./gradlew clean`

## Regression Checklist (Pre-Commit)
1. Verify the app builds cleanly without Gradle sync errors.
2. Launch the app on a local emulator (Pixel 6 or similar recommended).
3. Test one full lifecycle of a Math question (correct answer, incorrect answer, next question).
4. Verify layout scales correctly in landscape orientation (often used on tablets by kids).