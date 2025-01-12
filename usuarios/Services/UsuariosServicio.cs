
using Usuarios.Modelo;
using Repositorio;
using System.Collections.Generic;
using System.Linq;
using System;

namespace Usuarios.Servicio
{
    public interface IServicioUsuarios
    {
        CodigoActivacion GenerarCodigoActivacion(string idUsuario);
        
        string AltaUsuario(CodigoActivacion codigoActivacion, string idUsuario, string nombreUsuario,  string contraseña, string oauth2Id);
        
        void BajaUsuario(string idUsuario);

        Claims VerificarUsuarioContraseña(string idUsuario, string contraseña);

        Claims VerificarUsuarioOAuth2(string oauth2Id);
        
        List<Usuario> GetListadoUsuarios();
        
    }

    public class ServicioUsuarios : IServicioUsuarios
    {
        private Repositorio<Usuario, string> repositorioUsuarios;
        
        public ServicioUsuarios(Repositorio<Usuario, string> repositorio)
        {
            repositorioUsuarios = repositorio;
        }


        public CodigoActivacion GenerarCodigoActivacion(string idUsuario)
        {   
            Usuario usuario = repositorioUsuarios.GetById(idUsuario);

            //Comprobar que no hay ningun usuario con ese id
            if (usuario != null)
                return null;

            string codigo = Guid.NewGuid().ToString();

            CodigoActivacion codigoActivacion = new CodigoActivacion(){
                IdUsuario = idUsuario,
                Codigo = codigo,
                FechaExpiracion = DateTime.Now.AddDays(1)  
            };

            return codigoActivacion;
        }

        public string AltaUsuario(CodigoActivacion codigoActivacion, string idUsuario, string nombreUsuario, string contraseña, string oauth2Id)
        {
            if(codigoActivacion == null || codigoActivacion.IdUsuario != idUsuario || codigoActivacion.FechaExpiracion < DateTime.Now)
                return null;
            
            else
            {
                if(contraseña != null)
                {
                    if(VerificarUsuarioContraseña(idUsuario, contraseña) != null)
                    {
                        Usuario usuario = new Usuario(){
                            Id = idUsuario,
                            Nombre = nombreUsuario,
                            Contraseña = contraseña,
                            Rol = "usuario",
                        };

                        repositorioUsuarios.Add(usuario);

                        return usuario.Id;
                    }
                }
                else if(oauth2Id != null)
                {
                    if(VerificarUsuarioOAuth2(oauth2Id) != null)
                    {
                        Usuario usuario = new Usuario(){
                            Id = idUsuario,
                            Nombre = nombreUsuario,
                            OAuth2Id = oauth2Id,
                            Rol = "usuario",
                        };

                        repositorioUsuarios.Add(usuario);

                        return usuario.Id;
                    }
                }

                return null;
            }   
        }
        
        public void BajaUsuario(string idUsuario)
        {
            Usuario usuario = repositorioUsuarios.GetById(idUsuario);

            if (usuario == null)
                return;

            repositorioUsuarios.Delete(usuario);
        }

        public Claims VerificarUsuarioContraseña(string idUsuario, string contraseña)
        {   
            Usuario usuario = repositorioUsuarios.GetById(idUsuario);

            if (usuario == null)
                return null;

            Claims claims = new Claims(){
                Id = usuario.Id,
                Nombre = usuario.Nombre,
                Rol = usuario.Rol
            };

            return claims;
        }

        public Claims VerificarUsuarioOAuth2(string oauth2Id)
        {
            List<Usuario> usuarios = repositorioUsuarios.GetAll();

            Usuario usuario = usuarios.FirstOrDefault(u => u.OAuth2Id == oauth2Id);

            if (usuario == null)
                return null;

            Claims claims = new Claims(){
                Id = usuario.Id,
                Nombre = usuario.Nombre,
                Rol = usuario.Rol
            };

            return claims;
        }

        public List<Usuario> GetListadoUsuarios ()
        {
            return repositorioUsuarios.GetAll();
        }

    }

}