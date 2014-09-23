package bc.diffusion.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.Timer;

public class DiffusionGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private Timer timer;
	private Grids grids;
	private JSplitPane splitV, splitH;
	private InputPanel inputPanel;
	private ChemicalPanel chemPanel;
	private Plotter plotter;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public DiffusionGUI() {
		this.setTitle("Chemical Diffusion CA Model");
		this.setBackground(Color.black);

		// set bottom panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		getContentPane().add(topPanel);

		screenSize.width = 800;
		screenSize.height = 400;

		int panelWidth = screenSize.width / 2;
		int panelHeight = screenSize.height;

		createField(panelWidth, panelHeight);
		setFrameComponents(topPanel, panelWidth, panelHeight);

		timer = new Timer(10, this);
		timer.start();
	}

	public void createField(int panelWidth, int panelHeight) {
		grids = new Grids(panelWidth / Driver.scale, panelHeight / Driver.scale);

		// set diffusion panels
		chemPanel = new ChemicalPanel(grids, panelWidth / Driver.scale, panelHeight / Driver.scale);
		chemPanel.add(new JLabel("Diffusion of Chemicals"), BorderLayout.NORTH);
		
		inputPanel = new InputPanel(grids);
		inputPanel.setBackground(new Color(55, 185, 176));
		
		screenSize.width *= 1.51;
		screenSize.height += 130;

		plotter = new Plotter(grids, panelWidth, panelHeight);
		plotter.setBackground(new Color(182, 200, 209));
		plotter.add(new JLabel("Diffusion Plot of Chemicals"),
				BorderLayout.NORTH);
	}

	public void setFrameComponents(JPanel top, int panelWidth, int panelHeight) {
		splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		splitV.setDividerLocation(panelHeight);
		splitV.add(chemPanel);
		splitV.add(inputPanel);

		splitV.setLeftComponent(chemPanel);
		splitV.setRightComponent(inputPanel);
		
		splitH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitH.setDividerLocation(panelWidth);
		top.add(splitH);

		splitH.setLeftComponent(splitV);
		splitH.setRightComponent(plotter);
	}

	@Override
	public void paintComponents(Graphics g) {
		super.paintComponents(g);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

	public void createAndShowGUI() {
		// Set up the content pane.
		this.setPreferredSize(screenSize);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(true);
		this.setVisible(true);
	}

}
