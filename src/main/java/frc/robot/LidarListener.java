/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
/*Thank you to FRC team 5687 on whose code this was based*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort;
import frc.robot.subsystems.Lidar;

/**
 * Add your docs here.
 */
public class LidarListener implements Runnable{
    private Lidar lidar;
    private byte[] frameStart;
    private byte[] data; 
    private SerialPort port;

    public LidarListener(Lidar lidar , SerialPort.Port port){
        this.lidar = lidar;
        frameStart = new byte[2];
        data = new byte[7];
        this.port = new SerialPort(115200 , port);
    }

    public void run(){
        while (true){
            try{            
                frameStart = port.read(2);
                if (frameStart[0] == 89 && frameStart[1] == 89){
                    data = port.read(7);
                    lidar.senddata(data);
                }
            }
            catch(Exception e){
                DriverStation.reportError("LidarListener exception: " + e.toString(), false);
            }
        }
    }
  
}
