import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;



public class RegistrationPage extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton registirationButton;
    private JPanel register;
    private JPanel registerPanel;
    // Yeni sayfanın tasarım bileşenlerini ve işlevselliğini burada tanımlayabilirsiniz.
    // Örneğin, yeni bir JTextField, JLabel, JButton vb. ekleyebilirsiniz.

    public RegistrationPage() {
        setContentPane(registerPanel);
        setTitle("Registration Page");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setVisible(true);
        registirationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField1.getText();
                String mail = textField1.getText();
                String pass = textField1.getText();
                try {
                    String endpoint = "http://localhost:5000/register";  // Python API endpoint URL
                    URL url = new URL(endpoint);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setDoOutput(true);

                    // İstek verilerini JSON formatında oluştur
                    String requestData = "{\"name\": \"" + name + "\", \"email\": \"" + mail + "\", \"password\": \"" + pass + "\"}";

                    // İstek gövdesine verileri yaz
                    OutputStream outputStream = connection.getOutputStream();
                    outputStream.write(requestData.getBytes());
                    outputStream.flush();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuilder response = new StringBuilder();
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();

                        // API yanıtını kullanarak yapılacak işlemler
                        System.out.println("API Response: " + response.toString());
                    } else {
                        System.out.println("API Call Failed: " + responseCode);
                    }

                    connection.disconnect();
                } catch (Exception s) {
                    s.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        textField1 = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        registirationButton = new JButton();
        register = new JPanel();
        registerPanel = new JPanel();
    }
}
