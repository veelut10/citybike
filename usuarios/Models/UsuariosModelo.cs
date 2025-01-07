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

    }
}