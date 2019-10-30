package edu.sfsu;

import com.google.common.base.Preconditions;

import java.util.*;

/**
 * Process Scheduler
 * <p>
 * Instructions: upgrade the current round-robin implementation to a priority based one. This is the
 * most important part of your assignment. Remember that each process has its own priority.
 */
class ProcessScheduler
{
    private static final int RUN_CYCLES = 5; // Default number of cycles given to each process on a single run.

    //Create a PriorityQueue that uses SimulatedProcess's compareTo function to sort the processes
    // from highest to lowest.
    PriorityQueue<SimulatedProcess> priorityControlBlock = new PriorityQueue<>(SimulatedProcess::compareTo);

    private final CentralProcessingUnit cpu;
    private int lastAssignedProcessNumber = 0;

    // Prevent direct instantiation.
    private ProcessScheduler(CentralProcessingUnit cpu) {
        this.cpu = cpu;
    }

    /**
     * Create a Process Scheduler with a starting CPU.
     */
    static ProcessScheduler create(CentralProcessingUnit cpu)
    {
        Preconditions.checkNotNull(cpu);
        return new ProcessScheduler(cpu);
    }

    /**
     * Called when a process is done executing. Instructions: implement a priority based algorithm
     * instead.
     */
    void processDone(SimulatedProcess process)
    {
        //If the priorityControlBlock is not empty, grab the next process in it and send to the CPU to run it.
        if(!priorityControlBlock.isEmpty())
        {
            SimulatedProcess nextProcess = priorityControlBlock.poll();
            cpu.runProcess(this, nextProcess, RUN_CYCLES);
        }
    }

    /**
     * Add a single process.
     */
    synchronized void addProcess(SimulatedProcess process)
    {
        lastAssignedProcessNumber++;
        process.setProcessNumber(lastAssignedProcessNumber);

        //Add the process to the priorityControlBlock which will then use the compareTo function to sort their priorities
        // from highest to lowest priority.
        System.out.println("Adding Process #" + process.processNumber() + ", Priority #" + process.getPriority() + "...");
        priorityControlBlock.add(process);

        if (cpu.isIdle())
        {
            System.out.println("CPU currently idling...");
            cpu.runProcess(this, process, RUN_CYCLES);
            System.out.println("CPU currently running...");
        }
    }

    /**
     * Removes a process from the PBC.
     */
    void removeProcess(SimulatedProcess p) {
        priorityControlBlock.remove(p.processNumber());
    }

}
