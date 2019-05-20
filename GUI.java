import java.awt.Component;
import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class GUI {
	private JFrame frame;
	Sample Sm;
	Learner Boltz;
	int UG;
	private JTextField InputB;
	private JTextField InputH;
	private JTextField InputEpsilon;
	private JTextField Times;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField patient;
	private JTextField diagnostic;
	private JTextField diagnosticAll;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setFrame(new JFrame());
		getFrame().setBounds(100, 100, 450, 510);
		getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getFrame().getContentPane().setLayout(null);
		
		JFileChooser fileChooser = new JFileChooser();
		
		
		JButton btnBrowseCsvFile = new JButton("Browse csv file");
		btnBrowseCsvFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int returnValue=fileChooser.showOpenDialog((Component)e.getSource());
				if (returnValue==JFileChooser.APPROVE_OPTION){
					Sm = teste.read(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		btnBrowseCsvFile.setBounds(139, 11, 131, 29);
		getFrame().getContentPane().add(btnBrowseCsvFile);
		
		JLabel lblB = new JLabel("b:");
		lblB.setBounds(24, 38, 21, 34);
		frame.getContentPane().add(lblB);
		
		InputB = new JTextField();
		InputB.setText("0");
		InputB.setBounds(65, 45, 86, 20);
		frame.getContentPane().add(InputB);
		InputB.setColumns(10);
		
		JLabel lblH = new JLabel("h:");
		lblH.setBounds(24, 83, 46, 14);
		frame.getContentPane().add(lblH);
		
		InputH = new JTextField();
		InputH.setText("5");
		InputH.setBounds(65, 76, 86, 20);
		frame.getContentPane().add(InputH);
		InputH.setColumns(10);
		
		JLabel lblEpsilon = new JLabel("Epsilon:");
		lblEpsilon.setBounds(24, 108, 46, 14);
		frame.getContentPane().add(lblEpsilon);
		
		InputEpsilon = new JTextField();
		InputEpsilon.setText("0.01");
		InputEpsilon.setBounds(88, 105, 86, 20);
		frame.getContentPane().add(InputEpsilon);
		InputEpsilon.setColumns(10);
		
		JRadioButton Unif = new JRadioButton("Unif");
		Unif.setBounds(85, 160, 109, 23);
		buttonGroup.add(Unif);
		Unif.setSelected(true);
		frame.getContentPane().add(Unif);
		
		JRadioButton Gauss = new JRadioButton("Gauss");
		Gauss.setBounds(213, 160, 109, 23);
		buttonGroup.add(Gauss);
		Gauss.setSelected(true);
		frame.getContentPane().add(Gauss);
		
		JLabel lblNewLabel = new JLabel("W:");
		lblNewLabel.setBounds(24, 164, 46, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNumberOfSamples = new JLabel("Number of times to sample:");
		lblNumberOfSamples.setBounds(24, 139, 150, 14);
		frame.getContentPane().add(lblNumberOfSamples);
		
		Times = new JTextField();
		Times.setText("20");
		Times.setBounds(194, 136, 86, 20);
		frame.getContentPane().add(Times);
		Times.setColumns(10);
		
		JButton Start = new JButton("Start!");
		Start.setBounds(128, 190, 165, 51);
		frame.getContentPane().add(Start);
		Start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Unif.isSelected()) {
					UG = 0;
				} else if (Gauss.isSelected()) {
					UG = 1;
				}
				int numbH = Integer.parseInt(InputH.getText());
				int times = Integer.parseInt(Times.getText());
				double typeB = Double.parseDouble(InputB.getText());
				double ep = Double.parseDouble(InputEpsilon.getText());
				
				Boltz = new Learner(Sm, UG, numbH, times, typeB, ep);
				Boltz.engineLoop(times);
			}
		});
		
		JLabel lblPatientToDiagno = new JLabel("Patient to diagnose:");
		lblPatientToDiagno.setBounds(10, 319, 131, 14);
		frame.getContentPane().add(lblPatientToDiagno);
		
		patient = new JTextField();
		patient.setBounds(154, 316, 65, 20);
		frame.getContentPane().add(patient);
		patient.setColumns(10);
		
		
		JLabel label = new JLabel("  ----->");
		label.setBounds(47, 344, 43, 14);
		frame.getContentPane().add(label);
		
		diagnostic = new JTextField();
		diagnostic.setBounds(88, 344, 256, 20);
		frame.getContentPane().add(diagnostic);
		diagnostic.setColumns(10);
		
		JButton btnGo = new JButton("GO");
		btnGo.setBounds(229, 315, 53, 23);
		frame.getContentPane().add(btnGo);
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int numberPatient = Integer.parseInt(patient.getText());
				ArrayList<Integer> patientData = new ArrayList<Integer>();
				patientData = Sm.element(numberPatient);
				patientData.remove(patientData.size() - 1);
				if (Boltz.getGB().classify(patientData) == 1) {
					diagnostic.setText("Ill");
				} else if (Boltz.getGB().classify(patientData) == 0) {
					diagnostic.setText("Healthy");
				}
			}
		});
		
		JLabel lblDiagnoseAllPatients = new JLabel("Diagnose all patients:");
		lblDiagnoseAllPatients.setBounds(10, 392, 131, 14);
		frame.getContentPane().add(lblDiagnoseAllPatients);
		
		JButton btnGo_1 = new JButton("GO");
		btnGo_1.setBounds(139, 388, 89, 23);
		frame.getContentPane().add(btnGo_1);
		btnGo_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double bingo = Boltz.getGB().bingoSample(Sm);
				double notBingo = Sm.length() - bingo;
				double total = bingo + notBingo;
				diagnosticAll.setText("Diagnosed right: " + bingo + " in " + total);
				
			}
		});
		
		JLabel label_1 = new JLabel("  ----->");
		label_1.setBounds(65, 417, 43, 14);
		frame.getContentPane().add(label_1);
		
		diagnosticAll = new JTextField();
		diagnosticAll.setBounds(118, 414, 263, 20);
		frame.getContentPane().add(diagnosticAll);
		diagnosticAll.setColumns(10);
		
		JLabel label_2 = new JLabel("---------------------------------------------------------------");
		label_2.setBounds(84, 262, 282, 14);
		frame.getContentPane().add(label_2);
		
		
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
}