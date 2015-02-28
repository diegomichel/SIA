/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package otherPackages;

import bareMysqlTables.Registro;
import com.dmrr.asistenciasx.MyUtils;
import static com.dmrr.asistenciasx.MyUtils.convertHorariosFromBooleanToHours;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.joda.time.DateTime;

/**
 *
 * @author diegomichel
 */
public class FixAsistenciasOfDay {
    
    EntityManager em;
    DateTime dateTime;
    private List<Map> results;
    String fecha;

    public FixAsistenciasOfDay(String fecha) {
        this.fecha = fecha;//"2015-02-17";
        conectarDB();
        convertirAccesoToAsistencias();
    }
    
    public void conectarDB() {
        em = javax.persistence.Persistence.createEntityManagerFactory("asistenciasx?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
    }

    public void convertirAccesoToAsistencias() {
        Query getAccesos = em.createNativeQuery("SELECT * FROM asistenciasx.profesor_acceso WHERE fechayhora BETWEEN '" + fecha + " 00:00:00' AND '" + fecha + " 23:55:55'");
        getAccesos.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
        List<Map> x = getAccesos.getResultList();
        for (Map row : x) {
            checaMateriasQueCoincidadConLaHoraDeEntrada(row.get("idprofesor") + "", row.get("fechayhora") + "");
        }
    }

    private void generaAsistencia(Map row) {
        Query checkIfAsistenciaExiste = em.createNativeQuery(
                "SELECT * FROM asistenciasx.registro WHERE idhorario = '" + row.get("idhorario") + "' AND fechayhora BETWEEN '" + fecha + " 00:00:00' AND '" + fecha + " 23:55:55'"
        );
        try {
            Object x = checkIfAsistenciaExiste.getSingleResult();
            //Do nada
        } catch (javax.persistence.NoResultException e) {
            System.out.println("Restoring falta");
            Registro registro = new Registro();
            registro.setFechayhora(dateTime.toDate());
            registro.setIdhorario(Integer.parseInt(row.get("idhorario") + ""));

            em.getTransaction().begin();
            em.persist(registro);
            em.getTransaction().commit();
        } catch (javax.persistence.NonUniqueResultException ex) {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM asistenciasx.registro WHERE idhorario = '" + row.get("idhorario") +"'").executeUpdate();
            em.getTransaction().commit();
            System.out.println("Deleting falta");
            generaAsistencia(row);
        }
    }

    private void convertHourFromH55toH00Ejemplo855to900(Map row) {
        if (row.get("horaSalida") != null) {
            String salida = row.get("horaSalida").toString();
            if (salida.charAt(salida.length() - 1) == '5') {
                String hora = "";
                if (salida.length() == 3) {
                    hora = Character.getNumericValue(salida.charAt(0)) + "";
                }
                if (salida.length() == 4) {
                    hora = Character.getNumericValue(salida.charAt(0)) + "" + Character.getNumericValue(salida.charAt(1));
                }
                hora = (Integer.parseInt(hora) + 1) + "";
                row.replace("horaSalida", hora + "00");
            }
        }
    }

    private void checaMateriasQueCoincidadConLaHoraDeEntrada(String idprofesor, String fecha) {
        String[] fechaBits = fecha.split(" ");
        String[] yyyymmdd = fechaBits[0].split("-");
        String[] hhmm = fechaBits[1].split(":");

        Query queryHorariosPorProfesor = em.createNativeQuery("Select * from horariosporprofesor WHERE idprofesor=" + idprofesor + " order by " + MyUtils.getTodayString3Char() + " desc, horaEntrada asc");
        queryHorariosPorProfesor.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
        results = queryHorariosPorProfesor.getResultList();

        for (Map row : results) {
            convertHourFromH55toH00Ejemplo855to900(row);
            convertHorariosFromBooleanToHours(row, "lun");
            convertHorariosFromBooleanToHours(row, "mar");
            convertHorariosFromBooleanToHours(row, "mie");
            convertHorariosFromBooleanToHours(row, "jue");
            convertHorariosFromBooleanToHours(row, "vie");
            convertHorariosFromBooleanToHours(row, "sab");
        }

        dateTime = new DateTime(Integer.parseInt(yyyymmdd[0]),
                Integer.parseInt(yyyymmdd[1]),
                Integer.parseInt(yyyymmdd[2]),
                Integer.parseInt(hhmm[0]),
                Integer.parseInt(hhmm[1]), 0, 0);

        Integer hora = dateTime.getHourOfDay();
        if (dateTime.getMinuteOfHour() >= 45) {
            hora++;
        }
        String horaString = hora + "00";

        boolean checaHoraConsecutiva = false;
        for (Map row : results) {
            switch (MyUtils.getDayOfWeek(yyyymmdd)) {
                case 2:
                    if (row.get("lun") == null) {
                        continue;
                    }
                    break;
                case 3:
                    if (row.get("mar") == null) {
                        continue;
                    }
                    break;
                case 4:
                    if (row.get("mie") == null) {
                        continue;
                    }
                    break;
                case 5:
                    if (row.get("jue") == null) {
                        continue;
                    }
                    break;
                case 6:
                    if (row.get("vie") == null) {
                        continue;
                    }
                    break;
                case 7:
                    if (null == row.get("sab")) {
                        continue;
                    }
                    break;
            }
            String horaEntrada = "" + row.get("horaEntrada");
            boolean permitirDobleRegistro = true;
            if (checaHoraConsecutiva && permitirDobleRegistro) {
                horaString = (hora + 1) + "00";
                if (horaEntrada.equals(horaString)) {
                    generaAsistencia(row);
                } else {
                    horaString = (hora + 2) + "00";
                    if (horaEntrada.equals(horaString)) {
                        generaAsistencia(row);
                    }
                }
                break;
            } else if (horaEntrada.equals(horaString)) {
                generaAsistencia(row);
                checaHoraConsecutiva = true;
            }
        }
    }
}
