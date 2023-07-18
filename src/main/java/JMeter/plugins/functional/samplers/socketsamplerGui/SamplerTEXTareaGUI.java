package JMeter.plugins.functional.samplers.socketsamplerGui;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

public class SamplerTEXTareaGUI extends JPanel {

	/**
	 * Create the panel.
	 */
	public SamplerTEXTareaGUI() {
		setLayout(null);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(42, 53, 365, 209);
		add(textArea);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(42, 10, 365, 33);
		add(comboBox);

	}
}
