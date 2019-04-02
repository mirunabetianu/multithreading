package model;


public class Client implements Comparable<Client> {
    private int id;
    private int arrivalTime;
    private int processingTime;
    private int finishTime;

    public Client(int arrivalTime, int processingTime, int id) {
        this.arrivalTime = arrivalTime;
        this.processingTime = processingTime;
        this.id = id;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    int getFinishTime() {
        return finishTime;
    }

    int getId() {
        return id;
    }

    public int compareTo(Client o) {
        return this.getArrivalTime() - o.getArrivalTime();

    }

    void setFinishTime(int finishTime) {
        this.finishTime = finishTime;
    }
}
