package bc.diffusion.main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * This class is used for input components of the simulation, like changing the diffusion rates of U and V
 * chemicals and the feed/kill rates of the equations for the chemicals.
 * 
 * @author Brennan Collins
 *
 */
public class InputPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Grids grids;
	private JTextField uRate, vRate, f, k;

	public InputPanel(Grids grids) {
		this.grids = grids;

		add(createButtons());
		add(createTextFields());
	}

	public JComponent createButtons() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
		JButton button = new JButton("Start");
		button.setBackground(new Color(40, 185, 185));
		button.addActionListener(this);
		button.setActionCommand("start");

		panel.add(button);

		button = new JButton("Pause");
		button.setBackground(new Color(40, 185, 185));
		button.addActionListener(this);
		button.setActionCommand("pause");

		panel.add(button);

		button = new JButton("Random");
		button.setBackground(new Color(40, 185, 185));
		button.addActionListener(this);
		button.setActionCommand("random");

		panel.add(button);

		button = new JButton("Toggle");
		button.setBackground(new Color(40, 185, 185));
		button.addActionListener(this);
		button.setActionCommand("toggle");

		panel.add(button);
		panel.setBackground(new Color(40, 185, 185));

		return panel;
	}

	public JComponent createTextFields() {
		JPanel panel = new JPanel();

		uRate = new JTextField();
		uRate.setColumns(5);
		uRate.setText(Double.toString(Driver.ru));
		JLabel uLabel = new JLabel("Ru ", JLabel.TRAILING);
		uLabel.setLabelFor(uRate);
		uRate.addActionListener(this);

		vRate = new JTextField();
		vRate.setColumns(5);
		vRate.setText(Double.toString(Driver.rv));
		JLabel vLabel = new JLabel("Rv ", JLabel.TRAILING);
		uLabel.setLabelFor(vRate);
		vRate.addActionListener(this);

		f = new JTextField();
		f.setColumns(5);
		f.setText(Double.toString(Driver.f));
		JLabel fLabel = new JLabel("f ", JLabel.TRAILING);
		fLabel.setLabelFor(f);
		f.addActionListener(this);

		k = new JTextField();
		k.setColumns(5);
		k.setText(Double.toString(Driver.k));
		JLabel kLabel = new JLabel("k ", JLabel.TRAILING);
		kLabel.setLabelFor(k);
		k.addActionListener(this);

		panel.add(uLabel);
		panel.add(uRate);
		panel.add(vLabel);
		panel.add(vRate);
		panel.add(fLabel);
		panel.add(f);
		panel.add(kLabel);
		panel.add(k);

		panel.setBackground(new Color(235, 235, 235));

		return panel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if ("random".equals(e.getActionCommand())) {
			Driver.random = !Driver.random;
			
			Driver.ru = Double.parseDouble(uRate.getText());
			uRate.setText(Double.toString(Driver.ru));
			
			Driver.rv = Double.parseDouble(vRate.getText());
			vRate.setText(Double.toString(Driver.rv));
			
			Driver.f = Double.parseDouble(f.getText());
			f.setText(Double.toString(Driver.f));
			
			Driver.k = Double.parseDouble(k.getText());
			k.setText(Double.toString(Driver.k));
			
			grids.reset();
		
		} else if ("start".equals(e.getActionCommand())) {
			Driver.concentric = !Driver.concentric;
			
			Driver.ru = Double.parseDouble(uRate.getText());
			uRate.setText(Double.toString(Driver.ru));
			
			Driver.rv = Double.parseDouble(vRate.getText());
			vRate.setText(Double.toString(Driver.rv));
			
			Driver.f = Double.parseDouble(f.getText());
			f.setText(Double.toString(Driver.f));
			
			Driver.k = Double.parseDouble(k.getText());
			k.setText(Double.toString(Driver.k));
			
			grids.reset();
			
		} else if ("pause".equals(e.getActionCommand())) {
			Driver.paused = !Driver.paused;
		} else if ("toggle".equals(e.getActionCommand())) {
			Driver.toggle = (Driver.toggle + 1) % 3;
		} else
			return;
	}

}
