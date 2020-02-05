/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LidarListener;

public class Lidar extends SubsystemBase {
  private byte[] data; 
  private int distance;
  private int strength;
  private SerialPort.Port port;
  private LidarListener lidarListener;
  private Thread lidarThread;
  private boolean lidarInitialized;

  /**
   * Creates a new Lidar.
   */
  public Lidar() {
    data = new byte[7];
    distance = -10;
    strength = -10;
    port = SerialPort.Port.kMXP;
    try{
      lidarListener = new LidarListener(this, port);
      lidarThread = new Thread(lidarListener);
      lidarThread.start();
      lidarInitialized = true;
    }
    catch (Exception e){
      lidarInitialized = false;
      DriverStation.reportError("LidarProxy could not intialize properly. " + e.getStackTrace().toString(), false);
    }
    SmartDashboard.putBoolean("Lidar/initializedProperly", lidarInitialized);

  }

  public int getDistance(){
    return distance;
  }

  public void senddata(byte[] data){
    this.data = data;
  }

  @Override
  public void periodic() {
    distance = data[1] * 256 + data[0];
    strength = data[3] * 256 + data[2];
    SmartDashboard.putNumber("Distance", distance * .393701);
    SmartDashboard.putNumber("Signal Strength", strength);
;    // This method will be called once per scheduler run
  }
}
