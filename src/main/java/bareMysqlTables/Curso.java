/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bareMysqlTables;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *
 * @author diego
 */
@Entity
@Table(name = "curso", catalog = "asistenciasx", schema = "")
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
    @NamedQuery(name = "Curso.findByIdcurso", query = "SELECT c FROM Curso c WHERE c.idcurso = :idcurso"),
    @NamedQuery(name = "Curso.findByDepartamento", query = "SELECT c FROM Curso c WHERE c.departamento = :departamento"),
    @NamedQuery(name = "Curso.findBySec", query = "SELECT c FROM Curso c WHERE c.sec = :sec"),
    @NamedQuery(name = "Curso.findByIdprofesor", query = "SELECT c FROM Curso c WHERE c.idprofesor = :idprofesor"),
    @NamedQuery(name = "Curso.findByIdmateria", query = "SELECT c FROM Curso c WHERE c.idmateria = :idmateria"),
    @NamedQuery(name = "Curso.findByIdcarrera", query = "SELECT c FROM Curso c WHERE c.idcarrera = :idcarrera")})
public class Curso implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idcurso")
    private Integer idcurso;
    @Column(name = "departamento")
    private String departamento;
    @Column(name = "sec")
    private String sec;
    @Column(name = "idprofesor")
    private Integer idprofesor;
    @Column(name = "idmateria")
    private String idmateria;
    @Column(name = "idcarrera")
    private String idcarrera;

    public Curso() {
    }

    public Curso(Integer idcurso) {
        this.idcurso = idcurso;
    }

    public Integer getIdcurso() {
        return idcurso;
    }

    public void setIdcurso(Integer idcurso) {
        Integer oldIdcurso = this.idcurso;
        this.idcurso = idcurso;
        changeSupport.firePropertyChange("idcurso", oldIdcurso, idcurso);
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        String oldDepartamento = this.departamento;
        this.departamento = departamento;
        changeSupport.firePropertyChange("departamento", oldDepartamento, departamento);
    }

    public String getSec() {
        return sec;
    }

    public void setSec(String sec) {
        String oldSec = this.sec;
        this.sec = sec;
        changeSupport.firePropertyChange("sec", oldSec, sec);
    }

    public Integer getIdprofesor() {
        return idprofesor;
    }

    public void setIdprofesor(Integer idprofesor) {
        Integer oldIdprofesor = this.idprofesor;
        this.idprofesor = idprofesor;
        changeSupport.firePropertyChange("idprofesor", oldIdprofesor, idprofesor);
    }

    public String getIdmateria() {
        return idmateria;
    }

    public void setIdmateria(String idmateria) {
        String oldIdmateria = this.idmateria;
        this.idmateria = idmateria;
        changeSupport.firePropertyChange("idmateria", oldIdmateria, idmateria);
    }

    public String getIdcarrera() {
        return idcarrera;
    }

    public void setIdcarrera(String idcarrera) {
        String oldIdcarrera = this.idcarrera;
        this.idcarrera = idcarrera;
        changeSupport.firePropertyChange("idcarrera", oldIdcarrera, idcarrera);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcurso != null ? idcurso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.idcurso == null && other.idcurso != null) || (this.idcurso != null && !this.idcurso.equals(other.idcurso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bareMysqlTables.Curso[ idcurso=" + idcurso + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
