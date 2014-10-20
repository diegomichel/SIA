/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otherPackages;

import bareMysqlTables.Profesor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author diego
 */
public class ImportHuellas {

    public ImportHuellas() {
    }

    public static void main(String args[]) throws ClassNotFoundException {
        try {
            System.out.println("Starting...");
            Class.forName("java.sql.Driver");
            Connection conexionOldTable = DriverManager.getConnection("jdbc:mysql://localhost:3306/control_asistencia", "root", "1234");
            Statement stmt = conexionOldTable.createStatement();

            String query = "Select * from huella;";
            ResultSet rs = stmt.executeQuery(query);

            Profesor profe;
            EntityManager em = javax.persistence.Persistence.createEntityManagerFactory("asistenciasx?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
            while (rs.next()) {
                String codigo = rs.getString("Codigo");
                byte huella[] = rs.getBytes("huella");
                profe = em.find(Profesor.class, Integer.parseInt(codigo));
                if(profe == null) continue;
                System.out.println(profe.getNombres());
                em.getTransaction().begin();
                profe.setHuella(huella);
                em.getTransaction().commit();
            }
            System.out.println("Done :D...");

        } catch (SQLException ex) {
            Logger.getLogger(ImportHuellas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
