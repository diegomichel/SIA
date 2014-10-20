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
@Table(name = "profesor_acceso", catalog = "asistenciasx", schema = "")
@NamedQueries({
    @NamedQuery(name = "ProfesorAcceso.findAll", query = "SELECT p FROM ProfesorAcceso p"),
    @NamedQuery(name = "ProfesorAcceso.findByIdprofesorAcceso", query = "SELECT p FROM ProfesorAcceso p WHERE p.idprofesorAcceso = :idprofesorAcceso"),
    @NamedQuery(name = "ProfesorAcceso.findByIdprofesor", query = "SELECT p FROM ProfesorAcceso p WHERE p.idprofesor = :idprofesor"),
    @NamedQuery(name = "ProfesorAcceso.findByFechayhora", query = "SELECT p FROM ProfesorAcceso p WHERE p.fechayhora = :fechayhora")})
public class ProfesorAcceso implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idprofesor_acceso")
    private Integer idprofesorAcceso;
    @Basic(optional = false)
    @Column(name = "idprofesor")
    private int idprofesor;
    @Basic(optional = false)
    @Column(name = "fechayhora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechayhora;

    public ProfesorAcceso() {
    }

    public ProfesorAcceso(Integer idprofesorAcceso) {
        this.idprofesorAcceso = idprofesorAcceso;
    }

    public ProfesorAcceso(Integer idprofesorAcceso, int idprofesor, Date fechayhora) {
        this.idprofesorAcceso = idprofesorAcceso;
        this.idprofesor = idprofesor;
        this.fechayhora = fechayhora;
    }

    public Integer getIdprofesorAcceso() {
        return idprofesorAcceso;
    }

    public void setIdprofesorAcceso(Integer idprofesorAcceso) {
        Integer oldIdprofesorAcceso = this.idprofesorAcceso;
        this.idprofesorAcceso = idprofesorAcceso;
        changeSupport.firePropertyChange("idprofesorAcceso", oldIdprofesorAcceso, idprofesorAcceso);
    }

    public int getIdprofesor() {
        return idprofesor;
    }

    public void setIdprofesor(int idprofesor) {
        int oldIdprofesor = this.idprofesor;
        this.idprofesor = idprofesor;
        changeSupport.firePropertyChange("idprofesor", oldIdprofesor, idprofesor);
    }

    public Date getFechayhora() {
        return fechayhora;
    }

    public void setFechayhora(Date fechayhora) {
        Date oldFechayhora = this.fechayhora;
        this.fechayhora = fechayhora;
        changeSupport.firePropertyChange("fechayhora", oldFechayhora, fechayhora);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprofesorAcceso != null ? idprofesorAcceso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProfesorAcceso)) {
            return false;
        }
        ProfesorAcceso other = (ProfesorAcceso) object;
        if ((this.idprofesorAcceso == null && other.idprofesorAcceso != null) || (this.idprofesorAcceso != null && !this.idprofesorAcceso.equals(other.idprofesorAcceso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bareMysqlTables.ProfesorAcceso[ idprofesorAcceso=" + idprofesorAcceso + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
