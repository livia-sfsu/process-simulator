package edu.sfsu;
import java.util.*;

import com.google.common.base.Preconditions;

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
  private PriorityQueue<SimulatedProcess> priorityProcess = new PriorityQueue<SimulatedProcess>(Collections.reverseOrder());

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
    /*if (processControlBlock.containsKey(currentRunningProcess + 1)) {
      SimulatedProcess nextProcess = processControlBlock.get(currentRunningProcess + 1);
      cpu.runProcess(this, nextProcess, RUN_CYCLES);
    }*/
    /*while(!priorityProcess.isEmpty()) {
      System.out.println(priorityProcess.poll().priority);
    }*/
    //System.out.println("Peek is " + priorityProcess.peek().priority);
    //cpu.runProcess(this, priorityProcess.peek(), RUN_CYCLES);
    Iterator<SimulatedProcess> itr = priorityProcess.iterator();
    if(itr.hasNext()){
      SimulatedProcess sp = itr.next();
      if(sp.cycles() > 0)
        System.out.printf("-->Resuming process number %d<---\n",sp.processNumber());
      cpu.runProcess(this,sp, RUN_CYCLES - sp.cycles());
      lastprocess = sp;
      itr.remove();
    }
    processControlBlock.put(process.processNumber(),process);



  }

  /**
   * Add a single process.
   */
  private SimulatedProcess lastprocess = null;

  synchronized  void rerunProcess(int cycles){
    cpu.runProcess(this,priorityProcess.peek(), 5);
    priorityProcess.poll();
  }

  synchronized void addProcess(SimulatedProcess process) {
    lastAssignedProcessNumber++;
    process.setProcessNumber(lastAssignedProcessNumber);

    // Priority is ignored in this version.
    //processControlBlock.put(lastAssignedProcessNumber, process);

    System.out.println("****Adding Process Number: " + process.processNumber() + " With priority "+ process.getPriority()+ "****"); // This is where I test priority and to debug.
    priorityProcess.add(process);

    if(lastprocess != null) {
      //System.out.println(process.compareTo(lastprocess));
      //System.out.println(process.getPriority() + "--" + lastprocess.getPriority());
      if (process.compareTo(lastprocess) == 1) {
        System.out.println("Add has interupted");
        cpu.setInterupt(true);
      }
    }


    //Test to see if priority was working

    if (cpu.isIdle()) {
      System.out.println("I AM IDLE -- Will run current process");
      cpu.runProcess(this, process, RUN_CYCLES);
      lastprocess = process;
      //priorityProcess.poll();
    }
  }

  /**
   * Removes a process from the PBC.
   */
  void removeProcess() {
    Iterator<Integer> it = processControlBlock.keySet().iterator();
    while(it.hasNext()){
      int value = it.next();
      System.out.println("****Removing Process Number "+value+" From Process Table.****");
      it.remove();
    }
  }

}
