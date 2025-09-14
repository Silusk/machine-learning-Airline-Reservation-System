import java.awt.Image;

import java.awt.Graphics;

import java.awt.Graphics2D;

import javax.swing.JWindow;
import java.awt.Toolkit;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import javax.imageio.ImageIO;

import java.io.File;

import javax.swing.event.MouseInputListener;
import java.io.IOException;
import javax.swing.*;

import java.awt.*;

import java.awt.event.*;

import java.sql.*;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import com.lowagie.text.*;

import com.lowagie.text.pdf.*;

import java.io.FileOutputStream;
import com.lowagie.text.Font;

import com.lowagie.text.pdf.BaseFont;

import com.lowagie.text.FontFactory;
import java.awt.Desktop;

import javax.mail.*;

import javax.mail.internet.*;

import java.util.Properties;
import java.io.File;
import org.jxmapviewer.JXMapViewer;

import org.jxmapviewer.viewer.*;

import org.jxmapviewer.input.*;

import org.jxmapviewer.painter.CompoundPainter;

import org.jxmapviewer.painter.Painter;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.util.Map;

import java.util.Set;

import java.util.HashMap;

import java.util.HashSet;
import org.jxmapviewer.viewer.GeoPosition;

import org.jxmapviewer.OSMTileFactoryInfo;
import org.vosk.Model;

import org.vosk.Recognizer;

import org.vosk.LibVosk;

import javax.sound.sampled.*;
import org.vosk.LogLevel;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Dimension;
import org.jxmapviewer.viewer.Waypoint;

import org.jxmapviewer.viewer.DefaultWaypoint;

import org.jxmapviewer.viewer.WaypointPainter;

import org.jxmapviewer.viewer.TileFactory;

import org.jxmapviewer.viewer.TileFactoryInfo;


import java.awt.geom.Point2D;
import org.jdesktop.swingx.*;

import java.time.LocalDateTime;

import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;

import java.time.Duration;
//import org.jdesktop.swingx.mapviewer.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
public class airs extends JFrame {


private static final String DB_URL = "jdbc:mysql://localhost:3306/airline_system";

private static final String DB_USER = "root";

private static final String DB_PASS = "forachieve"; 
// change if needed


private JTextField usernameField, sourceField, destinationField, seatCountField,emailField,cancelUsernameField,bookingIdField;
JTabbedPane tabbedPane = new JTabbedPane();
private JPasswordField passwordField;

private JTextArea resultArea;

private JComboBox<Integer> flightSelector;

private String selectedSourceCity = null;

private String selectedDestinationCity = null;
public static Map<String, GeoPosition> cities = new HashMap<>();
 
private JXMapViewer map; 
private String lastBookedSource = "Delhi";

private String lastBookedDestination = "Mumbai";
String departureTimeStr="2025-05-12T10:00";
String arrivalTimeStr="2025-05-12T13:00";
class PlaneAnimationPanel extends JPanel implements ActionListener {
    
private java.awt.Image planeImage;
    
private int x = 0;
    
private int y=100;
private Timer timer;

    
public PlaneAnimationPanel() {
  
try{      
Image originalImage=ImageIO.read(getClass().getResource("flight.jpeg"));
planeImage=originalImage.getScaledInstance(200,120,Image.SCALE_SMOOTH);
//System.out.println("Image loaded: " + (planeImage != null));
}catch(IOException e){
e.printStackTrace();
}       
setPreferredSize(new Dimension(800,200));
timer = new Timer(30, this);      
timer.start();
    
}

    
@Override
    
protected void paintComponent(Graphics g) {
        
super.paintComponent(g);
     
g.drawImage(planeImage, x,15,this); 
}

    
@Override
    
public void actionPerformed(ActionEvent e) {
        
x += 4;
        
if (x > getWidth()) {
            
x = -100; 
}
        
repaint();
    
}

}

public airs() {
  
setTitle("FlyJet Airline Reservation");
    
setSize(900, 600);
    
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
setLocationRelativeTo(null);

       
PlaneAnimationPanel animationPanel = new PlaneAnimationPanel();
    
animationPanel.setPreferredSize(new Dimension(700, 100));
    
add(animationPanel, BorderLayout.NORTH);

     
tabbedPane.addTab("User Login/Register", createLoginPanel());
    
tabbedPane.addTab("Search Flights", createSearchPanel());
    
tabbedPane.addTab("Book Flight", createBookingPanel());

    
tabbedPane.addTab("Cancel Booking",createCancelPanel());
tabbedPane.addTab("View Bookings",createViewBookingsPanel());
tabbedPane.addTab("Track Flight",createTrackFlightPanel());
tabbedPane.addTab("ChatBot Assistant",new ChatBotPanel());
resultArea = new JTextArea(8, 40);
    
resultArea.setEditable(false);
    
resultArea.setBorder(BorderFactory.createTitledBorder("Output"));

    
add(tabbedPane, BorderLayout.CENTER);
    
add(new JScrollPane(resultArea), BorderLayout.SOUTH);

    
setVisible(true);    
new VoiceAssistant(this).start();
}


private JPanel createLoginPanel() {
    
JPanel panel = new JPanel(new GridBagLayout());
    
panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
    
panel.setBackground(Color.WHITE); 
GridBagConstraints gbc = new GridBagConstraints();
    
gbc.insets = new Insets(10, 10, 10, 10);
    
gbc.anchor = GridBagConstraints.WEST;
    
gbc.fill = GridBagConstraints.HORIZONTAL;

    
gbc.gridx = 0;
    
gbc.gridy = 0;
    
panel.add(new JLabel("Username:"), gbc);

  
gbc.gridx = 1;
    
usernameField = new JTextField(25);
    
panel.add(usernameField, gbc);


gbc.gridx = 0;
    
gbc.gridy = 1;
    
panel.add(new JLabel("Password:"), gbc);

       
gbc.gridx = 1;
    
passwordField = new JPasswordField(25);
    
panel.add(passwordField, gbc);

    
gbc.gridx = 0;
    
gbc.gridy = 2;
    
gbc.gridwidth = 2;
    
gbc.anchor = GridBagConstraints.CENTER;
    
gbc.fill = GridBagConstraints.NONE;
    
JButton faceLoginBtn = new JButton("Login Through Face");
    
faceLoginBtn.setPreferredSize(new Dimension(150, 50));
    
faceLoginBtn.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
    
faceLoginBtn.addActionListener(e -> loginWithFace());
    
panel.add(faceLoginBtn, gbc);

    
gbc.gridy = 3;
    
JButton loginBtn = new JButton("Login / Register");
    
loginBtn.setPreferredSize(new Dimension(150, 50));
    
loginBtn.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12));
    
loginBtn.addActionListener(e -> loginOrRegister());
    
panel.add(loginBtn, gbc);

    
return panel;

}
private JPanel createSearchPanel() {
    
JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
    
panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    
panel.add(new JLabel("Source:"));
    
sourceField = new JTextField();
    
panel.add(sourceField);

    
panel.add(new JLabel("Destination:"));
    
destinationField = new JTextField();
    
panel.add(destinationField);

    
JButton searchBtn = new JButton("Search Flights");
    
searchBtn.addActionListener(e -> searchFlights());

    
JButton mapBtn = new JButton("Select From Map");
    
mapBtn.addActionListener(e -> openMap());

    
panel.add(mapBtn);
    
panel.add(searchBtn);

    
return panel;

}

private JPanel createBookingPanel() {
    
JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
    
panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    
panel.add(new JLabel("Email:"));
    
emailField = new JTextField();
    
panel.add(emailField);

    
panel.add(new JLabel("Seat Count:"));
    
seatCountField = new JTextField();
    
panel.add(seatCountField);

    
panel.add(new JLabel("Select Flight ID:"));
    
flightSelector = new JComboBox<>();
    
panel.add(flightSelector);

    
JButton bookBtn = new JButton("Book Flight");
    
bookBtn.addActionListener(e -> bookFlight());
    
panel.add(new JLabel());
    
panel.add(bookBtn);

  
JButton seatMapBtn = new JButton("Select Seats Graphically");

seatMapBtn.addActionListener(e -> openSeatSelection());


panel.add(seatMapBtn);  
return panel;

}
private Connection connect() throws SQLException {
    
return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

}


private void loginOrRegister() {
    
String user = usernameField.getText();
    
String pass = new String(passwordField.getPassword());

    
if (user.isEmpty() || pass.isEmpty()) {
        
resultArea.setText("Username or password cannot be empty.");
        
return;
    
}

    
try (Connection conn = connect()) {
        
PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username=?");
        
checkStmt.setString(1, user);
        
ResultSet rs = checkStmt.executeQuery();

        
if (rs.next()) {
            
resultArea.setText("Welcome back, " + user + "!");
        
} 
else {
            
PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            
insertStmt.setString(1, user);
            
insertStmt.setString(2, pass);
            
insertStmt.executeUpdate();
            
resultArea.setText("User registered and logged in: " + user);
        
}
    
} catch (SQLException ex) {
        
resultArea.setText("Database error: " + ex.getMessage());
    
}

}
private double calculateEuclideanDistance(GeoPosition point1, GeoPosition point2) {
    
double lat1 = point1.getLatitude();
    
double lon1 = point1.getLongitude();
    
double lat2 = point2.getLatitude();
    
double lon2 = point2.getLongitude();
    
  
lat1 = Math.toRadians(lat1);
    
lon1 = Math.toRadians(lon1);
    
lat2 = Math.toRadians(lat2);
    
lon2 = Math.toRadians(lon2);

   
double latDiff = lat2 - lat1;
    
double lonDiff = lon2 - lon1;

      
double R = 6371; 
double distance = Math.sqrt(Math.pow(latDiff, 2) + Math.pow(lonDiff, 2)) * R;

    
return distance;

}

private void searchFlights() {
    
String source = sourceField.getText();
    
String destination = destinationField.getText();

    
if (source.isEmpty() || destination.isEmpty()) {
        
resultArea.setText("Please enter source and destination.");
        
return;
    
}

    
String predictedPrice = predictPrice("FlyJet", source, destination);
    
resultArea.append("\n[Predicted Price (ML)]: " + predictedPrice);

    
try (Connection conn = connect()) {
        
PreparedStatement stmt = conn.prepareStatement("SELECT * FROM flights WHERE source=? AND destination=?");
        
stmt.setString(1, source);
        
stmt.setString(2, destination);
        
ResultSet rs = stmt.executeQuery();

        
StringBuilder sb = new StringBuilder("Available Flights:\n");
        
flightSelector.removeAllItems();

        
while (rs.next()) {
            
int flightId = rs.getInt("flight_id");
            
sb.append("Flight ID: ").append(flightId).append(", Airline: ").append(rs.getString("airline"))
.append(", Departure: ").append(rs.getTime("departure_time"))
.append(", Arrival: ").append(rs.getTime("arrival_time")).append(", Price: ?").append(rs.getDouble("price")).append("\n");
            
flightSelector.addItem(flightId);
        
}

        
resultArea.setText(sb.toString());

    
} catch (SQLException ex) {
        
resultArea.setText("Error: " + ex.getMessage());
    
}

}
private Set<String> selectedSeats = new HashSet<>();


private void openSeatSelection() {
    
JFrame seatFrame = new JFrame("Select Seats");
    
seatFrame.setSize(400, 400);
    
seatFrame.setLocationRelativeTo(this);
    
    
JPanel seatPanel = new JPanel(new GridLayout(10, 6, 5, 5));   
for (int row = 1; row <= 10; row++) {
        
for (char col = 'A'; col <= 'F'; col++) {
            
String seatId = row + String.valueOf(col);
            
JToggleButton seatBtn = new JToggleButton(seatId);
            
seatBtn.addItemListener(e -> {
                
if (seatBtn.isSelected()) selectedSeats.add(seatId);
                
else selectedSeats.remove(seatId);
            
});
            
seatPanel.add(seatBtn);
        
}
    
}

    
JButton confirmBtn = new JButton("Confirm Selection");
    
confirmBtn.addActionListener(e -> {
        
seatCountField.setText(String.valueOf(selectedSeats.size()));
        
seatFrame.dispose();
    
});

    
seatFrame.add(new JScrollPane(seatPanel), BorderLayout.CENTER);

seatFrame.add(confirmBtn, BorderLayout.SOUTH);
    
seatFrame.setVisible(true);
}
public static void showNotification(String message) {
    
JWindow window = new JWindow();
    
window.setLayout(new BorderLayout());
    
JLabel label = new JLabel(message, SwingConstants.CENTER);
    
label.setOpaque(true);
    
label.setBackground(new Color(60, 63, 65));
    
label.setForeground(Color.WHITE);
    
label.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
    
label.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    
window.add(label, BorderLayout.CENTER);
    
window.pack();

    
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    
int x = (screenSize.width - window.getWidth()) / 2;
    
int y = screenSize.height - window.getHeight() - 100;
    
window.setLocation(400, 400);

  
window.setAlwaysOnTop(true); 
System.out.println("showing nofication"); 
window.setVisible(true);

    
new Timer(3000, e -> window.dispose()).start();

}
private void bookFlight() {
    
String user = usernameField.getText();
    
String source = sourceField.getText();
    
String destination = destinationField.getText();

 
lastBookedSource = sourceField.getText();
lastBookedDestination =destinationField.getText();

 
int seatCount;
    
try {
        
seatCount = Integer.parseInt(seatCountField.getText());
        
if (seatCount <= 0) {
            
resultArea.setText("Seat count must be positive.");
            
return;
        
}
    
} 
catch (NumberFormatException ex) {
        
resultArea.setText("Please enter a valid seat count.");
        
return;
    
}

    
if (user.isEmpty() || source.isEmpty() || destination.isEmpty()) {
        
resultArea.setText("All fields are required to book a flight.");
        
return;
    
}

    
Integer selectedFlightId = (Integer) flightSelector.getSelectedItem();
    
if (selectedFlightId == null) {
        
resultArea.setText("Please select a flight to book.");
        
return;
    
}

    
try (Connection conn = connect()) {
        
PreparedStatement userStmt = conn.prepareStatement("SELECT id FROM users WHERE username=?");
        
userStmt.setString(1, user);
        
ResultSet userRs = userStmt.executeQuery();

        
if (!userRs.next()) {
            
resultArea.setText("Please login/register first.");
            
return;
        
}
        
int userId = userRs.getInt("id");

        
PreparedStatement flightStmt = conn.prepareStatement("SELECT * FROM flights WHERE flight_id=?");
        
flightStmt.setInt(1, selectedFlightId);
        
ResultSet flightRs = flightStmt.executeQuery();

        
if (!flightRs.next()) {
            
resultArea.setText("Flight not found.");
            
return;
        
}

        
int availableSeats = flightRs.getInt("seats_available");
        
String airline = flightRs.getString("airline");
        
Time departure = flightRs.getTime("departure_time");
    
Time arrival=flightRs.getTime("arrival_time");    
double price = flightRs.getDouble("price");

        
if (seatCount > availableSeats) {
            
resultArea.setText("Not enough seats available. Only " + availableSeats + " seats left.");
            
return;
        
}

        
PaymentDialog paymentDialog = new PaymentDialog(this); // mainFrame = your 

paymentDialog.setVisible(true);

if (paymentDialog.isPaymentSuccess()) {
        
SwingUtilities.invokeLater(() ->showNotification("Booking successful. Ticket sent to email."));

} 
else {
    
SwingUtilities.invokeLater(() ->showNotification("Payment was cancelled."));

}
PreparedStatement bookStmt = conn.prepareStatement("INSERT INTO bookings (user_id, flight_id, seat_count) VALUES (?, ?, ?)");
        
bookStmt.setInt(1, userId);
        
bookStmt.setInt(2, selectedFlightId);
        
bookStmt.setInt(3, seatCount);
        
bookStmt.executeUpdate();

        
PreparedStatement updateStmt = conn.prepareStatement("UPDATE flights SET seats_available = seats_available - ? WHERE flight_id = ?");
        
updateStmt.setInt(1, seatCount);
        
updateStmt.setInt(2, selectedFlightId);
        
updateStmt.executeUpdate();

        
double totalCost = price * seatCount;
  
StringBuilder ticket = new StringBuilder();
        
ticket.append("* Ticket Confirmation *\n");
        
ticket.append("Passenger: ").append(user).append("\n");
        
ticket.append("Flight ID: ").append(selectedFlightId).append(" | Airline: ").append(airline).append("\n");
        
ticket.append("Route: ").append(source).append(" ? ").append(destination).append("\n");
        
ticket.append("Departure: ").append(departure).append("\n");
    
ticket.append("Arrival: ").append(arrival).append("\n");    
ticket.append("Seats Booked: ").append(seatCount).append("\n");
        
ticket.append("Total Price: ?").append(totalCost).append("\n");
        
ticket.append("Booking Confirmed!\n");

        
resultArea.setText(ticket.toString());

               
Document document = new Document();
        
String fileName = "Ticket_" + user + "_" + System.currentTimeMillis() + ".pdf";
        
PdfWriter.getInstance(document, new FileOutputStream(fileName));
        
document.open();
        
document.addTitle("Flight Ticket");
        
Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 24, Font.BOLD);

Paragraph header = new Paragraph("FlyJet Airline Ticket", headerFont);

document.add(header); 
document.add(new Paragraph(" "));
        
document.add(new Paragraph(ticket.toString(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
        
document.close();
resultArea.append("\nTicket PDF saved as: " + fileName);


String userEmail = emailField.getText();

if (!userEmail.isEmpty()) {
    
sendEmail(userEmail, fileName, user); // method below
}
Desktop.getDesktop().open(new File(fileName));    
} catch (Exception ex) {
        
resultArea.setText("Booking error: " + ex.getMessage());
    
}

}

private String predictPrice(String airline, String source, String destination) {
    
try {
        
ProcessBuilder pb = new ProcessBuilder("python", "predict_price.py", airline, source, destination);
        
pb.redirectErrorStream(true);
        
Process process = pb.start();
        
BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        
String line;
        
StringBuilder output = new StringBuilder();
        
while ((line = reader.readLine()) != null) {
            
output.append(line);
        
}
        
int exitCode = process.waitFor();
        
if (exitCode == 0) {
            
return output.toString();
        
} 
else {
            
return "Error predicting price";
        
}
    
} 
catch (Exception ex) {
        
return "Prediction failed: " + ex.getMessage();
    
}

}
private void sendEmail(String to, String pdfPath, String username) {
    
final String from = "silus0908@gmail.com"; // your sender email
    
final String password = "your password"; 
// generate App Password from Gmail settings

    
Properties props = new Properties();
    
props.put("mail.smtp.auth", "true");
    
props.put("mail.smtp.starttls.enable", "true");
    
props.put("mail.smtp.host", "smtp.gmail.com");
    
props.put("mail.smtp.port", "587");

    
Session session = Session.getInstance(props, new Authenticator() {
        
protected PasswordAuthentication getPasswordAuthentication() {
            
return new PasswordAuthentication(from, password);
        
}
    
});

    
try {
        
Message message = new MimeMessage(session);
        
message.setFrom(new InternetAddress(from));
        
message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
        
message.setSubject("Your FlyJet Ticket");

        
BodyPart messageBodyPart = new MimeBodyPart();
        
messageBodyPart.setText("Hi " + username + ",\n\nThank you for booking with FlyJet! Please find your ticket attached.");

        
Multipart multipart = new MimeMultipart();
        
multipart.addBodyPart(messageBodyPart);

        
MimeBodyPart attachmentPart = new MimeBodyPart();
        
attachmentPart.attachFile(new java.io.File(pdfPath));
        
multipart.addBodyPart(attachmentPart);

        
message.setContent(multipart);

        
Transport.send(message);

        
System.out.println("Email sent to " + to);

    
} 
catch (Exception e) {
        
e.printStackTrace();
        
resultArea.append("\nFailed to send email: " + e.getMessage());
    
}
}
private void openMap() {
    
JXMapViewer mapViewer = new JXMapViewer();
    
TileFactoryInfo info = new OSMTileFactoryInfo();
    
DefaultTileFactory tileFactory = new DefaultTileFactory(info);
    
mapViewer.setTileFactory(tileFactory);

      
GeoPosition india = new GeoPosition(22.9734, 78.6569); 
mapViewer.setZoom(13);
    
mapViewer.setAddressLocation(india);

    
MouseInputListener mia = new PanMouseInputListener(mapViewer);
    
mapViewer.addMouseListener(mia);
    
mapViewer.addMouseMotionListener(mia);
    
mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

  
mapViewer.addMouseListener(new MouseAdapter() {
        
@Override
        
public void mouseClicked(MouseEvent e) {
        
Point clickedPoint = e.getPoint();
        
GeoPosition clickedGeo = mapViewer.convertPointToGeoPosition(clickedPoint);

        
String selectedCity = getCityFromGeoPosition(clickedGeo);
System.out.println("Clicked Location: " + clickedGeo.getLatitude() + ", " + clickedGeo.getLongitude());       
if (selectedCity != null) {
            
showCitySelectionDialog(selectedCity);
        
}
    
}
});
JFrame mapFrame = new JFrame("Select City on Map");
    
mapFrame.add(mapViewer);
    mapFrame.setSize(800, 600);
    
mapFrame.setLocationRelativeTo(null);
    
mapFrame.setVisible(true);

}
private String getCityFromGeoPosition(GeoPosition geoPosition) {
      
double lat = geoPosition.getLatitude();
    
double lon = geoPosition.getLongitude();

    
if (isNear(lat, lon, 8.7139, 77.7567)) return "Tirunelveli";
    
if (isNear(lat, lon, 13.0827, 80.2707)) return "Chennai";
    
if (isNear(lat, lon, 9.9252, 78.1198)) return "Madurai";
 
if(isNear(lat, lon,  22.976648454149785, 78.65841865539551)) return "Jaipur"; 
if(isNear(lat, lon,  19.0760, 72.8777)) return "Mumbai";
if(isNear(lat, lon, 28.6139, 77.2090)) return "Delhi"; 
if(isNear(lat, lon,  22.5726, 88.3639)) return "Kolkata"; 
if(isNear(lat, lon, 12.9716, 77.5946)) return "Bengaluru"; 
if(isNear(lat, lon, 17.3850, 78.4867)) return "Hyderabad"; 
if(isNear(lat, lon, 23.0225, 72.5714)) return "Ahmedabad";         
return "Unknown City";

}
private boolean isNear(double lat1, double lon1, double lat2, double lon2) {
    
double tolerance = 0.5;  return Math.abs(lat1 - lat2) < tolerance && Math.abs(lon1 - lon2) < tolerance;

}
private void showCitySelectionDialog(String city) {
    
JDialog dialog = new JDialog((Frame) null, "Select City Role", true);
    
dialog.setLayout(new BorderLayout());
    
dialog.setSize(300, 150);
    
dialog.setLocationRelativeTo(null);

    
JLabel label = new JLabel("Selected city: " + city, SwingConstants.CENTER);
    
dialog.add(label, BorderLayout.CENTER);

    
JPanel buttonPanel = new JPanel();
    
JButton sourceButton = new JButton("Set as Source");
    
JButton destButton = new JButton("Set as Destination");

    
sourceButton.addActionListener(e -> {
        
selectedSourceCity = city;
 
//lastBookedSource = selectedSourceCity; 
sourceField.setText(selectedSourceCity);    
dialog.dispose();
        
JOptionPane.showMessageDialog(null, "Source set to: " + city);
    
});

    
destButton.addActionListener(e -> {
        
selectedDestinationCity = city;
    
//lastBookedDestination = selectedDestinationCity;  
destinationField.setText(selectedDestinationCity);    
dialog.dispose();
        
JOptionPane.showMessageDialog(null, "Destination set to: " + city);
    
});

   
 buttonPanel.add(sourceButton);
    
buttonPanel.add(destButton);
    
dialog.add(buttonPanel, BorderLayout.SOUTH);

    
dialog.setVisible(true);

}
private String getNearestCity(GeoPosition pos) {
      
InitializeCityCoordinates();
String nearest = null;
    double minDist = Double.MAX_VALUE;
    
for (Map.Entry<String, GeoPosition> entry : cities.entrySet()) {
        
double dist = calculateEuclideanDistance(pos, entry.getValue());
        
if (dist < minDist) {
            
minDist = dist;
            
nearest = entry.getKey();
        
}
    
}

    
return nearest;

}
public class PaymentDialog extends JDialog {
    
private boolean paymentSuccess = false;

    
public PaymentDialog(JFrame parent) {
        
super(parent, "Payment", true);
        
setLayout(new BorderLayout());

        
JPanel methodPanel = new JPanel(new GridLayout(3, 1));
        
JRadioButton cardOption = new JRadioButton("Credit/Debit Card");
        
JRadioButton upiOption = new JRadioButton("UPI");
        
JRadioButton netBankingOption = new JRadioButton("Net Banking");
        
ButtonGroup group = new ButtonGroup();
        
group.add(cardOption);
        
group.add(upiOption);
        
group.add(netBankingOption);
        
methodPanel.setBorder(BorderFactory.createTitledBorder("Choose Payment Method"));
        
methodPanel.add(cardOption);
        
methodPanel.add(upiOption);
        
methodPanel.add(netBankingOption);

        
JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        
JTextField input1 = new JTextField();
        
JTextField input2 = new JTextField();
        
JTextField input3 = new JTextField();

        
cardOption.addActionListener(e -> {
            
inputPanel.removeAll();
            
inputPanel.add(new JLabel("Card Number:"));
            
inputPanel.add(input1);
            
inputPanel.add(new JLabel("Expiry (MM/YY):"));
            
inputPanel.add(input2);
            
inputPanel.add(new JLabel("CVV:"));
            
inputPanel.add(input3);
            
inputPanel.revalidate();
            
inputPanel.repaint();
        
});

        
upiOption.addActionListener(e -> {
            
inputPanel.removeAll();
            
inputPanel.add(new JLabel("UPI ID:"));
            
inputPanel.add(input1);
            
inputPanel.revalidate();
            
inputPanel.repaint();
        
});

        
netBankingOption.addActionListener(e -> {
            
inputPanel.removeAll();
            
inputPanel.add(new JLabel("Choose Bank:"));
            
String[] banks = {"SBI", "HDFC", "ICICI", "Axis"};
            
JComboBox<String> bankDropdown = new JComboBox<>(banks);
            
inputPanel.add(bankDropdown);
            
inputPanel.revalidate();
            
inputPanel.repaint();
       
 });

        
JButton payButton = new JButton("Pay Now");
        
payButton.addActionListener(e -> {
           
JOptionPane.showMessageDialog(this, "Payment successful!");
            
paymentSuccess = true;
            
dispose();
        
});

        
add(methodPanel, BorderLayout.NORTH);
        
add(inputPanel, BorderLayout.CENTER);
        
add(payButton, BorderLayout.SOUTH);
        
setSize(400, 300);
        
setLocationRelativeTo(parent);
    
}

    
public boolean isPaymentSuccess() {
        
return paymentSuccess;
    
}

}
public class ChatBotPanel extends JPanel {
    
private JTextArea chatArea;
    
private JTextField inputField;
    
private JButton sendButton;

    
public ChatBotPanel() {
        
setLayout(new BorderLayout());

        
chatArea = new JTextArea();
        
chatArea.setEditable(false);
        
chatArea.setLineWrap(true);

        
JScrollPane scrollPane = new JScrollPane(chatArea);

        
inputField = new JTextField();
        
sendButton = new JButton("Send");

        
JPanel inputPanel = new JPanel(new BorderLayout());
        
inputPanel.add(inputField, BorderLayout.CENTER);
        
inputPanel.add(sendButton, BorderLayout.EAST);

        
add(scrollPane, BorderLayout.CENTER);
        
add(inputPanel, BorderLayout.SOUTH);

             
sendButton.addActionListener(e -> sendMessage());
               
inputField.addActionListener(e -> sendMessage());
    
}

    
private void sendMessage() {
        
String userInput = inputField.getText().trim();
        
if (!userInput.isEmpty()) {
            
chatArea.append("You: " + userInput + "\n");
            
String response = getBotResponse(userInput);
            
chatArea.append("FlyJetBot: " + response + "\n");
            
inputField.setText("");
        
}
    
}

    
private String getBotResponse(String input) {
        
input = input.toLowerCase();
        
if (input.contains("book") && input.contains("flight")) {
            
return "To book a flight, go to the 'Search Flights' tab and choose your route.";
        
} 
if(input.contains("log in")||input.contains("register")){
return "go to the User Login/Register page and fill your details then click Login/Register button";
}
else if (input.contains("cancel")&& input.contains("booking")) {
            
return "To cancel a booking, use the 'Manage Booking' option and enter your ticket ID.";
        
} 
else if (input.contains("view") &&input.contains("booking")) {
            
return "go to the View Booking panel and fill the details then it will show your bookings";
        
}
else if (input.contains("price")) {
            
return "you should click the search flights to see price for each flights";
        
} 
else if (input.contains("download") || input.contains("bookings")||input.contains("text")) {
            
return "go to the View Bookings page then fill your details and then click export bookings to text file button it will download in your system";
        
}
else if (input.contains("hello") || input.contains("hi")) {
            
return "Hello! I'm your FlyJet Assistant. How can I help you today?";
        
} 
else if (input.contains("map") || input.contains("location")) {
            
return "You can select cities directly from the map in the booking panel.";
        
} 
else if (input.contains("thanks") || input.contains("successfully")) {
            
return "congrat have a fantastic journey";
        
}
else if (input.contains("flight") || input.contains("booking")) {
            
return "To book a flight go to the Book Flight fill your details then click the Book Flight button it will book the flight ";
        
}
else {
            
return "Sorry, I did not understand that. Try asking about booking, cancellation, or flight status.";
        
}
    
}

}
private JPanel createCancelPanel() {
    
JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
    
panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    
cancelUsernameField = new JTextField();
    
bookingIdField = new JTextField();

    
panel.add(new JLabel("Username:"));
    
panel.add(cancelUsernameField);
    
panel.add(new JLabel("Booking ID:"));
    
panel.add(bookingIdField);

    
JButton cancelBtn = new JButton("Cancel Booking");
    
panel.add(new JLabel()); // empty space
    
panel.add(cancelBtn);

    
cancelBtn.addActionListener(e -> cancelBooking()); 
return panel;

}

// This method is called on button click

public void cancelBooking() {
    
String username = cancelUsernameField.getText();
    
String bookingIdText = bookingIdField.getText();

    
if (username.isEmpty() || bookingIdText.isEmpty()) {
        
resultArea.setText("Please enter both username and booking ID.");
        
return;
    
}

    
try {
        
int bookingId = Integer.parseInt(bookingIdText);
        
boolean success = performBookingCancellation(username, bookingId);

        
if (success) {
            
resultArea.setText("Booking cancelled successfully!");
        
} else {
            
resultArea.setText("Booking cancellation failed. Please check your details.");
        
}
    
} catch (NumberFormatException ex) {
        
resultArea.setText("Invalid booking ID format.");
    
} catch (Exception ex) {
        
ex.printStackTrace();
        
resultArea.setText("An error occurred during cancellation.");
    
}

}

// This method does the real work (cancels the booking and updates seat availability)

private boolean performBookingCancellation(String username, int bookingId) {
    
try (Connection conn = connect()) {
       
PreparedStatement userStmt = conn.prepareStatement("SELECT id FROM users WHERE username=?");
        
userStmt.setString(1, username);
        
ResultSet userRs = userStmt.executeQuery();
        
if (!userRs.next()) {
            
return false; // user not found
        
}
        
int userId = userRs.getInt("id");

        
PreparedStatement checkStmt = conn.prepareStatement(
"SELECT flight_id, seat_count FROM bookings WHERE booking_id=? AND user_id=?"
);
        
checkStmt.setInt(1, bookingId);
        
checkStmt.setInt(2, userId);
        
ResultSet bookingRs = checkStmt.executeQuery();
        
if (!bookingRs.next()) {
            
return false; // booking not found
        
}

        
int flightId = bookingRs.getInt("flight_id");
        
int seatCount = bookingRs.getInt("seat_count");

           
PreparedStatement deleteStmt = conn.prepareStatement(
"DELETE FROM bookings WHERE booking_id=?"
);
        
deleteStmt.setInt(1, bookingId);
        
int rowsAffected = deleteStmt.executeUpdate();

        
if (rowsAffected > 0) {
                    
PreparedStatement updateFlightStmt = conn.prepareStatement(
"UPDATE flights SET seats_available = seats_available + ? WHERE flight_id=?"
);
            
updateFlightStmt.setInt(1, seatCount);
            
updateFlightStmt.setInt(2, flightId);
            
updateFlightStmt.executeUpdate();

            
return true;    
} else {
            
return false;        
}
    
} catch (Exception ex) {
        
ex.printStackTrace();
        
return false; 
}

}
private JTextField viewBookingUsernameField;

private JTextArea viewBookingOutputArea;


private JPanel createViewBookingsPanel() {
    
JPanel panel = new JPanel(new BorderLayout(10, 10));
    
panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

    
JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));

    
viewBookingUsernameField = new JTextField();
    
JButton viewBtn = new JButton("View Bookings");

    
inputPanel.add(new JLabel("Enter Username:"));
    
inputPanel.add(viewBookingUsernameField);

    
viewBookingOutputArea = new JTextArea(10, 40);
    
viewBookingOutputArea.setEditable(false);
    
JScrollPane scrollPane = new JScrollPane(viewBookingOutputArea);

    
panel.add(inputPanel, BorderLayout.NORTH);
    
panel.add(scrollPane, BorderLayout.CENTER);
    
panel.add(viewBtn, BorderLayout.SOUTH);

  
JButton exportBtn = new JButton("Export Bookings to File");

panel.add(exportBtn, BorderLayout.EAST);  
exportBtn.setPreferredSize(new Dimension(100,25));
exportBtn.addActionListener(e -> exportBookingsToFile());
viewBtn.addActionListener(e -> viewBookings());    
return panel;

}


private void viewBookings() {
    
String username = viewBookingUsernameField.getText().trim();

    
if (username.isEmpty()) {
        
viewBookingOutputArea.setText("Please enter a username.");
        
return;
    
}

try (Connection conn = connect()) {
        
PreparedStatement userStmt = conn.prepareStatement("SELECT id FROM users WHERE username=?");
        
userStmt.setString(1, username);
        
ResultSet userRs = userStmt.executeQuery();

        
if (!userRs.next()) {
            
viewBookingOutputArea.setText("User not found.");
            
return;
        
}

        
int userId = userRs.getInt("id");

        
PreparedStatement bookingStmt = conn.prepareStatement(
"SELECT booking_id, flight_id, seat_count FROM bookings WHERE user_id = ?");
        
bookingStmt.setInt(1, userId);
        
ResultSet rs = bookingStmt.executeQuery();

        
StringBuilder result = new StringBuilder("Bookings for " + username + ":\n\n");
        
boolean hasBookings = false;

        
while (rs.next()) {
            
hasBookings = true;
            
int bookingId = rs.getInt("booking_id");
            
int flightId = rs.getInt("flight_id");
            
int seats = rs.getInt("seat_count");

            
result.append("Booking ID: ").append(bookingId)
.append(" | Flight ID: ").append(flightId)
.append(" | Seats: ").append(seats).append("\n");
        
}

        
viewBookingOutputArea.setText(hasBookings ? result.toString() : "No bookings found for this user.");

    
} 
catch (SQLException ex) {
        
viewBookingOutputArea.setText("Error: " + ex.getMessage());
    
}

}
private void exportBookingsToFile() {
    
String content = viewBookingOutputArea.getText();
    
if (content.isEmpty()) {
        
JOptionPane.showMessageDialog(null, "No booking data to export.");
        
return;
    
}
    
try (FileWriter writer = new FileWriter("booking_history.txt")) {
        
writer.write(content);
        
JOptionPane.showMessageDialog(null, "Bookings exported to booking_history.txt");
    
} catch (IOException e) {
        
JOptionPane.showMessageDialog(null, "Error exporting: " + e.getMessage());
    
}

}
private void InitializeCityCoordinates(){
cities.put("Mumbai", new GeoPosition(19.0760, 72.8777));
    
cities.put("Delhi", new GeoPosition(28.6139, 77.2090));
    
cities.put("Chennai", new GeoPosition(13.0827, 80.2707));
    
cities.put("Kolkata", new GeoPosition(22.5726, 88.3639));
    
cities.put("Bengaluru", new GeoPosition(12.9716, 77.5946));
    
cities.put("Hyderabad", new GeoPosition(17.3850, 78.4867));
    
cities.put("Tirunelveli", new GeoPosition(8.7139, 77.7567));
    
cities.put("Jaipur", new GeoPosition(26.9124, 75.7873));
    
cities.put("Ahmedabad", new GeoPosition(23.0225, 72.5714));


}
public JPanel createTrackFlightPanel() {

      
cities.put("Mumbai", new GeoPosition(19.0760, 72.8777));
    
cities.put("Delhi", new GeoPosition(28.6139, 77.2090));
    
cities.put("Chennai", new GeoPosition(13.0827, 80.2707));
    
cities.put("Kolkata", new GeoPosition(22.5726, 88.3639));
    
cities.put("Bengaluru", new GeoPosition(12.9716, 77.5946));
    
cities.put("Hyderabad", new GeoPosition(17.3850, 78.4867));
    
cities.put("Tirunelveli", new GeoPosition(8.7139, 77.7567));
    
cities.put("Jaipur", new GeoPosition(26.9124, 75.7873));
    
cities.put("Ahmedabad", new GeoPosition(23.0225, 72.5714));

    
map = new JXMapViewer();
    
TileFactory tileFactory = new DefaultTileFactory(new OSMTileFactoryInfo());
    
map.setTileFactory(tileFactory);
    
map.setZoom(5);
    
map.setAddressLocation(cities.get("Delhi"));

    
JPanel panel = new JPanel(new BorderLayout());

      
ObjectWrapper<DefaultWaypoint> planeWaypointWrapper = new ObjectWrapper<>(new DefaultWaypoint(cities.get("Delhi")));
    
Set<Waypoint> waypoints = new HashSet<>();
    
waypoints.add(planeWaypointWrapper.getObject());

    
WaypointPainter<Waypoint> painter = new WaypointPainter<Waypoint>() {
        
@Override
        
protected void doPaint(Graphics2D g, JXMapViewer map, int width, int height) {
            
GeoPosition pos = planeWaypointWrapper.getObject().getPosition();
            
Point2D point = map.getTileFactory().geoToPixel(pos, map.getZoom());
            
ImageIcon icon = new ImageIcon("planeicon.jpg.jpg");           
g.drawImage(icon.getImage(), (int) point.getX() - 15, (int) point.getY() - 15, null);
        
}
    
};

    
painter.setWaypoints(waypoints);
    
map.setOverlayPainter(painter);

    
panel.add(map, BorderLayout.CENTER);

      
JButton startButton = new JButton("Start Tracking");
panel.add(startButton, BorderLayout.SOUTH);


startButton.addActionListener(e -> {
    
System.out.println("Flight tracker started.");
    
  
GeoPosition source = cities.get(lastBookedSource);
    
GeoPosition dest = cities.get(lastBookedDestination);
    
    
departureTimeStr = "2025-05-12T10:00"; 
String arrivalTimeStr = "2025-05-12T13:00";   
DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

LocalDateTime departureTime = LocalDateTime.parse(departureTimeStr, formatter);

LocalDateTime arrivalTime = LocalDateTime.parse(arrivalTimeStr, formatter);   
String departureTimeStr = "2025-05-12T10:00"; 


long travelTimeMillis = calculateTravelTimeInMillis(departureTime, arrivalTime);
    
System.out.println("Travel time: " + travelTimeMillis + " milliseconds");
    
   
    
Timer timer = new Timer(50, null); 
final long[] startTime = {System.currentTimeMillis()
}; 
// Store start time to calculate elapsed time
    

timer.addActionListener(new ActionListener() {
        
@Override
        
public void actionPerformed(ActionEvent e) {
            
long elapsedTime = System.currentTimeMillis() - startTime[0];          
            
if (elapsedTime > travelTimeMillis) {
((Timer) e.getSource()).stop();              
return;
            
}

            
double progress = (double) elapsedTime / travelTimeMillis;          
double lat = source.getLatitude() + (dest.getLatitude() - source.getLatitude()) * progress;
            
double lon = source.getLongitude() + (dest.getLongitude() - source.getLongitude()) * progress;

            
GeoPosition currentPosition = new GeoPosition(lat, lon);
            
planeWaypointWrapper.getObject().setPosition(currentPosition);

            
map.setAddressLocation(currentPosition);      
map.setOverlayPainter(painter);          
            
map.repaint();
        
}
    
});

    
timer.start();
});
return panel;

}


public static class ObjectWrapper<T> {
    
private T object;
    
public ObjectWrapper(T object) { 
this.object = object; 
}
    
public T getObject() { 
return object; 
}
    
public void setObject(T object) { 
this.object = object; 
}

}
public class PlaneWaypoint implements Waypoint {
    
private GeoPosition position;

    
public PlaneWaypoint(GeoPosition pos) {
        
this.position = pos;
    
}

    
public void setPosition(GeoPosition pos) {
        
this.position = pos;
    
}

     
public GeoPosition getPosition() {
        
return position;
    
}

}
public void loginWithFace() {
    
try {
        
ProcessBuilder pb = new ProcessBuilder("python", "face_login.py");
        
Process p = pb.start();
        
BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        
String username = reader.readLine(); 
System.out.println(username);  // assuming first line is the username
        
if (username != null && !username.isEmpty()) {
            
if (isUserInDatabase(username)) {
                
JOptionPane.showMessageDialog(null, "Login successful: " + username);
  
try (Connection conn = connect()) {
        
PreparedStatement checkStmt = conn.prepareStatement("SELECT * FROM users WHERE username=?");
        
checkStmt.setString(1, username);
        
ResultSet rs = checkStmt.executeQuery();

        
if (rs.next()) {
            
resultArea.setText("Welcome back, " + username + "!");
        
} 
else {
            
PreparedStatement insertStmt = conn.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)");
            
insertStmt.setString(1, username);
            
insertStmt.setString(2, "password");
            
insertStmt.executeUpdate();
            
resultArea.setText("User registered and logged in: " + username);
        
}
    
} catch (SQLException ex) {
        
resultArea.setText("Database error: " + ex.getMessage());
    
}

                   
} else {
                
JOptionPane.showMessageDialog(null, "User not found in DB.");
            
}
        
} else {
            
JOptionPane.showMessageDialog(null, "Face not recognized.");
        
}
    
} catch (Exception ex) {
        
ex.printStackTrace();
    
}

}


private boolean isUserInDatabase(String username) {
    try {
        
Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/airline_system","root","forachieve");
        
PreparedStatement ps = conn.prepareStatement("SELECT * FROM face_users WHERE username = ?");
        
ps.setString(1, username);
        
ResultSet rs = ps.executeQuery();
        
return rs.next();
    
} catch (Exception e) {
        
e.printStackTrace();
        
return false;
    
}

}
public static long calculateTravelTimeInMillis(LocalDateTime departureTime, LocalDateTime arrivalTime) {
     
return ChronoUnit.MILLIS.between(departureTime, arrivalTime);

}
class VoiceAssistant extends Thread {
    
private airs app;

    
public VoiceAssistant(airs app) {
        
this.app = app; 
}

    
@Override
    
public void run() {
        
try {
            
//LibVosk.setLogLevel(LogLevel.NONE); // Turn off Vosk internal logs
            
Model model = new Model("vosk-model-small-en-us-0.15"); // folder name where vosk model is extracted

            
AudioFormat format = new AudioFormat(16000, 16, 1, true, false);
            
DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            
if (!AudioSystem.isLineSupported(info)) {
                
System.out.println("Microphone not supported");
                
return;
            
}
            
TargetDataLine microphone = (TargetDataLine) AudioSystem.getLine(info);
            
microphone.open(format);
            
microphone.start();

            
Recognizer recognizer = new Recognizer(model, 16000);
            
byte[] buffer = new byte[4096];
            
            
while (true) {
                
int nbytes = microphone.read(buffer, 0, buffer.length);
                
if (recognizer.acceptWaveForm(buffer, nbytes)) {
    
String result = recognizer.getResult();
    
//handleVoicePanelCommand(result.toLowerCase());
System.out.println(result);
    
if (result.contains("book flight")) {
        
System.out.println("Voice command recognized: Book Flight!");
        
SwingUtilities.invokeLater(() -> app.bookFlight());
}
else if(result.contains("cancel booking")){
System.out.println("voice command recognized: Cancel Booking!");
SwingUtilities.invokeLater(() -> app.cancelBooking());
}

else if(result.contains("select from the map")){
System.out.println("voice command recognized: opening the map!");
SwingUtilities.invokeLater(() -> app.openMap());
}

else if(result.contains("search flights")){
System.out.println("voice command recognized: searching flights!");
SwingUtilities.invokeLater(() -> app.searchFlights());
}

else if(result.contains("log in")){
System.out.println("voice command recognized: logging in!");
SwingUtilities.invokeLater(() -> app.loginOrRegister());
}

else if(result.contains("select seats graphically")){
System.out.println("voice command recognized:opening the graphical seats!");
SwingUtilities.invokeLater(() -> app.openSeatSelection());
}

else if(result.contains("login through face")){
System.out.println("voice command recognized:viewing bookings");
SwingUtilities.invokeLater(() -> app.loginWithFace());
}
else if(result.contains("view bookings")){
System.out.println("voice command recognized:viewing bookings");
SwingUtilities.invokeLater(() -> app.viewBookings());
}
else if(result.contains("export bookings to file ")){
System.out.println("voice command recognized:viewing bookings");
SwingUtilities.invokeLater(() -> app.exportBookingsToFile());
}
else if (result.contains("open user login") || result.contains("user login") || result.contains("go to user login")) {
    
app.tabbedPane.setSelectedIndex(0); // First tab - User Login/Register

}
else if (result.contains("open search flight") || result.contains("go to search flight")) {
    
app.tabbedPane.setSelectedIndex(1); // Second tab - Search Flights

}
else if (result.contains("open book flight") || result.contains("go to book flight")) {
    
app.tabbedPane.setSelectedIndex(2); // Third tab - Book Flight

}
else if (result.contains("open cancel booking") || result.contains("go to cancel booking")) {
    
app.tabbedPane.setSelectedIndex(3); // Fourth tab - Cancel Booking

}
else if (result.contains("open view booking") || result.contains("go to view  booking")) {
    
app.tabbedPane.setSelectedIndex(4); // Fifth tab - View Bookings

}
else if (result.contains("open chatbot assistance") || result.contains("go to chatbot assistant")) {
    
app.tabbedPane.setSelectedIndex(6); // Sixth tab - ChatBot Assistant

}
else if (result.contains("open Track Flight") || result.contains("go to Track Flight")) {
    
app.tabbedPane.setSelectedIndex(5); // Sixth tab - ChatBot Assistant

}
}
}
        
} 
catch (Exception ex) {
            
ex.printStackTrace();
        
}
    
}

}
public static void main(String[] args) {
  

SwingUtilities.invokeLater(airs::new);
  
}

}
