/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Lidar extends SubsystemBase {
  private byte[] data; 
  private int distance;
  private int strength;
  private SerialPort port;

  /**
   * Creates a new Lidar.
   */
  public Lidar() {
    data = new byte[9];
    distance = 0;
    strength = 0;
    port = new SerialPort(115200 , SerialPort.Port.kOnboard);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
