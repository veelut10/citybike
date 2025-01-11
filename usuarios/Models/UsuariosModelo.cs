using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Usuarios.Modelo
{
    public class Usuario
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string Id { get; set; }
     
        public string Nombre { get; set; }
      
        public string Contraseña { get; set; }
       
        public string OAuth2Id { get; set; }

        public string Rol { get; set; }

        public CodigoActivacion CodigoActivacion { get; set; } 
    }

    public class CodigoActivacion
    {
        public string IdUsuario { get; set; }

        public string Codigo { get; set; }

        public DateTime FechaExpiracion { get; set; }
    }

    public class Claims{

        public string Id { get; set; }

        public string Nombre { get; set; }

        public string Rol { get; set; }
    }
}