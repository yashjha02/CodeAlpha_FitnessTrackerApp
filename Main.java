import javax.swing.*;
import java.sql.*;

public class Main {

    static Connection conn;

    public static void main(String[] args) {

        try {
            conn = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS fitness (steps INT, calories INT, workout TEXT)");
        } catch (Exception e) {
            System.out.println(e);
        }

        JFrame f = new JFrame("Fitness Tracker");
        f.setSize(350, 250);
        f.setLayout(null);

        JLabel l1 = new JLabel("Steps:");
        l1.setBounds(20, 20, 100, 25);
        f.add(l1);

        JTextField steps = new JTextField();
        steps.setBounds(120, 20, 150, 25);
        f.add(steps);

        JLabel l2 = new JLabel("Calories:");
        l2.setBounds(20, 60, 100, 25);
        f.add(l2);

        JTextField calories = new JTextField();
        calories.setBounds(120, 60, 150, 25);
        f.add(calories);

        JLabel l3 = new JLabel("Workout:");
        l3.setBounds(20, 100, 100, 25);
        f.add(l3);

        JTextField workout = new JTextField();
        workout.setBounds(120, 100, 150, 25);
        f.add(workout);

        JButton save = new JButton("Save");
        save.setBounds(50, 150, 100, 30);
        f.add(save);

        JButton view = new JButton("View");
        view.setBounds(170, 150, 100, 30);
        f.add(view);

        save.addActionListener(e -> {
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO fitness VALUES (?, ?, ?)");
                ps.setInt(1, Integer.parseInt(steps.getText()));
                ps.setInt(2, Integer.parseInt(calories.getText()));
                ps.setString(3, workout.getText());
                ps.executeUpdate();
                JOptionPane.showMessageDialog(f, "Saved!");
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        view.addActionListener(e -> {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM fitness");

                String data = "";
                while (rs.next()) {
                    data += rs.getInt("steps") + ", "
                          + rs.getInt("calories") + ", "
                          + rs.getString("workout") + "\n";
                }

                JOptionPane.showMessageDialog(f, data);
            } catch (Exception ex) {
                System.out.println(ex);
            }
        });

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
