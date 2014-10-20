/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bareMysqlTables;

/**
 *
 * @author diego
 */
public class HorarioMateriaHelper {

    public HorarioMateriaHelper() {
    }
    private Integer idcurso;
    private String nombre;
    private Integer horaEntrada;
    private Integer horaSalida;
    private Boolean lun;
    private Boolean mar;
    private Boolean mie;
    private Boolean jue;
    private Boolean vie;
    private Boolean sab;
    private String edif;
    private String aula;
    private Integer idprofesor;

    public Integer getIdprofesor() {
        return idprofesor;
    }

    public void setIdprofesor(Integer idprofesor) {
        this.idprofesor = idprofesor;
    }
    
    public Integer getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Integer horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Integer getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Integer horaSalida) {
        this.horaSalida = horaSalida;
    }

    public Boolean getLun() {
        return lun;
    }

    public void setLun(Boolean lun) {
        this.lun = lun;
    }

    public Boolean getMar() {
        return mar;
    }

    public void setMar(Boolean mar) {
        this.mar = mar;
    }

    public Boolean getMie() {
        return mie;
    }

    public void setMie(Boolean mie) {
        this.mie = mie;
    }

    public Boolean getJue() {
        return jue;
    }

    public void setJue(Boolean jue) {
        this.jue = jue;
    }

    public Boolean getVie() {
        return vie;
    }

    public void setVie(Boolean vie) {
        this.vie = vie;
    }

    public Boolean getSab() {
        return sab;
    }

    public void setSab(Boolean sab) {
        this.sab = sab;
    }

    public String getEdif() {
        return edif;
    }

    public void setEdif(String edif) {
        this.edif = edif;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public Integer getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
