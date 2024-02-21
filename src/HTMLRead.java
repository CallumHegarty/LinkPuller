import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class HTMLRead implements ActionListener, KeyListener {

    private String website;
    private String keyword;

    public static void main(String[] args) {
        HTMLRead html = new HTMLRead();
    }

    public HTMLRead(){
        GUI();
    }


    private JFrame mainFrame;
    private JLabel siteLabel;
    private JLabel wordLabel;
    private JButton button;
    private JPanel minorPanel;
    private JPanel panel;
    private JTextField siteInput;
    private JTextField wordInput;
    private JTextArea linkList;
    private JScrollPane scrollPane;

    public void GUI(){
        mainFrame = new JFrame();
        siteLabel = new JLabel("        Website:");
        wordLabel = new JLabel("       Keyword:");
        siteInput = new JTextField();
        siteInput.addKeyListener(this);
        wordInput = new JTextField();
        wordInput.addKeyListener(this);
        linkList = new JTextArea();
        scrollPane = new JScrollPane(linkList);
        button = new JButton("Search");
        button.addActionListener(this);

        minorPanel = new JPanel();
        panel = new JPanel();

        int borderSize = 50;
        panel.setBorder(BorderFactory.createEmptyBorder(borderSize,borderSize,borderSize,borderSize));
        panel.setLayout(new BorderLayout());
        minorPanel.setLayout(new GridLayout(1,5));
        minorPanel.add(siteLabel);
        minorPanel.add(siteInput);
        minorPanel.add(wordLabel);
        minorPanel.add(wordInput);
        minorPanel.add(button);
        panel.add(minorPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        mainFrame.add(panel);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object buttonClicked = e.getSource();

        if(buttonClicked == button){
            website = siteInput.getText();
            keyword = wordInput.getText();
            System.out.println(website+", "+keyword);
            pullLink();
        }
    }

    public void pullLink(){
        linkList.setText("");
        try {
            URL url = new URL(website);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            while( (line = reader.readLine()) != null ){
                if(line.contains("<a href")&&line.contains(keyword)){
                    int linkIndex = line.indexOf("<a href");
                    String link = line.substring(linkIndex+9);
                    int quoteIndex = link.indexOf("\"");
                    link = link.substring(0, quoteIndex);
                    if(link.contains(keyword)) {
                        linkList.setText(linkList.getText() + link + "\n");
                    }
                }
            }

            reader.close();

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            website = siteInput.getText();
            keyword = wordInput.getText();
            System.out.println(website+", "+keyword);
            pullLink();
        }
    }


    public void keyReleased(KeyEvent e) {

    }
}
