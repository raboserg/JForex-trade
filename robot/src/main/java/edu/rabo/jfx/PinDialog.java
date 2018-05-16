package edu.rabo.jfx;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dukascopy.api.system.IClient;

@SuppressWarnings("serial")
public class PinDialog extends JDialog {
	private final String jnlpUrl;
	@SuppressWarnings("unused")
	private final IClient client;
	private final JTextField pinfield = new JTextField();
	private final static JFrame noParentFrame = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(PinDialog.class);

	public static String showAndGetPin(final IClient client, final String url) throws Exception {
		return new PinDialog(client, url).pinfield.getText();
	}

	public PinDialog(final IClient client, final String url) throws Exception {
		super(noParentFrame, "PIN Dialog", true);
		
		this.jnlpUrl = url; 
		this.client = client;

		
		JPanel captchaPanel = new JPanel();
		captchaPanel.setLayout(new BoxLayout(captchaPanel, BoxLayout.Y_AXIS));

		final JLabel captchaImage = new JLabel();
		captchaImage.setIcon(new ImageIcon(client.getCaptchaImage(jnlpUrl)));
		captchaPanel.add(captchaImage);

		captchaPanel.add(pinfield);
		getContentPane().add(captchaPanel);

		JPanel buttonPane = new JPanel();

		JButton btnLogin = new JButton("Login");
		buttonPane.add(btnLogin);
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				dispose();
			}
		});

		JButton btnReload = new JButton("Reload");
		buttonPane.add(btnReload);
		btnReload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					captchaImage.setIcon(new ImageIcon(client.getCaptchaImage(jnlpUrl)));
				} catch (Exception ex) {
					LOGGER.info(ex.getMessage(), ex);
				}
			}
		});
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();
		setVisible(true);
	}
}
