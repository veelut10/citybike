using Microsoft.AspNetCore.Mvc;
using Usuarios.Modelo;
using Usuarios.Servicio;

namespace UsuariosControllers
{
    public class UsuarioContraseña
    {
        public string IdUsuario { get; set; }

        public string Contraseña { get; set; }
    }

    public class IdentificadorOAuth2
    {
        public string OAuth2Id { get; set; }
    }
 

    [Route("api/usuarios")]
    [ApiController]
    public class UsuariosController : ControllerBase
    {
        private readonly IServicioUsuarios _servicioUsuarios;

        public UsuariosController(IServicioUsuarios servicio)
        {
            _servicioUsuarios = servicio;
        }

        [HttpGet("{idUsuario}", Name = "GenerarCodigoActivacion")]
        public ActionResult<CodigoActivacion> Get(string idUsuario)
        {
            CodigoActivacion codigoActivacion = _servicioUsuarios.GenerarCodigoActivacion(idUsuario);
           
            if (codigoActivacion == null)
                return NotFound();

            return codigoActivacion;
        }

        [HttpPost (Name = "AltaUsuario")]
        public ActionResult<Usuario> AltaUsuario(Usuario usuario)
        {   
            _servicioUsuarios.AltaUsuario(usuario.CodigoActivacion, usuario.Id, usuario.Nombre, usuario.Contraseña, usuario.OAuth2Id);

            return CreatedAtRoute("GenerarCodigoActivacion", new {idUsuario = usuario.Id}, usuario); 
        }

        [HttpDelete ("{idUsuario}", Name = "BajaUsuario")]
        public IActionResult Delete(string idUsuario)
        {
            _servicioUsuarios.BajaUsuario(idUsuario);
            
            return NoContent();
        }

        [HttpPost ("verificacionContraseña", Name = "VerificarUsuarioContraseña")]
        public ActionResult<Claims> VerificarUsuarioContraseña(UsuarioContraseña usuarioContraseña)
        {    
            Claims claims = _servicioUsuarios.VerificarUsuarioContraseña(usuarioContraseña.IdUsuario, usuarioContraseña.Contraseña);
            if (claims == null){
                return NotFound();
            }else{
                return claims;
            }
        }

        [HttpPost ("verificacionOauth2", Name = "VerificarUsuarioOAuth2")]
        public ActionResult<Claims> VerificarUsuarioOAuth2(IdentificadorOAuth2 identificadorOAuth2)
        {
            Claims claims = _servicioUsuarios.VerificarUsuarioOAuth2(identificadorOAuth2.OAuth2Id);
            
            if (claims == null){
                return NotFound();
            }else{
                return claims;
            } 
        }

        [HttpGet (Name = "GetListadoUsuarios")]
        public  ActionResult<List<Usuario>> Get()
        {
            return _servicioUsuarios.GetListadoUsuarios();
        }
       
    }
}