
using Usuarios.Modelo;
using Repositorio;

namespace Usuarios.Servicio
{
    public interface IServicioUsuarios
    {
        string GenerarCodigoActivacion(string idUsuario);
        
        Usuario AltaUsuario(string id, string codigoActivacion, string nombre,  string contraseña, string oauth2);
        
        void BajaUsuario(string idUsuario);

        Dictionary<string, string> VerificarUsuario(string nombreUsuario, string contraseña);

        Dictionary<string, string> VerificarUsuarioOAuth2(string oauth2Id);
        
        List<Usuario> ListadoUsuarios();
        
    }

    public class ServicioUsuarios : IServicioUsuarios
    {

        private Repositorio<Usuario, String> repositorioUsuarios;
        
        public ServicioUsuarios(Repositorio<Usuario, String> repositorio)
        {
            repositorioUsuarios = repositorio;
        }
        public string GenerarCodigoActivacion(string idUsuario)
        {   
            Usuario usuario = repositorioUsuarios.GetById(idUsuario);
            if (usuario == null)
                return null;

            string codigoActivacion = Guid.NewGuid().ToString();

            return codigoActivacion;
        }

        public Usuario AltaUsuario(string id, string codigoActivacion, string nombre, string contraseña, string oauth2)
        {
            return null;
        }
        
        public void BajaUsuario(string idUsuario)
        {
            Usuario usuario = repositorioUsuarios.GetById(idUsuario);
            if (usuario == null)
                return;

            repositorioUsuarios.Delete(usuario);
        }

        public Dictionary<string, string> VerificarUsuario(string nombreUsuario, string contraseña)
        {   
            List<Usuario> usuarios = repositorioUsuarios.GetAll();

            Usuario usuario = usuarios.FirstOrDefault(u => u.Nombre == nombreUsuario && u.Contraseña == contraseña);

            if (usuario == null)
                return null;

            Dictionary<string, string> claims = new Dictionary<string, string>();

            claims.Add("idUsuario", usuario.Id);
            claims.Add("nombre", usuario.Nombre);
            claims.Add("rol", usuario.Rol);

            return claims;
        }

        public Dictionary<string, string> VerificarUsuarioOAuth2(string oauth2Id)
        {
            List<Usuario> usuarios = repositorioUsuarios.GetAll();

            Usuario usuario = usuarios.FirstOrDefault(u => u.OAuth2Id == oauth2Id);

            if (usuario == null)
                return null;

            Dictionary<string, string> claims = new Dictionary<string, string>();

            claims.Add("idUsuario", usuario.Id);
            claims.Add("nombre", usuario.Nombre);
            claims.Add("rol", usuario.Rol);

            return claims;
        }

        public List<Usuario> ListadoUsuarios ()
        {
            return repositorioUsuarios.GetAll();
        }

    }

}