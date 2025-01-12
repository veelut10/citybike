using System;
using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace Usuarios.Modelo
{
    public class Usuario
    {
        [BsonId]
        public string Id { get; set; }
     
        [BsonElement("nombre")]
        public string Nombre { get; set; }

        [BsonElement("contraseña")]
        public string Contraseña { get; set; }

        [BsonElement("oAuth2Id")]
        public string OAuth2Id { get; set; }

        [BsonElement("rol")]
        public string Rol { get; set; }

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