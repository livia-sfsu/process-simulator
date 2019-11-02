package edu.sfsu;

import com.google.common.base.Preconditions;

import java.util.*;

/**
 * Process Scheduler
 *
 * Instructions: upgrade the current round-robin implementation to a priority based one. This is the
 * most important part of your assignment. Remember that each process has its own priority.
 */




class ProcessScheduler {

  private static final int RUN_CYCLES = 5; // Default number of cycles given to each process on a single run.
  private final Map<Integer, SimulatedProcess> processControlBlock = new HashMap<>();
  private final CentralProcessingUnit cpu;
  private int lastAssignedProcessNumber = 0;
  private Queue<SimulatedProcess> PriorityQueue=new PriorityQueue<SimulatedProcess>(SimulatedProcess::compareTo);

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
    if(!PriorityQueue.isEmpty()){
      cpu.runProcess(this,PriorityQueue.poll(),RUN_CYCLES);

    }
  }


  /**
   * Add a single process.
   */
  synchronized void addProcess(SimulatedProcess process) {
    lastAssignedProcessNumber++;
    process.setProcessNumber(lastAssignedProcessNumber);

    // Priority is ignored in this version.
//    processControlBlock.put(lastAssignedProcessNumber, process);
     PriorityQueue.add(process);
    if (cpu.isIdle()) {
      cpu.runProcess(this,PriorityQueue.poll(), RUN_CYCLES);
    }
  }

  /**
   * Removes a process from the PBC.
   */
  void removeProcess(SimulatedProcess p) {
    PriorityQueue.remove(p.processNumber());
  }

}
