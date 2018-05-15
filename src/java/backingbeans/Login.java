/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package backingbeans;

import entidadesJPA.Usuario;
import entidadesJPA.Rol;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;


/**
 *
 * @author francis
 */
@Named(value = "login")
@RequestScoped
public class Login {

    private String usuario;
    private String contrasenia;
    private List<Usuario> usuarios;
    
    
    @Inject
    private ControlAutorizacion ctrl;

    /**
     * Creates a new instance of Login
     */
    public Login() {
        
        usuarios = new ArrayList<Usuario>();
       
        usuarios.add(new Usuario("admin", "123", Rol.administrador));
        usuarios.add(new Usuario("educando", "123", Rol.educando));
        usuarios.add(new Usuario("voluntario", "123", Rol.voluntario));
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String autenticar() {
        // Implementar este método
        
        String result=null;
        
        Iterator <Usuario> it = usuarios.iterator();// cojemos un itearador de la lista de uuarios
        
        boolean encontrado =false;
        
        while(it.hasNext() && !encontrado)
        {
            Usuario us = it.next();
            
            if ( us.getAliasUs().equals(usuario)) //el usuario de la lista igual al usuario que recojemos del formulario
            {
                if (us.getContrasenia().equals(contrasenia))
                {
                    ctrl.setUsuario(us);
                    result=ctrl.home();
                    encontrado=true;
                    
                }
                else
                {
                    FacesContext ctx = FacesContext.getCurrentInstance();//objeto para crear el mensaje
                    ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Contraseña incorrecta", "Contraseña incorrecta"));
                }
            }
        }
        if (!encontrado){
            FacesContext ctx = FacesContext.getCurrentInstance();
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no encontrado", "Usuario no encontrado"));
        }
        return result;
    }
}
