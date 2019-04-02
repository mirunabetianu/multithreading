package model;
import view.SimulationFrame;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue implements Runnable{

    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod = new AtomicInteger();
    private int id;
    private AtomicInteger currentTime;
    private int timeLimit;
    private SimulationFrame frame;
    private int[] rmCount = {0,0,0,0,0};
    private double[] rmWaiting = {0,0,0,0,0};
    private int[] rmCountS = {0,0,0,0,0};
    private double[] rmService = {0,0,0,0,0};
    private double maxWaiting = 0;
    private int emptyTime;


    public Queue(int id, AtomicInteger currentTime, int timeLimit, SimulationFrame frame)
    {
        clients = new ArrayBlockingQueue<Client>(100);
        waitingPeriod.set(0);
        this.id = id;
        this.currentTime = currentTime;
        this.timeLimit = timeLimit;
        this.frame = frame;
        emptyTime = 0;
    }

    public void addClient(Client newClient)
    {
        clients.add(newClient);
        waitingPeriod.addAndGet(newClient.getProcessingTime());

        if(rmWaiting[this.getId()-1] > maxWaiting) maxWaiting = rmWaiting[this.getId()-1];
        rmWaiting[this.getId()-1] += this.waitingPeriod.intValue();
        rmCount[this.getId()-1] ++;

        rmService[this.getId()-1] += newClient.getProcessingTime();
        rmCountS[this.getId()-1] ++;

       // System.out.println("Client " + newClient.getId() + " enters the q" + this.getId() + " waiting " +waitingPeriod.intValue() + " proc " + newClient.getProcessingTime());
        frame.setFields(this.toString(),this.id -1);
        frame.setTextArea("Client " + newClient.getId() + " enters the queue number " + this.getId() + " having the arrival time of " + newClient.getArrivalTime() + "s" + " and a service time of " + newClient.getProcessingTime() + "s" );
        //  frame.setTextArea("Waiting time for client " + newClient.getId() + " is " + waitingPeriod.intValue() +"s");
    }

    private void removeClient()
    {
        waitingPeriod.addAndGet(-clients.element().getProcessingTime());
       // System.out.println("Client " + clients.element().getId() + " leaves the q" +this.getId() + " waiting " +waitingPeriod.intValue() + " proc " + clients.element().getProcessingTime());
        frame.setFields(this.toString(),this.id-1 );
        clients.element().setFinishTime(clients.element().getProcessingTime() + clients.element().getArrivalTime() + waitingPeriod.intValue());
        frame.setTextArea("Client " + clients.element().getId() + " leaves the queue number " +this.getId() + " after " + clients.element().getFinishTime()+ "s");
        clients.remove();
    }

    public void run()
    {
        while(true) {
            if (clients.size() > 0) {
                Client t = clients.element();
                try {
                    Thread.sleep(t.getProcessingTime() * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                removeClient();
                frame.setFields(this.toString(),this.id-1 );
            }
            else if(currentTime.intValue() > timeLimit) break;
        }
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    private int getId() {
        return id;
    }

    public String toString()
    {
        String queue="";
        if(!clients.isEmpty())
        {
            for(Client c: clients)
            {
                queue = queue + c.getId() + " ";
            }
        }
        else queue = "Empty";
        return queue;
    }

    public BlockingQueue<Client> getClients() {
        return clients;
    }

    public double getReportWaiting()
    {
        return this.rmWaiting[this.getId()-1]/this.rmCount[this.getId()-1];
    }

    public double getMaxWaiting() {
        return maxWaiting;
    }

    public double getReportService()
    {
        return this.rmService[this.getId()-1]/this.rmCountS[this.getId()-1];
    }

    public void setEmptyTime(int emptyTime) {
        this.emptyTime = emptyTime;
    }

    public int getReportsEmpty()
    {
        return this.emptyTime;
    }
}
