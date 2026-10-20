# System Architecture

## Pattern: MVVM (Model-View-ViewModel)

### 1. View (Jetpack Compose)
*   Responsible purely for rendering state.
*   Passes user intents (button clicks, number inputs) up to the ViewModel.
*   *Components:* `MainDashboard`, `MathScreen`, `MoneyScreen`, `CalendarScreen`, `SettingsDialog`.

### 2. ViewModel
*   Maintains the UI state (`StateFlow`).
*   Handles game logic, question progression, configurable round limits (10, 15, 20, 25), and replay rounds for failed questions.
*   *Components:* `MathViewModel`, `MoneyViewModel`, `CalendarViewModel`.

### 3. Model (Data/Logic Providers)
*   Stateless utility classes and engines that perform problem generation and calculations.
*   *Components:*
    *   `MathEngine` (`MathProblem`): Generates 2-digit addition (totals < 100) and subtraction pairs (non-negative results).
    *   `TimeEngine` (`DateInfo`): Calendar logic and date offsets using `java.time.LocalDate`.
    *   `MoneyEngine` (`MoneyProblem`, `CoinInstance`, `CoinType`): Generates US coin clusters with head/tail PNG assets and supports both cent counting and dollar conversion modes.
    *   `CalendarEngine` (`CalendarProblem`): Calendar math problem generator.

## Data Flow
`User Input` -> `Composable Event` -> `ViewModel Intent` -> `ViewModel verifies with Model` -> `ViewModel updates StateFlow` -> `Composable Recomposes`.
