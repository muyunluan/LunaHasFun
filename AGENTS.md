# AI Agent Instructions

## Project Context
This is a native Android application built for 2nd-grade students to practice fundamental math (2-digit addition/subtraction), money counting (via coin images), and basic calendar logic (current date, +7 days).

## Tech Stack
*   **Language:** Kotlin
*   **UI Framework:** Jetpack Compose
*   **Architecture:** MVVM (Model-View-ViewModel)
*   **IDE:** Android Studio

## Agent Rules
1.  **Code Generation:** Always write modern, idiomatic Kotlin. Use coroutines for any asynchronous work.
2.  **UI Rules:** Use Jetpack Compose exclusively. Avoid XML layouts. Build reusable, stateless composables.
3.  **Simplicity:** Keep logic readable. The target audience is children, so error handling should result in gentle visual cues rather than complex stack traces or harsh error messages.
4.  **Naming Conventions:**
    *   Composables: PascalCase (e.g., `MathQuizScreen`)
    *   Variables/Functions: camelCase (e.g., `generateMathProblem`)
    *   Constants: SCREAMING_SNAKE_CASE