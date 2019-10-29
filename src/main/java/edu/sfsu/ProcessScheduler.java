package edu.sfsu;

import com.google.common.base.Preconditions;
import java.util.PriorityQueue;

/**
 * Process Scheduler
 *
 * Instructions: upgrade the current round-robin implementation to a priority based one. This is the
 * most important part of your assignment. Remember that each process has its own priority.
 */
class ProcessScheduler {

  private static final int RUN_CYCLES = 5; // Default number of cycles given to each process on a single run.
  private final PriorityQueue<SimulatedProcess> processControlBlock = new PriorityQueue<>(5, SimulatedProcess::compareTo);
  private final CentralProcessingUnit cpu;
  private int lastAssignedProcessNumber = 0;

  // Prevent direct instantiation.
  private ProcessScheduler(CentralProcessingUnit cpu) {
    this.cpu = cpu;
  }

  /**
   * Create a Process Scheduler with a starting CPU.
   */
  static ProcessScheduler create(CentralProcessingUnit cpu) {
    Preconditions.checkNotNull(cpu);
    return new ProcessScheduler(cpu);
  }

  /**
   * Called when a process is done executing. Instructions: implement a priority based algorithm
   * instead.
   */
  void processDone(SimulatedProcess process) {
    if (processControlBlock.peek() != null) {
      SimulatedProcess nextProcess = processControlBlock.poll();
      cpu.runProcess(this, nextProcess, RUN_CYCLES);
    }
  }

  /**
   * Add a single process.
   */
  synchronized void addProcess(SimulatedProcess process) {
    process.setProcessNumber(++lastAssignedProcessNumber);
    processControlBlock.add(process);

    if (cpu.isIdle()) {
      cpu.runProcess(this, processControlBlock.poll(), RUN_CYCLES);
    }
  }

  /**
   * Removes a process from the PBC.
   */
  void removeProcess(SimulatedProcess p) {
    processControlBlock.remove(p.processNumber());
  }

}
