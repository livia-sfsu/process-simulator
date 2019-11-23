package edu.sfsu;

import com.google.common.base.Preconditions;
//import com.google.common.collect.MinMaxPriorityQueue;

//import java.util.HashMap;
//import java.util.Map;

import java.util.PriorityQueue;

/**
 * Process Scheduler
 *
 * Instructions: upgrade the current round-robin implementation to a priority based one. This is the
 * most important part of your assignment. Remember that each process has its own priority.
 */
class ProcessScheduler {

  private static final int RUN_CYCLES = 5; // Default number of cycles given to each process on a single run.
  private final PriorityQueue<SimulatedProcess> processControlBlock = new PriorityQueue<>();
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
    /*TODO: replace this ROUND-ROBIN SOLUTION.*/
    if (processControlBlock.peek() != null) { //peek is head element
      SimulatedProcess nextProcess = processControlBlock.poll(); // poll removes head and appends to nextProcess

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

