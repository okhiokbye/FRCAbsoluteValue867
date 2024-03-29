// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.button.CommandJoystick;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.*;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;


public class Robot extends TimedRobot {

  private final CommandJoystick m_controller = new CommandJoystick(0);
  private final Drivetrain m_swerve = new Drivetrain();

  // Slew rate limiters to make joystick inputs more gentle; 1/3 sec from 0 to 1.
  private final SlewRateLimiter m_xspeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_yspeedLimiter = new SlewRateLimiter(3);
  private final SlewRateLimiter m_rotLimiter = new SlewRateLimiter(3);
  private final AHRS m_gyro = new AHRS(SPI.Port.kMXP);

  @Override
  public void robotInit(){
    m_gyro.reset();
  }
  @Override
  public void teleopInit() {
    m_gyro.reset();
  }
  @Override
  public void autonomousPeriodic() {
    driveWithJoystick(false);
    m_swerve.updateOdometry();
  }

  @Override
public void teleopPeriodic() {
    driveWithJoystick(true);
  }

  private void driveWithJoystick(boolean fieldRelative) {
    // Get the x speed. We are inverting this because Xbox controllers return
    // negative values when we push forward.
    final var xSpeed = 0.11;// m_xspeedLimiter.calculate(MathUtil.applyDeadband(m_controller.getX(), 0.2))
         // * Drivetrain.kMaxSpeed;

    // Get the y speed or sideways/strafe speed. We are inverting this because
    // we want a positive value when we pull to the left. Xbox controllers
    // return positive values when you pull to the right by default.
    final var ySpeed = 0;//m_yspeedLimiter.calculate(MathUtil.applyDeadband(m_controller.getY(), 0.2))
          //* Drivetrain.kMaxSpeed;

            

    // Get the rate of angular rotation. We are inverting this because we want a
    // positive value when we pull to the left (remember, CCW is positive in
    // mathematics). Xbox controllers return positive values when you pull to
    // the right by default.
    final var rot = 0.1;// -m_rotLimiter.calculate(MathUtil.applyDeadband(m_controller.getZ(), 0.2))
         //* Drivetrain.kMaxAngularSpeed;
           //-m_rotLimiter.calculate(MathUtil.applyDeadband(m_controller.getTwist(), 0.2))
           //* Drivetrain.kMaxAngularSpeed;

           // talon.set(ControlMode.PercentOutput, 50); jeffrey what is this

       // System.out.println(rot + " " + ySpeed + " " + xSpeed);
    m_swerve.drive(xSpeed, ySpeed, rot, fieldRelative);
  }
}
