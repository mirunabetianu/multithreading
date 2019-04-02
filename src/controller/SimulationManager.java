package controller;
import model.Queue;
import model.Client;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import view.SimulationFrame;

public class SimulationManager implements Runnable {
    private int maxProcessingTime;
    private int minProcessingTime;
    private int minArrivalTime;
    private int maxArrivalTime;
    private int numberOfQueues;
    private int minWaitingPeriod;
    private AtomicInteger currentTime = new AtomicInteger();
    private int simulationTime;

    private List<Queue> queues = new ArrayList<Queue>(numberOfQueues);
    private List<Client> clients = new ArrayList<Client>(simulationTime);
    private static SimulationFrame frame;

    private float serviceTime_avg = 0;
    private float waitingTime_avg = 0;
    private double peakTime = 0;
    private float emptyTime = 0;

    SimulationManager(int maxProcessingTime, int minProcessingTime, int minArrivalTime, int maxArrivalTime, int numberOfQueues,int simulationTime) {

        this.maxProcessingTime = maxProcessingTime;
        this.minProcessingTime = minProcessingTime;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.numberOfQueues = numberOfQueues;
        this.simulationTime = simulationTime;
        generateNRandomClients();
        for(int i = 0; i < numberOfQueues; i++) {
            int timeLimit = 100;
            queues.add(new Queue(i+1,currentTime, timeLimit,frame));
            new Thread(queues.get(i)).start();
        }
        currentTime.set(0);
        minWaitingPeriod = Integer.MAX_VALUE;
    }

    private void generateNRandomClients()
    {
        int arrivalTime = 0;
        int processingTime;
        for(int i = 0; i < simulationTime; i++) {
            Random rnd = new Random();
            processingTime = minProcessingTime + rnd.nextInt(maxProcessingTime - minProcessingTime);
            arrivalTime += minArrivalTime + rnd.nextInt(maxArrivalTime - minArrivalTime);
            Client client = new Client(arrivalTime,processingTime,i+1);
            clients.add(client);
        }
    }

    public void run() {
        while(!clients.isEmpty()) {
            if (clients.get(0).getArrivalTime() == currentTime.intValue()) {
                minWaitingPeriod = Integer.MAX_VALUE;
                int min = 0;
                for (int i = 0; i < numberOfQueues; i++) {
                    if (queues.get(i).getWaitingPeriod().intValue() < minWaitingPeriod) {
                        min = i;
                        minWaitingPeriod = queues.get(i).getWaitingPeriod().intValue();
                    }
                    if(queues.get(i).getClients().isEmpty()) queues.get(i).setEmptyTime(queues.get(i).getReportsEmpty() + 1);
                }
                queues.get(min).addClient(clients.get(0));
                frame.setFields(queues.get(min).toString(), min);
                clients.remove(0);
            }

            currentTime.incrementAndGet();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        System.out.println("Waiting time");
        for(int i = 0; i < numberOfQueues; i++)
        {
            waitingTime_avg += queues.get(i).getReportWaiting();
            serviceTime_avg += queues.get(i).getReportService();
            emptyTime += queues.get(i).getReportsEmpty();
            if(peakTime < queues.get(i).getMaxWaiting()) peakTime = queues.get(i).getMaxWaiting();
            System.out.println("id:"+ (i+1) + "-> avg: "+df.format(queues.get(i).getReportWaiting()));
        }
        serviceTime_avg/=numberOfQueues;
        waitingTime_avg/=numberOfQueues;
        emptyTime/= numberOfQueues;
        frame.setTextService(df.format(serviceTime_avg) + "s");
        frame.setTextTotal(df.format(waitingTime_avg) + "s");
        frame.setTextEmpty(df.format(emptyTime) + "s");
        frame.setTextPeak(peakTime + "s");
    }
    public static void main(String[] args)
    {
        frame = new SimulationFrame();
        Controller controller = new Controller(frame);
        frame.setVisible(true);
    }
}
