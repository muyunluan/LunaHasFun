# System Architecture

## Pattern: MVVM (Model-View-ViewModel)

### 1. View (Jetpack Compose)
*   Responsible purely for rendering state.
*   Passes user intents (button clicks, number inputs) up to the ViewModel.
*   *Components:* `DashboardScreen`, `MathScreen`, `MoneyScreen`, `CalendarScreen`.

### 2. ViewModel
*   Maintains the UI state (`StateFlow`).
*   Handles game logic: checking answers, updating scores, and generating new questions.
*   *Components:* `MathViewModel`, `MoneyViewModel`, `CalendarViewModel`.

### 3. Model (Data/Logic Providers)
*   Stateless utility classes that perform the actual generation.
*   *Components:* `QuestionGenerator`, `CoinCalculator`, `DateManager`.

## Data Flow
`User Input` -> `Composable Event` -> `ViewModel Intent` -> `ViewModel verifies with Model` -> `ViewModel updates StateFlow` -> `Composable Recomposes`.