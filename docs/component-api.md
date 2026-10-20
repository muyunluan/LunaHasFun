# Component APIs & Contracts

## Models / Engines

### `MathEngine`
*   `fun generateAddition(): MathProblem`
    *   *Returns:* `MathProblem` containing `val num1: Int`, `val num2: Int`, `val expectedAnswer: Int` (< 100).
*   `fun generateSubtraction(): MathProblem`
    *   *Returns:* `MathProblem` ensuring `num1 >= num2` to avoid negative answers.

### `TimeEngine`
*   `fun getTodayInfo(): DateInfo`
    *   *Returns:* `DateInfo` with current `DayOfWeek` and `LocalDate`.
*   `fun getFutureDate(daysToAdd: Long): LocalDate`
    *   *Input:* Number of days (e.g., 7).
    *   *Returns:* Exact `LocalDate`.

### `MoneyEngine`
*   `fun generateCoinCluster(): MoneyProblem`
    *   *Returns:* `MoneyProblem` containing `List<CoinInstance>` (with randomized head/tail PNG assets), `expectedTotalCents: Int`, and `problemType` (`CENT_COUNTING` or `DOLLAR_CONVERSION`).

### `CalendarEngine`
*   `fun generateQuestion(): CalendarProblem`
    *   *Returns:* `CalendarProblem` containing question text and target date.

## ViewModels

### `MathViewModel` / `MoneyViewModel` / `CalendarViewModel`
*   **State:** `uiState: StateFlow<UiState>` (contains current problem, user input, score, question number, total questions, mode, and failed problems for review).
*   **Events:**
    *   `fun onDigitPressed(digit: Int)`
    *   `fun onDotPressed()`
    *   `fun onClearPressed()`
    *   `fun onSubmitAnswer()`
    *   `fun nextProblem()`
    *   `fun restartQuiz()`
    *   `fun setTotalQuestions(count: Int)`
