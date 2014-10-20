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
@Table(name = "materia", catalog = "asistenciasx", schema = "")
@NamedQueries({
    @NamedQuery(name = "Materia.findAll", query = "SELECT m FROM Materia m"),
    @NamedQuery(name = "Materia.findByIdmateria", query = "SELECT m FROM Materia m WHERE m.idmateria = :idmateria"),
    @NamedQuery(name = "Materia.findByNombre", query = "SELECT m FROM Materia m WHERE m.nombre = :nombre"),
    @NamedQuery(name = "Materia.findByArea", query = "SELECT m FROM Materia m WHERE m.area = :area"),
    @NamedQuery(name = "Materia.findByCreditos", query = "SELECT m FROM Materia m WHERE m.creditos = :creditos"),
    @NamedQuery(name = "Materia.findByTeoria", query = "SELECT m FROM Materia m WHERE m.teoria = :teoria"),
    @NamedQuery(name = "Materia.findByPractica", query = "SELECT m FROM Materia m WHERE m.practica = :practica"),
    @NamedQuery(name = "Materia.findByTipo", query = "SELECT m FROM Materia m WHERE m.tipo = :tipo"),
    @NamedQuery(name = "Materia.findByNivel", query = "SELECT m FROM Materia m WHERE m.nivel = :nivel"),
    @NamedQuery(name = "Materia.findByExtraordinario", query = "SELECT m FROM Materia m WHERE m.extraordinario = :extraordinario")})
public class Materia implements Serializable {
    @Transient
    private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idmateria")
    private String idmateria;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "area")
    private String area;
    @Column(name = "creditos")
    private Integer creditos;
    @Column(name = "teoria")
    private Integer teoria;
    @Column(name = "practica")
    private Integer practica;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "nivel")
    private String nivel;
    @Column(name = "extraordinario")
    private String extraordinario;

    public Materia() {
    }

    public Materia(String idmateria) {
        this.idmateria = idmateria;
    }

    public Materia(String idmateria, String nombre) {
        this.idmateria = idmateria;
        this.nombre = nombre;
    }

    public String getIdmateria() {
        return idmateria;
    }

    public void setIdmateria(String idmateria) {
        String oldIdmateria = this.idmateria;
        this.idmateria = idmateria;
        changeSupport.firePropertyChange("idmateria", oldIdmateria, idmateria);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        String oldNombre = this.nombre;
        this.nombre = nombre;
        changeSupport.firePropertyChange("nombre", oldNombre, nombre);
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        String oldArea = this.area;
        this.area = area;
        changeSupport.firePropertyChange("area", oldArea, area);
    }

    public Integer getCreditos() {
        return creditos;
    }

    public void setCreditos(Integer creditos) {
        Integer oldCreditos = this.creditos;
        this.creditos = creditos;
        changeSupport.firePropertyChange("creditos", oldCreditos, creditos);
    }

    public Integer getTeoria() {
        return teoria;
    }

    public void setTeoria(Integer teoria) {
        Integer oldTeoria = this.teoria;
        this.teoria = teoria;
        changeSupport.firePropertyChange("teoria", oldTeoria, teoria);
    }

    public Integer getPractica() {
        return practica;
    }

    public void setPractica(Integer practica) {
        Integer oldPractica = this.practica;
        this.practica = practica;
        changeSupport.firePropertyChange("practica", oldPractica, practica);
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        String oldTipo = this.tipo;
        this.tipo = tipo;
        changeSupport.firePropertyChange("tipo", oldTipo, tipo);
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        String oldNivel = this.nivel;
        this.nivel = nivel;
        changeSupport.firePropertyChange("nivel", oldNivel, nivel);
    }

    public String getExtraordinario() {
        return extraordinario;
    }

    public void setExtraordinario(String extraordinario) {
        String oldExtraordinario = this.extraordinario;
        this.extraordinario = extraordinario;
        changeSupport.firePropertyChange("extraordinario", oldExtraordinario, extraordinario);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmateria != null ? idmateria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Materia)) {
            return false;
        }
        Materia other = (Materia) object;
        if ((this.idmateria == null && other.idmateria != null) || (this.idmateria != null && !this.idmateria.equals(other.idmateria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bareMysqlTables.Materia[ idmateria=" + idmateria + " ]";
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changeSupport.removePropertyChangeListener(listener);
    }
    
}
