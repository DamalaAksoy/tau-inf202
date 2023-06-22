    import javax.swing.*;
    import java.awt.event.ActionEvent;
    import java.awt.event.ActionListener;
    import java.net.HttpURLConnection;
    import java.net.URL;
    import java.io.BufferedReader;
    import java.io.InputStreamReader;
    import java.io.OutputStream;

    public class login extends JFrame {
        private JTextField textField1;
        private JPasswordField passwordField1;
        private JButton REGISTERButton;
        private JButton LOGINButton;
        private JPanel LoginPanel;

        public login() {

            setContentPane(LoginPanel);
            setTitle("Login");
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(300,200);
            setLocationRelativeTo(null);
            setVisible(true);
            REGISTERButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    RegistrationPage registrationPage = new RegistrationPage();
                }

            });
            LOGINButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String mail = textField1.getText();
                    char[] passwordChars = passwordField1.getPassword();
                    String password = new String(passwordChars);
                    System.out.println(password);
                    try {
                        String endpoint = "http://localhost:5000/login";  // Python API endpoint URL
                        URL url = new URL(endpoint);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setDoOutput(true);

                        // İstek verilerini JSON formatında oluştur
                        String requestData = "{\"email\": \"" + mail + "\", \"password\": \"" + password + "\"}";

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
                            if (response.toString().equals("200")) {
                                home homepage =new home();
                            }

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
        public static void main(String[] args){
            new login();
        }

        private void createUIComponents() {
            LoginPanel = new JPanel();
            textField1 = new JTextField();
            passwordField1 = new JPasswordField();
            REGISTERButton = new JButton();
            LOGINButton = new JButton();
        }
    }
