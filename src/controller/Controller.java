package controller;

import view.SimulationFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 class Controller {
    private SimulationFrame simulationFrame;

     Controller(SimulationFrame simulationFrame)
    {
        this.simulationFrame = simulationFrame;
        simulationFrame.addButtonListener(new addButtonListener());
    }

    public class addButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            try {
                SimulationManager simulationManager = new SimulationManager(
                        simulationFrame.getMaxServiceTime(), simulationFrame.getMinServiceTime(), simulationFrame.getMinArrivalTime(), simulationFrame.getMaxArrivalTime(), simulationFrame.getNoOfQueues(), simulationFrame.getSimulationTime()
                );
                Thread t = new Thread(simulationManager);
                t.start();
                simulationFrame.mainFrame.dispose();
                simulationFrame.newFrame();
            }catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(null,"Please introduce all the data","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
