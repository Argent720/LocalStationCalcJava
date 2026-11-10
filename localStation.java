import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;


public class localStation {
    
   private JFrame window;
   private JButton calculate;
   private JLabel temperature;
   private JLabel mercury;
   private JTextField tempInput;
   private JTextField mercInput;
   private JTextArea output;
   private JPanel menu;
   private JPanel leftSide;
   private JPanel rightSide;
   private JPanel bottomSide;

   private int width;
   private int height;

   public localStation(int w, int h) {
    window = new JFrame();
    menu = new JPanel(new FlowLayout());
    leftSide = new JPanel();
    rightSide = new JPanel();
    bottomSide = new JPanel();
    temperature = new JLabel("Local Temperature (\u00B0C)");
    mercury = new JLabel("Mercury Height (mm)");
    tempInput = new JTextField(6);
    mercInput = new JTextField(6);
    output = new JTextArea();
    calculate = new JButton("Calculate");


    width = w;
    height = h;
   }

   public void setUpGUI() {
    Container con = window.getContentPane();
    BorderLayout bord = new BorderLayout();
    con.setLayout(bord);
    window.setSize(width, height);
    window.setTitle("MEAP II - Barometric Local Station Calculator");
    menu.add(temperature);
    menu.add(tempInput);
    menu.add(mercury);
    menu.add(mercInput);
    menu.add(calculate);
    leftSide.setPreferredSize(new Dimension(25, 100));
    rightSide.setPreferredSize(new Dimension(25, 100));
    bottomSide.setPreferredSize(new Dimension(100, 25));
    con.add(menu, BorderLayout.NORTH);
    con.add(output, BorderLayout.CENTER);
    con.add(leftSide, BorderLayout.WEST);
    con.add(rightSide, BorderLayout.EAST);
    con.add(bottomSide, BorderLayout.SOUTH);
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setLocationRelativeTo(null);
    window.setVisible(true);
   }


   public void setUpButtonListeners() {
    ActionListener calcListener = new ActionListener() {
        public void actionPerformed(ActionEvent ae) {
            String strTemp = tempInput.getText();
            String strMerc = mercInput.getText();

            double localTemp = Double.valueOf(strTemp);
            double mercValue = Double.valueOf(strMerc);

            localStationCalc lsc = new localStationCalc();
            lsc.localStationCalc(localTemp, mercValue);

            output.setText(null);

            if (localTemp >= 21 && localTemp <= 22) {
                output.append("Room Temperature: " + localTemp + "\u00B0C \u00B1 0.5\u00B0C" + "\n");
                output.append("Observed Mercury Height: " + mercValue + "mmHg \u00B1 0.05 mmHg" + "\n");
                output.append("\n");
                output.append("Temperature Correction: " + String.format("%.5f",lsc.Tcorrection) + "mmHg" + "\n");
                output.append("Latitude Correction: " + String.format("%.5f",lsc.latCorrection) + "mmHg" + "\n");
                output.append("\n");
                output.append("Local Station Pressure: " + String.format("%.3f",lsc.localPressure) + "mmHg \u00B1" + String.format("%.5f",lsc.localPressure_UNC) + "mmHg" + "\n");
                output.append("Local Station Pressure: " + String.format("%.3f",lsc.localPressurekPa) + "kPa \u00B1" + String.format("%.5f",lsc.localPressurekPa_UNC) + "kPa" + "\n");
                output.append("\n");
                output.append("Barometric Pressure: " + String.format("%.3f",lsc.localPressurekPa) + "kPa \u00B1" + String.format("%.5f",lsc.localPressurekPa_UNC) + "kPa" + "\n");
                output.append("Room Temperature: " + localTemp + "\u00B0C \u00B1 0.5\u00B0C" + "\n");
                output.append("Air Density: " + String.format("%.4f",lsc.airDensity) + "kg/m^3 \u00B1" + String.format("%.5f",lsc.airDensity_UNC) + "kg/m^3" + "\n");
                output.append("Air Density Relative Uncertainty: " + String.format("%.5f", lsc.airDensity_RU) + "%");
            }
            else {
                output.append("Calculation Error: Invalid Temperature");
            }
        }
    };

    calculate.addActionListener(calcListener);
   }
}
