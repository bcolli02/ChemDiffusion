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
	private JSplitPane splitH;
	private ChemicalPanel uPanel, vPanel;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public DiffusionGUI () {
		this.setTitle("Chemical Diffusion CA Model");
		this.setBackground(Color.black);
	    
	    // set bottom panel
	    JPanel topPanel = new JPanel();
	    topPanel.setLayout( new BorderLayout() );
	    getContentPane().add( topPanel );
	    
	    screenSize.width *= 0.7;
	    screenSize.height *= 0.7;
	    
	    int panelWidth = screenSize.width / 2;
	    int panelHeight = screenSize.height;
	    
	    grids = new Grids(panelWidth, panelHeight); 
	    
	    // set diffusion panels
	    uPanel = new UPanel(grids, panelWidth, panelHeight);
	    vPanel = new VPanel(grids, panelWidth, panelHeight);
	    
	    uPanel.add(new JLabel("Diffusion of Chemical U"), BorderLayout.NORTH);
	    vPanel.add(new JLabel("Diffusion of Chemical V"), BorderLayout.NORTH);
	    
	    splitH = new JSplitPane( JSplitPane.HORIZONTAL_SPLIT );
	    splitH.setDividerLocation(panelWidth);
	    splitH.setLeftComponent( uPanel );
	    splitH.setRightComponent( vPanel );
	    
	    topPanel.add(splitH);
	    timer = new Timer(45, this);
	    timer.start();
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
		this.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}


}
