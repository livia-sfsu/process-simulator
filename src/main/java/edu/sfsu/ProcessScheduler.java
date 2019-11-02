package edu.sfsu;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Process Scheduler
 *
 * Instructions: upgrade the current round-robin implementation to a priority based one. This is the
 * most important part of your assignment. Remember that each process has its own priority.
 */
class ProcessScheduler {

  private static final int RUN_CYCLES = 5; // Default number of cycles given to each process on a single run.
  private final Map<Integer, SimulatedProcess> processControlBlock = new HashMap<>();
  private final PriorityQueue<SimulatedProcess> processPriorityQueue = new PriorityQueue<>(SimulatedProcess::compareTo);
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
    // TODO: replace this ROUND-ROBIN SOLUTION.
    /*int currentRunningProcess = process.processNumber();
    if (processControlBlock.containsKey(currentRunningProcess + 1)) {
      SimulatedProcess nextProcess = processControlBlock.get(currentRunningProcess + 1);
      cpu.runProcess(this, nextProcess, RUN_CYCLES);
    }*/
    if(!processPriorityQueue.isEmpty()) {
      SimulatedProcess nextProcess = processPriorityQueue.poll();
      cpu.runProcess(this, nextProcess, RUN_CYCLES);
    }
  }

  /**
   * Add a single process.
   */
  synchronized void addProcess(SimulatedProcess process) {
    lastAssignedProcessNumber++;
    process.setProcessNumber(lastAssignedProcessNumber);

    // Priority is ignored in this version.
    processControlBlock.put(lastAssignedProcessNumber, process);
    if(process.processNumber() != 1) {
      processPriorityQueue.add(process);
    }
    if (cpu.isIdle()) {
      cpu.runProcess(this, process, RUN_CYCLES);
    }
  }

  /**
   * Removes a process from the PBC.
   */
  void removeProcess(SimulatedProcess p) {
    processControlBlock.remove(p.processNumber());
  }

}
