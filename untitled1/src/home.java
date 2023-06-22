import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class home extends JFrame {
    private JPanel home;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JButton REFRESHButton;
    private JButton TİSCHREGİSTİERUNGButton;

    public home() {
        setContentPane(home);
        setTitle("Registration Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        REFRESHButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // İstek yapılacak URL'yi belirtin
                    URL url = new URL("http://localhost:5000/bibliothek");

                    // HttpURLConnection objesi oluşturun
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    // İstek gönderin
                    int responseCode = connection.getResponseCode();

                    // Yanıtı okuyun
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Yanıtı işle
                    String responseString = response.toString();

                    // Parantezlerin içindeki verileri al
                    int startIndex = responseString.indexOf("(");
                    int endIndex;

                    DefaultComboBoxModel<String> comboBoxModel1 = new DefaultComboBoxModel<>();
                    DefaultComboBoxModel<String> comboBoxModel2 = new DefaultComboBoxModel<>();

                    while (startIndex != -1) {
                        endIndex = responseString.indexOf(")", startIndex);

                        if (endIndex != -1) {
                            String tupleString = responseString.substring(startIndex + 1, endIndex);
                            String[] values = tupleString.split(",");

                            // İlk değeri al
                            String value1 = values[0].trim().replaceAll("'", ""); // İlk değeri al ve tek tırnakları kaldır
                            // İkinci değeri al
                            int value2 = Integer.parseInt(values[1].trim());

                            // Verileri combobox'lara ekle
                            comboBoxModel1.addElement(value1);
                            comboBoxModel2.addElement(String.valueOf(value2));

                            startIndex = responseString.indexOf("(", endIndex);
                        } else {
                            break;
                        }
                    }

                    // Combobox'lara veri set etme
                    comboBox1.setModel(comboBoxModel1);
                    comboBox2.setModel(comboBoxModel2);

                    // Yanıtı kullanın
                    System.out.println("HTTP Response Code: " + responseCode);
                    System.out.println("HTTP Response: " + responseString);
                } catch (IOException s) {
                    s.printStackTrace();
                }
            }
        });
    }
}
