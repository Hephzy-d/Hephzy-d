import javax.swing.SwingUtilities;

import register.AccountTypeSelection;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AccountTypeSelection().setVisible(true);
            }
        });
    }
}
