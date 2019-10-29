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
    int currentRunningProcess = process.processNumber();
    if (processControlBlock.containsKey(currentRunningProcess + 1)) {

      Iterator iter = processControlBlock.keySet().iterator();
      SimulatedProcess PriorityProcess=null;
      while (iter.hasNext()){
        //find out the max priority process in hashmap
        SimulatedProcess nextProcess= (SimulatedProcess) iter.next();
        SimulatedProcess nextProcess1=processControlBlock.get(nextProcess.processNumber()+1);
        if(nextProcess.compareTo(nextProcess1)==1){
          PriorityProcess=nextProcess;
        }
      }
      cpu.runProcess(this, PriorityProcess, RUN_CYCLES);
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
