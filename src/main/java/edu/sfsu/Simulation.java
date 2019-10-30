package edu.sfsu;

import static java.lang.Thread.sleep;

/**
 * Executes the Process Management simulation.
 *
 * Instructions: create a
 */
public class Simulation
{
  static void prioritySimulation() throws InterruptedException
  {
    System.out.println("Priority Simulation starting.");
    CentralProcessingUnit cpu = new CentralProcessingUnit();
    ProcessScheduler scheduler = ProcessScheduler.create(cpu);

    // Sleep for 5 seconds to show a few NO-OPs.
    for (int i = 0; i < 5; i++)
    {
      sleep(1000);
    }

    //Start the simulation here.
    SimulatedProcess parentProcess = SimulatedProcess.create(0);
    scheduler.addProcess(parentProcess);
    sleep(2000);

    //First child process will be low priority.
    SimulatedProcess firstChild = SimulatedProcess.create(parentProcess.processNumber(), SimulatedProcess.LOW_PRIORITY);
    scheduler.addProcess(firstChild);
    sleep(2000);

    //Second child process will be high priority.
    SimulatedProcess secondChild = SimulatedProcess.create(parentProcess.processNumber(), SimulatedProcess.HIGH_PRIORITY);
    scheduler.addProcess(secondChild);
    sleep(2000);

    //Third child process will be medium priority.
    SimulatedProcess thirdChild = SimulatedProcess.create(parentProcess.processNumber(), SimulatedProcess.MEDIUM_PRIORITY);
    scheduler.addProcess(thirdChild);
    sleep(2000);

    // Sleep for 30 seconds and let the processes run.
    for (int i = 0; i < 30; i++)
    {
      try
      {
        sleep(1000);
      } catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }

    // Remove them one by one with a delay.
    scheduler.removeProcess(thirdChild);
    sleep(2000);
    scheduler.removeProcess(secondChild);
    sleep(2000);
    scheduler.removeProcess(firstChild);
    sleep(2000);
    scheduler.removeProcess(parentProcess);
    sleep(2000);
  }

  public static void main(String[] args) throws InterruptedException
  {
    //Runs the priority process simulation.
    Simulation.prioritySimulation();
    System.out.println("END OF PROGRAM");
  }
}
