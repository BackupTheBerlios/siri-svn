package org.iris.client.swing.util.statuspanel;

import java.awt.*;
import javax.swing.*;

public class MyJProgressBar
    extends JProgressBar implements Runnable
{
    long myTimeDelayForMovingProgressBarToMax;
    boolean myMoveUpAndDown = true;
    private Thread myRunner;
    private boolean myIsRunningThread = true;
    private static int counter = 0;

    public MyJProgressBar()
    {
    }

    public MyJProgressBar(long theTimeDelayForMovingProgressBarToMax, boolean newMoveUpAndDown)
    {
        this.setMinimum(0);
        this.setMaximum(100);
        this.setForeground(SystemColor.inactiveCaption);
        myTimeDelayForMovingProgressBarToMax = theTimeDelayForMovingProgressBarToMax;
        myMoveUpAndDown = newMoveUpAndDown;

    }

    public void start()
    {
        myIsRunningThread = true;

        //
        // first time the thread is null..
        if (myRunner == null)
        {
            myRunner = new Thread(this);
            counter++;
            myRunner.setName("ThreadProgressBar" + counter);
            myRunner.start();
        }
        else if (!myRunner.isAlive()) // we now have a thread running
        { // but we don't want to start a new thread if the current thread is not dead
            myRunner = new Thread(this);
            counter++;
            myRunner.setName("ThreadProgressBar" + counter);
            myRunner.start();

        }
    }

    public synchronized void stop()
    {
        myIsRunningThread = false;
        this.setMinimum(this.getMinimum());
    }

    public static void main(String[] args)
    {
//        testProgressBarMovingUpAndDown();
        testProgressBarMovingUpOnce();

    }

    public void setToMin()
    {
        this.setMinimum(this.getMinimum());

        //myIsRunningThread = false;
    }

    public void setToMax()
    {
        //myIsRunningThread = false;
        this.setMaximum(this.getMaximum());
    }

    private void setProgressBarMovingUpOnce()
    {
        int i = 0;
        while (myIsRunningThread)
        {
            try
            {
                // first sleep and then move progressbar
                Thread.sleep(myTimeDelayForMovingProgressBarToMax);
                i++;
                this.setValue(i);

                // if we passed the max value exit
                if (this.getValue() > this.getMaximum())
                {
                    myIsRunningThread = false;
                    break;
                }
            }
            catch (Throwable thr)
            {
                thr.printStackTrace();
            }

        }

        // move to min when finished
        super.setValue(0);
    }

    public void run()
    {
        if (!myMoveUpAndDown)
        {
            setProgressBarMovingUpOnce();
        }
        else
        {
            this.setProgressBarMovingUpAndDown();
        }

    }

    private void setProgressBarMovingUpAndDown()
    {
        int i = 0;
        boolean isMaxReached = false;
        while (myIsRunningThread)
        {
            try
            {
                Thread.sleep(myTimeDelayForMovingProgressBarToMax);
                if (!isMaxReached)
                {
                    isMaxReached = false;
                    i++;
                    this.setValue(i);
                    if (i > this.getMaximum())
                    {
                        isMaxReached = true;
                        switchBackForeGroundColors();
                    }
                }
                else if (isMaxReached)
                {
                    isMaxReached = true;
                    i--;
                    this.setValue(i);
                    if (i < this.getMinimum())
                    {
                        isMaxReached = false;
                        switchBackForeGroundColors();
                    }
                }
            }
            catch (Throwable thr)
            {
                thr.printStackTrace();
            }

        }
    }

    private void switchBackForeGroundColors()
    {
        Color tmp = this.getBackground();
        this.setBackground(this.getForeground());
        this.setForeground(tmp);
    }

    private static void testProgressBarMovingUpAndDown()
    {
        MyJProgressBar aBar = new MyJProgressBar(10, true);
        aBar.setMinimum(0);
        aBar.setMaximum(100);
        aBar.setBackground(SystemColor.inactiveCaption);
        aBar.setForeground(SystemColor.activeCaption);
        aBar.setPreferredSize(new Dimension(180, 10));
        aBar.start();

        JFrame dlg = new JFrame("testProgressBarMovingUpAndDown");
        dlg.getContentPane().setLayout(new FlowLayout());
        dlg.getContentPane().setBackground(SystemColor.yellow);
        dlg.getContentPane().setBackground(SystemColor.green);
        JPanel aPanel = new JPanel();
        aPanel.add(aBar);
        dlg.getContentPane().add(aPanel);
        dlg.pack();
        dlg.setVisible(true);
        try
        {
            Thread.sleep(5000);
        }
        catch (Exception ex)
        {

        }

        aBar.stop();

        try
        {
            Thread.sleep(5000);
        }
        catch (Exception ex)
        {

        }

        aBar.start();
    }

    private static void testProgressBarMovingUpOnce()
    {
        MyJProgressBar aBar = new MyJProgressBar(10, false);
        //aBar.setMinimum( 0 );
        //aBar.setMaximum( 100 );
        //aBar.setForeground( SystemColor.inactiveCaption );
        aBar.setPreferredSize(new Dimension(100, 10));
        aBar.start();

        JFrame dlg = new JFrame("testProgressBarMovingUpAndDown");
        dlg.getContentPane().setLayout(new FlowLayout());
        dlg.getContentPane().setBackground(SystemColor.yellow);
        dlg.getContentPane().setBackground(SystemColor.green);
        JPanel aPanel = new JPanel();
        aPanel.setBackground(SystemColor.activeCaption);
        aPanel.add(aBar);
        dlg.getContentPane().add(aPanel);
        dlg.pack();
        dlg.setVisible(true);

        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {

        }

        aBar.stop();

        try
        {
            Thread.sleep(1000);
        }
        catch (Exception ex)
        {

        }

        for (int i = 0; i < 100; i++)
        {
            aBar.start();

            try
            {
                Thread.sleep(1000);
            }
            catch (Exception ex)
            {

            }

            aBar.stop();
            try
            {
                Thread.sleep(3000);
            }
            catch (Exception ex)
            {

            }

        }
    }
}