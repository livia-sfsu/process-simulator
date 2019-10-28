package edu.sfsu;

import com.google.common.base.Stopwatch;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simulates a CPU. Each CPU cycle lasts one second.
 */
class CentralProcessingUnit {

  private static final Duration cycleDuration = Duration.ofSeconds(1);

  private Stopwatch stopwatch = Stopwatch.createUnstarted();
  private Timer timer = new Timer("Simulation system timer", true);
  private RunningProcess runningProcess = null;
  private ProcessScheduler scheduler = null;

  /**
   * Creates a CPU that simulates one cycle every cycleDuration.
   */
  CentralProcessingUnit() {
    // Start the cycle timer.
    timer.scheduleAtFixedRate(new TimerTask() {
                                @Override
                                public void run() {
                                  tick();
                                }
                              },
        0,
        cycleDuration.toMillis());
  }

  /**
   * Runs a new process.
   *
   * @param scheduler the scheduler calling the CPU
   * @param process the new process
   * @param targetCycles how many cycles this process should run.
   */
  synchronized void runProcess(ProcessScheduler scheduler, SimulatedProcess process,
      int targetCycles) {
    //System.out.println(process.priority + "I Am In RunProcess");
    runningProcess = new RunningProcess(process, targetCycles);
    this.scheduler = scheduler;

    if (!stopwatch.isRunning()) {
      stopwatch.start();
    }
  }

  /**
   * @return true when the CPU is idle (not running any program.)
   */
  boolean isIdle() {
    return runningProcess == null;
  }

  /**
   * Called on each cycle.
   */
  private void tick() {
    // Is there a process running?
    if (runningProcess != null) {
      // Has it run enough cycles?
      if (runningProcess.elapsedCycles() >= runningProcess.targetCycles()) {
        // This process is done. Clear out and notify the scheduler.
        SimulatedProcess doneProcess = runningProcess.process;
        runningProcess = null;
        scheduler.processDone(doneProcess);
      } else {
        runningProcess.tick();
      }
    } else {
      // Nothing is running.
      System.out.println("NO-OP");
    }
  }

  /**
   * Wraps a SimulatedProcess and its intended target cycles.
   */
  private class RunningProcess {

    SimulatedProcess process;
    int elapsedCycles = 0;
    int targetCycles;

    RunningProcess(SimulatedProcess process, int targetCycles) {
      this.process = process;
      this.targetCycles = targetCycles;
    }

    void tick() {
      elapsedCycles++;
      process.tick();
    }

    int elapsedCycles() {
      return elapsedCycles;
    }

    int targetCycles() {
      return targetCycles;
    }
  }

}
