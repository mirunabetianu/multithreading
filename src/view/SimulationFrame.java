package view;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;

public class SimulationFrame extends JFrame {

    private static final long serialVersionUID=1L;
    public JFrame mainFrame;
    private JTextField textMinArrival = new JTextField(2);
    private JTextField textMaxArrival = new JTextField(2);
    private JTextField textMinService = new JTextField(2);
    private JTextField textMaxService = new JTextField(2);
    private JTextField textNoOfQueues = new JTextField(2);
    private JTextField textSimulation = new JTextField(2);
    private JButton buttonStart = new JButton("Start");
    private JTextField[] fields;
    private JTextArea textArea = new JTextArea();
    private JTextField textService = new JTextField(4);
    private JTextField textTotal = new JTextField(4);
    private JTextField textPeak = new JTextField(4);
    private JTextField textEmpty = new JTextField(4);

    public SimulationFrame()
    {
        mainFrame = new JFrame();
        JPanel p1 = new JPanel();
        p1.add(new JLabel("Arrival time interval"));
        JPanel p2 = new JPanel();
        p2.add(new JLabel("Min"));
        p2.add(textMinArrival);
        p2.add(new JLabel("Max"));
        p2.add(textMaxArrival);
        JPanel p3 = new JPanel();
        p3.setLayout(new BoxLayout(p3,BoxLayout.Y_AXIS));
        p3.add(p1);
        p3.add(p2);

        JPanel p4 = new JPanel();
        p4.add(new JLabel("Service time interval"));
        JPanel p5 = new JPanel();
        p5.add(new JLabel("Min"));
        p5.add(textMinService);
        p5.add(new JLabel("Max"));
        p5.add(textMaxService);
        JPanel p6 = new JPanel();
        p6.setLayout(new BoxLayout(p6,BoxLayout.Y_AXIS));
        p6.add(p4);
        p6.add(p5);

        JPanel p7 = new JPanel();
        p7.add(new JLabel("Number of queues  "));
        p7.add(textNoOfQueues);

        JPanel p8 = new JPanel();
        p8.add(new JLabel("Simulation time      "));
        p8.add(textSimulation);

        JPanel p9 = new JPanel();
        buttonStart.setSize(20,15);
        p9.add(buttonStart);

        JPanel aux_panel = new JPanel();
        aux_panel.setLayout(new BoxLayout(aux_panel,BoxLayout.Y_AXIS));

        JPanel p10 = new JPanel();
        p10.add(p3);
        p10.add(p6);

        aux_panel.add(p10);
        aux_panel.add(p7);
        aux_panel.add(p8);
        aux_panel.add(p9);

        mainFrame.add(aux_panel);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

    }
    public void newFrame()
    {
        mainFrame.setVisible(false);
        JFrame second_frame = new JFrame();
        JPanel aux_panel = new JPanel();
        aux_panel.setLayout(new BoxLayout(aux_panel,BoxLayout.Y_AXIS));
        fields = new JTextField[getNoOfQueues()];

        for(int i = 0; i < getNoOfQueues(); i++)
        {
            fields[i] = new JTextField(40);
            fields[i].setEditable(false);
            JPanel p = new JPanel();
            p.add(new JLabel("Queue "+ (i+1) + ":"));
            fields[i].setText("Unused");
            p.add(fields[i]);
            aux_panel.add(p);
        }
        JLabel label = new JLabel("Logs");
        label.setHorizontalAlignment(SwingConstants.LEFT);
        Font font = new Font("TimesRoman", Font.PLAIN,14);
        aux_panel.add(label);
        textArea.setEditable(false);
        textArea.setRows(15);
        textArea.setText(null);
        textArea.setFont(font);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        DefaultCaret caret = (DefaultCaret)textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        aux_panel.add(scroll);

        //average

        JPanel p_avg = new JPanel();
        p_avg.add(new JLabel("Simulation results:"));
        JPanel p_w1 = new JPanel();
        p_w1.add(new JLabel("Average service time:"));
        textService.setEditable(false);
        textService.setText("... s");
        p_w1.add(textService);

        JPanel p_wt = new JPanel();
        p_wt.add(new JLabel("Average waiting time:"));
        textTotal.setText("... s");
        textTotal.setEditable(false);
        p_wt.add(textTotal);

        JPanel p_we = new JPanel();
        p_we.add(new JLabel("Average empty queue time:"));
        textEmpty.setText("... s");
        textEmpty.setEditable(false);
        p_we.add(textEmpty);

        JPanel p_wp = new JPanel();
        p_wp.add(new JLabel("Peak time:"));
        textPeak.setText("... s");
        textPeak.setEditable(false);
        p_wp.add(textPeak);

        aux_panel.add(p_avg);
        aux_panel.add(p_w1);
        aux_panel.add(p_wt);
        aux_panel.add(p_we);
        aux_panel.add(p_wp);
        second_frame.add(aux_panel);
        second_frame.pack();
        second_frame.setVisible(true);
        second_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        second_frame.setLocationRelativeTo(null);

    }

    public Integer getNoOfQueues(){
        String text = textNoOfQueues.getText();
        return Integer.parseInt(text);
    }
    public Integer getMinServiceTime()
    {
        String text = textMinService.getText();
        return Integer.parseInt(text);
    }
    public Integer getMaxServiceTime()
    {
        String text = textMaxService.getText();
        return Integer.parseInt(text);
    }
    public Integer getMinArrivalTime()
    {
        String text = textMinArrival.getText();
        return Integer.parseInt(text);
    }
    public Integer getMaxArrivalTime()
    {
        String text = textMaxArrival.getText();
        return Integer.parseInt(text);
    }
    public Integer getSimulationTime()
    {
        String text = textSimulation.getText();
        return Integer.parseInt(text);
    }
    public void addButtonListener(ActionListener a){buttonStart.addActionListener(a);}

    public void setTextArea(String s)
    {
        String text = textArea.getText();
        if(text != null) text = text + "\n" + s;
        else text = s;
        textArea.setText(text);
    }

    public void setFields(String s, int i)
    {
        fields[i].setText(s);
    }

    public void setTextService(String s)
    {
        textService.setText(s);
    }

    public void setTextTotal(String s)
    {
        textTotal.setText(s);
    }

    public void setTextPeak(String s)
    {
        textPeak.setText(s);
    }

    public void setTextEmpty(String s)
    {
        textEmpty.setText(s);
    }

}
