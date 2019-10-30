package edu.sfsu;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

/**
 * Simulates a process with a priority.
 */
public class SimulatedProcess implements Comparable<SimulatedProcess> {

  // Constants used to define standard priorities.
  static final int LOW_PRIORITY = 10;
  static final int NORMAL_PRIORITY = 50;
  static final int HIGH_PRIORITY = 100;

  private int processNumber = 0;
  private int parentProcessNumber = 0;
  private int priority = NORMAL_PRIORITY;
  private int cycles = 0; // The number of cycles this process has run.

  // Prevent direct instantiation, use create instead.
  private SimulatedProcess() {
  }

  /**
   * Create a new process with a default priority.
   *
   * @param parent the parent process number.
   */
  static SimulatedProcess create(int parent) {
    return create(parent, NORMAL_PRIORITY);
  }

  /**
   * Create a new process with a default priority.
   *
   * @param parent the parent process number.
   * @param priority the priority, [1-100].
   */
  static SimulatedProcess create(int parent, int priority) {
    SimulatedProcess result = new SimulatedProcess();
    result.parentProcessNumber = parent;
    result.priority = priority;
    return result;
  }

  /**
   * Simulates a single cycle given to this process to run.
   *
   * Instead of actually running, this simulation prints a small message.
   */
  void tick() {
    cycles++;
    System.out.print(
        String.format(
            "Process %d, Parent %d, Cycle %d, Priority %d\n", processNumber, parentProcessNumber, cycles, priority));
  }

  /**
   * Allows the Process Scheduler to set the process number.
   */
  void setProcessNumber(int number) {
    Preconditions.checkArgument(processNumber == 0,
        "Cannot change the process number once it's been set.");
    processNumber = number;
  }

  int processNumber() {
    return processNumber;
  }

  int parentNumber() {
    return parentProcessNumber;
  }

  int cycles() {
    return cycles;
  }

  int getPriority()
  {
      return priority;
  }

  /**
   * One process is equal to another when they have the same process number.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final SimulatedProcess s = (SimulatedProcess) obj;
    return processNumber == s.processNumber;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(processNumber);
  }

  /**
   * Helps adding SimulatedProcess to collections. This will help you to decide which process has a
   * higher priority than another.
   */
  @Override
  public int compareTo(SimulatedProcess o) {
    return Integer.compare(o.priority, priority);
  } //switched o.priority and priority to sort highest priority comes first
}
