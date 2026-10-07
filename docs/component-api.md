# Component APIs & Contracts

## Models / Engines

### `MathEngine`
*   `fun generateAddition(): MathProblem`
    *   *Returns:* Data class containing `val num1: Int`, `val num2: Int`, `val expectedAnswer: Int`. (Both numbers 10-99).
*   `fun generateSubtraction(): MathProblem`
    *   *Returns:* Ensures `num1 >= num2` to avoid negative answers.

### `TimeEngine`
*   `fun getTodayInfo(): DateInfo`
    *   *Returns:* Data class with current DayOfWeek and LocalDate.
*   `fun getFutureDate(daysToAdd: Long): LocalDate`
    *   *Input:* Number of days (e.g., 7).
    *   *Returns:* Exact date string for matching.

### `MoneyEngine`
*   `fun generateCoinCluser(): CoinProblem`
    *   *Returns:* A list of enums `List<Coin>` (e.g., `[PENNY, DIME, DIME]`) and the `expectedTotalCents: Int`.

## ViewModels

### `MathViewModel`
*   **State:** `uiState: StateFlow<MathUiState>` (contains current problem, user input, and validation state).
*   **Events:**
    *   `fun onDigitPressed(digit: Int)`
    *   `fun onClearPressed()`
    *   `fun onSubmitAnswer()`