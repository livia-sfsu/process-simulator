package edu.sfsu;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Process Scheduler
 *
 * Instructions: upgrade the current round-robin implementation to a priority based one. This is the
 * most important part of your assignment. Remember that each process has its own priority.
 */
class ProcessScheduler {

  class ProcessControlBlock{
    ArrayList<SimulatedProcess> processes = new ArrayList<>();
    void sortProcesses(){
      Collections.sort(processes, Collections.reverseOrder());
    }
    boolean checkQueue(){
      return !processes.isEmpty();
    }
    void addProcess(SimulatedProcess p){
      processes.add(p);
    }

    void removeProcess(SimulatedProcess p){
      processes.remove(p);
    }

    SimulatedProcess getNextProcess(){
      return processes.get(0);
    }
  }

  private static final int RUN_CYCLES = 5; // Default number of cycles given to each process on a single run.
  private final CentralProcessingUnit cpu;
  private int lastAssignedProcessNumber = 0;
  private final ProcessControlBlock pcb = new ProcessControlBlock();

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
    pcb.removeProcess(process);
    if(pcb.checkQueue())
      cpu.runProcess(this,pcb.getNextProcess(),RUN_CYCLES);
  }

  /**
   * Add a single process.
   */
  synchronized void addProcess(SimulatedProcess process) {
    lastAssignedProcessNumber++;
    process.setProcessNumber(lastAssignedProcessNumber);

    // Priority is ignored in this version.
    pcb.addProcess(process);
    pcb.sortProcesses();
    if (cpu.isIdle()) {
      cpu.runProcess(this, process, RUN_CYCLES);
    }
  }

  /**
   * Removes a process from the PBC.
   */
  void removeProcess(SimulatedProcess p) {
    pcb.removeProcess(p);
  }

}
