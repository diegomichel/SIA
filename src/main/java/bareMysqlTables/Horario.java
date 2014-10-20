/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bareMysqlTables;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "horario", catalog = "asistenciasx", schema = "")
@NamedQueries({
    @NamedQuery(name = "Horario.findAll", query = "SELECT h FROM Horario h"),
    @NamedQuery(name = "Horario.findByIdhorario", query = "SELECT h FROM Horario h WHERE h.idhorario = :idhorario"),
    @NamedQuery(name = "Horario.findByHoraEntrada", query = "SELECT h FROM Horario h WHERE h.horaEntrada = :horaEntrada"),
    @NamedQuery(name = "Horario.findByHoraSalida", query = "SELECT h FROM Horario h WHERE h.horaSalida = :horaSalida"),
    @NamedQuery(name = "Horario.findByLun", query = "SELECT h FROM Horario h WHERE h.lun = :lun"),
    @NamedQuery(name = "Horario.findByMar", query = "SELECT h FROM Horario h WHERE h.mar = :mar"),
    @NamedQuery(name = "Horario.findByMie", query = "SELECT h FROM Horario h WHERE h.mie = :mie"),
    @NamedQuery(name = "Horario.findByJue", query = "SELECT h FROM Horario h WHERE h.jue = :jue"),
    @NamedQuery(name = "Horario.findByVie", query = "SELECT h FROM Horario h WHERE h.vie = :vie"),
    @NamedQuery(name = "Horario.findBySab", query = "SELECT h FROM Horario h WHERE h.sab = :sab"),
    @NamedQuery(name = "Horario.findByEdif", query = "SELECT h FROM Horario h WHERE h.edif = :edif"),
    @NamedQuery(name = "Horario.findByAula", query = "SELECT h FROM Horario h WHERE h.aula = :aula"),
    @NamedQuery(name = "Horario.findByFechaInicio", query = "SELECT h FROM Horario h WHERE h.fechaInicio = :fechaInicio"),
    @NamedQuery(name = "Horario.findByFechaFin", query = "SELECT h FROM Horario h WHERE h.fechaFin = :fechaFin"),
    @NamedQuery(name = "Horario.findByIdcurso", query = "SELECT h FROM Horario h WHERE h.idcurso = :idcurso")})
public class Horario implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idhorario")
    private Integer idhorario;
    @Column(name = "horaEntrada")
    private Integer horaEntrada;
    @Column(name = "horaSalida")
    private Integer horaSalida;
    @Column(name = "Lun")
    private Boolean lun;
    @Column(name = "Mar")
    private Boolean mar;
    @Column(name = "Mie")
    private Boolean mie;
    @Column(name = "Jue")
    private Boolean jue;
    @Column(name = "Vie")
    private Boolean vie;
    @Column(name = "Sab")
    private Boolean sab;
    @Column(name = "edif")
    private String edif;
    @Column(name = "aula")
    private String aula;
    @Column(name = "fechaInicio")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;
    @Column(name = "fechaFin")
    @Temporal(TemporalType.DATE)
    private Date fechaFin;
    @Column(name = "idcurso")
    private Integer idcurso;

    public Horario() {
    }

    public Horario(Integer idhorario) {
        this.idhorario = idhorario;
    }

    public Integer getIdhorario() {
        return idhorario;
    }

    public void setIdhorario(Integer idhorario) {
        Integer oldIdhorario = this.idhorario;
        this.idhorario = idhorario;
        changeSupport.firePropertyChange("idhorario", oldIdhorario, idhorario);
    }

    public Integer getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Integer horaEntrada) {
        Integer oldHoraEntrada = this.horaEntrada;
        this.horaEntrada = horaEntrada;
        changeSupport.firePropertyChange("horaEntrada", oldHoraEntrada, horaEntrada);
    }

    public Integer getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Integer horaSalida) {
        Integer oldHoraSalida = this.horaSalida;
        this.horaSalida = horaSalida;
        changeSupport.firePropertyChange("horaSalida", oldHoraSalida, horaSalida);
    }

    public Boolean getLun() {
        return lun;
    }

    public void setLun(Boolean lun) {
        Boolean oldLun = this.lun;
        this.lun = lun;
        changeSupport.firePropertyChange("lun", oldLun, lun);
    }

    public Boolean getMar() {
        return mar;
    }

    public void setMar(Boolean mar) {
        Boolean oldMar = this.mar;
        this.mar = mar;
        changeSupport.firePropertyChange("mar", oldMar, mar);
    }

    public Boolean getMie() {
        return mie;
    }

    public void setMie(Boolean mie) {
        Boolean oldMie = this.mie;
        this.mie = mie;
        changeSupport.firePropertyChange("mie", oldMie, mie);
    }

    public Boolean getJue() {
        return jue;
    }

    public void setJue(Boolean jue) {
        Boolean oldJue = this.jue;
        this.jue = jue;
        changeSupport.firePropertyChange("jue", oldJue, jue);
    }

    public Boolean getVie() {
        return vie;
    }

    public void setVie(Boolean vie) {
        Boolean oldVie = this.vie;
        this.vie = vie;
        changeSupport.firePropertyChange("vie", oldVie, vie);
    }

    public Boolean getSab() {
        return sab;
    }

    public void setSab(Boolean sab) {
        Boolean oldSab = this.sab;
        this.sab = sab;
        changeSupport.firePropertyChange("sab", oldSab, sab);
    }

    public String getEdif() {
        return edif;
    }

    public void setEdif(String edif) {
        String oldEdif = this.edif;
        this.edif = edif;
        changeSupport.firePropertyChange("edif", oldEdif, edif);
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        String oldAula = this.aula;
        this.aula = aula;
        changeSupport.firePropertyChange("aula", oldAula, aula);
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        Date oldFechaInicio = this.fechaInicio;
        this.fechaInicio = fechaInicio;
        changeSupport.firePropertyChange("fechaInicio", oldFechaInicio, fechaInicio);
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        Date oldFechaFin = this.fechaFin;
        this.fechaFin = fechaFin;
        changeSupport.firePropertyChange("fechaFin", oldFechaFin, fechaFin);
    }

    public Integer getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(Integer idcurso) {
        Integer oldIdcurso = this.idcurso;
        this.idcurso = idcurso;
        changeSupport.firePropertyChange("idcurso", oldIdcurso, idcurso);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhorario != null ? idhorario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Horario)) {
            return false;
        }
        Horario other = (Horario) object;
        if ((this.idhorario == null && other.idhorario != null) || (this.idhorario != null && !this.idhorario.equals(other.idhorario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bareMysqlTables.Horario[ idhorario=" + idhorario + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
