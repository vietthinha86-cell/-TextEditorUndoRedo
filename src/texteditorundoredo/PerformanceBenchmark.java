package texteditorundoredo;

public class PerformanceBenchmark {

    private static final int[] INPUT_SIZES = {100, 500, 1000, 5000, 10000};
    private static final int TRIALS = 5;

    public static void main(String[] args) {

        System.out.println("Input Size (n), Without Batching Avg Time (ms), "
                + "With Batching Avg Time (ms), Without Batching Stack Entries, "
                + "With Batching Stack Entries");

        for (int n : INPUT_SIZES) {

            double totalWithoutBatching = 0.0;
            double totalWithBatching = 0.0;
            int withoutBatchingEntries = 0;
            int withBatchingEntries = 0;

            for (int trial = 1; trial <= TRIALS; trial++) {

                BenchmarkResult withoutResult = runWithoutBatching(n);
                BenchmarkResult withResult = runWithBatching(n);

                totalWithoutBatching += withoutResult.timeMs;
                totalWithBatching += withResult.timeMs;
                withoutBatchingEntries = withoutResult.stackEntries;
                withBatchingEntries = withResult.stackEntries;
            }

            System.out.printf("%d, %.6f, %.6f, %d, %d%n",
                    n,
                    totalWithoutBatching / TRIALS,
                    totalWithBatching / TRIALS,
                    withoutBatchingEntries,
                    withBatchingEntries);
        }
    }

    private static BenchmarkResult runWithoutBatching(int n) {

        TextEditorManager editor = new TextEditorManager(n + 10);

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            editor.saveStateWithBatching("INSERT", "a", false);
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                (end - start) / 1_000_000.0,
                editor.getEngine().getUndoStack().size()
        );
    }

    private static BenchmarkResult runWithBatching(int n) {

        TextEditorManager editor = new TextEditorManager(n + 10);

        long start = System.nanoTime();

        for (int i = 0; i < n; i++) {
            editor.saveStateWithBatching("INSERT", "a", true);
        }

        long end = System.nanoTime();

        return new BenchmarkResult(
                (end - start) / 1_000_000.0,
                editor.getEngine().getUndoStack().size()
        );
    }

    private static class BenchmarkResult {

        private final double timeMs;
        private final int stackEntries;

        private BenchmarkResult(double timeMs, int stackEntries) {
            this.timeMs = timeMs;
            this.stackEntries = stackEntries;
        }
    }
}
